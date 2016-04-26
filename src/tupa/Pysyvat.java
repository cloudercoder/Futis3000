/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tupa;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 *
 * @author Omistaja
 */
public class Pysyvat {

    private TUPA ikkuna;
    
    Pysyvat(){
        
    }
    
    Pysyvat (TUPA ikkuna){
        this.ikkuna = ikkuna;
    }
    
    public HBox rakennaYlaosa(){
                HBox kuvaotsikko = new HBox();    
        kuvaotsikko.setPadding(new Insets(10, 10, 15, 0));
        kuvaotsikko.setSpacing(10);

        ImageView selectedImage = new ImageView();
        Image image1 = new Image(TUPA.class.getResourceAsStream("pallo.jpg"));
        selectedImage.setImage(image1);
        selectedImage.setFitHeight(20);
        selectedImage.setFitWidth(20);

        ImageView selectedImage2 = new ImageView();
        Image image2 = new Image(TUPA.class.getResourceAsStream("pallo.jpg"));
        selectedImage2.setImage(image2);
        selectedImage2.setFitHeight(20);
        selectedImage2.setFitWidth(20);

        Label logo = new Label("TUPA \t - \t Tulospalvelu ");
        logo.setFont(Font.font("Papyrus", FontWeight.BOLD, 28));

        kuvaotsikko.setStyle("-fx-background-color:  linear-gradient(to bottom, #00b300, 	 #33ff33); -fx-border-color: BLACK; -fx-border-width: 1px 0px 1px 0px;");
        kuvaotsikko.setPadding(new Insets(20));
        kuvaotsikko.setSpacing(30);
        kuvaotsikko.setAlignment(Pos.CENTER);
        kuvaotsikko.getChildren().addAll(selectedImage, logo, selectedImage2);
        return kuvaotsikko;
    }
    
    public VBox rakennaAlaosa(){
            ListView loki = ikkuna.annaLoki();
        VBox alaosio = new VBox();
        alaosio.setStyle("-fx-background-color: linear-gradient(to bottom, #1a1a1a, #404040); -fx-border-color: BLACK; -fx-border-width: 1px; -fx-text-fill: #ff0099 ");
        alaosio.setPadding(new Insets(10));
        alaosio.setSpacing(10);
        Label alaotsikko = new Label("Tapahtumatiedot:");
        alaotsikko.setStyle("-fx-text-fill: #fff ");
        loki.setPrefHeight(100);
        loki.setPrefWidth(25);
        alaosio.getChildren().addAll(alaotsikko, loki);
        
        return alaosio;
    }

    
    public void writeMessage(String msg) {
        ObservableList<String> viesti = FXCollections.observableArrayList(msg);
        ikkuna.annaLoki().setItems(viesti);

    }
}
