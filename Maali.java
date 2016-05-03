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
public class Maali {
    
    private Pelaaja maalintekija;
    private Pelaaja syottaja;
    private Ottelu ottelu;
    
    Maali(){
        
    }
    
    Maali(Pelaaja maalintekija, Pelaaja syottaja, Ottelu ottelu){
        
        this.maalintekija = maalintekija;
        this.syottaja = syottaja;
        this.ottelu = ottelu;
        
    }
    
    public Pelaaja annaMaalinTekija(){
        
        return maalintekija;
    }
    
    public Pelaaja annaSyottaja(){
        
        return syottaja;
    }
    
    public void asetaMaalinTekija(Pelaaja maalintekija){
        
        this.maalintekija = maalintekija;
    }
    
    public void asetaSyottaja(Pelaaja syottaja){
        
        this.syottaja = syottaja;
    }
}
