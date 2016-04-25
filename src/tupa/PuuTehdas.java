package tupa;

import java.util.ArrayList;
import java.util.List;
import javafx.event.EventHandler;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.HBox;

public class PuuTehdas {

    private List<Sarja> sarjatk;
    private List<Joukkue> joukkuetk;
    private List<Pelaaja> pelaajatk;
    private List<Tuomari> tuomaritk;
    private List<Valmentaja> valmentajatk;
    private List<Joukkueenjohtaja> jojotk;

    public PuuTehdas() {
    }

    public PuuTehdas(List<Sarja> sarjat, List<Joukkue> joukkueet, List<Pelaaja> pelaajat, List<Tuomari> tuomarit , List<Valmentaja> valkut, List<Joukkueenjohtaja> jojot) {
        this.sarjatk = sarjat;
        this.joukkuetk = joukkueet;
        this.pelaajatk = pelaajat;
         this.tuomaritk = tuomarit;
          this.valmentajatk = valkut;
           this.jojotk = jojot;
    }
    // metodi, joka luo ArrayList:n TreeItemsista (Sarjat)

    public ArrayList<TreeItem<Kohde>> getSarjat() {

        ArrayList<TreeItem<Kohde>> sarjat = new ArrayList<TreeItem<Kohde>>();

        String nimi = "";

        if(!sarjatk.isEmpty()){
             for (int i = 0; i < sarjatk.size(); i++) {
            nimi = sarjatk.get(i).toString();
            Kohde s = new Sarja(nimi);
            TreeItem ts1 = new TreeItem(s);
            sarjat.add(ts1);

        }
            
        }
       

        return sarjat;
    }

    // metodi, joka luo ArrayList:n TreeItemsista (Joukkueet)
    public ArrayList<TreeItem<Kohde>> getJoukkueet() {
        ArrayList<TreeItem<Kohde>> joukkueet = new ArrayList<TreeItem<Kohde>>();

        String nimi = "";

        if(!joukkuetk.isEmpty()){
            
               for (int i = 0; i < joukkuetk.size(); i++) {
            nimi = joukkuetk.get(i).toString();
            Kohde s = new Joukkue(nimi);
            TreeItem ts1 = new TreeItem(s);
            joukkueet.add(ts1);
        } 
            
        }
    

        return joukkueet;
    }

    // metodi, joka luo ArrayList:n TreeItemsista (Pelaajat)
    public ArrayList<TreeItem<Kohde>> getPelaajat() {
        ArrayList<TreeItem<Kohde>> pelaajat = new ArrayList<TreeItem<Kohde>>();

        String nimi = "";
  if(!pelaajatk.isEmpty()){
      
          for (int i = 0; i < pelaajatk.size(); i++) {
            nimi = pelaajatk.get(i).toString();
            Kohde s = new Pelaaja(nimi);
            TreeItem ts1 = new TreeItem(s);
            pelaajat.add(ts1);
        } 
      
  }
   

        return pelaajat;
    }

    public ArrayList<TreeItem<Kohde>> getTuomarit() {
        ArrayList<TreeItem<Kohde>> tuomarit = new ArrayList<TreeItem<Kohde>>();
String nimi = "";
     if(!tuomaritk.isEmpty()){
      
          for (int i = 0; i < tuomaritk.size(); i++) {
            nimi = tuomaritk.get(i).toString();
            Kohde s = new Tuomari(nimi);
            TreeItem ts1 = new TreeItem(s);
            tuomarit.add(ts1);
        } 
      

       
    }
      return tuomarit;
    }
    
    public ArrayList<TreeItem<Kohde>> getValmentajat() {

        ArrayList<TreeItem<Kohde>> valmentajat = new ArrayList<TreeItem<Kohde>>();

       String nimi = "";
           if(!valmentajatk.isEmpty()){
      
          for (int i = 0; i < valmentajatk.size(); i++) {
            nimi = valmentajatk.get(i).toString();
            Kohde s = new Valmentaja(nimi);
            TreeItem ts1 = new TreeItem(s);
            valmentajat.add(ts1);
        } 
      
    }
         return valmentajat;    
    }
    
    public ArrayList<TreeItem<Kohde>> getJoukkueenjohtajat() {

        ArrayList<TreeItem<Kohde>> jojot = new ArrayList<TreeItem<Kohde>>();

     
       String nimi = "";
           if(!jojotk.isEmpty()){
      
          for (int i = 0; i < jojotk.size(); i++) {
            nimi = jojotk.get(i).toString();
            Kohde s = new Joukkueenjohtaja(nimi);
            TreeItem ts1 = new TreeItem(s);
            jojot.add(ts1);
        } 

      
    }
           
            return jojot; 
    }
}
