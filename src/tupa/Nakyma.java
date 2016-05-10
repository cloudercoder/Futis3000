/*
Luokka, joka muodostaa eri näkymiä
 */
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
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import static javafx.scene.control.ScrollPane.ScrollBarPolicy.AS_NEEDED;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.StringConverter;

/**
 *
 * @author Marianne
 */
public class Nakyma {

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

    Nakyma() {

    }

    Nakyma(Tupa ikkuna) {
        this.ikkuna = ikkuna;
        muuttaja = new Muuttaja(ikkuna, this);
        tiedottaja = new Tiedottaja(ikkuna);
        varmistaja = new Varmistus(ikkuna, this);
        pakollinen.setId("label-pakko");
        pakollinen2.setId("label-pakko");
        pakollinen3.setId("label-pakko");
        pakollinen4.setId("label-pakko");

    }

    public void luoOhje(String uusiohje, TreeItem<Kohde> arvo) {

        HBox ohjepalkki = new HBox();
        ohjepalkki.setStyle("-fx-background-color: blue;");
        ohjepalkki.setPadding(new Insets(10, 30, 10, 30));
        Text ohje = new Text(uusiohje);
        ohje.setFont(Font.font("Papyrus", FontWeight.BOLD, 20));

        ohjepalkki.getChildren().add(ohje);

        VBox peitto = new VBox();
        peitto.setStyle("-fx-background-color: white;");
        ikkuna.annaNaytto().getChildren().add(peitto);

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(60));
        grid.setAlignment(Pos.TOP_CENTER);

        grid.setHgap(20);

        grid.add(ohjepalkki, 1, 3);

        Button uusi = new Button();

        if (arvo.getValue() instanceof Sarja) {
            uusi.setText("Lisää uusi sarja");

        } else if (arvo.getValue() instanceof Tuomari) {
            uusi.setText("Lisää uusi tuomari");

        }
        uusi.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

                VBox peitto = new VBox();
                peitto.setStyle("-fx-background-color: white;");
                ikkuna.annaNaytto().getChildren().add(peitto);

                if (arvo.getValue() instanceof Sarja) {
                    ikkuna.annaNaytto().getChildren().add(luoPaanayttoSarjat());
                } else if (arvo.getValue() instanceof Tuomari) {
                    ikkuna.annaNaytto().getChildren().add(luoPaanayttoTuomarit());
                }

            }
        });

        grid.add(uusi, 5, 1);
        ikkuna.annaNaytto().getChildren().add(grid);
    }

    public void luoEtusivu() {

        HBox osa = new HBox();

        HBox nimipalkki = new HBox();

        nimipalkki.setPadding(new Insets(20));
        Label nimi = new Label(ikkuna.annaTurnaus().toString());
        nimi.setFont(Font.font("Papyrus", FontWeight.BOLD, 30));
        nimipalkki.setAlignment(Pos.CENTER);
        nimipalkki.getChildren().addAll(nimi);

        VBox peitto = new VBox();
        peitto.setStyle("-fx-background-color: white;");
        ikkuna.annaNaytto().getChildren().add(peitto);

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(40, 10, 40, 300));

        Button muokkausnappula = new Button();

        muokkausnappula.setText("Muokkaa");
        muokkausnappula.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

                VBox peitto = new VBox();
                peitto.setStyle("-fx-background-color: white;");
                ikkuna.annaNaytto().getChildren().add(peitto);

                ikkuna.annaNaytto().getChildren().add(luoEtusivuMuokkaus());

            }
        });

        VBox rivi1 = new VBox();
        rivi1.setAlignment(Pos.TOP_RIGHT);
        rivi1.getChildren().addAll(muokkausnappula);

        VBox hakupalkki = new VBox();
        hakupalkki.setAlignment(Pos.CENTER);
        hakupalkki.setPadding(new Insets(20));
        hakupalkki.setSpacing(20);
        Label otsikko = new Label("Hae sarjaa/joukkuetta/tuomaria: ");
        otsikko.setFont(Font.font("Papyrus", FontWeight.BOLD, 18));
        //hakutoiminto HBox
        HBox haku = new HBox();
        Label hakuteksti = new Label("Hae: ");
        hakuteksti.setId("label-haku");

        TextField hakukentta = new TextField();

        //nappula
        ImageView hakukuva = new ImageView();
        Image hkuva = new Image(getClass().getResourceAsStream("haku.png"));
        hakukuva.setImage(hkuva);
        hakukuva.setFitHeight(20);
        hakukuva.setFitWidth(20);

        Button hakunappula = new Button();
        hakunappula.setGraphic(hakukuva);
        hakunappula.setId("button-haku");

        haku.setPadding(new Insets(10));
        haku.setSpacing(10);
        haku.getChildren().addAll(hakuteksti, hakukentta, hakunappula);

        VBox tulospalkki = new VBox();
        tulospalkki.setPadding(new Insets(10));
        tulospalkki.setSpacing(10);
        tulospalkki.setAlignment(Pos.CENTER);
        hakunappula.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                Haku haku = new Haku();
                ListView tulos = haku.luoHakuTulos(hakukentta.getText());
                tulospalkki.getChildren().add(tulos);
                hakukentta.setText("");

            }
        });
        tulospalkki.setAlignment(Pos.CENTER);
        ScrollPane sb = new ScrollPane();
        sb.setStyle("-fx-background: #fff;");

        hakupalkki.getChildren().addAll(otsikko, haku, tulospalkki);

        grid.add(nimipalkki, 1, 1);
        grid.add(muokkausnappula, 2, 0);
        grid.add(hakupalkki, 1, 2);
        grid.setAlignment(Pos.CENTER_RIGHT);

        osa.getChildren().addAll(grid);
        osa.setAlignment(Pos.CENTER);
        sb.setContent(osa);

        StackPane uusisp = new StackPane();
        uusisp = ikkuna.annaNaytto();
        uusisp.setAlignment(sb, Pos.CENTER);
        ikkuna.annaNaytto().getChildren().add(sb);

    }

    public GridPane luoEtusivuMuokkaus() {
        Button muokkausnappula = new Button("Tallenna");
        muokkausnappula.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (nimi.getText().trim().isEmpty()) {

                    tiedottaja.annaVaroitus("Et voi antaa tyhjää kenttää.");
                } else {

                    ikkuna.annaTurnaus().asetaNimi(nimi.getText());

                    tiedottaja.kirjoitaLoki("Turnauksen nimeä muokattu.");
                    nimi.setText("");
                    ikkuna.asetaMuutos(true);
                    luoEtusivu();
                }

            }
        });

        Button peruuta = new Button("Peruuta");
        peruuta.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                luoEtusivu();

            }
        });

        nimi.setText(ikkuna.annaTurnaus().toString());

        HBox hbox1 = new HBox();
        Label label1 = new Label("Turnauksen nimi: ");
        HBox pakollinen_kentta1 = new HBox();

        pakollinen_kentta1.getChildren().addAll(label1, pakollinen);

        hbox1.setSpacing(10);
        hbox1.getChildren().addAll(pakollinen_kentta1, nimi, muokkausnappula, peruuta);

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(40));
        grid.setAlignment(Pos.CENTER);

        grid.add(hbox1, 1, 7);

        return grid;
    }

    public GridPane luoPaanayttoSarjat() {

        Button lisaysnappula = new Button("Tallenna");
        lisaysnappula.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (nimi.getText().trim().isEmpty()) {

                    tiedottaja.annaVaroitus("Et voi antaa tyhjää kenttää.");
                } else {

                    Turnaus turnaus = (Turnaus) ikkuna.annaTurnaus();
                    Kohde uusi = new Sarja(nimi.getText(), turnaus);
                    turnaus.annaSarjat().add((Sarja) uusi);

                    muuttaja.lisaaKohde(uusi);
                    nimi.setText("");
                    ikkuna.asetaMuutos(true);
                    tiedottaja.kirjoitaLoki("Uusi sarja lisätty");
                    TreeItem<Kohde> uusiPuuItem = new TreeItem<>(uusi);
                    luoSarjaSivu(uusiPuuItem);
                }

            }
        });

        HBox hbox1 = new HBox();
        Label label1 = new Label("Sarjan nimi: ");
        HBox pakollinen_kentta1 = new HBox();

        pakollinen_kentta1.getChildren().addAll(label1, pakollinen);

        hbox1.setSpacing(10);
        hbox1.getChildren().addAll(pakollinen_kentta1, nimi, lisaysnappula);

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(40));
        grid.setAlignment(Pos.TOP_CENTER);

        Label otsikko = new Label("Lisää uusi sarja.");
        otsikko.setFont(Font.font("Papyrus", 28));

        grid.add(otsikko, 1, 5);
        grid.add(hbox1, 1, 7);
        grid.setVgap(20);
        return grid;
    }

    //NÄKYMÄT, JOTKA AUKEE KUN VASEMMALTA ON KLIKANNUT TIETTYÄ SARJAA TAI TUOMARIA
    public void luoSarjaSivu(TreeItem<Kohde> arvo) {

        ScrollPane sb = new ScrollPane();

        Sarja sarja = (Sarja) arvo.getValue();

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20, 10, 40, 10));

        VBox rivi1 = new VBox();
        rivi1.setAlignment(Pos.CENTER);

        Button muokkausnappula = new Button();

        muokkausnappula.setText("Muokkaa");
        muokkausnappula.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

                VBox peitto = new VBox();
                peitto.setStyle("-fx-background-color: white;");
                ikkuna.annaNaytto().getChildren().add(peitto);

                ikkuna.annaNaytto().getChildren().add(luoSarjaMuokkaus(sarja));

            }
        });

        Button poistonappula = new Button("Poista sarja");
        poistonappula.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                varmistaja.annaPoistoVarmistus(sarja);

            }
        });
        HBox painikkeet = new HBox();
        painikkeet.setPadding(new Insets(20));
        painikkeet.setSpacing(20);
        painikkeet.setAlignment(Pos.TOP_RIGHT);
        painikkeet.getChildren().addAll(muokkausnappula, poistonappula);
        rivi1.getChildren().addAll(painikkeet);

        VBox rivi2 = new VBox();
        rivi2.setPadding(new Insets(20));
        rivi2.setAlignment(Pos.CENTER);
        Label nimi = new Label(sarja.toString());
        nimi.setFont(Font.font("Papyrus", 32));

        rivi2.getChildren().addAll(nimi);

        grid.add(painikkeet, 0, 0);
        grid.add(rivi2, 0, 1);

        HBox rivi3 = new HBox();
        rivi3.setPadding(new Insets(20, 10, 0, 20));

        Taulukko taulukontekija1 = new Taulukko(this);

        ottelut = taulukontekija1.luoOtteluTaulukko(sarja);
        ottelut.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        VBox osio1 = new VBox();
        osio1.setSpacing(20);
        osio1.setAlignment(Pos.CENTER);
        Label otsikko1 = new Label("Otteluluettelo");
        otsikko1.setFont(Font.font("Papyrus", 18));

        VBox osio2 = new VBox();
        osio2.setPadding(new Insets(40, 0, 0, 0));
        osio2.setSpacing(20);
        osio2.setAlignment(Pos.CENTER);
        Label otsikko2 = new Label("Joukkueet");
        otsikko2.setFont(Font.font("Papyrus", 18));

        Taulukko taulukontekija3 = new Taulukko(this);
        joukkueet = taulukontekija3.luoJoukkueTaulukko(sarja);
        joukkueet.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        VBox osio3 = new VBox();
        osio3.setSpacing(20);
        osio3.setAlignment(Pos.CENTER);
        Label otsikko3 = new Label("Sarjataulukko");
        otsikko3.setFont(Font.font("Papyrus", 18));

        Taulukko taulukontekija4 = new Taulukko(this);
        sarjataulukko = taulukontekija4.luoSarjaTaulukko(sarja);
        sarjataulukko.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        VBox osio4 = new VBox();
        osio4.setSpacing(20);
        osio4.setAlignment(Pos.CENTER);

        Label otsikko4 = new Label("Pistepörssi");
        otsikko4.setFont(Font.font("Papyrus", 18));
        Taulukko taulukontekija2 = new Taulukko(this);
        pisteporssi = taulukontekija2.luoPisteporssiTaulukko(sarja);
        pisteporssi.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        VBox ylialle = new VBox();
        ylialle.setSpacing(10);
        HBox alle = new HBox();
        alle.setSpacing(10);

        Button ottelunappula = new Button("Lisää yksittäisiä otteluita");

        ottelunappula.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                luoSarjaSivu2(sarja);

            }
        });

        Button autonappula = new Button("Suorita automaattinen otteluluettelon laadinta");

        autonappula.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                varmistaja.annaAutoVarmistus(sarja);

            }
        });

        Button tyhjennysnappula = new Button("Poista kaikki ottelut");

        tyhjennysnappula.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                varmistaja.annaKaikkienOtteluidenPoistoVarmistus(sarja);

            }
        });

        HBox painikeboksi = new HBox();
        painikeboksi.setSpacing(40);

        painikeboksi.getChildren().addAll(ottelunappula, autonappula, tyhjennysnappula);

        ylialle.getChildren().addAll(painikeboksi);

        HBox alle2 = new HBox();
        alle2.setSpacing(30);
        Button joukkuenappula = new Button("Lisää joukkueita");

        joukkuenappula.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                luoSarjaSivu3(sarja);

            }
        });

        Button joukkuepoistonappula = new Button("Poista kaikki joukkueet");

        joukkuepoistonappula.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                varmistaja.annaKaikkienJoukkueidenPoistoVarmistus(sarja);

            }
        });

        alle2.getChildren().addAll(joukkuenappula, joukkuepoistonappula);

        osio1.getChildren().addAll(otsikko1, ottelut, ylialle);
        osio2.getChildren().addAll(otsikko2, joukkueet, alle2);
        osio3.getChildren().addAll(otsikko3, sarjataulukko);
        osio4.getChildren().addAll(otsikko4, pisteporssi);
        rivi3.getChildren().addAll(osio1);

        HBox rivi4 = new HBox();
        rivi4.setPadding(new Insets(40, 0, 0, 0));
        rivi4.setSpacing(40);

        rivi4.getChildren().addAll(osio3);

        HBox rivi5 = new HBox();
        rivi5.setPadding(new Insets(30, 0, 0, 0));

        rivi5.getChildren().addAll(osio4);

        HBox rivi6 = new HBox();
        rivi6.setPadding(new Insets(0));

        rivi6.getChildren().addAll(osio2);
        grid.add(rivi3, 0, 2);
        grid.add(rivi4, 0, 3);
        grid.add(rivi5, 0, 4);
        grid.add(rivi6, 0, 5);
        sb.setContent(grid);
        VBox peitto = new VBox();
        peitto.setStyle("-fx-background-color: white;");
        ikkuna.annaNaytto().getChildren().add(peitto);

        ikkuna.annaNaytto().getChildren().add(sb);

    }

    public void luoSarjaSivu2(Sarja sarja) {

        ScrollPane sb = new ScrollPane();

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20, 10, 40, 10));

        VBox rivi1 = new VBox();
        rivi1.setAlignment(Pos.CENTER);

        Button paluunappula = new Button();

        paluunappula.setText("<< Palaa takaisin");
        paluunappula.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

                TreeItem<Kohde> mihin = new TreeItem<>(sarja);

                luoSarjaSivu(mihin);

            }
        });

        HBox painikkeet = new HBox();
        painikkeet.setPadding(new Insets(20));
        painikkeet.setSpacing(20);
        painikkeet.setAlignment(Pos.TOP_RIGHT);
        painikkeet.getChildren().addAll(paluunappula);
        rivi1.getChildren().addAll(painikkeet);

        VBox rivi2 = new VBox();
        rivi2.setPadding(new Insets(20));
        rivi2.setAlignment(Pos.CENTER);
        Label nimi = new Label("Lisää yksittäisiä otteluita sarjaan " + sarja.toString() + ":");
        nimi.setFont(Font.font("Papyrus", 32));

        rivi2.getChildren().addAll(nimi);

        grid.add(painikkeet, 0, 0);
        grid.add(rivi2, 0, 1);

        HBox rivi3 = new HBox();
        rivi3.setPadding(new Insets(20, 10, 0, 20));

        Taulukko taulukontekija1 = new Taulukko(this);

        ottelut = taulukontekija1.luoOtteluTaulukko(sarja);
        ottelut.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        VBox osio1 = new VBox();
        osio1.setSpacing(20);
        osio1.setAlignment(Pos.CENTER);
        Label otsikko1 = new Label("Otteluluettelo");
        otsikko1.setFont(Font.font("Papyrus", 18));

        VBox ylialle = new VBox();
        ylialle.setSpacing(10);
        HBox alle = new HBox();
        alle.setSpacing(10);

        Label ohje = new Label("Lisää ottelu:");
        ohje.setFont(Font.font("Papyrus", 14));

        VBox ohjekierros = new VBox();
        Label ohjeki = new Label("Kierros");

        ComboBox<Integer> kierros = new ComboBox();
        List<Integer> kierroslista = new ArrayList();

        for (int i = 1; i < 99; i++) {
            kierroslista.add(i);
        }

        ObservableList kierrokset = FXCollections.observableList(kierroslista);
        kierros.setItems(kierrokset);

        ohjekierros.getChildren().addAll(ohjeki, kierros);

        VBox ohjekoti = new VBox();
        Label ohjek = new Label("Koti");
        ComboBox<Joukkue> koti = new ComboBox();
        List<Joukkue> kotijoukkuelista = new ArrayList();

        for (int i = 0; i < sarja.annaJoukkueet().size(); i++) {
            kotijoukkuelista.add(sarja.annaJoukkueet().get(i));
        }

        ObservableList joukkueet1 = FXCollections.observableList(kotijoukkuelista);
        koti.setItems(joukkueet1);
        ohjekoti.getChildren().addAll(ohjek, koti);

        VBox ohjevieras = new VBox();
        Label ohjev = new Label("Vieras");
        ComboBox<Joukkue> vieras = new ComboBox();
        List<Joukkue> vierasjoukkuelista = new ArrayList();

        for (int i = 0; i < sarja.annaJoukkueet().size(); i++) {
            vierasjoukkuelista.add(sarja.annaJoukkueet().get(i));
        }

        ObservableList joukkueet2 = FXCollections.observableList(vierasjoukkuelista);
        vieras.setItems(joukkueet2);
        ohjevieras.getChildren().addAll(ohjev, vieras);

        VBox ohjeaika = new VBox();

        Label ohjea = new Label("Ajankohta");
        DatePicker ajankohta = new DatePicker();
        ajankohta.setValue(LocalDate.now());
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

        ComboBox<String> kellotunnit = new ComboBox();
        List<String> tuntilista = new ArrayList();

        for (int i = 0; i < 24; i++) {
            tuntilista.add("" + i);
        }

        ObservableList tunnit = FXCollections.observableList(tuntilista);
        kellotunnit.setItems(tunnit);
        kellotunnit.getSelectionModel().selectFirst();

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
        kellominuutit.getSelectionModel().selectFirst();

        ohjeaika.getChildren().addAll(ohjea, ajankohta);

        VBox ohjekello = new VBox();
        Label ohjeke = new Label("Kello");

        ohjekello.setAlignment(Pos.CENTER);
        HBox ajanasetus = new HBox();
        ajanasetus.setSpacing(5);
        Label pisteet = new Label(":");
        ajanasetus.getChildren().addAll(kellotunnit, pisteet, kellominuutit);

        ohjekello.getChildren().addAll(ohjeke, ajanasetus);

        VBox ohjepaikka = new VBox();
        Label ohjep = new Label("Paikka");
        TextField paikka = new TextField();
        paikka.setPromptText("Paikka");
        ohjepaikka.getChildren().addAll(ohjep, paikka);

        VBox ohjetuomari1 = new VBox();
        VBox ohjetuomari2 = new VBox();
        VBox ohjetuomari3 = new VBox();

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

        Label ohjet1 = new Label("Erotuomari");
        ComboBox<Tuomari> erotuomari = new ComboBox();
        erotuomari.setItems(erotuomarit);

        erotuomari.setPromptText("Valitse");

        vieras.setPromptText("Valitse");
        koti.setPromptText("Valitse");

        Label ohjet2 = new Label("1. Avustava");
        ComboBox<Tuomari> avustava1 = new ComboBox();
        avustava1.setItems(avustavat1);
        avustava1.setPromptText("Valitse");

        Label ohjet3 = new Label("2. Avustava");
        ComboBox<Tuomari> avustava2 = new ComboBox();
        avustava2.setItems(avustavat2);
        avustava2.setPromptText("Valitse");

        ohjetuomari1.getChildren().addAll(ohjet1, erotuomari);
        ohjetuomari2.getChildren().addAll(ohjet2, avustava1);
        ohjetuomari3.getChildren().addAll(ohjet3, avustava2);

        HBox painikeboksi = new HBox();
        painikeboksi.setPadding(new Insets(15, 0, 0, 0));
        painikeboksi.setSpacing(10);

        Button lisaysnappula = new Button("Tallenna");
        lisaysnappula.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
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
                    muuttaja.lisaaOttelu(koti.getValue(), vieras.getValue(), ajankohta.getValue(), kellotunnit.getValue(), kellominuutit.getValue(), paikka.getText(), erotuomari.getValue(), avustava1.getValue(), avustava2.getValue(), sarja);
                    ikkuna.asetaMuutos(true);
                    TreeItem<Kohde> sarjak = new TreeItem<>((Kohde) sarja);
                    luoSarjaSivu(sarjak);
                }

            }
        });

        Button tyhjennys = new Button("Tyhjennä");
        tyhjennys.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

                luoSarjaSivu2(sarja);
            }
        });

        painikeboksi.getChildren().addAll(lisaysnappula, tyhjennys);

        alle.getChildren().addAll(ohjekierros, ohjekoti, ohjevieras, ohjeaika, ohjekello, ohjepaikka, ohjetuomari1, ohjetuomari2, ohjetuomari3);
        ylialle.getChildren().addAll(ohje, alle, painikeboksi);

        osio1.getChildren().addAll(otsikko1, ottelut, ylialle);

        rivi3.getChildren().addAll(osio1);

        grid.add(rivi3, 0, 2);

        sb.setContent(grid);
        VBox peitto = new VBox();
        peitto.setStyle("-fx-background-color: white;");
        ikkuna.annaNaytto().getChildren().add(peitto);

        ikkuna.annaNaytto().getChildren().add(sb);

    }

    public void luoSarjaSivu3(Sarja sarja) {

        ScrollPane sb = new ScrollPane();

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20, 10, 40, 10));

        VBox rivi1 = new VBox();
        rivi1.setAlignment(Pos.CENTER);

        Button paluunappula = new Button();

        paluunappula.setText("<< Palaa takaisin");
        paluunappula.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

                TreeItem<Kohde> mihin = new TreeItem<>(sarja);

                luoSarjaSivu(mihin);
            }
        });

        HBox painikkeet = new HBox();
        painikkeet.setPadding(new Insets(20));
        painikkeet.setSpacing(20);
        painikkeet.setAlignment(Pos.TOP_RIGHT);
        painikkeet.getChildren().addAll(paluunappula);
        rivi1.getChildren().addAll(painikkeet);

        VBox rivi2 = new VBox();
        rivi2.setPadding(new Insets(20));
        rivi2.setAlignment(Pos.CENTER);
        Label nimi = new Label("Lisää joukkueita sarjaan " + sarja.toString() + ":");
        nimi.setFont(Font.font("Papyrus", 32));

        rivi2.getChildren().addAll(nimi);

        grid.add(painikkeet, 0, 0);
        grid.add(rivi2, 0, 1);

        VBox osio2 = new VBox();
        osio2.setPadding(new Insets(40, 0, 0, 0));
        osio2.setSpacing(20);
        osio2.setAlignment(Pos.CENTER);
        Label otsikko2 = new Label("Joukkueet");
        otsikko2.setFont(Font.font("Papyrus", 18));

        Taulukko taulukontekija3 = new Taulukko(this);
        joukkueet = taulukontekija3.luoJoukkueTaulukko(sarja);
        joukkueet.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        VBox alle2 = new VBox();
        alle2.setSpacing(10);
        Label ohjej = new Label("Lisää joukkue:");
        ohjej.setFont(Font.font("Papyrus", 14));

        HBox painikeboksi2 = new HBox();

        painikeboksi2.setSpacing(10);

        TextField lisaaJoukkue = new TextField();
        lisaaJoukkue.setPromptText("Joukkueen nimi");

        Button lisaysnappula2 = new Button("Tallenna");
        lisaysnappula2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                boolean ok = true;
                //tarkistus, onko tyhjä kenttä
                if (lisaaJoukkue.getText().trim().isEmpty()) {

                    tiedottaja.annaVaroitus("Et voi antaa tyhjää kenttää.");
                    ok = false;
                }
                //tarkistus, onko samanniminen jos tässä sarjassa EI OTA HUOMIOON KIRJAINTEN KOKOA!!!!

                for (int i = 0; i < sarja.annaJoukkueet().size(); i++) {
                    if (sarja.annaJoukkueet().get(i).toString().equals(lisaaJoukkue.getText())) {
                        tiedottaja.annaVaroitus("Tämänniminen joukkue on jo sarjassa.");
                        ok = false;
                    }
                }

                if (ok) {
                    muuttaja.lisaaJoukkue(lisaaJoukkue.getText(), sarja);

                    lisaaJoukkue.clear();

                    luoSarjaSivu3(sarja);
                }
            }
        });

        painikeboksi2.getChildren().addAll(lisaaJoukkue, lisaysnappula2);
        alle2.getChildren().addAll(ohjej, painikeboksi2);

        osio2.getChildren().addAll(otsikko2, joukkueet, alle2);

        HBox rivi6 = new HBox();
        rivi6.setPadding(new Insets(0));

        rivi6.getChildren().addAll(osio2);

        grid.add(rivi6, 0, 5);
        sb.setContent(grid);
        VBox peitto = new VBox();
        peitto.setStyle("-fx-background-color: white;");
        ikkuna.annaNaytto().getChildren().add(peitto);

        ikkuna.annaNaytto().getChildren().add(sb);

    }

    public GridPane luoSarjaMuokkaus(Sarja arvo) {
        Button muokkausnappula = new Button("Tallenna");
        muokkausnappula.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (nimi.getText().trim().isEmpty()) {

                    tiedottaja.annaVaroitus("Et voi antaa tyhjää kenttää.");
                } else {

                    //poisto puusta
                    TreeItem<Kohde> parent = new TreeItem<>();
                    parent = ikkuna.annaRootSarjat();

                    for (int i = 0; i < parent.getChildren().size(); i++) {

                        if (parent.getChildren().get(i).getValue().equals(arvo)) {
                            parent.getChildren().remove(i);
                        }

                    }

                    arvo.asetaNimi(nimi.getText());

                    //muutettu takaisin puuhun
                    TreeItem<Kohde> muutettu = new TreeItem<Kohde>(arvo);
                    parent.getChildren().add(muutettu);

                    tiedottaja.kirjoitaLoki("Sarjan nimeä muokattu.");
                    nimi.setText("");
                    ikkuna.asetaMuutos(true);

                    luoSarjaSivu(muutettu);
                }

            }
        });
        Button peruuta = new Button("Peruuta");
        peruuta.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                TreeItem<Kohde> kuka = new TreeItem<>(arvo);
                luoSarjaSivu(kuka);

            }
        });

        nimi.setText(arvo.toString());

        VBox vbox = new VBox();
        vbox.setSpacing(20);
        Label otsikko = new Label("Muokkaa sarjan " + arvo.toString() + " nimeä: ");
        otsikko.setFont(Font.font("Papyrus", 22));
        HBox hbox1 = new HBox();
        Label label1 = new Label("Sarjan nimi: ");
        HBox pakollinen_kentta1 = new HBox();

        pakollinen_kentta1.getChildren().addAll(label1, pakollinen);
        hbox1.setSpacing(10);
        hbox1.getChildren().addAll(pakollinen_kentta1, nimi);

        HBox painikkeet = new HBox();
        painikkeet.setSpacing(20);
        painikkeet.getChildren().addAll(muokkausnappula, peruuta);

        vbox.getChildren().addAll(otsikko, hbox1, painikkeet);
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(40));
        grid.setAlignment(Pos.CENTER);

        grid.add(vbox, 0, 7);

        return grid;
    }

    public GridPane luoPaanayttoTuomarit() {

        Button lisaysnappula = new Button("Tallenna");
        lisaysnappula.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                boolean ok = true;
                //nimen syöttöjen tarkistus
                if (etunimi.getText().trim().isEmpty() || sukunimi.getText().trim().isEmpty()) {

                    tiedottaja.annaVaroitus("Et voi antaa tyhjää kenttää.");
                    ok = false;
                } else if (etunimi.getText().length() > 64 || sukunimi.getText().length() > 64) {
                    tiedottaja.annaVaroitus("Sekä etu- että sukunimi saa sisältää korkeintaan 64 merkkiä.");
                    ok = false;
                }
                for (char c : etunimi.getText().toCharArray()) {

                    if (!Character.isLetter(c)) {
                        if (!Character.toString(c).equals("-")) {
                            ok = false;
                            tiedottaja.annaVaroitus("Sekä etu- että sukunimi saa sisältää vain kirjaimia ja tavuviivoja.");
                            break;
                        }

                    }
                }
                for (char c : sukunimi.getText().toCharArray()) {
                    if (!Character.isLetter(c)) {
                        if (!Character.toString(c).equals("-")) {
                            ok = false;
                            tiedottaja.annaVaroitus("Sekä etu- että sukunimi saa sisältää vain kirjaimia ja tavuviivoja.");
                            break;
                        }
                    }
                }

                if (ok) {

                    Kohde uusi = new Tuomari(etunimi.getText(), sukunimi.getText());

                    muuttaja.lisaaKohde(uusi);
                    etunimi.setText("");
                    sukunimi.setText("");
                    ikkuna.asetaMuutos(true);
                }

            }
        });

        Button peruuta = new Button("Peruuta");

        peruuta.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                luoEtusivu();

            }
        });

        VBox vbox = new VBox();
        vbox.setSpacing(20);
        HBox hbox1 = new HBox();
        Label label1 = new Label("Etunimi: ");
        HBox pakollinen_kentta1 = new HBox();

        pakollinen_kentta1.getChildren().addAll(label1, pakollinen);
        hbox1.setSpacing(20);
        hbox1.getChildren().addAll(pakollinen_kentta1, etunimi);

        HBox hbox2 = new HBox();
        Label label2 = new Label("Sukunimi: ");
        HBox pakollinen_kentta2 = new HBox();

        pakollinen_kentta2.getChildren().addAll(label2, pakollinen2);
        hbox2.setSpacing(10);
        hbox2.getChildren().addAll(pakollinen_kentta2, sukunimi);

        vbox.getChildren().addAll(hbox1, hbox2, lisaysnappula);

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(40));
        grid.setAlignment(Pos.TOP_CENTER);
        grid.setVgap(20);
        Label otsikko = new Label("Lisää uusi tuomari.");
        otsikko.setFont(Font.font("Papyrus", 28));

        grid.add(otsikko, 1, 5);
        grid.add(vbox, 1, 7);

        return grid;
    }

    public void luoTuomariSivu(TreeItem<Kohde> arvo) {

        ScrollPane sb = new ScrollPane();
        Tuomari tuomari = (Tuomari) arvo.getValue();

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20, 10, 40, 300));

        //riville 1
        HBox painike = new HBox();
        painike.setSpacing(20);
        Button muokkausnappula = new Button();

        muokkausnappula.setText("Muokkaa");
        muokkausnappula.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

                VBox peitto = new VBox();
                peitto.setStyle("-fx-background-color: white;");
                ikkuna.annaNaytto().getChildren().add(peitto);

                ikkuna.annaNaytto().getChildren().add(luoTuomariMuokkaus(tuomari));

            }
        });

        Button poistonappula = new Button("Poista tuomari");
        poistonappula.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                varmistaja.annaPoistoVarmistus(arvo.getValue());

            }
        });

        painike.setPadding(new Insets(60, 20, 20, 20));
        painike.setAlignment(Pos.TOP_RIGHT);
        painike.getChildren().addAll(muokkausnappula, poistonappula);
        grid.add(painike, 0, 0);

        //riville 2
        VBox info = new VBox();
        info.setPadding(new Insets(10, 10, 40, 10));
        Label nimi = new Label(tuomari.toString());
        nimi.setFont(Font.font("Papyrus", 28));
        Label id = new Label("TuomariID: " + tuomari.annaJulkinenId());
        id.setFont(Font.font("Papyrus", 14));
        info.setAlignment(Pos.CENTER);
        info.getChildren().addAll(nimi, id);

        grid.add(info, 0, 1);

        Taulukko taulukontekija1 = new Taulukko(this);

        ottelut = taulukontekija1.luoTuomarinOtteluTaulukko(tuomari);
        ottelut.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        //riville 3
        VBox osio1 = new VBox();
        osio1.setSpacing(20);
        osio1.setAlignment(Pos.CENTER);
        Label otsikko1 = new Label("Otteluluettelo");
        otsikko1.setFont(Font.font("Papyrus", 18));
        osio1.getChildren().addAll(otsikko1, ottelut);

        grid.add(osio1, 0, 2);
        sb.setContent(grid);
        VBox peitto = new VBox();
        peitto.setStyle("-fx-background-color: white;");
        ikkuna.annaNaytto().getChildren().add(peitto);
        ikkuna.annaNaytto().getChildren().add(sb);

    }

    public GridPane luoTuomariMuokkaus(Tuomari tuomari) {
        Button muokkausnappula = new Button("Tallenna");
        muokkausnappula.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                boolean ok = true;
                //nimen syöttöjen tarkistus
                if (etunimi.getText().trim().isEmpty() || sukunimi.getText().trim().isEmpty()) {

                    tiedottaja.annaVaroitus("Et voi antaa tyhjää kenttää.");
                    ok = false;
                } else if (etunimi.getText().length() > 64 || sukunimi.getText().length() > 64) {
                    tiedottaja.annaVaroitus("Sekä etu- että sukunimi saa sisältää korkeintaan 64 merkkiä.");
                    ok = false;
                }
                for (char c : etunimi.getText().toCharArray()) {

                    if (!Character.isLetter(c)) {
                        if (!Character.toString(c).equals("-")) {
                            ok = false;
                            tiedottaja.annaVaroitus("Sekä etu- että sukunimi saa sisältää vain kirjaimia ja tavuviivoja.");
                            break;
                        }

                    }
                }
                for (char c : sukunimi.getText().toCharArray()) {
                    if (!Character.isLetter(c)) {
                        if (!Character.toString(c).equals("-")) {
                            ok = false;
                            tiedottaja.annaVaroitus("Sekä etu- että sukunimi saa sisältää vain kirjaimia ja tavuviivoja.");
                            break;
                        }
                    }
                }

                if (ok) {

                    //poisto puusta
                    TreeItem<Kohde> parent = new TreeItem<>();
                    parent = ikkuna.annaRootTuomarit();

                    for (int i = 0; i < parent.getChildren().size(); i++) {

                        if (parent.getChildren().get(i).getValue().equals(tuomari)) {
                            parent.getChildren().remove(i);
                        }

                    }

                    tuomari.asetaEtuNimi(etunimi.getText());
                    tuomari.asetaSukuNimi(sukunimi.getText());
                    tuomari.asetaNimi(etunimi.getText() + " " + sukunimi.getText());

                    //muutettu takaisin puuhun
                    TreeItem<Kohde> muutettu = new TreeItem<Kohde>(tuomari);
                    parent.getChildren().add(muutettu);

                    tiedottaja.kirjoitaLoki("Tuomarin nimeä muokattu.");
                    nimi.setText("");
                    ikkuna.asetaMuutos(true);

                    luoTuomariSivu(muutettu);
                }

            }
        });

        Button peruuta = new Button("Peruuta");
        peruuta.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                TreeItem<Kohde> kuka = new TreeItem<>(tuomari);
                luoTuomariSivu(kuka);

            }
        });

        etunimi.setText(tuomari.annaEtuNimi());
        sukunimi.setText(tuomari.annaSukuNimi());

        VBox vbox = new VBox();
        vbox.setSpacing(20);

        Label otsikko = new Label("Muokkaa tuomarin " + tuomari.toString() + " tietoja: ");
        otsikko.setFont(Font.font("Papyrus", 22));

        HBox hbox1 = new HBox();
        Label label1 = new Label("Etunimi: ");
        HBox pakollinen_kentta1 = new HBox();

        pakollinen_kentta1.getChildren().addAll(label1, pakollinen);
        hbox1.setSpacing(20);
        hbox1.getChildren().addAll(pakollinen_kentta1, etunimi);
        HBox hbox2 = new HBox();
        Label label2 = new Label("Sukunimi: ");
        HBox pakollinen_kentta2 = new HBox();

        pakollinen_kentta2.getChildren().addAll(label2, pakollinen2);
        hbox2.setSpacing(10);
        hbox2.getChildren().addAll(pakollinen_kentta2, sukunimi);

        HBox painikkeet = new HBox();
        painikkeet.setSpacing(20);
        painikkeet.getChildren().addAll(muokkausnappula, peruuta);
        vbox.getChildren().addAll(otsikko, hbox1, hbox2, painikkeet);

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(40));
        grid.setAlignment(Pos.CENTER);

        grid.add(vbox, 1, 7);

        return grid;
    }

    public void luoJoukkueSivu(Joukkue joukkue) {

        ScrollPane sb = new ScrollPane();
        sb.setHbarPolicy(AS_NEEDED);
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20, 10, 40, 10));

        HBox rivi1 = new HBox();
        rivi1.setPadding(new Insets(20, 10, 20, 80));
        rivi1.setAlignment(Pos.TOP_RIGHT);
        rivi1.setSpacing(20);

        Button muokkausnappula = new Button();

        muokkausnappula.setText("Muokkaa nimeä");
        muokkausnappula.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

                VBox peitto = new VBox();
                peitto.setStyle("-fx-background-color: white;");
                ikkuna.annaNaytto().getChildren().add(peitto);

                ikkuna.annaNaytto().getChildren().add(luoJoukkueMuokkaus(joukkue));

            }
        });

        Button poistonappula = new Button("Poista joukkue");
        poistonappula.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                varmistaja.annaPoistoVarmistus(joukkue);

            }
        });

        Button paluunappula = new Button("<< Palaa takaisin");
        paluunappula.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                TreeItem<Kohde> mihin = new TreeItem<>(joukkue.annaSarja());
                luoSarjaSivu(mihin);

            }
        });

        rivi1.getChildren().addAll(paluunappula, muokkausnappula, poistonappula);
        grid.add(rivi1, 0, 0);

        //rivi2
        VBox info = new VBox();
        info.setPadding(new Insets(10, 10, 40, 10));

        Label nimi = new Label(joukkue.toString());
        nimi.setFont(Font.font("Papyrus", 32));

        info.setAlignment(Pos.CENTER);
        info.getChildren().addAll(nimi);
        grid.add(info, 0, 1);

        HBox rivi3 = new HBox();
        rivi3.setSpacing(40);
        rivi3.setPadding(new Insets(10, 10, 60, 10));
        Taulukko taulukontekija1 = new Taulukko(this);
        ottelut = taulukontekija1.luoJoukkueenOtteluTaulukko(joukkue);
        ottelut.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        VBox osio1 = new VBox();
        osio1.setSpacing(20);
        osio1.setPadding(new Insets(0, 0, 10, 0));
        osio1.setAlignment(Pos.CENTER);
        Label otsikko1 = new Label("Joukkueen ottelut");
        otsikko1.setFont(Font.font("Papyrus", 18));
        osio1.getChildren().addAll(otsikko1, ottelut);

        VBox osio2 = new VBox();
        osio2.setSpacing(20);
        osio2.setAlignment(Pos.CENTER);
        Label otsikko2 = new Label("Pelaajat");
        otsikko2.setFont(Font.font("Papyrus", 18));
        Taulukko taulukontekija3 = new Taulukko(this);

        pelaajat = taulukontekija3.luoPelaajaTaulukko(joukkue);
        pelaajat.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        VBox osio3 = new VBox();
        osio3.setSpacing(20);

        Label otsikko3 = new Label("Toimihenkilöt");
        otsikko3.setFont(Font.font("Papyrus", 18));

        Taulukko taulukontekija4 = new Taulukko(this);
        toimihenkilot = taulukontekija4.luoToimihenkiloTaulukko(joukkue);
        toimihenkilot.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        VBox osio4 = new VBox();

        osio4.setSpacing(20);

        Label otsikko4 = new Label("Joukkueen sisäinen pistepörssi");
        otsikko4.setFont(Font.font("Papyrus", 18));
        Taulukko taulukontekija2 = new Taulukko(this);
        pisteporssi = taulukontekija2.luoJoukkueenPisteporssiTaulukko(joukkue);
        pisteporssi.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        osio4.getChildren().addAll(otsikko4, pisteporssi);

        HBox pelaajienalle = new HBox();
        pelaajienalle.setPadding(new Insets(20, 0, 0, 0));
        pelaajienalle.setSpacing(30);

        Button plisaysnappula = new Button("Lisää pelaajia");
        plisaysnappula.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                luoJoukkueenPelaajaLisays(joukkue);

            }
        });

        Button pepoistonappula = new Button("Poista kaikki pelaajat");
        pepoistonappula.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                varmistaja.annaKaikkienPelaajienPoistoVarmitus(joukkue);

            }
        });

        pelaajienalle.getChildren().addAll(plisaysnappula, pepoistonappula);

        HBox toimarienalle = new HBox();
        toimarienalle.setPadding(new Insets(20, 0, 0, 0));
        toimarienalle.setSpacing(30);

        Button tlisaysnappula = new Button("Lisää toimihenkilöitä");
        tlisaysnappula.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                luoJoukkueenToimariLisays(joukkue);

            }
        });

        Button tpoistonappula = new Button("Poista kaikki toimihenkilöt");
        tpoistonappula.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                varmistaja.annaKaikkienToimarienPoistoVarmitus(joukkue);

            }
        });

        toimarienalle.getChildren().addAll(tlisaysnappula, tpoistonappula);

        osio3.getChildren().addAll(otsikko3, toimihenkilot, toimarienalle);
        osio2.getChildren().addAll(otsikko2, pelaajat, pelaajienalle);
        rivi3.getChildren().addAll(osio1);
        grid.add(rivi3, 0, 2);

        HBox rivi4 = new HBox();
        rivi4.setSpacing(40);
        rivi4.getChildren().addAll(osio2, osio4);

        HBox rivi5 = new HBox();
        rivi5.setPadding(new Insets(40, 0, 0, 0));
        rivi5.getChildren().addAll(osio3);
        grid.add(rivi4, 0, 3);
        grid.add(rivi5, 0, 4);

        sb.setContent(grid);
        VBox peitto = new VBox();
        peitto.setStyle("-fx-background-color: white;");
        ikkuna.annaNaytto().getChildren().add(peitto);

        ikkuna.annaNaytto().getChildren().add(sb);

    }

    public void luoJoukkueenPelaajaLisays(Joukkue joukkue) {

        ScrollPane sb = new ScrollPane();
        sb.setHbarPolicy(AS_NEEDED);
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20, 10, 40, 10));

        HBox rivi1 = new HBox();
        rivi1.setPadding(new Insets(20, 10, 20, 80));
        rivi1.setAlignment(Pos.TOP_RIGHT);
        rivi1.setSpacing(20);

        Button paluunappula = new Button();

        paluunappula.setText("<< Palaa takaisin");
        paluunappula.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

                luoJoukkueSivu(joukkue);

            }
        });

        rivi1.getChildren().addAll(paluunappula);
        grid.add(rivi1, 0, 0);

        //rivi2
        VBox info = new VBox();
        info.setPadding(new Insets(10, 10, 40, 10));

        Label nimi = new Label("Lisää pelaajia joukkueeseen " + joukkue.toString() + ":");
        nimi.setFont(Font.font("Papyrus", 32));

        info.setAlignment(Pos.CENTER);
        info.getChildren().addAll(nimi);
        grid.add(info, 0, 1);

        VBox osio2 = new VBox();
        osio2.setSpacing(20);
        osio2.setAlignment(Pos.CENTER);
        Label otsikko2 = new Label("Pelaajat");
        otsikko2.setFont(Font.font("Papyrus", 18));
        Taulukko taulukontekija3 = new Taulukko(this);

        pelaajat = taulukontekija3.luoPelaajaTaulukko(joukkue);
        pelaajat.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        VBox alle = new VBox();
        alle.setPadding(new Insets(20, 0, 60, 0));
        Label ohje = new Label("Lisää uusi pelaaja:");
        ohje.setFont(Font.font("Papyrus", 14));

        HBox lisays = new HBox();
        lisays.setSpacing(10);
        lisays.setPadding(new Insets(10, 0, 0, 0));

        VBox vbox1 = new VBox();

        Label ohjeE = new Label("Etunimi: ");
        HBox pakollinen_kentta1 = new HBox();

        pakollinen_kentta1.getChildren().addAll(ohjeE, pakollinen);
        TextField etunimi = new TextField();
        etunimi.setPromptText("Etunimi");
        vbox1.getChildren().addAll(pakollinen_kentta1, etunimi);

        VBox vbox2 = new VBox();

        Label ohjeS = new Label("Sukunimi: ");
        HBox pakollinen_kentta2 = new HBox();

        pakollinen_kentta2.getChildren().addAll(ohjeS, pakollinen2);
        TextField sukunimi = new TextField();
        sukunimi.setPromptText("Sukunimi");
        vbox2.getChildren().addAll(pakollinen_kentta2, sukunimi);

        VBox vbox3 = new VBox();

        Label ohjePe = new Label("Pelipaikka");
        TextField pelipaikka = new TextField();
        pelipaikka.setPromptText("Pelipaikka");
        vbox3.getChildren().addAll(ohjePe, pelipaikka);

        VBox vbox0 = new VBox();

        Label ohjePn = new Label("Pelinumero");
        ComboBox<Integer> pelinumero = new ComboBox();
        List<Integer> numerolista = new ArrayList();
        for (int i = 1; i <= 99; i++) {
            numerolista.add(i);
        }

        pelinumero.setItems(FXCollections.observableArrayList(numerolista));
        pelinumero.getSelectionModel().selectFirst();
        vbox0.getChildren().addAll(ohjePn, pelinumero);

        VBox vbox4 = new VBox();

        Label ohjeTy = new Label("");
        Button lisaysnappula = new Button("Lisää");
        lisaysnappula.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                boolean ok = true;
                //nimen syöttöjen tarkistus
                if (etunimi.getText().trim().isEmpty() || sukunimi.getText().trim().isEmpty()) {

                    tiedottaja.annaVaroitus("Et voi antaa tyhjää kenttää.");
                    ok = false;
                } else if (etunimi.getText().length() > 64 || sukunimi.getText().length() > 64) {
                    tiedottaja.annaVaroitus("Sekä etu- että sukunimi saa sisältää korkeintaan 64 merkkiä.");
                    ok = false;
                }
                for (char c : etunimi.getText().toCharArray()) {

                    if (!Character.isLetter(c)) {
                        if (!Character.toString(c).equals("-")) {
                            ok = false;
                            tiedottaja.annaVaroitus("Sekä etu- että sukunimi saa sisältää vain kirjaimia ja tavuviivoja.");
                            break;
                        }

                    }
                }
                for (char c : sukunimi.getText().toCharArray()) {
                    if (!Character.isLetter(c)) {
                        if (!Character.toString(c).equals("-")) {
                            ok = false;
                            tiedottaja.annaVaroitus("Sekä etu- että sukunimi saa sisältää vain kirjaimia ja tavuviivoja.");
                            break;
                        }
                    }
                }
                // pelinumeron tarkistus, eli että onko jo käytössä

                for (int i = 0; i < joukkue.annaPelaajat().size(); i++) {

                    if (joukkue.annaPelaajat().get(i).annaPelinumero() == pelinumero.getValue()) {
                        ok = false;
                        tiedottaja.annaVaroitus("Valitsemasi pelinumero on jo käytössä toisella joukkueen pelaajalla.");
                        break;
                    }

                }

                if (ok) {
                    muuttaja.lisaaPelaaja(etunimi.getText(), sukunimi.getText(), pelipaikka.getText(), pelinumero.getValue(), joukkue);

                    luoJoukkueenPelaajaLisays(joukkue);
                }
            }
        });
        vbox0.getChildren().addAll(ohjeTy, lisaysnappula);

        lisays.getChildren().addAll(vbox0, vbox1, vbox2, vbox3, vbox4);
        alle.getChildren().addAll(ohje, lisays);
        osio2.getChildren().addAll(otsikko2, pelaajat, alle);

        HBox rivi4 = new HBox();
        rivi4.setSpacing(40);
        rivi4.getChildren().addAll(osio2);

        grid.add(rivi4, 0, 3);

        sb.setContent(grid);
        VBox peitto = new VBox();
        peitto.setStyle("-fx-background-color: white;");
        ikkuna.annaNaytto().getChildren().add(peitto);

        ikkuna.annaNaytto().getChildren().add(sb);

    }

    public void luoJoukkueenToimariLisays(Joukkue joukkue) {

        ScrollPane sb = new ScrollPane();
        sb.setHbarPolicy(AS_NEEDED);
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20, 10, 40, 10));

        HBox rivi1 = new HBox();
        rivi1.setPadding(new Insets(20, 10, 20, 80));
        rivi1.setAlignment(Pos.TOP_RIGHT);
        rivi1.setSpacing(20);

        Button paluunappula = new Button();

        paluunappula.setText("<< Palaa takaisin");
        paluunappula.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

                luoJoukkueSivu(joukkue);

            }
        });

        rivi1.getChildren().addAll(paluunappula);
        grid.add(rivi1, 0, 0);

        //rivi2
        VBox info = new VBox();
        info.setPadding(new Insets(10, 10, 40, 10));

        Label nimi = new Label("Lisää toimihenkilöitä joukkueeseen " + joukkue.toString() + ":");
        nimi.setFont(Font.font("Papyrus", 32));

        info.setAlignment(Pos.CENTER);
        info.getChildren().addAll(nimi);
        grid.add(info, 0, 1);

        VBox osio3 = new VBox();
        osio3.setSpacing(20);

        Label otsikko3 = new Label("Toimihenkilöt");
        otsikko3.setFont(Font.font("Papyrus", 18));

        Taulukko taulukontekija4 = new Taulukko(this);
        toimihenkilot = taulukontekija4.luoToimihenkiloTaulukko(joukkue);
        toimihenkilot.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        VBox alle2 = new VBox();
        alle2.setPadding(new Insets(20, 0, 60, 0));
        Label ohje2 = new Label("Lisää uusi toimihenkilö:");
        ohje2.setFont(Font.font("Papyrus", 14));

        HBox lisays2 = new HBox();
        lisays2.setSpacing(10);
        lisays2.setPadding(new Insets(10, 0, 0, 0));

        VBox vbox11 = new VBox();

        Label ohjeE2 = new Label("Etunimi: ");
        HBox pakollinen_kentta3 = new HBox();

        pakollinen_kentta3.getChildren().addAll(ohjeE2, pakollinen3);
        TextField etunimi2 = new TextField();
        etunimi2.setPromptText("Etunimi");
        vbox11.getChildren().addAll(pakollinen_kentta3, etunimi2);

        VBox vbox12 = new VBox();

        Label ohjeS2 = new Label("Sukunimi: ");
        HBox pakollinen_kentta4 = new HBox();

        pakollinen_kentta4.getChildren().addAll(ohjeS2, pakollinen4);
        TextField sukunimi2 = new TextField();
        sukunimi2.setPromptText("Sukunimi");
        vbox12.getChildren().addAll(pakollinen_kentta4, sukunimi2);

        VBox vbox13 = new VBox();

        Label ohjeR = new Label("Rooli");
        TextField rooli = new TextField();
        rooli.setPromptText("Rooli");
        vbox13.getChildren().addAll(ohjeR, rooli);

        VBox vbox14 = new VBox();

        Label ohjeSp = new Label("Sähköposti");
        TextField sposti = new TextField();
        sposti.setPromptText("Sähköposti");
        vbox14.getChildren().addAll(ohjeSp, sposti);

        VBox vbox15 = new VBox();

        Label ohjePuh = new Label("Puh");
        TextField puh = new TextField();
        puh.setPromptText("Puhelinnumero");
        vbox15.getChildren().addAll(ohjePuh, puh);

        VBox vbox16 = new VBox();

        Label ohjeTyh = new Label("");
        Button lisaysnappula2 = new Button("Lisää");
        lisaysnappula2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                boolean ok = true;
                //nimen syöttöjen tarkistus
                if (etunimi2.getText().trim().isEmpty() || sukunimi2.getText().trim().isEmpty()) {

                    tiedottaja.annaVaroitus("Et voi antaa tyhjää kenttää.");
                    ok = false;
                } else if (etunimi2.getText().length() > 64 || sukunimi2.getText().length() > 64) {
                    tiedottaja.annaVaroitus("Sekä etu- että sukunimi saa sisältää korkeintaan 64 merkkiä.");
                    ok = false;
                }
                for (char c : etunimi2.getText().toCharArray()) {

                    if (!Character.isLetter(c)) {
                        if (!Character.toString(c).equals("-")) {
                            ok = false;
                            tiedottaja.annaVaroitus("Sekä etu- että sukunimi saa sisältää vain kirjaimia ja tavuviivoja.");
                            break;
                        }

                    }
                }
                for (char c : sukunimi2.getText().toCharArray()) {
                    if (!Character.isLetter(c)) {
                        if (!Character.toString(c).equals("-")) {
                            ok = false;
                            tiedottaja.annaVaroitus("Sekä etu- että sukunimi saa sisältää vain kirjaimia ja tavuviivoja.");
                            break;
                        }
                    }
                }

                if (ok) {
                    muuttaja.lisaaToimari(etunimi2.getText(), sukunimi2.getText(), rooli.getText(), sposti.getText(), puh.getText(), joukkue);

                    luoJoukkueenToimariLisays(joukkue);
                }
            }
        });
        vbox16.getChildren().addAll(ohjeTyh, lisaysnappula2);

        lisays2.getChildren().addAll(vbox11, vbox12, vbox13, vbox14, vbox15, vbox16);
        alle2.getChildren().addAll(ohje2, lisays2);
        osio3.getChildren().addAll(otsikko3, toimihenkilot, alle2);

        HBox rivi5 = new HBox();
        rivi5.setPadding(new Insets(40, 0, 0, 0));
        rivi5.getChildren().addAll(osio3);

        grid.add(rivi5, 0, 4);

        sb.setContent(grid);
        VBox peitto = new VBox();
        peitto.setStyle("-fx-background-color: white;");
        ikkuna.annaNaytto().getChildren().add(peitto);

        ikkuna.annaNaytto().getChildren().add(sb);

    }

    public GridPane luoJoukkueMuokkaus(Joukkue joukkue) {
        Button muokkausnappula = new Button("Tallenna");
        muokkausnappula.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (nimi.getText().trim().isEmpty()) {

                    tiedottaja.annaVaroitus("Et voi antaa tyhjää kenttää.");
                } else {

                    joukkue.asetaNimi(nimi.getText());

                    tiedottaja.kirjoitaLoki("Joukkueen nimeä muokattu.");
                    nimi.setText("");
                    ikkuna.asetaMuutos(true);

                    luoJoukkueSivu(joukkue);
                }

            }
        });

        Button peruuta = new Button("Peruuta");
        peruuta.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                luoJoukkueSivu(joukkue);

            }
        });

        nimi.setText(joukkue.toString());

        VBox vbox = new VBox();
        vbox.setSpacing(20);

        Label otsikko = new Label("Muokkaa joukkueen " + joukkue.toString() + " tietoja: ");
        otsikko.setFont(Font.font("Papyrus", 22));

        HBox hbox1 = new HBox();
        Label label1 = new Label("Nimi: ");
        HBox pakollinen_kentta1 = new HBox();

        pakollinen_kentta1.getChildren().addAll(label1, pakollinen);
        hbox1.setSpacing(20);
        hbox1.getChildren().addAll(pakollinen_kentta1, nimi);

        HBox painikkeet = new HBox();
        painikkeet.setSpacing(20);
        painikkeet.getChildren().addAll(muokkausnappula, peruuta);
        vbox.getChildren().addAll(otsikko, hbox1, painikkeet);

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(40));
        grid.setAlignment(Pos.CENTER);

        grid.add(vbox, 1, 7);

        return grid;
    }

    public void luoPelaajaSivu(Pelaaja pelaaja) {
        ScrollPane sb = new ScrollPane();

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20, 10, 40, 300));

        //riville 1
        HBox painike = new HBox();
        painike.setSpacing(20);

        Button muokkausnappula = new Button();

        muokkausnappula.setText("Muokkaa");
        muokkausnappula.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

                VBox peitto = new VBox();
                peitto.setStyle("-fx-background-color: white;");
                ikkuna.annaNaytto().getChildren().add(peitto);

                luoPelaajaMuokkaus(pelaaja);

            }
        });

        Button poistonappula = new Button("Poista pelaaja");
        poistonappula.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                varmistaja.annaPoistoVarmistus(pelaaja);

            }
        });

        Button paluunappula = new Button("<< Palaa");
        paluunappula.setPadding(new Insets(0, 400, 0, 0));
        paluunappula.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                Joukkue joukkue = pelaaja.annaJoukkue();
                luoJoukkueSivu(joukkue);

            }
        });

        painike.setPadding(new Insets(20));

        painike.getChildren().addAll(paluunappula, muokkausnappula, poistonappula);
        grid.add(painike, 0, 0);

        //riville 2
        VBox info = new VBox();
        info.setSpacing(10);
        info.setPadding(new Insets(10, 10, 20, 10));
        Label nimi = new Label(pelaaja.toString());
        nimi.setFont(Font.font("Papyrus", 32));
        info.setAlignment(Pos.CENTER);

        HBox tiedot = new HBox();
        tiedot.setPadding(new Insets(10, 10, 40, 10));
        tiedot.setSpacing(40);

        Label id = new Label("PelaajaID: " + pelaaja.annaJulkinenID());
        id.setFont(Font.font("Papyrus", 14));
        info.getChildren().addAll(nimi, id);

        Label pelipaikka = new Label("Pelipaikka: " + pelaaja.annaPelipaikka());
        pelipaikka.setFont(Font.font("Papyrus", 14));
        Label pelinumero = new Label("Pelinumero: " + pelaaja.annaPelinumero());
        pelinumero.setFont(Font.font("Papyrus", 14));
        Label joukkue = new Label("Joukkue: " + pelaaja.annaJoukkue());
        joukkue.setFont(Font.font("Papyrus", 14));

        info.setAlignment(Pos.CENTER);
        tiedot.setAlignment(Pos.CENTER);
        tiedot.getChildren().addAll(pelinumero, pelipaikka, joukkue);
        grid.add(info, 0, 1);
        grid.add(tiedot, 0, 2);

        Taulukko taulukontekija1 = new Taulukko(this);

        pisteet = taulukontekija1.luoPelaajanPisteTaulukko(pelaaja);
        pisteet.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        //riville 3
        VBox osio1 = new VBox();
        osio1.setSpacing(20);
        osio1.setAlignment(Pos.CENTER);
        Label otsikko1 = new Label("Pelaajan pistetilasto:");
        otsikko1.setFont(Font.font("Papyrus", 18));
        osio1.getChildren().addAll(otsikko1, pisteet);

        grid.add(osio1, 0, 3);
        sb.setContent(grid);
        VBox peitto = new VBox();
        peitto.setStyle("-fx-background-color: white;");
        ikkuna.annaNaytto().getChildren().add(peitto);
        ikkuna.annaNaytto().getChildren().add(sb);

    }

    public void luoPelaajaMuokkaus(Pelaaja pelaaja) {

        Button muokkausnappula = new Button("Tallenna");
        muokkausnappula.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                boolean ok = true;

                //nimen syöttöjen tarkistus
                if (etunimi.getText().trim().isEmpty() || sukunimi.getText().trim().isEmpty()) {

                    tiedottaja.annaVaroitus("Et voi antaa tyhjää kenttää.");
                    ok = false;
                } else if (etunimi.getText().length() > 64 || sukunimi.getText().length() > 64) {
                    tiedottaja.annaVaroitus("Sekä etu- että sukunimi saa sisältää korkeintaan 64 merkkiä.");
                    ok = false;
                }
                for (char c : etunimi.getText().toCharArray()) {

                    if (!Character.isLetter(c)) {
                        if (!Character.toString(c).equals("-")) {
                            ok = false;
                            tiedottaja.annaVaroitus("Sekä etu- että sukunimi saa sisältää vain kirjaimia ja tavuviivoja.");
                            break;
                        }

                    }
                }
                for (char c : sukunimi.getText().toCharArray()) {
                    if (!Character.isLetter(c)) {
                        if (!Character.toString(c).equals("-")) {
                            ok = false;
                            tiedottaja.annaVaroitus("Sekä etu- että sukunimi saa sisältää vain kirjaimia ja tavuviivoja.");
                            break;
                        }
                    }
                }

                // pelinumeron tarkistus, eli että onko jo käytössä
                Joukkue joukkue = pelaaja.annaJoukkue();

                for (int i = 0; i < joukkue.annaPelaajat().size(); i++) {
                    if (!joukkue.annaPelaajat().get(i).equals(pelaaja)) {
                        if (joukkue.annaPelaajat().get(i).annaPelinumero() == pelinumero.getValue()) {
                            ok = false;
                            tiedottaja.annaVaroitus("Valitsema pelinumero on jo käytössä toisella joukkueen pelaajalla.");
                            break;
                        }
                    }

                }

                if (ok) {

                    pelaaja.asetaEtuNimi(etunimi.getText());
                    pelaaja.asetaSukuNimi(sukunimi.getText());
                    pelaaja.asetaNimi(etunimi.getText() + " " + sukunimi.getText());
                    pelaaja.asetaPelipaikka(pelipaikka.getText());
                    pelaaja.asetaPelinumero(pelinumero.getValue());

                    tiedottaja.kirjoitaLoki("Pelaajan tietoja muokattu.");
                    nimi.setText("");
                    ikkuna.asetaMuutos(true);

                    luoPelaajaSivu(pelaaja);
                }

            }
        });

        Button peruuta = new Button("Peruuta");
        peruuta.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                luoPelaajaSivu(pelaaja);

            }
        });
        List<Integer> numerolista = new ArrayList();
        for (int i = 1; i <= 99; i++) {
            numerolista.add(i);
        }
        pelinumero.setItems(FXCollections.observableArrayList(numerolista));

        etunimi.setText(pelaaja.annaEtuNimi());
        sukunimi.setText(pelaaja.annaSukuNimi());
        pelinumero.setValue(pelaaja.annaPelinumero());
        pelipaikka.setText(pelaaja.annaPelipaikka());

        VBox vbox = new VBox();
        vbox.setSpacing(20);

        Label otsikko = new Label("Muokkaa pelaajan " + pelaaja.toString() + " tietoja: ");
        otsikko.setFont(Font.font("Papyrus", 22));

        HBox hbox1 = new HBox();
        hbox1.setPadding(new Insets(20, 0, 0, 0));
        Label label1 = new Label("Etunimi: ");
        HBox pakollinen_kentta1 = new HBox();

        pakollinen_kentta1.getChildren().addAll(label1, pakollinen);
        hbox1.setSpacing(20);
        hbox1.getChildren().addAll(pakollinen_kentta1, etunimi);
        HBox hbox2 = new HBox();
        Label label2 = new Label("Sukunimi: ");
        HBox pakollinen_kentta2 = new HBox();

        pakollinen_kentta2.getChildren().addAll(label2, pakollinen2);
        hbox2.setSpacing(10);
        hbox2.getChildren().addAll(pakollinen_kentta2, sukunimi);

        HBox hbox3 = new HBox();
        Label label3 = new Label("Pelinumero:");
        hbox3.setSpacing(20);
        hbox3.getChildren().addAll(label3, pelinumero);

        HBox hbox4 = new HBox();
        Label label4 = new Label("Pelipaikka:");
        hbox4.setSpacing(20);
        hbox4.getChildren().addAll(label4, pelipaikka);

        HBox painikkeet = new HBox();
        painikkeet.setSpacing(20);
        painikkeet.getChildren().addAll(muokkausnappula, peruuta);
        vbox.getChildren().addAll(otsikko, hbox1, hbox2, hbox3, hbox4, painikkeet);

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(40));
        grid.setAlignment(Pos.CENTER);

        grid.add(vbox, 1, 7);

        VBox peitto = new VBox();
        peitto.setStyle("-fx-background-color: white;");
        ikkuna.annaNaytto().getChildren().add(peitto);
        ikkuna.annaNaytto().getChildren().add(grid);
    }

    public void luoToimariSivu(Toimihenkilo toimari) {
        ScrollPane sb = new ScrollPane();

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20, 10, 40, 300));

        //riville 1
        HBox painike = new HBox();
        painike.setSpacing(20);

        Button muokkausnappula = new Button();

        muokkausnappula.setText("Muokkaa");
        muokkausnappula.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

                VBox peitto = new VBox();
                peitto.setStyle("-fx-background-color: white;");
                ikkuna.annaNaytto().getChildren().add(peitto);

                luoToimariMuokkaus(toimari);

            }
        });

        Button poistonappula = new Button("Poista toimihenkilö");
        poistonappula.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                varmistaja.annaPoistoVarmistus(toimari);

            }
        });

        Button paluunappula = new Button("<< Palaa");
        paluunappula.setPadding(new Insets(0, 150, 0, 0));
        paluunappula.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                Joukkue joukkue = toimari.annaJoukkue();
                luoJoukkueSivu(joukkue);

            }
        });
        painike.setPadding(new Insets(20));

        painike.getChildren().addAll(paluunappula, muokkausnappula, poistonappula);
        grid.add(painike, 0, 0);

        //riville 2
        VBox info = new VBox();
        info.setPadding(new Insets(10, 10, 20, 10));
        Label nimi = new Label(toimari.toString());
        nimi.setFont(Font.font("Papyrus", 32));
        info.setAlignment(Pos.CENTER);
        info.getChildren().addAll(nimi);

        HBox tiedot = new HBox();
        tiedot.setPadding(new Insets(10, 10, 40, 10));
        tiedot.setSpacing(40);

        Label rooli = new Label("Rooli: " + toimari.annaRooli());
        rooli.setFont(Font.font("Papyrus", 14));
        Label sposti = new Label("Sähköpostiosoite: " + toimari.annaSposti());
        sposti.setFont(Font.font("Papyrus", 14));
        Label puh = new Label("Puhelinnumero: " + toimari.annaPuh());
        puh.setFont(Font.font("Papyrus", 18));

        info.setAlignment(Pos.CENTER);
        tiedot.setAlignment(Pos.CENTER);
        tiedot.getChildren().addAll(rooli, sposti, puh);
        grid.add(info, 0, 1);
        grid.add(tiedot, 0, 2);

        sb.setContent(grid);
        VBox peitto = new VBox();
        peitto.setStyle("-fx-background-color: white;");
        ikkuna.annaNaytto().getChildren().add(peitto);
        ikkuna.annaNaytto().getChildren().add(sb);
    }

    public void luoToimariMuokkaus(Toimihenkilo toimari) {
        Button muokkausnappula = new Button("Tallenna");
        muokkausnappula.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                boolean ok = true;
                //nimen syöttöjen tarkistus
                if (etunimi.getText().trim().isEmpty() || sukunimi.getText().trim().isEmpty()) {

                    tiedottaja.annaVaroitus("Et voi antaa tyhjää kenttää.");
                    ok = false;
                } else if (etunimi.getText().length() > 64 || sukunimi.getText().length() > 64) {
                    tiedottaja.annaVaroitus("Sekä etu- että sukunimi saa sisältää korkeintaan 64 merkkiä.");
                    ok = false;
                }
                for (char c : etunimi.getText().toCharArray()) {

                    if (!Character.isLetter(c)) {
                        if (!Character.toString(c).equals("-")) {
                            ok = false;
                            tiedottaja.annaVaroitus("Sekä etu- että sukunimi saa sisältää vain kirjaimia ja tavuviivoja.");
                            break;
                        }

                    }
                }
                for (char c : sukunimi.getText().toCharArray()) {
                    if (!Character.isLetter(c)) {
                        if (!Character.toString(c).equals("-")) {
                            ok = false;
                            tiedottaja.annaVaroitus("Sekä etu- että sukunimi saa sisältää vain kirjaimia ja tavuviivoja.");
                            break;
                        }
                    }
                }

                if (ok) {

                    toimari.asetaEtuNimi(etunimi.getText());
                    toimari.asetaSukuNimi(sukunimi.getText());
                    toimari.asetaNimi(etunimi.getText() + " " + sukunimi.getText());

                    toimari.asetaRooli(rooli.getText());
                    toimari.asetaSposti(sposti.getText());
                    toimari.asetaPuh(puh.getText());

                    tiedottaja.kirjoitaLoki("Toimihenkilön tietoja muokattu.");
                    nimi.setText("");
                    ikkuna.asetaMuutos(true);

                    luoToimariSivu(toimari);
                }

            }
        });

        Button peruuta = new Button("Peruuta");
        peruuta.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                luoToimariSivu(toimari);

            }
        });

        etunimi.setText(toimari.annaEtuNimi());
        sukunimi.setText(toimari.annaSukuNimi());

        rooli.setText(toimari.annaRooli());
        sposti.setText(toimari.annaSposti());
        puh.setText(toimari.annaPuh());

        VBox vbox = new VBox();
        vbox.setSpacing(20);

        Label otsikko = new Label("Muokkaa toimihenkilön " + toimari.toString() + " tietoja: ");
        otsikko.setFont(Font.font("Papyrus", 22));

        HBox hbox1 = new HBox();
        Label label1 = new Label("Etunimi: ");
        HBox pakollinen_kentta1 = new HBox();

        pakollinen_kentta1.getChildren().addAll(label1, pakollinen);
        hbox1.setSpacing(20);
        hbox1.getChildren().addAll(pakollinen_kentta1, etunimi);
        HBox hbox2 = new HBox();
        Label label2 = new Label("Sukunimi: ");
        HBox pakollinen_kentta2 = new HBox();

        pakollinen_kentta2.getChildren().addAll(label2, pakollinen2);
        hbox2.setSpacing(10);
        hbox2.getChildren().addAll(pakollinen_kentta2, sukunimi);
        HBox hbox3 = new HBox();
        Label label3 = new Label("Rooli:");
        hbox3.setSpacing(10);
        hbox3.getChildren().addAll(label3, rooli);
        HBox hbox4 = new HBox();
        Label label4 = new Label("Sähköposti:");
        hbox4.setSpacing(10);
        hbox4.getChildren().addAll(label4, sposti);
        HBox hbox5 = new HBox();
        Label label5 = new Label("Puh.:");
        hbox5.setSpacing(10);
        hbox5.getChildren().addAll(label5, puh);
        HBox painikkeet = new HBox();
        painikkeet.setSpacing(20);
        painikkeet.getChildren().addAll(muokkausnappula, peruuta);
        vbox.getChildren().addAll(otsikko, hbox1, hbox2, hbox3, hbox4, hbox5, painikkeet);

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(40));
        grid.setAlignment(Pos.CENTER);

        grid.add(vbox, 1, 7);

        VBox peitto = new VBox();
        peitto.setStyle("-fx-background-color: white;");
        ikkuna.annaNaytto().getChildren().add(peitto);
        ikkuna.annaNaytto().getChildren().add(grid);
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

                VBox peitto = new VBox();
                peitto.setStyle("-fx-background-color: white;");
                ikkuna.annaNaytto().getChildren().add(peitto);

                ikkuna.annaNaytto().getChildren().add(luoOtteluMuokkaus(ottelu, ottelu.annaSarja()));

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
                luoSarjaSivu(mihin);

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

    public ScrollPane luoOtteluMuokkaus(Ottelu ottelu, Sarja sarja) {
        ScrollPane sb = new ScrollPane();
        sb.setHbarPolicy(AS_NEEDED);
        GridPane grid = new GridPane();

        VBox ohjekierros = new VBox();
        Label ohjeki = new Label("Kierros");

        ComboBox<Integer> kierros = new ComboBox();
        List<Integer> kierroslista = new ArrayList();

        for (int i = 1; i < 99; i++) {
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
        return sb;
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
