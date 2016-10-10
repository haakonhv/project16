package project16;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;

import org.xml.sax.SAXException;

import java.sql.*;

import java.lang.Math;


public class Main{

	public static String xmlGameFile = "f24-90-2014-739634-eventdetails.xml";;


	public static void sendGame() throws ParserConfigurationException, SAXException, IOException, SQLException{
		Document doc = MyDomParser.getDocument(xmlGameFile);
		Game game = MyDomParser.getGame(doc);
		ArrayList<Event> eventList = MyDomParser.getEventList(doc, game);
		MyDomParser parser = new MyDomParser(eventList, doc, game);
		String gameID =  Integer.toString(parser.game.id);
		String homeID = Integer.toString(parser.game.getHome_team_id());
		String awayID = Integer.toString(parser.game.getAway_team_id());
		String matchday = Integer.toString(parser.game.getMatchday());
		String season = Integer.toString(parser.game.getSeason_id());
		DataBaseConnector.insert("GAME", gameID+","+homeID+","+awayID+","+matchday+","+season);




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

	public static void sendValues() throws ParserConfigurationException, SAXException, IOException, SQLException{
		Document doc = MyDomParser.getDocument(xmlGameFile);
		Game game = MyDomParser.getGame(doc);
		ArrayList<Event> eventList = MyDomParser.getEventList(doc, game);
		MyDomParser parser = new MyDomParser(eventList, doc, game);
		for(int i=0; i<eventList.size();i++){
			Event e = eventList.get(i);
			for (int j=0; j<e.getQualifierList().size();j++){
				Qualifier q = e.qualifierList.get(j);
				if(q.values!=null){
					for(int k=0; k<q.values.size(); k++){
						String thisValue = q.values.get(k);
						String qID = Integer.toString(q.id);
						try{
							Float floatValue = Float.parseFloat(thisValue);
							DataBaseConnector.insert("VALUE_F", qID+","+thisValue);
						}
						catch(NumberFormatException nfe_ex){
							DataBaseConnector.insert("VALUE_S", qID+","+"'"+thisValue+"'");

						}


					}

				}
				else{
					String qID = Integer.toString(q.id);
					DataBaseConnector.insert("VALUE_N", qID);

				}

			}
		}

	}

	public static void buildDatabase() throws ParserConfigurationException, SAXException, IOException, SQLException{
		Document doc = MyDomParser.getDocument(xmlGameFile);
		Game game = MyDomParser.getGame(doc);
		ArrayList<Event> eventList = MyDomParser.getEventList(doc, game);
		MyDomParser parser = new MyDomParser(eventList, doc, game);
		String gameID =  Integer.toString(parser.game.id);
		String homeID = Integer.toString(parser.game.getHome_team_id());
		String awayID = Integer.toString(parser.game.getAway_team_id());
		String matchday = Integer.toString(parser.game.getMatchday());
		String season = Integer.toString(parser.game.getSeason_id());
		//DataBaseConnector.insert("GAME", gameID+","+homeID+","+awayID+","+matchday+","+season);
		int cornerhelp = -1;
		int cornercount=0;
		for(int i=0; i<eventList.size();i++){
			Event e = eventList.get(i);
			String eventID =  Integer.toString(parser.eventList.get(i).id);
			String typeID =  Integer.toString(parser.eventList.get(i).value);
			String teamID =  Integer.toString(parser.eventList.get(i).teamid);
			String playerID =  Integer.toString(parser.eventList.get(i).playerid);
			String xStart =  Float.toString(parser.eventList.get(i).xstart);
			String yStart =  Float.toString(parser.eventList.get(i).ystart);
			String number = Float.toString(parser.eventList.get(i).number);
			String values = eventID+","+typeID+","+teamID+","+playerID+","+xStart+","+yStart+","+gameID+","+number;
			//DataBaseConnector.insert("EVENT", values);
			ArrayList<Qualifier> qualifierList = e.getQualifierList();
			if (e.getValue()==6){
				if (cornerhelp==-1 || e.getNumber()!=cornerhelp+1){
					cornerhelp=e.getNumber();
					cornercount +=1;
					CreateCorner(qualifierList, eventList, e, i);
				}
				
				
			}
			
			for (int j=0; j<qualifierList.size();j++){
				Qualifier thisQual = parser.eventList.get(i).qualifierList.get(j);
				String qID = Integer.toString(thisQual.id);
				String qualifierID = Integer.toString(thisQual.qualifier_id);
				values = qID+","+qualifierID+","+eventID;
				//DataBaseConnector.insert("QUALIFIER", values);


				
				
				if(thisQual.values!=null){
					for(int k=0; k<thisQual.values.size(); k++){
						String thisValue = thisQual.values.get(k);
						try{
							Float floatValue = Float.parseFloat(thisValue);
					//		DataBaseConnector.insert("VALUE_F", qID+","+thisValue);
						}
						catch(NumberFormatException nfe_ex){
						//	DataBaseConnector.insert("VALUE_S", qID+","+"'"+thisValue+"'");
						}
					}

				}
				else{
					qID = Integer.toString(thisQual.id);
					//DataBaseConnector.insert("VALUE_N", qID);

				}

			}
		}
		System.out.println(cornercount);
	}
	
	public static void CreateCorner(ArrayList<Qualifier> qualifierList, ArrayList<Event> eventList, Event event, int i){
		Corner corner = new Corner();

		for (Qualifier qual:qualifierList){
			if(qual.getQualifier_id()==219){
				corner.setFar_post(1);
				corner.setFirst_post(1);
				break;
			}
			else if(qual.getQualifier_id()==220){
				corner.setFirst_post(1);
				corner.setFar_post(0);
				break;
			}
			else if(qual.getQualifier_id()==221){
				corner.setFirst_post(0);
				corner.setFar_post(1);
				break;
			}
			else if(qual.getQualifier_id()==222){
				corner.setFirst_post(0);
				corner.setFar_post(0);
				break;
			}
		}
		ArrayList<Qualifier> nexteventlist = eventList.get(i+1).getQualifierList();
		for (Qualifier qual:nexteventlist){
			if(qual.getQualifier_id()==219){
				corner.setFar_post(1);
				corner.setFirst_post(1);
				break;
			}
			else if(qual.getQualifier_id()==220){
				corner.setFirst_post(1);
				corner.setFar_post(0);
				break;
			}
			else if(qual.getQualifier_id()==221){
				corner.setFirst_post(0);
				corner.setFar_post(1);
				break;
			}
			else if(qual.getQualifier_id()==222){
				corner.setFirst_post(0);
				corner.setFar_post(0);
				break;
			}
		}
		boolean taken = false;
		while(!taken){
			if(eventList.get(i+1).getValue()==1){
				taken=true;
				ArrayList<Qualifier> takenlist=eventList.get(i+1).getQualifierList();
				boolean xdone=false;
				boolean ydone=false;
				boolean laterality=false;
				corner.setEvent_id(event.getId());
				System.out.println(event.getYstart());
				
				for (Qualifier qual:takenlist){
					if (qual.getQualifier_id()==140){
						corner.setKoord_x(Float.parseFloat(qual.getValues().get(0)));
						xdone=true;
					}
					else if (qual.getQualifier_id()==141){
						corner.setKoord_y(Float.parseFloat(qual.getValues().get(0)));
						ydone=true;
					}
					else if (qual.getQualifier_id()==223){
						corner.setInswing(1);
						laterality=true;
					}
					else if (qual.getQualifier_id()==224){
						corner.setOutswing(1);
						laterality=true;
					}
					else if (qual.getQualifier_id()==225){
						corner.setStraight(1);
						laterality=true;
					}
					if(xdone==true && ydone==true && laterality==true){
						break;
					}
				}
			}
			else{
				i+=1;
			}
		}
		float ystart=eventList.get(i+1).getYstart();
		if(ystart>99){
			corner.setLeft(1);
			corner.setLength(ystart-corner.getKoord_y());
		}
		else if(ystart<1){
			corner.setRight(1);
			corner.setLength(ystart+corner.getKoord_y());
		}
		System.out.println(corner.toString());
	}

	public static void sendTeams() throws ParserConfigurationException, SAXException, IOException, SQLException{
		Document doc = MyDomParser.getDocument("srml-90-2016-squads.xml");
		NodeList teams = doc.getElementsByTagName("Team");

		for(int i=0; i<teams.getLength();i++){
			Node node = teams.item(i);
			Element t = (Element) node;
			if (!t.hasAttribute("country")){
				node.getParentNode().removeChild(node);
			}
			else{
				NodeList cl = node.getChildNodes();
				Node c = cl.item(1);
				String teamName = c.getTextContent();
				try{
					int num = Integer.parseInt(teamName);
					c = cl.item(3);
				}
				catch (NumberFormatException nfe){
				}
				Team team = new Team();
				String uid = t.getAttribute("uID");
				uid = uid.replace("t", "");
				team.setId(Integer.parseInt(uid));
				team.setName(c.getTextContent());
				System.out.println(team.getName());
				String values = team.getId()+","+"'"+team.getName()+"'";
				DataBaseConnector.insert("TEAM", values);
			}
		}


	}

	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException, SQLException, ClassNotFoundException{
		long startTime = System.nanoTime();

		DataBaseConnector.openConnection();
		sendTeams();
		DataBaseConnector.closeConnection();

		long endTime = System.nanoTime();
		System.out.println("Took "+(endTime - startTime)/Math.pow(10,9) + " seconds");



	}


}
