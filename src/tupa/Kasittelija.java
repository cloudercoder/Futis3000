/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tupa;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

    private Tupa ikkuna;
    private Tiedottaja tiedottaja;
    private Nakyma nakyma;

    Kasittelija() {

    }

    Kasittelija(Tupa ikkuna) {
        this.ikkuna = ikkuna;
        tiedottaja = new Tiedottaja(ikkuna);
        nakyma = new Nakyma(ikkuna);
    }

    public void valittuKohde(TreeItem<Kohde> arvo) {

        String ohje = "";
        if (arvo == ikkuna.annaRootSarjat()) {
            ohje = ("Valitse vasemmalta haluamasi sarja.");
            nakyma.luoOhje(ohje, arvo);

        } else if (arvo == ikkuna.annaRootTuomarit()) {
            ohje = ("Valitse vasemmalta haluamasi tuomari.");
            nakyma.luoOhje(ohje, arvo);

        } else if (arvo.getParent().getValue() instanceof Sarja) {
            nakyma.luoSarjaSivu(arvo);
        }
        else if (arvo.getParent().getValue() instanceof Tuomari) {
           nakyma.luoTuomariSivu(arvo);
        }


    }
    
    public void branchExpended(TreeItem.TreeModificationEvent<Kohde> event) {
        String nodeValue = event.getSource().getValue().toString();
        tiedottaja.kirjoitaLoki("Kohde " + nodeValue + " valittu.");

    }

    public void branchCollapsed(TreeItem.TreeModificationEvent<Kohde> event) {
        String nodeValue = event.getSource().getValue().toString();
        tiedottaja.kirjoitaLoki("Kohde " + nodeValue + " suljettu.");

}

}
