/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tupa;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 *
 * @author Omistaja
 */
public class Tiedottaja {
    
    private Tupa ikkuna;

    Tiedottaja() {

    }

    Tiedottaja(Tupa ikkuna) {
        this.ikkuna = ikkuna;
    }
    
    public void kirjoitaLoki(String msg) {
             
        ikkuna.annaLokilista().add(msg);
        ObservableList<String> viesti = FXCollections.observableArrayList(ikkuna.annaLokilista());
        ikkuna.annaLoki().setItems(viesti);

    }
    
    public void annaVaroitus(String msg){
   
Alert alert = new Alert(AlertType.WARNING);

alert.setTitle("TUPA - TULOSPALVELU");

alert.setHeaderText("Huom!");


alert.setContentText(msg);

alert.show();

        
        
    }
}
