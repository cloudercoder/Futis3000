package tupa;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author Marianne
 */
public class Ohjeistus{ 
        
        Ohjeistus(){
            
        }
        
     public void annaYleisOhje() {
        Stage stage = new Stage();
        BorderPane alue = new BorderPane();

stage.getIcons().add(new Image(this.getClass().getResource("icon.png").toString()));
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(30));
        vbox.setSpacing(10);

        HBox hbox1 = new HBox();
        Text viesti = new Text("TUPA - tulospalvelun käyttöliittymä on pyritty toteuttamaan siten, ettei käyttäjän tarvitse lukea erillistä käyttöohjetta.\n\nTarpeen mukaan sellainen voidaan kuitenkin laatia tähän.");

        hbox1.setAlignment(Pos.CENTER);
        hbox1.getChildren().add(viesti);

        HBox hbox2 = new HBox();
        hbox2.setPadding(new Insets(60));
        hbox2.setSpacing(10);
        Button ok = new Button("Sulje");
       
 
        ok.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                stage.close();
            }
        });
      hbox2.setAlignment(Pos.CENTER);
        hbox2.getChildren().addAll(ok);
        vbox.getChildren().addAll(hbox1, hbox2);
        alue.setCenter(vbox);

        Scene sceneV = new Scene(alue, 700, 200);
        stage.setTitle("TUPA - TULOSPALVELU");
        stage.setScene(sceneV);
        stage.show();

    }
    
          public void annaTietoa() {
        Stage stage = new Stage();
        BorderPane alue = new BorderPane();

stage.getIcons().add(new Image(this.getClass().getResource("icon.png").toString()));
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(10));
        vbox.setSpacing(60);

        VBox vbox1 = new VBox();
        vbox1.setSpacing(20);
        Text viesti1 = new  Text("TUPA - tulospalvelu v1.0");
         Text viesti2 = new  Text("\u00A9 \t Marianne Sjöberg & Victor Slätis 2016");
        
        vbox1.setAlignment(Pos.CENTER);
        vbox1.getChildren().addAll(viesti1, viesti2);

        HBox hbox2 = new HBox();
        hbox2.setPadding(new Insets(10));
        hbox2.setSpacing(10);
        Button ok = new Button("Sulje");
       
 
        ok.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                stage.close();
            }
        });
        hbox2.setAlignment(Pos.CENTER);
        hbox2.getChildren().addAll(ok);
        vbox.getChildren().addAll(vbox1, hbox2);
        alue.setCenter(vbox);

        Scene sceneV = new Scene(alue, 400, 200);
        stage.setTitle("TUPA - TULOSPALVELU");
        stage.setScene(sceneV);
        stage.show();

    }
    
}