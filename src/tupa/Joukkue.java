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
public class Joukkue extends Kohde {

    private static int joLaskuri;

    private int julkinenID;

    private transient StringProperty taulukkonimi = new SimpleStringProperty();
    private transient IntegerProperty taulukko_ottelut = new SimpleIntegerProperty();
    private transient IntegerProperty taulukkovoitot = new SimpleIntegerProperty();
    private transient IntegerProperty taulukkohaviot = new SimpleIntegerProperty();
    private transient IntegerProperty taulukkotasapelit = new SimpleIntegerProperty();
    private transient IntegerProperty taulukkopisteet = new SimpleIntegerProperty();
    private transient IntegerProperty taulukkosijoitus = new SimpleIntegerProperty();
    private transient StringProperty taulukkomaalisuhde = new SimpleStringProperty();

    
    
    private Sarja sarja;
    private List<Ottelu> ottelut = new ArrayList<Ottelu>();
    private List<Toimihenkilo> toimarit = new ArrayList<Toimihenkilo>();
    private List<Pelaaja> pelaajat = new ArrayList<Pelaaja>();
    private List<Integer> voitot = new ArrayList<Integer>();
    private List<Integer> tasapelit = new ArrayList<Integer>();
    private List<Integer> haviot = new ArrayList<Integer>();
    private List<Integer> tehdyt = new ArrayList<Integer>();
    private List<Integer> paastetyt = new ArrayList<Integer>();
  

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

    public int annaPelatutOttelut(){
        int maara = 0;
      
        for(int i=0; i<ottelut.size(); i++){
            if(ottelut.get(i).annaTulos().equals("-")){
                maara = maara;
            }
            else{
                maara = maara + 1;
            }
           
        }
        
        return maara;
    }
    public IntegerProperty taulukko_ottelutProperty() {
        return taulukko_ottelut;
    }

    public void asetaTaulukko_ottelut() {
        this.taulukko_ottelut = new SimpleIntegerProperty(this.annaPelatutOttelut());
    }

    public void asetaTaulukkovoitot() {
        this.taulukkovoitot = new SimpleIntegerProperty(this.annaVoitot().size());
    }

    public IntegerProperty taulukkovoitotProperty() {
        return taulukkovoitot;
    }

    public void asetaTaulukkohaviot() {
        this.taulukkohaviot = new SimpleIntegerProperty(this.annaHaviot().size());
    }

    public IntegerProperty taulukkohaviotProperty() {
        return taulukkohaviot;
    }

    public void asetaTaulukkotasapelit() {
        this.taulukkotasapelit = new SimpleIntegerProperty(this.annaTasapelit().size());
    }

    public IntegerProperty taulukkotasapelitProperty() {
        return taulukkotasapelit;
    }

    public void asetaTaulukkomaalisuhde() {
        this.taulukkomaalisuhde = new SimpleStringProperty(this.annaMaalisuhde());
    }

    public StringProperty taulukkomaalisuhdeProperty() {
        return taulukkomaalisuhde;
    }

    public void asetaTaulukkopisteet() {
        this.taulukkopisteet = new SimpleIntegerProperty(this.annaPisteet());
    }

    public IntegerProperty taulukkopisteetProperty() {
        return taulukkopisteet;
    }

    public void asetaTaulukkosijoitus(int i) {
        this.taulukkosijoitus = new SimpleIntegerProperty(i);
    }

    public IntegerProperty taulukkosijoitusProperty() {
        return taulukkosijoitus;
    }

    public List<Integer> annaVoitot() {
        return voitot;
    }

    public List<Integer> annaHaviot() {
        return haviot;
    }

    public List<Integer> annaTasapelit() {
        return tasapelit;
    }

    public int annaPisteet() {
        return ((3 * voitot.size()) + (1 * tasapelit.size()));
    }
    
    public List<Integer> annaTehdyt(){
        return tehdyt;
    }
     public List<Integer> annaPaastetyt(){
        return paastetyt;
    }
    
    
    public String annaMaalisuhde() {      
      int tehdyt_maalit = 0;
      
      for(int i=0; i<tehdyt.size();i++){
          tehdyt_maalit = tehdyt_maalit + tehdyt.get(i);
      }
      
      int paastetyt_maalit = 0;
      
      for(int i=0; i<paastetyt.size();i++){
          paastetyt_maalit = paastetyt_maalit + paastetyt.get(i);
      }
      
      return tehdyt_maalit + " : " + paastetyt_maalit;
    }
    
    public int annaMaaliero() {      
      int tehdyt_maalit = 0;
      
      for(int i=0; i<tehdyt.size();i++){
          tehdyt_maalit = tehdyt_maalit + tehdyt.get(i);
      }
      
      int paastetyt_maalit = 0;
      
      for(int i=0; i<paastetyt.size();i++){
          paastetyt_maalit = paastetyt_maalit + paastetyt.get(i);
      }
      
      return tehdyt_maalit - paastetyt_maalit;
    }
   
    public void asetaTehdyt(int tehdyt_maalit){
        this.tehdyt.add(tehdyt_maalit);
    }
   public void asetaPaastetyt(int paastetyt_maalit){
        this.paastetyt.add(paastetyt_maalit);
    }
}
