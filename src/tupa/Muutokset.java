/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tupa;

import javafx.scene.control.TreeItem;

/**
 *
 * @author Omistaja
 */
public class Muutokset {

    private TUPA ikkuna;
    private Pysyvat loki;

    Muutokset() {

    }

    Muutokset(TUPA ikkuna) {
        this.ikkuna = ikkuna;
        loki = new Pysyvat(ikkuna);
    }

    public void addItem(Kohde value) {

        TreeItem<Kohde> parent = new TreeItem<>();

        if (value instanceof Sarja) {
            parent = ikkuna.annaRootSarjat();
        } else if (value instanceof Joukkue) {
            parent = ikkuna.annaRootJoukkueet();
        } else if (value instanceof Pelaaja) {
            parent = ikkuna.annaRootPelaajat();
        } else if (value instanceof Valmentaja) {
            parent = ikkuna.annaRootToimarit1();
        } else if (value instanceof Joukkueenjohtaja) {
            parent = ikkuna.annaRootToimarit2();
        } else if (value instanceof Tuomari) {
            parent = ikkuna.annaRootTuomarit();
        }

        for (TreeItem<Kohde> child : parent.getChildren()) {
            if (child.getValue().toString().equals(value.toString())) {

                if (value instanceof Sarja) {
                    loki.writeMessage("Tämänniminen sarja on jo olemassa ");
                } else if (value instanceof Joukkue) {
                    loki.writeMessage("Tämänniminen joukkue on jo olemassa ");
                }

                return;
            }
        }

        // MITÄ MUUTA PITÄÄ TARKISTAA??!?!
        //LISÄYKSET:
        // puuhun:
        TreeItem<Kohde> newItem = new TreeItem<Kohde>(value);
        parent.getChildren().add(newItem);

        // kohteen omat:
        //yleiseen tietokantaan:
        if (value instanceof Sarja) {
            ikkuna.annaSarjatk().add((Sarja) value);

        } else if (value instanceof Joukkue) {
            ikkuna.annaJoukkuetk().add((Joukkue) value);
        } else if (value instanceof Pelaaja) {
            ikkuna.annaPelaajatk().add((Pelaaja) value);
        } else if (value instanceof Tuomari) {
            ikkuna.annaTuomaritk().add((Tuomari) value);
        } else if (value instanceof Valmentaja) {
            ikkuna.annaValmentajatk().add((Valmentaja) value);
        } else if (value instanceof Joukkueenjohtaja) {
            ikkuna.annaJojotk().add((Joukkueenjohtaja) value);
        }

        ikkuna.annaKohteet().add(value);
        //avataan se valikko, mihin uusi kohde on lisätty
        if (!parent.isExpanded()) {
            parent.setExpanded(true);
        }
    }

    public void removeItem() {
        TreeItem<Kohde> item = ikkuna.annaSivuPuu().getSelectionModel().getSelectedItem();

        if (item == null) {
            loki.writeMessage("Valitse sarja, jonka haluat poistaa.");
            return;
        }

        TreeItem<Kohde> parent = item.getParent();
        if (parent == null) {
            loki.writeMessage("Sarjaa ei voida poistaa.");
        } else {
            parent.getChildren().remove(item);
        }
    }
}
