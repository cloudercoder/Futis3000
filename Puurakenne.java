/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tupa;

import java.util.ArrayList;
import javafx.event.EventHandler;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

/**
 *
 * @author Omistaja
 */
public class Puurakenne {

    private Tupa ikkuna;
    private Kasittelija kasittelija;

    Puurakenne() {

    }

    Puurakenne(Tupa ikkuna) {

        this.ikkuna = ikkuna;
        kasittelija = new Kasittelija(ikkuna);
    }

    public TreeView<Kohde> rakennaPuu() {

        PuuTehdas puutehdas = new PuuTehdas(ikkuna);

        ArrayList<TreeItem<Kohde>> sarjat = puutehdas.getSarjat();
        ArrayList<TreeItem<Kohde>> tuomarit = puutehdas.getTuomarit();

        Kohde rootS = new Sarja("Sarjat", (Turnaus) ikkuna.annaTurnaus());

        TreeItem<Kohde> rs = new TreeItem<>(rootS);
        rs.getChildren().addAll(sarjat);
        ikkuna.asetaRootSarjat(rs);

        //tehdään keinotekonen väli
        Kohde vali = new Kohde("");
        TreeItem<Kohde> keinoVali = new TreeItem<>(vali);

        Kohde rootT = new Tuomari("Tuomarit");

        TreeItem<Kohde> rt = new TreeItem<>(rootT);
        rt.getChildren().addAll(tuomarit);
        ikkuna.asetaRootTuomarit(rt);

        // näkymätön juuri
        Kohde rootK = new Kohde("Menu");
        TreeItem<Kohde> rootSivuPuu = new TreeItem<>(rootK);
        rootSivuPuu.getChildren().addAll(rs, keinoVali, rt);

        TreeView<Kohde> sivuPuu = new TreeView<>();
        sivuPuu.setRoot(rootSivuPuu);

        // seuraavat käsittelee tapahtumia, kun käyttäjä klikkaa sivuvalikon kohteita
        rs.addEventHandler(TreeItem.<Kohde>branchExpandedEvent(), new EventHandler<TreeItem.TreeModificationEvent<Kohde>>() {
            @Override
            public void handle(TreeItem.TreeModificationEvent<Kohde> event) {
                kasittelija.branchExpended(event);
            }
        });

        rs.addEventHandler(TreeItem.<Kohde>branchCollapsedEvent(), new EventHandler<TreeItem.TreeModificationEvent<Kohde>>() {
            @Override
            public void handle(TreeItem.TreeModificationEvent<Kohde> event) {
                kasittelija.branchCollapsed(event);
            }
        });

        rt.addEventHandler(TreeItem.<Kohde>branchExpandedEvent(), new EventHandler<TreeItem.TreeModificationEvent<Kohde>>() {
            @Override
            public void handle(TreeItem.TreeModificationEvent<Kohde> event) {
                kasittelija.branchExpended(event);
            }
        });

        rt.addEventHandler(TreeItem.<Kohde>branchCollapsedEvent(), new EventHandler<TreeItem.TreeModificationEvent<Kohde>>() {
            @Override
            public void handle(TreeItem.TreeModificationEvent<Kohde> event) {
                kasittelija.branchCollapsed(event);
            }
        });

        return sivuPuu;

    }

}
