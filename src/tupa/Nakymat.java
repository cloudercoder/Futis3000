/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tupa;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
public class Nakymat {

    private TUPA ikkuna;
    private Kasittelija kasittelija;
    private Muutokset muuttaja;
    private Pysyvat loki;
    //uusien kohtien lisäystä varten
    private TextField textField = new TextField();

    Nakymat() {

    }

    Nakymat(TUPA ikkuna) {
        this.ikkuna = ikkuna;
        kasittelija = new Kasittelija(ikkuna);
        muuttaja = new Muutokset(ikkuna);
        loki = new Pysyvat(ikkuna);
    }

    public void setEkaOhje(String uusiohje) {

        HBox ohjepalkki = new HBox();
        ohjepalkki.setStyle("-fx-background-color: blue;");
        ohjepalkki.setPadding(new Insets(10, 30, 10, 30));
        Text ohje = new Text(uusiohje);
        ohje.setFont(Font.font("Papyrus", FontWeight.BOLD, 20));

        ohjepalkki.getChildren().add(ohje);

        VBox peitto = new VBox();
        peitto.setStyle("-fx-background-color: white;");
        ikkuna.annaNaytto().getChildren().add(peitto);

        GridPane grid = new GridPane();

        grid.setPadding(new Insets(0, 100, 0, 300));
        grid.setVgap(10);
        grid.add(ohjepalkki, 1, 5);

        ikkuna.annaNaytto().getChildren().add(grid);

    }

    public GridPane luoPaanayttoSarjat() {

        Button addItemBtn = new Button("Lisää uusi sarja");
        addItemBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (textField.getText().trim().isEmpty()) {

                    loki.writeMessage("Et voi antaa tyhjää kenttää.");
                } else {

                    Kohde uusi = new Sarja(textField.getText());

                    muuttaja.addItem(uusi);
                }

            }
        });

        // tämän vois laittaa kunkin sarjan omaan näyttöön
//        Button removeItemBtn = new Button("Poista valittu sarja");
//        removeItemBtn.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                muuttaja.removeItem();
//            }
//        });
        HBox hbox1 = new HBox();
        Label label1 = new Label("Sarjan nimi:");
        hbox1.setSpacing(10);
        hbox1.getChildren().addAll(label1, textField, addItemBtn);

//        VBox hbox2 = new VBox();
//        Label label2 = new Label("Valitse vasemman puolen valikosta haluamasi sarja ja paina oheista painiketta poistaaksesi sen.");
//
//        hbox2.setSpacing(10);
//        hbox2.getChildren().addAll(label2, removeItemBtn);
        GridPane grid = new GridPane();

        grid.setPadding(new Insets(0, 100, 0, 300));
        grid.setVgap(10);

        Label otsikko = new Label("Lisää uusi sarja tai poista olemassa oleva.");
        otsikko.setFont(Font.font("Papyrus", 28));

        grid.add(otsikko, 1, 5);
        grid.add(hbox1, 1, 7);
//        grid.add(hbox2, 1, 9);

        return grid;
    }

    public GridPane luoPaanayttoJoukkueet() {

        Button addItemBtn = new Button("Lisää uusi joukkue");
        addItemBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (textField.getText().trim().isEmpty()) {

                    loki.writeMessage("Et voi antaa tyhjää kenttää.");
                } else {

                    Kohde uusi = new Joukkue(textField.getText());

                    muuttaja.addItem(uusi);
                }

            }
        });

        // tämän vois laittaa kunkin sarjan omaan näyttöön
//        Button removeItemBtn = new Button("Poista valittu joukkue");
//        removeItemBtn.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                muuttaja.removeItem();
//            }
//        });
        HBox hbox1 = new HBox();
        Label label1 = new Label("Joukkueen nimi:");
        hbox1.setSpacing(10);
        hbox1.getChildren().addAll(label1, textField, addItemBtn);
//
//        VBox hbox2 = new VBox();
//        Label label2 = new Label("Valitse vasemman puolen valikosta haluamasi joukkue ja paina oheista painiketta poistaaksesi sen.");
//
//        hbox2.setSpacing(10);
//        hbox2.getChildren().addAll(label2, removeItemBtn);

        GridPane grid = new GridPane();

        grid.setPadding(new Insets(0, 100, 0, 300));
        grid.setVgap(10);

        Label otsikko = new Label("Lisää uusi joukkue tai poista olemassa oleva.");
        otsikko.setFont(Font.font("Papyrus", 28));

        grid.add(otsikko, 1, 5);
        grid.add(hbox1, 1, 7);
//        grid.add(hbox2, 1, 9);

        return grid;
    }
}
