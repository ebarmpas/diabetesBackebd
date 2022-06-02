package edu.sheffield.cloudcomputing.diabetestracker;


public class Test {
	public static void main(String[] args) {
		DayDAO dao = DayDAO.getInstance();
		
		
//		Day d = new Day(1, "2021-01-16", 5, 5, 2);
//		System.out.println(dao.addDay("Jenny", d));
//		System.out.println(dao.averageCarbIntake("George", "2021-01-01", "2021-02-01"));
		System.out.println(dao.averageBloodGlucoseAll("Jim"));
	}
	
	
	
}
