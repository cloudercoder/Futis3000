package tupa;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
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
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.util.StringConverter;

/**
 *
 * @author Marianne
 */
public class SarjaNakyma {

    private Tupa ikkuna;
    private Muuttaja muuttaja;
    //uusien kohtien lisäystä varten
    private TextField nimi = new TextField();

    private Label pakollinen = new Label("*");
    private Label pakollinen2 = new Label("*");
    private Label pakollinen3 = new Label("*");
    private Label pakollinen4 = new Label("*");

    private Tiedottaja tiedottaja;
    private Varmistaja varmistaja;

    //taulukot
    private TableView ottelut;
    private TableView pisteporssi;
    private TableView joukkueet;
    private TableView sarjataulukko;

    private PaaNakyma nakyma;

    SarjaNakyma() {

    }

    SarjaNakyma(Tupa ikkuna, PaaNakyma nakyma) {
        this.ikkuna = ikkuna;
        this.nakyma = nakyma;
        muuttaja = new Muuttaja(ikkuna, nakyma);
        tiedottaja = new Tiedottaja(ikkuna);
        varmistaja = new Varmistaja(ikkuna, nakyma);
        pakollinen.setId("label-pakko");
        pakollinen2.setId("label-pakko");
        pakollinen3.setId("label-pakko");
        pakollinen4.setId("label-pakko");

    }

    public void luoSarjanLisaysSivu() {

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
                    nimi.clear();
                    ikkuna.asetaMuutos(true);
                

                    luoSarjanLisaysSivu();
                }

            }
        });

        Button peruuta = new Button("Peruuta");
        peruuta.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                nakyma.luoEtusivu();
            }
        });

        VBox lisays = new VBox();
        lisays.setSpacing(20);
        HBox hbox1 = new HBox();
        Label label1 = new Label("Sarjan nimi: ");
        HBox pakollinen_kentta1 = new HBox();

        pakollinen_kentta1.getChildren().addAll(label1, pakollinen);

        hbox1.setSpacing(10);
        hbox1.getChildren().addAll(pakollinen_kentta1, nimi);

        HBox hbox2 = new HBox();
        hbox2.setSpacing(10);
        hbox2.getChildren().addAll(lisaysnappula, peruuta);

        lisays.getChildren().addAll(hbox1, hbox2);

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);

        Label otsikko = new Label("Lisää uusi sarja");
        otsikko.setFont(Font.font("Papyrus", 28));

        grid.add(otsikko, 0, 0);
        grid.add(lisays, 0, 1);
        grid.setVgap(40);

        VBox peitto = new VBox();
        peitto.setStyle("-fx-background-color: white;");
        ikkuna.annaNaytto().getChildren().add(peitto);
        ikkuna.annaNaytto().getChildren().add(grid);
    }

    public void luoSarjaSivu(TreeItem<Kohde> arvo) {

        ScrollPane sb = new ScrollPane();

        Sarja sarja = (Sarja) arvo.getValue();

        GridPane grid = new GridPane();

        VBox rivi1 = new VBox();
        rivi1.setAlignment(Pos.CENTER);

        Button muokkausnappula = new Button();

        muokkausnappula.setText("\uD83D\uDD89");
        muokkausnappula.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

                luoSarjaMuokkaus(sarja);

            }
        });

        Button poistonappula = new Button("X");
        poistonappula.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                varmistaja.annaPoistoVarmistus(sarja);

            }
        });
        HBox painikkeet = new HBox();
        painikkeet.setPadding(new Insets(20, 20, 40, 80));
        painikkeet.setSpacing(20);

        painikkeet.getChildren().addAll(muokkausnappula, poistonappula);
        rivi1.getChildren().addAll(painikkeet);

        HBox rivi2 = new HBox();
        rivi2.setPadding(new Insets(20));
        rivi2.setAlignment(Pos.CENTER);
        Label nimi = new Label(sarja.toString());
        nimi.setFont(Font.font("Papyrus", 32));

        rivi2.getChildren().addAll(nimi, painikkeet);

        grid.add(rivi2, 0, 1);

        HBox rivi3 = new HBox();
        rivi3.setPadding(new Insets(20, 10, 0, 0));

        Taulukko taulukontekija1 = new Taulukko(nakyma, varmistaja);

        ottelut = taulukontekija1.luoOtteluTaulukko(sarja);
        ottelut.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        VBox osio1 = new VBox();
        osio1.setSpacing(20);
        osio1.setAlignment(Pos.CENTER);

        HBox otsikkorivi1 = new HBox();
        otsikkorivi1.setSpacing(20);
        Label otsikko1 = new Label("Otteluluettelo");
        otsikko1.setFont(Font.font("Papyrus", 18));
        Button ottelunappula = new Button();
         ottelunappula.setId("button-ohje");
        ottelunappula.setText("\u003F");
        ottelunappula.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                Opastus otteluopastus = new Opastus();
                otteluopastus.annaOhjeOtteluLuettelo();

            }
        });
        otsikkorivi1.getChildren().addAll(otsikko1, ottelunappula);

        VBox osio2 = new VBox();
        osio2.setPadding(new Insets(40, 0, 0, 0));
        osio2.setSpacing(20);
        osio2.setAlignment(Pos.CENTER);

        HBox otsikkorivi2 = new HBox();
        otsikkorivi2.setSpacing(20);
        Label otsikko2 = new Label("Joukkueet");
        otsikko2.setFont(Font.font("Papyrus", 18));
        Button joukkuenappula = new Button();
         joukkuenappula.setId("button-ohje");
        joukkuenappula.setText("\u003F");
        joukkuenappula.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                Opastus joukkueopastus = new Opastus();
                joukkueopastus.annaOhjeJoukkueLuettelo();

            }
        });
        otsikkorivi2.getChildren().addAll(otsikko2, joukkuenappula);

        Taulukko taulukontekija3 = new Taulukko(nakyma, varmistaja);
        joukkueet = taulukontekija3.luoJoukkueTaulukko(sarja);
        joukkueet.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        VBox osio3 = new VBox();
        osio3.setSpacing(20);
        osio3.setAlignment(Pos.CENTER);
        Label otsikko3 = new Label("Sarjataulukko");
        otsikko3.setFont(Font.font("Papyrus", 18));

        Taulukko taulukontekija4 = new Taulukko(nakyma, varmistaja);
        sarjataulukko = taulukontekija4.luoSarjaTaulukko(sarja);
        sarjataulukko.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        VBox osio4 = new VBox();
        osio4.setSpacing(20);
        osio4.setAlignment(Pos.CENTER);

        Label otsikko4 = new Label("Pistepörssi");
        otsikko4.setFont(Font.font("Papyrus", 18));
        Taulukko taulukontekija2 = new Taulukko(nakyma, varmistaja);
        pisteporssi = taulukontekija2.luoPisteporssiTaulukko(sarja);
        pisteporssi.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        VBox ylialle = new VBox();
        ylialle.setSpacing(10);
        HBox alle = new HBox();
        alle.setSpacing(10);

        Button otteluluettelomuokkaus = new Button("Muokkaa otteluluetteloa");

        otteluluettelomuokkaus.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                luoOtteluLuetteloMuokkaus(sarja);

            }
        });

        HBox painikeboksi = new HBox();
        painikeboksi.setSpacing(40);

        painikeboksi.getChildren().addAll(otteluluettelomuokkaus);

        ylialle.getChildren().addAll(painikeboksi);

        HBox alle2 = new HBox();
        alle2.setSpacing(30);
        Button joukkuemuokkaus = new Button("Muokkaa joukkueluetteloa");

        joukkuemuokkaus.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                luoJoukkueenLisaysSivu(sarja);

            }
        });

        alle2.getChildren().addAll(joukkuemuokkaus);

        osio1.getChildren().addAll(otsikkorivi1, ottelut, ylialle);
        osio2.getChildren().addAll(otsikkorivi2, joukkueet, alle2);
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
        grid.setPadding(new Insets(20));
        sb.setContent(grid);
        VBox peitto = new VBox();
        peitto.setStyle("-fx-background-color: white;");
        ikkuna.annaNaytto().getChildren().add(peitto);

        ikkuna.annaNaytto().getChildren().add(sb);

    }

    public void luoOtteluLuetteloMuokkaus(Sarja sarja) {

        ScrollPane sb = new ScrollPane();

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20, 10, 40, 10));

           VBox rivi1 = new VBox();

        Button paluunappula = new Button("<< Palaa sarjasivulle");
        paluunappula.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                
                TreeItem<Kohde> mihin = new TreeItem<>(sarja);
                luoSarjaSivu(mihin);

            }
        });

        HBox painikkeet = new HBox();
        painikkeet.setPadding(new Insets(20));
        painikkeet.setSpacing(20);
        painikkeet.setAlignment(Pos.TOP_LEFT);
        painikkeet.getChildren().addAll(paluunappula);
        rivi1.getChildren().addAll(painikkeet);
        grid.add(rivi1, 0, 0);

        
        VBox rivi2 = new VBox();
        rivi2.setPadding(new Insets(20));
        rivi2.setAlignment(Pos.CENTER);
        Label nimi = new Label("Muokkaa sarjan " + sarja.toString() + " otteluita:");
        nimi.setFont(Font.font("Papyrus", 32));

        rivi2.getChildren().addAll(nimi);

        grid.add(rivi2, 0, 1);

        HBox rivi3 = new HBox();
        rivi3.setPadding(new Insets(20, 10, 0, 20));

        Taulukko taulukontekija1 = new Taulukko(nakyma, varmistaja);

        ottelut = taulukontekija1.luoOtteluTaulukkoMuokattava(sarja);
        ottelut.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        VBox osio1 = new VBox();
        osio1.setSpacing(20);
        osio1.setAlignment(Pos.CENTER);

        HBox otsikkorivi1 = new HBox();
        otsikkorivi1.setSpacing(20);
        Label otsikko1 = new Label("Otteluluettelo");
        otsikko1.setFont(Font.font("Papyrus", 18));
        Button ottelunappula = new Button();
         ottelunappula.setId("button-ohje");
        ottelunappula.setText("\u003F");
        ottelunappula.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                Opastus otteluopastus = new Opastus();
                otteluopastus.annaOhjeOtteluLuetteloMuokkaus();

            }
        });
        otsikkorivi1.getChildren().addAll(otsikko1, ottelunappula);

        VBox ylialle = new VBox();
        ylialle.setSpacing(10);
        HBox alle = new HBox();
        alle.setSpacing(10);

        Button ottelulisaysnappula = new Button("Lisää yksittäisiä otteluita");

        ottelulisaysnappula.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                luoOttelunLisaysSivu(sarja);

            }
        });

        Button autonappula = new Button("Suorita automaattinen otteluluettelon laadinta");

        autonappula.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(sarja.annaOttelut().size() != 0){
                     varmistaja.annaAutoVarmistus(sarja);
                }
               
                else{
                      muuttaja.suoritaAutoOtteluLista(sarja);
                      luoOtteluLuetteloMuokkaus(sarja);
                }
                  

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

        painikeboksi.getChildren().addAll(ottelulisaysnappula, autonappula, tyhjennysnappula);

        ylialle.getChildren().addAll(painikeboksi);

        osio1.getChildren().addAll(otsikkorivi1, ottelut, ylialle);

        rivi3.getChildren().addAll(osio1);

        grid.add(rivi3, 0, 2);

        sb.setContent(grid);
        VBox peitto = new VBox();
        peitto.setStyle("-fx-background-color: white;");
        ikkuna.annaNaytto().getChildren().add(peitto);

        ikkuna.annaNaytto().getChildren().add(sb);

    }

    public void luoOttelunLisaysSivu(Sarja sarja) {

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

                luoOtteluLuetteloMuokkaus(sarja);

            }
        });

        HBox painikkeet = new HBox();
        painikkeet.setPadding(new Insets(20));
        painikkeet.setSpacing(20);
        painikkeet.setAlignment(Pos.TOP_LEFT);
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

        Taulukko taulukontekija1 = new Taulukko(nakyma, varmistaja);

        ottelut = taulukontekija1.luoOtteluTaulukko(sarja);
        ottelut.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        VBox osio1 = new VBox();
        osio1.setSpacing(20);
        osio1.setAlignment(Pos.CENTER);
        Label otsikko1 = new Label("Otteluluettelo");
        otsikko1.setFont(Font.font("Papyrus", 18));
        HBox otsikkorivi1 = new HBox();
        otsikkorivi1.setSpacing(20);
        Button ottelunappula = new Button();
         ottelunappula.setId("button-ohje");
        ottelunappula.setText("\u003F");
        ottelunappula.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                Opastus otteluopastus = new Opastus();
                otteluopastus.annaOhjeOtteluLuettelo();

            }
        });
        otsikkorivi1.getChildren().addAll(otsikko1, ottelunappula);

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

        for (int i = 0; i < 99; i++) {
            kierroslista.add(i);
        }

        ObservableList kierrokset = FXCollections.observableList(kierroslista);
        kierros.setItems(kierrokset);
        kierros.getSelectionModel().selectFirst();
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

        Button lisaysnappula = new Button("Lisää");
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
                if (((erotuomari.getValue() == avustava1.getValue()) && (erotuomari.getValue() != null)) || ((erotuomari.getValue() == avustava2.getValue()) && (erotuomari.getValue() != null)) || ((avustava2.getValue() == avustava1.getValue()) && (avustava1.getValue() != null))) {
                    tiedottaja.annaVaroitus("Sama henkilö ei voi olla kuin yhdessä tuomarin roolissa.");
                    ok = false;

                }

                if (ok) {
                    muuttaja.lisaaOttelu(koti.getValue(), vieras.getValue(), ajankohta.getValue(), kellotunnit.getValue(), kellominuutit.getValue(), paikka.getText(), erotuomari.getValue(), avustava1.getValue(), avustava2.getValue(), sarja);
                   tiedottaja.kirjoitaLoki("Otteluluetteloa muokattu.");
                    ikkuna.asetaMuutos(true);
                    luoOttelunLisaysSivu(sarja);
                }

            }
        });

        Button tyhjennys = new Button("Tyhjennä");
        tyhjennys.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

                luoOttelunLisaysSivu(sarja);
            }
        });

        painikeboksi.getChildren().addAll(lisaysnappula, tyhjennys);

        alle.getChildren().addAll(ohjekierros, ohjekoti, ohjevieras, ohjeaika, ohjekello, ohjepaikka, ohjetuomari1, ohjetuomari2, ohjetuomari3);
        ylialle.getChildren().addAll(ohje, alle, painikeboksi);

        osio1.getChildren().addAll(otsikkorivi1, ottelut, ylialle);

        rivi3.getChildren().addAll(osio1);

        grid.add(rivi3, 0, 2);

        sb.setContent(grid);
        VBox peitto = new VBox();
        peitto.setStyle("-fx-background-color: white;");
        ikkuna.annaNaytto().getChildren().add(peitto);

        ikkuna.annaNaytto().getChildren().add(sb);

    }

    public void luoJoukkueenLisaysSivu(Sarja sarja) {

        ScrollPane sb = new ScrollPane();

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20, 10, 40, 300));

        VBox rivi1 = new VBox();
        rivi1.setAlignment(Pos.CENTER);

 


        HBox rivi2 = new HBox();
        rivi2.setPadding(new Insets(20));
        rivi2.setAlignment(Pos.CENTER);
        Label nimi = new Label("Lisää joukkueita sarjaan " + sarja.toString() + ":");
        nimi.setFont(Font.font("Papyrus", 32));

        rivi2.getChildren().addAll(nimi);

        grid.add(rivi2, 0, 1);

        VBox osio2 = new VBox();
        osio2.setPadding(new Insets(40, 0, 0, 0));
        osio2.setSpacing(20);
        osio2.setAlignment(Pos.CENTER);

        HBox otsikkorivi2 = new HBox();
        otsikkorivi2.setSpacing(20);
        Label otsikko2 = new Label("Joukkueet");
        otsikko2.setFont(Font.font("Papyrus", 18));
        Button joukkuenappula = new Button();
        joukkuenappula.setId("button-ohje");
        joukkuenappula.setText("\u003F");
        joukkuenappula.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                Opastus joukkueopastus = new Opastus();
                joukkueopastus.annaOhjeJoukkueLuetteloMuokkaus();

            }
        });
        otsikkorivi2.getChildren().addAll(otsikko2, joukkuenappula);

        Taulukko taulukontekija3 = new Taulukko(nakyma, varmistaja);
        joukkueet = taulukontekija3.luoJoukkueTaulukkoMuokattava(sarja);
        joukkueet.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        VBox alle2 = new VBox();
        alle2.setSpacing(10);

        HBox painikeboksi2 = new HBox();

        painikeboksi2.setSpacing(10);

        TextField lisaaJoukkue = new TextField();
        lisaaJoukkue.setPromptText("Joukkueen nimi");

        Button lisaysnappula2 = new Button("Lisää");
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
tiedottaja.kirjoitaLoki("Joukkueluetteloa muokattu.");
                    luoJoukkueenLisaysSivu(sarja);
                }
            }
        });

        painikeboksi2.getChildren().addAll(lisaaJoukkue, lisaysnappula2);
        alle2.getChildren().addAll(painikeboksi2);



        osio2.getChildren().addAll(otsikkorivi2, joukkueet, alle2);

        HBox rivi6 = new HBox();
        rivi6.setPadding(new Insets(0));

        rivi6.getChildren().addAll(osio2);

        grid.add(rivi6, 0, 2);
        sb.setContent(grid);
        VBox peitto = new VBox();
        peitto.setStyle("-fx-background-color: white;");
        ikkuna.annaNaytto().getChildren().add(peitto);

        ikkuna.annaNaytto().getChildren().add(sb);

    }

    public void luoSarjaMuokkaus(Sarja arvo) {
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

        VBox peitto = new VBox();
        peitto.setStyle("-fx-background-color: white;");
        ikkuna.annaNaytto().getChildren().add(peitto);
        ikkuna.annaNaytto().getChildren().add(grid);
    }

}
