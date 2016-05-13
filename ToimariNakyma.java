package tupa;

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
public class ToimariNakyma {
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
    
    ToimariNakyma() {

    }

    ToimariNakyma(Tupa ikkuna, PaaNakyma nakyma) {
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
    
      public void luoToimariSivu(Toimihenkilo toimari) {
        ScrollPane sb = new ScrollPane();

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20, 10, 40, 300));

        //riville 1
        HBox painike = new HBox();
        painike.setSpacing(20);

        Button muokkausnappula = new Button();

        muokkausnappula.setText("\uD83D\uDD89");
        muokkausnappula.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

                VBox peitto = new VBox();
                peitto.setStyle("-fx-background-color: white;");
                ikkuna.annaNaytto().getChildren().add(peitto);

                luoToimariMuokkaus(toimari);

            }
        });

        Button poistonappula = new Button("X");
        poistonappula.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                varmistaja.annaToimarinPoistoVarmistus(toimari);

            }
        });

        Button paluunappula = new Button("<< Palaa");
        paluunappula.setPadding(new Insets(0, 150, 0, 0));
        paluunappula.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                Joukkue joukkue = toimari.annaJoukkue();
                joukkuenakyma.luoJoukkueSivu(joukkue);

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

}
