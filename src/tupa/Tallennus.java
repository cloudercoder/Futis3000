/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tupa;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Omistaja
 */
public class Tallennus {
    
    private String tallennusTiedosto = "TUPA_kohteet";
    
        
    private List<Kohde> kohdetk = new ArrayList<>();
    
    Tallennus(){
        
    }
    
    Tallennus (List<Kohde> kohteet){
        kohdetk = kohteet;
    }
    
    
    public void suoritaTallennus(){
                ObjectOutputStream ulos = null;

        try {
            ulos = new ObjectOutputStream(new FileOutputStream(tallennusTiedosto));

            if (!kohdetk.isEmpty()) {
               
                for (int i = 0; i < kohdetk.size(); i++) {
                    
                    ulos.writeObject(kohdetk.get(i));
                }
            }

        } catch (IOException e) {
            

            System.out.println("\n****************************************************************");
            System.out.println("\nKohteiden tallentaminen ei onnistunut.");
            System.out.println("\n****************************************************************");

        } finally {
            try {
                ulos.close();
            } catch (IOException e) {
               

                System.out.println("\n************************************************************");
                System.out.println("Tiedoston sulkeminen ei onnistunut.");
                System.out.println("\n***********************************************************");

            }
        }
    }
    
    public String annaTallennusTiedosto(){
        return tallennusTiedosto;
    }
}
