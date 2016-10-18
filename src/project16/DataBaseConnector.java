package project16;

import java.io.IOException;
import java.sql.*;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

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




			//System.out.println("Inserting records into the table...");

			stmt = conn.createStatement();

			String sql = null;

			if(table.equals("GAME")){
				sql = "INSERT INTO GAME " +
						"VALUES ("+values+")";
			}
			else if(table.equals("EVENT")){
				sql = "INSERT INTO EVENT " +
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
			else if(table.equals("Corner")){
				sql = "INSERT INTO CORNER "+ values;
			}



			try{
				stmt.executeUpdate(sql);
				}
				catch (Exception e){
					if(table.equals("QUALIFIER")){
						List<String> valuelist = Arrays.asList(values.split("\\s*,\\s*"));
						int oldQ_id = Integer.parseInt(valuelist.get(0));
						Random r = new Random();
						int randomAdd = r.nextInt(1000-1)+1;
						String newQ_id = Integer.toString(oldQ_id+ randomAdd);
						String newvalues = newQ_id+","+valuelist.get(1)+","+valuelist.get(2);
						insert("QUALIFIER",newvalues);
					}
					else{
						throw e;
					}
				}

			//System.out.println("Inserted records into the table...");



	}

	public static ResultSet SelectPlayer(String Statement) throws SQLException{
		Statement stmt=conn.createStatement();
		ResultSet rs=stmt.executeQuery(Statement);
		return rs;
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

	public static Connection returnConnection() throws ClassNotFoundException, SQLException {

		Class.forName("com.mysql.jdbc.Driver");

		System.out.println("Connecting to database...");

		conn = DriverManager.getConnection(DB_URL, USER, PASS);
		System.out.println("Connected database successfully...");
		return conn;




	}


}
