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
public class Tuomari extends Henkilo{
    
    private static int tuLaskuri;
    private Ottelu ottelu;


   Tuomari (){
      
       tuLaskuri++;
   }
   
   Tuomari(String nimi){
            
       super(nimi);
        
   }
    
   public int annaTuomariMaara(){
        return tuLaskuri;
    } 
}
