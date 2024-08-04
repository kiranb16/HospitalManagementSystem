package com.manageement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class HospitalManagementSystem {

	private static final String url="jdbc:mysql://localhost:3306/usdb";
	private static final String username="root";
	private static final String password="root";
	
	public static void main(String[] args) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			
		}
		   Scanner scanner= new Scanner(System.in);
		   
		try {
			 Connection connection= DriverManager.getConnection(url,username,password);
			 Patient patient= new Patient(connection, scanner);
			 Doctor doctor= new Doctor(connection);
		
			 while(true) {
				 System.out.println("HOSPITAL MANAGEMENT SYSTEM");
				 System.out.println("1.  add patient");
				 System.out.println("2.  view patient");
				 System.out.println("3.  view doctors");
				 System.out.println("4.  book appointment"); 
				 System.out.println("5.   exist");
				 System.out.println("enter your choice");
				 
				 int choice=scanner.nextInt();
				 switch (choice) {
                 case 1: // Add patient
                     patient.addPatient();
                     System.out.println();
                     break;
                 
                 case 2: // View patient
                     patient.viewPatient();
                     System.out.println();
                     break;
                 
                 case 3: // View doctors
                     doctor.viewDoctor();
                     System.out.println();
                     break;
                 
                 case 4: // Book appointment
                     bookAppointment(patient, doctor, connection, scanner);
                     System.out.println();
                     break;
                 
                 case 5: // Exit
                     System.out.println("Exiting the system. Goodbye!");
                     return; // Exit the loop and end the program
                 
                 default: 
                     System.out.println("Enter a valid choice!");
                     System.out.println();
                     break;
             }
         }
     } catch (SQLException e) {
         e.printStackTrace();
     }
         scanner.close();
     }
				 
	
	
	public static void bookAppointment(Patient patient, Doctor doctor, Connection connection, Scanner scanner) {
		System.out.println("Enter patient id");
		 int patientId=scanner.nextInt();
		 
		 System.out.println("Enter doctor id");
		 int doctorId=scanner.nextInt();
		 
		 System.out.println(" enter appointment date (yyyy-mm-dd)");
		 
		 String AppDate= scanner.next();
		 
		 if(patient.getPatientById(patientId) && doctor.getDoctortById(doctorId)) {
			 if(checkDoctorAvailability(doctorId, AppDate,connection)) {
				 String appointmentQuery=" insert into appointments(patient_id, doctor_id, appointment_date) values(?,?,?)";
				 
				 try {
					PreparedStatement preparedStatement= connection.prepareStatement(appointmentQuery);
					
					preparedStatement.setInt(1,patientId);
					preparedStatement.setInt(2, doctorId);
					preparedStatement.setString(3, AppDate);
					
					int affectedRow=preparedStatement.executeUpdate();
					if(affectedRow==0) {
						System.out.println("appointment booked");
					}else {
						System.out.println("failed to book appointment");
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			 }else {
				 System.out.println("doctor not available on  that date");
			 } 
			 
		 }else {
			 System.out.println("Either patient or doctor not exist !!!");
		 }
		
	}

	private static boolean checkDoctorAvailability(int doctorId, String appDate, Connection connection) {
		String query="SELECT * FROM appointments WHERE doctor_id = ? AND appointment_date = ?";

		try {
			PreparedStatement preparedStatement= connection.prepareStatement(query);
			preparedStatement.setInt(1, doctorId);
			preparedStatement.setString(2, appDate);
			 ResultSet resultSet = preparedStatement.executeQuery();
			 if(resultSet.next()) {
				 int count=resultSet.getInt(1);
				 
				 if(count==0) {
					 return true;
				 }else {
					 return false;
				 }
			 }
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	
	
}









