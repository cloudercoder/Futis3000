
package tupa;

import java.io.Serializable;
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
    private Joukkue koti;
    private Joukkue vieras;
    private int kotimaalit;
    private int vierasmaalit;
    private String tulos;
    private String nimi;
    private Sarja sarja;
    private String aika;
    private String paikka;    
    private List<Maali> maalit = new ArrayList<>();
    private List<TuomarinRooli> roolit = new ArrayList<>();
    
    
    private transient IntegerProperty taulukkoid = new SimpleIntegerProperty();
    private transient StringProperty taulukkopaikka = new SimpleStringProperty();
    private transient StringProperty taulukkonimi = new SimpleStringProperty();
    private transient StringProperty taulukkotulos = new SimpleStringProperty();
    private transient StringProperty taulukkoaika = new SimpleStringProperty();
    private transient StringProperty taulukkoerotuomari = new SimpleStringProperty();
     private transient StringProperty taulukkoavustava1 = new SimpleStringProperty();
    private transient StringProperty taulukkoavustava2 = new SimpleStringProperty(); 
    
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

    public String toString() {
        return nimi;
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
        tulos = (koti + " - " + vieras);
    }

    public String annaTulos() {
        return tulos;
    }

    public void asetaJoukkueet(Joukkue koti, Joukkue vieras) {

        this.koti = koti;
        this.vieras = vieras;
         this.nimi = (koti.toString() + " - " + vieras.toString());
    }

    public Joukkue annaKoti() {
        return koti;
    }

    public Joukkue annaVieras() {
        return vieras;
    }

    public void asetaAika(String aika) {

        this.aika = aika;
    }

    public void asetaPaikka(String paikka) {

        this.paikka = paikka;
    }

    public String annaAika() {

        return aika;
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
    
     public StringProperty taulukkoaikaProperty() {
        return taulukkoaika;
    }

    public void asetaTaulukkoaika() {
        this.taulukkoaika = new SimpleStringProperty(this.annaAika());
    }
    
     public IntegerProperty taulukkoidProperty() {
        return taulukkoid;
    }

    public void asetaTaulukkoid() {
        this.taulukkoid = new SimpleIntegerProperty(this.annaOtteluNumero());
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
        
        for(int i=0; i < this.annaRoolit().size(); i++){
            
            if(this.annaRoolit().get(i).annaRooli().equals("Erotuomari")){
                this.taulukkoerotuomari = new SimpleStringProperty(this.annaRoolit().get(i).annaTuomari().annaKokoNimi());
            }
            else if(this.annaRoolit().get(i).annaRooli().equals("1. Avustava erotuomari")){
                this.taulukkoavustava1 = new SimpleStringProperty(this.annaRoolit().get(i).annaTuomari().annaKokoNimi());
            }
             else if(this.annaRoolit().get(i).annaRooli().equals("2. Avustava erotuomari")){
                this.taulukkoavustava2 = new SimpleStringProperty(this.annaRoolit().get(i).annaTuomari().annaKokoNimi());
            }
        }
        
    }
   
    
    public List<TuomarinRooli> annaRoolit(){
        return roolit;
    }
}
