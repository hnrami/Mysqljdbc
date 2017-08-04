package com.demo.jdbc.mysql.jdbc;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import au.com.bytecode.opencsv.CSVWriter;

public class EntryPoint {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		String dbURL = "jdbc:mysql://localhost:3306/amc";
		String username = "root";
		String password = "admin";
		
		
		try (Connection conn = DriverManager.getConnection(dbURL, username, password)) {
			//Instead of card table we have to use parameter that query retuen 
			
			  String toEmail ="./src/main/java/toEmail.csv";
			  String messageEmail ="./src/main/java/messageEmail.csv";
			  CSVWriter toEmailwriter = new CSVWriter(new FileWriter(toEmail));
			  CSVWriter messageEmailwriter = new CSVWriter(new FileWriter(messageEmail));
			  
			  
			String selectTableSQL = "SELECT * from card";
			
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(selectTableSQL);
			while (rs.next()) {
				
				//parameter will change it i use random 
			    String funding_amt =rs.getString("FUNDING_DEBIT_AMOUNT");
			    String delta_amt = rs.getString("DELTA_DEBIT_COUNT");
			   if(funding_amt==null)
			    	funding_amt="0";
			  
			   if(delta_amt==null)
				   delta_amt="0";
			  
			   	System.out.println("funding_amt"+funding_amt);
			   	System.out.println("delta_amt"+delta_amt);
			    int differnt_amt=Integer.parseInt(funding_amt)-Integer.parseInt(delta_amt);
			    System.out.println("differnt_amt"+differnt_amt);
			    //this amount will change base on requirement 
			    if(differnt_amt >= 1 && differnt_amt < 5000){
			    	System.out.println("size");
			    	String value=funding_amt+","+delta_amt;
			    	String [] country = value.split(",");
			    	toEmailwriter.writeNext(country);
			    	
			    }
			    else if(differnt_amt > 5000){
			    	System.out.println("size2");
			    	String value=funding_amt+","+delta_amt;
			    	String [] country = value.split(",");
			    	messageEmailwriter.writeNext(country);
			    }
			    else{
//			    	System.out.println("it is good nothing will send");
			    }
			  
			}
			  toEmailwriter.close();
			    messageEmailwriter.close();
			    
			    
			    File toEmailFile =new File(toEmail);
			    File messageEmailFile =new File(messageEmail);
			    System.out.println(toEmailFile.length());
			    System.out.println(messageEmailFile.length());
			    
			    if(toEmailFile.length()>0){
			    	EmailSend.sendEmail("ramihemang@gmail.com", "body", "subject",toEmailFile,"result.csv");
			    }
			    if(toEmailFile.length()>0){
			    	EmailSend.sendEmail("ramihemang@gmail.com", "body", "subject",messageEmailFile,"result.csv");
			    }
			    toEmailFile.delete();
			    messageEmailFile.delete();
			    
			
		} catch (SQLException ex) {
			ex.printStackTrace();
		}		
		 
	}

		
		

	}


