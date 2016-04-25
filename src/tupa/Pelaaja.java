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
public class Pelaaja extends Henkilo {
   
    private static int peLaskuri;
    private Joukkue joukkue;
    // välilät 1-99
    private int pelaajaNumero;
 
    // ottelut ja maalit
    private String pelipaikka;

   Pelaaja (){
      
       peLaskuri++;
   }
   
   Pelaaja(String nimi){
            
       super(nimi);
        
   }
    public int annaPelaajaMaara(){
        return peLaskuri;
    }
    
    public int annaPelaajaNumero(){
        return pelaajaNumero;
    }
    
   public void asetaPelaajaNumero(int pelinumero){
        pelaajaNumero = pelinumero;
    }
    
    public void asetaPelipaikka(String pelipaikka){
        
       this.pelipaikka = pelipaikka; 
    }
    
    public String annaPelipaikka(){
        return pelipaikka;
    }
}
