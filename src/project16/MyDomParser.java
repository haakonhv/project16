package project16;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class MyDomParser {

	public ArrayList<Event> eventList;
	public Document doc;
	public Game game;


	//Parser-Constructor
    public MyDomParser(ArrayList<Event> eventList, Document doc, Game game) {
		super();
		this.eventList = eventList;
		this.doc = doc;
		this.game = game;
	}



    //Bygger factory og document fra xml-fil.
	public static Document getDocument(String fileName) throws ParserConfigurationException, SAXException, IOException{
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(fileName);
        return doc;
    }

    //Henter game-node fra xml-dokument oppretter nytt Game-object, setter Game-id og returnerer gamet
	public static  Game getGame(Document doc){
    	Game game = new Game();
        Node gameNode = doc.getElementsByTagName("Game").item(0); //Game-noden er det første og eneste elementet med TagName "Game"
        Element gameElement = (Element) gameNode;  //Node castes til Element for å kunne bruke getAttribute()
        game.setId(Integer.parseInt(gameElement.getAttribute("id")));
        game.setHome_team_id(Integer.parseInt(gameElement.getAttribute("home_team_id")));
        game.setAway_team_id(Integer.parseInt(gameElement.getAttribute("away_team_id")));
        game.setMatchday(Integer.parseInt(gameElement.getAttribute("matchday")));
        game.setSeason_id(Integer.parseInt(gameElement.getAttribute("season_id")));
        return game;
    }

    //Lager en ArrayList med alle Events i et game/document. Attributter i alle Eventene settes. Eventene legges i lista som returneres
	public static  ArrayList<Event> getEventList (Document doc, Game game){
    	 NodeList xmlEventList = getXmlEventList(doc);
         ArrayList<Event> eventList = new ArrayList<Event>();
         int originalnumber=-1;
         for (int i=0; i<xmlEventList.getLength();i++){
         	Element xmlEvent = (Element) xmlEventList.item(i); //Node castes til Element for å kunne bruke getAttribute()
         	Event event = new Event();
         	event.setId(Integer.parseInt(xmlEvent.getAttribute("id")));
         	event.setGameid(game.getId());
         	event.setNumber(i+1);
         	if(xmlEvent.hasAttribute("player_id")){
         		event.setPlayerid(Integer.parseInt(xmlEvent.getAttribute("player_id")));
         	}
         	else{
         		event.setPlayerid(0);
         	}
         	if(xmlEvent.hasAttribute("team_id")){
         		event.setTeamid(Integer.parseInt(xmlEvent.getAttribute("team_id")));
         	}
         	event.setValue(Integer.parseInt(xmlEvent.getAttribute("type_id")));
         	event.setXstart(Float.parseFloat(xmlEvent.getAttribute("x")));
         	event.setYstart(Float.parseFloat(xmlEvent.getAttribute("y")));
         	event.setQualifierList(getQualifierList(xmlEvent.getAttribute("id"),xmlEventList));

         	eventList.add(event);
         }
         return eventList;
    }

	//Henter Qualifiers fra et bestemt Event. Oppretter Qualifier-objekter, setter feltene, legger de til objektene i en ArrayList og returnerer denne.
	public static ArrayList<Qualifier> getQualifierList(String eventId, NodeList eventList){
    	ArrayList<Qualifier> qualifierList = new ArrayList<Qualifier>();
    	NodeList qList = getQByEventId(eventId, eventList); //Henter NodeList med Qualifiers fra XML-filen
    	for(int i=0; i<qList.getLength();i++){
    		Element q = (Element) qList.item(i); //Caster til Element
    		Qualifier qualifier = new Qualifier();
    		qualifier.setId(Integer.parseInt(q.getAttribute("id")));
    		qualifier.setQualifier_id(Integer.parseInt(q.getAttribute("qualifier_id")));
    		List<String> values = new ArrayList<String>(); //Dette er en liste med alle verdiene (values) til en bestemt Qualifier i et bestemt element
    		String thisValue = q.getAttribute("value"); //Nåværende iterasjons value-streng. Strengen kan bestå av flere verdier
    		if (q.hasAttribute("value")){ //Hvis q har values, legges disse til i en liste hvor hver value er et element i lista
    			values = Arrays.asList(thisValue.split("\\s*,\\s*")); //Her legges alle verdiene inn i value-lista. Splitter på komma for å legge inn alle verdiene i strengen
    			qualifier.setValues(values);
    		}
    		qualifierList.add(qualifier);

    	}

    	return qualifierList;
    }

	//Returner en nodeliste med alle eventene i et document
    public static NodeList getXmlEventList(Document doc){
        NodeList xmlEventList = doc.getElementsByTagName("Event");
        return xmlEventList;

    }




    //Returnerer en Nodelist med alle Q-er til et bestemt event. Fjerner alle noder som ikke er element-noder.
    public static NodeList getQByEventId(String eventId, NodeList eventList){
        NodeList qList = null;
        for(int i=0;i<eventList.getLength();i++){
            Node e = eventList.item(i);
            Element event = (Element) e;
            String thisID = event.getAttribute("id");
            if(thisID.equals(eventId)){
                qList = event.getChildNodes();
            }
        }
        for (int i=0 ; i<qList.getLength();i++){
        	Node q = qList.item(i);
        	if(q.getNodeType()!=Node.ELEMENT_NODE){
        		q.getParentNode().removeChild(q);
        	}
        }

        return qList;
    }




}
