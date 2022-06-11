package edu.sheffield.cloudcomputing.diabetestracker;

import java.util.List;

public class Test {
	public static void main(String[] args) {
		//This class does nothing but it can be used to perform CRUD operations on the database that can't be done on the frontend due to the CORS issue/
//		DayDAO dao = DayDAO.getInstance();

		String x = "name=Geo&bloodGlucose=5&carbIntake=5&medicationDose=5&trackedDay=3-3-3";
		
		String[] fields = x.split("[&=]");
		
		Day d = new Day(fields[9],3d,3d,3d);
		System.out.println(d);
		
		for(int i = 0; i < fields.length; i++)
			System.out.println(fields[i]);
	}
}
//Names of the currently registered patients:
//George
//Jim
//Maria
//Tiffany
//Harry
//Amy