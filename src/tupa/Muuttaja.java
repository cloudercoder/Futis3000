/*
Luokka, joka lisää ja poistaa eri kohteita
 */
package tupa;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.TreeItem;

/**
 *
 * @author Marianne
 */
public class Muuttaja {

    private Tupa ikkuna;
    private Tiedottaja tiedottaja;
    private PaaNakyma nakyma;
    private SarjaNakyma sarjanakyma;

    Muuttaja() {

    }

    Muuttaja(Tupa ikkuna, PaaNakyma nakyma) {
        this.ikkuna = ikkuna;
        tiedottaja = new Tiedottaja(ikkuna);
        this.nakyma = nakyma;
        sarjanakyma = nakyma.annaSarjanakyma();
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
            tiedottaja.kirjoitaLoki("Sarja " + arvo.toString() + " lisätty.");

        } else if (arvo instanceof Tuomari) {
            ikkuna.annaTuomaritk().add((Tuomari) arvo);
            tiedottaja.kirjoitaLoki("Tuomari " + arvo.toString() + " lisätty.");
        }

        ikkuna.annaKohteet().add(arvo);

//avataan se valikko, mihin uusi kohde on lisätty
        if (!parent.isExpanded()) {
            parent.setExpanded(true);
        }

    }

    public void poistaKohde(Kohde arvo) {

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
                    tiedottaja.kirjoitaLoki("Sarja " + arvo.toString() + " poistettu.");

                }

            }
        } else if (arvo instanceof Tuomari) {

            for (int i = 0; i < ikkuna.annaTuomaritk().size(); i++) {

                if (ikkuna.annaTuomaritk().get(i).annaID() == arvo.annaID()) {
                    ikkuna.annaTuomaritk().remove(i);
                    tiedottaja.kirjoitaLoki("Tuomari " + arvo.toString() + " poistettu.");

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
        nakyma.luoEtusivu();
    }

    public void poistaOttelu(Ottelu ottelu) {

        Sarja sarja = ottelu.annaSarja();

        Joukkue joukkue1 = ottelu.annaKotijoukkue();
        Joukkue joukkue2 = ottelu.annaVierasjoukkue();

        for (int i = 0; i < ottelu.annaRoolit().size(); i++) {
            if (ottelu.annaRoolit().get(i).annaRooli().equals("Erotuomari")) {
                Tuomari erotuomari = ottelu.annaRoolit().get(i).annaTuomari();
                for (int j = 0; j < erotuomari.annaTuomarinRoolit().size(); j++) {
                    if (erotuomari.annaTuomarinRoolit().get(j).annaOttelu().equals(ottelu)) {
                        erotuomari.annaTuomarinRoolit().remove(erotuomari.annaTuomarinRoolit().get(j));

                    }

                }
            }
            if (ottelu.annaRoolit().get(i).annaRooli().equals("1. Avustava erotuomari")) {
                Tuomari avustava1 = ottelu.annaRoolit().get(i).annaTuomari();
                for (int j = 0; j < avustava1.annaTuomarinRoolit().size(); j++) {
                    if (avustava1.annaTuomarinRoolit().get(j).annaOttelu().equals(ottelu)) {
                        avustava1.annaTuomarinRoolit().remove(avustava1.annaTuomarinRoolit().get(j));

                    }

                }
            }
            if (ottelu.annaRoolit().get(i).annaRooli().equals("2. Avustava erotuomari")) {
                Tuomari avustava2 = ottelu.annaRoolit().get(i).annaTuomari();
                for (int j = 0; j < avustava2.annaTuomarinRoolit().size(); j++) {
                    if (avustava2.annaTuomarinRoolit().get(j).annaOttelu().equals(ottelu)) {
                        avustava2.annaTuomarinRoolit().remove(avustava2.annaTuomarinRoolit().get(j));

                    }

                }
            }
        }

        for (int i = 0; i < joukkue1.annaPelaajat().size(); i++) {
            Pelaaja pelaaja = joukkue1.annaPelaajat().get(i);

            for (int j = 0; j < pelaaja.annaMaaliLista().size(); j++) {
                Maali maali = pelaaja.annaMaaliLista().get(j);
                if (maali.annaOttelu().equals(ottelu)) {
                    pelaaja.annaMaaliLista().remove(maali);
                }
            }
        }
        for (int i = 0; i < joukkue2.annaPelaajat().size(); i++) {
            Pelaaja pelaaja = joukkue2.annaPelaajat().get(i);

            for (int j = 0; j < pelaaja.annaMaaliLista().size(); j++) {
                Maali maali = pelaaja.annaMaaliLista().get(j);
                if (maali.annaOttelu().equals(ottelu)) {
                    pelaaja.annaMaaliLista().remove(maali);
                }
            }
        }

        for (int i = 0; i < joukkue1.annaPelaajat().size(); i++) {
            Pelaaja pelaaja = joukkue1.annaPelaajat().get(i);

            for (int j = 0; j < pelaaja.annaKokoonpanot().size(); j++) {
                Kokoonpano kokoonpano = pelaaja.annaKokoonpanot().get(j);
                if (kokoonpano.annaOttelu().equals(ottelu)) {
                    pelaaja.annaKokoonpanot().remove(kokoonpano);
                }
            }
        }
        for (int i = 0; i < joukkue2.annaPelaajat().size(); i++) {
            Pelaaja pelaaja = joukkue2.annaPelaajat().get(i);

            for (int j = 0; j < pelaaja.annaKokoonpanot().size(); j++) {
                Kokoonpano kokoonpano = pelaaja.annaKokoonpanot().get(j);
                if (kokoonpano.annaOttelu().equals(ottelu)) {
                    pelaaja.annaKokoonpanot().remove(kokoonpano);
                }
            }
        }

        if (!ottelu.annaTulos().equals("-")) {
            //kotijoukkue voittanut
            if (ottelu.annaKotimaalit() > ottelu.annaVierasmaalit()) {
                joukkue1.annaVoitot().remove(0);
                joukkue2.annaHaviot().remove(0);
            }
            //vierasjoukkue voittanut
            if (ottelu.annaKotimaalit() < ottelu.annaVierasmaalit()) {
                joukkue1.annaHaviot().remove(0);
                joukkue2.annaVoitot().remove(0);
            }
            //tasapeli
            if (ottelu.annaKotimaalit() == ottelu.annaVierasmaalit()) {
                joukkue1.annaTasapelit().remove(0);
                joukkue2.annaTasapelit().remove(0);
            }

            joukkue1.annaTehdyt().add(ottelu.annaKotimaalit() * (-1));
            joukkue1.annaPaastetyt().add(ottelu.annaVierasmaalit() * (-1));
            joukkue2.annaTehdyt().add(ottelu.annaVierasmaalit() * (-1));
            joukkue2.annaPaastetyt().add(ottelu.annaKotimaalit() * (-1));
        }

        joukkue1.annaOttelut().remove(ottelu);
        joukkue2.annaOttelut().remove(ottelu);
        sarja.annaOttelut().remove(ottelu);
        tiedottaja.kirjoitaLoki("Ottelu " + ottelu.toString() + " poistettu.");
        ikkuna.asetaMuutos(true);

    }

    public void lisaaJoukkue(String nimi, Sarja sarja) {

        Joukkue joukkue = new Joukkue(nimi);

        sarja.annaJoukkueet().add(joukkue);
        ikkuna.annaJoukkuetk().add(joukkue);

        ikkuna.annaKohteet().add((Kohde) joukkue);

        joukkue.asetaSarja(sarja);
        tiedottaja.kirjoitaLoki("Joukkue " + joukkue.toString() + " lisätty.");
        ikkuna.asetaMuutos(true);

    }

    public void poistaPelaaja(Pelaaja pelaaja, Joukkue joukkue) {

        for (int i = 0; i < ikkuna.annaKohteet().size(); i++) {
            if (ikkuna.annaKohteet().get(i).annaID() == joukkue.annaID()) {

                ikkuna.annaKohteet().remove(i);

            }
        }
        joukkue.annaPelaajat().remove(joukkue);
        tiedottaja.kirjoitaLoki("Pelaaja " + pelaaja.toString() + " poistettu.");
        ikkuna.asetaMuutos(true);
    }

    public void poistaToimari(Toimihenkilo toimari, Joukkue joukkue) {

        for (int i = 0; i < ikkuna.annaKohteet().size(); i++) {
            if (ikkuna.annaKohteet().get(i).annaID() == toimari.annaID()) {

                ikkuna.annaKohteet().remove(i);

            }
        }
        joukkue.annaToimarit().remove(toimari);
        tiedottaja.kirjoitaLoki("Toimihenkilö " + toimari.toString() + " poistettu.");
        ikkuna.asetaMuutos(true);
    }

    public void poistaJoukkue(Joukkue joukkue, Sarja sarja) {

        for (int j = 0; j < joukkue.annaPelaajat().size(); j++) {
            for (int i = 0; i < ikkuna.annaKohteet().size(); i++) {
                if (ikkuna.annaKohteet().get(i).annaID() == joukkue.annaPelaajat().get(j).annaID()) {

                    ikkuna.annaKohteet().remove(i);

                }
            }

        }

        joukkue.annaPelaajat().clear();

        for (int i = 0; i < ikkuna.annaKohteet().size(); i++) {
            if (ikkuna.annaKohteet().get(i).annaID() == joukkue.annaID()) {

                ikkuna.annaKohteet().remove(i);

            }
        }
        sarja.annaJoukkueet().remove(joukkue);
        tiedottaja.kirjoitaLoki("Joukkue " + joukkue.toString() + " poistettu.");
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
        tiedottaja.kirjoitaLoki("Pelaaja " + pelaaja.toString() + " lisätty.");
        ikkuna.asetaMuutos(true);

    }

    public void lisaaOttelu(Joukkue koti, Joukkue vieras, LocalDate aika, String tunnit, String minuutit, String paikka, Tuomari erotuomari, Tuomari avustava1, Tuomari avustava2, Sarja sarja) {

        Ottelu ottelu = new Ottelu(sarja);

        ottelu.asetaJoukkueet(koti, vieras);
        ottelu.asetaPaikka(paikka);
        ottelu.asetaAika(aika, tunnit, minuutit);

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
        tiedottaja.kirjoitaLoki("Ottelu " + ottelu.toString() + " lisätty.");
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
        tiedottaja.kirjoitaLoki("Toimihenkilö " + toimari.toString() + " lisätty.");

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
                    pelaajat[i].annaKokoonpanot().add(kotikokoonpano);
                } else if (joukkue.equals(ottelu.annaVierasjoukkue())) {

                    Kokoonpano vieraskokoonpano = ottelu.annaVierasKokoonpano();
                    vieraskokoonpano.asetaPelaaja(pelaajat[i]);
                    pelaajat[i].annaKokoonpanot().add(vieraskokoonpano);
                }
            } else if (joukkue.equals(ottelu.annaKotijoukkue())) {
                Kokoonpano kotikokoonpano = ottelu.annaKotiKokoonpano();
                kotikokoonpano.annaPelaajat().remove(pelaajat[i]);
                pelaajat[i].annaKokoonpanot().remove(kotikokoonpano);
            } else if (joukkue.equals(ottelu.annaVierasjoukkue())) {
                Kokoonpano vieraskokoonpano = ottelu.annaVierasKokoonpano();
                vieraskokoonpano.annaPelaajat().remove(pelaajat[i]);
                pelaajat[i].annaKokoonpanot().remove(vieraskokoonpano);
            }

        }
        tiedottaja.kirjoitaLoki("Ottelun " + ottelu.toString() + " maalitilastoa päivitetty.");
    }

    public void suoritaAutoOtteluLista(Sarja sarja) {

        // poistetaan kaikki aiemmin luodut ottelut ensin
        while (!sarja.annaOttelut().isEmpty()) {
            for (int i = 0; i < sarja.annaOttelut().size(); i++) {
                poistaOttelu(sarja.annaOttelut().get(i));
            }
        }

        int maara = sarja.annaJoukkueet().size();
        boolean parillinen_maara = true;
        //jos joukkeita on pariton määrä, lisätään yksi ylimääräinen "dummy" joukkue, jota vastaan olevaa peliä ei oikeasti pelata

        Joukkue dummy = new Joukkue();
        if (maara % 2 != 0) {
            parillinen_maara = false;

            maara = maara + 1;

        }
        int puolikas = maara / 2;
        //PARILLINEN MÄÄRÄ?!
        Joukkue[] joukkueet = new Joukkue[maara];
        Joukkue[] joukkueet1 = new Joukkue[maara / 2];
        Joukkue[] joukkueet2 = new Joukkue[maara / 2];

        if (parillinen_maara) {
            for (int i = 0; i < maara; i++) {

                joukkueet[i] = sarja.annaJoukkueet().get(i);

            }

            for (int i = 0; i < puolikas; i++) {

                joukkueet1[i] = joukkueet[i];

            }

            int kohta = puolikas;
            for (int i = puolikas - 1; i >= 0; i--) {

                joukkueet2[i] = joukkueet[kohta];
                kohta++;

            }
        } else {
            joukkueet[0] = dummy;
            for (int i = 1; i < maara; i++) {

                joukkueet[i] = sarja.annaJoukkueet().get(i - 1);

            }

            for (int i = 0; i < puolikas; i++) {

                joukkueet1[i] = joukkueet[i];

            }

            int kohta = puolikas;
            for (int i = puolikas - 1; i >= 0; i--) {

                joukkueet2[i] = joukkueet[kohta];
                kohta++;

            }

        }

        //yksinkertaisten sarjojen otteluiden lkm (aritmeettinen summa, n = maara - 1): 
        int ottelut = 0;

        ottelut = ((maara - 1 + 1) / 2) * (maara - 1);

        //joukkueet taulukossa
        //muodostetaan otteluiden lukumaara * 2- matriisi siten, että yhdellä rivillä on yksi ottelu
        Joukkue[][] ottelutaulu = new Joukkue[ottelut][2];

        Joukkue[] apu1 = new Joukkue[maara / 2];
        Joukkue[] apu2 = new Joukkue[maara / 2];

        //kiinnitetään vika, joka ei siis liiku
        Joukkue kiinni = joukkueet[maara - 1];

        int kierrosten_lkm = maara - 1;

        int j = 0;

        int kierros = 1;
        for (int i = 1; i <= kierrosten_lkm; i++) {

            for (int k = 0; k < puolikas; k++) {

                ottelutaulu[j][0] = joukkueet1[k];
                ottelutaulu[j][1] = joukkueet2[k];

                j++;
            }
            kierros++;
            //kiinnitys
            if (kierros % 2 == 0) {

                apu1[0] = kiinni;
                apu2[0] = joukkueet2[puolikas - 1];

                int kohta2 = puolikas - 1;
                for (int k = 1; k <= puolikas - 1; k++) {
                    apu2[k] = joukkueet1[kohta2];
                    kohta2--;

                }
                int kohta3 = puolikas - 2;
                for (int k = 1; k < puolikas - 1; k++) {
                    apu1[k] = joukkueet2[kohta3];
                    kohta3--;

                }
                apu1[puolikas - 1] = joukkueet1[0];

            } else {

                apu2[0] = kiinni;
                apu1[0] = joukkueet2[puolikas - 1];

                int kohta2 = puolikas - 1;
                for (int k = 1; k <= puolikas - 1; k++) {
                    apu2[k] = joukkueet1[kohta2];
                    kohta2--;

                }
                int kohta3 = puolikas - 2;
                for (int k = 1; k < puolikas - 1; k++) {
                    apu1[k] = joukkueet2[kohta3];
                    kohta3--;

                }
                apu1[puolikas - 1] = joukkueet2[0];
            }

            for (int k = 0; k <= puolikas - 1; k++) {
                joukkueet1[k] = apu1[k];
                joukkueet2[k] = apu2[k];

            }

        }

        //jos joukkueita pariton määrä, varmistetaan, ettei koti- ja vieraspelit jakaudu epätasaisesti
        if (!parillinen_maara) {
            List<Joukkue> kotiyli = new ArrayList<>();
            List<Joukkue> kotiali = new ArrayList<>();

            //käydään kaikki joukkueet läpi
            for (int i = 0; i < joukkueet.length; i++) {

                if (!joukkueet[i].equals(dummy)) {
                    int kotiottelut = 0;
                    int vierasottelut = 0;
                    //käydään ottelutaulu läpi
                    for (int k = 0; k < ottelut; k++) {

                        if (ottelutaulu[k][0].equals(joukkueet[i]) && !ottelutaulu[k][1].equals(dummy)) {
                            kotiottelut++;
                        } else if (ottelutaulu[k][1].equals(joukkueet[i]) && !ottelutaulu[k][0].equals(dummy)) {
                            vierasottelut++;
                        }

                    }

                    if (!parillinen_maara && kotiottelut >= maara / 2) {
                        System.out.println("Kotiyliin " + joukkueet[i] + ", kotiotteluiden määärä " + kotiottelut);
                        kotiyli.add(joukkueet[i]);

                    } else if (!parillinen_maara && vierasottelut >= maara / 2) {
                        System.out.println("Kotialiin " + joukkueet[i] + ", vierasotteluiden määärä " + vierasottelut);
                        kotiali.add(joukkueet[i]);

                    }

                }

            }

            for (int k = 0; k < ottelut; k++) {
                if (kotiyli.size() > 0 && kotiali.size() > 0) {

                    for (int kotiylialku = 0; kotiylialku < kotiyli.size(); kotiylialku++) {

                        for (int m = 0; m < kotiali.size(); m++) {

                            if (ottelutaulu[k][0].equals(kotiyli.get(kotiylialku)) && ottelutaulu[k][1].equals(kotiali.get(m))) {

                                ottelutaulu[k][0] = kotiali.get(m);
                                ottelutaulu[k][1] = kotiyli.get(kotiylialku);
                                kotiali.remove(kotiali.get(m));
                                kotiyli.remove(kotiyli.get(kotiylialku));

                                break;
                            }
                        }

                    }

                }

            }

        }

        //matriisin sisällön pohjalta luodaan ottelut
        for (int i = 0; i < ottelut; i++) {

            if (!(ottelutaulu[i][0].equals(dummy) || ottelutaulu[i][1].equals(dummy))) {
                Ottelu ottelu = new Ottelu(sarja);
                Joukkue kotijoukkue = ottelutaulu[i][0];
                Joukkue vierasjoukkue = ottelutaulu[i][1];
                ottelu.asetaJoukkueet(kotijoukkue, vierasjoukkue);
                sarja.annaOttelut().add(ottelu);

            }

        }
        int ottelua_per_kierros = 0;
        if (parillinen_maara) {
            ottelua_per_kierros = maara / 2;
        } else {
            maara = maara - 1;
            ottelua_per_kierros = maara / 2;
        }

        List<Ottelu> lisatyt_ottelut = sarja.annaOttelut();

        int ottelulaskuri = 0;
        int kierroslaskuri = 1;
        for (int i = 0; i < lisatyt_ottelut.size(); i++) {

            ottelulaskuri++;

            if (ottelulaskuri > ottelua_per_kierros) {

                kierroslaskuri++;
                ottelulaskuri = 1;

            }

            lisatyt_ottelut.get(i).asetaKierros(kierroslaskuri);
            lisatyt_ottelut.get(i).asetaTaulukkokierros();

        }
        tiedottaja.kirjoitaLoki("Otteluluettelo laadittu sarjaan " + sarja.toString() + ".");
        ikkuna.asetaMuutos(true);

    }

    public void poistaKaikkiOttelut(List<Ottelu> ottelut, Sarja sarja) {

        while (!sarja.annaOttelut().isEmpty()) {
            for (int i = 0; i < ottelut.size(); i++) {

                poistaOttelu(ottelut.get(i));

            }
        }

        sarja.annaOttelut().clear();
        tiedottaja.kirjoitaLoki("Kaikki ottelut poistettu sarjasta " + sarja.toString() + ".");
        ikkuna.asetaMuutos(true);
    }

    public void poistaKaikkiJoukkueet(List<Joukkue> joukkueet, Sarja sarja) {

        while (!sarja.annaJoukkueet().isEmpty()) {
            for (int i = 0; i < joukkueet.size(); i++) {
                poistaJoukkue(joukkueet.get(i), sarja);

            }
        }
        sarja.annaJoukkueet().clear();
        tiedottaja.kirjoitaLoki("Kaikki joukkueet poistettu sarjasta " + sarja.toString() + ".");
        ikkuna.asetaMuutos(true);
    }

    void poistaKaikkiPelaajat(List<Pelaaja> poistettavat, Joukkue joukkue) {

        while (!joukkue.annaPelaajat().isEmpty()) {
            for (int i = 0; i < poistettavat.size(); i++) {
                poistaPelaaja(poistettavat.get(i), joukkue);

            }
        }
        joukkue.annaPelaajat().clear();
        tiedottaja.kirjoitaLoki("Kaikki pelaajat poistettu joukkueesta " + joukkue.toString() + ".");
        ikkuna.asetaMuutos(true);
    }

    void poistaKaikkiToimarit(List<Toimihenkilo> poistettavat, Joukkue joukkue) {

        while (!joukkue.annaToimarit().isEmpty()) {
            for (int i = 0; i < poistettavat.size(); i++) {
                poistaToimari(poistettavat.get(i), joukkue);

            }
        }
        joukkue.annaToimarit().clear();
        tiedottaja.kirjoitaLoki("Kaikki toimihenkilöt poistettu joukkueesta " + joukkue.toString() + ".");
        ikkuna.asetaMuutos(true);

    }

}
