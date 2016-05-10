package tupa;

import java.io.Serializable;
import java.time.LocalDate;
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
public class Ottelu implements Serializable {

    private static int laskuri;
    private int id;
    private int otteluNumero;
    private Joukkue kotijoukkue;
    private Joukkue vierasjoukkue;
    private int kotimaalit;
    private int vierasmaalit;
    private String tulos;
    private String nimi;
    private Sarja sarja;
    private String paiva;
    private String kello;
    private LocalDate kalenteriaika;
    private String paikka;
    private List<Maali> maalit = new ArrayList<>();
    private List<TuomarinRooli> roolit = new ArrayList<>();
    private Kokoonpano koti_kokoonpano;
    private Kokoonpano vieras_kokoonpano;
    private String kellotunnit;
    private String kellominuutit;
    private int kierros;
    

    //taulukkoattribuutit   
    private transient IntegerProperty taulukkoid = new SimpleIntegerProperty();
    private transient StringProperty taulukkopaikka = new SimpleStringProperty();
    private transient StringProperty taulukkonimi = new SimpleStringProperty();
    private transient StringProperty taulukkotulos = new SimpleStringProperty();
    private transient StringProperty taulukkopaiva = new SimpleStringProperty();
    private transient StringProperty taulukkokello = new SimpleStringProperty();
    private transient StringProperty taulukkoerotuomari = new SimpleStringProperty();
    private transient StringProperty taulukkoavustava1 = new SimpleStringProperty();
    private transient StringProperty taulukkoavustava2 = new SimpleStringProperty();
     private transient IntegerProperty taulukkokierros = new SimpleIntegerProperty();

    Ottelu() {
        laskuri++;
        id = laskuri;
        otteluNumero = 88 + laskuri;
    }

    Ottelu(Sarja sarja) {
        this.sarja = sarja;
        laskuri++;
        id = laskuri;
        otteluNumero = 88 + laskuri;
        tulos = "-";

    }

    public void asetaID(int id) {
        this.id = id;
    }

    public String toString() {
        return nimi;
    }

    public int annaKierros(){
        return kierros;
    }
    
    public int annaKotimaalit(){
        return kotimaalit;
    }
    
    public int annaVierasmaalit(){
        return vierasmaalit;
    }
    
    public void asetaKierros(int kierros){
        this.kierros = kierros;
    }
    
    public int annaMaara() {
        return laskuri;
    }

    public int annaId() {
        return id;
    }

    public int annaOtteluNumero() {
        return otteluNumero;
    }

    public void asetaTulos(int koti, int vieras) {
        kotimaalit = koti;
        vierasmaalit = vieras;

        //päivitetään maalitilastot
        kotijoukkue.asetaTehdyt(kotimaalit);
        kotijoukkue.asetaPaastetyt(vierasmaalit);
        vierasjoukkue.asetaTehdyt(vierasmaalit);
        vierasjoukkue.asetaPaastetyt(kotimaalit);

        //päivitetään pistetilastot
        if (kotimaalit > vierasmaalit) {
            kotijoukkue.annaVoitot().add(1);
            vierasjoukkue.annaHaviot().add(1);
        } else if (kotimaalit < vierasmaalit) {
            kotijoukkue.annaHaviot().add(1);
            vierasjoukkue.annaVoitot().add(1);
        } else {
            kotijoukkue.annaTasapelit().add(1);
            vierasjoukkue.annaTasapelit().add(1);
        }

        tulos = (kotimaalit + " - " + vierasmaalit);
    }

    public String annaTulos() {
        return tulos;
    }

    public void asetaJoukkueet(Joukkue koti, Joukkue vieras) {

        this.kotijoukkue = koti;
        this.vierasjoukkue = vieras;
        kotijoukkue.annaOttelut().add(this);
        vierasjoukkue.annaOttelut().add(this);
        this.nimi = (koti.toString() + " - " + vieras.toString());
        koti_kokoonpano = new Kokoonpano(this, koti);
        vieras_kokoonpano = new Kokoonpano(this, vieras);
    }

    public Joukkue annaKotijoukkue() {
        return kotijoukkue;
    }

    public Joukkue annaVierasjoukkue() {
        return vierasjoukkue;
    }

    public Kokoonpano annaKotiKokoonpano() {
        return koti_kokoonpano;
    }

    public Kokoonpano annaVierasKokoonpano() {
        return vieras_kokoonpano;
    }

    public void asetaAika(LocalDate aika, String tunnit, String minuutit) {

        this.kalenteriaika = aika;

        this.paiva = aika.toString();
        this.kellotunnit = tunnit;
        this.kellominuutit = minuutit;
        this.kello = tunnit + " : " + minuutit;
    }

    public String annaKello() {
        return kello;
    }

    public void asetaPaikka(String paikka) {

        this.paikka = paikka;
    }

    public String annaPaiva() {

        return paiva;
    }

    public String annaKellominuutit() {
        return kellominuutit;
    }

    public String annaKellotunnit() {
        return kellotunnit;
    }

    public LocalDate annaKalenteriAika() {

        return kalenteriaika;
    }

    public String annaPaikka() {

        return paikka;
    }

    public Sarja annaSarja() {
        return sarja;
    }

    public StringProperty taulukkonimiProperty() {
        return taulukkonimi;
    }

    public void asetaTaulukkonimi() {
        this.taulukkonimi = new SimpleStringProperty(this.toString());
    }

    public StringProperty taulukkopaikkaProperty() {
        return taulukkopaikka;
    }

    public void asetaTaulukkopaikka() {
        this.taulukkopaikka = new SimpleStringProperty(this.annaPaikka());
    }

    public StringProperty taulukkopaivaProperty() {
        return taulukkopaiva;
    }

    public void asetaTaulukkopaiva() {
        this.taulukkopaiva = new SimpleStringProperty(this.annaPaiva());
    }

    public StringProperty taulukkokelloProperty() {
        return taulukkokello;
    }

    public void asetaTaulukkokello() {
        this.taulukkokello = new SimpleStringProperty(this.annaKello());
    }

    public IntegerProperty taulukkoidProperty() {
        return taulukkoid;
    }

    public void asetaTaulukkoid() {
        this.taulukkoid = new SimpleIntegerProperty(this.annaOtteluNumero());
    }
    
    public IntegerProperty taulukkokierrosProperty() {
        return taulukkokierros;
    }

    public void asetaTaulukkokierros() {
        this.taulukkokierros = new SimpleIntegerProperty(this.annaKierros());
    }

    public StringProperty taulukkotulosProperty() {
        return taulukkotulos;
    }

    public void asetaTaulukkotulos() {
        this.taulukkotulos = new SimpleStringProperty(this.annaTulos());
    }

    public StringProperty taulukkoerotuomariProperty() {
        return taulukkoerotuomari;
    }

    public StringProperty taulukkoavustava1Property() {
        return taulukkoavustava1;
    }

    public StringProperty taulukkoavustava2Property() {
        return taulukkoavustava2;
    }

    public void asetaTaulukkotuomarit() {

        for (int i = 0; i < this.annaRoolit().size(); i++) {

            if (this.annaRoolit().get(i).annaRooli().equals("Erotuomari")) {
                this.taulukkoerotuomari = new SimpleStringProperty(this.annaRoolit().get(i).annaTuomari().annaKokoNimi());
            } else if (this.annaRoolit().get(i).annaRooli().equals("1. Avustava erotuomari")) {
                this.taulukkoavustava1 = new SimpleStringProperty(this.annaRoolit().get(i).annaTuomari().annaKokoNimi());
            } else if (this.annaRoolit().get(i).annaRooli().equals("2. Avustava erotuomari")) {
                this.taulukkoavustava2 = new SimpleStringProperty(this.annaRoolit().get(i).annaTuomari().annaKokoNimi());
            }
        }

    }

    public List<Maali> annaMaalit() {
        return maalit;
    }

    public List<TuomarinRooli> annaRoolit() {
        return roolit;
    }

    public Tuomari annaErotuomari() {

        Tuomari erotuomari = new Tuomari();

        for (int i = 0; i < this.annaRoolit().size(); i++) {

            if (this.annaRoolit().get(i).annaRooli().equals("Erotuomari")) {
                erotuomari = this.annaRoolit().get(i).annaTuomari();
            }
        }
        return erotuomari;
    }

    public Tuomari annaAvustava1() {
        Tuomari avustava = new Tuomari();

        for (int i = 0; i < this.annaRoolit().size(); i++) {

            if (this.annaRoolit().get(i).annaRooli().equals("1. Avustava erotuomari")) {
                avustava = this.annaRoolit().get(i).annaTuomari();
            }
        }
        return avustava;
    }

    public Tuomari annaAvustava2() {
        Tuomari avustava = new Tuomari();

        for (int i = 0; i < this.annaRoolit().size(); i++) {

            if (this.annaRoolit().get(i).annaRooli().equals("2. Avustava erotuomari")) {
                avustava = this.annaRoolit().get(i).annaTuomari();
            }
        }
        return avustava;
    }
}
