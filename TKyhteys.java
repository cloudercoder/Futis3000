/*
 * Derp
 * ASD
 * ASD
 */
package TKyhteys;

import java.sql.*;
import javax.swing.JOptionPane;


public class TKyhteys 
{
	public static void main(String[] args) {
		  String url = "jdbc:mysql://localhost:3306/";
		  String dbName = "mydb";
		  String driver = "com.mysql.jdbc.Driver";
		  String userName = "root"; 
		  String password = "ebin";
		  try {
		  Class.forName(driver).newInstance();
		  Connection conn = DriverManager.getConnection(url+dbName,userName,password);
		  Statement st = conn.createStatement();
		  ResultSet res = st.executeQuery("SELECT * FROM  tulostaulu");
		  while (res.next()) {
		  int id = res.getInt("idtulostaulu");
                                   
		  String msg = res.getString("tulos");
		  System.out.println(id + "\t" + msg);
		  }
                  
                      String kysytietoja = JOptionPane.showInputDialog("Please input mark for test 1: "); 
                  
                  
		  int val = st.executeUpdate("INSERT into tulostaulu VALUES("+6+",+ kysytietoja +");
		  if(val==1)
			  System.out.print("Successfully inserted value");
                  
		  conn.close();
		  } catch (Exception e) {
		  e.printStackTrace();
		  }
		  }
}
