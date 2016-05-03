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
public class Turnaus extends Kohde{
    
    private String nimi;
    private static int turLaskuri;
    private List<Sarja> sarjat = new ArrayList<>();
    private List<Tuomari> tuomarit = new ArrayList<>();
    
    Turnaus(){
  
        nimi = "Uusi turnaus";
    }
    
    
    public String toString(){
        return nimi;
    }
    
    public void asetaNimi(String nimi){
        this.nimi = nimi;
    }
    
    public int annaMaara(){
        return turLaskuri;
    }
    
    public List<Sarja> annaSarjat(){
        return sarjat;
    }
    
    public List<Tuomari> annaTuomarit(){
        return tuomarit;
    }
}
