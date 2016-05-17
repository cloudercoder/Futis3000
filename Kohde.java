package tupa;

import java.io.Serializable;

/**
 *
 * @author Marianne
 */
public class Kohde implements Serializable {

    private String nimi;
    private int id;
    private static int laskuri;
    

   Kohde (){
        laskuri++;
        id = laskuri;
   }
    
    Kohde (String nimi) {
        this.nimi = nimi;
        laskuri++;
        id = laskuri;

    }

    public String toString() {
        return nimi;
    } 
    
        public void asetaNimi(String nimi){
        
        this.nimi = nimi;
    }
 
   public int annaID(){
       return id;
   }
}
