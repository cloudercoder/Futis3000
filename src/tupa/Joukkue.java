/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tupa;

import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Omistaja
 */
public class Joukkue extends Kohde {

    private static int joLaskuri;

    private int julkinenID;

    private transient StringProperty taulukkonimi = new SimpleStringProperty();
    private Sarja sarja;
    private List<Ottelu> ottelut = new ArrayList<Ottelu>();
    private List<Toimihenkilo> toimarit = new ArrayList<Toimihenkilo>();
    private List<Pelaaja> pelaajat = new ArrayList<Pelaaja>();

    Joukkue() {

        joLaskuri++;
        julkinenID = 201600 + joLaskuri;

    }

    Joukkue(String nimi) {

        super(nimi);
        joLaskuri++;
        julkinenID = 201600 + joLaskuri;

    }

    public int annaJoukkueMaara() {
        return joLaskuri;
    }

    public int annaJulkinenID() {
        return julkinenID;
    }

    public StringProperty taulukkonimiProperty() {
        return taulukkonimi;
    }

    public void asetaSarja(Sarja sarja) {
        this.sarja = sarja;
    }

    public Sarja annaSarja() {
        return sarja;
    }

    public List<Pelaaja> annaPelaajat() {
        return pelaajat;
    }

    public void asetaTaulukkonimi() {
        this.taulukkonimi = new SimpleStringProperty(this.toString());
    }

    public List<Toimihenkilo> annaToimarit() {
        return toimarit;
    }

    public List<Ottelu> annaOttelut() {
        return ottelut;
    }
}
