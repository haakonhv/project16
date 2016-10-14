package project16;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.sql.*;

import java.net.*;
import java.io.*;

import java.math.*;



public class HeightScript {

	static Connection conn;
	static ArrayList<Player> playerlist;


	public static ArrayList<Player> getPlayersNoHeight () throws ClassNotFoundException, SQLException{
		conn = DataBaseConnector.returnConnection();

		Statement stmt = conn.createStatement();

		String sql = "SELECT First_name, Last_name, Player_id FROM PLAYER WHERE Height IS NULL AND NOT Player_id = 0";
		playerlist = new ArrayList<Player>();
		ResultSet rs= stmt.executeQuery(sql);
		while(rs.next()){
			Player player = new Player();
			String name = rs.getString(1)+" "+rs.getString(2);
			player.setFirst_name(rs.getString(1));
			player.setLast_name(rs.getString(2));
			player.setId(Integer.parseInt(rs.getString(3)));
			playerlist.add(player);
		}


		DataBaseConnector.closeConnection();
		return playerlist;

	}

	public static Float getHeight(String sURL) throws IOException{
		URL url = new URL(sURL);
		HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
		urlConn.setRequestProperty("User-Agent", "Mozilla/5.0 ( compatible ) ");
		urlConn.setRequestProperty("Accept","*/*");
		float height = 0;

		InputStream is;
		if (urlConn.getResponseCode() >= 400){
			is = urlConn.getErrorStream();
		}
		else{
			is = urlConn.getInputStream();
		}

		BufferedReader in = new BufferedReader(new InputStreamReader(is));
		String inputline;
		boolean d = false;
		while ((inputline = in.readLine()) != null){
			if(inputline.contains("<th>Height:</th>")){
				d = true;
			}
			else if(d){
				d = false;
				inputline = inputline.replace("<td>","");
				inputline = inputline.replace(" m</td>","");
				inputline = inputline.replace(",",".");

				height = Float.parseFloat(inputline);

			}

		}
		return height;
	}

	public static String getURL (String name) throws IOException{
		String sName = name.replace(" ", "+");
		sName = sName.replace("å", "a");
		sName = sName.replace("ø", "o");
		sName = sName.replace("æ", "ae");
		sName = sName.replace("è", "e");
		sName = sName.replace("ö", "o");
		URL url = new URL("http://www.transfermarkt.com/schnellsuche/ergebnis/schnellsuche?query="+sName+"&x=0&y=0");
		HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
		urlConn.setRequestProperty("User-Agent", "Mozilla/5.0 ( compatible ) ");
		urlConn.setRequestProperty("Accept","*/*");


		InputStream is;
		if (urlConn.getResponseCode() >= 400){
			is = urlConn.getErrorStream();
		}
		else{
			is = urlConn.getInputStream();
		}

		BufferedReader in = new BufferedReader(new InputStreamReader(is));
		String inputline;
		String URL = "http://www.transfermarkt.com/";
		while ((inputline = in.readLine()) != null){
			if(inputline.contains("class=\"spielprofil_tooltip\"")){
				List<String> items = Arrays.asList(inputline.split("\\s* \\s*"));
				for (int i=0;i<items.size();i++){
					String s;
					if (items.get(i).contains("profil/spieler")){
						s = items.get(i);
						s = s.replace("href=\"", "");
						s = s.split("\">")[0];
						URL = URL+s;
					}
				}
			}



		}

		return URL;
	}


	public static ArrayList<Player> setHeight(ArrayList<Player> players) throws IOException{
		for(Player p : players){
			String playerName = p.getFirst_name()+" "+p.getLast_name();
			Float heightFloat = getHeight(getURL(playerName))*100;
			int height = Math.round(heightFloat);
			p.setHeight(height);
			System.out.println(p.getFirst_name()+p.getLast_name()+p.getHeight());
		}
		return players;

	}

	public static void sendHeight(ArrayList<Player> players) throws ClassNotFoundException, SQLException{
		conn = DataBaseConnector.returnConnection();

		Statement stmt = conn.createStatement();

		for (Player p : players){
			if(p.height>0){
				String sql = "UPDATE PLAYER SET Height = "+p.getHeight()+" WHERE Player_id = "+p.getId();
				stmt.executeUpdate(sql);
			}
		}

		DataBaseConnector.closeConnection();

	}

	public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException {
		long startTime = System.nanoTime();
		ArrayList<Player> playersNoHeight = getPlayersNoHeight();
		ArrayList<Player> playersWithHeight = setHeight(playersNoHeight);
		sendHeight(playersWithHeight);
		long endTime = System.nanoTime();
		System.out.println("Took "+(endTime - startTime)/Math.pow(10,9) + " seconds");

	}



}
