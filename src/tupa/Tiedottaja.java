package tupa;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Calendar;
import java.util.Date;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 *
 * @author Marianne
 */
public class Tiedottaja {

    private Tupa ikkuna;

    Tiedottaja() {

    }

    Tiedottaja(Tupa ikkuna) {
        this.ikkuna = ikkuna;
    }

    public void kirjoitaLoki(String msg) {

        String paiva = LocalDate.now().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM));
        Date tanaan = new Date();

        String aika = tanaan.getHours() + ":" + tanaan.getMinutes();

        ikkuna.annaLokilista().add(paiva + " (" + aika + ") : " + msg);
        ObservableList<String> viesti = FXCollections.observableArrayList(ikkuna.annaLokilista());
        ikkuna.annaLoki().setItems(viesti);

    }

    public void annaVaroitus(String msg) {

        Alert alert = new Alert(AlertType.WARNING);

        alert.setTitle("TUPA - TULOSPALVELU");

        alert.setHeaderText("Huom!");

        alert.setContentText(msg);

        alert.show();

    }
}
