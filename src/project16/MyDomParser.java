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
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse("f24-90-2014-739631-eventdetails.xml");
            NodeList eventList = getEventList(doc);
            NodeList qList = getQByEventId("596660156", eventList);
            for (int i=0; i<qList.getLength();i++){
            	Node q = qList.item(i);
            	Element qual = (Element) q;
            	System.out.println(qual.getAttribute("id"));
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
