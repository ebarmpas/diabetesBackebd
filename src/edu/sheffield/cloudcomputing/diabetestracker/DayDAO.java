package edu.sheffield.cloudcomputing.diabetestracker;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

//Singleton class
public class DayDAO {
	
	final private static int ID = 1;
	final private static int DATE = 5;
	final private static int BLOOD_GLUCOSE = 2;
	final private static int CARB_INTAKE = 3;
	final private static int MEDICATION_DOSE = 4;
	
	private static DayDAO instance;
	
	private static Connection database;
	private static String url;
	private static Properties props;
	
	static {
		
		url = "jdbc:postgres://vefwemgkfrlekv:80fb0c1818d3ed6620086e1173a5d763e80b96d4be9d5dc6403a47976ea9b580@ec2-52-18-116-67.eu-west-1.compute.amazonaws.com:5432/d5n22bs5ngdp5e";
		props = new Properties();
		
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		
		props.setProperty("user", "vefwemgkfrlekv");
		props.setProperty("password", "80fb0c1818d3ed6620086e1173a5d763e80b96d4be9d5dc6403a47976ea9b580");
		
		try {
			database = DriverManager.getConnection(url, props);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private DayDAO() {
		
	}
	
	//Creates and returns single class instance
	//The if statement ensures that, no matter how many times getInstance() is called (from external objects_, it will only create 
	//a single instance of the class
	public static DayDAO getInstance() {
		if (instance == null) {
			instance = new DayDAO(); 
		}

		
		return instance;				
	}

	public Day getDay(String date, String patient)  {
		
		Day answer = null;
		
		PreparedStatement statement;
		try {
			statement = database.prepareStatement("SELECT * FROM Day "
					+ "WHERE fk_patient = ?"
					+ "AND tracked_day = ?;");
			
			statement.setInt(1, getPatientId(patient));
			statement.setDate(2, Date.valueOf(date));
			
			statement.execute();
			ResultSet res = statement.getResultSet();
			if(res.next())
				answer = new Day(res.getInt(ID),res.getString(DATE),
						res.getDouble(BLOOD_GLUCOSE),res.getDouble(CARB_INTAKE),res.getDouble(MEDICATION_DOSE));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return answer;
	}
	private int getPatientId(String name) {
		int answer = 0 ;
		
		try {
			PreparedStatement statement = database.prepareStatement("SELECT patient_id FROM Patient WHERE name = ?;");
			statement.setString(1, name);
			statement.execute();
			
			ResultSet res = statement.getResultSet();
			if(res.next())
				answer = res.getInt(ID);
		} catch (SQLException e) {
			answer = -1;
		}
			
		return answer;
		
	}
	

	public List<Day> listAll(String Patient) {
		ArrayList<Day> answer = null;
			
		try {
			PreparedStatement statement= database.prepareStatement(
				"SELECT * FROM Day "
				+ "WHERE fk_patient = ?;");
			statement.setInt(1, getPatientId(Patient));
			statement.execute();
			ResultSet res = statement.getResultSet();
			
			answer = new ArrayList<Day>();
			
			while(res.next())
				answer.add(new Day(res.getInt(ID),res.getString(DATE),res.getDouble(BLOOD_GLUCOSE),
						res.getDouble(CARB_INTAKE),res.getDouble(MEDICATION_DOSE)));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return answer;
	}
	
	public List<Day> listBetween(String patient, String d1, String d2){
		ArrayList<Day> answer = null;
		
		try {
			 PreparedStatement statement = database.prepareStatement(
				"SELECT * FROM Day "
				+ "WHERE fk_patient = ?"
				+ "AND tracked_day >= ?"
				+ "AND tracked_day <= ?;");
			
			statement.setInt(1, getPatientId(patient));
			statement.setDate(2, Date.valueOf(d1));
			statement.setDate(3, Date.valueOf(d2));
			
			statement.execute();
			ResultSet res = statement.getResultSet();
			
			answer = new ArrayList<Day>();
			while(res.next()) 
				answer.add(new Day(res.getInt(ID),res.getString(DATE),res.getDouble(BLOOD_GLUCOSE),
						res.getDouble(CARB_INTAKE),res.getDouble(MEDICATION_DOSE)));
		} catch (SQLException e) {
			
		}
		
		return answer;
	}
	
	public double averageCarbIntakeAll(String patient) {
		double answer = 0;
		try {
			PreparedStatement statement = database.prepareStatement(
					"SELECT AVG(carb_intake) FROM Day "
					+ "WHERE fk_patient = ?;");
			
			statement.setInt(1, getPatientId(patient));
			
			statement.execute();
			
			ResultSet res = statement.getResultSet();
			res.next();
			answer = res.getDouble(1);
		} catch (SQLException e) {
			answer = -1;
		}

		return answer;
	}
	public double averageCarbIntakeBetween(String patient, String d1, String d2) {
		double answer = 0;
		try {
			PreparedStatement statement = database.prepareStatement(
					"SELECT AVG(carb_intake) FROM Day "
					+ "WHERE fk_patient = ?"
					+ "AND tracked_day >= ?"
					+ "AND tracked_day <= ?;");
			
			statement.setInt(1, getPatientId(patient));
			statement.setDate(2, Date.valueOf(d1));
			statement.setDate(3, Date.valueOf(d2));
			
			statement.execute();
			
			ResultSet res = statement.getResultSet();
			res.next();
			answer = res.getDouble(1);
		} catch (SQLException e) {
			answer = -1;
		}

		return answer;
	}
	
	public double averageBloodGlucoseAll(String patient) {
		double answer = 0;
		try {
			PreparedStatement statement = database.prepareStatement(
					"SELECT AVG(blood_glucose) FROM Day "
					+ "WHERE fk_patient = ?");
			
			statement.setInt(1, getPatientId(patient));		
			statement.execute();
			
			ResultSet res = statement.getResultSet();
			res.next();
			answer = res.getDouble(1);
		} catch (SQLException e) {
			answer = -1;
		}

		return answer;
	}
	
	public double averageBloodGlucoseBetween(String patient, String d1, String d2) {
		double answer = 0;
		try {
			PreparedStatement statement = database.prepareStatement(
					"SELECT AVG(blood_glucose) FROM Day "
					+ "WHERE fk_patient = ?"
					+ "AND tracked_day >= ?"
					+ "AND tracked_day <= ?;");
			
			statement.setInt(1, getPatientId(patient));
			statement.setDate(2, Date.valueOf(d1));
			statement.setDate(3, Date.valueOf(d2));
			
			statement.execute();
			
			ResultSet res = statement.getResultSet();
			res.next();
			answer = res.getDouble(1);
		} catch (SQLException e) {
			answer = -1;
		}

		return answer;
	}
	
	public int addDay(String Patient, Day day) {
		
		int answer = -1;
		try {
			PreparedStatement statement = database.prepareStatement("INSERT INTO Day"
					+ "(blood_glucose, carb_intake, medication_dose, tracked_day, fk_patient)"
					+ " VALUES (?, ?, ?, ?, ?)"
					+ "RETURNING day_id;");
			
			statement.setDouble(1, day.getBloodGlucose());
			statement.setDouble(2, day.getCarbIntake());
			statement.setDouble(3, day.getMedicationDose());
			statement.setDate(4, Date.valueOf(day.getDate()));
			statement.setInt(5, getPatientId(Patient));
			
			statement.execute();
			ResultSet res = statement.getResultSet();
			res.next();
			answer = res.getInt(1);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return answer;
	}
}

