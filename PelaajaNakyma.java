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
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

/**
 *
 * @author Marianne
 */
public class PelaajaNakyma {
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
    private Varmistaja varmistaja;

    //taulukot
    private TableView ottelut;
    private TableView pisteporssi;
    private TableView joukkueet;
    private TableView sarjataulukko;
    private TableView pelaajat;
    private TableView toimihenkilot;
    private TableView pisteet;

    private PaaNakyma nakyma;
    private JoukkueNakyma joukkuenakyma;
    
   PelaajaNakyma() {

    }

    PelaajaNakyma(Tupa ikkuna, PaaNakyma nakyma) {
        this.ikkuna = ikkuna;
        this.nakyma = nakyma;
        joukkuenakyma = nakyma.annaJoukkuenakyma();
        muuttaja = new Muuttaja(ikkuna, nakyma);
        tiedottaja = new Tiedottaja(ikkuna);
        varmistaja = new Varmistaja(ikkuna, nakyma);
        pakollinen.setId("label-pakko");
        pakollinen2.setId("label-pakko");
        pakollinen3.setId("label-pakko");
        pakollinen4.setId("label-pakko");

    }
    
     public void luoPelaajaSivu(Pelaaja pelaaja) {
        ScrollPane sb = new ScrollPane();

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20, 10, 40, 550));

        //riville 1
        HBox painike = new HBox();
        painike.setSpacing(20);
painike.setSpacing(100);
        Button muokkausnappula = new Button();

        muokkausnappula.setText("\uD83D\uDD89");
        muokkausnappula.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

                VBox peitto = new VBox();
                peitto.setStyle("-fx-background-color: white;");
                ikkuna.annaNaytto().getChildren().add(peitto);

                luoPelaajaMuokkaus(pelaaja);

            }
        });

        Button poistonappula = new Button("X");
        poistonappula.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                varmistaja.annaPelaajanPoistoVarmistus(pelaaja);

            }
        });

        Button paluunappula = new Button("<< Palaa takaisin");
        paluunappula.setPadding(new Insets(0, 400, 0, 0));
        paluunappula.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                Joukkue joukkue = pelaaja.annaJoukkue();
                joukkuenakyma.luoJoukkueSivu(joukkue);

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

        Taulukko taulukontekija1 = new Taulukko(nakyma, varmistaja);

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
}


