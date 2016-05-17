/*
Erilaisia taulukoita muodostava luokka
 */
package tupa;

import com.sun.prism.impl.Disposer.Record;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Callback;

/**
 *
 * @author Marianne
 */
public class Taulukko {

    private TableView taulukko = new TableView();
    private PaaNakyma nakyma;
    private SarjaNakyma sarjanakyma;
    private TuomariNakyma tuomarinakyma;
    private JoukkueNakyma joukkuenakyma;
    private OtteluNakyma ottelunakyma;
    private PelaajaNakyma pelaajanakyma;
    private ToimariNakyma toimarinakyma;
    private Varmistaja varmistaja;
    private Tarkistaja tarkistaja;
    private Tupa ikkuna;

    Taulukko() {

    }

    Taulukko(PaaNakyma nakyma) {
        this.nakyma = nakyma;
        tarkistaja = new Tarkistaja(nakyma.annaIkkuna());
        this.ikkuna = nakyma.annaIkkuna();
    }

    Taulukko(PaaNakyma nakyma, Varmistaja varmistaja) {
        this.nakyma = nakyma;
        this.varmistaja = varmistaja;
        tarkistaja = new Tarkistaja(nakyma.annaIkkuna());
    }

    Taulukko(SarjaNakyma sarjanakyma, Varmistaja varmistaja) {
        this.sarjanakyma = sarjanakyma;
        this.varmistaja = varmistaja;
    }

    Taulukko(TuomariNakyma tuomarinakyma) {
        this.tuomarinakyma = tuomarinakyma;
    }

    Taulukko(JoukkueNakyma joukkuenakyma) {
        this.joukkuenakyma = joukkuenakyma;
    }

    Taulukko(OtteluNakyma ottelunakyma) {
        this.ottelunakyma = ottelunakyma;
    }

    Taulukko(PelaajaNakyma pelaajanakyma) {
        this.pelaajanakyma = pelaajanakyma;
    }

    Taulukko(ToimariNakyma toimarinakyma) {
        this.toimarinakyma = toimarinakyma;
    }

    public TableView luoOtteluTaulukko(Sarja sarja) {
        taulukko.setId("my-table");

        taulukko.setPlaceholder(new Label("Ei lisättyjä otteluita"));
        List<Ottelu> ottelut = new ArrayList<>();

        for (int i = 0; i < sarja.annaOttelut().size(); i++) {
            sarja.annaOttelut().get(i).asetaTaulukkopaivastring();
            sarja.annaOttelut().get(i).asetaTaulukkokello();
            sarja.annaOttelut().get(i).asetaTaulukkoid();
            sarja.annaOttelut().get(i).asetaTaulukkonimi();
            sarja.annaOttelut().get(i).asetaTaulukkopaikka();
            sarja.annaOttelut().get(i).asetaTaulukkotulos();
            sarja.annaOttelut().get(i).asetaTaulukkotuomarit();
            sarja.annaOttelut().get(i).asetaTaulukkokierros();
            ottelut.add(sarja.annaOttelut().get(i));
            sarja.annaOttelut().get(i).annaPaivaDate();
        }

        ObservableList<Ottelu> data
                = FXCollections.observableArrayList(ottelut);

        TableColumn id = new TableColumn("ID");
        TableColumn kierros = new TableColumn("Kierros");
        TableColumn ottelu = new TableColumn("Ottelu");
        TableColumn ajankohta = new TableColumn("Ajankohta");
        TableColumn paiva = new TableColumn("Päivä");
        TableColumn kello = new TableColumn("Klo");
        ajankohta.getColumns().addAll(paiva, kello);
        TableColumn paikka = new TableColumn("Paikka");
        TableColumn tuomarit = new TableColumn("Tuomarit");
        TableColumn erotuomari = new TableColumn("Erotuomari");
        TableColumn avustava1 = new TableColumn("1. Avustava");
        TableColumn avustava2 = new TableColumn("2. Avustava");
        tuomarit.getColumns().addAll(erotuomari, avustava1, avustava2);
        TableColumn tulos = new TableColumn("Tulos");

        id.setCellValueFactory(new PropertyValueFactory<Ottelu, Integer>("taulukkoid"));
        kierros.setCellValueFactory(new PropertyValueFactory<Ottelu, Integer>("taulukkokierros"));
        ottelu.setCellValueFactory(new PropertyValueFactory<Ottelu, String>("taulukkonimi"));
        paiva.setCellValueFactory(new PropertyValueFactory<Ottelu, String>("taulukkopaivastring"));
        kello.setCellValueFactory(new PropertyValueFactory<Ottelu, String>("taulukkokello"));
        paikka.setCellValueFactory(new PropertyValueFactory<Ottelu, String>("taulukkopaikka"));
        erotuomari.setCellValueFactory(new PropertyValueFactory<Ottelu, String>("taulukkoerotuomari"));
        avustava1.setCellValueFactory(new PropertyValueFactory<Ottelu, String>("taulukkoavustava1"));
        avustava2.setCellValueFactory(new PropertyValueFactory<Ottelu, String>("taulukkoavustava2"));
        tulos.setCellValueFactory(new PropertyValueFactory<Ottelu, String>("taulukkotulos"));
        avustava1.prefWidthProperty().bind(avustava1.widthProperty());

        taulukko.getColumns().addAll(id, kierros, ottelu, ajankohta, paikka, tuomarit, tulos);
        taulukko.setItems(data);
        id.setPrefWidth(50);
        kierros.setPrefWidth(70);
        ottelu.setMinWidth(200);
        paikka.setMinWidth(200);
        erotuomari.setMinWidth(150);
        avustava1.setMinWidth(150);
        avustava2.setMinWidth(150);
        tulos.setPrefWidth(50);
        paiva.setSortType(TableColumn.SortType.ASCENDING);
      
        taulukko.getSortOrder().add(paiva);
        taulukko.getSortOrder().add(kello);

        taulukko.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            ottelunakyma = nakyma.annaOttelunakyma();
            ottelunakyma.luoOttelusivu((Ottelu) newSelection);
        });

        taulukko.setFixedCellSize(25);

        if (taulukko.getItems().size() == 0) {
            taulukko.prefHeightProperty().bind(taulukko.fixedCellSizeProperty().multiply(Bindings.size(taulukko.getItems()).add(3)));
        } else {
            taulukko.prefHeightProperty().bind(taulukko.fixedCellSizeProperty().multiply(Bindings.size(taulukko.getItems()).add(2.1)));
        }

        taulukko.minHeightProperty().bind(taulukko.prefHeightProperty());
        taulukko.maxHeightProperty().bind(taulukko.prefHeightProperty());

        return taulukko;
    }

    public TableView luoOtteluTaulukkoMuokattava(Sarja sarja) {
        taulukko.setId("ei-klikattava2");

        taulukko.setPlaceholder(new Label("Ei lisättyjä otteluita"));
        List<Ottelu> ottelut = new ArrayList<>();

        for (int i = 0; i < sarja.annaOttelut().size(); i++) {
            sarja.annaOttelut().get(i).asetaTaulukkopaiva();
            sarja.annaOttelut().get(i).asetaTaulukkokello();
            sarja.annaOttelut().get(i).asetaTaulukkoid();
            sarja.annaOttelut().get(i).asetaTaulukkokotijoukkue();
            sarja.annaOttelut().get(i).asetaTaulukkovierasjoukkue();
            sarja.annaOttelut().get(i).asetaTaulukkonimi();
            sarja.annaOttelut().get(i).asetaTaulukkopaikka();
            sarja.annaOttelut().get(i).asetaTaulukkotulos();
            sarja.annaOttelut().get(i).asetaTaulukkotuomarit();
            sarja.annaOttelut().get(i).asetaTaulukkokierros();
            ottelut.add(sarja.annaOttelut().get(i));

        }

        ObservableList<Ottelu> data
                = FXCollections.observableArrayList(ottelut);

        TableColumn id = new TableColumn("ID");
        TableColumn kierros = new TableColumn("Kierros");
        TableColumn ottelu = new TableColumn("Ottelu");
        TableColumn koti = new TableColumn("Koti");
        TableColumn vieras = new TableColumn("Vieras");
        TableColumn ajankohta = new TableColumn("Ajankohta");
        TableColumn paiva = new TableColumn("Päivä");
        TableColumn kello = new TableColumn("Klo");
        TableColumn tunnit = new TableColumn("h");
        TableColumn minuutit = new TableColumn("min");
        kello.setId("ei-klikattava2");
        kello.getColumns().addAll(tunnit, minuutit);
        ottelu.getColumns().addAll(koti, vieras);
        ajankohta.getColumns().addAll(paiva, kello);
        TableColumn paikka = new TableColumn("Paikka");
        TableColumn tuomarit = new TableColumn("Tuomarit");
        TableColumn erotuomari = new TableColumn("Erotuomari");
        TableColumn avustava1 = new TableColumn("1. Avustava");
        TableColumn avustava2 = new TableColumn("2. Avustava");
        tuomarit.getColumns().addAll(erotuomari, avustava1, avustava2);

        TableColumn col_action = new TableColumn<>("Poista");

        id.setCellValueFactory(new PropertyValueFactory<Ottelu, Integer>("taulukkoid"));
        kierros.setCellValueFactory(new PropertyValueFactory<Ottelu, Integer>("taulukkokierros"));
        ottelu.setCellValueFactory(new PropertyValueFactory<Ottelu, String>("taulukkonimi"));
        paiva.setCellValueFactory(new PropertyValueFactory<Ottelu, String>("taulukkopaiva"));
        kello.setCellValueFactory(new PropertyValueFactory<Ottelu, String>("taulukkokello"));
        tunnit.setCellValueFactory(new PropertyValueFactory<Ottelu, String>("taulukkotunnit"));
        minuutit.setCellValueFactory(new PropertyValueFactory<Ottelu, String>("taulukkominuutit"));
        paikka.setCellValueFactory(new PropertyValueFactory<Ottelu, String>("taulukkopaikka"));
        koti.setCellValueFactory(new PropertyValueFactory<Ottelu, String>("taulukkokotijoukkue"));
        vieras.setCellValueFactory(new PropertyValueFactory<Ottelu, String>("taulukkovierasjoukkue"));
        erotuomari.setCellValueFactory(new PropertyValueFactory<Ottelu, String>("taulukkoerotuomari"));
        avustava1.setCellValueFactory(new PropertyValueFactory<Ottelu, String>("taulukkoavustava1"));
        avustava2.setCellValueFactory(new PropertyValueFactory<Ottelu, String>("taulukkoavustava2"));

        avustava1.prefWidthProperty().bind(avustava1.widthProperty());

        taulukko.getColumns().addAll(id, kierros, ottelu, ajankohta, paikka, tuomarit, col_action);
        taulukko.setItems(data);
        
        id.setMinWidth(50);
        kierros.setMinWidth(50);
        paiva.setMinWidth(150);
        tunnit.setMinWidth(50);
        minuutit.setMinWidth(50);
  
        paikka.setMinWidth(150);
         koti.setMinWidth(150);
          vieras.setMinWidth(150);
        col_action.setMinWidth(50);
        erotuomari.setMinWidth(150);
        
        avustava1.setMinWidth(150);
        avustava2.setMinWidth(150);
          paiva.setMinWidth(70);
        paiva.setSortType(TableColumn.SortType.ASCENDING);
     
        taulukko.getSortOrder().add(paiva);
        taulukko.getSortOrder().add(tunnit);
        taulukko.getSortOrder().add(minuutit);

        taulukko.setFixedCellSize(25);

        if (taulukko.getItems().size() == 0) {
            taulukko.prefHeightProperty().bind(taulukko.fixedCellSizeProperty().multiply(Bindings.size(taulukko.getItems()).add(4)));
        } else {
            taulukko.prefHeightProperty().bind(taulukko.fixedCellSizeProperty().multiply(Bindings.size(taulukko.getItems()).add(3.1)));
        }

        taulukko.minHeightProperty().bind(taulukko.prefHeightProperty());
        taulukko.maxHeightProperty().bind(taulukko.prefHeightProperty());

        taulukko.setEditable(true);

        Callback<TableColumn, TableCell> cellFactory
                = new Callback<TableColumn, TableCell>() {
            public TableCell call(TableColumn p) {
                return new MuokkausSoluOttelu();
            }
        };

        Callback<TableColumn, TableCell> comboBoxCellFactory
                = new Callback<TableColumn, TableCell>() {
            public TableCell call(TableColumn p) {
                return new ComboBoxOttelu();
            }
        };

        Callback<TableColumn, TableCell> comboBoxCellFactory2
                = new Callback<TableColumn, TableCell>() {
            public TableCell call(TableColumn p) {
                return new TuomariCombo(sarja);
            }
        };

        Callback<TableColumn, TableCell> comboBoxCellFactory3
                = new Callback<TableColumn, TableCell>() {
            public TableCell call(TableColumn p) {
                return new KloCombo();
            }
        };

        Callback<TableColumn, TableCell> comboBoxCellFactory4
                = new Callback<TableColumn, TableCell>() {
            public TableCell call(TableColumn p) {
                return new KloCombo2();
            }
        };
        Callback<TableColumn, TableCell> comboBoxCellFactory5
                = new Callback<TableColumn, TableCell>() {
            public TableCell call(TableColumn p) {
                return new JoukkueCombo(sarja);
            }
        };

        paikka.setCellFactory(cellFactory);
        paikka.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Ottelu, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Ottelu, String> t) {

                if (!tarkistaja.onTyhja(t.getNewValue())) {
                    ((Ottelu) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())).asetaPaikka(t.getNewValue());
                    ikkuna = nakyma.annaIkkuna();
                    ikkuna.asetaMuutos(true);
                }
                sarjanakyma = nakyma.annaSarjanakyma();
                sarjanakyma.luoOtteluLuetteloMuokkaus(sarja);
            }
        }
        );

        kierros.setCellFactory(comboBoxCellFactory);
        kierros.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Ottelu, Integer>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Ottelu, Integer> t) {

                ((Ottelu) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())).asetaKierros(t.getNewValue());

                ikkuna = nakyma.annaIkkuna();
                ikkuna.asetaMuutos(true);

                sarjanakyma = nakyma.annaSarjanakyma();
                sarjanakyma.luoOtteluLuetteloMuokkaus(sarja);
            }
        }
        );

        erotuomari.setCellFactory(comboBoxCellFactory2);
        avustava1.setCellFactory(comboBoxCellFactory2);
        avustava2.setCellFactory(comboBoxCellFactory2);

        erotuomari.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Ottelu, Tuomari>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Ottelu, Tuomari> t) {

                Ottelu ottelu = ((Ottelu) t.getTableView().getItems().get(t.getTablePosition().getRow()));

                if (tarkistaja.erotuomariOK(t.getNewValue(), ottelu)) {
                    ((Ottelu) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())).annaRoolit().remove(t.getOldValue());
                    ((Ottelu) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())).asetaErotuomari(t.getNewValue());
                    ikkuna = nakyma.annaIkkuna();
                    ikkuna.asetaMuutos(true);
                }

                sarjanakyma = nakyma.annaSarjanakyma();
                sarjanakyma.luoOtteluLuetteloMuokkaus(sarja);
            }
        }
        );
        avustava1.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Ottelu, Tuomari>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Ottelu, Tuomari> t) {

                Ottelu ottelu = ((Ottelu) t.getTableView().getItems().get(t.getTablePosition().getRow()));

                if (tarkistaja.avustava1OK(t.getNewValue(), ottelu)) {
                    ((Ottelu) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())).annaRoolit().remove(t.getOldValue());
                    ((Ottelu) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())).asetaAvustava1(t.getNewValue());
                    ikkuna = nakyma.annaIkkuna();
                    ikkuna.asetaMuutos(true);
                }

                sarjanakyma = nakyma.annaSarjanakyma();
                sarjanakyma.luoOtteluLuetteloMuokkaus(sarja);
            }
        }
        );
        avustava2.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Ottelu, Tuomari>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Ottelu, Tuomari> t) {
                Ottelu ottelu = ((Ottelu) t.getTableView().getItems().get(t.getTablePosition().getRow()));

                if (tarkistaja.avustava2OK(t.getNewValue(), ottelu)) {
                    ((Ottelu) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())).annaRoolit().remove(t.getOldValue());
                    ((Ottelu) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())).asetaAvustava2(t.getNewValue());
                    ikkuna = nakyma.annaIkkuna();
                    ikkuna.asetaMuutos(true);
                }
                sarjanakyma = nakyma.annaSarjanakyma();
                sarjanakyma.luoOtteluLuetteloMuokkaus(sarja);
            }
        }
        );

        tunnit.setCellFactory(comboBoxCellFactory3);

        tunnit.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Ottelu, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Ottelu, String> t) {
                ((Ottelu) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())).asetaTunnit(t.getNewValue());
                ikkuna = nakyma.annaIkkuna();
                ikkuna.asetaMuutos(true);
                sarjanakyma = nakyma.annaSarjanakyma();
                sarjanakyma.luoOtteluLuetteloMuokkaus(sarja);

            }
        }
        );

        minuutit.setCellFactory(comboBoxCellFactory4);

        minuutit.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Ottelu, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Ottelu, String> t) {
                ((Ottelu) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())).asetaMinuutit(t.getNewValue());
                ikkuna = nakyma.annaIkkuna();
                ikkuna.asetaMuutos(true);
                sarjanakyma = nakyma.annaSarjanakyma();
                sarjanakyma.luoOtteluLuetteloMuokkaus(sarja);

            }
        }
        );
        koti.setCellFactory(comboBoxCellFactory5);
        vieras.setCellFactory(comboBoxCellFactory5);

        koti.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Ottelu, Joukkue>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Ottelu, Joukkue> t) {

                Ottelu ottelu = ((Ottelu) t.getTableView().getItems().get(t.getTablePosition().getRow()));

                if (tarkistaja.kotijoukkueOK(t.getNewValue(), ottelu)) {

                    ((Ottelu) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())).asetaKotijoukkue(t.getNewValue());
                    ikkuna = nakyma.annaIkkuna();
                    ikkuna.asetaMuutos(true);
                }

                sarjanakyma = nakyma.annaSarjanakyma();
                sarjanakyma.luoOtteluLuetteloMuokkaus(sarja);

            }
        }
        );

        vieras.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Ottelu, Joukkue>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Ottelu, Joukkue> t) {
                Ottelu ottelu = ((Ottelu) t.getTableView().getItems().get(t.getTablePosition().getRow()));

                if (tarkistaja.vierasjoukkueOK(t.getNewValue(), ottelu)) {

                    ((Ottelu) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())).asetaVierasjoukkue(t.getNewValue());
                    ikkuna = nakyma.annaIkkuna();
                    ikkuna.asetaMuutos(true);
                }

                sarjanakyma = nakyma.annaSarjanakyma();
                sarjanakyma.luoOtteluLuetteloMuokkaus(sarja);
            }
        }
        );

        Callback<TableColumn, TableCell> dateCellFactory
                = new Callback<TableColumn, TableCell>() {
            public TableCell call(TableColumn p) {
                return new PaivaMuokkausSolu();
            }
        };

        paiva.setCellFactory(dateCellFactory);

        paiva.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Ottelu, Date>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Ottelu, Date> t) {
                ((Ottelu) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())).asetaPaivaDate(t.getNewValue());
                ikkuna = nakyma.annaIkkuna();
                ikkuna.asetaMuutos(true);
                sarjanakyma = nakyma.annaSarjanakyma();
                sarjanakyma.luoOtteluLuetteloMuokkaus(sarja);

            }
        }
        );

        col_action.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Record, Boolean>, ObservableValue<Boolean>>() {

            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Record, Boolean> p) {
                return new SimpleBooleanProperty(p.getValue() != null);
            }
        });

        col_action.setCellFactory(
                new Callback<TableColumn<Record, Boolean>, TableCell<Record, Boolean>>() {

            @Override
            public TableCell<Record, Boolean> call(TableColumn<Record, Boolean> p) {
                return new PoistoSoluOttelu(data, varmistaja);
            }

        });

        return taulukko;
    }

    public TableView luoSarjaTaulukko(Sarja sarja) {
        taulukko.setId("my-table");
        taulukko.setPlaceholder(new Label(""));

        List<Joukkue> joukkueet = new ArrayList<>();

        //haetaan sarjan kaikki joukkueet
        for (int i = 0; i < sarja.annaJoukkueet().size(); i++) {
            Joukkue joukkue = sarja.annaJoukkueet().get(i);

            joukkueet.add(joukkue);

        }

        // järjestetään pisteiden mukaiseen järjestykseen
        // lajitellaan kaikkien joukkueiden pelaajat pisteiden mukaiseen järjestykseen
        Joukkue suurin = new Joukkue();
        int pituus = joukkueet.size();

        for (int j = 0; j < pituus - 1; j++) {
            suurin = joukkueet.get(0);

            for (int i = 1; i < pituus - j; i++) {

                if (suurin.annaPisteet() < joukkueet.get(i).annaPisteet()) {
                    suurin = joukkueet.get(i);
                } // TASAPISTEISSÄ MAALIERO RATKAISEE
                else if (suurin.annaPisteet() == joukkueet.get(i).annaPisteet()) {
                    if (suurin.annaMaaliero() < joukkueet.get(i).annaMaaliero()) {
                        suurin = joukkueet.get(i);
                    }
                }

            }
            joukkueet.remove(suurin);
            //suurin menee vikaks
            joukkueet.add(suurin);

        }
        if (!joukkueet.isEmpty()) {
            Joukkue pienin = joukkueet.get(0);
            joukkueet.remove(pienin);
            joukkueet.add(pienin);
        }

        for (int i = 0; i < joukkueet.size(); i++) {

            Joukkue joukkue = joukkueet.get(i);

            joukkue.asetaTaulukkosijoitus(i + 1);
            joukkue.asetaTaulukkonimi();
            joukkue.asetaTaulukko_ottelut();
            joukkue.asetaTaulukkovoitot();
            joukkue.asetaTaulukkotasapelit();
            joukkue.asetaTaulukkohaviot();
            joukkue.asetaTaulukkomaalisuhde();
            joukkue.asetaTaulukkopisteet();

        }

        ObservableList<Joukkue> data
                = FXCollections.observableArrayList(joukkueet);

        TableColumn sijoitus = new TableColumn("Sij");
        TableColumn joukkue = new TableColumn("Joukkue");
        TableColumn ottelut = new TableColumn("Ott");
        TableColumn voitot = new TableColumn("V");
        TableColumn tasapelit = new TableColumn("T");
        TableColumn haviot = new TableColumn("H");
        TableColumn maalisuhde = new TableColumn("Tehdyt:Päästetyt");
        TableColumn pisteet = new TableColumn("Pis");

        taulukko.setFixedCellSize(25);

        joukkue.setCellValueFactory(new PropertyValueFactory<Joukkue, String>("taulukkonimi"));
        voitot.setCellValueFactory(new PropertyValueFactory<Joukkue, Integer>("taulukkovoitot"));
        haviot.setCellValueFactory(new PropertyValueFactory<Joukkue, Integer>("taulukkohaviot"));
        tasapelit.setCellValueFactory(new PropertyValueFactory<Joukkue, Integer>("taulukkotasapelit"));
        ottelut.setCellValueFactory(new PropertyValueFactory<Joukkue, Integer>("taulukko_ottelut"));
        maalisuhde.setCellValueFactory(new PropertyValueFactory<Joukkue, String>("taulukkomaalisuhde"));
        pisteet.setCellValueFactory(new PropertyValueFactory<Joukkue, Integer>("taulukkopisteet"));
        sijoitus.setCellValueFactory(new PropertyValueFactory<Joukkue, Integer>("taulukkosijoitus"));

        taulukko.getColumns().addAll(sijoitus, joukkue, ottelut, voitot, tasapelit, haviot, maalisuhde, pisteet);

        taulukko.setItems(data);
        maalisuhde.setMinWidth(120);
        joukkue.setMinWidth(180);

        sijoitus.setPrefWidth(50);
        ottelut.setPrefWidth(50);
        haviot.setPrefWidth(50);
        voitot.setPrefWidth(50);
        tasapelit.setPrefWidth(50);
        pisteet.setPrefWidth(50);
        sijoitus.setSortType(TableColumn.SortType.ASCENDING);
        taulukko.getSortOrder().add(sijoitus);

        taulukko.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            joukkuenakyma = nakyma.annaJoukkuenakyma();
            joukkuenakyma.luoJoukkueSivu((Joukkue) newSelection);
        });

        if (taulukko.getItems().size() == 0) {
            taulukko.prefHeightProperty().bind(taulukko.fixedCellSizeProperty().multiply(Bindings.size(taulukko.getItems()).add(2)));
        } else {
            taulukko.prefHeightProperty().bind(taulukko.fixedCellSizeProperty().multiply(Bindings.size(taulukko.getItems()).add(1.1)));
        }

        taulukko.minHeightProperty().bind(taulukko.prefHeightProperty());
        taulukko.maxHeightProperty().bind(taulukko.prefHeightProperty());

        return taulukko;
    }

    public TableView luoPisteporssiTaulukko(Sarja sarja) {
        taulukko.setId("my-table");
        taulukko.setPlaceholder(new Label(""));

        List<Pelaaja> pelaajat = new ArrayList<>();
        List<Joukkue> joukkueet = new ArrayList<>();
        List<Pelaaja> joukkueiden_pelaajat = new ArrayList<>();

        //haetaan kaikki joukkueet
        for (int j = 0; j < sarja.annaJoukkueet().size(); j++) {
            joukkueet.add(sarja.annaJoukkueet().get(j));

        }

        //haetaan kaikkien joukkuiden kaikki pelaajat
        for (int k = 0; k < joukkueet.size(); k++) {
            Joukkue joukkue = joukkueet.get(k);

            for (int i = 0; i < joukkue.annaPelaajat().size(); i++) {
                Pelaaja joukkueen_pelaaja = joukkue.annaPelaajat().get(i);
                joukkueiden_pelaajat.add(joukkueen_pelaaja);
            }
        }

        // lajitellaan kaikkien joukkueiden pelaajat pisteiden mukaiseen järjestykseen
        Pelaaja suurin = new Pelaaja();
        int pituus = joukkueiden_pelaajat.size();

        for (int j = 0; j < pituus - 1; j++) {
            suurin = joukkueiden_pelaajat.get(0);

            for (int i = 1; i < pituus - j; i++) {

                if (suurin.annaPisteet() < joukkueiden_pelaajat.get(i).annaPisteet()) {
                    suurin = joukkueiden_pelaajat.get(i);
                } // TASAPISTEISSÄ MAALIT RATKAISEE
                else if (suurin.annaPisteet() == joukkueiden_pelaajat.get(i).annaPisteet()) {
                    if (suurin.annaMaalit() < joukkueiden_pelaajat.get(i).annaMaalit()) {
                        suurin = joukkueiden_pelaajat.get(i);
                    }
                }

            }
            joukkueiden_pelaajat.remove(suurin);
            //suurin menee vikaks
            joukkueiden_pelaajat.add(suurin);

        }

        if (!joukkueiden_pelaajat.isEmpty()) {
            // pienin on jäänyt ekaks -> siirretään loppuun
            Pelaaja pienin = joukkueiden_pelaajat.get(0);
            joukkueiden_pelaajat.remove(pienin);
            joukkueiden_pelaajat.add(pienin);

        }

        for (int i = 0; i < joukkueiden_pelaajat.size(); i++) {
            pelaajat.add(joukkueiden_pelaajat.get(i));
        }

        //nyt pelaajat on oikeassa järjestyksessä
        for (int i = 0; i < pelaajat.size(); i++) {

            Pelaaja pelaaja = pelaajat.get(i);

            pelaaja.asetaTaulukkosijoitus(i + 1);
            pelaaja.asetaTaulukkomaalit();
            pelaaja.asetaTaulukkonimi();
            pelaaja.asetaTaulukkopisteet();
            pelaaja.asetaTaulukkosyotot();
            pelaaja.asetaTaulukkojoukkue();
            pelaaja.asetaTaulukko_ottelut();

        }

        ObservableList<Pelaaja> data
                = FXCollections.observableArrayList(pelaajat);

        TableColumn sijoitus = new TableColumn("Sij");
        TableColumn pelaaja = new TableColumn("Pelaaja");
        TableColumn joukkue = new TableColumn("Joukkue");
        TableColumn maalit = new TableColumn("Maalit");
        TableColumn syotot = new TableColumn("Syötöt");
        TableColumn pisteet = new TableColumn("Pisteet");
        TableColumn ottelut = new TableColumn("Ottelut");
        taulukko.setFixedCellSize(25);

        pelaaja.setCellValueFactory(new PropertyValueFactory<Pelaaja, String>("taulukkonimi"));
        joukkue.setCellValueFactory(new PropertyValueFactory<Pelaaja, String>("taulukkojoukkue"));
        maalit.setCellValueFactory(new PropertyValueFactory<Pelaaja, Integer>("taulukkomaalit"));
        syotot.setCellValueFactory(new PropertyValueFactory<Pelaaja, Integer>("taulukkosyotot"));
        pisteet.setCellValueFactory(new PropertyValueFactory<Pelaaja, Integer>("taulukkopisteet"));
        ottelut.setCellValueFactory(new PropertyValueFactory<Pelaaja, Integer>("taulukko_ottelut"));
        sijoitus.setCellValueFactory(new PropertyValueFactory<Pelaaja, Integer>("taulukkosijoitus"));
        taulukko.getColumns().addAll(sijoitus, pelaaja, joukkue, maalit, syotot, pisteet, ottelut);
        taulukko.setItems(data);
        pelaaja.setMinWidth(180);
        sijoitus.setPrefWidth(50);
        joukkue.setMinWidth(180);
        sijoitus.setSortType(TableColumn.SortType.ASCENDING);
        taulukko.getSortOrder().add(sijoitus);

        pelaaja.setPrefWidth(50);

        taulukko.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            pelaajanakyma = nakyma.annaPelaajanakyma();
            pelaajanakyma.luoPelaajaSivu((Pelaaja) newSelection);
        });

        taulukko.setFixedCellSize(25);

        if (taulukko.getItems().size() == 0) {
            taulukko.prefHeightProperty().bind(taulukko.fixedCellSizeProperty().multiply(Bindings.size(taulukko.getItems()).add(2)));
        } else {
            taulukko.prefHeightProperty().bind(taulukko.fixedCellSizeProperty().multiply(Bindings.size(taulukko.getItems()).add(1.1)));
        }

        taulukko.minHeightProperty().bind(taulukko.prefHeightProperty());
        taulukko.maxHeightProperty().bind(taulukko.prefHeightProperty());

        return taulukko;
    }

    public TableView luoToimihenkiloTaulukko(Joukkue joukkue) {
        taulukko.setId("my-table");
        taulukko.setPlaceholder(new Label("Ei lisättyjä toimihenkilöitä"));
        List<Toimihenkilo> toimarit = new ArrayList<>();

        for (int i = 0; i < joukkue.annaToimarit().size(); i++) {
            joukkue.annaToimarit().get(i).asetaTaulukkonimi();
            joukkue.annaToimarit().get(i).asetaTaulukkopuh();
            joukkue.annaToimarit().get(i).asetaTaulukkorooli();
            joukkue.annaToimarit().get(i).asetaTaulukkosposti();
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
        taulukko.getColumns().addAll(nimi, rooli, sposti, puh);
        taulukko.setItems(data);

        nimi.setMinWidth(180);
        rooli.setMinWidth(120);
        sposti.setMinWidth(180);
        puh.setMinWidth(120);
        nimi.setSortType(TableColumn.SortType.ASCENDING);
        taulukko.getSortOrder().add(nimi);

        taulukko.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            toimarinakyma = nakyma.annaToimarinakyma();
            toimarinakyma.luoToimariSivu((Toimihenkilo) newSelection);
        });

        taulukko.setFixedCellSize(25);

        if (taulukko.getItems().size() == 0) {
            taulukko.prefHeightProperty().bind(taulukko.fixedCellSizeProperty().multiply(Bindings.size(taulukko.getItems()).add(2)));
        } else {
            taulukko.prefHeightProperty().bind(taulukko.fixedCellSizeProperty().multiply(Bindings.size(taulukko.getItems()).add(1.1)));
        }

        taulukko.minHeightProperty().bind(taulukko.prefHeightProperty());
        taulukko.maxHeightProperty().bind(taulukko.prefHeightProperty());

        return taulukko;

    }

    public TableView luoToimihenkiloTaulukkoMuokattava(Joukkue joukkue) {
        taulukko.setId("ei-klikattava2");
        taulukko.setPlaceholder(new Label("Ei lisättyjä toimihenkilöitä"));
        List<Toimihenkilo> toimarit = new ArrayList<>();

        for (int i = 0; i < joukkue.annaToimarit().size(); i++) {
            joukkue.annaToimarit().get(i).asetaTaulukkoetunimi();
            joukkue.annaToimarit().get(i).asetaTaulukkosukunimi();
            joukkue.annaToimarit().get(i).asetaTaulukkopuh();
            joukkue.annaToimarit().get(i).asetaTaulukkorooli();
            joukkue.annaToimarit().get(i).asetaTaulukkosposti();
            toimarit.add(joukkue.annaToimarit().get(i));

        }

        ObservableList<Toimihenkilo> data
                = FXCollections.observableArrayList(toimarit);

        TableColumn etunimi = new TableColumn("Etunimi");
        TableColumn sukunimi = new TableColumn("Sukunimi");
        TableColumn rooli = new TableColumn("Rooli");
        TableColumn sposti = new TableColumn("Sähköposti");
        TableColumn puh = new TableColumn("Puh.");
        TableColumn col_action = new TableColumn<>("Poista");
        etunimi.setCellValueFactory(new PropertyValueFactory<Toimihenkilo, String>("taulukkoetunimi"));
        sukunimi.setCellValueFactory(new PropertyValueFactory<Toimihenkilo, String>("taulukkosukunimi"));
        rooli.setCellValueFactory(new PropertyValueFactory<Toimihenkilo, String>("taulukkorooli"));
        sposti.setCellValueFactory(new PropertyValueFactory<Toimihenkilo, String>("taulukkosposti"));
        puh.setCellValueFactory(new PropertyValueFactory<Toimihenkilo, String>("taulukkopuh"));
        taulukko.getColumns().addAll(etunimi, sukunimi, rooli, sposti, puh, col_action);
        taulukko.setItems(data);

        etunimi.setMinWidth(100);
        sukunimi.setMinWidth(100);
        rooli.setMinWidth(120);
        sposti.setMinWidth(180);
        puh.setMinWidth(120);
 col_action.setMinWidth(70);
        taulukko.setFixedCellSize(25);

        if (taulukko.getItems().size() == 0) {
            taulukko.prefHeightProperty().bind(taulukko.fixedCellSizeProperty().multiply(Bindings.size(taulukko.getItems()).add(2)));
        } else {
            taulukko.prefHeightProperty().bind(taulukko.fixedCellSizeProperty().multiply(Bindings.size(taulukko.getItems()).add(1.1)));
        }

        taulukko.minHeightProperty().bind(taulukko.prefHeightProperty());
        taulukko.maxHeightProperty().bind(taulukko.prefHeightProperty());

        taulukko.setEditable(true);

        Callback<TableColumn, TableCell> cellFactory
                = new Callback<TableColumn, TableCell>() {
            public TableCell call(TableColumn p) {
                return new MuokkausSoluToimari();
            }
        };
        etunimi.setCellFactory(cellFactory);
        etunimi.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Toimihenkilo, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Toimihenkilo, String> t) {
                if (tarkistaja.nimiOK(t.getNewValue())) {
                    ((Toimihenkilo) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())).asetaEtuNimi(t.getNewValue());
                    ikkuna = nakyma.annaIkkuna();
                    ikkuna.asetaMuutos(true);
                }

                joukkuenakyma = nakyma.annaJoukkuenakyma();
                joukkuenakyma.luoJoukkueenToimariLisays(joukkue);
            }
        }
        );

        sukunimi.setCellFactory(cellFactory);
        sukunimi.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Toimihenkilo, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Toimihenkilo, String> t) {
                if (tarkistaja.nimiOK(t.getNewValue())) {
                    ((Toimihenkilo) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())).asetaSukuNimi(t.getNewValue());
                    ikkuna = nakyma.annaIkkuna();
                    ikkuna.asetaMuutos(true);
                }

                joukkuenakyma = nakyma.annaJoukkuenakyma();
                joukkuenakyma.luoJoukkueenToimariLisays(joukkue);
            }
        }
        );

        sposti.setCellFactory(cellFactory);
        sposti.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Toimihenkilo, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Toimihenkilo, String> t) {
                ((Toimihenkilo) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())).asetaSposti(t.getNewValue());
                ikkuna = nakyma.annaIkkuna();
                ikkuna.asetaMuutos(true);
                joukkuenakyma = nakyma.annaJoukkuenakyma();
                joukkuenakyma.luoJoukkueenToimariLisays(joukkue);
            }
        }
        );
        rooli.setCellFactory(cellFactory);
        rooli.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Toimihenkilo, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Toimihenkilo, String> t) {
                ((Toimihenkilo) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())).asetaRooli(t.getNewValue());
                ikkuna = nakyma.annaIkkuna();
                ikkuna.asetaMuutos(true);
                joukkuenakyma = nakyma.annaJoukkuenakyma();
                joukkuenakyma.luoJoukkueenToimariLisays(joukkue);
            }
        }
        );
        puh.setCellFactory(cellFactory);
        puh.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Toimihenkilo, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Toimihenkilo, String> t) {
                ((Toimihenkilo) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())).asetaPuh(t.getNewValue());
                ikkuna = nakyma.annaIkkuna();
                ikkuna.asetaMuutos(true);
                joukkuenakyma = nakyma.annaJoukkuenakyma();
                joukkuenakyma.luoJoukkueenToimariLisays(joukkue);
            }
        }
        );
        col_action.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Record, Boolean>, ObservableValue<Boolean>>() {

            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Record, Boolean> p) {
                return new SimpleBooleanProperty(p.getValue() != null);
            }
        });

        col_action.setCellFactory(
                new Callback<TableColumn<Record, Boolean>, TableCell<Record, Boolean>>() {

            @Override
            public TableCell<Record, Boolean> call(TableColumn<Record, Boolean> p) {
                return new PoistoSoluToimari(data, varmistaja);
            }

        });
        return taulukko;

    }

    public TableView luoJoukkueTaulukko(Sarja sarja) {
        taulukko.setId("my-table");
        taulukko.setPlaceholder(new Label("Ei lisättyjä joukkueita"));
        List<Joukkue> joukkueet = new ArrayList<>();

        for (int i = 0; i < sarja.annaJoukkueet().size(); i++) {
            sarja.annaJoukkueet().get(i).asetaTaulukkonimi();
            joukkueet.add(sarja.annaJoukkueet().get(i));

        }

        ObservableList<Joukkue> data
                = FXCollections.observableArrayList(joukkueet);

        TableColumn joukkue = new TableColumn("Joukkue");

        joukkue.setCellValueFactory(new PropertyValueFactory<Joukkue, String>("taulukkonimi"));

        taulukko.getColumns().addAll(joukkue);
        taulukko.setItems(data);
        joukkue.setSortType(TableColumn.SortType.ASCENDING);
        taulukko.getSortOrder().add(joukkue);

        taulukko.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            joukkuenakyma = nakyma.annaJoukkuenakyma();
            joukkuenakyma.luoJoukkueSivu((Joukkue) newSelection);
        });
        joukkue.setMinWidth(180);
        taulukko.setFixedCellSize(25);

        if (taulukko.getItems().size() == 0) {
            taulukko.prefHeightProperty().bind(taulukko.fixedCellSizeProperty().multiply(Bindings.size(taulukko.getItems()).add(2)));
        } else {
            taulukko.prefHeightProperty().bind(taulukko.fixedCellSizeProperty().multiply(Bindings.size(taulukko.getItems()).add(1.1)));
        }

        taulukko.minHeightProperty().bind(taulukko.prefHeightProperty());
        taulukko.maxHeightProperty().bind(taulukko.prefHeightProperty());

        return taulukko;
    }

    public TableView luoJoukkueTaulukkoMuokattava(Sarja sarja) {
        taulukko.setId("ei-klikattava2");
        taulukko.setPlaceholder(new Label("Ei lisättyjä joukkueita"));
        List<Joukkue> joukkueet = new ArrayList<>();

        for (int i = 0; i < sarja.annaJoukkueet().size(); i++) {
            sarja.annaJoukkueet().get(i).asetaTaulukkonimi();

            joukkueet.add(sarja.annaJoukkueet().get(i));

        }

        ObservableList<Joukkue> data
                = FXCollections.observableArrayList(joukkueet);

        TableColumn joukkue = new TableColumn("Joukkue");
        TableColumn col_action = new TableColumn<>("Poista");
        joukkue.setCellValueFactory(new PropertyValueFactory<Joukkue, String>("taulukkonimi"));

        taulukko.getColumns().addAll(joukkue, col_action);
        taulukko.setItems(data);
        joukkue.setSortType(TableColumn.SortType.ASCENDING);
        taulukko.getSortOrder().add(joukkue);

        joukkue.setMinWidth(180);
        taulukko.setFixedCellSize(25);

        if (taulukko.getItems().size() == 0) {
            taulukko.prefHeightProperty().bind(taulukko.fixedCellSizeProperty().multiply(Bindings.size(taulukko.getItems()).add(2)));
        } else {
            taulukko.prefHeightProperty().bind(taulukko.fixedCellSizeProperty().multiply(Bindings.size(taulukko.getItems()).add(1.1)));
        }

        taulukko.minHeightProperty().bind(taulukko.prefHeightProperty());
        taulukko.maxHeightProperty().bind(taulukko.prefHeightProperty());
        taulukko.setEditable(true);
        Callback<TableColumn, TableCell> cellFactory
                = new Callback<TableColumn, TableCell>() {
            public TableCell call(TableColumn p) {
                return new MuokkausSoluOttelu();
            }
        };

        joukkue.setCellFactory(cellFactory);
        joukkue.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Joukkue, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Joukkue, String> t) {
                if (!tarkistaja.onTyhja(t.getNewValue())) {
                    ((Joukkue) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())).asetaNimi(t.getNewValue());
                    ikkuna = nakyma.annaIkkuna();
                    ikkuna.asetaMuutos(true);
                }

                sarjanakyma = nakyma.annaSarjanakyma();
                sarjanakyma.luoJoukkueenLisaysSivu(sarja);
            }
        }
        );

        col_action.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Record, Boolean>, ObservableValue<Boolean>>() {

            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Record, Boolean> p) {
                return new SimpleBooleanProperty(p.getValue() != null);
            }
        });

        col_action.setCellFactory(
                new Callback<TableColumn<Record, Boolean>, TableCell<Record, Boolean>>() {

            @Override
            public TableCell<Record, Boolean> call(TableColumn<Record, Boolean> p) {
                return new PoistoSoluJoukkue(data, varmistaja);
            }

        });

        return taulukko;
    }

    public TableView luoJoukkueenOtteluTaulukko(Joukkue joukkue) {
        taulukko.setPlaceholder(new Label("Ei lisättyjä otteluita"));
        taulukko.setId("my-table");
        List<Ottelu> ottelut = new ArrayList<>();

        for (int i = 0; i < joukkue.annaOttelut().size(); i++) {
          joukkue.annaOttelut().get(i).asetaTaulukkopaivastring();
            joukkue.annaOttelut().get(i).asetaTaulukkokello();
            joukkue.annaOttelut().get(i).asetaTaulukkoid();
            joukkue.annaOttelut().get(i).asetaTaulukkonimi();
            joukkue.annaOttelut().get(i).asetaTaulukkopaikka();
            joukkue.annaOttelut().get(i).asetaTaulukkotulos();
            joukkue.annaOttelut().get(i).asetaTaulukkotuomarit();
            joukkue.annaOttelut().get(i).asetaTaulukkokierros();
            ottelut.add(joukkue.annaOttelut().get(i));

        }

        ObservableList<Ottelu> data
                = FXCollections.observableArrayList(ottelut);

        TableColumn id = new TableColumn("ID");
        TableColumn kierros = new TableColumn("Kierros");
        TableColumn ottelu = new TableColumn("Ottelu");
        TableColumn ajankohta = new TableColumn("Ajankohta");
        TableColumn paiva = new TableColumn("Päivä");
        TableColumn kello = new TableColumn("Klo");
        ajankohta.getColumns().addAll(paiva, kello);
        TableColumn paikka = new TableColumn("Paikka");
        TableColumn tuomarit = new TableColumn("Tuomarit");
        TableColumn erotuomari = new TableColumn("Erotuomari");
        TableColumn avustava1 = new TableColumn("1. Avustava");
        TableColumn avustava2 = new TableColumn("2. Avustava");
        tuomarit.getColumns().addAll(erotuomari, avustava1, avustava2);
        TableColumn tulos = new TableColumn("Tulos");

        id.setCellValueFactory(new PropertyValueFactory<Ottelu, Integer>("taulukkoid"));
        kierros.setCellValueFactory(new PropertyValueFactory<Ottelu, Integer>("taulukkokierros"));
        ottelu.setCellValueFactory(new PropertyValueFactory<Ottelu, String>("taulukkonimi"));
      paiva.setCellValueFactory(new PropertyValueFactory<Ottelu, String>("taulukkopaivastring"));
        kello.setCellValueFactory(new PropertyValueFactory<Ottelu, String>("taulukkokello"));
        paikka.setCellValueFactory(new PropertyValueFactory<Ottelu, String>("taulukkopaikka"));
        erotuomari.setCellValueFactory(new PropertyValueFactory<Ottelu, String>("taulukkoerotuomari"));
        avustava1.setCellValueFactory(new PropertyValueFactory<Ottelu, String>("taulukkoavustava1"));
        avustava2.setCellValueFactory(new PropertyValueFactory<Ottelu, String>("taulukkoavustava2"));
        tulos.setCellValueFactory(new PropertyValueFactory<Ottelu, String>("taulukkotulos"));
        avustava1.prefWidthProperty().bind(avustava1.widthProperty());
        taulukko.getColumns().addAll(id, kierros, ottelu, ajankohta, paikka, tuomarit, tulos);
        taulukko.setItems(data);
        kello.setSortType(TableColumn.SortType.ASCENDING);
        taulukko.getSortOrder().add(paiva);
        taulukko.getSortOrder().add(kello);
        taulukko.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            ottelunakyma = nakyma.annaOttelunakyma();
            ottelunakyma.luoOttelusivu((Ottelu) newSelection);
        });

        id.setPrefWidth(50);
        kierros.setPrefWidth(70);
        ottelu.setMinWidth(200);
        paikka.setMinWidth(200);
        erotuomari.setMinWidth(150);
        avustava1.setMinWidth(150);
        avustava2.setMinWidth(150);
        tulos.setPrefWidth(50);

        taulukko.setFixedCellSize(25);

        if (taulukko.getItems().size() == 0) {
            taulukko.prefHeightProperty().bind(taulukko.fixedCellSizeProperty().multiply(Bindings.size(taulukko.getItems()).add(3)));
        } else {
            taulukko.prefHeightProperty().bind(taulukko.fixedCellSizeProperty().multiply(Bindings.size(taulukko.getItems()).add(2.1)));
        }

        taulukko.minHeightProperty().bind(taulukko.prefHeightProperty());
        taulukko.maxHeightProperty().bind(taulukko.prefHeightProperty());

        return taulukko;
    }

    public TableView luoOttelunTaulukko(Ottelu ottelu) {
        taulukko.setPlaceholder(new Label(""));
        taulukko.setId("ei-klikattava");
        List<Ottelu> ottelut = new ArrayList<>();

        ottelu.asetaTaulukkopaivastring();
        ottelu.asetaTaulukkokello();
        ottelu.asetaTaulukkoid();
        ottelu.asetaTaulukkonimi();
        ottelu.asetaTaulukkopaikka();
        ottelu.asetaTaulukkotulos();
        ottelu.asetaTaulukkotuomarit();
        ottelu.asetaTaulukkokierros();
        ottelut.add(ottelu);

        ObservableList<Ottelu> data
                = FXCollections.observableArrayList(ottelut);

        TableColumn id = new TableColumn("ID");
        TableColumn kierros = new TableColumn("Kierros");
        TableColumn otteluc = new TableColumn("Ottelu");
        TableColumn ajankohta = new TableColumn("Ajankohta");
        TableColumn paiva = new TableColumn("Päivä");
        TableColumn kello = new TableColumn("Klo");
        ajankohta.getColumns().addAll(paiva, kello);
        TableColumn paikka = new TableColumn("Paikka");
        TableColumn tuomarit = new TableColumn("Tuomarit");
        TableColumn erotuomari = new TableColumn("Erotuomari");
        TableColumn avustava1 = new TableColumn("1. Avustava");
        TableColumn avustava2 = new TableColumn("2. Avustava");
        tuomarit.getColumns().addAll(erotuomari, avustava1, avustava2);
        TableColumn tulos = new TableColumn("Tulos");

        id.setCellValueFactory(new PropertyValueFactory<Ottelu, Integer>("taulukkoid"));
        kierros.setCellValueFactory(new PropertyValueFactory<Ottelu, Integer>("taulukkokierros"));
        otteluc.setCellValueFactory(new PropertyValueFactory<Ottelu, String>("taulukkonimi"));
              paiva.setCellValueFactory(new PropertyValueFactory<Ottelu, String>("taulukkopaivastring"));
        kello.setCellValueFactory(new PropertyValueFactory<Ottelu, String>("taulukkokello"));
        paikka.setCellValueFactory(new PropertyValueFactory<Ottelu, String>("taulukkopaikka"));
        erotuomari.setCellValueFactory(new PropertyValueFactory<Ottelu, String>("taulukkoerotuomari"));
        avustava1.setCellValueFactory(new PropertyValueFactory<Ottelu, String>("taulukkoavustava1"));
        avustava2.setCellValueFactory(new PropertyValueFactory<Ottelu, String>("taulukkoavustava2"));
        tulos.setCellValueFactory(new PropertyValueFactory<Ottelu, String>("taulukkotulos"));
        avustava1.prefWidthProperty().bind(avustava1.widthProperty());
        taulukko.getColumns().addAll(id, kierros, otteluc, ajankohta, paikka, tuomarit, tulos);
        taulukko.setItems(data);

        taulukko.setFixedCellSize(25);
        id.setPrefWidth(50);
        kierros.setPrefWidth(70);
        otteluc.setMinWidth(200);
        paikka.setMinWidth(200);
        erotuomari.setMinWidth(150);
        avustava1.setMinWidth(150);
        avustava2.setMinWidth(150);
        tulos.setPrefWidth(50);
        if (taulukko.getItems().size() == 0) {
            taulukko.prefHeightProperty().bind(taulukko.fixedCellSizeProperty().multiply(Bindings.size(taulukko.getItems()).add(3)));
        } else {
            taulukko.prefHeightProperty().bind(taulukko.fixedCellSizeProperty().multiply(Bindings.size(taulukko.getItems()).add(2.1)));
        }

        taulukko.minHeightProperty().bind(taulukko.prefHeightProperty());
        taulukko.maxHeightProperty().bind(taulukko.prefHeightProperty());

        return taulukko;
    }

    public TableView luoPelaajaTaulukko(Joukkue joukkue) {
        taulukko.setPlaceholder(new Label("Ei lisättyjä pelaajia"));
        taulukko.setId("my-table");
        List<Pelaaja> pelaajat = new ArrayList<>();

        for (int i = 0; i < joukkue.annaPelaajat().size(); i++) {
            joukkue.annaPelaajat().get(i).asetaTaulukkonimi();
            joukkue.annaPelaajat().get(i).asetaTaulukkonumero();
            joukkue.annaPelaajat().get(i).asetaTaulukkopelipaikka();
            pelaajat.add(joukkue.annaPelaajat().get(i));

        }

        ObservableList<Pelaaja> data
                = FXCollections.observableArrayList(pelaajat);

        TableColumn pelaaja = new TableColumn("Pelaaja");
        TableColumn pelinumero = new TableColumn("Nro");
        TableColumn pelipaikka = new TableColumn("Pelipaikka");
        pelaaja.setMinWidth(180);
        pelinumero.setPrefWidth(50);
        pelaaja.setCellValueFactory(new PropertyValueFactory<Pelaaja, String>("taulukkonimi"));
        pelinumero.setCellValueFactory(new PropertyValueFactory<Pelaaja, Integer>("taulukkonumero"));
        pelipaikka.setCellValueFactory(new PropertyValueFactory<Pelaaja, String>("taulukkopelipaikka"));

        pelipaikka.setPrefWidth(100);
        taulukko.getColumns().addAll(pelinumero, pelaaja, pelipaikka);
        taulukko.setItems(data);
        pelinumero.setSortType(TableColumn.SortType.ASCENDING);
        taulukko.getSortOrder().add(pelinumero);

        taulukko.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {

            pelaajanakyma = nakyma.annaPelaajanakyma();
            pelaajanakyma.luoPelaajaSivu((Pelaaja) newSelection);
        });

        taulukko.setFixedCellSize(25);

        if (taulukko.getItems().size() == 0) {
            taulukko.prefHeightProperty().bind(taulukko.fixedCellSizeProperty().multiply(Bindings.size(taulukko.getItems()).add(2)));
        } else {
            taulukko.prefHeightProperty().bind(taulukko.fixedCellSizeProperty().multiply(Bindings.size(taulukko.getItems()).add(1.1)));
        }

        taulukko.minHeightProperty().bind(taulukko.prefHeightProperty());
        taulukko.maxHeightProperty().bind(taulukko.prefHeightProperty());

        return taulukko;
    }

    public TableView luoPelaajaTaulukkoMuokattava(Joukkue joukkue) {
        taulukko.setPlaceholder(new Label("Ei lisättyjä pelaajia"));
        taulukko.setId("ei-klikattava2");
        List<Pelaaja> pelaajat = new ArrayList<>();

        for (int i = 0; i < joukkue.annaPelaajat().size(); i++) {
            joukkue.annaPelaajat().get(i).asetaTaulukkoetunimi();
            joukkue.annaPelaajat().get(i).asetaTaulukkosukunimi();
            joukkue.annaPelaajat().get(i).asetaTaulukkonumero();
            joukkue.annaPelaajat().get(i).asetaTaulukkopelipaikka();
            pelaajat.add(joukkue.annaPelaajat().get(i));

        }

        ObservableList<Pelaaja> data
                = FXCollections.observableArrayList(pelaajat);

        TableColumn etunimi = new TableColumn("Etunimi");
        TableColumn sukunimi = new TableColumn("Sukunimi");
        TableColumn pelinumero = new TableColumn("Nro");
        TableColumn pelipaikka = new TableColumn("Pelipaikka");
        TableColumn col_action = new TableColumn<>("Poista");
        etunimi.setMinWidth(100);
        sukunimi.setMinWidth(100);
        pelinumero.setPrefWidth(50);
        etunimi.setCellValueFactory(new PropertyValueFactory<Pelaaja, String>("taulukkoetunimi"));
        sukunimi.setCellValueFactory(new PropertyValueFactory<Pelaaja, String>("taulukkosukunimi"));
        pelinumero.setCellValueFactory(new PropertyValueFactory<Pelaaja, Integer>("taulukkonumero"));
        pelipaikka.setCellValueFactory(new PropertyValueFactory<Pelaaja, String>("taulukkopelipaikka"));

        pelipaikka.setPrefWidth(100);
        taulukko.getColumns().addAll(pelinumero, etunimi, sukunimi, pelipaikka, col_action);
        taulukko.setItems(data);
        pelinumero.setSortType(TableColumn.SortType.ASCENDING);
        taulukko.getSortOrder().add(pelinumero);

        taulukko.setFixedCellSize(25);

        if (taulukko.getItems().size() == 0) {
            taulukko.prefHeightProperty().bind(taulukko.fixedCellSizeProperty().multiply(Bindings.size(taulukko.getItems()).add(2)));
        } else {
            taulukko.prefHeightProperty().bind(taulukko.fixedCellSizeProperty().multiply(Bindings.size(taulukko.getItems()).add(1.1)));
        }

        taulukko.minHeightProperty().bind(taulukko.prefHeightProperty());
        taulukko.maxHeightProperty().bind(taulukko.prefHeightProperty());
        taulukko.setEditable(true);

        Callback<TableColumn, TableCell> cellFactory
                = new Callback<TableColumn, TableCell>() {
            public TableCell call(TableColumn p) {
                return new MuokkausSoluPelaaja();
            }
        };

        Callback<TableColumn, TableCell> comboBoxCellFactory
                = new Callback<TableColumn, TableCell>() {
            public TableCell call(TableColumn p) {
                return new ComboBoxPelaaja();
            }
        };

        etunimi.setCellFactory(cellFactory);
        etunimi.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Pelaaja, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Pelaaja, String> t) {

                if (tarkistaja.nimiOK(t.getNewValue())) {
                    ((Pelaaja) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())).asetaEtuNimi(t.getNewValue());
                    ikkuna = nakyma.annaIkkuna();
                    ikkuna.asetaMuutos(true);
                }

                joukkuenakyma = nakyma.annaJoukkuenakyma();
                joukkuenakyma.luoJoukkueenPelaajaLisays(joukkue);
            }
        }
        );
        sukunimi.setCellFactory(cellFactory);
        sukunimi.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Pelaaja, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Pelaaja, String> t) {

                if (tarkistaja.nimiOK(t.getNewValue())) {
                    ((Pelaaja) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())).asetaSukuNimi(t.getNewValue());

                    ikkuna = nakyma.annaIkkuna();
                    ikkuna.asetaMuutos(true);
                }

                joukkuenakyma = nakyma.annaJoukkuenakyma();
                joukkuenakyma.luoJoukkueenPelaajaLisays(joukkue);
            }
        }
        );
             pelipaikka.setCellFactory(cellFactory);
        pelipaikka.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Pelaaja, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Pelaaja, String> t) {

                if (tarkistaja.nimiOK(t.getNewValue())) {
                    ((Pelaaja) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())).asetaPelipaikka(t.getNewValue());

                    ikkuna = nakyma.annaIkkuna();
                    ikkuna.asetaMuutos(true);
                }

                joukkuenakyma = nakyma.annaJoukkuenakyma();
                joukkuenakyma.luoJoukkueenPelaajaLisays(joukkue);
            }
        }
        );
        

        pelinumero.setCellFactory(comboBoxCellFactory);
        pelinumero.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Pelaaja, Integer>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Pelaaja, Integer> t) {

                if (tarkistaja.pelinumeroOK(t.getNewValue(), joukkue)) {

                    ((Pelaaja) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())).asetaPelinumero(t.getNewValue());

                    ikkuna = nakyma.annaIkkuna();
                    ikkuna.asetaMuutos(true);
                }

                joukkuenakyma = nakyma.annaJoukkuenakyma();
                joukkuenakyma.luoJoukkueenPelaajaLisays(joukkue);
            }
        }
        );

        col_action.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Record, Boolean>, ObservableValue<Boolean>>() {

            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Record, Boolean> p) {
                return new SimpleBooleanProperty(p.getValue() != null);
            }
        });

        col_action.setCellFactory(
                new Callback<TableColumn<Record, Boolean>, TableCell<Record, Boolean>>() {

            @Override
            public TableCell<Record, Boolean> call(TableColumn<Record, Boolean> p) {
                return new PoistoSoluPelaaja(data, varmistaja);
            }

        });

        return taulukko;
    }

    public TableView luoJoukkueenPisteporssiTaulukko(Joukkue joukkue) {
        taulukko.setId("my-table");
        taulukko.setPlaceholder(new Label(""));

        List<Pelaaja> pelaajat = new ArrayList<>();

        //haetaan kaikki joukkueen pelaajat
        for (int i = 0; i < joukkue.annaPelaajat().size(); i++) {
            Pelaaja pelaaja = joukkue.annaPelaajat().get(i);

            pelaajat.add(pelaaja);

        }

        // järjestetään pelaajat pisteiden mukaiseen järjestykseen
        Pelaaja suurin = new Pelaaja();
        int pituus = pelaajat.size();

        for (int j = 0; j < pituus - 1; j++) {
            suurin = pelaajat.get(0);

            for (int i = 1; i < pituus - j; i++) {

                if (suurin.annaPisteet() < pelaajat.get(i).annaPisteet()) {
                    suurin = pelaajat.get(i);
                } // TASAPISTEISSÄ MAALIT RATKAISEE
                else if (suurin.annaPisteet() == pelaajat.get(i).annaPisteet()) {
                    if (suurin.annaMaalit() < pelaajat.get(i).annaMaalit()) {
                        suurin = pelaajat.get(i);
                    }
                }

            }
            pelaajat.remove(suurin);
            //suurin menee vikaks
            pelaajat.add(suurin);

        }
        if (!pelaajat.isEmpty()) {
            // pienin on jäänyt ekaks -> siirretään loppuun
            Pelaaja pienin = pelaajat.get(0);
            pelaajat.remove(pienin);
            pelaajat.add(pienin);
        }

        // nyt on oikea järkkä
        for (int i = 0; i < pelaajat.size(); i++) {

            Pelaaja pelaaja = pelaajat.get(i);

            pelaaja.asetaTaulukkosijoitus(i + 1);
            pelaaja.asetaTaulukkomaalit();
            pelaaja.asetaTaulukkonimi();
            pelaaja.asetaTaulukkopisteet();
            pelaaja.asetaTaulukkosyotot();
            pelaaja.asetaTaulukko_ottelut();

        }

        ObservableList<Pelaaja> data
                = FXCollections.observableArrayList(pelaajat);

        TableColumn sijoitus = new TableColumn("Sij");
        TableColumn pelaaja = new TableColumn("Pelaaja");
        TableColumn maalit = new TableColumn("Maalit");
        TableColumn syotot = new TableColumn("Syötöt");
        TableColumn pisteet = new TableColumn("Pis");
        TableColumn ottelut = new TableColumn("Ott");
        taulukko.setFixedCellSize(25);

        pelaaja.setCellValueFactory(new PropertyValueFactory<Pelaaja, String>("taulukkonimi"));
        maalit.setCellValueFactory(new PropertyValueFactory<Pelaaja, Integer>("taulukkomaalit"));
        syotot.setCellValueFactory(new PropertyValueFactory<Pelaaja, Integer>("taulukkosyotot"));
        pisteet.setCellValueFactory(new PropertyValueFactory<Pelaaja, Integer>("taulukkopisteet"));
        ottelut.setCellValueFactory(new PropertyValueFactory<Pelaaja, Integer>("taulukko_ottelut"));
        sijoitus.setCellValueFactory(new PropertyValueFactory<Pelaaja, Integer>("taulukkosijoitus"));
        taulukko.getColumns().addAll(sijoitus, pelaaja, maalit, syotot, pisteet, ottelut);
        taulukko.setItems(data);
        sijoitus.setPrefWidth(50);
        pelaaja.setMinWidth(180);
        sijoitus.setPrefWidth(50);
        ottelut.setPrefWidth(50);
        pisteet.setPrefWidth(50);
        maalit.setPrefWidth(50);
        syotot.setPrefWidth(50);
        sijoitus.setSortType(TableColumn.SortType.ASCENDING);
        taulukko.getSortOrder().add(sijoitus);
        taulukko.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            pelaajanakyma = nakyma.annaPelaajanakyma();
            pelaajanakyma.luoPelaajaSivu((Pelaaja) newSelection);
        });
        taulukko.setFixedCellSize(25);

        if (taulukko.getItems().size() == 0) {
            taulukko.prefHeightProperty().bind(taulukko.fixedCellSizeProperty().multiply(Bindings.size(taulukko.getItems()).add(2)));
        } else {
            taulukko.prefHeightProperty().bind(taulukko.fixedCellSizeProperty().multiply(Bindings.size(taulukko.getItems()).add(1.1)));
        }

        taulukko.minHeightProperty().bind(taulukko.prefHeightProperty());
        taulukko.maxHeightProperty().bind(taulukko.prefHeightProperty());

        return taulukko;

    }

    public TableView luoTuomarinOtteluTaulukko(Tuomari tuomari) {

        taulukko.setPlaceholder(new Label("Ei lisättyjä otteluita"));
        taulukko.setId("my-table");

        List<Ottelu> ottelut = new ArrayList<>();

        for (int i = 0; i < tuomari.annaTuomarinRoolit().size(); i++) {
            if (tuomari.annaTuomarinRoolit().get(i).annaTuomari().equals(tuomari)) {
                tuomari.annaTuomarinRoolit().get(i).annaOttelu().asetaTaulukkoid();
                tuomari.annaTuomarinRoolit().get(i).annaOttelu().asetaTaulukkopaikka();
                tuomari.annaTuomarinRoolit().get(i).annaOttelu().asetaTaulukkonimi();
                tuomari.annaTuomarinRoolit().get(i).annaOttelu().asetaTaulukkotulos();
                tuomari.annaTuomarinRoolit().get(i).annaOttelu().asetaTaulukkopaivastring();
                tuomari.annaTuomarinRoolit().get(i).annaOttelu().asetaTaulukkokello();
                tuomari.annaTuomarinRoolit().get(i).annaOttelu().asetaTaulukkotuomarit();
                ottelut.add(tuomari.annaTuomarinRoolit().get(i).annaOttelu());
            }

        }

        ObservableList<Ottelu> data
                = FXCollections.observableArrayList(ottelut);

        TableColumn id = new TableColumn("ID");
        TableColumn ottelu = new TableColumn("Ottelu");
        TableColumn ajankohta = new TableColumn("Ajankohta");
        TableColumn paiva = new TableColumn("Päivä");
        TableColumn kello = new TableColumn("Klo");
        ajankohta.getColumns().addAll(paiva, kello);
        TableColumn paikka = new TableColumn("Paikka");
        TableColumn tuomarit = new TableColumn("Tuomarit");
        TableColumn erotuomari = new TableColumn("Erotuomari");
        TableColumn avustava1 = new TableColumn("1. Avustava");
        TableColumn avustava2 = new TableColumn("2. Avustava");
        tuomarit.getColumns().addAll(erotuomari, avustava1, avustava2);
        TableColumn tulos = new TableColumn("Tulos");

        id.setCellValueFactory(new PropertyValueFactory<Ottelu, Integer>("taulukkoid"));
        ottelu.setCellValueFactory(new PropertyValueFactory<Ottelu, String>("taulukkonimi"));
          paiva.setCellValueFactory(new PropertyValueFactory<Ottelu, String>("taulukkopaivastring"));
        kello.setCellValueFactory(new PropertyValueFactory<Ottelu, String>("taulukkokello"));
        paikka.setCellValueFactory(new PropertyValueFactory<Ottelu, String>("taulukkopaikka"));
        erotuomari.setCellValueFactory(new PropertyValueFactory<Ottelu, String>("taulukkoerotuomari"));
        avustava1.setCellValueFactory(new PropertyValueFactory<Ottelu, String>("taulukkoavustava1"));
        avustava2.setCellValueFactory(new PropertyValueFactory<Ottelu, String>("taulukkoavustava2"));
        tulos.setCellValueFactory(new PropertyValueFactory<Ottelu, String>("taulukkotulos"));
        taulukko.getColumns().addAll(id, ottelu, ajankohta, paikka, tuomarit, tulos);
        taulukko.setItems(data);

        id.setPrefWidth(50);

        ottelu.setMinWidth(200);
        paikka.setMinWidth(200);
        erotuomari.setMinWidth(150);
        avustava1.setMinWidth(150);
        avustava2.setMinWidth(150);
        tulos.setPrefWidth(50);

        paiva.setSortType(TableColumn.SortType.ASCENDING);
        taulukko.getSortOrder().add(paiva);
        taulukko.getSortOrder().add(kello);

        taulukko.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            ottelunakyma = nakyma.annaOttelunakyma();
            ottelunakyma.luoOttelusivu((Ottelu) newSelection);
        });

        taulukko.setFixedCellSize(25);

        if (taulukko.getItems().size() == 0) {
            taulukko.prefHeightProperty().bind(taulukko.fixedCellSizeProperty().multiply(Bindings.size(taulukko.getItems()).add(3)));
        } else {
            taulukko.prefHeightProperty().bind(taulukko.fixedCellSizeProperty().multiply(Bindings.size(taulukko.getItems()).add(2)));
        }

        taulukko.minHeightProperty().bind(taulukko.prefHeightProperty());
        taulukko.maxHeightProperty().bind(taulukko.prefHeightProperty());

        return taulukko;
    }

    public TableView luoPelaajanPisteTaulukko(Pelaaja pelaaja) {
        taulukko.setId("ei-klikattava");
        taulukko.setPlaceholder(new Label(""));

        pelaaja.asetaTaulukko_ottelut();
        pelaaja.asetaTaulukkomaalit();
        pelaaja.asetaTaulukkosyotot();
        pelaaja.asetaTaulukkopisteet();

        List<Pelaaja> pelaajat = new ArrayList<>();

        pelaajat.add(pelaaja);

        ObservableList<Pelaaja> data
                = FXCollections.observableArrayList(pelaajat);

        TableColumn ottelut = new TableColumn("Ottelut");
        TableColumn maalit = new TableColumn("Maalit");
        TableColumn syotot = new TableColumn("Syötöt");
        TableColumn pisteet = new TableColumn("Pisteet");

        ottelut.setCellValueFactory(new PropertyValueFactory<Pelaaja, Integer>("taulukko_ottelut"));
        maalit.setCellValueFactory(new PropertyValueFactory<Pelaaja, Integer>("taulukkomaalit"));
        syotot.setCellValueFactory(new PropertyValueFactory<Pelaaja, Integer>("taulukkosyotot"));
        pisteet.setCellValueFactory(new PropertyValueFactory<Pelaaja, Integer>("taulukkopisteet"));

        taulukko.getColumns().addAll(ottelut, maalit, syotot, pisteet);
        taulukko.setItems(data);
        pisteet.setSortType(TableColumn.SortType.ASCENDING);
        taulukko.getSortOrder().add(pisteet);

        taulukko.setFixedCellSize(25);

        if (taulukko.getItems().size() == 0) {
            taulukko.prefHeightProperty().bind(taulukko.fixedCellSizeProperty().multiply(Bindings.size(taulukko.getItems()).add(2)));
        } else {
            taulukko.prefHeightProperty().bind(taulukko.fixedCellSizeProperty().multiply(Bindings.size(taulukko.getItems()).add(1.1)));
        }

        taulukko.minHeightProperty().bind(taulukko.prefHeightProperty());
        taulukko.maxHeightProperty().bind(taulukko.prefHeightProperty());

        return taulukko;
    }

    public TableView luoKotiKokoonpanoTaulukko(Ottelu ottelu) {
        taulukko.setPlaceholder(new Label("Ei lisättyjä pelaajia"));
        taulukko.setId("ei-klikattava");

        Kokoonpano kotikokoonpano = ottelu.annaKotiKokoonpano();

        List<Pelaaja> pelaajat = new ArrayList<Pelaaja>();

        for (int i = 0; i < kotikokoonpano.annaPelaajat().size(); i++) {
            kotikokoonpano.annaPelaajat().get(i).asetaTaulukkonimi();
            kotikokoonpano.annaPelaajat().get(i).asetaTaulukkonumero();
            kotikokoonpano.annaPelaajat().get(i).asetaTaulukkopelipaikka();
            kotikokoonpano.annaPelaajat().get(i).asetaTaulukkorooli(ottelu);
            pelaajat.add(kotikokoonpano.annaPelaajat().get(i));

        }

        ObservableList<Pelaaja> data
                = FXCollections.observableArrayList(pelaajat);

        TableColumn pelaaja = new TableColumn("Pelaaja");
        TableColumn pelinumero = new TableColumn("Pelinumero");
        TableColumn pelipaikka = new TableColumn("Pelipaikka");
        TableColumn rooli = new TableColumn("Rooli ottelussa");

        pelaaja.setCellValueFactory(new PropertyValueFactory<Pelaaja, String>("taulukkonimi"));
        pelinumero.setCellValueFactory(new PropertyValueFactory<Pelaaja, Integer>("taulukkonumero"));
        pelipaikka.setCellValueFactory(new PropertyValueFactory<Pelaaja, String>("taulukkopelipaikka"));
        rooli.setCellValueFactory(new PropertyValueFactory<Pelaaja, String>("taulukkorooli"));

        pelaaja.setPrefWidth(10);
        pelinumero.setPrefWidth(10);
        pelipaikka.setPrefWidth(10);
        taulukko.getColumns().addAll(pelinumero, pelaaja, rooli, pelipaikka);
        taulukko.setItems(data);
        pelinumero.setSortType(TableColumn.SortType.ASCENDING);
        taulukko.getSortOrder().add(pelinumero);

        taulukko.setFixedCellSize(25);

        if (taulukko.getItems().size() == 0) {
            taulukko.prefHeightProperty().bind(taulukko.fixedCellSizeProperty().multiply(Bindings.size(taulukko.getItems()).add(2)));
        } else {
            taulukko.prefHeightProperty().bind(taulukko.fixedCellSizeProperty().multiply(Bindings.size(taulukko.getItems()).add(1.1)));
        }

        taulukko.minHeightProperty().bind(taulukko.prefHeightProperty());
        taulukko.maxHeightProperty().bind(taulukko.prefHeightProperty());
        return taulukko;
    }

    public TableView luoVierasKokoonpanoTaulukko(Ottelu ottelu) {

        taulukko.setPlaceholder(new Label("Ei lisättyjä pelaajia"));
        taulukko.setId("ei-klikattava");

        Kokoonpano vieraskokoonpano = ottelu.annaVierasKokoonpano();

        List<Pelaaja> pelaajat = new ArrayList<Pelaaja>();

        for (int i = 0; i < vieraskokoonpano.annaPelaajat().size(); i++) {
            vieraskokoonpano.annaPelaajat().get(i).asetaTaulukkonimi();
            vieraskokoonpano.annaPelaajat().get(i).asetaTaulukkonumero();
            vieraskokoonpano.annaPelaajat().get(i).asetaTaulukkopelipaikka();
            vieraskokoonpano.annaPelaajat().get(i).asetaTaulukkorooli(ottelu);
            pelaajat.add(vieraskokoonpano.annaPelaajat().get(i));

        }

        ObservableList<Pelaaja> data
                = FXCollections.observableArrayList(pelaajat);

        TableColumn pelaaja = new TableColumn("Pelaaja");
        TableColumn pelinumero = new TableColumn("Pelinumero");
        TableColumn pelipaikka = new TableColumn("Pelipaikka");
        TableColumn rooli = new TableColumn("Rooli ottelussa");

        pelaaja.setCellValueFactory(new PropertyValueFactory<Pelaaja, String>("taulukkonimi"));
        pelinumero.setCellValueFactory(new PropertyValueFactory<Pelaaja, Integer>("taulukkonumero"));
        pelipaikka.setCellValueFactory(new PropertyValueFactory<Pelaaja, String>("taulukkopelipaikka"));
        rooli.setCellValueFactory(new PropertyValueFactory<Pelaaja, String>("taulukkorooli"));

        pelaaja.setPrefWidth(10);
        pelinumero.setPrefWidth(10);
        pelipaikka.setPrefWidth(10);
        taulukko.getColumns().addAll(pelinumero, pelaaja, rooli, pelipaikka);
        taulukko.setItems(data);
        pelinumero.setSortType(TableColumn.SortType.ASCENDING);
        taulukko.getSortOrder().add(pelinumero);

        taulukko.setFixedCellSize(25);

        if (taulukko.getItems().size() == 0) {
            taulukko.prefHeightProperty().bind(taulukko.fixedCellSizeProperty().multiply(Bindings.size(taulukko.getItems()).add(2)));
        } else {
            taulukko.prefHeightProperty().bind(taulukko.fixedCellSizeProperty().multiply(Bindings.size(taulukko.getItems()).add(1.1)));
        }

        taulukko.minHeightProperty().bind(taulukko.prefHeightProperty());
        taulukko.maxHeightProperty().bind(taulukko.prefHeightProperty());
        return taulukko;
    }
     public TableView luoOttelunMaaliTaulukko(Ottelu ottelu) {
        taulukko.setPlaceholder(new Label(""));
        taulukko.setId("ei-klikattava");

        List<Maali> maalit = new ArrayList<Maali>();

        for (int i = 0; i < ottelu.annaMaalit().size(); i++) {
            ottelu.annaMaalit().get(i).asetaTaulukkoaika();
            ottelu.annaMaalit().get(i).asetaTaulukkomaalintekija();
            ottelu.annaMaalit().get(i).asetaTaulukkosyottaja();
            ottelu.annaMaalit().get(i).asetaTaulukkojoukkue();
            maalit.add(ottelu.annaMaalit().get(i));

        }

        ObservableList<Maali> data
                = FXCollections.observableArrayList(maalit);

        TableColumn aika = new TableColumn("Min");
        TableColumn maalintekija = new TableColumn("Maalintekijä");
        TableColumn syottaja = new TableColumn("Syöttäjä");
         TableColumn joukkue = new TableColumn("Joukkue");
         
        aika.setCellValueFactory(new PropertyValueFactory<Maali, Integer>("taulukkoaika"));
        maalintekija.setCellValueFactory(new PropertyValueFactory<Maali, String>("taulukkomaalintekija"));
        syottaja.setCellValueFactory(new PropertyValueFactory<Maali, String>("taulukkosyottaja"));
joukkue.setCellValueFactory(new PropertyValueFactory<Maali, String>("taulukkojoukkue"));

        taulukko.getColumns().addAll(aika, joukkue, maalintekija, syottaja);
        taulukko.setItems(data);
        
         maalintekija.setMinWidth(150);
          syottaja.setMinWidth(150);
           joukkue.setMinWidth(150);
        aika.setSortType(TableColumn.SortType.ASCENDING);
        taulukko.getSortOrder().add(aika);

        taulukko.setFixedCellSize(25);

        if (taulukko.getItems().size() == 0) {
            taulukko.prefHeightProperty().bind(taulukko.fixedCellSizeProperty().multiply(Bindings.size(taulukko.getItems()).add(2)));
        } else {
            taulukko.prefHeightProperty().bind(taulukko.fixedCellSizeProperty().multiply(Bindings.size(taulukko.getItems()).add(1.1)));
        }

        taulukko.minHeightProperty().bind(taulukko.prefHeightProperty());
        taulukko.maxHeightProperty().bind(taulukko.prefHeightProperty());

   

        return taulukko;
    }

    public TableView luoOttelunMaaliTaulukkoMuokattava(Ottelu ottelu) {
        taulukko.setPlaceholder(new Label("Ei merkittyjä maalintekijöitä/syöttäjiä."));
        taulukko.setId("ei-klikattava");

        List<Maali> maalit = new ArrayList<Maali>();

        for (int i = 0; i < ottelu.annaMaalit().size(); i++) {
            ottelu.annaMaalit().get(i).asetaTaulukkoaika();
            ottelu.annaMaalit().get(i).asetaTaulukkomaalintekija();
            ottelu.annaMaalit().get(i).asetaTaulukkosyottaja();
            ottelu.annaMaalit().get(i).asetaTaulukkojoukkue();

            maalit.add(ottelu.annaMaalit().get(i));

        }

        ObservableList<Maali> data
                = FXCollections.observableArrayList(maalit);

        TableColumn aika = new TableColumn("Min");
        TableColumn maalintekija = new TableColumn("Maalintekijä");
        TableColumn syottaja = new TableColumn("Syöttäjä");
        TableColumn col_action = new TableColumn<>("Poista");
         TableColumn joukkue = new TableColumn("Joukkue");
        aika.setCellValueFactory(new PropertyValueFactory<Maali, Integer>("taulukkoaika"));
        maalintekija.setCellValueFactory(new PropertyValueFactory<Maali, String>("taulukkomaalintekija"));
        syottaja.setCellValueFactory(new PropertyValueFactory<Maali, String>("taulukkosyottaja"));
joukkue.setCellValueFactory(new PropertyValueFactory<Maali, String>("taulukkojoukkue"));
        
        
        taulukko.getColumns().addAll(aika, joukkue, maalintekija, syottaja, col_action);
        taulukko.setItems(data);
        
        aika.setPrefWidth(70);
   col_action.setPrefWidth(70);
           maalintekija.setMinWidth(150);
          syottaja.setMinWidth(150);
           joukkue.setMinWidth(150);
        aika.setSortType(TableColumn.SortType.ASCENDING);
        taulukko.getSortOrder().add(aika);

        taulukko.setFixedCellSize(25);

        if (taulukko.getItems().size() == 0) {
            taulukko.prefHeightProperty().bind(taulukko.fixedCellSizeProperty().multiply(Bindings.size(taulukko.getItems()).add(2)));
        } else {
            taulukko.prefHeightProperty().bind(taulukko.fixedCellSizeProperty().multiply(Bindings.size(taulukko.getItems()).add(1.1)));
        }

        taulukko.minHeightProperty().bind(taulukko.prefHeightProperty());
        taulukko.maxHeightProperty().bind(taulukko.prefHeightProperty());

        col_action.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Record, Boolean>, ObservableValue<Boolean>>() {

            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Record, Boolean> p) {
                return new SimpleBooleanProperty(p.getValue() != null);
            }
        });

        col_action.setCellFactory(
                new Callback<TableColumn<Record, Boolean>, TableCell<Record, Boolean>>() {

            @Override
            public TableCell<Record, Boolean> call(TableColumn<Record, Boolean> p) {
                return new PoistoSoluMaali(data);
            }

        });

        return taulukko;
    }

    public HBox luoKokoonpanoTaulukko(Ottelu ottelu, Joukkue joukkue) {

        HBox kokoonpanoluettelo = new HBox();

        kokoonpanoluettelo.setSpacing(10);

        Label otnro = new Label("Pelinumero");
        Label otnimi = new Label("Pelaaja");
        Label otpelipaikka = new Label("Pelipaikka");
        Label otrooli = new Label("Rooli ottelussa");

        otnro.setFont(Font.font("Papyrus", FontWeight.BOLD, 12));
        otnimi.setFont(Font.font("Papyrus", FontWeight.BOLD, 12));
        otpelipaikka.setFont(Font.font("Papyrus", FontWeight.BOLD, 12));
        otrooli.setFont(Font.font("Papyrus", FontWeight.BOLD, 12));

        VBox sarake1 = new VBox();
        sarake1.setSpacing(10);
        sarake1.setPadding(new Insets(20));
        sarake1.getChildren().add(otnro);

        VBox sarake2 = new VBox();
        sarake2.setSpacing(10);
        sarake2.setPadding(new Insets(20));
        sarake2.getChildren().add(otnimi);

        VBox sarake3 = new VBox();
        sarake3.setSpacing(10);
        sarake3.setPadding(new Insets(20));
        sarake3.getChildren().add(otpelipaikka);

        VBox sarake4 = new VBox();
        sarake4.setSpacing(10);
        sarake4.setPadding(new Insets(20));
        sarake4.getChildren().add(otrooli);

        int koko = joukkue.annaPelaajat().size();

        for (int i = 0; i < koko; i++) {

            Pelaaja haettu = joukkue.annaPelaajat().get(i);

            Label nro = new Label("" + haettu.annaPelinumero() + "");
            sarake1.getChildren().add(nro);

            Label nimi = new Label(haettu.toString());
            sarake2.getChildren().add(nimi);
            //onko kokoonpanossa:
            boolean on = false;
            for (int j = 0; j < haettu.annaKokoonpanot().size(); j++) {
                if (haettu.annaKokoonpanot().get(j).annaOttelu().equals(ottelu)) {
                    on = true;
                }

            }
            Label orooli = new Label();
            if (on) {
                orooli.setText("Kokoonpanossa");
            } else {
                orooli.setText("Ei kokoonpanossa");
            }

            Label opelipaikka = new Label(haettu.annaPelipaikka());
            sarake3.getChildren().add(opelipaikka);
            sarake4.getChildren().add(orooli);

        }

        kokoonpanoluettelo.getChildren().addAll(sarake1, sarake2, sarake3, sarake4);
        return kokoonpanoluettelo;
    }

}
