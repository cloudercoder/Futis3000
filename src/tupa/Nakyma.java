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
        
          private ComboBox<Integer> pelinumero  = new ComboBox<>();
private TextField sposti = new TextField();
private TextField puh = new TextField();
private TextField rooli = new TextField();

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
        muuttaja = new Muuttaja(ikkuna);
        tiedottaja = new Tiedottaja(ikkuna);
        varmistaja = new Varmistus(ikkuna);
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
        Label label1 = new Label("Turnauksen nimi:");
        hbox1.setSpacing(10);
        hbox1.getChildren().addAll(label1, nimi, muokkausnappula, peruuta);

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

                    TreeItem<Kohde> uusiPuuItem = new TreeItem<>(uusi);
                    luoSarjaSivu(uusiPuuItem);
                }

            }
        });

        HBox hbox1 = new HBox();
        Label label1 = new Label("Sarjan nimi:");
        hbox1.setSpacing(10);
        hbox1.getChildren().addAll(label1, nimi, lisaysnappula);

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

                varmistaja.annaPoistoVarmistus(arvo.getValue());

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

        Taulukko taulukontekija1 = new Taulukko();

        ottelut = taulukontekija1.luoOtteluTaulukko(sarja);
        ottelut.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        VBox osio1 = new VBox();
        osio1.setSpacing(20);
        osio1.setAlignment(Pos.CENTER);
        Label otsikko1 = new Label("Otteluluettelo");
        otsikko1.setFont(Font.font("Papyrus", 18));

        VBox osio2 = new VBox();
        osio2.setPadding(new Insets(60, 0, 0, 0));
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

        Taulukko taulukontekija4 = new Taulukko();
        sarjataulukko = taulukontekija4.luoMaalintekijaTaulukko();
        sarjataulukko.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        VBox osio4 = new VBox();
        osio4.setSpacing(20);
        osio4.setAlignment(Pos.CENTER);

        Label otsikko4 = new Label("Pistepörssi");
        otsikko4.setFont(Font.font("Papyrus", 18));
        Taulukko taulukontekija2 = new Taulukko();
        pisteporssi = taulukontekija2.luoMaalintekijaTaulukko();
        pisteporssi.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        VBox ylialle = new VBox();
        ylialle.setSpacing(10);
        HBox alle = new HBox();
        alle.setSpacing(10);

        Label ohje = new Label("Lisää ottelu:");
        ohje.setFont(Font.font("Papyrus", 14));

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

        koti.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Joukkue>() {
            @Override
            public void changed(ObservableValue ov, Joukkue vanha, Joukkue uusi) {

                if (vanha != null) {

                    for (int i = 0; i < joukkueet2.size(); i++) {
                        if (joukkueet2.get(i).equals(ov.getValue())) {
                            joukkueet2.remove(uusi);
                              joukkueet2.add(vanha);
                        }
                    }

                } else {

                    joukkueet2.remove(uusi);
                }
            }

        });

        vieras.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Joukkue>() {
            @Override
            public void changed(ObservableValue ov, Joukkue vanha, Joukkue uusi) {

                if (vanha != null) {

                    for (int i = 0; i < joukkueet1.size(); i++) {
                        if (joukkueet1.get(i).equals(ov.getValue())) {
                            joukkueet1.remove(uusi);
                             joukkueet1.add(vanha);
                        }
                    }

                } else {

                    joukkueet1.remove(uusi);
                }
            }

        });

        VBox ohjeaika = new VBox();

        Label ohjea = new Label("Ajankohta");
        DatePicker ajankohta = new DatePicker();
        String pattern = "dd.MM.yyyy";
        ajankohta.setValue(LocalDate.now());
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

        ohjeaika.getChildren().addAll(ohjea, ajankohta);

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

        erotuomari.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tuomari>() {
            @Override
            public void changed(ObservableValue ov, Tuomari vanha, Tuomari uusi) {

                if (vanha != null) {

                    for (int i = 0; i < avustavat2.size(); i++) {
                        if (avustavat2.get(i).equals(ov.getValue())) {
                            avustavat2.remove(uusi);
                               avustavat2.add(vanha);
                        }
                    }

                    for (int i = 0; i < avustavat1.size(); i++) {
                        if (avustavat1.get(i).equals(ov.getValue())) {
                            avustavat1.remove(uusi);
                               avustavat1.add(vanha);
                        }
                    }

                } else {
                    avustavat1.remove(uusi);
                    avustavat2.remove(uusi);
                }
            }

        });

        avustava1.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tuomari>() {
            @Override
            public void changed(ObservableValue ov, Tuomari vanha, Tuomari uusi) {

                if (vanha != null) {

                    for (int i = 0; i < avustavat2.size(); i++) {
                        if (avustavat2.get(i).equals(ov.getValue())) {
                            avustavat2.remove(uusi);
                            avustavat2.add(vanha);
                        }
                    }

                    for (int i = 0; i < erotuomarit.size(); i++) {
                        if (erotuomarit.get(i).equals(ov.getValue())) {
                            erotuomarit.remove(uusi);
                            erotuomarit.add(vanha);
                        }
                    }

                } else {
                    erotuomarit.remove(uusi);
                    avustavat2.remove(uusi);
                }
            }

        });

        avustava2.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tuomari>() {
            @Override
            public void changed(ObservableValue ov, Tuomari vanha, Tuomari uusi) {

                if (vanha != null) {

                    for (int i = 0; i < avustavat1.size(); i++) {
                        if (avustavat1.get(i).equals(ov.getValue())) {
                            avustavat1.remove(uusi);
                               avustavat1.add(vanha);
                        }
                    }

                    for (int i = 0; i < erotuomarit.size(); i++) {
                        if (erotuomarit.get(i).equals(ov.getValue())) {
                            erotuomarit.remove(uusi);
                               erotuomarit.add(vanha);
                        }
                    }

                } else {
                    erotuomarit.remove(uusi);
                    avustavat1.remove(uusi);
                }
            }

        });

        VBox painikeboksi = new VBox();
        painikeboksi.setPadding(new Insets(15, 0, 0, 0));
        painikeboksi.setSpacing(10);

        Button lisaysnappula = new Button("Lisää");
        lisaysnappula.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

                muuttaja.lisaaOttelu(koti.getValue(), vieras.getValue(), ajankohta.getValue().toString(), paikka.getText(), erotuomari.getValue(), avustava1.getValue(), avustava2.getValue(), sarja);
                TreeItem<Kohde> sarjak = new TreeItem<>((Kohde) sarja);
                luoSarjaSivu(sarjak);
            }
        });

        Button tyhjennys = new Button("Tyhjennä valinnat");
        tyhjennys.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

                TreeItem<Kohde> sarjak = new TreeItem<>((Kohde) sarja);
                luoSarjaSivu(sarjak);
            }
        });

        painikeboksi.getChildren().addAll(lisaysnappula, tyhjennys);

        alle.getChildren().addAll(ohjekoti, ohjevieras, ohjeaika, ohjepaikka, ohjetuomari1, ohjetuomari2, ohjetuomari3, painikeboksi);
        ylialle.getChildren().addAll(ohje, alle);
        VBox alle2 = new VBox();
        alle2.setSpacing(10);
        Label ohjej = new Label("Lisää joukkue:");
        ohjej.setFont(Font.font("Papyrus", 14));
     

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

                    TreeItem<Kohde> sarjak = new TreeItem<>((Kohde) sarja);
                    luoSarjaSivu(sarjak);
                }
            }
        });

        painikeboksi2.getChildren().addAll(lisaaJoukkue, lisaysnappula2);
        alle2.getChildren().addAll(ohjej, painikeboksi2);

        osio1.getChildren().addAll(otsikko1, ottelut, ylialle);
        osio2.getChildren().addAll(otsikko2, joukkueet, alle2);
        osio3.getChildren().addAll(otsikko3, sarjataulukko);
        osio4.getChildren().addAll(otsikko4, pisteporssi);
        rivi3.getChildren().addAll(osio1);

        HBox rivi4 = new HBox();
        rivi4.setPadding(new Insets(0));
        rivi4.setSpacing(40);

        rivi4.getChildren().addAll(osio2, osio3, osio4);

        grid.add(rivi3, 0, 2);
        grid.add(rivi4, 0, 3);
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

                    tiedottaja.kirjoitaLoki("Tuomarin nimeä muokattu.");
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
        Label label1 = new Label("Sarjan nimi:");
        hbox1.setSpacing(10);
        hbox1.getChildren().addAll(label1, nimi);

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
                if (etunimi.getText().trim().isEmpty() || sukunimi.getText().trim().isEmpty()) {

                    tiedottaja.annaVaroitus("Et voi antaa tyhjää kenttää.");
                } else {

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
        hbox1.setSpacing(20);
        hbox1.getChildren().addAll(label1, etunimi);

        HBox hbox2 = new HBox();
        Label label2 = new Label("Sukunimi: ");
        hbox2.setSpacing(10);
        hbox2.getChildren().addAll(label2, sukunimi);

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

        painike.setPadding(new Insets(60, 20, 20 ,20));
        painike.setAlignment(Pos.TOP_RIGHT);
        painike.getChildren().addAll(muokkausnappula, poistonappula);
        grid.add(painike, 0, 0);

        //riville 2
        VBox info = new VBox();
        info.setPadding(new Insets(10, 10, 40, 10));
        Label nimi = new Label(tuomari.toString());
        nimi.setFont(Font.font("Papyrus", 28));
        Label id = new Label("tuomariID: " + tuomari.annaJulkinenId());
        id.setFont(Font.font("Papyrus", 18));
        info.setAlignment(Pos.CENTER);
        info.getChildren().addAll(nimi, id);

        grid.add(info, 0, 1);

        Taulukko taulukontekija1 = new Taulukko();

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

    public GridPane luoTuomariMuokkaus(Tuomari arvo) {
        Button muokkausnappula = new Button("Tallenna");
        muokkausnappula.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (etunimi.getText().trim().isEmpty() || sukunimi.getText().trim().isEmpty()) {

                    tiedottaja.annaVaroitus("Et voi antaa tyhjää kenttää.");
                } else {

                    //poisto puusta
                    TreeItem<Kohde> parent = new TreeItem<>();
                    parent = ikkuna.annaRootTuomarit();

                    for (int i = 0; i < parent.getChildren().size(); i++) {

                        if (parent.getChildren().get(i).getValue().equals(arvo)) {
                            parent.getChildren().remove(i);
                        }

                    }

                    arvo.asetaEtuNimi(etunimi.getText());
                    arvo.asetaSukuNimi(sukunimi.getText());
                    arvo.asetaNimi(etunimi.getText() + " " + sukunimi.getText());

                    //muutettu takaisin puuhun
                    TreeItem<Kohde> muutettu = new TreeItem<Kohde>(arvo);
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

                TreeItem<Kohde> kuka = new TreeItem<>(arvo);
                luoTuomariSivu(kuka);

            }
        });

        etunimi.setText(arvo.annaEtuNimi());
        sukunimi.setText(arvo.annaSukuNimi());

        VBox vbox = new VBox();
        vbox.setSpacing(20);

        Label otsikko = new Label("Muokkaa tuomarin " + arvo.toString() + " tietoja: ");
        otsikko.setFont(Font.font("Papyrus", 22));

        HBox hbox1 = new HBox();
        Label label1 = new Label("Etunimi:");
        hbox1.setSpacing(20);
        hbox1.getChildren().addAll(label1, etunimi);
        HBox hbox2 = new HBox();
        Label label2 = new Label("Sukunimi:");
        hbox2.setSpacing(10);
        hbox2.getChildren().addAll(label2, sukunimi);

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

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20, 10, 40, 10));

        HBox rivi1 = new HBox();
        rivi1.setPadding(new Insets(20, 10, 20, 80));
        rivi1.setAlignment(Pos. TOP_RIGHT);
        rivi1.setSpacing(20);
        
        Button muokkausnappula = new Button();

        muokkausnappula.setText("Muokkaa");
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
        
        rivi1.getChildren().addAll(muokkausnappula, poistonappula);
        grid.add(rivi1, 0, 0);

        //rivi2
        VBox info = new VBox();
        info.setPadding(new Insets(10,10,40,10));

        Label nimi = new Label(joukkue.toString());
        nimi.setFont(Font.font("Papyrus", 32));
        
        info.setAlignment(Pos.CENTER);
        info.getChildren().addAll(nimi);
        grid.add(info, 0, 1);
        

        HBox rivi3 = new HBox();
        rivi3.setSpacing(40);
 rivi3.setPadding(new Insets(10,10,60,10));
        Taulukko taulukontekija1 = new Taulukko();
        ottelut = taulukontekija1.luoJoukkueenOtteluTaulukko(joukkue);
        ottelut.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        VBox osio1 = new VBox();
        osio1.setSpacing(20);
          osio1.setPadding(new Insets(0,0, 10, 0));
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
      
        osio3.setAlignment(Pos.CENTER);
        Label otsikko3 = new Label("Toimihenkilöt");
        otsikko3.setFont(Font.font("Papyrus", 18));

        Taulukko taulukontekija4 = new Taulukko();
        toimihenkilot = taulukontekija4.luoToimihenkiloTaulukko(joukkue);
        toimihenkilot.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        VBox osio4 = new VBox();
        
        osio4.setSpacing(20);
        osio4.setAlignment(Pos.CENTER);

        Label otsikko4 = new Label("Joukkueen sisäinen pistepörssi");
        otsikko4.setFont(Font.font("Papyrus", 18));
        Taulukko taulukontekija2 = new Taulukko();
        pisteporssi = taulukontekija2.luoJoukkueenMaalintekijaTaulukko(joukkue);
        pisteporssi.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        osio4.getChildren().addAll(otsikko4, pisteporssi);

        VBox alle = new VBox();
        alle.setPadding(new Insets(20, 0, 60, 0));
        Label ohje = new Label("Lisää uusi pelaaja:");
        ohje.setFont(Font.font("Papyrus", 14));

        HBox lisays = new HBox();
        lisays.setSpacing(10);
        lisays.setPadding(new Insets(10, 0, 0, 0));

        VBox vbox1 = new VBox();

        Label ohjeE = new Label("Etunimi");
        TextField etunimi = new TextField();
        etunimi.setPromptText("Etunimi");
        vbox1.getChildren().addAll(ohjeE, etunimi);

        VBox vbox2 = new VBox();

        Label ohjeS = new Label("Sukunimi");
        TextField sukunimi = new TextField();
        sukunimi.setPromptText("Sukunimi");
        vbox2.getChildren().addAll(ohjeS, sukunimi);

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
        vbox0.getChildren().addAll(ohjePn, pelinumero);

        VBox vbox4 = new VBox();

        Label ohjeTy = new Label("");
        Button lisaysnappula = new Button("Lisää");
        lisaysnappula.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                boolean ok = true;
                //tarkistus, onko tyhjä kenttä PITÄÄ TARKISTAA MYÖS MUITA!!!
                if (etunimi.getText().trim().isEmpty() || sukunimi.getText().trim().isEmpty()) {

                    tiedottaja.annaVaroitus("Et voi antaa tyhjää kenttää.");
                    ok = false;
                }
                //TARKISTUKSET!!!

                if (ok) {
                    muuttaja.lisaaPelaaja(etunimi.getText(), sukunimi.getText(), pelipaikka.getText(), pelinumero.getValue(), joukkue);

                    luoJoukkueSivu(joukkue);
                }
            }
        });
        vbox0.getChildren().addAll(ohjeTy, lisaysnappula);

        lisays.getChildren().addAll(vbox0, vbox1, vbox2, vbox3, vbox4);
        alle.getChildren().addAll(ohje, lisays);
         osio2.getChildren().addAll(otsikko2, pelaajat, alle);

        VBox alle2 = new VBox();
        alle2.setPadding(new Insets(20, 0, 60, 0));
        Label ohje2 = new Label("Lisää uusi toimihenkilö:");
        ohje2.setFont(Font.font("Papyrus", 14));

        HBox lisays2 = new HBox();
        lisays2.setSpacing(10);
        lisays2.setPadding(new Insets(10, 0, 0, 0));

        VBox vbox11 = new VBox();

        Label ohjeE2 = new Label("Etunimi");
        TextField etunimi2 = new TextField();
        etunimi2.setPromptText("Etunimi");
        vbox11.getChildren().addAll(ohjeE2, etunimi2);

        VBox vbox12 = new VBox();

        Label ohjeS2 = new Label("Sukunimi");
        TextField sukunimi2 = new TextField();
        sukunimi2.setPromptText("Sukunimi");
        vbox12.getChildren().addAll(ohjeS2, sukunimi2);

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
                //tarkistus, onko tyhjä kenttä PITÄÄ TARKISTAA MYÖS MUITA!!!
                if (etunimi2.getText().trim().isEmpty() || sukunimi2.getText().trim().isEmpty()) {

                    tiedottaja.annaVaroitus("Et voi antaa tyhjää kenttää.");
                    ok = false;
                }
                //TARKISTUKSET!!!

                if (ok) {
                    muuttaja.lisaaToimari(etunimi2.getText(), sukunimi2.getText(), rooli.getText(), sposti.getText(), puh.getText(), joukkue);

                    luoJoukkueSivu(joukkue);
                }
            }
        });
        vbox16.getChildren().addAll(ohjeTyh, lisaysnappula2);

        lisays2.getChildren().addAll(vbox11, vbox12, vbox13, vbox14, vbox15, vbox16);
        alle2.getChildren().addAll(ohje2, lisays2);
       osio3.getChildren().addAll(otsikko3, toimihenkilot, alle2);

      rivi3.getChildren().addAll(osio1, osio4);
      grid.add(rivi3, 0, 2);
      
      HBox rivi4 = new HBox();
      rivi4.setSpacing(40);
      rivi4.getChildren().addAll(osio2, osio3);
      grid.add(rivi4, 0, 3);
      
      
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
        Label label1 = new Label("Nimi:");
        hbox1.setSpacing(20);
        hbox1.getChildren().addAll(label1, nimi);

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

        painike.setPadding(new Insets(20, 20, 20 ,200));
        painike.setAlignment(Pos.TOP_RIGHT);
        painike.getChildren().addAll(muokkausnappula, poistonappula);
        grid.add(painike, 0, 0);

        //riville 2
        VBox info = new VBox();
        info.setPadding(new Insets(10, 10, 20, 10)); 
        Label nimi = new Label(pelaaja.toString());
        nimi.setFont(Font.font("Papyrus", 32));
           info.setAlignment(Pos.CENTER);
        info.getChildren().addAll(nimi);
        
        HBox tiedot = new HBox();
         tiedot.setPadding(new Insets(10, 10, 40, 10)); 
        tiedot.setSpacing(40);
         
        Label id = new Label("PelaajaID: " + pelaaja.annaJulkinenID());
        id.setFont(Font.font("Papyrus", 14));
        Label pelipaikka = new Label("Pelipaikka: " + pelaaja.annaPelipaikka());
        pelipaikka.setFont(Font.font("Papyrus", 14));
        Label pelinumero = new Label("Pelinumero: " + pelaaja.annaPelinumero());
        pelinumero.setFont(Font.font("Papyrus", 14));
         Label joukkue = new Label("Joukkue: " + pelaaja.annaJoukkue());
        joukkue.setFont(Font.font("Papyrus", 18));
     
   info.setAlignment(Pos.CENTER);
     tiedot.setAlignment(Pos.CENTER);
        tiedot.getChildren().addAll(id, joukkue, pelipaikka, pelinumero);
        grid.add(info, 0, 1);
        grid.add(tiedot, 0, 2);

        Taulukko taulukontekija1 = new Taulukko();

        pisteet = taulukontekija1.luoPelaajanPisteTaulukko(pelaaja);
        pisteet.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        //riville 3
        VBox osio1 = new VBox();
        osio1.setSpacing(20);
        osio1.setAlignment(Pos.CENTER);
        Label otsikko1 = new Label("Pelaajan pisteet");
        otsikko1.setFont(Font.font("Papyrus", 18));
        osio1.getChildren().addAll(otsikko1, pisteet);

        grid.add(osio1, 0, 3);
        sb.setContent(grid);
        VBox peitto = new VBox();
        peitto.setStyle("-fx-background-color: white;");
        ikkuna.annaNaytto().getChildren().add(peitto);
        ikkuna.annaNaytto().getChildren().add(sb);

    }

    public void luoPelaajaMuokkaus(Pelaaja pelaaja){
        Button muokkausnappula = new Button("Tallenna");
        muokkausnappula.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (etunimi.getText().trim().isEmpty() || sukunimi.getText().trim().isEmpty()) {

                    tiedottaja.annaVaroitus("Et voi antaa tyhjää kenttää.");
                } else {

                   

                    pelaaja.asetaEtuNimi(etunimi.getText());
                    pelaaja.asetaSukuNimi(sukunimi.getText());
                    pelaaja.asetaNimi(etunimi.getText() + " " + sukunimi.getText());

                 

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

        etunimi.setText(pelaaja.annaEtuNimi());
        sukunimi.setText(pelaaja.annaSukuNimi());

        VBox vbox = new VBox();
        vbox.setSpacing(20);

        Label otsikko = new Label("Muokkaa pelaajan " + pelaaja.toString() + " tietoja: ");
        otsikko.setFont(Font.font("Papyrus", 22));

        HBox hbox1 = new HBox();
        Label label1 = new Label("Etunimi:");
        hbox1.setSpacing(20);
        hbox1.getChildren().addAll(label1, etunimi);
        HBox hbox2 = new HBox();
        Label label2 = new Label("Sukunimi:");
        hbox2.setSpacing(10);
        hbox2.getChildren().addAll(label2, sukunimi);

        HBox painikkeet = new HBox();
        painikkeet.setSpacing(20);
        painikkeet.getChildren().addAll(muokkausnappula, peruuta);
        vbox.getChildren().addAll(otsikko, hbox1, hbox2, painikkeet);

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

        painike.setPadding(new Insets(20, 20, 20 ,150));
        painike.setAlignment(Pos.TOP_RIGHT);
        painike.getChildren().addAll(muokkausnappula, poistonappula);
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

   public void luoToimariMuokkaus(Toimihenkilo toimari){
          Button muokkausnappula = new Button("Tallenna");
        muokkausnappula.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (etunimi.getText().trim().isEmpty() || sukunimi.getText().trim().isEmpty()) {

                    tiedottaja.annaVaroitus("Et voi antaa tyhjää kenttää.");
                } else {

                   

                   toimari.asetaEtuNimi(etunimi.getText());
                    toimari.asetaSukuNimi(sukunimi.getText());
                    toimari.asetaNimi(etunimi.getText() + " " + sukunimi.getText());

                 

                    tiedottaja.kirjoitaLoki("Pelaajan tietoja muokattu.");
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

        VBox vbox = new VBox();
        vbox.setSpacing(20);

        Label otsikko = new Label("Muokkaa toimihenkilön " + toimari.toString() + " tietoja: ");
        otsikko.setFont(Font.font("Papyrus", 22));

        HBox hbox1 = new HBox();
        Label label1 = new Label("Etunimi:");
        hbox1.setSpacing(20);
        hbox1.getChildren().addAll(label1, etunimi);
        HBox hbox2 = new HBox();
        Label label2 = new Label("Sukunimi:");
        hbox2.setSpacing(10);
        hbox2.getChildren().addAll(label2, sukunimi);

        HBox painikkeet = new HBox();
        painikkeet.setSpacing(20);
        painikkeet.getChildren().addAll(muokkausnappula, peruuta);
        vbox.getChildren().addAll(otsikko, hbox1, hbox2, painikkeet);

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

    }
   public void luoOtteluMuokkaus(Ottelu ottelu) {

    }
}
