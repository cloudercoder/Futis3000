/*
Luokka, joka lisää ja poistaa eri kohteita
 */
package tupa;

import javafx.scene.control.TreeItem;

/**
 *
 * @author Marianne
 */
public class Muuttaja {

    private Tupa ikkuna;
    private Tiedottaja tiedottaja;

    Muuttaja() {

    }

    Muuttaja(Tupa ikkuna) {
        this.ikkuna = ikkuna;
        tiedottaja = new Tiedottaja(ikkuna);
    }

    public void lisaaKohde(Kohde arvo) {

        if (arvo instanceof Sarja) {
            // tallennetaan turnaus, johon kuuluu
            Sarja sarja = (Sarja) arvo;
            sarja.asetaTurnaus((Turnaus) ikkuna.annaTurnaus());
        } else if (arvo instanceof Tuomari) {
            // tallennetaan turnaus, johon kuuluu
            Tuomari tuomari = (Tuomari) arvo;
            Turnaus turnaus = (Turnaus) ikkuna.annaTurnaus();
            tuomari.asetaTurnaus(turnaus);
            //tallennetaan tuomari turnauksen tietoihin, sarjoille tämä on jo tehty muodostimen yhteydessä
            turnaus.annaTuomarit().add(tuomari);

        }

        TreeItem<Kohde> parent = new TreeItem<>();

        if (arvo instanceof Sarja) {
            parent = ikkuna.annaRootSarjat();
        } else if (arvo instanceof Tuomari) {
            parent = ikkuna.annaRootTuomarit();
        }

        for (TreeItem<Kohde> child : parent.getChildren()) {
            if (child.getValue().toString().equals(arvo.toString())) {

                if (arvo instanceof Sarja) {
                    tiedottaja.kirjoitaLoki("Tämänniminen sarja on jo olemassa ");
                }

                return;
            }
        }

        TreeItem<Kohde> newItem = new TreeItem<Kohde>(arvo);
        parent.getChildren().add(newItem);

        // kohteen omat:
        //yleiseen tietokantaan:
        if (arvo instanceof Sarja) {
            ikkuna.annaSarjatk().add((Sarja) arvo);

        } else if (arvo instanceof Tuomari) {
            ikkuna.annaTuomaritk().add((Tuomari) arvo);
        }

        ikkuna.annaKohteet().add(arvo);

//avataan se valikko, mihin uusi kohde on lisätty
        if (!parent.isExpanded()) {
            parent.setExpanded(true);
        }

    }

    public void poistaKohde(Kohde arvo) {

        //jos kyseessä sarja poistetaan turnauksen sarjoista
        if (arvo instanceof Sarja) {

            Sarja sarja = (Sarja) arvo;
            Turnaus turnaus = sarja.annaTurnaus();
            for (int i = 0; i < turnaus.annaSarjat().size(); i++) {
                if (turnaus.annaSarjat().get(i).equals(sarja)) {
                    turnaus.annaSarjat().remove(i);
                }

            }
        } else if (arvo instanceof Tuomari) {

            Tuomari tuomari = (Tuomari) arvo;
            Turnaus turnaus = tuomari.annaTurnaus();
            for (int i = 0; i < turnaus.annaTuomarit().size(); i++) {
                if (turnaus.annaTuomarit().get(i).equals(tuomari)) {
                    turnaus.annaTuomarit().remove(i);
                }

            }
        }

        //poisto puusta
        TreeItem<Kohde> parent = new TreeItem<>();

        if (arvo instanceof Sarja) {
            parent = ikkuna.annaRootSarjat();

            for (int i = 0; i < parent.getChildren().size(); i++) {
                if (parent.getChildren().get(i).getValue().equals(arvo)) {
                    parent.getChildren().remove(i);
                }

            }

        } else if (arvo instanceof Tuomari) {
            parent = ikkuna.annaRootTuomarit();

            for (int i = 0; i < parent.getChildren().size(); i++) {

                if (parent.getChildren().get(i).getValue().equals(arvo)) {
                    parent.getChildren().remove(i);
                }

            }
        }

        //poisto yleisistä "tietokannoista" eli listoista
        if (arvo instanceof Sarja) {
            for (int i = 0; i < ikkuna.annaSarjatk().size(); i++) {

                if (ikkuna.annaSarjatk().get(i).annaID() == arvo.annaID()) {
                    ikkuna.annaSarjatk().remove(i);
                    tiedottaja.kirjoitaLoki("Sarja poistettu.");

                }
            }
        } else if (arvo instanceof Tuomari) {

            for (int i = 0; i < ikkuna.annaTuomaritk().size(); i++) {

                if (ikkuna.annaTuomaritk().get(i).annaID() == arvo.annaID()) {
                    ikkuna.annaTuomaritk().remove(i);
                    tiedottaja.kirjoitaLoki("Tuomari poistettu.");

                }
            }

        }

        // ja lopuksi poisto kohdelistasta
        for (int i = 0; i < ikkuna.annaKohteet().size(); i++) {
            if (ikkuna.annaKohteet().get(i).annaID() == arvo.annaID()) {

                ikkuna.annaKohteet().remove(i);

            }
        }

        ikkuna.asetaMuutos(true);
    }

    public void lisaaJoukkue(String nimi, Sarja sarja) {

        Joukkue joukkue = new Joukkue(nimi);

        sarja.annaJoukkueet().add(joukkue);
        ikkuna.annaJoukkuetk().add(joukkue);

        ikkuna.annaKohteet().add((Kohde) joukkue);

        joukkue.asetaSarja(sarja);
        joukkue.asetaTaulukkonimi();
        ikkuna.asetaMuutos(true);

    }

    public void lisaaPelaaja(String etunimi, String sukunimi, String pelipaikka, int pelinumero, Joukkue joukkue) {

        Pelaaja pelaaja = new Pelaaja(etunimi, sukunimi);
        pelaaja.asetaPelipaikka(pelipaikka);
        pelaaja.asetaPelinumero(pelinumero);
        pelaaja.asetaJoukkue(joukkue);

        joukkue.annaPelaajat().add(pelaaja);
        ikkuna.annaPelaajatk().add(pelaaja);
        ikkuna.annaKohteet().add((Kohde) pelaaja);

        pelaaja.asetaTaulukkonumero();
        pelaaja.asetaTaulukkopelipaikka();
        pelaaja.asetaTaulukkonimi();
        System.out.println(pelaaja.annaTaulukkopelipaikka());
        ikkuna.asetaMuutos(true);

    }

    public void lisaaOttelu(Joukkue koti, Joukkue vieras, String aika, String paikka, Tuomari erotuomari, Tuomari avustava1, Tuomari avustava2, Sarja sarja) {
        System.out.println(erotuomari);
        Ottelu ottelu = new Ottelu(sarja);
        koti.annaOttelut().add(ottelu);
        vieras.annaOttelut().add(ottelu);
        ottelu.asetaJoukkueet(koti, vieras);
        ottelu.asetaPaikka(paikka);
        ottelu.asetaAika(aika);

        //tuomarit kohdilleen 
        if (erotuomari != null) {
            TuomarinRooli erotuomariR = new TuomarinRooli(erotuomari, ottelu);
            erotuomariR.asetaRooli("Erotuomari");
            erotuomariR.asetaTaulukkonimi();
            erotuomariR.asetaTaulukkorooli();
            ottelu.annaRoolit().add(erotuomariR);
            erotuomari.annaTuomarinRoolit().add(erotuomariR);
        }

        if (avustava1 != null) {

            TuomarinRooli avustava1R = new TuomarinRooli(avustava1, ottelu);
            avustava1R.asetaRooli("1. Avustava erotuomari");
            avustava1R.asetaTaulukkonimi();
            avustava1R.asetaTaulukkorooli();
            ottelu.annaRoolit().add(avustava1R);
            avustava1.annaTuomarinRoolit().add(avustava1R);
        }

        if (avustava2 != null) {
            TuomarinRooli avustava2R = new TuomarinRooli(avustava2, ottelu);
            avustava2R.asetaRooli("2. Avustava erotuomari");
            avustava2R.asetaTaulukkonimi();
            avustava2R.asetaTaulukkorooli();

            ottelu.annaRoolit().add(avustava2R);
            avustava2.annaTuomarinRoolit().add(avustava2R);
        }

        ottelu.asetaTaulukkopaikka();
        ottelu.asetaTaulukkoaika();
        ottelu.asetaTaulukkonimi();
        ottelu.asetaTaulukkoid();
        ottelu.asetaTaulukkotuomarit();
        ottelu.asetaTaulukkotulos();
        sarja.annaOttelut().add(ottelu);
        ikkuna.asetaMuutos(true);

    }

    public void lisaaToimari(String etunimi, String sukunimi, String rooli, String sposti, String puh, Joukkue joukkue) {

        Toimihenkilo toimari = new Toimihenkilo(etunimi, sukunimi);

        toimari.asetaRooli(rooli);
        toimari.asetaSposti(sposti);
        toimari.asetaPuh(puh);
        toimari.asetaJoukkue(joukkue);

        joukkue.annaToimarit().add(toimari);

        ikkuna.annaToimaritk().add(toimari);
        ikkuna.annaKohteet().add((Kohde) toimari);

        toimari.asetaTaulukkonimi();
        toimari.asetaTaulukkosposti();
        toimari.asetaTaulukkopuh();
        toimari.asetaTaulukkorooli();

        ikkuna.asetaMuutos(true);

    }

}
