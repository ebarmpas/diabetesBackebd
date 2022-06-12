package edu.sheffield.cloudcomputing.diabetestracker;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;

import java.util.ArrayList;
import java.util.List;

//Singleton class
public class DiabetesDAO {
	
	final private static int ID = 1;
	final private static int DATE = 5;
	final private static int BLOOD_GLUCOSE = 2;
	final private static int CARB_INTAKE = 3;
	final private static int MEDICATION_DOSE = 4;
	
	private static DiabetesDAO instance;
	
	private static Connection database;
//	private static String url;
	
	private DiabetesDAO() {
		
	}
	
	//Creates and returns single class instance
	//The if statement ensures that, no matter how many times getInstance() is called (from external objects_, it will only create 
	//a single instance of the class
	public static DiabetesDAO getInstance() {
		if (instance == null) {
						
			try {
				Class.forName("org.postgresql.Driver");
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			}
			
			try {
				database = DriverManager.getConnection(System.getenv("JDBC_DATABASE_URL"));
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			
			instance = new DiabetesDAO(); 
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
				answer = new Day(res.getString(DATE),
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
				answer.add(new Day(res.getString(DATE),res.getDouble(BLOOD_GLUCOSE),
						res.getDouble(CARB_INTAKE),res.getDouble(MEDICATION_DOSE)));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return answer;
	}
	public List<String> listPatients(){
		ArrayList<String> answer = null;
		
		try {
		PreparedStatement statement= database.prepareStatement(
				"SELECT name FROM Patient;");
			statement.execute();
			ResultSet res = statement.getResultSet();
			
			answer = new ArrayList<String>();
			
			while(res.next())
				answer.add(res.getString(1));
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
				answer.add(new Day(res.getString(DATE),res.getDouble(BLOOD_GLUCOSE),
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
	
	
	public int addDay(String patient, Day day) {
		
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
			statement.setInt(5, getPatientId(patient));
			
			statement.execute();
			
			ResultSet res = statement.getResultSet();
			res.next();
			answer = res.getInt(1);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return answer;
	}
	
	public String addPatient(String patient) {
		
		String answer = null;
		
		if(getPatientId(patient) != 0)
			return answer;
		try {	
			PreparedStatement statement = database.prepareStatement("INSERT INTO Patient (name)"
					+ "VALUES (?)"
					+ "RETURNING name;");
			
			statement.setString(1, patient);
			statement.execute();
			
			ResultSet res = statement.getResultSet();
			res.next();
			answer = res.getString(1);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return answer;
		
	}
	
	public boolean deleteDay(String patient, String date) {
		
		boolean answer = false;
		try {
			PreparedStatement statement = database.prepareStatement("DELETE FROM Day"
					+ "WHERE tracked_day = ?"
					+ "AND fk_patient = ?;");
			
			statement.setDate(1, Date.valueOf(date));
			statement.setInt(2, getPatientId(patient));
			
			statement.execute();
			answer = true;
		} catch (SQLException e) {
			answer = false;
			e.printStackTrace();
		}
		return answer;
	}
	
}

