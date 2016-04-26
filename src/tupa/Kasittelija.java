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
import javafx.scene.control.TreeItem;
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
public class Kasittelija {

    private TUPA ikkuna;
    private Pysyvat loki;

    Kasittelija() {

    }
    
    Kasittelija(TUPA ikkuna){
        this.ikkuna = ikkuna;
        loki = new Pysyvat(ikkuna);
    }
    
       public void valittuKohde(TreeItem<Kohde> arvo) {

        String ohje = "";
        if (arvo == ikkuna.annaRootSarjat()) {

            ohje = ("Valitse vasemmalta haluamasi sarja.");
            setOhje(ohje, arvo);

        } else if (arvo == ikkuna.annaRootJoukkueet()) {
            ohje = ("Valitse vasemmalta haluamasi joukkue.");
            setOhje(ohje, arvo);

        } else if (arvo == ikkuna.annaRootPelaajat()) {
            ohje = ("Valitse vasemmalta haluamasi pelaaja.");
            setOhje(ohje, arvo);

        } else if (arvo == ikkuna.annaRootTuomarit()) {
            ohje = ("Valitse vasemmalta haluamasi tuomari.");
            setOhje(ohje, arvo);

        } else if (arvo == ikkuna.annaRootToimarit()) {
            ohje = ("Valitse vasemmalta haluamasi toimihenkilö.");
            setOhje(ohje, arvo);

        } else if (arvo == ikkuna.annaRootToimarit1()) {
            ohje = ("Valitse vasemmalta haluamasi valmentaja.");
            setOhje(ohje, arvo);

        } else if (arvo == ikkuna.annaRootToimarit2()) {
            ohje = ("Valitse vasemmalta haluamasi joukkueenjohtaja.");
            setOhje(ohje, arvo);

        } else {
            //tähän mitä tapahtuu, jos alakohteita klikattu
        }

    }
    
    public void setOhje(String uusiohje, TreeItem<Kohde> arvo) {

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

        Button uusi = new Button();

        if (arvo.getValue() instanceof Sarja) {
            uusi.setText("Lisää uusi sarja");

        } else if (arvo.getValue() instanceof Joukkue) {
            uusi.setText("Lisää uusi joukkue");

        }
        uusi.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

                VBox peitto = new VBox();
                peitto.setStyle("-fx-background-color: white;");
                ikkuna.annaNaytto().getChildren().add(peitto);
                    
                Nakymat nakyma = new Nakymat(ikkuna);
                
                if (arvo.getValue() instanceof Sarja) {
                    ikkuna.annaNaytto().getChildren().add(nakyma.luoPaanayttoSarjat());
                } else if (arvo.getValue() instanceof Joukkue) {
                    ikkuna.annaNaytto().getChildren().add(nakyma.luoPaanayttoJoukkueet());
                }

            }
        });

        grid.add(uusi, 3, 2);
        ikkuna.annaNaytto().getChildren().add(grid);
    }
    
    public void branchExpended(TreeItem.TreeModificationEvent<Kohde> event) {
        String nodeValue = event.getSource().getValue().toString();
        loki.writeMessage("Kohde " + nodeValue + " valittu.");

    }

    public void branchCollapsed(TreeItem.TreeModificationEvent<Kohde> event) {
        String nodeValue = event.getSource().getValue().toString();
        loki.writeMessage("Kohde " + nodeValue + " suljettu.");

    }
    
}
