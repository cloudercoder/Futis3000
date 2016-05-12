package tupa;

/**
 *
 * @author Marianne
 */
public class Henkilo extends Kohde {
    
    // 1-64 kirjainta, vain isot ja pienet kirjaimet, yks tai useampi tavuviiva
    private String etunimi;
    // 1-64 kirjainta, vain isot ja pienet kirjaimet, yks tai useampi tavuviiva    
    private String sukunimi;  
    private static int heLaskuri;

    
    Henkilo(){
        heLaskuri++;      
    }
  
    Henkilo(String etunimi, String sukunimi, String nimi){
        super(nimi);
        this.etunimi = etunimi;
       this.sukunimi = sukunimi;
      
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
    
      
    public int annaHenkiloMaara(){
        
        return heLaskuri;
        
    }
    
   public String annaKokoNimi() {
        return (etunimi + " " + sukunimi);
    }
    
}
