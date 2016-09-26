package project16;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class MyDomParser {

    public static void main(String[] args){
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            //builds document from xml file
        	DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse("f24-90-2014-739631-eventdetails.xml");

            //oppretter game-objekt, henter ut og setter id fra Game-node i doc
            Game game = new Game();
            Node gameNode = doc.getElementsByTagName("Game").item(0);
            Element gameElement = (Element) gameNode;
            game.setId(Integer.parseInt(gameElement.getAttribute("id")));

            //henter alle XMLevent-noder fra doc og oppretter og setter verdier for Event-objekter. Legger disse
            // i en ArrayList
            NodeList xmlEventList = getEventList(doc);
            ArrayList eventList = new ArrayList();
            for (int i=0; i<xmlEventList.getLength();i++){
            	Element xmlEvent = (Element) xmlEventList.item(i);
            	Event event = new Event();
            	event.setId(Integer.parseInt(xmlEvent.getAttribute("id")));
            	event.setGameid(game.getId());
            	event.setNumber(i+1);
            	if(xmlEvent.hasAttribute("player_id")){
            		event.setPlayerid(Integer.parseInt(xmlEvent.getAttribute("player_id")));
            	}
            	if(xmlEvent.hasAttribute("player_id")){
            		event.setTeamid(Integer.parseInt(xmlEvent.getAttribute("team_id")));
            	}
            	event.setValue(Integer.parseInt(xmlEvent.getAttribute("event_id")));
            	event.setXstart(Float.parseFloat(xmlEvent.getAttribute("x")));
            	event.setYstart(Float.parseFloat(xmlEvent.getAttribute("y")));

            	eventList.add(event);
            }











        } catch (ParserConfigurationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SAXException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static NodeList getEventList(Document doc){
        NodeList eventList = doc.getElementsByTagName("Event");
        return eventList;

    }

    public static NodeList getChildNodeList(Element e){
        NodeList childNodeList = e.getChildNodes();
        return childNodeList;

    }
    public static NodeList getQByEventId(String eventId, NodeList eventList){
        NodeList qList = null;
        for(int i=0;i<eventList.getLength();i++){
            Node e = eventList.item(i);
            Element event = (Element) e;
            String thisID = event.getAttribute("id");
            if(thisID.equals(eventId)){
                qList = getChildNodeList(event);
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

    public static String getNodeId(Node n){
		Element e = (Element) n;
    	return e.getAttribute("id");

    }




}
