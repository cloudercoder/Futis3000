/*
Erilaisia taulukoita muodostava luokka
 */
package tupa;

import java.util.ArrayList;
import java.util.List;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author Marianne
 */
public class Taulukko {

    private TableView taulukko = new TableView();
    private Nakyma nakyma;

    Taulukko() {

    }

    Taulukko(Nakyma nakyma) {
        this.nakyma = nakyma;

    }

    public TableView luoOtteluTaulukko(Sarja sarja) {
        taulukko.setId("my-table");
        List<Ottelu> ottelut = new ArrayList<>();

        for (int i = 0; i < sarja.annaOttelut().size(); i++) {

            ottelut.add(sarja.annaOttelut().get(i));

        }

        ObservableList<Ottelu> data
                = FXCollections.observableArrayList(ottelut);

        TableColumn id = new TableColumn("OtteluID");
        TableColumn ottelu = new TableColumn("Ottelu");
        TableColumn ajankohta = new TableColumn("Ajankohta");
        TableColumn paikka = new TableColumn("Paikka");
        TableColumn tuomarit = new TableColumn("Tuomarit");
        TableColumn erotuomari = new TableColumn("Erotuomari");
        TableColumn avustava1 = new TableColumn("1. Avustava");
        TableColumn avustava2 = new TableColumn("2. Avustava");
        tuomarit.getColumns().addAll(erotuomari, avustava1, avustava2);
        TableColumn tulos = new TableColumn("Tulos");

        id.setCellValueFactory(new PropertyValueFactory<Ottelu, Integer>("taulukkoid"));
        ottelu.setCellValueFactory(new PropertyValueFactory<Ottelu, String>("taulukkonimi"));
        ajankohta.setCellValueFactory(new PropertyValueFactory<Ottelu, String>("taulukkoaika"));
        paikka.setCellValueFactory(new PropertyValueFactory<Ottelu, String>("taulukkopaikka"));
        erotuomari.setCellValueFactory(new PropertyValueFactory<Ottelu, String>("taulukkoerotuomari"));
        avustava1.setCellValueFactory(new PropertyValueFactory<Ottelu, String>("taulukkoavustava1"));
        avustava2.setCellValueFactory(new PropertyValueFactory<Ottelu, String>("taulukkoavustava2"));
        tulos.setCellValueFactory(new PropertyValueFactory<Ottelu, String>("taulukkotulos"));
avustava1.prefWidthProperty().bind(avustava1.widthProperty());
        taulukko.setItems(data);

        taulukko.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
//            nakyma.luoOtteluSivu((Ottelu) newSelection);
        });

        taulukko.setFixedCellSize(25);

        if (taulukko.getItems().size() == 0) {
            taulukko.prefHeightProperty().bind(taulukko.fixedCellSizeProperty().multiply(Bindings.size(taulukko.getItems()).add(3)));
        } else {
            taulukko.prefHeightProperty().bind(taulukko.fixedCellSizeProperty().multiply(Bindings.size(taulukko.getItems()).add(2.1)));
        }

        taulukko.minHeightProperty().bind(taulukko.prefHeightProperty());
        taulukko.maxHeightProperty().bind(taulukko.prefHeightProperty());

        taulukko.getColumns().addAll(id, ottelu, ajankohta, paikka, tuomarit, tulos);

        return taulukko;
    }

    public TableView luoSarjaTaulukko() {
        taulukko.setId("my-table");
        TableColumn sijoitus = new TableColumn("#");
        TableColumn joukkue = new TableColumn("Joukkue");
        TableColumn ottelut = new TableColumn("Ott");
        TableColumn voitot = new TableColumn("V");
        TableColumn tasapelit = new TableColumn("T");
        TableColumn haviot = new TableColumn("H");
        TableColumn maalit = new TableColumn("M");
        TableColumn pisteet = new TableColumn("Pis");
        taulukko.setFixedCellSize(25);
        taulukko.getColumns().addAll(sijoitus, joukkue, ottelut, voitot, tasapelit, haviot, maalit, pisteet);
        taulukko.prefHeightProperty().bind(taulukko.fixedCellSizeProperty().multiply(Bindings.size(taulukko.getItems()).add(2)));
        taulukko.minHeightProperty().bind(taulukko.prefHeightProperty());
        taulukko.maxHeightProperty().bind(taulukko.prefHeightProperty());
        return taulukko;
    }

    public TableView luoMaalintekijaTaulukko() {
        taulukko.setId("my-table");
        TableColumn sijoitus = new TableColumn("#");
        TableColumn pelaaja = new TableColumn("Pelaaja");
        TableColumn maalit = new TableColumn("Maalit");
        TableColumn syotot = new TableColumn("Syötöt");
        taulukko.setFixedCellSize(25);
        taulukko.getColumns().addAll(sijoitus, pelaaja, maalit, syotot);
        taulukko.prefHeightProperty().bind(taulukko.fixedCellSizeProperty().multiply(Bindings.size(taulukko.getItems()).add(2)));
        taulukko.minHeightProperty().bind(taulukko.prefHeightProperty());
        taulukko.maxHeightProperty().bind(taulukko.prefHeightProperty());
        return taulukko;
    }

    public TableView luoPelaajaTaulukko() {
        taulukko.setId("my-table");
        TableColumn nimi = new TableColumn("Nimi");
        TableColumn pelipaikka = new TableColumn("Pelipaikka");
        TableColumn pelinumero = new TableColumn("Pelinumero");
        taulukko.setFixedCellSize(25);
        taulukko.getColumns().addAll(pelinumero, nimi, pelipaikka);
        taulukko.prefHeightProperty().bind(taulukko.fixedCellSizeProperty().multiply(Bindings.size(taulukko.getItems()).add(2)));
        taulukko.minHeightProperty().bind(taulukko.prefHeightProperty());
        taulukko.maxHeightProperty().bind(taulukko.prefHeightProperty());
        return taulukko;
    }

    public TableView luoToimihenkiloTaulukko(Joukkue joukkue) {
        taulukko.setId("my-table");

        List<Toimihenkilo> toimarit = new ArrayList<>();

        for (int i = 0; i < joukkue.annaToimarit().size(); i++) {

            toimarit.add(joukkue.annaToimarit().get(i));

        }

        ObservableList<Toimihenkilo> data
                = FXCollections.observableArrayList(toimarit);

        TableColumn nimi = new TableColumn("Nimi");
        TableColumn rooli = new TableColumn("Rooli");
        TableColumn sposti = new TableColumn("Sähköposti");
        TableColumn puh = new TableColumn("Puh.");

        nimi.setCellValueFactory(new PropertyValueFactory<Toimihenkilo, String>("taulukkonimi"));
        rooli.setCellValueFactory(new PropertyValueFactory<Toimihenkilo, String>("taulukkorooli"));
        sposti.setCellValueFactory(new PropertyValueFactory<Toimihenkilo, String>("taulukkosposti"));
        puh.setCellValueFactory(new PropertyValueFactory<Toimihenkilo, String>("taulukkopuh"));

        taulukko.setItems(data);

        taulukko.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            nakyma.luoToimariSivu((Toimihenkilo) newSelection);
        });

        taulukko.setFixedCellSize(25);
        taulukko.getColumns().addAll(nimi, rooli, sposti, puh);
        if (taulukko.getItems().size() == 0) {
            taulukko.prefHeightProperty().bind(taulukko.fixedCellSizeProperty().multiply(Bindings.size(taulukko.getItems()).add(2)));
        } else {
            taulukko.prefHeightProperty().bind(taulukko.fixedCellSizeProperty().multiply(Bindings.size(taulukko.getItems()).add(1.1)));
        }

        taulukko.minHeightProperty().bind(taulukko.prefHeightProperty());
        taulukko.maxHeightProperty().bind(taulukko.prefHeightProperty());

        return taulukko;

    }

    public TableView luoJoukkueTaulukko(Sarja sarja) {
        taulukko.setId("my-table");
        List<Joukkue> joukkueet = new ArrayList<>();

        for (int i = 0; i < sarja.annaJoukkueet().size(); i++) {

            joukkueet.add(sarja.annaJoukkueet().get(i));

        }

        ObservableList<Joukkue> data
                = FXCollections.observableArrayList(joukkueet);

        TableColumn joukkue = new TableColumn("Joukkue");

        joukkue.setCellValueFactory(new PropertyValueFactory<Joukkue, String>("taulukkonimi"));

        joukkue.setPrefWidth(100);

        taulukko.setItems(data);

        taulukko.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            nakyma.luoJoukkueSivu((Joukkue) newSelection);
        });

        taulukko.setFixedCellSize(25);

        taulukko.getColumns().addAll(joukkue);
        if (taulukko.getItems().size() == 0) {
            taulukko.prefHeightProperty().bind(taulukko.fixedCellSizeProperty().multiply(Bindings.size(taulukko.getItems()).add(2)));
        } else {
            taulukko.prefHeightProperty().bind(taulukko.fixedCellSizeProperty().multiply(Bindings.size(taulukko.getItems()).add(1.1)));
        }

        taulukko.minHeightProperty().bind(taulukko.prefHeightProperty());
        taulukko.maxHeightProperty().bind(taulukko.prefHeightProperty());

        return taulukko;
    }

    public TableView luoJoukkueenOtteluTaulukko(Joukkue joukkue) {
        taulukko.setId("my-table");
        List<Ottelu> ottelut = new ArrayList<>();

        for (int i = 0; i < joukkue.annaOttelut().size(); i++) {

            ottelut.add(joukkue.annaOttelut().get(i));

        }

        ObservableList<Ottelu> data
                = FXCollections.observableArrayList(ottelut);

        TableColumn id = new TableColumn("OtteluID");
        TableColumn ottelu = new TableColumn("Ottelu");
        TableColumn ajankohta = new TableColumn("Ajankohta");
        TableColumn paikka = new TableColumn("Paikka");
        TableColumn tuomari = new TableColumn("Tuomari");
        TableColumn tulos = new TableColumn("Tulos");

        id.setCellValueFactory(new PropertyValueFactory<Ottelu, Integer>("taulukkoid"));
        ottelu.setCellValueFactory(new PropertyValueFactory<Ottelu, String>("taulukkonimi"));
        ajankohta.setCellValueFactory(new PropertyValueFactory<Ottelu, String>("taulukkoaika"));
        paikka.setCellValueFactory(new PropertyValueFactory<Ottelu, String>("taulukkopaikka"));
        tuomari.setCellValueFactory(new PropertyValueFactory<Ottelu, String>("taulukkotuomari"));
        tulos.setCellValueFactory(new PropertyValueFactory<Ottelu, String>("taulukkotulos"));

        taulukko.setItems(data);

        taulukko.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
//            nakyma.luoOtteluSivu((Ottelu) newSelection);
        });

        taulukko.setFixedCellSize(25);

        if (taulukko.getItems().size() == 0) {
            taulukko.prefHeightProperty().bind(taulukko.fixedCellSizeProperty().multiply(Bindings.size(taulukko.getItems()).add(2)));
        } else {
            taulukko.prefHeightProperty().bind(taulukko.fixedCellSizeProperty().multiply(Bindings.size(taulukko.getItems()).add(1.1)));
        }

        taulukko.minHeightProperty().bind(taulukko.prefHeightProperty());
        taulukko.maxHeightProperty().bind(taulukko.prefHeightProperty());

        taulukko.getColumns().addAll(id, ottelu, ajankohta, paikka, tuomari, tulos);

        return taulukko;
    }

    public TableView luoPelaajaTaulukko(Joukkue joukkue) {
        taulukko.setId("my-table");
        List<Pelaaja> pelaajat = new ArrayList<>();

        for (int i = 0; i < joukkue.annaPelaajat().size(); i++) {

            pelaajat.add(joukkue.annaPelaajat().get(i));

        }

        ObservableList<Pelaaja> data
                = FXCollections.observableArrayList(pelaajat);

        TableColumn pelaaja = new TableColumn("Pelaaja");
        TableColumn pelinumero = new TableColumn("#");
        TableColumn pelipaikka = new TableColumn("Pelipaikka");

        pelaaja.setCellValueFactory(new PropertyValueFactory<TuomarinRooli, String>("taulukkonimi"));
        pelinumero.setCellValueFactory(new PropertyValueFactory<TuomarinRooli, Integer>("taulukkonumero"));
        pelipaikka.setCellValueFactory(new PropertyValueFactory<TuomarinRooli, String>("taulukkopelipaikka"));

        pelaaja.setPrefWidth(10);
        pelinumero.setPrefWidth(10);
        pelipaikka.setPrefWidth(10);

        taulukko.setItems(data);

        taulukko.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            nakyma.luoPelaajaSivu((TuomarinRooli) newSelection);
        });

        taulukko.setFixedCellSize(25);

        taulukko.getColumns().addAll(pelinumero, pelaaja, pelipaikka);
        if (taulukko.getItems().size() == 0) {
            taulukko.prefHeightProperty().bind(taulukko.fixedCellSizeProperty().multiply(Bindings.size(taulukko.getItems()).add(2)));
        } else {
            taulukko.prefHeightProperty().bind(taulukko.fixedCellSizeProperty().multiply(Bindings.size(taulukko.getItems()).add(1.1)));
        }

        taulukko.minHeightProperty().bind(taulukko.prefHeightProperty());
        taulukko.maxHeightProperty().bind(taulukko.prefHeightProperty());

        return taulukko;
    }

    public TableView luoJoukkueenMaalintekijaTaulukko(Joukkue joukkue) {
        taulukko.setId("my-table");
        TableColumn sijoitus = new TableColumn("#");
        TableColumn pelaaja = new TableColumn("Pelaaja");
        TableColumn maalit = new TableColumn("Maalit");
        TableColumn syotot = new TableColumn("Syötöt");
        taulukko.setFixedCellSize(25);
        taulukko.getColumns().addAll(sijoitus, pelaaja, maalit, syotot);
        taulukko.prefHeightProperty().bind(taulukko.fixedCellSizeProperty().multiply(Bindings.size(taulukko.getItems()).add(2)));
        taulukko.minHeightProperty().bind(taulukko.prefHeightProperty());
        taulukko.maxHeightProperty().bind(taulukko.prefHeightProperty());
        return taulukko;
    }

    public TableView luoTuomarinOtteluTaulukko(Tuomari tuomari) {
        
        taulukko.setId("my-table");
        List<Ottelu> ottelut = new ArrayList<>();

        for (int i = 0; i < tuomari.annaRoolit().size(); i++) {

            ottelut.add(tuomari.annaRoolit().get(i).annaOttelu());

        }

        ObservableList<Ottelu> data
                = FXCollections.observableArrayList(ottelut);

        TableColumn id = new TableColumn("OtteluID");
        TableColumn ottelu = new TableColumn("Ottelu");
        TableColumn ajankohta = new TableColumn("Ajankohta");
        TableColumn paikka = new TableColumn("Paikka");
        TableColumn tuomarit = new TableColumn("Tuomarit");
        TableColumn erotuomari = new TableColumn("Erotuomari");
        TableColumn avustava1 = new TableColumn("1. Avustava erotuomari");
        TableColumn avustava2 = new TableColumn("2. Avustava erotuomari");
        tuomarit.getColumns().addAll(erotuomari, avustava1, avustava2);
        TableColumn tulos = new TableColumn("Tulos");

        id.setCellValueFactory(new PropertyValueFactory<Ottelu, Integer>("taulukkoid"));
        ottelu.setCellValueFactory(new PropertyValueFactory<Ottelu, String>("taulukkonimi"));
        ajankohta.setCellValueFactory(new PropertyValueFactory<Ottelu, String>("taulukkoaika"));
        paikka.setCellValueFactory(new PropertyValueFactory<Ottelu, String>("taulukkopaikka"));
        erotuomari.setCellValueFactory(new PropertyValueFactory<Ottelu, String>("taulukkoerotuomari"));
        avustava1.setCellValueFactory(new PropertyValueFactory<Ottelu, String>("taulukkoavustava1"));
         avustava2.setCellValueFactory(new PropertyValueFactory<Ottelu, String>("taulukkoavustava2"));
        tulos.setCellValueFactory(new PropertyValueFactory<Ottelu, String>("taulukkotulos"));

        taulukko.setItems(data);

        taulukko.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
//            nakyma.luoOtteluSivu((Ottelu) newSelection);
        });

        taulukko.setFixedCellSize(25);

        if (taulukko.getItems().size() == 0) {
            taulukko.prefHeightProperty().bind(taulukko.fixedCellSizeProperty().multiply(Bindings.size(taulukko.getItems()).add(3)));
        } else {
            taulukko.prefHeightProperty().bind(taulukko.fixedCellSizeProperty().multiply(Bindings.size(taulukko.getItems()).add(2)));
        }

        taulukko.minHeightProperty().bind(taulukko.prefHeightProperty());
        taulukko.maxHeightProperty().bind(taulukko.prefHeightProperty());

        taulukko.getColumns().addAll(id, ottelu, ajankohta, paikka, tuomarit, tulos);

        return taulukko;
    }

}
