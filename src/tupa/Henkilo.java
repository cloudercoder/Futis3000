/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tupa;

/**
 *
 * @author Omistaja
 */
public class Henkilo extends Kohde {
    
    // 1-64 kirjainta, vain isot ja pienet kirjaimet, yks tai useampi tavuviiva
    private String etunimi;
    // 1-64 kirjainta, vain isot ja pienet kirjaimet, yks tai useampi tavuviiva    
    private String sukunimi;  
    
    private static int heLaskuri;
    private int id;
    
    Henkilo(){
        
        heLaskuri++;
        id = heLaskuri;
    }
  
    Henkilo(String nimi){
            
       super(nimi);
     
        
   }
    public void asetaEtuNimi(String etu){
        
        etunimi = etu;
        
    }
    
    public void asetaSukuNimi(String suku){
        
        sukunimi = suku;
        
    }

    public void asetaKokoNimi(String etu, String suku){
        etunimi = etu;
        sukunimi = suku;
        
    }    
    
    public String annaEtuNimi(){
        
        return etunimi;
        
    }
    
    public String annaSukuNimi(){
        
        return sukunimi;
        
    }
    
    public int annaId(){
        
        return id;
        
    }
      
    public int annaHenkiloMaara(){
        
        return heLaskuri;
        
    }
    
   public String annaKokoNimi() {
        return (etunimi + " " + sukunimi);
    }
    
}
