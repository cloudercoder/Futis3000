/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tupa;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.input.KeyCharacterCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author Omistaja
 */
public class Valikko implements EventHandler<ActionEvent>{
    



protected MenuBar menuBar;



public Valikko(){
    
}

public Valikko(MenuBar menu){
    
    this.menuBar=menu;
    
}

public MenuBar buildMenuBar() {


/// TIEDOSTO ///
Menu menuTiedosto = new Menu ("Tiedosto");
MenuItem uusi = new MenuItem ("Uusi");
uusi.setAccelerator (new KeyCharacterCombination("N", KeyCombination.SHORTCUT_DOWN));

MenuItem avaa = new MenuItem ("Avaa");
avaa.setAccelerator (new KeyCharacterCombination("O", KeyCombination.SHORTCUT_DOWN));

MenuItem tallenna = new MenuItem ("Tallenna");
tallenna.setAccelerator (new KeyCharacterCombination("S", KeyCombination.SHORTCUT_DOWN));


MenuItem tallenna_n = new MenuItem ("Tallenna nimellä");
tallenna_n.setAccelerator (new KeyCharacterCombination("S", KeyCombination.SHIFT_DOWN,  KeyCombination.SHORTCUT_DOWN));

MenuItem lopeta = new MenuItem ("Lopeta");
lopeta.setAccelerator (new KeyCharacterCombination("Q", KeyCombination.SHORTCUT_DOWN));
//menuTiedosto.getItems().add(lopeta);
//    lopeta.setOnAction(actionEvent -> Platform.exit());
//
//
//
//uusi.setOnAction (this);



uusi.setOnAction (this);
avaa.setOnAction (this);
tallenna.setOnAction (this);
tallenna_n.setOnAction (this);
lopeta.setOnAction (this);

menuTiedosto.getItems ().addAll(uusi, avaa, tallenna, tallenna_n, new SeparatorMenuItem (),lopeta);

Menu menuOhje = new Menu ("Ohje");
MenuItem ohje = new MenuItem ("Ohje");
ohje.setAccelerator (new KeyCharacterCombination("H", KeyCombination.SHORTCUT_DOWN));
ohje.setOnAction (this);
menuOhje.getItems ().addAll(ohje);




menuBar.getMenus ().addAll (menuTiedosto, menuOhje);

return menuBar;
    }
    
    
    @Override
public void handle (ActionEvent e)
{
   Object lahde = e.getSource ();
 
if (lahde instanceof MenuItem)
{
   
     
valikostaValittu (
((MenuItem) lahde).getText (), e);
return;
}

}



public void valikostaValittu (String teksti, ActionEvent e)
{
   
switch (teksti)
{
case "Uusi":{
 
    //sulkee vanhan ja avaa uuden, pitää kyllä kysyä tallennus!!!
     Stage stage = new Stage();
  
stage = (Stage) menuBar.getScene().getWindow();
//        stage.setTitle("popup");
//        stage.setScene(scene);
        stage.show();
   
    break;
}    
case "Avaa":
    //ks. OLIOPELI!!
    break;

case "Tallenna":
    //ks. OLIOPELI!!
    break;

case "Tallenna nimellä":
    //ks. OLIOPELI!!
    break;    
    
case "Lopeta":{
    
    //eka varmistus, että haluaako tallentaa..
    
//    Button nappi = (Button) e.getSource ();
	
   	BorderPane alue = new BorderPane ();
		
		Label viesti = new Label ("Haluatko tallentaa ennen ohjelman lopettamista?");
		alue.setTop (viesti);
		
		HBox hbox = new HBox ();
		hbox.setPadding (new Insets (10, 10, 15, 15));
		hbox.setSpacing (10);
		Button okNappi = new Button ("Tallenna");
		Button cancelNappi = new Button ("Lopeta tallentamatta");
		
		hbox.getChildren ().addAll (okNappi, cancelNappi);
		alue.setBottom (hbox);
		  Stage stageV = new Stage();
		Scene sceneV = new Scene (alue, 50, 100);
		stageV.setTitle ("TUPA - TULOSPALVELU");
		stageV.setScene (sceneV);
		stageV.show ();	
 
 
   
//    Platform.exit ();
    
}

break;
default:
System.out.println ("Valikosta " + teksti);
break;
}
}
}
