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
public class Toimihenkilo extends Henkilo {
    
    private static int toLaskuri;
   private Joukkue joukkue;

    
    Toimihenkilo (){
        toLaskuri++;
   }
   
   Toimihenkilo(String nimi){
            
       super(nimi);
        
   }
   public int annaToimariMaara(){
       
       return toLaskuri;
   }
    
    
}
