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
    
    private static int saLaskuri;
    private List<Ottelu> ottelut = new ArrayList<Ottelu>();
    private List<Joukkue> joukkueet = new ArrayList<Joukkue>();
    private Turnaus turnaus;

   Sarja (){
       saLaskuri++;    
   }

   Sarja(String nimi, Turnaus turnaus){
            
       super(nimi);
        saLaskuri++;
        this.turnaus = turnaus;
   }
  
    public int annaMaara(){
        return saLaskuri;
    }

    public Turnaus annaTurnaus(){
        return turnaus;
    }
    
    public void asetaTurnaus(Turnaus turnaus){
        this.turnaus = turnaus;
    }
    
    public List<Ottelu> annaOttelut(){
        return ottelut;
    }
    
    public List<Joukkue> annaJoukkueet(){
        return joukkueet;
    }
    
}
