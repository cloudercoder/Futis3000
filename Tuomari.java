package tupa;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Marianne
 */
public class Tuomari extends Henkilo {

    private int id_julkinen;
    private static int tuLaskuri;
    private List<TuomarinRooli> roolit = new ArrayList<>();
    private Turnaus turnaus;

    Tuomari() {
        tuLaskuri++;
        id_julkinen = 88 + tuLaskuri;
    }

    Tuomari(String etunimi, String sukunimi) {
        super(etunimi, sukunimi, etunimi + " " + sukunimi);
        tuLaskuri++;
        id_julkinen = 88 + tuLaskuri;

    }

    Tuomari(String nimi) {
        super(nimi);
        tuLaskuri++;
        id_julkinen = 88 + tuLaskuri;
    }

    public int annaTuomariMaara() {
        return tuLaskuri;
    }

    public int annaJulkinenId() {
        return id_julkinen;
    }

    public List<TuomarinRooli> annaTuomarinRoolit() {
        return roolit;
    }

    public void asetaTurnaus(Turnaus turnaus) {
        this.turnaus = turnaus;
    }

    public Turnaus annaTurnaus() {
        return turnaus;
    }
}
