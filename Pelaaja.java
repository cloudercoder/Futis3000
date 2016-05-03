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
    // välilät 1-99
    private int pelinumero;
    private String pelipaikka;
    private transient IntegerProperty taulukkonumero = new SimpleIntegerProperty();
    private transient StringProperty taulukkopelipaikka = new SimpleStringProperty();
    private transient StringProperty taulukkonimi = new SimpleStringProperty();
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

    public int annaPelaajaMaara() {
        return peLaskuri;
    }

    public int annaJulkinenID() {
        return id_julkinen;
    }

    public Joukkue annaJoukkue(){
        return joukkue;
    }
    
    public void asetaJoukkue(Joukkue joukkue){
        this.joukkue = joukkue;
    }
    
    public List<Kokoonpano> annaKokoonpanot(){
        return kokoonpanot;
    }
    
    public List<Maali> annaMaalit(){
        return maalit;
    }
    
    public void asetaPelinumero(int pelinumero){
        this.pelinumero = pelinumero;
    }
    
    public int annaPelinumero(){
        return pelinumero;
    }
    
    public void asetaPelipaikka(String pelipaikka){
        this.pelipaikka = pelipaikka;
    }
    
    public String annaPelipaikka(){
        return pelipaikka;
    }
    
    public IntegerProperty taulukkonumeroProperty() {
        return taulukkonumero;
    }

    public void asetaTaulukkonumero() {
        this.taulukkonumero = new SimpleIntegerProperty(this.annaPelinumero());
    }
    
    public StringProperty taulukkopelipaikkaProperty() {
        return taulukkopelipaikka;
    }

    public void asetaTaulukkopelipaikka() {
        this.taulukkopelipaikka = new SimpleStringProperty(this.annaPelipaikka());
    }
    
    public String annaTaulukkopelipaikka(){
        return taulukkopelipaikka.get();
    }
   public StringProperty taulukkonimiProperty() {
        return taulukkonimi;
    }

    public void asetaTaulukkonimi() {
        this.taulukkonimi = new SimpleStringProperty(this.annaKokoNimi());
    }
    
    
    
}
