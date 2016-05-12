package tupa;

import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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

/**
 *
 * @author Marianne
 */
public class JoukkueNakyma {
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
    
    JoukkueNakyma() {

    }

    JoukkueNakyma(Tupa ikkuna, PaaNakyma nakyma) {
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
                sarjanakyma.luoSarjaSivu(mihin);

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
    
}
