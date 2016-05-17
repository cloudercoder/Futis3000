package tupa;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Marianne
 */
public class Tallennus {

    private String tallennusTiedosto = "TUPA_tallennus";
    private Tupa ikkuna;

    private List<Kohde> kohdetk = new ArrayList<>();

    Tallennus() {

    }

    Tallennus(Tupa ikkuna) {

        this.ikkuna = ikkuna;
    }

    public void suoritaTallennus() {
        ObjectOutputStream ulos = null;
        kohdetk = ikkuna.annaKohteet();

        
        try {
            ulos = new ObjectOutputStream(new FileOutputStream(tallennusTiedosto));

            if (!kohdetk.isEmpty()) {

                for (int i = 0; i < kohdetk.size(); i++) {

                    ulos.writeObject(kohdetk.get(i));
                }
            }

        } catch (IOException e) {

            System.out.println("\n****************************************************************");
            System.out.println("\nKohteiden tallentaminen ei onnistunut.");
            System.out.println("\n****************************************************************");

        } finally {
            try {
                ulos.close();
            } catch (IOException e) {

                System.out.println("\n************************************************************");
                System.out.println("Tiedoston sulkeminen ei onnistunut.");
                System.out.println("\n***********************************************************");

            }
        }

        //päivitetään tilanne, että tallennus on suoritettu
        ikkuna.asetaMuutos(false);

    }

    public String annaTallennusTiedosto() {
        return tallennusTiedosto;
    }
}
