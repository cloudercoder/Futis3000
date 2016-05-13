package tupa;

import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Marianne
 */
public class Pelaaja extends Henkilo {

    private static int peLaskuri;
    private int id_julkinen;
    private Joukkue joukkue;
    // väliltä 1-99
    private int pelinumero;
    private String pelipaikka;
    private transient IntegerProperty taulukkonumero = new SimpleIntegerProperty();
    private transient IntegerProperty taulukko_ottelut = new SimpleIntegerProperty();
    private transient IntegerProperty taulukkomaalit = new SimpleIntegerProperty();
    private transient IntegerProperty taulukkosyotot = new SimpleIntegerProperty();
    private transient IntegerProperty taulukkopisteet = new SimpleIntegerProperty();
    private transient StringProperty taulukkopelipaikka = new SimpleStringProperty();
    private transient StringProperty taulukkonimi = new SimpleStringProperty();
        private transient StringProperty taulukkojoukkue = new SimpleStringProperty();
    private transient IntegerProperty taulukkosijoitus = new SimpleIntegerProperty();
    private transient StringProperty taulukkorooli = new SimpleStringProperty();
        private transient StringProperty taulukkosukunimi = new SimpleStringProperty();
    private transient StringProperty taulukkoetunimi = new SimpleStringProperty();

    private List<Kokoonpano> kokoonpanot = new ArrayList<>();
    private List<Maali> maalit = new ArrayList<>();

    Pelaaja() {

        peLaskuri++;
    }

    Pelaaja(String etunimi, String sukunimi) {
        super(etunimi, sukunimi, etunimi + " " + sukunimi);
        peLaskuri++;
        id_julkinen = 85000 + peLaskuri;

    }

    public List<Maali> annaMaaliLista(){
        return maalit;
    }
    
    public int annaPelaajaMaara() {
        return peLaskuri;
    }

    public int annaJulkinenID() {
        return id_julkinen;
    }

    public Joukkue annaJoukkue() {
        return joukkue;
    }

    public void asetaJoukkue(Joukkue joukkue) {
        this.joukkue = joukkue;
    }

    public List<Kokoonpano> annaKokoonpanot() {
        return kokoonpanot;
    }

    public void asetaPelinumero(int pelinumero) {
        this.pelinumero = pelinumero;
    }

    public int annaPelinumero() {
        return pelinumero;
    }

    public void asetaPelipaikka(String pelipaikka) {
        this.pelipaikka = pelipaikka;
    }

    public String annaPelipaikka() {
        return pelipaikka;
    }

    public int annaOttelut() {
        return kokoonpanot.size();
    }

    public int annaMaalit() {

        int maara = 0;

        for (int i = 0; i < maalit.size(); i++) {
            if (maalit.get(i).annaMaalinTekija().equals(this)) {
                maara++;
            }
        }

        return maara;
    }

    public int annaSyotot() {

        int maara = 0;

        for (int i = 0; i < maalit.size(); i++) {
            if (maalit.get(i).annaSyottaja().equals(this)) {
                maara++;
            }

        }

        return maara;
    }

    public int annaPisteet() {

        return (this.annaMaalit() + annaSyotot());
    }

    public IntegerProperty taulukkonumeroProperty() {
        return taulukkonumero;
    }

    public void asetaTaulukkonumero() {
        this.taulukkonumero = new SimpleIntegerProperty(this.annaPelinumero());
    }

    public IntegerProperty taulukko_ottelutProperty() {
        return taulukko_ottelut;
    }

    public void asetaTaulukko_ottelut() {
        this.taulukko_ottelut = new SimpleIntegerProperty(this.annaOttelut());
    }

    public IntegerProperty taulukkomaalitProperty() {
        return taulukkomaalit;
    }

    public void asetaTaulukkomaalit() {
        this.taulukkomaalit = new SimpleIntegerProperty(this.annaMaalit());
    }

    public IntegerProperty taulukkosyototProperty() {
        return taulukkosyotot;
    }

    public void asetaTaulukkosyotot() {
        this.taulukkosyotot = new SimpleIntegerProperty(this.annaSyotot());
    }

    public IntegerProperty taulukkopisteetProperty() {
        return taulukkopisteet;
    }

    public void asetaTaulukkopisteet() {
        this.taulukkopisteet = new SimpleIntegerProperty(this.annaPisteet());
    }

    public StringProperty taulukkopelipaikkaProperty() {
        return taulukkopelipaikka;
    }

    public void asetaTaulukkopelipaikka() {
        this.taulukkopelipaikka = new SimpleStringProperty(this.annaPelipaikka());
    }

    public String annaTaulukkopelipaikka() {
        return taulukkopelipaikka.get();
    }

    public StringProperty taulukkonimiProperty() {
        return taulukkonimi;
    }

    public void asetaTaulukkonimi() {
        this.taulukkonimi = new SimpleStringProperty(this.annaKokoNimi());
    }

    public StringProperty taulukkoetunimiProperty() {
        return taulukkoetunimi;
    }

    public void asetaTaulukkoetunimi() {
        
        this.taulukkoetunimi = new SimpleStringProperty(this.annaEtuNimi());
    }
    
       public StringProperty taulukkosukunimiProperty() {
        return taulukkosukunimi;
    }

    public void asetaTaulukkosukunimi() {
        
        this.taulukkosukunimi = new SimpleStringProperty(this.annaSukuNimi());
    }
    
    
    public StringProperty taulukkojoukkueProperty() {
        return taulukkojoukkue;
    }

    public void asetaTaulukkojoukkue() {
        this.taulukkojoukkue = new SimpleStringProperty(this.annaJoukkue().toString());
    }

    public void asetaTaulukkosijoitus(int i) {
        this.taulukkosijoitus = new SimpleIntegerProperty(i);
    }

    public IntegerProperty taulukkosijoitusProperty() {
        return taulukkosijoitus;
    }

    public List<Maali> annaKaikkiMaalit() {
        return maalit;
    }

    public void asetaTaulukkorooli(Ottelu ottelu) {

        boolean on_kokoonpanossa = false;

        for (int i = 0; i < kokoonpanot.size(); i++) {
            if (kokoonpanot.get(i).annaOttelu().equals(ottelu)) {
                on_kokoonpanossa = true;
            }
        }

        if (on_kokoonpanossa) {
            this.taulukkorooli = new SimpleStringProperty("Kokoonpanossa");
        } else {
            this.taulukkorooli = new SimpleStringProperty("");
        }

    }

    public StringProperty taulukkorooliProperty() {
        return taulukkorooli;
    }

}
