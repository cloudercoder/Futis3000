package tupa;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.TreeItem;

/**
 *
 * @author Marianne
 */

public class PuuTehdas {

    private List<Sarja> sarjatk;
    private List<Tuomari> tuomaritk;
    private Tupa ikkuna;

    public PuuTehdas() {
    }

    public PuuTehdas(Tupa ikkuna) {
        this.ikkuna = ikkuna;
        this.sarjatk = ikkuna.annaSarjatk();
        this.tuomaritk = ikkuna.annaTuomaritk();

    }
    
    // metodi, joka luo ArrayList:n TreeItemsista (Sarjat)

    public ArrayList<TreeItem<Kohde>> getSarjat() {

        ArrayList<TreeItem<Kohde>> sarjat = new ArrayList<TreeItem<Kohde>>();

        String nimi = "";

        if (!sarjatk.isEmpty()) {
            for (int i = 0; i < sarjatk.size(); i++) {
                nimi = sarjatk.get(i).toString();
                Kohde s = new Sarja(nimi, (Turnaus) ikkuna.annaTurnaus());
                TreeItem ts1 = new TreeItem(s);
                sarjat.add(ts1);

            }

        }

        return sarjat;
    }

    public ArrayList<TreeItem<Kohde>> getTuomarit() {
        ArrayList<TreeItem<Kohde>> tuomarit = new ArrayList<TreeItem<Kohde>>();
        String nimi = "";
        if (!tuomaritk.isEmpty()) {

            for (int i = 0; i < tuomaritk.size(); i++) {
                nimi = tuomaritk.get(i).toString();
                Kohde s = new Tuomari(nimi);
                TreeItem ts1 = new TreeItem(s);
                tuomarit.add(ts1);
            }

        }
        return tuomarit;
    }


}
