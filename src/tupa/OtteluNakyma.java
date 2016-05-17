package tupa;

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
    private SarjaNakyma sarjanakyma;

    OtteluNakyma() {

    }

    OtteluNakyma(Tupa ikkuna, PaaNakyma nakyma) {
        this.ikkuna = ikkuna;
        this.nakyma = nakyma;
        sarjanakyma = nakyma.annaSarjanakyma();
        muuttaja = new Muuttaja(ikkuna, nakyma);
        tiedottaja = new Tiedottaja(ikkuna);
        varmistaja = new Varmistaja(ikkuna, nakyma);
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
       

        Button paluunappula = new Button("<< Palaa sarjasivulle");
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
                if (ottelu.annaTulos().equals("-")) {
                    tiedottaja.annaVaroitus("Maalitilastoa voi muokata vasta, kun ottelun tulos on syötetty.");
                } else {
                    luoOttelunMaalisivu(ottelu);
                }

            }

        });
        Button tulosnappula = new Button();
        if (ottelu.annaTulos().equals("-")) {

            tulosnappula.setText("Lisää tulos");
        } else {
            tulosnappula.setText("Muokkaa tulosta");
        }

        tulosnappula.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                luoOttelunTulossivu(ottelu);

            }

        });

        HBox painikkeet = new HBox();
        painikkeet.setPadding(new Insets(30));
        painikkeet.setSpacing(20);
        painikkeet.setAlignment(Pos.TOP_LEFT);
        painikkeet.getChildren().addAll(paluunappula);
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
        rivi3.setPadding(new Insets(10));
        rivi3.setSpacing(20);

        HBox painikkeet2 = new HBox();
        painikkeet2.setPadding(new Insets(20, 0, 0, 0));
        painikkeet2.setSpacing(40);
        painikkeet2.getChildren().addAll(tulosnappula);

        Taulukko taulukontekija1 = new Taulukko(nakyma, varmistaja);

        TableView ottelutaulu = taulukontekija1.luoOttelunTaulukko(ottelu);
        ottelutaulu.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        rivi3.getChildren().addAll(ottelutaulu, painikkeet2);

        HBox oikearivi3 = new HBox();
        oikearivi3.setPadding(new Insets(20));
        oikearivi3.setSpacing(40);

        HBox rivi5 = new HBox();
        rivi5.setPadding(new Insets(60));
        rivi5.setSpacing(60);

        Taulukko taulukontekija2 = new Taulukko(nakyma, varmistaja);
        Taulukko taulukontekija3 = new Taulukko(nakyma, varmistaja);
        Taulukko taulukontekija4 = new Taulukko(nakyma, varmistaja);
        HBox kotikokoonpano = taulukontekija2.luoKokoonpanoTaulukko(ottelu, ottelu.annaKotijoukkue());
        HBox vieraskokoonpano = taulukontekija3.luoKokoonpanoTaulukko(ottelu, ottelu.annaVierasjoukkue());
        TableView maalit = taulukontekija4.luoOttelunMaaliTaulukko(ottelu);

        maalit.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        VBox kotiosio = new VBox();
        kotiosio.setPadding(new Insets(0, 0, 0, 40));

        HBox koko_otsake_koti = new HBox();
         koko_otsake_koti.setPadding(new Insets(0, 0, 0, 20));
        koko_otsake_koti.setSpacing(20);

        Button otsikkonappula = new Button();
        otsikkonappula.setId("button-ohje");
        otsikkonappula.setText("\u003F");
        otsikkonappula.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                Opastus otteluopastus = new Opastus();
                otteluopastus.annaOhjeKokoonpano();

            }
        });

        Label kotiotsikko = new Label(ottelu.annaKotijoukkue().toString() + ":n kokoonpano:");
        kotiotsikko.setFont(Font.font("Papyrus", 18));

        koko_otsake_koti.getChildren().addAll(kotiotsikko, otsikkonappula);
        kotiosio.getChildren().addAll(koko_otsake_koti, kotikokoonpano);

        //mahdollinen vain kotijoukkeen henkilölle
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

        VBox vierasosio = new VBox();
        vierasosio.setPadding(new Insets(0, 0, 0, 40));

        HBox koko_otsake_vieras = new HBox();
        koko_otsake_vieras.setSpacing(20);
  koko_otsake_vieras.setPadding(new Insets(0, 0, 0, 20));
        Button otsikkonappula2 = new Button();
        otsikkonappula2.setId("button-ohje");
        otsikkonappula2.setText("\u003F");
        otsikkonappula2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                Opastus otteluopastus = new Opastus();
                otteluopastus.annaOhjeKokoonpano();

            }
        });

        Label vierasotsikko = new Label(ottelu.annaVierasjoukkue().toString() + ":n kokoonpano:");
        vierasotsikko.setFont(Font.font("Papyrus", 18));
        koko_otsake_vieras.getChildren().addAll(vierasotsikko, otsikkonappula2);
        vierasosio.getChildren().addAll(koko_otsake_vieras, vieraskokoonpano);

        rivi5.getChildren().addAll(kotiosio, vierasosio);
        //mahdollinen vain vierasjoukkeen henkilölle

        HBox vierasalle = new HBox();
        vierasalle.setPadding(new Insets(20));
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

        VBox maaliosio = new VBox();

        Label maaliotsikko = new Label("Maalit: ");
        maaliotsikko.setFont(Font.font("Papyrus", 18));
        HBox painikkeet3 = new HBox();
        painikkeet3.setPadding(new Insets(40, 0, 0, 0));
        painikkeet3.getChildren().addAll(maalinappula);

        // kenelle oikeudet?!?
        maaliosio.getChildren().addAll(maaliotsikko, maalit, painikkeet3);

        HBox rivi4 = new HBox();
        rivi4.setPadding(new Insets(30));

        rivi4.getChildren().addAll(maaliosio);

        oikearivi3.getChildren().addAll(rivi3);

        grid.add(oikearivi3, 0, 2);
        grid.add(rivi4, 0, 3);
        grid.add(rivi5, 0, 4);

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
        grid.setPadding(new Insets(20, 10, 40, 100));

        VBox rivi1 = new VBox();

        Button paluu = new Button();

        paluu.setText("<< Palaa ottelusivulle");
        paluu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

                luoOttelusivu(ottelu);

            }
        });

        HBox painikkeet = new HBox();
     
        painikkeet.setSpacing(20);
        painikkeet.setAlignment(Pos.TOP_LEFT);
        painikkeet.getChildren().addAll(paluu);
        rivi1.getChildren().addAll(painikkeet);

        VBox rivi2 = new VBox();
        rivi2.setPadding(new Insets(30, 20, 60, 20));
        rivi2.setAlignment(Pos.CENTER);
        Label nimi = new Label(ottelu.toString());
        nimi.setFont(Font.font("Papyrus", 32));

        rivi2.getChildren().addAll(nimi);

        grid.add(painikkeet, 0, 0);
        grid.add(rivi2, 0, 1);

        VBox rivi3 = new VBox();
        rivi3.setSpacing(20);

        HBox koko_otsake = new HBox();
        koko_otsake.setSpacing(20);

        Button otsikkonappula = new Button();
        otsikkonappula.setId("button-ohje");
        otsikkonappula.setText("\u003F");
        otsikkonappula.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                Opastus otteluopastus = new Opastus();
                otteluopastus.annaOhjeKokoonpano();

            }
        });

        Label otsake = new Label("Lisää joukkueen " + joukkue.toString() + " kokoonpano otteluun:");
        otsake.setFont(Font.font("Papyrus", FontWeight.BOLD, 18));

        koko_otsake.getChildren().addAll(otsake, otsikkonappula);

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
        sarake1.setPadding(new Insets(20, 20, 20, 0));
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

                for (int i = 0; i < roolitaulukko.length; i++) {
                    System.out.println(roolitaulukko[i]);
                }
                muuttaja.lisaaKokoonpano(pelaajataulukko, roolitaulukko, joukkue, ottelu);
                ikkuna.asetaMuutos(true);
                luoOttelusivu(ottelu);

            }
        });
        painikeboksi.getChildren().addAll(lisaysnappula);
        kokoonpanoluettelo.getChildren().addAll(sarake1, sarake2, sarake3, sarake4);

        rivi3.getChildren().addAll(koko_otsake, kokoonpanoluettelo, painikeboksi);

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

        Button paluunappula = new Button("<< Palaa ottelusivulle");
        paluunappula.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                luoOttelusivu(ottelu);

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

        Label otsikko = new Label("Muokkaa ottelun " + ottelu.toString() + " maalitilastoa: ");
        otsikko.setFont(Font.font("Papyrus", 22));
        rivi2.getChildren().addAll(otsikko);

        VBox rivi3 = new VBox();
        rivi3.setPadding(new Insets(20));

        Taulukko taulukontekija1 = new Taulukko(nakyma, varmistaja);

        TableView ottelutaulu = taulukontekija1.luoOttelunTaulukko(ottelu);
        ottelutaulu.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        rivi3.getChildren().addAll(ottelutaulu);

        VBox rivi4 = new VBox();
        rivi4.setPadding(new Insets(40, 20, 20, 20));

        Taulukko taulukontekija2 = new Taulukko(nakyma, varmistaja);

        TableView maalitaulu = taulukontekija2.luoOttelunMaaliTaulukkoMuokattava(ottelu);
        maalitaulu.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        HBox rivi5 = new HBox();
        rivi5.setPadding(new Insets(20, 0, 0, 20));
        rivi5.setSpacing(60);

        VBox kotiboxi = new VBox();
        kotiboxi.setPadding(new Insets(20, 0, 0, 0));
        kotiboxi.setSpacing(20);

        HBox maalialle1 = new HBox();
        maalialle1.setSpacing(20);
        maalialle1.setPadding(new Insets(20, 0, 0, 0));

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

        HBox otsikkokoti_koko = new HBox();
        otsikkokoti_koko.setSpacing(20);

        Label otsikkokoti = new Label("Kotijoukkueen maalit:");
        otsikkokoti.setFont(Font.font("Papyrus", 14));

        Button otsikkonappula = new Button();
        otsikkonappula.setId("button-ohje");
        otsikkonappula.setText("\u003F");
        otsikkonappula.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                Opastus otteluopastus = new Opastus();
                otteluopastus.annaOhjeMaalitilasto();

            }
        });
        otsikkokoti_koko.getChildren().addAll(otsikkokoti, otsikkonappula);

        Label eimahd = new Label("Kotijoukkueen maalit on jo syötetty");

        Label ohjepelaaja3 = new Label("Maalintekijä");

        ComboBox<Pelaaja> maalintekija1 = new ComboBox();
        List<Pelaaja> kotipelaajalista1 = new ArrayList();
        Pelaaja ohjeistus3 = new Pelaaja("Valitse", " ");
        kotipelaajalista1.add(ohjeistus3);
        Pelaaja oma1 = new Pelaaja("Oma ", "maali");
        kotipelaajalista1.add(oma1);

        for (int i = 0; i < ottelu.annaKotijoukkue().annaPelaajat().size(); i++) {

            Kokoonpano koti = ottelu.annaKotiKokoonpano();
            boolean kokoonpanossa = false;

            for (int j = 0; j < koti.annaPelaajat().size(); j++) {
                if (koti.annaPelaajat().get(j).equals(ottelu.annaKotijoukkue().annaPelaajat().get(i))) {
                    kokoonpanossa = true;
                }
            }
            if (kokoonpanossa) {
                kotipelaajalista1.add(ottelu.annaKotijoukkue().annaPelaajat().get(i));
            }
        }

        ObservableList kotipelaajat1 = FXCollections.observableList(kotipelaajalista1);
        maalintekija1.setItems(kotipelaajat1);
        maalintekija1.getSelectionModel().selectFirst();
        alle8.getChildren().addAll(ohjepelaaja3, maalintekija1);

        VBox alle9 = new VBox();

        Label ohjepelaaja4 = new Label("Syöttäjä");

        ComboBox<Pelaaja> syottaja1 = new ComboBox();
        List<Pelaaja> kotipelaajalista2 = new ArrayList();
        Pelaaja ohjeistus4 = new Pelaaja("Valitse", " ");
        kotipelaajalista2.add(ohjeistus4);
        Pelaaja ohjeistus5 = new Pelaaja("Ei ", "syöttäjää");
        kotipelaajalista2.add(ohjeistus5);
        for (int i = 0; i < ottelu.annaKotijoukkue().annaPelaajat().size(); i++) {
            Kokoonpano koti = ottelu.annaKotiKokoonpano();
            boolean kokoonpanossa = false;

            for (int j = 0; j < koti.annaPelaajat().size(); j++) {
                if (koti.annaPelaajat().get(j).equals(ottelu.annaKotijoukkue().annaPelaajat().get(i))) {
                    kokoonpanossa = true;
                }
            }
            if (kokoonpanossa) {
                kotipelaajalista2.add(ottelu.annaKotijoukkue().annaPelaajat().get(i));
            }
        }

        ObservableList kotipelaajat2 = FXCollections.observableList(kotipelaajalista2);
        syottaja1.setItems(kotipelaajat2);
        syottaja1.getSelectionModel().selectFirst();
        alle9.getChildren().addAll(ohjepelaaja4, syottaja1);

        int syotetyt_koti = 0;

        for (int i = 0; i < ottelu.annaMaalit().size(); i++) {
            if (ottelu.annaMaalit().get(i).annaMaalinTekija().annaJoukkue().equals(ottelu.annaKotijoukkue())) {
                syotetyt_koti++;
            }
        }

        maalialle1.getChildren().addAll(alle7, alle8, alle9);

        HBox painikeboksi3 = new HBox();
        painikeboksi3.setPadding(new Insets(30, 0, 0, 0));

        Button lisaysnappula3 = new Button("Tallenna");
        lisaysnappula3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

                muuttaja.lisaaMaali(aika.getValue(), maalintekija1.getValue(), syottaja1.getValue(), ottelu);
                ikkuna.asetaMuutos(true);
                luoOttelusivu(ottelu);
            }
        });

        painikeboksi3.getChildren().addAll(lisaysnappula3);

        rivi4.getChildren().addAll(maalitaulu);
        if (syotetyt_koti < ottelu.annaKotimaalit()) {
            kotiboxi.getChildren().addAll(otsikkokoti_koko, maalialle1, painikeboksi3);
        } else {
            kotiboxi.getChildren().addAll(otsikkokoti, eimahd);
        }

        VBox vierasboxi = new VBox();
        vierasboxi.setPadding(new Insets(20, 0, 0, 0));
        vierasboxi.setSpacing(20);

        HBox maalialle2 = new HBox();
        maalialle2.setSpacing(20);
        maalialle2.setPadding(new Insets(20, 0, 0, 0));

        VBox alle82 = new VBox();

        HBox otsikkovieras_koko = new HBox();
        otsikkovieras_koko.setSpacing(20);

        Button otsikkonappula2 = new Button();
        otsikkonappula2.setId("button-ohje");
        otsikkonappula2.setText("\u003F");
        otsikkonappula2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                Opastus otteluopastus = new Opastus();
                otteluopastus.annaOhjeMaalitilasto();

            }
        });
        Label otsikkovieras = new Label("Vierasjoukkueen maalit:");
        Label eimahd2 = new Label("Vierasjoukkueen maalit on jo syötetty");
        otsikkovieras.setFont(Font.font("Papyrus", 14));

        otsikkovieras_koko.getChildren().addAll(otsikkovieras, otsikkonappula2);

        Label ohjepelaaja32 = new Label("Maalintekijä");

        ComboBox<Pelaaja> maalintekija2 = new ComboBox();
        List<Pelaaja> vieraspelaajalista1 = new ArrayList();
        Pelaaja ohjeistus32 = new Pelaaja("Valitse", " ");
        vieraspelaajalista1.add(ohjeistus32);
        Pelaaja oma2 = new Pelaaja("Oma ", "maali");
        vieraspelaajalista1.add(oma2);

        for (int i = 0; i < ottelu.annaVierasjoukkue().annaPelaajat().size(); i++) {

            Kokoonpano vieras = ottelu.annaVierasKokoonpano();
            boolean kokoonpanossa = false;

            for (int j = 0; j < vieras.annaPelaajat().size(); j++) {
                if (vieras.annaPelaajat().get(j).equals(ottelu.annaVierasjoukkue().annaPelaajat().get(i))) {
                    kokoonpanossa = true;
                }
            }
            if (kokoonpanossa) {
                vieraspelaajalista1.add(ottelu.annaVierasjoukkue().annaPelaajat().get(i));
            }
        }

        ObservableList vieraspelaajat1 = FXCollections.observableList(vieraspelaajalista1);
        maalintekija2.setItems(vieraspelaajat1);
        maalintekija2.getSelectionModel().selectFirst();
        alle82.getChildren().addAll(ohjepelaaja32, maalintekija2);

        VBox alle92 = new VBox();

        Label ohjepelaaja42 = new Label("Syöttäjä");

        ComboBox<Pelaaja> syottaja2 = new ComboBox();
        List<Pelaaja> vieraspelaajalista2 = new ArrayList();
        Pelaaja ohjeistus42 = new Pelaaja("Valitse", " ");
        vieraspelaajalista2.add(ohjeistus42);
        Pelaaja ohjeistus52 = new Pelaaja("Ei ", "syöttäjää");
        vieraspelaajalista2.add(ohjeistus52);
        for (int i = 0; i < ottelu.annaVierasjoukkue().annaPelaajat().size(); i++) {
            Kokoonpano vieras = ottelu.annaVierasKokoonpano();
            boolean kokoonpanossa = false;

            for (int j = 0; j < vieras.annaPelaajat().size(); j++) {
                if (vieras.annaPelaajat().get(j).equals(ottelu.annaVierasjoukkue().annaPelaajat().get(i))) {
                    kokoonpanossa = true;
                }
            }
            if (kokoonpanossa) {
                vieraspelaajalista2.add(ottelu.annaVierasjoukkue().annaPelaajat().get(i));
            }
        }

        ObservableList vieraspelaajat2 = FXCollections.observableList(vieraspelaajalista2);
        syottaja2.setItems(vieraspelaajat2);
        syottaja2.getSelectionModel().selectFirst();
        alle92.getChildren().addAll(ohjepelaaja42, syottaja2);

        VBox alle72 = new VBox();

        Label ohjeaika2 = new Label("Min");
        ComboBox<Integer> aika2 = new ComboBox();
        List<Integer> aikalista2 = new ArrayList();
        for (int i = 1; i <= 200; i++) {
            aikalista2.add(i);
        }

        aika2.setItems(FXCollections.observableArrayList(aikalista2));
        aika2.getSelectionModel().selectFirst();

        alle72.getChildren().addAll(ohjeaika2, aika2);

        int syotetyt_vieras = 0;

        for (int i = 0; i < ottelu.annaMaalit().size(); i++) {
            if (ottelu.annaMaalit().get(i).annaMaalinTekija().annaJoukkue().equals(ottelu.annaVierasjoukkue())) {
                syotetyt_vieras++;
            }
        }

        maalialle2.getChildren().addAll(alle72, alle82, alle92);
        HBox painikeboksi32 = new HBox();
        painikeboksi32.setPadding(new Insets(30, 0, 0, 0));

        Button lisaysnappula32 = new Button("Tallenna");
        lisaysnappula32.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

                muuttaja.lisaaMaali(aika.getValue(), maalintekija1.getValue(), syottaja1.getValue(), ottelu);
                ikkuna.asetaMuutos(true);
                luoOttelusivu(ottelu);
            }
        });

        painikeboksi32.getChildren().addAll(lisaysnappula32);
        if (syotetyt_vieras < ottelu.annaVierasmaalit()) {
            vierasboxi.getChildren().addAll(otsikkovieras_koko, maalialle2, painikeboksi32);
        } else {
            kotiboxi.getChildren().addAll(otsikkovieras, eimahd2);
        }

        rivi5.getChildren().addAll(kotiboxi, vierasboxi);

        grid.add(rivi1, 0, 0);
        grid.add(rivi2, 0, 1);
        grid.add(rivi3, 0, 2);
        grid.add(rivi4, 0, 3);
        grid.add(rivi5, 0, 4);

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

        Button paluunappula = new Button("<< Palaa ottelusivulle");
        paluunappula.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                luoOttelusivu(ottelu);

            }
        });

        HBox painikkeet = new HBox();
        painikkeet.setPadding(new Insets(20));
        painikkeet.setSpacing(20);
        painikkeet.setAlignment(Pos.TOP_LEFT);
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

        Taulukko taulukontekija1 = new Taulukko(nakyma, varmistaja);

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
        maalitkoti.setValue(ottelu.annaKotimaalit());

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
        maalitvieras.setValue(ottelu.annaVierasmaalit());

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
