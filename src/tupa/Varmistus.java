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
import javafx.scene.control.TreeItem;
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
    private Tupa ikkuna;
    private Muuttaja muuttaja;
    Varmistus(){
        
    }
    
    Varmistus (List<Kohde> kohteet, Tupa ikkuna){
        kohdetk = kohteet;
        this.ikkuna = ikkuna;
    }
    
    Varmistus (Tupa ikkuna){
        muuttaja = new Muuttaja(ikkuna);
        this.ikkuna = ikkuna;
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
            
                Tallennus tallenna = new Tallennus(ikkuna);
                
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
                   hbox2.setAlignment(Pos. CENTER);
		hbox2.getChildren ().addAll (jooTallennus, eiTallennus, peruuta);
		vbox.getChildren ().addAll (hbox1, hbox2);
                alue.setCenter (vbox);
		  
		Scene sceneV = new Scene (alue, 400, 100);
		stageV.setTitle ("TUPA - TULOSPALVELU");
		stageV.setScene (sceneV);
		stageV.show ();	
 

            }
    
    public void annaPoistoVarmistus(Kohde arvo){
         Stage stageV = new Stage();
            BorderPane alue = new BorderPane ();
		
            VBox vbox = new VBox();
            	vbox.setPadding (new Insets (10));
		vbox.setSpacing (10);
		String nimi = arvo.toString();
                HBox hbox1 = new HBox();
                Label viesti = new Label ("Haluatko todella poistaa kohteen " + nimi + " ?");
		
                hbox1.setAlignment(Pos. CENTER);
		hbox1.getChildren().add(viesti);
                
                
		HBox hbox2 = new HBox ();
		hbox2.setPadding (new Insets (10));
		hbox2.setSpacing (10);
		Button joo = new Button ("Kyllä");
		
                Button peruuta = new Button ("Peruuta");
	
                 
                  joo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            
                
                muuttaja.poistaKohde(arvo);
                stageV.close();
                
            }
        });
                  peruuta.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
           
                  stageV.close();
            }
        });
                   hbox2.setAlignment(Pos. CENTER);
		hbox2.getChildren ().addAll (joo, peruuta);
		vbox.getChildren ().addAll (hbox1, hbox2);
                alue.setCenter (vbox);
		  
		Scene sceneV = new Scene (alue, 400, 100);
		stageV.setTitle ("TUPA - TULOSPALVELU");
		stageV.setScene (sceneV);
		stageV.show ();	
 

            }
    
    
    
    public void annaUudenVarmistus(){
         Stage stageV = new Stage();
            BorderPane alue = new BorderPane ();
		
            VBox vbox = new VBox();
            	vbox.setPadding (new Insets (10));
		vbox.setSpacing (10);
		
                HBox hbox1 = new HBox();
                Label viesti = new Label ("Haluatko tallentaa ennen uuden avaamista?");
		
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
                          //tyhjennetään kaikki tiedot 
                ikkuna.annaKohteet().clear();
               
                Kohde uusiTurnaus = new Turnaus();
                ikkuna.asetaTurnaus(uusiTurnaus);

                //vielä pitää tyhjentää puu
                TreeItem<Kohde> parentSarjat = ikkuna.annaRootSarjat();
                TreeItem<Kohde> parentTuomarit = ikkuna.annaRootTuomarit();
                parentSarjat.getChildren().clear();
                parentTuomarit.getChildren().clear();
                
                
               
                       stageV.close();
            }
        });
                 
                  jooTallennus.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            
                Tallennus tallenna = new Tallennus(ikkuna);
                
                tallenna.suoritaTallennus();
                  //tyhjennetään kaikki tiedot 
                ikkuna.annaKohteet().clear();
               
                Kohde uusiTurnaus = new Turnaus();
                ikkuna.asetaTurnaus(uusiTurnaus);

                //vielä pitää tyhjentää puu
                TreeItem<Kohde> parentSarjat = ikkuna.annaRootSarjat();
                TreeItem<Kohde> parentTuomarit = ikkuna.annaRootTuomarit();
                parentSarjat.getChildren().clear();
                parentTuomarit.getChildren().clear();
                
                
                      stageV.close();
            }
        });
                  peruuta.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
           
                  stageV.close();
            }
        });
		 hbox2.setAlignment(Pos. CENTER);
                  hbox2.getChildren ().addAll (jooTallennus, eiTallennus, peruuta);
		vbox.getChildren ().addAll (hbox1, hbox2);
                alue.setCenter (vbox);
		  
		Scene sceneV = new Scene (alue, 400, 100);
		stageV.setTitle ("TUPA - TULOSPALVELU");
		stageV.setScene (sceneV);
		stageV.show ();	
 

            }
    
    public void annaAvausVarmistus(){
         Stage stageV = new Stage();
            BorderPane alue = new BorderPane ();
		
            VBox vbox = new VBox();
            	vbox.setPadding (new Insets (10));
		vbox.setSpacing (10);
		
                HBox hbox1 = new HBox();
                Label viesti = new Label ("Haluatko tallentaa ennen uuden avaamista?");
		
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
                          //tyhjennetään kaikki tiedot 
                ikkuna.annaKohteet().clear();
             
                Kohde uusiTurnaus = new Turnaus();
                ikkuna.asetaTurnaus(uusiTurnaus);

                //vielä pitää tyhjentää puu
                TreeItem<Kohde> parentSarjat = ikkuna.annaRootSarjat();
                TreeItem<Kohde> parentTuomarit = ikkuna.annaRootTuomarit();
                parentSarjat.getChildren().clear();
                parentTuomarit.getChildren().clear();
                
                 //sitten vasta avaukseen
                //tuodaan tallennetut kohteet
              
                List<Kohde> kohdetk = new ArrayList<>();
                Avaus avaaja = new Avaus();
                kohdetk = avaaja.avaa();
                //talletetaan muistiin, mitä oli ennen "istunnon" aloittamista -> tarviiko kysyä tallennusta, jos käyttäjä sulkee näytön
                
                ikkuna.asetaKohteet(kohdetk);
           

                List<Sarja> sarjatk = new ArrayList<>();
              
                List<Tuomari> tuomaritk = new ArrayList<>();
              
                //viedään kohteet omiin listoihin
                TreeItem<Kohde> parent = new TreeItem<>();
                for (int i = 0; i < kohdetk.size(); i++) {

                    if (kohdetk.get(i) instanceof Sarja) {

                        sarjatk.add((Sarja) kohdetk.get(i));
                        ikkuna.annaSarjatk().add((Sarja) kohdetk.get(i));
                        parent = ikkuna.annaRootSarjat();
                        TreeItem<Kohde> newItem = new TreeItem<Kohde>(kohdetk.get(i));
                        parent.getChildren().add(newItem);

                    } else if (kohdetk.get(i) instanceof Tuomari) {
                        tuomaritk.add((Tuomari) kohdetk.get(i));
                        ikkuna.annaTuomaritk().add((Tuomari) kohdetk.get(i));
                        parent = ikkuna.annaRootTuomarit();
                        TreeItem<Kohde> newItem = new TreeItem<Kohde>(kohdetk.get(i));
                        parent.getChildren().add(newItem);
                    } else if (kohdetk.get(i) instanceof Turnaus) {

                        ikkuna.asetaTurnaus(kohdetk.get(i));

                    }
                }
                
                
               
                       stageV.close();
            }
        });
                 
                  jooTallennus.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            
                Tallennus tallenna = new Tallennus(ikkuna);
                
                tallenna.suoritaTallennus();
                  //tyhjennetään kaikki tiedot 
                ikkuna.annaKohteet().clear();
               
                Kohde uusiTurnaus = new Turnaus();
                ikkuna.asetaTurnaus(uusiTurnaus);

                //vielä pitää tyhjentää puu
                TreeItem<Kohde> parentSarjat = ikkuna.annaRootSarjat();
                TreeItem<Kohde> parentTuomarit = ikkuna.annaRootTuomarit();
                parentSarjat.getChildren().clear();
                parentTuomarit.getChildren().clear();
                 //sitten vasta avaukseen
                //tuodaan tallennetut kohteet
                List<Kohde> kohdetk_vanha = new ArrayList<>();
                List<Kohde> kohdetk = new ArrayList<>();
                Avaus avaaja = new Avaus();
                kohdetk = avaaja.avaa();
                //talletetaan muistiin, mitä oli ennen "istunnon" aloittamista -> tarviiko kysyä tallennusta, jos käyttäjä sulkee näytön
            
                ikkuna.asetaKohteet(kohdetk);
              

                List<Sarja> sarjatk = new ArrayList<>();
                
                List<Tuomari> tuomaritk = new ArrayList<>();
           

                //viedään kohteet omiin listoihin
                TreeItem<Kohde> parent = new TreeItem<>();
                for (int i = 0; i < kohdetk.size(); i++) {

                    if (kohdetk.get(i) instanceof Sarja) {

                        sarjatk.add((Sarja) kohdetk.get(i));
                        ikkuna.annaSarjatk().add((Sarja) kohdetk.get(i));
                        parent = ikkuna.annaRootSarjat();
                        TreeItem<Kohde> newItem = new TreeItem<Kohde>(kohdetk.get(i));
                        parent.getChildren().add(newItem);

                    }  else if (kohdetk.get(i) instanceof Tuomari) {
                        tuomaritk.add((Tuomari) kohdetk.get(i));
                        ikkuna.annaTuomaritk().add((Tuomari) kohdetk.get(i));
                        parent = ikkuna.annaRootTuomarit();
                        TreeItem<Kohde> newItem = new TreeItem<Kohde>(kohdetk.get(i));
                        parent.getChildren().add(newItem);
                    }  else if (kohdetk.get(i) instanceof Turnaus) {

                        ikkuna.asetaTurnaus(kohdetk.get(i));

                    }
                }
                
                      stageV.close();
            }
        });
                  peruuta.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
           
                  stageV.close();
            }
        });
		 hbox2.setAlignment(Pos. CENTER);
                  hbox2.getChildren ().addAll (jooTallennus, eiTallennus, peruuta);
		vbox.getChildren ().addAll (hbox1, hbox2);
                alue.setCenter (vbox);
		  
		Scene sceneV = new Scene (alue, 400, 100);
		stageV.setTitle ("TUPA - TULOSPALVELU");
		stageV.setScene (sceneV);
		stageV.show ();	
 

            }
    
        public void annaOtteluPoistoVarmistus(Ottelu ottelu){
         Stage stageV = new Stage();
            BorderPane alue = new BorderPane ();
		
            VBox vbox = new VBox();
            	vbox.setPadding (new Insets (10));
		vbox.setSpacing (10);
		
                HBox hbox1 = new HBox();
                Label viesti = new Label ("Haluatko todella poistaa ottelun " + ottelu.toString() + " ?");
		
                hbox1.setAlignment(Pos. CENTER);
		hbox1.getChildren().add(viesti);
                
                
		HBox hbox2 = new HBox ();
		hbox2.setPadding (new Insets (10));
		hbox2.setSpacing (10);
		Button joo = new Button ("Kyllä");
		
                Button peruuta = new Button ("Peruuta");
	
                 
                  joo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            
                
                muuttaja.poistaOttelu(ottelu);
                stageV.close();
                
            }
        });
                  peruuta.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
           
                  stageV.close();
            }
        });
                   hbox2.setAlignment(Pos. CENTER);
		hbox2.getChildren ().addAll (joo, peruuta);
		vbox.getChildren ().addAll (hbox1, hbox2);
                alue.setCenter (vbox);
		  
		Scene sceneV = new Scene (alue, 400, 100);
		stageV.setTitle ("TUPA - TULOSPALVELU");
		stageV.setScene (sceneV);
		stageV.show ();	
 

            }
                   
  
    
}
