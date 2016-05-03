/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tkyhteys.tkyhteys;

import java.sql.*;


public class TKyhteys1 
        
{

        
        

	public static String main(String[] args) {
		  String url = "jdbc:mysql://tite.work:3306/";
		  String dbName = "tupa";
		  String driver = "com.mysql.jdbc.Driver";
		  String userName = "root"; 
		  String password = "asdlol";
		  try {
		  Class.forName(driver).newInstance();
                      try (Connection conn = DriverManager.getConnection(url+dbName,userName,password)) {
                          Statement st = conn.createStatement();
                          ResultSet res = st.executeQuery("SELECT * FROM  tulostaulu");
                          ResultSetMetaData tulosmeta;
                          tulosmeta = res.getMetaData();
                          int laskin =tulosmeta.getColumnCount();
                          
                          StringBuilder r = new StringBuilder("<table>\n");
                          
                          int num = 1;
                          while (res.next()) {
                              r.append("<row>");
                              r.append("<num>").append(num++).append("</num>");
                              for (int i = 1; i<= laskin; i++){
                                  String sarakeNimi = tulosmeta.getColumnName(i);
                                  r.append('<').append(sarakeNimi).append('>');
                                  r.append(res.getObject(i));
                                  r.append("</").append(sarakeNimi).append('>');
                                  
                              }
                              r.append("</row>\n");
                          }
                          r.append("</table>");
                          
                          return r.toString();
                      }
		  } catch (Exception e) {
		  e.printStackTrace();
		  }
            String asd = null;
		 return asd; }
}


