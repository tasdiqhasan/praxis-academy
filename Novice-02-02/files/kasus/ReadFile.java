import java.io.File;
import java.io.FileReader;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;


import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;

public class ReadFile {

   public static void main(String[] args) {

      Long totalXml = null;
      Long totalJson = null;

      try {
         long startXml = System.nanoTime();

         File inputFile = new File("file.xml");
         DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
         DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
         Document doc = dBuilder.parse(inputFile);
         doc.getDocumentElement().normalize();
         System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
         NodeList nList = doc.getElementsByTagName("person");
         System.out.println("----------------------------");

         for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);
            System.out.println("\nCurrent Element :" + nNode.getNodeName());

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
               Element eElement = (Element) nNode;
               System.out.println("Person id : " 
                  + eElement.getAttribute("id"));
               System.out.println("Name : " 
                  + eElement
                  .getElementsByTagName("name")
                  .item(0)
                  .getTextContent());
               System.out.println("Company : " 
                  + eElement
                  .getElementsByTagName("company")
                  .item(0)
                  .getTextContent());
               System.out.println("Gender : " 
                  + eElement
                  .getElementsByTagName("gender")
                  .item(0)
                  .getTextContent());
               System.out.println("Registered : " 
                  + eElement
                  .getElementsByTagName("registered")
                  .item(0)
                  .getTextContent());
            }
         }

         long endXml   = System.nanoTime();
         totalXml = endXml - startXml;

      } catch (Exception e) {
         e.printStackTrace();
      }

      JSONParser parser = new JSONParser();

      try {

         long startJson = System.nanoTime();

         Reader reader = new FileReader("file.json");

         Object jsonObject = parser.parse(reader);
         JSONArray array = (JSONArray)jsonObject;

         JSONObject obj2 = (JSONObject)array.get(1);

         for (int i = 0; i < array.size(); i++) {
            JSONObject item = (JSONObject)array.get(i);
            System.out.println("Person id : " + item.get("id"));
            System.out.println("Name : " + item.get("name"));
            System.out.println("Company : " + item.get("company"));
            System.out.println("Gender : "  + item.get("gender"));
            System.out.println("Registered : " + item.get("registered"));
         }


         long endJson   = System.nanoTime();
         totalJson = endJson - startJson;

      } catch (IOException e) {
         e.printStackTrace();
      } catch (ParseException e) {
         e.printStackTrace();
      }


      System.out.println("\n\nTotal Execution Time Parsing Xml : "+totalXml);
      System.out.println("Total Execution Time Parsing Json : "+totalJson);
      if(totalJson > totalXml) {
         System.out.println("Parsing file json lebih lama dari parsing file xml");
      } else {
         System.out.println("Parsing file xml lebih lama dari parsing file json");
      }


   }
}