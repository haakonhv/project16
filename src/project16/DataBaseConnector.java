package project16;

import java.io.IOException;
import java.sql.*;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;


public class DataBaseConnector {
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://mysql.stud.ntnu.no:3306/haakosh_prosjektoppgdb";

	static final String USER = "haakosh_master";
	static final String PASS = "project16";
	static Connection conn = null;



	public static void insert(String table, String values) throws SQLException {

		Statement stmt = null;




			System.out.println("Inserting records into the table...");

			stmt = conn.createStatement();

			String sql = null;

			if(table.equals("GAME")){
				sql = "INSERT INTO GAME " +
						"VALUES ("+values+")";
			}
			else if(table.equals("EVENT")){
				sql = "INSERT INTO EVENT (Event_id,Value,Xstart,Ystart,Game_id) " +
						"VALUES ("+values+")";
			}
			else if(table.equals("QUALIFIER")){
				sql = "INSERT INTO QUALIFIER " +
						"VALUES ("+values+")";
			}
			else if(table.equals("VALUE_S")){
				sql = "INSERT INTO VALUE (Q_id,Value_string) " +
						"VALUES ("+values+")";
			}
			else if(table.equals("VALUE_F")){
				sql = "INSERT INTO VALUE (Q_id,Value_float) " +
						"VALUES ("+values+")";
			}
			else if(table.equals("VALUE_N")){
				sql = "INSERT INTO VALUE (Q_id) " +
						"VALUES ("+values+")";
			}
			else if(table.equals("Player")){
				sql = "INSERT INTO PLAYER "+ values;
			}
			else if(table.equals("TEAM")){
				sql = "INSERT INTO TEAM " +
						"VALUES ("+values+")";
			}



			stmt.executeUpdate(sql);

			System.out.println("Inserted records into the table...");



	}

	public static void openConnection() throws ClassNotFoundException, SQLException {

		Class.forName("com.mysql.jdbc.Driver");

		System.out.println("Connecting to database...");

		conn = DriverManager.getConnection(DB_URL, USER, PASS);
		System.out.println("Connected database successfully...");




	}

	public static void closeConnection() throws SQLException{
		conn.close();
		System.out.println("Connection closed...");
	}


}
