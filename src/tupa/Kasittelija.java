package tupa;

import javafx.scene.control.TreeItem;

/**
 *
 * @author Marianne
 */
public class Kasittelija {

    private Tupa ikkuna;
    private Tiedottaja tiedottaja;
    private PaaNakyma nakyma;
    private SarjaNakyma sarjanakyma;
    private TuomariNakyma tuomarinakyma;

    Kasittelija() {

    }

    Kasittelija(Tupa ikkuna) {
        this.ikkuna = ikkuna;
        tiedottaja = new Tiedottaja(ikkuna);
        nakyma = new PaaNakyma(ikkuna);
        sarjanakyma = nakyma.annaSarjanakyma();
        tuomarinakyma = nakyma.annaTuomarinakyma();
    }

    public void valittuKohde(TreeItem<Kohde> arvo) {

        String ohje = "";
        if (arvo == ikkuna.annaRootSarjat()) {
            ohje = ("Valitse vasemmalta haluamasi sarja tai lisää uusi.");
            nakyma.luoOhje(ohje, arvo);

        } else if (arvo == ikkuna.annaRootTuomarit()) {
            ohje = ("Valitse vasemmalta haluamasi tuomari tai lisää uusi.");
            nakyma.luoOhje(ohje, arvo);

        } else if (arvo.getParent().getValue() instanceof Sarja) {
            sarjanakyma.luoSarjaSivu(arvo);
        } else if (arvo.getParent().getValue() instanceof Tuomari) {
            tuomarinakyma.luoTuomariSivu(arvo);
        }

    }

    public void branchExpended(TreeItem.TreeModificationEvent<Kohde> event) {
        String nodeValue = event.getSource().getValue().toString();


    }

    public void branchCollapsed(TreeItem.TreeModificationEvent<Kohde> event) {
        String nodeValue = event.getSource().getValue().toString();
      

    }

}
