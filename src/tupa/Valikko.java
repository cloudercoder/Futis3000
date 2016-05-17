/*
Yläpalkin valikon muodostava luokka 
 */
package tupa;

import java.util.ArrayList;
import java.util.List;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TreeItem;
import javafx.scene.input.KeyCharacterCombination;
import javafx.scene.input.KeyCombination;

/**
 *
 * @author Marianne
 */
public class Valikko implements EventHandler<ActionEvent> {

    private MenuBar menuBar;
    private Tupa ikkuna;
    private Tiedottaja tiedottaja;
    private PaaNakyma nakyma;

    public Valikko() {

    }

    public Valikko(MenuBar menu, Tupa ikkuna) {

        this.menuBar = menu;
        this.ikkuna = ikkuna;
        tiedottaja = new Tiedottaja(ikkuna);
        nakyma = new PaaNakyma(ikkuna);
    }

    public MenuBar rakennaValikko() {

        Menu menuTiedosto = new Menu("Tiedosto");
        MenuItem uusi = new MenuItem("Uusi");
        uusi.setAccelerator(new KeyCharacterCombination("N", KeyCombination.SHORTCUT_DOWN));

        MenuItem avaa = new MenuItem("Avaa");
        avaa.setAccelerator(new KeyCharacterCombination("O", KeyCombination.SHORTCUT_DOWN));

        MenuItem tallenna = new MenuItem("Tallenna");
        tallenna.setAccelerator(new KeyCharacterCombination("S", KeyCombination.SHORTCUT_DOWN));

        MenuItem vie = new MenuItem("Vie");
        vie.setAccelerator(new KeyCharacterCombination("E", KeyCombination.SHORTCUT_DOWN));

        MenuItem tuo = new MenuItem("Tuo");
        tuo.setAccelerator(new KeyCharacterCombination("I", KeyCombination.SHORTCUT_DOWN));

        MenuItem lopeta = new MenuItem("Lopeta");
        lopeta.setAccelerator(new KeyCharacterCombination("Q", KeyCombination.SHORTCUT_DOWN));

        uusi.setOnAction(this);
        avaa.setOnAction(this);
        tallenna.setOnAction(this);
        vie.setOnAction(this);
        tuo.setOnAction(this);

        lopeta.setOnAction(this);

        menuTiedosto.getItems().addAll(uusi, avaa, tallenna, vie, tuo, new SeparatorMenuItem(), lopeta);

        Menu menuOhje = new Menu("Ohje");
        MenuItem ohje = new MenuItem("Ohje");
        MenuItem tietoa = new MenuItem("Tietoa ohjelmasta");
        ohje.setAccelerator(new KeyCharacterCombination("F8"));
        tietoa.setAccelerator(new KeyCharacterCombination("F9"));
        ohje.setOnAction(this);
        tietoa.setOnAction(this);
        menuOhje.getItems().addAll(ohje, new SeparatorMenuItem(), tietoa);

        menuBar.getMenus().addAll(menuTiedosto, menuOhje);

        return menuBar;
    }

    @Override
    public void handle(ActionEvent e) {
        Object lahde = e.getSource();

        if (lahde instanceof MenuItem) {

            valikostaValittu(
                    ((MenuItem) lahde).getText(), e);
            return;
        }

    }

    public void valikostaValittu(String teksti, ActionEvent e) {

        switch (teksti) {
            case "Uusi": {
                //tsekataan ensin, onko käyttäjä tehnyt muutoksia
                if (ikkuna.muutettu()) {
                    Varmistaja varmista = new Varmistaja(ikkuna.annaKohteet(), ikkuna);
                    varmista.annaUudenVarmistus();
                } else {
                    //tyhjennetään kaikki tiedot 
                    ikkuna.annaKohteet().clear();

                    Kohde uusiTurnaus = new Turnaus();
                    ikkuna.asetaTurnaus(uusiTurnaus);
                    ikkuna.annaKohteet().add(uusiTurnaus);

                    //vielä pitää tyhjentää puu
                    TreeItem<Kohde> parentSarjat = ikkuna.annaRootSarjat();
                    TreeItem<Kohde> parentTuomarit = ikkuna.annaRootTuomarit();
                    parentSarjat.getChildren().clear();
                    parentTuomarit.getChildren().clear();
                    nakyma.luoEtusivu();
                }

                break;
            }
            case "Avaa": {
                //tsekataan ensin, onko käyttäjä tehnyt muutoksia

                if (ikkuna.muutettu()) {
                    Varmistaja varmista = new Varmistaja(ikkuna.annaKohteet(), ikkuna);
                    varmista.annaUudenVarmistus();
                } else {
                    //tyhjennetään kaikki tiedot 
                    ikkuna.annaKohteet().clear();

           

                    //vielä pitää tyhjentää puu
                    TreeItem<Kohde> parentSarjat = ikkuna.annaRootSarjat();
                    TreeItem<Kohde> parentTuomarit = ikkuna.annaRootTuomarit();
                    parentSarjat.getChildren().clear();
                    parentTuomarit.getChildren().clear();

                    //sitten vasta avaukseen
                    //tuodaan tallennetut kohteet
                    List<Kohde> kohdetk = new ArrayList<>();
                    Avaus avaaja = new Avaus();
                    kohdetk = avaaja.avaa();

                    ikkuna.asetaKohteet(kohdetk);
 
                    List<Sarja> sarjatk = new ArrayList<>();

                    List<Tuomari> tuomaritk = new ArrayList<>();
                    List<Joukkue> joukkuetk = new ArrayList<>();
                    List<Pelaaja> pelaajatk = new ArrayList<>();
                    List<Toimihenkilo> toimaritk = new ArrayList<>();
                    //viedään kohteet omiin listoihin
                    TreeItem<Kohde> parent = new TreeItem<>();
                    for (int i = 0; i < kohdetk.size(); i++) {

                        if (kohdetk.get(i) instanceof Sarja) {
                            Sarja sarja = (Sarja) kohdetk.get(i);

                            sarjatk.add(sarja);
                            ikkuna.annaSarjatk().add(sarja);

                            parent = ikkuna.annaRootSarjat();
                            TreeItem<Kohde> newItem = new TreeItem<Kohde>(kohdetk.get(i));
                            parent.getChildren().add(newItem);

                        } else if (kohdetk.get(i) instanceof Tuomari) {
                            Tuomari tuomari = (Tuomari) kohdetk.get(i);
                            tuomaritk.add(tuomari);
                            ikkuna.annaTuomaritk().add(tuomari);

                            parent = ikkuna.annaRootTuomarit();
                            TreeItem<Kohde> uusiKohde = new TreeItem<Kohde>(kohdetk.get(i));
                            parent.getChildren().add(uusiKohde);
                        } else if (kohdetk.get(i) instanceof Joukkue) {
                            Joukkue joukkue = (Joukkue) kohdetk.get(i);
                            joukkuetk.add(joukkue);
                            ikkuna.annaJoukkuetk().add(joukkue);

                            joukkue.asetaTaulukkonimi();
                        } else if (kohdetk.get(i) instanceof Pelaaja) {
                            Pelaaja pelaaja = (Pelaaja) kohdetk.get(i);
                            pelaajatk.add(pelaaja);
                            ikkuna.annaPelaajatk().add(pelaaja);

                        } else if (kohdetk.get(i) instanceof Toimihenkilo) {
                            Toimihenkilo toimari = (Toimihenkilo) kohdetk.get(i);
                            toimaritk.add(toimari);
                            ikkuna.annaToimaritk().add(toimari);

                            toimari.asetaTaulukkonimi();
                            toimari.asetaTaulukkosposti();
                            toimari.asetaTaulukkopuh();
                            toimari.asetaTaulukkorooli();
                        } else if (kohdetk.get(i) instanceof Turnaus) {

                            ikkuna.asetaTurnaus(kohdetk.get(i));

                        }
                    }
                }

                tiedottaja.kirjoitaLoki("Tiedosto avattu.");
                nakyma.luoEtusivu();
                break;
            }

            case "Tallenna": {
                Tallennus tallenna = new Tallennus(ikkuna);
                tallenna.suoritaTallennus();
                tiedottaja.kirjoitaLoki("Tallennus onnistui.");
                break;
            }
                

            case "Lopeta": {

                if (ikkuna.muutettu()) {

                    Varmistaja varmista = new Varmistaja(ikkuna.annaKohteet(), ikkuna);
                    varmista.annaVarmistus();
                } else {
                    Platform.exit();
                }
                break;
            }
            case "Ohje": {

                Ohjeistus ohje = new Ohjeistus();
                ohje.annaYleisOhje();
                break;
            }
            case "Tietoa ohjelmasta": {

                Ohjeistus ohje = new Ohjeistus();
                ohje.annaTietoa();
                 break;
            }

      
        }
    }
}
