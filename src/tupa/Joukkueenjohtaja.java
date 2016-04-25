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
public class Joukkueenjohtaja extends Toimihenkilo{
    
   private static int joLaskuri;

   
   Joukkueenjohtaja (){
        joLaskuri++;
   }
   
   Joukkueenjohtaja(String nimi){
            
       super(nimi);
        
   }
   public int annaJojoMaara(){
       
       return joLaskuri;
   }

    
}
