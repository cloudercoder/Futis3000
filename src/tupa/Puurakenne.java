package tupa;

import java.util.ArrayList;
import javafx.event.EventHandler;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author Marianne
 */
public class Puurakenne {

    private Tupa ikkuna;

    Puurakenne() {

    }

    Puurakenne(Tupa ikkuna) {

        this.ikkuna = ikkuna;

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
        sivuPuu.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                String ohje = "";
                TreeItem<Kohde> klikattu = sivuPuu.getSelectionModel().getSelectedItem();
                if (klikattu != null) {
                    if ((!klikattu.equals(ikkuna.annaRootSarjat())) && (klikattu.getValue() instanceof Sarja)) {

                        Sarja sarja = (Sarja) klikattu.getValue();
                        TreeItem<Kohde> mihin = new TreeItem<>(sarja);
                        PaaNakyma nakyma = ikkuna.annaPaaNakyma();
                        SarjaNakyma sarjanakyma = nakyma.annaSarjanakyma();
                        sarjanakyma.luoSarjaSivu(mihin);

                    } else if ((!klikattu.equals(ikkuna.annaRootTuomarit())) && (klikattu.getValue() instanceof Tuomari)) {

                        Tuomari tuomari = (Tuomari) klikattu.getValue();
                        TreeItem<Kohde> mihin = new TreeItem<>(tuomari);
                        PaaNakyma nakyma = ikkuna.annaPaaNakyma();
                        TuomariNakyma tuomarinakyma = nakyma.annaTuomarinakyma();
                        tuomarinakyma.luoTuomariSivu(mihin);

                    } else if (klikattu.equals(ikkuna.annaRootTuomarit())) {
                        ohje = ("Valitse vasemmalta haluamasi tuomari tai lisää uusi.");
                        PaaNakyma nakyma = ikkuna.annaPaaNakyma();
                        nakyma.luoOhje(ohje, klikattu);
                    } else if (klikattu.equals(ikkuna.annaRootSarjat())) {
                        ohje = ("Valitse vasemmalta haluamasi sarja tai lisää uusi.");
                        PaaNakyma nakyma = ikkuna.annaPaaNakyma();
                        nakyma.luoOhje(ohje, klikattu);
                    }

                }

            }
        });

        sivuPuu.setRoot(rootSivuPuu);

        return sivuPuu;

    }

}
