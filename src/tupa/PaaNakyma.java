/*
Luokka, joka muodostaa päänäkymät
 */
package tupa;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
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


/**
 *
 * @author Marianne
 */
public class PaaNakyma {

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

    //muut näkymät
    private SarjaNakyma sarjanakyma;
    private OtteluNakyma ottelunakyma;
    private JoukkueNakyma joukkuenakyma;
    private PelaajaNakyma pelaajanakyma;
    private TuomariNakyma tuomarinakyma;
    private ToimariNakyma toimarinakyma;

    PaaNakyma() {

    }

    PaaNakyma(Tupa ikkuna) {
        this.ikkuna = ikkuna;
        muuttaja = new Muuttaja(ikkuna, this);
        tiedottaja = new Tiedottaja(ikkuna);
        varmistaja = new Varmistus(ikkuna, this);
        pakollinen.setId("label-pakko");
        pakollinen2.setId("label-pakko");
        pakollinen3.setId("label-pakko");
        pakollinen4.setId("label-pakko");
        sarjanakyma = new SarjaNakyma(ikkuna, this);
        ottelunakyma = new OtteluNakyma(ikkuna, this);
        joukkuenakyma = new JoukkueNakyma(ikkuna, this);
        pelaajanakyma = new PelaajaNakyma(ikkuna, this);
        tuomarinakyma = new TuomariNakyma(ikkuna, this);
        toimarinakyma = new ToimariNakyma(ikkuna, this);

    }

    public SarjaNakyma annaSarjanakyma(){
        return sarjanakyma;
    }
    
   public JoukkueNakyma annaJoukkuenakyma(){
        return joukkuenakyma;
    }
   
      public TuomariNakyma annaTuomarinakyma(){
        return tuomarinakyma;
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
                    ikkuna.annaNaytto().getChildren().add(sarjanakyma.luoPaanayttoSarjat());
                } else if (arvo.getValue() instanceof Tuomari) {
                    ikkuna.annaNaytto().getChildren().add(tuomarinakyma.luoPaanayttoTuomarit());
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

    
    

}   

    

   

  
    