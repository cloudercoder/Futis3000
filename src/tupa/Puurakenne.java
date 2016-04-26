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
    
    private TUPA ikkuna;
    private Kasittelija kasittelija;
    
    Puurakenne(){
        
    }
    
    Puurakenne(TUPA ikkuna){
        
        this.ikkuna = ikkuna;
        kasittelija = new Kasittelija(ikkuna);
    }
    
    public TreeView<Kohde> rakennaPuu(){
        
        PuuTehdas puutehdas = new PuuTehdas(ikkuna.annaSarjatk(), ikkuna.annaJoukkuetk(), ikkuna.annaPelaajatk(), ikkuna.annaTuomaritk(), ikkuna.annaValmentajatk(), ikkuna.annaJojotk());

        ArrayList<TreeItem<Kohde>> sarjat = puutehdas.getSarjat();
        ArrayList<TreeItem<Kohde>> joukkueet = puutehdas.getJoukkueet();
        ArrayList<TreeItem<Kohde>> pelaajat = puutehdas.getPelaajat();
        ArrayList<TreeItem<Kohde>> tuomarit = puutehdas.getTuomarit();
        ArrayList<TreeItem<Kohde>> valmentajat = puutehdas.getValmentajat();
        ArrayList<TreeItem<Kohde>> joukkueenjohtajat = puutehdas.getJoukkueenjohtajat();

        Kohde rootS = new Sarja("Sarjat");
        
        TreeItem<Kohde> rs = new TreeItem<>(rootS);
        rs.getChildren().addAll(sarjat); 
        ikkuna.setRootSarjat(rs);
        
    
        //tehdään keinotekonen väli
        Kohde v1 = new Kohde("");
        TreeItem<Kohde> vikaSarjat = new TreeItem<>(v1);

        Kohde rootJ = new Joukkue("Joukkueet");
        
        TreeItem<Kohde> rj = new TreeItem<>(rootJ);
        rj.getChildren().addAll(joukkueet); 
        ikkuna.setRootJoukkueet(rj);

        //tehdään keinotekonen väli
        Kohde v2 = new Kohde("");
        TreeItem<Kohde> vikaJoukkueet = new TreeItem<>(v2);

        Kohde rootP = new Pelaaja("Pelaajat");
        
        TreeItem<Kohde> rp = new TreeItem<>(rootP);
        rp.getChildren().addAll(pelaajat); 
        ikkuna.setRootPelaajat(rp);

        //tehdään keinotekonen väli
        Kohde v3 = new Kohde("");
        TreeItem<Kohde> vikaP = new TreeItem<>(v3);

        Kohde rootT = new Tuomari("Tuomarit");
        
        TreeItem<Kohde> rt = new TreeItem<>(rootT);
        rt.getChildren().addAll(tuomarit); 
        ikkuna.setRootTuomarit(rt);

        //tehdään keinotekonen väli
        Kohde v4 = new Kohde("");
        TreeItem<Kohde> vikaT = new TreeItem<>(v4);

        Kohde rootTo = new Toimihenkilo("Toimihenkilöt");
        
        TreeItem<Kohde> rto = new TreeItem<>(rootTo);

        Kohde rootTo1 = new Valmentaja("Valmentajat");
        
        TreeItem<Kohde> rto1 = new TreeItem<>(rootTo1);

        Kohde rootTo2 = new Joukkueenjohtaja("Joukkueenjohtajat");
        
        TreeItem<Kohde> rto2 = new TreeItem<>(rootTo2);

        rto1.getChildren().addAll(valmentajat);
        rto2.getChildren().addAll(joukkueenjohtajat);
        rto.getChildren().addAll(rto1, rto2);

        ikkuna.setRootToimarit(rto);
        ikkuna.setRootToimarit1(rto1);
        ikkuna.setRootToimarit2(rto2);
        
        // näkymätön juuri
        Kohde rootK = new Kohde("Menu");
        TreeItem<Kohde> rootSivuPuu = new TreeItem<>(rootK);
        rootSivuPuu.getChildren().addAll(rs, vikaSarjat, rj, vikaJoukkueet, rp, vikaP, rt, vikaT, rto);

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

        rj.addEventHandler(TreeItem.<Kohde>branchExpandedEvent(), new EventHandler<TreeItem.TreeModificationEvent<Kohde>>() {
            @Override
            public void handle(TreeItem.TreeModificationEvent<Kohde> event) {
                kasittelija.branchExpended(event);
            }
        });

        rj.addEventHandler(TreeItem.<Kohde>branchCollapsedEvent(), new EventHandler<TreeItem.TreeModificationEvent<Kohde>>() {
            @Override
            public void handle(TreeItem.TreeModificationEvent<Kohde> event) {
                kasittelija.branchCollapsed(event);
            }
        });

        rp.addEventHandler(TreeItem.<Kohde>branchExpandedEvent(), new EventHandler<TreeItem.TreeModificationEvent<Kohde>>() {
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
