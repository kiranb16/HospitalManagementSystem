package com.manageement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Scanner;

import com.mysql.cj.protocol.Resultset;

public class Doctor {

		
		private Connection connection;
		//private Scanner scanner;
		
		public Doctor(Connection connection) {
			this.connection= connection;
		
		}

		
		
public void viewDoctor() {
	String query = "SELECT * FROM doctors";
		    try {
		        PreparedStatement preparedStatement = connection.prepareStatement(query);
		        ResultSet resultSet = preparedStatement.executeQuery();
		        
		        System.out.println("Doctors:");
		        System.out.println("+-----------+---------------+-----------------+");
		        System.out.println("| Doctor ID | Name          | specialization  |");
		        System.out.println("+-----------+---------------+-----------------+");
		        
		        // Iterate through the result set and print the details
		        while (resultSet.next()) {
		            int id = resultSet.getInt("id");
		            String name = resultSet.getString("name");
		            String specialization = resultSet.getString("specialization");
		            System.out.printf("| %-9d | %-13s | %-15s |%n", id, name, specialization);
		            System.out.println("+-----------+---------------+-----------------+");
		        }
		        
		        // Close the resources
		        resultSet.close();
		        preparedStatement.close();
		        
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		}

		
		public boolean getDoctortById(int id) {
			String query="select * from doctors where id=?";
			try {
				PreparedStatement preparedStatement= connection.prepareStatement(query);
				preparedStatement.setInt(1, id);
				Resultset resultset= (Resultset) preparedStatement.executeQuery();
				if(((ResultSet) resultset).next()){
					return true;
				}else {
					return false;
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return false;
		}
	}






