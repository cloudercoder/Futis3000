/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tupa;

import java.io.Serializable;

/**
 *
 * @author Omistaja
 */
public class Kohde implements Serializable {

    private String nimi;
 

   Kohde (){
       
   }
    
    Kohde(String nimi) {
        this.nimi = nimi;

    }

    public String toString() {
        return nimi;
    } 
    
        public void asetaNimi(String nimi){
        
        this.nimi = nimi;
    }
}
