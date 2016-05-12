package tupa;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import static javafx.scene.control.ScrollPane.ScrollBarPolicy.AS_NEEDED;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.StringConverter;

/**
 *
 * @author Marianne
 */
public class OtteluNakyma {
         private Tupa ikkuna;
    private Muuttaja muuttaja;
    //uusien kohtien lisäystä varten
    private TextField nimi = new TextField();

    private TextField etunimi = new TextField();
    private TextField sukunimi = new TextField();
    private TextField pelipaikka = new TextField();

    private ComboBox<Integer> pelinumero = new ComboBox<>();
    private TextField sposti = new TextField();
    private TextField puh = new TextField();
    private TextField rooli = new TextField();

    private Label pakollinen = new Label("*");
    private Label pakollinen2 = new Label("*");
    private Label pakollinen3 = new Label("*");
    private Label pakollinen4 = new Label("*");

    private Tiedottaja tiedottaja;
    private Varmistus varmistaja;

    //taulukot
    private TableView ottelut;
    private TableView pisteporssi;
    private TableView joukkueet;
    private TableView sarjataulukko;
    private TableView pelaajat;
    private TableView toimihenkilot;
    private TableView pisteet;

    private PaaNakyma nakyma;
    private SarjaNakyma sarjanakyma;
    
    OtteluNakyma() {

    }

    OtteluNakyma(Tupa ikkuna, PaaNakyma nakyma) {
        this.ikkuna = ikkuna;
        this.nakyma = nakyma;
        sarjanakyma = nakyma.annaSarjanakyma();
        muuttaja = new Muuttaja(ikkuna, nakyma);
        tiedottaja = new Tiedottaja(ikkuna);
        varmistaja = new Varmistus(ikkuna, nakyma);
        pakollinen.setId("label-pakko");
        pakollinen2.setId("label-pakko");
        pakollinen3.setId("label-pakko");
        pakollinen4.setId("label-pakko");

    }
public void luoOttelusivu(Ottelu ottelu) {
        ScrollPane sb = new ScrollPane();
        sb.setHbarPolicy(AS_NEEDED);
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20, 10, 40, 10));

        VBox rivi1 = new VBox();
        rivi1.setAlignment(Pos.CENTER);

        Button muokkausnappula = new Button();

        muokkausnappula.setText("Muokkaa ottelun tietoja");
        muokkausnappula.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

                luoOtteluMuokkaus(ottelu, ottelu.annaSarja());

            }
        });

        Button poistonappula = new Button("Poista ottelu");
        poistonappula.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                varmistaja.annaOtteluPoistoVarmistus(ottelu);

            }
        });

        Button paluunappula = new Button("<< Palaa takaisin");
        paluunappula.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                TreeItem<Kohde> mihin = new TreeItem<>(ottelu.annaSarja());
                sarjanakyma.luoSarjaSivu(mihin);

            }
        });

        Button maalinappula = new Button("Muokkaa maalitilastoa");
        maalinappula.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                luoOttelunMaalisivu(ottelu);

            }

        });

        Button tulosnappula = new Button("Muokkaa tulosta");
        tulosnappula.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                luoOttelunTulossivu(ottelu);

            }

        });

        HBox painikkeet = new HBox();
        painikkeet.setPadding(new Insets(20));
        painikkeet.setSpacing(20);
        painikkeet.setAlignment(Pos.TOP_RIGHT);
        painikkeet.getChildren().addAll(paluunappula, poistonappula);
        rivi1.getChildren().addAll(painikkeet);

        VBox rivi2 = new VBox();
        rivi2.setPadding(new Insets(20));
        rivi2.setAlignment(Pos.CENTER);
        Label nimi = new Label(ottelu.toString());
        nimi.setFont(Font.font("Papyrus", 32));

        rivi2.getChildren().addAll(nimi);

        grid.add(painikkeet, 0, 0);
        grid.add(rivi2, 0, 1);

        VBox rivi3 = new VBox();
        rivi3.setPadding(new Insets(20));
        rivi3.setSpacing(20);

        HBox painikkeet2 = new HBox();
        painikkeet2.setPadding(new Insets(20, 0, 0, 0));
        painikkeet2.setSpacing(40);
        painikkeet2.getChildren().addAll(muokkausnappula, tulosnappula);

        Taulukko taulukontekija1 = new Taulukko(this);

        TableView ottelutaulu = taulukontekija1.luoOttelunTaulukko(ottelu);
        ottelutaulu.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        rivi3.getChildren().addAll(ottelutaulu, painikkeet2);

        HBox oikearivi3 = new HBox();
        oikearivi3.setPadding(new Insets(40));
        oikearivi3.setSpacing(40);

        HBox rivi4 = new HBox();
        rivi4.setPadding(new Insets(40));
        rivi4.setSpacing(60);

        Taulukko taulukontekija2 = new Taulukko(this);
        Taulukko taulukontekija3 = new Taulukko(this);
        Taulukko taulukontekija4 = new Taulukko(this);
        HBox kotikokoonpano = taulukontekija2.luoKokoonpanoTaulukko(ottelu, ottelu.annaKotijoukkue());
        HBox vieraskokoonpano = taulukontekija3.luoKokoonpanoTaulukko(ottelu, ottelu.annaVierasjoukkue());
        TableView maalit = taulukontekija4.luoOttelunMaaliTaulukko(ottelu);

        maalit.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        VBox kotiosio = new VBox();
        kotiosio.setPadding(new Insets(0, 0, 0, 40));

        Label kotiotsikko = new Label(ottelu.annaKotijoukkue().toString() + ":n kokoonpano:");
        kotiotsikko.setFont(Font.font("Papyrus", 18));
        kotiosio.getChildren().addAll(kotiotsikko, kotikokoonpano);

        //mahdollinen vain kotijoukkeen henkilölle
        if (ottelu.annaTulos().equals("-")) {

            HBox kotialle = new HBox();
            kotialle.setPadding(new Insets(20));
            Button lisayskoti = new Button("Muokkaa kokoonpanoa");
            lisayskoti.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {

                    Joukkue koti = ottelu.annaKotijoukkue();
                    luoKokoonpanosivu(ottelu, koti);
                }
            });
            kotialle.getChildren().add(lisayskoti);
            kotiosio.getChildren().add(kotialle);
        }

        VBox vierasosio = new VBox();
        vierasosio.setPadding(new Insets(0, 0, 0, 40));
        Label vierasotsikko = new Label(ottelu.annaVierasjoukkue().toString() + ":n kokoonpano:");
        vierasotsikko.setFont(Font.font("Papyrus", 18));

        vierasosio.getChildren().addAll(vierasotsikko, vieraskokoonpano);

        rivi4.getChildren().addAll(kotiosio, vierasosio);
        //mahdollinen vain vierasjoukkeen henkilölle
        if (ottelu.annaTulos().equals("-")) {

            HBox vierasalle = new HBox();
            vierasalle.setPadding(new Insets(0, 0, 0, 40));
            Button lisaysvieras = new Button("Muokkaa kokoonpanoa");
            lisaysvieras.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {

                    Joukkue vieras = ottelu.annaVierasjoukkue();
                    luoKokoonpanosivu(ottelu, vieras);
                }
            });
            vierasalle.getChildren().add(lisaysvieras);
            vierasosio.getChildren().add(vierasalle);
        }
        VBox maaliosio = new VBox();

        Label maaliotsikko = new Label("Maalit: ");
        maaliotsikko.setFont(Font.font("Papyrus", 18));
        HBox painikkeet3 = new HBox();
        painikkeet3.setPadding(new Insets(40, 0, 0, 0));
        painikkeet3.getChildren().addAll(maalinappula);

        // kenelle oikeudet?!?
        maaliosio.getChildren().addAll(maaliotsikko, maalit, painikkeet3);

        oikearivi3.getChildren().addAll(rivi3, maaliosio);

        grid.add(oikearivi3, 0, 2);
        grid.add(rivi4, 0, 3);

        sb.setContent(grid);
        VBox peitto = new VBox();
        peitto.setStyle("-fx-background-color: white;");
        ikkuna.annaNaytto().getChildren().add(peitto);
        ikkuna.annaNaytto().getChildren().add(sb);

    }

    public void luoOtteluMuokkaus(Ottelu ottelu, Sarja sarja) {
        ScrollPane sb = new ScrollPane();
        sb.setHbarPolicy(AS_NEEDED);
        GridPane grid = new GridPane();

        VBox ohjekierros = new VBox();
        Label ohjeki = new Label("Kierros");

        ComboBox<Integer> kierros = new ComboBox();
        List<Integer> kierroslista = new ArrayList();

        for (int i = 0; i < 99; i++) {
            kierroslista.add(i);
        }

        ObservableList kierrokset = FXCollections.observableList(kierroslista);
        kierros.setItems(kierrokset);
        kierros.setValue(ottelu.annaKierros());
        ohjekierros.getChildren().addAll(ohjeki, kierros);
        ohjekierros.setPadding(new Insets(20, 0, 0, 0));
        //jos ei vielä pelattu, niin tietoja voi muokata
        ComboBox<Joukkue> koti = new ComboBox();
        List<Joukkue> kotijoukkuelista = new ArrayList();

        for (int i = 0; i < sarja.annaJoukkueet().size(); i++) {
            kotijoukkuelista.add(sarja.annaJoukkueet().get(i));
        }

        ObservableList joukkueet1 = FXCollections.observableList(kotijoukkuelista);
        koti.setItems(joukkueet1);
        koti.setValue(ottelu.annaKotijoukkue());
        ComboBox<Joukkue> vieras = new ComboBox();
        List<Joukkue> vierasjoukkuelista = new ArrayList();

        for (int i = 0; i < sarja.annaJoukkueet().size(); i++) {
            vierasjoukkuelista.add(sarja.annaJoukkueet().get(i));
        }

        ObservableList joukkueet2 = FXCollections.observableList(vierasjoukkuelista);
        vieras.setItems(joukkueet2);

        vieras.setValue(ottelu.annaVierasjoukkue());

        DatePicker ajankohta = new DatePicker();
        String pattern = "dd.MM.yyyy";

        StringConverter<LocalDate> converter = new StringConverter<LocalDate>() {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        };

        ajankohta.setConverter(converter);
        ajankohta.setValue(ottelu.annaKalenteriAika());

        ComboBox<String> kellotunnit = new ComboBox();
        List<String> tuntilista = new ArrayList();

        for (int i = 0; i < 24; i++) {
            tuntilista.add("" + i);
        }

        ObservableList tunnit = FXCollections.observableList(tuntilista);
        kellotunnit.setItems(tunnit);
        kellotunnit.setValue(ottelu.annaKellotunnit());

        ComboBox<String> kellominuutit = new ComboBox();
        List<String> minuuttilista = new ArrayList();

        for (int i = 0; i < 10; i++) {
            minuuttilista.add("0" + i);
        }

        for (int i = 10; i < 60; i++) {
            minuuttilista.add("" + i);
        }

        ObservableList minuutit = FXCollections.observableList(minuuttilista);
        kellominuutit.setItems(minuutit);
        kellominuutit.setValue(ottelu.annaKellominuutit());

        TextField paikka = new TextField();
        paikka.setText(ottelu.annaPaikka());

        TextField tulos = new TextField();
        tulos.setText(ottelu.annaTulos());

        List<Tuomari> tuomarilista1 = new ArrayList<Tuomari>();

        for (int i = 0; i < sarja.annaTurnaus().annaTuomarit().size(); i++) {
            tuomarilista1.add(sarja.annaTurnaus().annaTuomarit().get(i));

        }

        List<Tuomari> tuomarilista2 = new ArrayList<Tuomari>();

        for (int i = 0; i < sarja.annaTurnaus().annaTuomarit().size(); i++) {
            tuomarilista2.add(sarja.annaTurnaus().annaTuomarit().get(i));
        }

        List<Tuomari> tuomarilista3 = new ArrayList<Tuomari>();

        for (int i = 0; i < sarja.annaTurnaus().annaTuomarit().size(); i++) {
            tuomarilista3.add(sarja.annaTurnaus().annaTuomarit().get(i));
        }

        ObservableList erotuomarit = FXCollections.observableList(tuomarilista1);
        ObservableList avustavat1 = FXCollections.observableList(tuomarilista2);
        ObservableList avustavat2 = FXCollections.observableList(tuomarilista3);

        ComboBox<Tuomari> erotuomari = new ComboBox();
        erotuomari.setItems(erotuomarit);

        ComboBox<Tuomari> avustava1 = new ComboBox();
        avustava1.setItems(avustavat1);

        ComboBox<Tuomari> avustava2 = new ComboBox();
        avustava2.setItems(avustavat2);

        for (int i = 0; i < ottelu.annaRoolit().size(); i++) {
            if (ottelu.annaRoolit().get(i).annaRooli().equals("1. Avustava erotuomari")) {
                avustava1.setValue(ottelu.annaRoolit().get(i).annaTuomari());
            } else if (ottelu.annaRoolit().get(i).annaRooli().equals("2. Avustava erotuomari")) {
                avustava2.setValue(ottelu.annaRoolit().get(i).annaTuomari());
            } else if (ottelu.annaRoolit().get(i).annaRooli().equals("Erotuomari")) {
                erotuomari.setValue(ottelu.annaRoolit().get(i).annaTuomari());
            }

        }

        Button muokkausnappula = new Button("Tallenna");
        muokkausnappula.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                boolean ok = true;
                //TARKISTUKSET!!
                if (koti.getValue() == null || vieras.getValue() == null) {
                    tiedottaja.annaVaroitus("Valitse sekä koti- että vierasjoukkue.");
                    ok = false;
                }
                if (koti.getValue() == vieras.getValue()) {
                    tiedottaja.annaVaroitus("Koti- ja vierasjoukkue eivät voi olla samoja.");
                    ok = false;
                }
                if (erotuomari.getValue() == avustava1.getValue() || erotuomari.getValue() == avustava2.getValue() || avustava2.getValue() == avustava1.getValue()) {
                    tiedottaja.annaVaroitus("Sama henkilö ei voi olla kuin yhdessä tuomarin roolissa.");
                    ok = false;
                }
                if (ok) {
                    ottelu.asetaKierros(kierros.getValue());
                    ottelu.asetaAika(ajankohta.getValue(), kellotunnit.getValue(), kellominuutit.getValue());
                    ottelu.asetaPaikka(paikka.getText());
                    ottelu.asetaJoukkueet(koti.getValue(), vieras.getValue());
                    Tuomari erotuomariT = erotuomari.getValue();
                    Tuomari avustava1T = avustava1.getValue();
                    Tuomari avustava2T = avustava2.getValue();

                    ottelu.annaRoolit().clear();

                    if (erotuomariT != null) {
                        TuomarinRooli erotuomariR = new TuomarinRooli(erotuomariT, ottelu);
                        erotuomariR.asetaRooli("Erotuomari");

                        ottelu.annaRoolit().add(erotuomariR);
                        erotuomariT.annaTuomarinRoolit().add(erotuomariR);
                    }

                    if (avustava1T != null) {

                        TuomarinRooli avustava1R = new TuomarinRooli(avustava1T, ottelu);
                        avustava1R.asetaRooli("1. Avustava erotuomari");

                        ottelu.annaRoolit().add(avustava1R);
                        avustava1T.annaTuomarinRoolit().add(avustava1R);
                    }

                    if (avustava2T != null) {
                        TuomarinRooli avustava2R = new TuomarinRooli(avustava2T, ottelu);
                        avustava2R.asetaRooli("2. Avustava erotuomari");

                        ottelu.annaRoolit().add(avustava2R);
                        avustava2T.annaTuomarinRoolit().add(avustava2R);
                    }

                    tiedottaja.kirjoitaLoki("Ottelun tietoja muokattu.");

                    ikkuna.asetaMuutos(true);

                    luoOttelusivu(ottelu);
                }
            }

        });

        Button peruuta = new Button("Peruuta");
        peruuta.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                luoOttelusivu(ottelu);

            }
        });

        HBox hbox = new HBox();
        hbox.setSpacing(20);

        Label otsikko = new Label("Muokkaa ottelun " + ottelu.toString() + " tietoja: ");
        otsikko.setFont(Font.font("Papyrus", 22));

        VBox vbox1 = new VBox();
        vbox1.setPadding(new Insets(20, 0, 0, 0));
        Label label1 = new Label("Kotijoukkue: ");
        vbox1.getChildren().addAll(label1, koti);

        VBox vbox2 = new VBox();
        Label label2 = new Label("Vierasjoukkue: ");
        vbox2.setPadding(new Insets(20, 0, 0, 0));
        vbox2.getChildren().addAll(label2, vieras);

        VBox vbox3 = new VBox();
        Label label3 = new Label("Paiva:");
        vbox3.setPadding(new Insets(20, 0, 0, 0));
        vbox3.getChildren().addAll(label3, ajankohta);

        VBox vbox8 = new VBox();
        Label label8 = new Label("Kello:");
        vbox8.setAlignment(Pos.CENTER);
        vbox8.setPadding(new Insets(20, 0, 0, 0));
        HBox kellovalinnat = new HBox();
        kellovalinnat.setSpacing(5);
        Label pisteet = new Label(":");
        kellovalinnat.getChildren().addAll(kellotunnit, pisteet, kellominuutit);
        vbox8.getChildren().addAll(label8, kellovalinnat);

        VBox vbox4 = new VBox();
        Label label4 = new Label("Paikka:");
        vbox4.setPadding(new Insets(20, 0, 0, 0));
        vbox4.getChildren().addAll(label4, paikka);

        VBox vbox5 = new VBox();
        Label label5 = new Label("Erotuomari:");
        vbox5.setPadding(new Insets(20, 0, 0, 0));
        vbox5.getChildren().addAll(label5, erotuomari);

        VBox vbox6 = new VBox();
        Label label6 = new Label("1. Avustava:");
        vbox6.setPadding(new Insets(20, 0, 0, 0));
        vbox6.getChildren().addAll(label6, avustava1);

        VBox vbox7 = new VBox();
        Label label7 = new Label("2. Avustava:");
        vbox7.setPadding(new Insets(20, 0, 0, 0));
        vbox7.getChildren().addAll(label7, avustava2);

        HBox painikkeet = new HBox();
        painikkeet.setSpacing(20);
        painikkeet.setPadding(new Insets(20, 0, 0, 0));
        painikkeet.getChildren().addAll(muokkausnappula, peruuta);
        hbox.getChildren().addAll(ohjekierros, vbox1, vbox2, vbox3, vbox4, vbox5, vbox6, vbox7);
        grid.add(otsikko, 0, 0);
        grid.add(hbox, 0, 1);
        grid.add(painikkeet, 0, 2);
        sb.setContent(grid);
        VBox peitto = new VBox();
        peitto.setStyle("-fx-background-color: white;");
        ikkuna.annaNaytto().getChildren().add(peitto);

        ikkuna.annaNaytto().getChildren().add(sb);
    }

    public void luoOtteluMuokkaus2(Ottelu ottelu, Sarja sarja) {
        ScrollPane sb = new ScrollPane();
        sb.setHbarPolicy(AS_NEEDED);
        GridPane grid = new GridPane();

        VBox ohjekierros = new VBox();
        Label ohjeki = new Label("Kierros");

        ComboBox<Integer> kierros = new ComboBox();
        List<Integer> kierroslista = new ArrayList();

        for (int i = 0; i < 99; i++) {
            kierroslista.add(i);
        }

        ObservableList kierrokset = FXCollections.observableList(kierroslista);
        kierros.setItems(kierrokset);
        kierros.setValue(ottelu.annaKierros());
        ohjekierros.getChildren().addAll(ohjeki, kierros);
        ohjekierros.setPadding(new Insets(20, 0, 0, 0));
        //jos ei vielä pelattu, niin tietoja voi muokata
        ComboBox<Joukkue> koti = new ComboBox();
        List<Joukkue> kotijoukkuelista = new ArrayList();

        for (int i = 0; i < sarja.annaJoukkueet().size(); i++) {
            kotijoukkuelista.add(sarja.annaJoukkueet().get(i));
        }

        ObservableList joukkueet1 = FXCollections.observableList(kotijoukkuelista);
        koti.setItems(joukkueet1);
        koti.setValue(ottelu.annaKotijoukkue());
        ComboBox<Joukkue> vieras = new ComboBox();
        List<Joukkue> vierasjoukkuelista = new ArrayList();

        for (int i = 0; i < sarja.annaJoukkueet().size(); i++) {
            vierasjoukkuelista.add(sarja.annaJoukkueet().get(i));
        }

        ObservableList joukkueet2 = FXCollections.observableList(vierasjoukkuelista);
        vieras.setItems(joukkueet2);

        vieras.setValue(ottelu.annaVierasjoukkue());

        DatePicker ajankohta = new DatePicker();
        String pattern = "dd.MM.yyyy";

        StringConverter<LocalDate> converter = new StringConverter<LocalDate>() {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        };

        ajankohta.setConverter(converter);
        ajankohta.setValue(ottelu.annaKalenteriAika());

        ComboBox<String> kellotunnit = new ComboBox();
        List<String> tuntilista = new ArrayList();

        for (int i = 0; i < 24; i++) {
            tuntilista.add("" + i);
        }

        ObservableList tunnit = FXCollections.observableList(tuntilista);
        kellotunnit.setItems(tunnit);
        kellotunnit.setValue(ottelu.annaKellotunnit());

        ComboBox<String> kellominuutit = new ComboBox();
        List<String> minuuttilista = new ArrayList();

        for (int i = 0; i < 10; i++) {
            minuuttilista.add("0" + i);
        }

        for (int i = 10; i < 60; i++) {
            minuuttilista.add("" + i);
        }

        ObservableList minuutit = FXCollections.observableList(minuuttilista);
        kellominuutit.setItems(minuutit);
        kellominuutit.setValue(ottelu.annaKellominuutit());

        TextField paikka = new TextField();
        paikka.setText(ottelu.annaPaikka());

        TextField tulos = new TextField();
        tulos.setText(ottelu.annaTulos());

        List<Tuomari> tuomarilista1 = new ArrayList<Tuomari>();

        for (int i = 0; i < sarja.annaTurnaus().annaTuomarit().size(); i++) {
            tuomarilista1.add(sarja.annaTurnaus().annaTuomarit().get(i));

        }

        List<Tuomari> tuomarilista2 = new ArrayList<Tuomari>();

        for (int i = 0; i < sarja.annaTurnaus().annaTuomarit().size(); i++) {
            tuomarilista2.add(sarja.annaTurnaus().annaTuomarit().get(i));
        }

        List<Tuomari> tuomarilista3 = new ArrayList<Tuomari>();

        for (int i = 0; i < sarja.annaTurnaus().annaTuomarit().size(); i++) {
            tuomarilista3.add(sarja.annaTurnaus().annaTuomarit().get(i));
        }

        ObservableList erotuomarit = FXCollections.observableList(tuomarilista1);
        ObservableList avustavat1 = FXCollections.observableList(tuomarilista2);
        ObservableList avustavat2 = FXCollections.observableList(tuomarilista3);

        ComboBox<Tuomari> erotuomari = new ComboBox();
        erotuomari.setItems(erotuomarit);

        ComboBox<Tuomari> avustava1 = new ComboBox();
        avustava1.setItems(avustavat1);

        ComboBox<Tuomari> avustava2 = new ComboBox();
        avustava2.setItems(avustavat2);

        for (int i = 0; i < ottelu.annaRoolit().size(); i++) {
            if (ottelu.annaRoolit().get(i).annaRooli().equals("1. Avustava erotuomari")) {
                avustava1.setValue(ottelu.annaRoolit().get(i).annaTuomari());
            } else if (ottelu.annaRoolit().get(i).annaRooli().equals("2. Avustava erotuomari")) {
                avustava2.setValue(ottelu.annaRoolit().get(i).annaTuomari());
            } else if (ottelu.annaRoolit().get(i).annaRooli().equals("Erotuomari")) {
                erotuomari.setValue(ottelu.annaRoolit().get(i).annaTuomari());
            }

        }

        Button muokkausnappula = new Button("Tallenna");
        muokkausnappula.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                boolean ok = true;
                //TARKISTUKSET!!
                if (koti.getValue() == null || vieras.getValue() == null) {
                    tiedottaja.annaVaroitus("Valitse sekä koti- että vierasjoukkue.");
                    ok = false;
                }
                if (koti.getValue() == vieras.getValue()) {
                    tiedottaja.annaVaroitus("Koti- ja vierasjoukkue eivät voi olla samoja.");
                    ok = false;
                }
                if (erotuomari.getValue() == avustava1.getValue() || erotuomari.getValue() == avustava2.getValue() || avustava2.getValue() == avustava1.getValue()) {
                    tiedottaja.annaVaroitus("Sama henkilö ei voi olla kuin yhdessä tuomarin roolissa.");
                    ok = false;
                }
                if (ok) {
                    ottelu.asetaKierros(kierros.getValue());
                    ottelu.asetaAika(ajankohta.getValue(), kellotunnit.getValue(), kellominuutit.getValue());
                    ottelu.asetaPaikka(paikka.getText());
                    ottelu.asetaJoukkueet(koti.getValue(), vieras.getValue());
                    Tuomari erotuomariT = erotuomari.getValue();
                    Tuomari avustava1T = avustava1.getValue();
                    Tuomari avustava2T = avustava2.getValue();

                    ottelu.annaRoolit().clear();

                    if (erotuomariT != null) {
                        TuomarinRooli erotuomariR = new TuomarinRooli(erotuomariT, ottelu);
                        erotuomariR.asetaRooli("Erotuomari");

                        ottelu.annaRoolit().add(erotuomariR);
                        erotuomariT.annaTuomarinRoolit().add(erotuomariR);
                    }

                    if (avustava1T != null) {

                        TuomarinRooli avustava1R = new TuomarinRooli(avustava1T, ottelu);
                        avustava1R.asetaRooli("1. Avustava erotuomari");

                        ottelu.annaRoolit().add(avustava1R);
                        avustava1T.annaTuomarinRoolit().add(avustava1R);
                    }

                    if (avustava2T != null) {
                        TuomarinRooli avustava2R = new TuomarinRooli(avustava2T, ottelu);
                        avustava2R.asetaRooli("2. Avustava erotuomari");

                        ottelu.annaRoolit().add(avustava2R);
                        avustava2T.annaTuomarinRoolit().add(avustava2R);
                    }

                    tiedottaja.kirjoitaLoki("Ottelun tietoja muokattu.");

                    ikkuna.asetaMuutos(true);

                    luoOttelusivu(ottelu);
                }
            }

        });

        Button peruuta = new Button("Peruuta");
        peruuta.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                luoOttelusivu(ottelu);

            }
        });

        HBox hbox = new HBox();
        hbox.setSpacing(20);

        Label otsikko = new Label("Muokkaa ottelun " + ottelu.toString() + " tietoja: ");
        otsikko.setFont(Font.font("Papyrus", 22));

        VBox vbox1 = new VBox();
        vbox1.setPadding(new Insets(20, 0, 0, 0));
        Label label1 = new Label("Kotijoukkue: ");
        vbox1.getChildren().addAll(label1, koti);

        VBox vbox2 = new VBox();
        Label label2 = new Label("Vierasjoukkue: ");
        vbox2.setPadding(new Insets(20, 0, 0, 0));
        vbox2.getChildren().addAll(label2, vieras);

        VBox vbox3 = new VBox();
        Label label3 = new Label("Paiva:");
        vbox3.setPadding(new Insets(20, 0, 0, 0));
        vbox3.getChildren().addAll(label3, ajankohta);

        VBox vbox8 = new VBox();
        Label label8 = new Label("Kello:");
        vbox8.setAlignment(Pos.CENTER);
        vbox8.setPadding(new Insets(20, 0, 0, 0));
        HBox kellovalinnat = new HBox();
        kellovalinnat.setSpacing(5);
        Label pisteet = new Label(":");
        kellovalinnat.getChildren().addAll(kellotunnit, pisteet, kellominuutit);
        vbox8.getChildren().addAll(label8, kellovalinnat);

        VBox vbox4 = new VBox();
        Label label4 = new Label("Paikka:");
        vbox4.setPadding(new Insets(20, 0, 0, 0));
        vbox4.getChildren().addAll(label4, paikka);

        VBox vbox5 = new VBox();
        Label label5 = new Label("Erotuomari:");
        vbox5.setPadding(new Insets(20, 0, 0, 0));
        vbox5.getChildren().addAll(label5, erotuomari);

        VBox vbox6 = new VBox();
        Label label6 = new Label("1. Avustava:");
        vbox6.setPadding(new Insets(20, 0, 0, 0));
        vbox6.getChildren().addAll(label6, avustava1);

        VBox vbox7 = new VBox();
        Label label7 = new Label("2. Avustava:");
        vbox7.setPadding(new Insets(20, 0, 0, 0));
        vbox7.getChildren().addAll(label7, avustava2);

        HBox painikkeet = new HBox();
        painikkeet.setSpacing(20);
        painikkeet.setPadding(new Insets(20, 0, 0, 0));
        painikkeet.getChildren().addAll(muokkausnappula, peruuta);
        hbox.getChildren().addAll(ohjekierros, vbox1, vbox2, vbox3, vbox4, vbox5, vbox6, vbox7);
        grid.add(otsikko, 0, 0);
        grid.add(hbox, 0, 1);
        grid.add(painikkeet, 0, 2);
        sb.setContent(grid);
        VBox peitto = new VBox();
        peitto.setStyle("-fx-background-color: white;");
        ikkuna.annaNaytto().getChildren().add(peitto);

        ikkuna.annaNaytto().getChildren().add(sb);
    }

    public void luoKokoonpanosivu(Ottelu ottelu, Joukkue joukkue) {
        ScrollPane sb = new ScrollPane();
        sb.setHbarPolicy(AS_NEEDED);
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20, 10, 40, 10));

        VBox rivi1 = new VBox();
        rivi1.setAlignment(Pos.CENTER);

        Button paluu = new Button();

        paluu.setText("<< Palaa takaisin");
        paluu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

                luoOttelusivu(ottelu);

            }
        });

        HBox painikkeet = new HBox();
        painikkeet.setPadding(new Insets(20));
        painikkeet.setSpacing(20);
        painikkeet.setAlignment(Pos.TOP_RIGHT);
        painikkeet.getChildren().addAll(paluu);
        rivi1.getChildren().addAll(painikkeet);

        VBox rivi2 = new VBox();
        rivi2.setPadding(new Insets(20));
        rivi2.setAlignment(Pos.CENTER);
        Label nimi = new Label(ottelu.toString());
        nimi.setFont(Font.font("Papyrus", 32));

        rivi2.getChildren().addAll(nimi);

        grid.add(painikkeet, 0, 0);
        grid.add(rivi2, 0, 1);

        VBox rivi3 = new VBox();
        rivi3.setSpacing(20);
        Label otsake = new Label("Lisää kokoonpano otteluun:");
        otsake.setFont(Font.font("Papyrus", FontWeight.BOLD, 18));

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

        HBox painikeboksi = new HBox();
        painikeboksi.setPadding(new Insets(10, 0, 0, 0));
        painikeboksi.setSpacing(10);

        Pelaaja[] pelaajataulukko = new Pelaaja[koko];
        String[] roolitaulukko = new String[koko];

        for (int i = 0; i < koko; i++) {
            int kohta = i;
            Pelaaja haettu = joukkue.annaPelaajat().get(i);
            pelaajataulukko[kohta] = haettu;

            Label nro = new Label("" + haettu.annaPelinumero() + "");
            sarake1.getChildren().add(nro);

            Label pnimi = new Label(haettu.toString());
            sarake2.getChildren().add(pnimi);

            ComboBox<String> rooli = new ComboBox();
            List<String> roolilista = new ArrayList();
            roolilista.add("Ei kokoonpanossa");
            roolilista.add("Kokoonpanossa");
            ObservableList roolit = FXCollections.observableList(roolilista);
            rooli.setItems(roolit);

//onko kokoonpanossa:
            boolean on = false;
            for (int j = 0; j < haettu.annaKokoonpanot().size(); j++) {
                if (haettu.annaKokoonpanot().get(j).annaOttelu().equals(ottelu)) {
                    on = true;
                }

            }

            if (on) {
                rooli.setValue("Kokoonpanossa");
                roolitaulukko[kohta] = ("Kokoonpanossa");
            } else {
                rooli.setValue("Ei kokoonpanossa");
                roolitaulukko[kohta] = ("Ei kokoonpanossa");
            }

            rooli.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue ov, String vanha, String uusi) {

                    roolitaulukko[kohta] = uusi;

                }

            });

            Label opelipaikka = new Label(haettu.annaPelipaikka());
            sarake3.getChildren().add(opelipaikka);
            sarake4.getChildren().add(rooli);

        }

        Button lisaysnappula = new Button("Tallenna");
        lisaysnappula.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

                muuttaja.lisaaKokoonpano(pelaajataulukko, roolitaulukko, joukkue, ottelu);
                ikkuna.asetaMuutos(true);
                luoOttelusivu(ottelu);

            }
        });
        painikeboksi.getChildren().addAll(lisaysnappula);
        kokoonpanoluettelo.getChildren().addAll(sarake1, sarake2, sarake3, sarake4);

        rivi3.getChildren().addAll(otsake, kokoonpanoluettelo, painikeboksi);

        grid.add(rivi3, 0, 2);

        sb.setContent(grid);
        VBox peitto = new VBox();
        peitto.setStyle("-fx-background-color: white;");
        ikkuna.annaNaytto().getChildren().add(peitto);
        ikkuna.annaNaytto().getChildren().add(sb);

    }

    public void luoOttelunMaalisivu(Ottelu ottelu) {
        ScrollPane sb = new ScrollPane();
        sb.setHbarPolicy(AS_NEEDED);
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20, 10, 40, 10));

        VBox rivi1 = new VBox();
        rivi1.setAlignment(Pos.CENTER);

        Button paluunappula = new Button("<< Palaa takaisin");
        paluunappula.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                luoOttelusivu(ottelu);

            }
        });

        HBox painikkeet = new HBox();
        painikkeet.setPadding(new Insets(20));
        painikkeet.setSpacing(20);
        painikkeet.setAlignment(Pos.TOP_RIGHT);
        painikkeet.getChildren().addAll(paluunappula);
        rivi1.getChildren().addAll(painikkeet);

        VBox rivi2 = new VBox();
        rivi2.setPadding(new Insets(20, 20, 0, 20));
        rivi2.setAlignment(Pos.CENTER);

        Label otsikko = new Label("Muokkaa ottelun " + ottelu.toString() + " maalitilastoa: ");
        otsikko.setFont(Font.font("Papyrus", 22));
        rivi2.getChildren().addAll(otsikko);

        VBox rivi3 = new VBox();
        rivi3.setPadding(new Insets(20));

        Taulukko taulukontekija1 = new Taulukko(this);

        TableView ottelutaulu = taulukontekija1.luoOttelunTaulukko(ottelu);
        ottelutaulu.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        rivi3.getChildren().addAll(ottelutaulu);

        VBox rivi4 = new VBox();
        rivi4.setPadding(new Insets(20));

        Taulukko taulukontekija2 = new Taulukko(this);

        TableView maalitaulu = taulukontekija2.luoOttelunMaaliTaulukko(ottelu);
        maalitaulu.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        HBox maalialle = new HBox();
        maalialle.setSpacing(20);
        maalialle.setPadding(new Insets(20, 0, 0, 0));

        VBox alle7 = new VBox();

        Label ohjeaika = new Label("Min");
        ComboBox<Integer> aika = new ComboBox();
        List<Integer> aikalista = new ArrayList();
        for (int i = 1; i <= 200; i++) {
            aikalista.add(i);
        }

        aika.setItems(FXCollections.observableArrayList(aikalista));
        aika.getSelectionModel().selectFirst();

        alle7.getChildren().addAll(ohjeaika, aika);

        VBox alle8 = new VBox();

        Label ohjepelaaja3 = new Label("Maalintekijä");

        ComboBox<Pelaaja> maalintekija = new ComboBox();
        List<Pelaaja> kaikkipelaajalista = new ArrayList();
        Pelaaja ohjeistus3 = new Pelaaja("Valitse", " ");
        kaikkipelaajalista.add(ohjeistus3);

        for (int i = 0; i < ottelu.annaKotijoukkue().annaPelaajat().size(); i++) {
            kaikkipelaajalista.add(ottelu.annaKotijoukkue().annaPelaajat().get(i));
        }
        for (int i = 0; i < ottelu.annaVierasjoukkue().annaPelaajat().size(); i++) {
            kaikkipelaajalista.add(ottelu.annaVierasjoukkue().annaPelaajat().get(i));
        }

        ObservableList kaikkipelaajat1 = FXCollections.observableList(kaikkipelaajalista);
        maalintekija.setItems(kaikkipelaajat1);
        maalintekija.getSelectionModel().selectFirst();
        alle8.getChildren().addAll(ohjepelaaja3, maalintekija);

        VBox alle9 = new VBox();

        Label ohjepelaaja4 = new Label("Syöttäjä");

        ComboBox<Pelaaja> syottaja = new ComboBox();
        List<Pelaaja> kaikkipelaajalista2 = new ArrayList();
        Pelaaja ohjeistus4 = new Pelaaja("Valitse", " ");
        kaikkipelaajalista2.add(ohjeistus4);
        Pelaaja ohjeistus5 = new Pelaaja("Ei ", "syöttäjää");
        kaikkipelaajalista2.add(ohjeistus5);
        for (int i = 0; i < ottelu.annaKotijoukkue().annaPelaajat().size(); i++) {
            kaikkipelaajalista2.add(ottelu.annaKotijoukkue().annaPelaajat().get(i));
        }
        for (int i = 0; i < ottelu.annaVierasjoukkue().annaPelaajat().size(); i++) {
            kaikkipelaajalista2.add(ottelu.annaVierasjoukkue().annaPelaajat().get(i));
        }

        ObservableList kaikkipelaajat2 = FXCollections.observableList(kaikkipelaajalista2);
        syottaja.setItems(kaikkipelaajat2);
        syottaja.getSelectionModel().selectFirst();
        alle9.getChildren().addAll(ohjepelaaja4, syottaja);

        maalialle.getChildren().addAll(alle7, alle8, alle9);

        HBox painikeboksi3 = new HBox();
        painikeboksi3.setPadding(new Insets(30, 0, 0, 0));

        Button lisaysnappula3 = new Button("Tallenna");
        lisaysnappula3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

                muuttaja.lisaaMaali(aika.getValue(), maalintekija.getValue(), syottaja.getValue(), ottelu);
                ikkuna.asetaMuutos(true);
                luoOttelusivu(ottelu);
            }
        });

        painikeboksi3.getChildren().addAll(lisaysnappula3);

        rivi4.getChildren().addAll(maalitaulu, maalialle, painikeboksi3);

        grid.add(rivi1, 0, 0);
        grid.add(rivi2, 0, 1);
        grid.add(rivi3, 0, 2);
        grid.add(rivi4, 0, 3);

        sb.setContent(grid);
        VBox peitto = new VBox();
        peitto.setStyle("-fx-background-color: white;");
        ikkuna.annaNaytto().getChildren().add(peitto);
        ikkuna.annaNaytto().getChildren().add(sb);

    }

    public void luoOttelunTulossivu(Ottelu ottelu) {

        ScrollPane sb = new ScrollPane();
        sb.setHbarPolicy(AS_NEEDED);
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20, 10, 40, 10));

        VBox rivi1 = new VBox();
        rivi1.setAlignment(Pos.CENTER);

        Button paluunappula = new Button("<< Palaa takaisin");
        paluunappula.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                luoOttelusivu(ottelu);

            }
        });

        HBox painikkeet = new HBox();
        painikkeet.setPadding(new Insets(20));
        painikkeet.setSpacing(20);
        painikkeet.setAlignment(Pos.TOP_RIGHT);
        painikkeet.getChildren().addAll(paluunappula);
        rivi1.getChildren().addAll(painikkeet);

        VBox rivi2 = new VBox();
        rivi2.setPadding(new Insets(20, 20, 0, 20));
        rivi2.setAlignment(Pos.CENTER);

        Label otsikko = new Label("Muokkaa ottelun " + ottelu.toString() + " tulosta: ");
        otsikko.setFont(Font.font("Papyrus", 22));
        rivi2.getChildren().addAll(otsikko);

        VBox rivi3 = new VBox();
        rivi3.setPadding(new Insets(20));

        Taulukko taulukontekija1 = new Taulukko(this);

        TableView ottelutaulu = taulukontekija1.luoOttelunTaulukko(ottelu);
        ottelutaulu.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        rivi3.getChildren().addAll(ottelutaulu);

        HBox ottelualle = new HBox();
        ottelualle.setSpacing(20);

        VBox kotimaalit = new VBox();
        kotimaalit.setSpacing(10);

        Label ohjekoti = new Label("Kotijoukkueen maalit");
        ComboBox<Integer> maalitkoti = new ComboBox();
        List<Integer> kotimaalilista = new ArrayList();
        for (int i = 0; i <= 99; i++) {
            kotimaalilista.add(i);
        }

        maalitkoti.setItems(FXCollections.observableArrayList(kotimaalilista));
        maalitkoti.getSelectionModel().selectFirst();

        kotimaalit.getChildren().addAll(ohjekoti, maalitkoti);

        VBox vierasmaalit = new VBox();
        vierasmaalit.setSpacing(10);

        Label ohjevieras = new Label("Vierasjoukkueen maalit");
        ComboBox<Integer> maalitvieras = new ComboBox();
        List<Integer> vierasmaalilista = new ArrayList();
        for (int i = 0; i <= 99; i++) {
            vierasmaalilista.add(i);
        }

        maalitvieras.setItems(FXCollections.observableArrayList(vierasmaalilista));
        maalitvieras.getSelectionModel().selectFirst();

        vierasmaalit.getChildren().addAll(ohjevieras, maalitvieras);

        HBox painikeboksi0 = new HBox();
        painikeboksi0.setPadding(new Insets(10, 0, 0, 0));
        painikeboksi0.setSpacing(20);

        Button lisaysnappula0 = new Button("Tallenna");
        lisaysnappula0.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

                muuttaja.lisaaTulos(maalitkoti.getValue(), maalitvieras.getValue(), ottelu);
                ikkuna.asetaMuutos(true);
                luoOttelusivu(ottelu);
            }
        });

        painikeboksi0.getChildren().addAll(lisaysnappula0);
        Label tulosohje = new Label("Lisää tulos:");
        tulosohje.setFont(Font.font("Papyrus", 14));
        ottelualle.getChildren().addAll(kotimaalit, vierasmaalit);

        VBox rivi4 = new VBox();
        rivi4.setPadding(new Insets(20));
        rivi4.setSpacing(20);

        rivi4.getChildren().addAll(ottelutaulu, ottelualle, painikeboksi0);

        grid.add(rivi1, 0, 0);
        grid.add(rivi2, 0, 1);
        grid.add(rivi3, 0, 2);
        grid.add(rivi4, 0, 3);

        sb.setContent(grid);
        VBox peitto = new VBox();
        peitto.setStyle("-fx-background-color: white;");
        ikkuna.annaNaytto().getChildren().add(peitto);
        ikkuna.annaNaytto().getChildren().add(sb);
    }

}
