/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tupa;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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

    public void suoritaTallennus() {
        ObjectOutputStream ulos = null;
        kohdetk = ikkuna.annaKohteet();
        
        
        
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
//                  ResultSet i = st.executeQuery("SELECT COUNT(*) AS idtulostaulu FROM tulostaulu");
//                  
//                  
//                      String kysytietoja = JOptionPane.showInputDialog("Syötä rivitieto"); 
//                      int  rivinum = 1 + laskin ;
//                  
//                  
//                      
//		  int val = st.executeUpdate("INSERT INTO `tulostaulu`(idtulostaulu, tulos) VALUE ("+rivinum+",'"+kysytietoja+"')");
//		  if(val==1)
//			  System.out.print("Lisättiin onnistuneesti arvot");
//                  
		  conn.close();
		  } catch (Exception e) {
		  e.printStackTrace();
		  }
        
    
        
 
        List<Sarja> sarjatk = new ArrayList<>();
        TreeItem<Kohde> parent = new TreeItem<>();
        List<Joukkue> joukkuetk = new ArrayList<>();
        List<Pelaaja> pelaajatk = new ArrayList<>();
        List<Toimihenkilo> toimaritk = new ArrayList<>();
        List<Tuomari> tuomaritk = new ArrayList<>();

                  
                  
        try {
            ulos = new ObjectOutputStream(new FileOutputStream(tallennusTiedosto));

            if (!kohdetk.isEmpty()) {

                for (int i = 0; i < kohdetk.size(); i++) {
                    
                     

                       if (kohdetk.get(i) instanceof Sarja) {
                            Sarja sarja = (Sarja) kohdetk.get(i);
                  
                            sarjatk.add(sarja);
                            ikkuna.annaSarjatk().add(sarja);

                            parent = ikkuna.annaRootSarjat();
                            TreeItem<Kohde> newItem = new TreeItem<Kohde>(kohdetk.get(i));
                            parent.getChildren().add(newItem);
                            System.out.println(kohdetk.get(i));
                            
                            System.out.println("testi-Sarja");

                        } else if (kohdetk.get(i) instanceof Tuomari) {
                            /*
                            Kohdetk.get(i) on olio.
                            TUtkii onko tuomariluokassa muodostettu olio.
                            Jos on niin sit se poimii sen tiedot
                            
                            */
                            
                            int tuomariID = kohdetk.get(i).annaID();
                            //String etunimi = kohdetk.get(i).annaEtunimi();
                           // String sukunimi = kohdetk.get(i).annaSukunimi();
                            
                            
                            Tuomari tuomari = (Tuomari) kohdetk.get(i);
                            tuomaritk.add(tuomari);
                            ikkuna.annaTuomaritk().add(tuomari);
                                                        
                            
                            //Tämä menee nimi sarakkeeseen
                            System.out.print(tuomariID +"\t" + kohdetk.get(i)+"\n");
                            
                            

                        } else if (kohdetk.get(i) instanceof Joukkue) {
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
                
                System.out.println(tuomaritk);
            }

        } catch (IOException e) {

            System.out.println("\n****************************************************************");
            System.out.println("\nKohteiden tallentaminen ei onnistunut.");
            System.out.println("\n****************************************************************");

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
}
