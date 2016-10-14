package project16;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

	public static String xmlGameFile = "f24-90-2014-739631-eventdetails.xml";;


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
		DataBaseConnector.insert("GAME", gameID+","+homeID+","+awayID+","+matchday+","+season);
		int cornerhelp = -1;
		ArrayList<String> homePlayersID = new ArrayList<String>();
		ArrayList<Integer> homePlayersHeight = new ArrayList<Integer>();
		ArrayList<String> awayPlayersID = new ArrayList<String>();
		ArrayList<Integer> awayPlayersHeight = new ArrayList<Integer>();
		int homegk=0;
		int awaygk=0;
		String homeHeightStatement = "SELECT Height FROM PLAYER WHERE Player_id IN (";
		String awayHeightStatement = "SELECT Height FROM PLAYER WHERE Player_id IN (";
		int homeTall=0;
		int awayTall=0;
		
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
			DataBaseConnector.insert("EVENT", values);
			ArrayList<Qualifier> qualifierList = e.getQualifierList();
			if (e.getValue()==6){
				if (cornerhelp==-1 || e.getNumber()!=cornerhelp+1){
					cornerhelp=e.getNumber();
						CreateCorner(qualifierList, eventList, e, i, homeTall, awayTall, homegk, awaygk, game.getHome_team_id(),game.getAway_team_id());
	
				}
			}
			
			for (int j=0; j<qualifierList.size();j++){
				Qualifier thisQual = parser.eventList.get(i).qualifierList.get(j);
				String qID = Integer.toString(thisQual.id);
				String qualifierID = Integer.toString(thisQual.qualifier_id);
				values = qID+","+qualifierID+","+eventID;
				DataBaseConnector.insert("QUALIFIER", values);
				if (i== 0& qualifierID.equals("30")){ //registrerer spillere i troppen
					List<String> players = thisQual.getValues();
					ResultSet gk =DataBaseConnector.SelectPlayer("SELECT Height FROM PLAYER WHERE Player_id="+players.get(0));
					gk.next();
					for (int y=1;y<11; y++){
						homePlayersID.add(players.get(y));
						homeHeightStatement+=players.get(y)+",";	
					}
					homeHeightStatement = homeHeightStatement.substring(0, homeHeightStatement.length()-1) +")";
					ResultSet rs=DataBaseConnector.SelectPlayer(homeHeightStatement);
					while(rs.next()){
						int height=rs.getInt("Height");
						homePlayersHeight.add(height);
					}
					homeTall=countTallPlayers(homePlayersHeight);
				}	
				if (i==1 & qualifierID.equals("30")){
					List<String> players = thisQual.getValues();
					ResultSet gk =DataBaseConnector.SelectPlayer("SELECT Height FROM PLAYER WHERE Player_id="+players.get(0));
					gk.next();
					awaygk=gk.getInt("Height");
					
					for (int y=1;y<11;y++){
						awayPlayersID.add(players.get(y));
						awayHeightStatement+=players.get(y)+",";
						
					}
					awayHeightStatement = awayHeightStatement.substring(0, awayHeightStatement.length()-1)+")";
					ResultSet rs=DataBaseConnector.SelectPlayer(awayHeightStatement);
					while(rs.next()){
						int height=rs.getInt("Height");
						awayPlayersHeight.add(height);
					}
					awayTall=countTallPlayers(awayPlayersHeight);
				}

				if(thisQual.values!=null){
					for(int k=0; k<thisQual.values.size(); k++){
						String thisValue = thisQual.values.get(k);
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
					qID = Integer.toString(thisQual.id);
					DataBaseConnector.insert("VALUE_N", qID);

				}

			}
		}
	}
	
	public static void CreateCorner(ArrayList<Qualifier> qualifierList, ArrayList<Event> eventList, Event event, int i, int homeTall, int awayTall, int homegk, int awaygk, int homeID, int awayID){
		Corner corner = new Corner();
		String column = "";
		String values ="";
		int cornerTeamID=0;

		for (Qualifier qual:qualifierList){
			if(qual.getQualifier_id()==219){
				corner.setFar_post(1);
				corner.setFirst_post(1);
				column+="Near_post,Far_post";
				values+="1, 1";
				break;
			}
			else if(qual.getQualifier_id()==220){
				corner.setFirst_post(1);
				corner.setFar_post(0);
				column+="Near_post,Far_post";
				values+="1,0";
				break;
			}
			else if(qual.getQualifier_id()==221){
				corner.setFirst_post(0);
				corner.setFar_post(1);
				column+="Near_post,Far_post";
				values="0,1";
				break;
			}
			else if(qual.getQualifier_id()==222){
				corner.setFirst_post(0);
				corner.setFar_post(0);
				column+="Near_post,Far_post";
				values="0,0";
				break;
			}
		}
		ArrayList<Qualifier> nexteventlist = eventList.get(i+1).getQualifierList();
		for (Qualifier qual:nexteventlist){
			if(qual.getQualifier_id()==219){
				corner.setFar_post(1);
				corner.setFirst_post(1);
				column+="Near_post,Far_post";
				values+="1, 1";
				break;
			}
			else if(qual.getQualifier_id()==220){
				corner.setFirst_post(1);
				corner.setFar_post(0);
				column+="Near_post,Far_post";
				values+="1,0";
				break;
			}
			else if(qual.getQualifier_id()==221){
				corner.setFirst_post(0);
				corner.setFar_post(1);
				column+="Near_post,Far_post";
				values="0,1";
				break;
			}
			else if(qual.getQualifier_id()==222){
				corner.setFirst_post(0);
				corner.setFar_post(0);
				column+="Near_post,Far_post";
				values="0,0";
				break;
			}
		}
		boolean taken = false;
		while(!taken){ //finner event_id til corneren. Events som f.eks. bytter kan komme mellom corner won og corner taken!
			if(eventList.get(i+1).getValue()==1){ //value==1 -> pasning --> corneren er tatt
				taken=true;
				column+=",Team_id";
				cornerTeamID=eventList.get(i+1).getTeamid();
				values+=","+cornerTeamID;
				if (cornerTeamID==homeID){
					column+=",Gk_height,attack_tall,defend_tall";
					values+=","+awaygk+","+homeTall+","+awayTall;
					
				}
				else{
					column+=",Gk_height,attack_tall,defend_tall";
					values+=","+homegk+","+awayTall+","+homeTall;
				}
				ArrayList<Qualifier> takenlist=eventList.get(i+1).getQualifierList();
				boolean xdone=false;
				boolean ydone=false;
				boolean laterality=false;
				corner.setEvent_id(event.getId());
				column+=",Event_id";
				values+=","+Integer.toString(event.getId());
				int directshot=CheckShot(eventList.get(i+2)); //setter binærvariabelen=1 hvis første hendelse er skudd
				int directgoal = CheckGoal(eventList.get(i+2)); //som over for mål
				int indirectshot=0;
				int indirectgoal=0;
				column+=",Directshot,Directgoal";
				values+=","+ Integer.toString(directshot)+","+Integer.toString(directgoal);
				if (directgoal==0){
					boolean done=false;
					int j=i+3;
					while (!done){
						done=checkIfFinished(eventList,j,cornerTeamID);
						if (!done){
							indirectshot=CheckShot(eventList.get(j));
							indirectgoal=CheckShot(eventList.get(j));
							if (indirectgoal==1){
								done=true;
							}
						}
						j++;
					}
				}
				column+=",Indirectshot,Indirectgoal";
				values+=","+Integer.toString(indirectshot)+","+Integer.toString(indirectshot);
				for (Qualifier qual:takenlist){
					if (qual.getQualifier_id()==140){ //xkoordinatet ballen lander
						corner.setKoord_x(Float.parseFloat(qual.getValues().get(0)));
						xdone=true;
						column+=",KoordX";
						values+=","+Float.parseFloat(qual.getValues().get(0));
					}
					else if (qual.getQualifier_id()==141){
						corner.setKoord_y(Float.parseFloat(qual.getValues().get(0)));
						ydone=true;
						column+=",KoordY";
						values+=","+Float.parseFloat(qual.getValues().get(0));
					}
					else if (qual.getQualifier_id()==223){
						corner.setInswing(1);
						laterality=true;
						column+=",Inswing,Outswing,Straight";
						values+=",1,0,0";
					}
					else if (qual.getQualifier_id()==224){
						corner.setOutswing(1);
						laterality=true;
						column+=",Inswing,Outswing,Straight";
						values+=",0,1,0";
					}
					else if (qual.getQualifier_id()==225){
						corner.setStraight(1);
						laterality=true;
						column+=",Inswing,Outswing,Straight";
						values+=",0,0,1";
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
		if(ystart>50){
			corner.setLeft(1);
			corner.setLength(ystart-corner.getKoord_y());
			column+=",Right_side,Left_side";
			values+=",0,1";
			
		}
		else if(ystart<50){
			corner.setRight(1);
			corner.setLength(ystart+corner.getKoord_y());
			column+=",Right_side,Left_side";
			values+=",1,0";
		}
		String sqlString="("+column+")"+" VALUES " +"("+values+")";
		try {
			DataBaseConnector.insert("Corner", sqlString);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static boolean checkIfFinished(ArrayList<Event> eventList, int j, int cornerTeamID) {
		Event event = eventList.get(j);
		int type = event.getValue();
		if(type==5||type==6||type==4||type==30){//5 indikerer innkast eller utspill fra mål, 6 er ny corner, 4 er forseelse, 30 er omgang ferdig
			return true;
		}
		if((event.getTeamid()==cornerTeamID&&event.getXstart()<80) ||(event.getTeamid()!=cornerTeamID&&event.getXstart()>20)){
			//sjekker om ballen er nærmere midten av banen enn x=80 for angripende lag
			return true;
		}
		return false;
	}

	private static int CheckGoal(Event event) {
			if(event.getValue()==16){
				return 1;
			}
		return 0;
	}

	public static int CheckShot (Event e){
		int type=e.getValue();
		if(type==13 ||type==16){
			return 1;
		}
		return 0;
	

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
				String values = team.getId()+","+"'"+team.getName()+"'";
				DataBaseConnector.insert("TEAM", values);
			}
		}
	}
	public static int countTallPlayers(ArrayList<Integer> List){
		int tall = 0;
		for (int i=0;i<List.size();i++){
			if (List.get(i)>=185){
				tall++;
			}	
		}
		return tall;
	}

	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException, SQLException, ClassNotFoundException{
		long startTime = System.nanoTime();

		DataBaseConnector.openConnection();
		buildDatabase();
		DataBaseConnector.closeConnection();

		long endTime = System.nanoTime();
		System.out.println("Took "+(endTime - startTime)/Math.pow(10,9) + " seconds");

	}


}
