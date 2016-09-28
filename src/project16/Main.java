package project16;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import java.sql.*;

public class Main{

	public static String xmlGameFile = "f24-90-2014-739631-eventdetails.xml";;


	public static void sendGame() throws ParserConfigurationException, SAXException, IOException, SQLException{
		Document doc = MyDomParser.getDocument(xmlGameFile);
		Game game = MyDomParser.getGame(doc);
		ArrayList<Event> eventList = MyDomParser.getEventList(doc, game);
		MyDomParser parser = new MyDomParser(eventList, doc, game);
		String gameID =  Integer.toString(parser.game.id);
		DataBaseConnector.insert("GAME", gameID);




	}

	public static void sendEvents() throws ParserConfigurationException, SAXException, IOException, SQLException{
		Document doc = MyDomParser.getDocument(xmlGameFile);
		Game game = MyDomParser.getGame(doc);
		ArrayList<Event> eventList = MyDomParser.getEventList(doc, game);
		MyDomParser parser = new MyDomParser(eventList, doc, game);
		for(int i=0; i<eventList.size();i++){
			String eventID =  Integer.toString(parser.eventList.get(i).id);
			String typeID =  Integer.toString(parser.eventList.get(i).value);
			String teamID =  Integer.toString(parser.eventList.get(i).teamid);
			String playerID =  Integer.toString(parser.eventList.get(i).playerid);
			String xStart =  Float.toString(parser.eventList.get(i).xstart);
			String yStart =  Float.toString(parser.eventList.get(i).ystart);
			String gameID =  Float.toString(parser.game.id);
			String values = eventID+","+typeID+","+xStart+","+yStart+","+gameID;
			DataBaseConnector.insert("EVENT", values);

		}

	}

	public static void sendQualifiers() throws ParserConfigurationException, SAXException, IOException, SQLException{
		Document doc = MyDomParser.getDocument(xmlGameFile);
		Game game = MyDomParser.getGame(doc);
		ArrayList<Event> eventList = MyDomParser.getEventList(doc, game);
		MyDomParser parser = new MyDomParser(eventList, doc, game);
		for(int i=0; i<eventList.size();i++){
			for (int j=0; j<eventList.get(i).getQualifierList().size();j++){
				Qualifier thisQual = parser.eventList.get(i).getQualifierList().get(j);
				String qID = Integer.toString(thisQual.id);
				String qualifierID = Integer.toString(thisQual.qualifier_id);
				String eventID = Integer.toString(parser.eventList.get(i).id);
				String values = qID+","+qualifierID+","+eventID;
				DataBaseConnector.insert("QUALIFIER", values);

			}
		}

	}
	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException, SQLException, ClassNotFoundException{
		DataBaseConnector.openConnection();
		sendEvents();
		sendQualifiers();
		DataBaseConnector.closeConnection();
	}


}
