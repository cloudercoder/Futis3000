/*
Luokka, joka lisää ja poistaa eri kohteita
 */
package tupa;

import java.time.LocalDate;
import java.util.List;
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
            for (int i = 0; i < ikkuna.annaKohteet().size(); i++) {
                if (ikkuna.annaKohteet().get(i) instanceof Turnaus) {
                    sarja.asetaTurnaus((Turnaus) ikkuna.annaKohteet().get(i));
                }

            }

        } else if (arvo instanceof Tuomari) {
            // tallennetaan turnaus, johon kuuluu
            Tuomari tuomari = (Tuomari) arvo;
            for (int i = 0; i < ikkuna.annaKohteet().size(); i++) {
                if (ikkuna.annaKohteet().get(i) instanceof Turnaus) {
                    Turnaus turnaus = (Turnaus) ikkuna.annaKohteet().get(i);
                    tuomari.asetaTurnaus(turnaus);
                    //tallennetaan tuomari turnauksen tietoihin, sarjoille tämä on jo tehty muodostimen yhteydessä
                    turnaus.annaTuomarit().add(tuomari);
                }

            }

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
        System.out.println(arvo);
        //jos kyseessä sarja poistetaan turnauksen sarjoista ja tuomari turnauksen tuomareista
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

                //POISTETTAVA KAIKKI TÄHÄN LIITTYVÄ!
            }
        } else if (arvo instanceof Tuomari) {

            for (int i = 0; i < ikkuna.annaTuomaritk().size(); i++) {

                if (ikkuna.annaTuomaritk().get(i).annaID() == arvo.annaID()) {
                    ikkuna.annaTuomaritk().remove(i);
                    tiedottaja.kirjoitaLoki("Tuomari poistettu.");

                }

                //MISSÄ MUUALLA ON??!?
            }

        } else if (arvo instanceof Joukkue) {

            for (int i = 0; i < ikkuna.annaJoukkuetk().size(); i++) {

                if (ikkuna.annaJoukkuetk().get(i).annaID() == arvo.annaID()) {
                    ikkuna.annaJoukkuetk().remove(i);
                    tiedottaja.kirjoitaLoki("Joukkue poistettu.");

                }

                //POISTETTAVA MYÖS KAIKKI PELAAJAT!!!
            }

        } else if (arvo instanceof Pelaaja) {

            for (int i = 0; i < ikkuna.annaPelaajatk().size(); i++) {
                System.out.println("pelaajassa id:Tä ennen");
                System.out.println("pelaajatkid: " + ikkuna.annaPelaajatk().get(i).annaID());
                System.out.println("arvoid: " + arvo.annaID());
                System.out.println("arvo: " + arvo);
                if (ikkuna.annaPelaajatk().get(i).annaID() == arvo.annaID()) {
                    System.out.println("id:n jälkeen");
                    System.out.println(arvo.annaID());
                    ikkuna.annaPelaajatk().remove(i);
                    tiedottaja.kirjoitaLoki("Pelaaja poistettu.");

                }
                //MITÄ MUUTA PITÄÄ POISTAA?!??!
            }

        } else if (arvo instanceof Toimihenkilo) {

            for (int i = 0; i < ikkuna.annaToimaritk().size(); i++) {

                if (ikkuna.annaToimaritk().get(i).annaID() == arvo.annaID()) {
                    ikkuna.annaToimaritk().remove(i);
                    tiedottaja.kirjoitaLoki("Tuomari poistettu.");

                }
                //MITÄ MUUTA PITÄÄ POISTAA?!??!
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

    public void poistaOttelu(Ottelu ottelu) {
        //HAETAAN SARJAN OTTELUT JA POISTETAAN SIELTÄ

        //MITÄ MUUTA PITÄÄ POISTAA?!?!
    }

    public void lisaaJoukkue(String nimi, Sarja sarja) {

        Joukkue joukkue = new Joukkue(nimi);

        sarja.annaJoukkueet().add(joukkue);
        ikkuna.annaJoukkuetk().add(joukkue);

        ikkuna.annaKohteet().add((Kohde) joukkue);

        joukkue.asetaSarja(sarja);

        ikkuna.asetaMuutos(true);

    }

    public void poistaPelaaja(Pelaaja pelaaja) {
        System.out.println("pelaajassa");
    }

    public void lisaaPelaaja(String etunimi, String sukunimi, String pelipaikka, int pelinumero, Joukkue joukkue) {

        Pelaaja pelaaja = new Pelaaja(etunimi, sukunimi);
        pelaaja.asetaPelipaikka(pelipaikka);
        pelaaja.asetaPelinumero(pelinumero);
        pelaaja.asetaJoukkue(joukkue);

        joukkue.annaPelaajat().add(pelaaja);
        ikkuna.annaPelaajatk().add(pelaaja);
        ikkuna.annaKohteet().add((Kohde) pelaaja);

        ikkuna.asetaMuutos(true);

    }

    public void lisaaOttelu(Joukkue koti, Joukkue vieras, LocalDate aika, String paikka, Tuomari erotuomari, Tuomari avustava1, Tuomari avustava2, Sarja sarja) {

        Ottelu ottelu = new Ottelu(sarja);

        ottelu.asetaJoukkueet(koti, vieras);
        ottelu.asetaPaikka(paikka);
        ottelu.asetaAika(aika);

        //tuomarit kohdilleen 
        if (erotuomari != null) {
            TuomarinRooli erotuomariR = new TuomarinRooli(erotuomari, ottelu);
            erotuomariR.asetaRooli("Erotuomari");

            ottelu.annaRoolit().add(erotuomariR);
            erotuomari.annaTuomarinRoolit().add(erotuomariR);
        }

        if (avustava1 != null) {

            TuomarinRooli avustava1R = new TuomarinRooli(avustava1, ottelu);
            avustava1R.asetaRooli("1. Avustava erotuomari");

            ottelu.annaRoolit().add(avustava1R);
            avustava1.annaTuomarinRoolit().add(avustava1R);
        }

        if (avustava2 != null) {
            TuomarinRooli avustava2R = new TuomarinRooli(avustava2, ottelu);
            avustava2R.asetaRooli("2. Avustava erotuomari");

            ottelu.annaRoolit().add(avustava2R);
            avustava2.annaTuomarinRoolit().add(avustava2R);
        }

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

        ikkuna.asetaMuutos(true);

    }

    public void lisaaMaali(int aika, Pelaaja maalintekija, Pelaaja syottaja, Ottelu ottelu) {

        Maali maali = new Maali(ottelu);
        maali.asetaTiedot(aika, maalintekija, syottaja);
    }

    public void lisaaTulos(int kotimaalit, int vierasmaalit, Ottelu ottelu) {
        ottelu.asetaTulos(kotimaalit, vierasmaalit);
    }

    public void lisaaKokoonpano(Pelaaja[] pelaajat, String[] roolit, Joukkue joukkue, Ottelu ottelu) {

        for (int i = 0; i < pelaajat.length; i++) {

            if (roolit[i].equals("Kokoonpanossa")) {
                //katsotaan onko koti- vai vierasjoukkue
                if (joukkue.equals(ottelu.annaKotijoukkue())) {
                    Kokoonpano kotikokoonpano = ottelu.annaKotiKokoonpano();
                    kotikokoonpano.asetaPelaaja(pelaajat[i]);
                } else if (joukkue.equals(ottelu.annaKotijoukkue())) {
                    Kokoonpano vieraskokoonpano = ottelu.annaVierasKokoonpano();
                    vieraskokoonpano.asetaPelaaja(pelaajat[i]);
                }
            }

        }

    }

}
