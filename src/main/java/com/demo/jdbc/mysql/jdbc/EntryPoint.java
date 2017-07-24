package com.demo.jdbc.mysql.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class EntryPoint {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String dbURL = "jdbc:mysql://localhost:3306/amc";
		String username = "root";
		String password = "admin";
		
		try (Connection conn = DriverManager.getConnection(dbURL, username, password)) {
			//Instead of card table we have to use parameter that query retuen 
			String selectTableSQL = "SELECT * from card";
			
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(selectTableSQL);
			while (rs.next()) {
				
				//parameter will change it i use random 
			    String funding_amt =rs.getString("FUNDING_DEBIT_AMOUNT");
			    String delta_amt = rs.getString("DELTA_DEBIT_AMOUNT");
			    
			    int differnt_amt=Integer.parseInt(funding_amt)-Integer.parseInt(delta_amt);
			    
			    //this amount will change base on requirement 
			    if(differnt_amt >1000 && differnt_amt < 5000){
			    	EmailSend.sendEmail("xxx@gmail.com", "body", "subject");
			    }
			    else if(differnt_amt > 5000){
			    	System.out.println("send message");
			    }
			    else{
			    	System.out.println("it is good nothing will send");
			    }
			 
			}
			
		} catch (SQLException ex) {
			ex.printStackTrace();
		}		
		 
	}

		
		

	}


