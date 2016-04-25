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
public class Joukkue extends Kohde {
    
    private static int joLaskuri;

   private List<Pelaaja> pelaajat = new ArrayList<Pelaaja>();

   Joukkue (){
      
       joLaskuri++;
   }
   
   Joukkue (String nimi){
            
       super(nimi);
        
   }
    
   public int annaJoukkueMaara(){
        return joLaskuri;
    } 
}
