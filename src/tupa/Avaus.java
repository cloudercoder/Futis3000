package tupa;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Marianne
 */
public class Avaus {

    private List<Kohde> kohdetk = new ArrayList<>();
  

    Avaus() {

    }

    public List<Kohde> avaa() {

        Tallennus tallenna = new Tallennus();
        String tiedosto = tallenna.annaTallennusTiedosto();
        ObjectInputStream sisaan2 = null;

        try {
            sisaan2 = new ObjectInputStream(new FileInputStream(tiedosto));
            while (true) {
                kohdetk.add((Kohde) sisaan2.readObject());
            }
        } catch (EOFException e) {

        } catch (ClassNotFoundException e) {

            System.out.println("\n**********************************************************");
            System.out.println("\nOngelmia kohteiden löytämisessä. ");
            System.out.println("\n**********************************************************");

        } catch (IOException e) {

            System.out.println("\n**********************************************************");
            System.out.println("\nOngelmia tiedoston avauksessa.");
            System.out.println("\n**********************************************************");

        }

        return kohdetk;
    }



}
