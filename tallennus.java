package tupa;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.TreeItem;
import javax.swing.JOptionPane;

/**
 *
 * @author Omistaja
 */
public class Tallennus {
    
    public static void main(String[] args) {}

    private String tallennusTiedosto = "TUPA_tallennus";
    private Tupa ikkuna;

    private List<Kohde> kohdetk = new ArrayList<>();

    Tallennus() {

    }

    
    
    
    
    Tallennus(Tupa ikkuna) {

        this.ikkuna = ikkuna;
    }

    public void suoritaTallennus() throws InstantiationException, SQLException, IllegalAccessException {
        ObjectOutputStream ulos = null;
        kohdetk = ikkuna.annaKohteet();
        
        
        

//		  try {
//		  Class.forName(driver).newInstance();
//		  Connection conn = DriverManager.getConnection(url+dbName,userName,password);
//		  Statement st = conn.createStatement();
//		  ResultSet tuomarit = st.executeQuery("SELECT * FROM  tuomarit");
//                  int laskin = 0;
//		  while (tuomarit.next()) {
//		  int tid = tuomarit.getInt("tuomari-ID");
//                                   
//		  String tEtu = tuomarit.getString("tuomariEtunimi");
//		  String tSuku = tuomarit.getString("tuomariSukunimi");
//                      
//		  System.out.println("Loop-testi");
//                  ++laskin;
//		  System.out.println(tid + "\t" + tEtu + "\t" + tSuku);
//		  }
////                  ResultSet i = st.executeQuery("SELECT COUNT(*) AS idtulostaulu FROM tulostaulu");
////                  
////                  
////                      String kysytietoja = JOptionPane.showInputDialog("Syötä rivitieto"); 
////                      int  rivinum = 1 + laskin ;
////                  
////                  
////                      
////		  int val = st.executeUpdate("INSERT INTO `tulostaulu`(idtulostaulu, tulos) VALUE ("+rivinum+",'"+kysytietoja+"')");
////		  if(val==1)
////			  System.out.print("Lisättiin onnistuneesti arvot");
////                  
//		  
//		//  System.out.println("sulku");
//		//  conn.close();
//		  } catch (Exception e) {
//		  e.printStackTrace();
//		  }
        
    
        
 
        List<Sarja> sarjatk = new ArrayList<>();
        TreeItem<Kohde> parent = new TreeItem<>();
        List<Joukkue> joukkuetk = new ArrayList<>();
        List<Pelaaja> pelaajatk = new ArrayList<>();
        List<Toimihenkilo> toimaritk = new ArrayList<>();
        List<Tuomari> tuomaritk = new ArrayList<>();

        
                	  String url = "jdbc:mysql://tite.work:3306/";
		  String dbName = "tupa";
		  String driver = "com.mysql.jdbc.Driver";
		  String userName = "root"; 
		  String password = "asdlol";
        
  
        
                  
                  
        try {
        	
        	          Class.forName(driver).newInstance();
		  Connection conn = DriverManager.getConnection(url+dbName,userName,password);
		  Statement st = conn.createStatement();
		  ResultSet tuomarit = st.executeQuery("SELECT * FROM  `tupa`.`tuomarit`");
                  int laskin = 4;
                  
                  System.out.println("Loop-testi");
		  while (tuomarit.next()) {
		  int tid = tuomarit.getInt("tuomari-ID");
                                   
		  String tEtu = tuomarit.getString("tuomariEtunimi");
		  String tSuku = tuomarit.getString("tuomariSukunimi");
                      
		  System.out.println("Loop-testi");
                  ++laskin;
		  System.out.println(tid + "\t" + tEtu + "\t" + tSuku);
		  }
      
        	
        	ResultSet asd = st.executeQuery("SELECT * FROM  `tupa`.`tuomarit`");
        	// int v3 =  st.executeUpdate("insert into tuomarit (tuomari-ID) values ('"+laskin+"')");
        	
        	
        	
        	
            ulos = new ObjectOutputStream(new FileOutputStream(tallennusTiedosto));

            if (!kohdetk.isEmpty()) {

                for (int i = 0; i < kohdetk.size(); i++) {
                    
                	
                	// Nopeutetaan koodia 
                	Kohde tiedot = kohdetk.get(i);
                     

                       if (tiedot instanceof Sarja) {
                            Sarja sarja = (Sarja) kohdetk.get(i);
                  
                            sarjatk.add(sarja);
                            ikkuna.annaSarjatk().add(sarja);

                         int sID = sarja.annaID();
                         Turnaus es = sarja.annaTurnaus();
                        
                            
                          

                        } else if (tiedot instanceof Tuomari) {
                            /*
                            Kohdetk.get(i) on olio.
                            TUtkii onko tuomariluokassa muodostettu olio.
                            Jos on niin sit se poimii sen tiedot
                            
                            */
                            
                            int tuomariID = kohdetk.get(i).annaID();
                            Tuomari tuomari = (Tuomari) kohdetk.get(i);
                            tuomaritk.add(tuomari);
                            ikkuna.annaTuomaritk().add(tuomari);
                            
                            int tID = tuomari.annaJulkinenId();
                            String etunimi = tuomari.annaEtuNimi();
                            String sukunimi = tuomari.annaSukuNimi();
                            
                            
                            
                            //Tämä menee nimi sarakkeeseen
                            
                            
                           System.out.println(tuomariID +"\t" + tID +"\t" + etunimi + sukunimi + kohdetk.get(i)+"\n");
                           
                        	//	  int val = st.executeUpdate("INSERT INTO `tupa`.`tuomarit`(tuomari-ID, tuomariEtunimi, tuomariSukunimi) VALUE ('"+tuomariID+"','"+etunimi+"','"+sukunimi+"')");
                                //          int v2 =  st.executeUpdate("insert into tuomarit (tuomari-ID) values ('"+tID+"')");
                                
                                 //int val = st.executeUpdate("INSERT INTO `tupa`.`tuomarit` VALUES(" + tID + "," + etunimi + "," + sukunimi + ")");
                                 
                                 st.executeUpdate("INSERT INTO tuomarit " + "VALUES ("+tID+",  '"+etunimi+"', '"+sukunimi+"')");
                                          
		//  if(val==1 || v2==1)
			  System.out.println("Lisättiin onnistuneesti arvot");




                            

                        } else if (tiedot instanceof Joukkue) {
                            System.out.println("testi-Joukkue");
                            Joukkue joukkue = (Joukkue) kohdetk.get(i);
                            joukkuetk.add(joukkue);
                            ikkuna.annaJoukkuetk().add(joukkue);

                            joukkue.asetaTaulukkonimi();
                            
                        } else if (kohdetk.get(i) instanceof Pelaaja) {
                            System.out.println("testi-Pelaaja");
                            Pelaaja pelaaja = (Pelaaja) kohdetk.get(i);
                            pelaajatk.add(pelaaja);
                            ikkuna.annaPelaajatk().add(pelaaja);
                       

                        } else if (kohdetk.get(i) instanceof Toimihenkilo) {
                            System.out.println("testi-Toimihenkilö");
                            Toimihenkilo toimari = (Toimihenkilo) kohdetk.get(i);
                            toimaritk.add(toimari);
                            ikkuna.annaToimaritk().add(toimari);

                            toimari.asetaTaulukkonimi();
                            toimari.asetaTaulukkosposti();
                            toimari.asetaTaulukkopuh();
                            toimari.asetaTaulukkorooli();
                        } else if (kohdetk.get(i) instanceof Turnaus) {

                            ikkuna.asetaTurnaus(kohdetk.get(i));

                        }  
                    
                    
                    ulos.writeObject(kohdetk.get(i));
                }
                
               // System.out.println(tuomaritk);
            }

        } catch (IOException e) {

            System.out.println("\n****************************************************************");
            System.out.println("\nKohteiden tallentaminen ei onnistunut.");
            System.out.println("\n****************************************************************");

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Tallennus.class.getName()).log(Le	vel.SEVERE, null, ex);
        } finally {
            try {
                ulos.close();
            } catch (IOException e) {

                System.out.println("\n************************************************************");
                System.out.println("Tiedoston sulkeminen ei onnistunut.");
                System.out.println("\n***********************************************************");

            }
        }

        //päivitetään tilanne, että tallennus on suoritettu
        ikkuna.asetaMuutos(false);

    }

    public String annaTallennusTiedosto() {
        return tallennusTiedosto;
    }

    private String getCurrentTimeStamp() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}