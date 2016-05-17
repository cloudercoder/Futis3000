/*
Tämä on assosiaatioluokka luokkien Tuomari ja Ottelu välillä
 */
package tupa;

import java.io.Serializable;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Marianne
 */
public class TuomarinRooli implements Serializable {

    private String rooli;
    private Ottelu ottelu;
    private Tuomari tuomari;

    private transient StringProperty taulukkorooli = new SimpleStringProperty();
    private transient StringProperty taulukkonimi = new SimpleStringProperty();

    TuomarinRooli() {

    }

    TuomarinRooli(Tuomari tuomari, Ottelu ottelu) {
        this.tuomari = tuomari;
        this.ottelu = ottelu;
    }

    public String annaRooli() {
        return rooli;
    }

    public void asetaRooli(String rooli) {
        this.rooli = rooli;
    }

    public void asetaOttelu(Ottelu ottelu) {

        this.ottelu = ottelu;
    }

    public Ottelu annaOttelu() {
        return ottelu;
    }

    public void asetaTuomari(Tuomari tuomari) {

        this.tuomari = tuomari;
    }

    public Tuomari annaTuomari() {
        return tuomari;
    }

    public StringProperty taulukkorooliProperty() {
        return taulukkorooli;
    }

    public void asetaTaulukkorooli() {
        this.taulukkorooli = new SimpleStringProperty(this.annaRooli());
    }

    public StringProperty taulukkonimiProperty() {
        return taulukkonimi;
    }

    public void asetaTaulukkonimi() {
        this.taulukkonimi = new SimpleStringProperty(this.annaTuomari().toString());
    }

}
