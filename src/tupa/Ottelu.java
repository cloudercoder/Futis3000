package tupa;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
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
    private Date paiva_date;
    private String kello = "0 : 00";
    private LocalDate kalenteriaika;
    private String paikka;
    private List<Maali> maalit = new ArrayList<>();
    private List<TuomarinRooli> roolit = new ArrayList<>();
    private Kokoonpano koti_kokoonpano;
    private Kokoonpano vieras_kokoonpano;
    private String kellotunnit = "0";
    private String kellominuutit = "00";
    private int kierros;

    //taulukkoattribuutit   
    private transient IntegerProperty taulukkoid = new SimpleIntegerProperty();
    private transient StringProperty taulukkopaikka = new SimpleStringProperty();
    private transient StringProperty taulukkonimi = new SimpleStringProperty();
    private transient StringProperty taulukkotulos = new SimpleStringProperty();
    private transient ObjectProperty<Date> taulukkopaiva = new SimpleObjectProperty();
        private transient StringProperty taulukkopaivastring = new SimpleStringProperty();
    private transient StringProperty taulukkokello = new SimpleStringProperty();
    private transient StringProperty taulukkotunnit = new SimpleStringProperty();
    private transient StringProperty taulukkominuutit = new SimpleStringProperty();
    private transient ObjectProperty<Tuomari> taulukkoerotuomari = new SimpleObjectProperty();

    private transient ObjectProperty<Tuomari> taulukkoavustava1 = new SimpleObjectProperty();
    private transient ObjectProperty<Tuomari> taulukkoavustava2 = new SimpleObjectProperty();
    private transient IntegerProperty taulukkokierros = new SimpleIntegerProperty();
    private transient ObjectProperty<Joukkue> taulukkokotijoukkue = new SimpleObjectProperty();
    private transient ObjectProperty<Joukkue> taulukkovierasjoukkue = new SimpleObjectProperty();

    Ottelu() {
    
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

    public void asetaNimi(String nimi) {
        this.nimi = nimi;
    }

    public String toString() {
           this.nimi = (this.kotijoukkue.toString() + " - " + this.vierasjoukkue.toString());
        return nimi;
    }

    public int annaKierros() {
        return kierros;
    }

    public int annaKotimaalit() {
        return kotimaalit;
    }

    public int annaVierasmaalit() {
        return vierasmaalit;
    }

    public void asetaKierros(int kierros) {
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

    public void asetaTunnit(String tunnit) {
        kellotunnit = tunnit;
        this.kello = kellotunnit + " : " + kellominuutit;
        asetaTaulukkokello();
    }

    public void asetaMinuutit(String minuutit) {
        kellominuutit = minuutit;
        this.kello = kellotunnit + " : " + kellominuutit;
        asetaTaulukkokello();
    }

    public void asetaKotimaalit(int kotimaalit){
        this.kotimaalit = kotimaalit;
        
    }
    
     public void asetaVierasmaalit(int vierasmaalit){
        this.vierasmaalit = vierasmaalit;
        
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
        this.nimi = (this.kotijoukkue.toString() + " - " + this.vierasjoukkue.toString());
        koti_kokoonpano = new Kokoonpano(this, this.kotijoukkue);
        vieras_kokoonpano = new Kokoonpano(this, this.vierasjoukkue);
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

    public StringProperty taulukkotunnitProperty() {
        return taulukkotunnit;
    }

    public void asetaTaulukkotunnit() {
        this.taulukkotunnit = new SimpleStringProperty(this.annaTunnit());
    }

    public String annaTunnit() {
        return kellotunnit;
    }

    public StringProperty taulukkominuutitProperty() {
        return taulukkominuutit;
    }

    public void asetaTaulukkominuutit() {
        this.taulukkominuutit = new SimpleStringProperty(this.annaMinuutit());
    }

    public String annaMinuutit() {
        return kellominuutit;
    }

 
    public ObjectProperty taulukkopaivaProperty() {
        return taulukkopaiva;
    }

    public void asetaTaulukkopaiva() {
        
        this.taulukkopaiva = new SimpleObjectProperty(this.annaPaivaDate());
    }

    private LocalDate getDate() {
            return paiva_date == null ? LocalDate.now() : paiva_date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        }
    
    public Date annaPaivaDate() {

        return paiva_date;
    }

    public void asetaTaulukkopaivastring(){
        this.taulukkopaivastring = new SimpleStringProperty(getDate().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)));
    }
    
       public StringProperty taulukkopaivastringProperty() {
        return taulukkopaivastring;
    }
    
    
    public void asetaPaivaDate(Date paiva) {
        this.paiva_date = paiva;
        asetaTaulukkopaiva();
    }

    public StringProperty taulukkokelloProperty() {
        return taulukkokello;
    }

    public void asetaTaulukkokello() {
        this.taulukkokello = new SimpleStringProperty(this.annaKello());
        asetaTaulukkominuutit();
        asetaTaulukkotunnit();
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

    public void asetaTaulukkokotijoukkue() {
        this.taulukkokotijoukkue = new SimpleObjectProperty(this.annaKotijoukkue());
    }

    public void asetaTaulukkovierasjoukkue() {
        this.taulukkovierasjoukkue = new SimpleObjectProperty(this.annaVierasjoukkue());
    }

    public ObjectProperty taulukkoerotuomariProperty() {
        return taulukkoerotuomari;
    }

    public ObjectProperty taulukkoavustava1Property() {
        return taulukkoavustava1;
    }

    public ObjectProperty taulukkoavustava2Property() {
        return taulukkoavustava2;
    }

    public ObjectProperty taulukkokotijoukkueProperty() {
        return taulukkokotijoukkue;
    }

    public ObjectProperty taulukkovierasjoukkueProperty() {
        return taulukkovierasjoukkue;
    }

    public void asetaKotijoukkue(Joukkue kotijoukkue) {
        this.kotijoukkue = kotijoukkue;
        asetaTaulukkokotijoukkue();
    }

    public void asetaVierasjoukkue(Joukkue vierasjoukkue) {
        this.vierasjoukkue = vierasjoukkue;
        asetaTaulukkovierasjoukkue();
    }

    public void asetaTaulukkotuomarit() {

        for (int i = 0; i < this.annaRoolit().size(); i++) {

            if (this.annaRoolit().get(i).annaRooli().equals("Erotuomari")) {
                this.taulukkoerotuomari = new SimpleObjectProperty(this.annaRoolit().get(i).annaTuomari());
            } else if (this.annaRoolit().get(i).annaRooli().equals("1. Avustava erotuomari")) {
                this.taulukkoavustava1 = new SimpleObjectProperty(this.annaRoolit().get(i).annaTuomari());
            } else if (this.annaRoolit().get(i).annaRooli().equals("2. Avustava erotuomari")) {
                this.taulukkoavustava2 = new SimpleObjectProperty(this.annaRoolit().get(i).annaTuomari());
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

    public void asetaErotuomari(Tuomari uusierotuomari) {

        //poistetaan vanha
        Tuomari erotuomari = new Tuomari();

        for (int i = 0; i < this.annaRoolit().size(); i++) {

            if (this.annaRoolit().get(i).annaRooli().equals("Erotuomari")) {
                erotuomari = this.annaRoolit().get(i).annaTuomari();
            }
        }

        this.annaRoolit().remove(erotuomari);

        TuomarinRooli erotuomariR = new TuomarinRooli(uusierotuomari, this);
        erotuomariR.asetaRooli("Erotuomari");

        this.annaRoolit().add(erotuomariR);
        uusierotuomari.annaTuomarinRoolit().add(erotuomariR);

        this.asetaTaulukkotuomarit();
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

    public void asetaAvustava1(Tuomari uusiavustava) {

        //poistetaan vanha
        Tuomari avustava = new Tuomari();

        for (int i = 0; i < this.annaRoolit().size(); i++) {

            if (this.annaRoolit().get(i).annaRooli().equals("1. Avustava erotuomari")) {
                avustava = this.annaRoolit().get(i).annaTuomari();
            }
        }

        this.annaRoolit().remove(avustava);

        TuomarinRooli erotuomariR = new TuomarinRooli(uusiavustava, this);
        erotuomariR.asetaRooli("1. Avustava erotuomari");

        this.annaRoolit().add(erotuomariR);
        uusiavustava.annaTuomarinRoolit().add(erotuomariR);

        this.asetaTaulukkotuomarit();
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

    public void asetaAvustava2(Tuomari uusiavustava) {

        //poistetaan vanha
        Tuomari avustava = new Tuomari();

        for (int i = 0; i < this.annaRoolit().size(); i++) {

            if (this.annaRoolit().get(i).annaRooli().equals("2. Avustava erotuomari")) {
                avustava = this.annaRoolit().get(i).annaTuomari();
            }
        }

        this.annaRoolit().remove(avustava);

        TuomarinRooli erotuomariR = new TuomarinRooli(uusiavustava, this);
        erotuomariR.asetaRooli("2. Avustava erotuomari");

        this.annaRoolit().add(erotuomariR);
        uusiavustava.annaTuomarinRoolit().add(erotuomariR);

        this.asetaTaulukkotuomarit();
    }
}
