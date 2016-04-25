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
public class Ottelu {
    
    private static int laskuri;
    private int id;
    private int otteluNumero;    
    private Joukkue koti;
    private Joukkue vieras;
    private int kotimaalit;
    private int vierasmaalit;
    private String tulos;
    private String nimi = (koti + " - " + vieras);
    private Sarja sarja;
    private String aika;
    private String paikka;
   private List<Maali> maalit = new ArrayList<Maali>();
    
    
   Ottelu (){
       laskuri++;
       id = laskuri;
       otteluNumero = 88 + laskuri;
   }
    
    public String toString() {
        return nimi;
    }
    
    
    public int annaMaara(){
        return laskuri;
    }
    
    public int annaId(){
        return id;
    }
    
    public int annaOtteluNumero(){
        return otteluNumero;
    }
    
    public void asetaTulos(int koti, int vieras){
        kotimaalit = koti;
        vierasmaalit = vieras;
        tulos = (koti + " - " + vieras);  
    }
    
    public String annaTulos(){
        return tulos;
    }
    
    public void asetaJoukkueet(Joukkue koti, Joukkue vieras){
        
        this.koti = koti;
        this.vieras = vieras;
    }
    
    public Joukkue annaKoti(){
        return koti;
    }
    
    public Joukkue annaVieras(){
        return vieras;
    }
    
    public void asetaAika(String aika){
        
        this.aika = aika;
    }
    
    public void asetaPaikka(String paikka){
        
        this.paikka = paikka;
    }
    
    public String annaAika(){
        
        return aika;
    }
    
    public String annaPaikka(){
        
        return paikka;
    }
    
}
