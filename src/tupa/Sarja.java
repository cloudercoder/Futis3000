/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tupa;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Omistaja
 */
public class Sarja extends Kohde {
    
    private static int laskuri;
    private int id;
    private List<Ottelu> ottelut = new ArrayList<Ottelu>();

   Sarja (){
       laskuri++;
       id = laskuri;
   }

   Sarja(String nimi){
            
       super(nimi);
        
   }
  
    public int annaMaara(){
        return laskuri;
    }
    
    public int annaId(){
        return id;
    }

}
