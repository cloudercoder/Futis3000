/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tupa;

import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;


/**
 *
 * @author Omistaja
 */
public class Haku {
    
        //alaosan loki
    private ListView hakutulos = new ListView();
    private List<Kohde> tuloslista = new ArrayList<>();
    private boolean tulos = false;
    private ObservableList<Kohde> tulokset;
       private ObservableList<String> eitulokset;
    Haku(){
        
    }
    
    public ListView luoHakuTulos(String hakusana){
        
        //tähän haku tietokannasta ja jos löyty tulos = true, else tulos = false
        
        if(tulos){
            //luettelon muodostus:
            //tuloslista = ...
            
//            tulokset = FXCollections.observableArrayList(tuloslista);
        }
       else{
             eitulokset = FXCollections.observableArrayList("Ei tuloksia");
             hakutulos.setStyle("-fx-background-color: white; -fx-border: none;");
        }

        hakutulos.setItems(eitulokset);
        return hakutulos;
    }
    

    
}
