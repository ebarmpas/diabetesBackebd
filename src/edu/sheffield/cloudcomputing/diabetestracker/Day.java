package edu.sheffield.cloudcomputing.diabetestracker;

public class Day {
	
	private String date;
	
	private double bloodGlucose;
	private double carbIntake;
	private double medicationDose;
	
	
	public Day(String date, double bloodGlucose, double carbIntake, double medicationDose) {
		this.date = date;
		this.bloodGlucose = bloodGlucose;
		this.carbIntake = carbIntake;
		this.medicationDose = medicationDose;
	}
	public String getDate() {
		return date;
	}
	
	public double getBloodGlucose() {
		return bloodGlucose;
	}
	public double getCarbIntake() {
		return carbIntake;
	}
	public double getMedicationDose() {
		return medicationDose;
	}
	@Override
	public String toString() {
		return "Day [" + "date=" + date + ", bloodGlucose=" + bloodGlucose + ", carbIntake=" + carbIntake
				+ ", medicationDose=" + medicationDose + "]";
	}


	

}