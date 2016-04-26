/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tupa;

import java.util.ArrayList;
import java.util.List;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author Omistaja
 */
public class Varmistus {
    
    private List<Kohde> kohdetk = new ArrayList<>();
    
    Varmistus(){
        
    }
    
    Varmistus (List<Kohde> kohteet){
        kohdetk = kohteet;
    }
    
    public void annaVarmistus(){
         Stage stageV = new Stage();
            BorderPane alue = new BorderPane ();
		
            VBox vbox = new VBox();
            	vbox.setPadding (new Insets (10));
		vbox.setSpacing (10);
		
                HBox hbox1 = new HBox();
                Label viesti = new Label ("Haluatko tallentaa ennen ohjelman lopettamista?");
		
                hbox1.setAlignment(Pos. CENTER);
		hbox1.getChildren().add(viesti);
                
                
		HBox hbox2 = new HBox ();
		hbox2.setPadding (new Insets (10));
		hbox2.setSpacing (10);
		Button jooTallennus = new Button ("Tallenna");
		Button eiTallennus = new Button ("Älä tallenna");
                Button peruuta = new Button ("Peruuta");
		 eiTallennus.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                    Platform.exit ();
            }
        });
                 
                  jooTallennus.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            
                Tallennus tallenna = new Tallennus(kohdetk);
                
                tallenna.suoritaTallennus();
                   Platform.exit ();
            }
        });
                  peruuta.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
           
                  stageV.close();
            }
        });
		hbox2.getChildren ().addAll (jooTallennus, eiTallennus, peruuta);
		vbox.getChildren ().addAll (hbox1, hbox2);
                alue.setCenter (vbox);
		  
		Scene sceneV = new Scene (alue, 400, 100);
		stageV.setTitle ("TUPA - TULOSPALVELU");
		stageV.setScene (sceneV);
		stageV.show ();	
 

            }
  
    
}
