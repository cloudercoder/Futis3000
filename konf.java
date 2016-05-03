

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.File;
import java.sql.*;
import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;


public class konf 
{
	public static void main(String[] args) {
		  String url = "jdbc:mysql://tite.work:3306/";
		  String dbName = "tupa";
		  String driver = "com.mysql.jdbc.Driver";
		  String userName = "root"; 
		  String password = "asdlol";
		  try {
		  Class.forName(driver).newInstance();
		  Connection conn = DriverManager.getConnection(url+dbName,userName,password);
		  Statement st = conn.createStatement();
		  ResultSet res = st.executeQuery("SELECT * FROM  tulostaulu");
                  int laskin = 0;
                  
                  
                  String tiedostoN = "data.xml";
                  
                  DocumentBuilderFactory rakentajaTehdas = DocumentBuilderFactory.newInstance ();
		  			DocumentBuilder docTehdas = rakentajaTehdas.newDocumentBuilder ();
		  			Document doc = docTehdas.newDocument ();
		  			
		  			
		  			//Luodaan elementit. Juurisolmu on koko XML-tiedoston ydin.
		  			Element juuriSolmu = doc.createElement ("Tulospalvelu-3000");
		  			
		  			//Lis‰t‰‰n uuteen elementtiin arvo 
		  			doc.appendChild (juuriSolmu);
                  
		  			
                  
                  
                  
		  while (res.next()) {
			    ++laskin;
			  
		  int id = res.getInt("idtulostaulu");
                                   
		  String viesti = res.getString("tulos");
          
		  int luku = id;
		  String numS = Integer.toString(luku);      
		 		  		  		  
		  
//		  <Tulospalvelu-3000>
//		    <tulos id="1">
//		      <tulos>ghfj</tulos>

		  
		  
          // Luodaan elementti idtulos. Annetaan sen nimeksi / tagiksi tulos   
		  //Annetaan attribuutin nimeksi ID 
		  //Annetaan attribuutin ID = arvoksi numS (Arvo saadaan tietokannasta)
		  //Lis‰t‰‰n attribuutti idtulos elementtiin		 
		  //Lis‰t‰‰n juuri tehty idtulos elementtiin Juurisolmu   
		  
		  Element idtulos = doc.createElement ("tulos");
		  Attr buutti = doc.createAttribute ("id");
		  buutti.setNodeValue (numS);
		  idtulos.setAttributeNode (buutti);
		  juuriSolmu.appendChild(idtulos);
		  
		  			
		  //Luodaan uusi elementti jonka nimi on tulos
		  //Sijoitetaan siihen arvo viesti (ARvo tietokannasta)			
		  //T‰ss‰ luodaan alielementti "tulos" elementille idtulos		
		  			Element tulos = doc.createElement ("tulos");
		  			tulos.appendChild (doc.createTextNode (viesti));
		  			idtulos.appendChild (tulos);
                  
                      
                  
		//  System.out.println(id + "\t" + viesti);
		 
		  		  	  		  		  
		  
		  }
		  
		  

			
			
			TransformerFactory transformerFactory = TransformerFactory.newInstance ();
  			Transformer transformer = transformerFactory.newTransformer ();
  			transformer.setOutputProperty (OutputKeys.INDENT, "yes");
  			transformer.setOutputProperty ("{http://xml.apache.org/xslt}indent-amount", "2");
  			
  			DOMSource source = new DOMSource (doc);
  			
  			// Tulostetaan XML-serialisoitu olio konsoliin.
  			StreamResult konsoliin = new StreamResult (System.out);
  			transformer.transform (source, konsoliin);

  			// Kirjoitetaan XML-serialisoitu olio tiedostoon.
  			File tiedosto = new File (tiedostoN);
  			StreamResult result = new StreamResult (tiedosto);
  			transformer.transform (source, result);
		  
		  
		  
		  
		  
		  
		  
		  
//                  ResultSet i = st.executeQuery("SELECT COUNT(*) AS idtulostaulu FROM tulostaulu");
//                  
//                  
//                      String kysytietoja = JOptionPane.showInputDialog("Please input mark for test 1: "); 
//                      int  rivinum = 1 + laskin ;
//                  
//                  
//                      
//		  int val = st.executeUpdate("INSERT INTO `tulostaulu`(idtulostaulu, tulos) VALUE ("+rivinum+",'"+kysytietoja+"')");
//		  if(val==1)
//			  System.out.print("Lis‰ttiin onnistuneesti uusi rivi\n\n\n\n");
                  
		  conn.close();
		  } catch (Exception e) {
		  e.printStackTrace();
		  }
		  
		  

		  
		  		String tiedostoNimi = "data.xml";
		  		
		  		try
		  		{
		  			DocumentBuilderFactory rakentajaTehdas = DocumentBuilderFactory.newInstance ();
		  			DocumentBuilder docTehdas = rakentajaTehdas.newDocumentBuilder ();
		  			Document doc = docTehdas.newDocument ();
		  			
		  			Element juuriSolmu = doc.createElement ("Tulos");
		  			doc.appendChild (juuriSolmu);
		  			

		  			
		  			Element koko1 = doc.createElement ("Tulos");
		  			
		  			Attr attr = doc.createAttribute ("id");
		  			attr.setValue ("1");
		  			koko1.setAttributeNode (attr);
		  			
		  			Element leveys = doc.createElement ("tulos");
		  			leveys.appendChild (doc.createTextNode ("42"));
		  			koko1.appendChild (leveys);
		  			

		  			
		  			juuriSolmu.appendChild (koko1);
		  			
		  			TransformerFactory transformerFactory = TransformerFactory.newInstance ();
		  			Transformer transformer = transformerFactory.newTransformer ();
		  			transformer.setOutputProperty (OutputKeys.INDENT, "yes");
		  			transformer.setOutputProperty ("{http://xml.apache.org/xslt}indent-amount", "2");
		  			
		  			DOMSource source = new DOMSource (doc);
		  			
//		  			// Tulostetaan XML-serialisoitu olio konsoliin.
//		  			StreamResult konsoliin = new StreamResult (System.out);
//		  			transformer.transform (source, konsoliin);

		  			// Kirjoitetaan XML-serialisoitu olio tiedostoon.
		  			File tiedosto = new File (tiedostoNimi);
		  			StreamResult result = new StreamResult (tiedosto);
		  			transformer.transform (source, result);
		  		}
		  		catch (ParserConfigurationException e)
		  		{
		  			e.printStackTrace ();
		  		}
		  		catch (TransformerException e)
		  		{
		  			e.printStackTrace ();
		  		}
		
		  
		  
		  
		  
		  
		  
		  
		  
		  }
}








