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
public class Valmentaja extends Toimihenkilo {
    
   private static int vaLaskuri;

   
   Valmentaja (){
        vaLaskuri++;
   }

    Valmentaja(String nimi){
            
       super(nimi);
        
   }   
   public int annaValmentajaMaara(){
       
       return vaLaskuri;
   }
}