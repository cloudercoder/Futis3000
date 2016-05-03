/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tkyhteys.tkyhteys;

import java.sql.*;
import javax.swing.JOptionPane;


public class TKyhteys 
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
		  while (res.next()) {
		  int id = res.getInt("idtulostaulu");
                                   
		  String msg = res.getString("tulos");
                      
                  ++laskin;
		  System.out.println(id + "\t" + msg);
		  }
                  ResultSet i = st.executeQuery("SELECT COUNT(*) AS idtulostaulu FROM tulostaulu");
                  
                  
                      String kysytietoja = JOptionPane.showInputDialog("Please input mark for test 1: "); 
                      int  rivinum = 1 + laskin ;
                  
                  
                      
		  int val = st.executeUpdate("INSERT INTO `tulostaulu`(idtulostaulu, tulos) VALUE ("+rivinum+",'"+kysytietoja+"')");
		  if(val==1)
			  System.out.print("Successfully inserted value");
                  
		  conn.close();
		  } catch (Exception e) {
		  e.printStackTrace();
		  }
		  }
}
