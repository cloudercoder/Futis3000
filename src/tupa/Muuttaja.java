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
        nakyma.luoEtusivu();
    }

    public void poistaOttelu(Ottelu ottelu) {

        Sarja sarja = ottelu.annaSarja();

        Joukkue joukkue1 = ottelu.annaKotijoukkue();
        Joukkue joukkue2 = ottelu.annaVierasjoukkue();

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
        ikkuna.asetaMuutos(true);

    }

    public void lisaaJoukkue(String nimi, Sarja sarja) {

        Joukkue joukkue = new Joukkue(nimi);

        sarja.annaJoukkueet().add(joukkue);
        ikkuna.annaJoukkuetk().add(joukkue);

        ikkuna.annaKohteet().add((Kohde) joukkue);

        joukkue.asetaSarja(sarja);

        ikkuna.asetaMuutos(true);

    }

    public void poistaPelaaja(Pelaaja pelaaja, Joukkue joukkue) {

        for (int i = 0; i < ikkuna.annaKohteet().size(); i++) {
            if (ikkuna.annaKohteet().get(i).annaID() == joukkue.annaID()) {

                ikkuna.annaKohteet().remove(i);

            }
        }
        joukkue.annaPelaajat().remove(joukkue);
        ikkuna.asetaMuutos(true);
    }

    public void poistaToimari(Toimihenkilo toimari, Joukkue joukkue) {

        for (int i = 0; i < ikkuna.annaKohteet().size(); i++) {
            if (ikkuna.annaKohteet().get(i).annaID() == toimari.annaID()) {

                ikkuna.annaKohteet().remove(i);

            }
        }
        joukkue.annaToimarit().remove(toimari);
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

    public void suoritaAutoOtteluLista(Sarja sarja) {

        // poistetaan kaikki aiemmin luodut ottelut ensin
        for (int i = 0; i < sarja.annaOttelut().size(); i++) {
            poistaOttelu(sarja.annaOttelut().get(i));
        }

        int maara = sarja.annaJoukkueet().size();

        Joukkue[] joukkueet = new Joukkue[maara];

        for (int i = 0; i < maara; i++) {
            joukkueet[i] = sarja.annaJoukkueet().get(i);

        }

        //yksinkertaisten sarjojen otteluiden lkm (aritmeettinen summa): 
        int ottelut = ((maara - 1 + 1) / 2) * (maara - 1);

        //joukkueet taulukossa
        //muodostetaan otteluiden lukumaara * 2- matriisi siten, että yhdellä rivillä on yksi ottelu
        Joukkue[][] ottelutaulu = new Joukkue[ottelut][2];

        int ottelua_per_kierros = maara / 2;

        int j = 0;
        for (int i = 0; i < maara; i++) {
            boolean koti = false;
            for (int k = i + 1; k < maara; k++) {

                if (koti == false) {
                    ottelutaulu[j][0] = joukkueet[i];
                    ottelutaulu[j][1] = joukkueet[k];
                    koti = true;
                } else {

                    ottelutaulu[j][1] = joukkueet[i];
                    ottelutaulu[j][0] = joukkueet[k];
                    koti = false;
                }

                j++;
            }
        }

        //muutetaan sisältöä, jottei peräkkäisiä pelejä
        Joukkue[][] kierrostaulu = new Joukkue[ottelut][2];

        for (int i = 0; i < maara - 1; i++) {

            int alaraja = i * ottelua_per_kierros;
            int ylaraja = alaraja + ottelua_per_kierros;

            for (int k = alaraja; k < ylaraja; k++) {

                for (int s = i; s < ottelut; s++) {

                    boolean onjo = false;

                    for (int m = k; m >= alaraja; m--) {

                        if (kierrostaulu[m][0] != null && kierrostaulu[m][1] != null) {
                            if (kierrostaulu[m][0].equals(ottelutaulu[s][0]) || kierrostaulu[m][0].equals(ottelutaulu[s][1]) || kierrostaulu[m][1].equals(ottelutaulu[s][0]) || kierrostaulu[m][1].equals(ottelutaulu[s][1])) {
                                onjo = true;
                            }
                        } else {
                            kierrostaulu[k][0] = ottelutaulu[s][0];
                            kierrostaulu[k][1] = ottelutaulu[s][1];
                            onjo = true;
                        }
                    }
                    if (!onjo) {

                        kierrostaulu[k][0] = ottelutaulu[s][0];
                        kierrostaulu[k][1] = ottelutaulu[s][1];

                        s = ottelut;

                    }

                }
            }

        }

        int kierrosten_lkm = maara - 1;

        //matriisin sisällön pohjalta luodaan ottelut
        for (int i = 0; i < ottelut; i++) {

            Ottelu ottelu = new Ottelu(sarja);
            Joukkue kotijoukkue = kierrostaulu[i][0];
            Joukkue vierasjoukkue = kierrostaulu[i][1];

            ottelu.asetaJoukkueet(kotijoukkue, vierasjoukkue);

            sarja.annaOttelut().add(ottelu);
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

        ikkuna.asetaMuutos(true);
        TreeItem<Kohde> mihin = new TreeItem<>(sarja);

        sarjanakyma.luoSarjaSivu(mihin);

    }

    public void poistaKaikkiOttelut(List<Ottelu> ottelut, Sarja sarja) {
        for (int i = 0; i < ottelut.size(); i++) {
            poistaOttelu(ottelut.get(i));

        }

        sarja.annaOttelut().clear();
        ikkuna.asetaMuutos(true);
    }

    public void poistaKaikkiJoukkueet(List<Joukkue> joukkueet, Sarja sarja) {
        for (int i = 0; i < joukkueet.size(); i++) {
            poistaJoukkue(joukkueet.get(i), sarja);

        }

        sarja.annaJoukkueet().clear();
        ikkuna.asetaMuutos(true);
    }

    void poistaKaikkiPelaajat(List<Pelaaja> poistettavat, Joukkue joukkue) {
        for (int i = 0; i < poistettavat.size(); i++) {
            poistaPelaaja(poistettavat.get(i), joukkue);

        }

        joukkue.annaPelaajat().clear();
        ikkuna.asetaMuutos(true);
    }

    void poistaKaikkiToimarit(List<Toimihenkilo> poistettavat, Joukkue joukkue) {
        for (int i = 0; i < poistettavat.size(); i++) {
            poistaToimari(poistettavat.get(i), joukkue);

        }

        joukkue.annaToimarit().clear();
        ikkuna.asetaMuutos(true);
    }

}
