package project16;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.Node;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Readplayers {
	public static Document doc;
	
	public static void main(String[] args) {
		try {
			Document doc = MyDomParser.getDocument("srml-90-2014-squads.xml");
			NodeList players = doc.getElementsByTagName("Player");
			for(int i = 0; i < players.getLength(); i++){
				Element p = (Element)players.item(i);
				Player player = new Player();
				String uid=p.getAttribute("uID");
				uid = uid.replace("p", "");
				player.setId(Integer.parseInt(uid));
				
				NodeList stats = p.getElementsByTagName("Stat");
				for (int j=0;j<stats.getLength();j++){
					if(stats.item(j).getNodeType()!=Node.ELEMENT_NODE){
						stats.item(j).getParentNode().removeChild(stats.item(j));
						break;
					}
					Element s = (Element)stats.item(j);
					if(s.getAttribute("Type").equals("first_name")){
						player.setFirst_name(s.getTextContent());
					}
					else if (s.getAttribute("Type").equals("last_name")){
						player.setLast_name(s.getTextContent());
					}
					else if (s.getAttribute("Type").equals("birth_date")){
						String birth = s.getTextContent();
						if (!birth.equals("Unknown")){	
							player.setBirth_year(Integer.parseInt(birth.substring(0, 4)));
						}
					}
					else if (s.getAttribute("Type").equals("height")){
						String content=s.getTextContent();
						if (!content.equals("Unknown")){
							player.setHeight(Integer.parseInt(content));
						}
					}
					else if (s.getAttribute("Type").equals("real_position")){
						String content=s.getTextContent();
						if (!content.equals("Unknown")){
							player.setReal_position(content);
						}
					}
					else if (s.getAttribute("Type").equals("country")){
						player.setCountry(s.getTextContent());
					}
				}
				System.out.println(player.toString());
				
				
				
				
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
	
	
	
}
