package project16;

import java.sql.*;


public class DataBaseConnector {
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://mysql.stud.ntnu.no:3306/haakosh_prosjektoppgdb";

	static final String USER = "haakosh_master";
	static final String PASS = "project16";



	public static void main(String[] args) {
		Connection conn = null;
		Statement stmt = null;

		try{
			Class.forName("com.mysql.jdbc.Driver");

			System.out.println("Connecting to database...");

			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			System.out.println("Connected database successfully...");

			System.out.println("Inserting records into the table...");

			stmt = conn.createStatement();

			 String sql = "INSERT INTO GAME " +
	                   "VALUES (12345)";
			 stmt.executeUpdate(sql);

			 System.out.println("Inserted records into the table...");

		}catch(SQLException se){
		      //Handle errors for JDBC
		      se.printStackTrace();
		   }catch(Exception e){
		      //Handle errors for Class.forName
		      e.printStackTrace();
		   }finally{
		      //finally block used to close resources
		      try{
		         if(stmt!=null)
		            conn.close();
		      }catch(SQLException se){
		      }// do nothing
		      try{
		         if(conn!=null)
		            conn.close();
		      }catch(SQLException se){
		         se.printStackTrace();
		      }
		      System.out.println("Goodbye!");

		}
	}

}
