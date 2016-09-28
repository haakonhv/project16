package project16;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import java.sql.*;

public class Main {

	public static String xmlGameFile;

	public static void sendGame() throws ParserConfigurationException, SAXException, IOException{
		xmlGameFile = "f24-90-2014-739631-eventdetails.xml";
//		MyDomParser.getDocument(xmlGameFile);
		Document doc = MyDomParser.getDocument(xmlGameFile);
		Game game = MyDomParser.getGame(doc);
		ArrayList<Event> eventList = MyDomParser.getEventList(doc, game);
		MyDomParser parser = new MyDomParser(eventList, doc, game);
		String gameID =  Integer.toString(parser.game.id);
		DataBaseConnector.insert("GAME", gameID);




	}
	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException{
		sendGame();
	}


}
