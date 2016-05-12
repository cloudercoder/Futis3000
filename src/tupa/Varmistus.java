package tupa;

import java.util.ArrayList;
import java.util.List;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author Marianne
 */
public class Varmistus {

    private List<Kohde> kohdetk = new ArrayList<>();
    private Tupa ikkuna;
    private Muuttaja muuttaja;
    private PaaNakyma nakyma;
    private SarjaNakyma sarjanakyma;
    private JoukkueNakyma joukkuenakyma;

    Varmistus() {

    }

    Varmistus(List<Kohde> kohteet, Tupa ikkuna) {
        kohdetk = kohteet;
        this.ikkuna = ikkuna;
        sarjanakyma = ikkuna.annaPaaNakyma().annaSarjanakyma();
        joukkuenakyma = ikkuna.annaPaaNakyma().annaJoukkuenakyma();
    }

    Varmistus(Tupa ikkuna, PaaNakyma nakyma) {
        muuttaja = new Muuttaja(ikkuna, nakyma);
        this.ikkuna = ikkuna;
        this.nakyma = nakyma;
        sarjanakyma = nakyma.annaSarjanakyma();
        joukkuenakyma = nakyma.annaJoukkuenakyma();

    }

    public void annaVarmistus() {
        Stage stageV = new Stage();
        BorderPane alue = new BorderPane();

        VBox vbox = new VBox();
        vbox.setPadding(new Insets(10));
        vbox.setSpacing(10);

        HBox hbox1 = new HBox();
        Label viesti = new Label("Haluatko tallentaa ennen ohjelman lopettamista?");

        hbox1.setAlignment(Pos.CENTER);
        hbox1.getChildren().add(viesti);

        HBox hbox2 = new HBox();
        hbox2.setPadding(new Insets(10));
        hbox2.setSpacing(10);
        Button jooTallennus = new Button("Tallenna");
        Button eiTallennus = new Button("Älä tallenna");
        Button peruuta = new Button("Peruuta");
        eiTallennus.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Platform.exit();
            }
        });

        jooTallennus.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                Tallennus tallenna = new Tallennus(ikkuna);

                tallenna.suoritaTallennus();
                Platform.exit();
            }
        });
        peruuta.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                stageV.close();
            }
        });
        hbox2.setAlignment(Pos.CENTER);
        hbox2.getChildren().addAll(jooTallennus, eiTallennus, peruuta);
        vbox.getChildren().addAll(hbox1, hbox2);
        alue.setCenter(vbox);

        Scene sceneV = new Scene(alue, 400, 100);
        stageV.setTitle("TUPA - TULOSPALVELU");
        stageV.setScene(sceneV);
        stageV.show();

    }

    public void annaPoistoVarmistus(Kohde arvo) {
        Stage stageV = new Stage();
        BorderPane alue = new BorderPane();

        VBox vbox = new VBox();
        vbox.setPadding(new Insets(10));
        vbox.setSpacing(10);
        String nimi = arvo.toString();
        HBox hbox1 = new HBox();
        Label viesti = new Label("Haluatko todella poistaa kohteen " + nimi + " ?");

        hbox1.setAlignment(Pos.CENTER);
        hbox1.getChildren().add(viesti);

        HBox hbox2 = new HBox();
        hbox2.setPadding(new Insets(10));
        hbox2.setSpacing(10);
        Button joo = new Button("Kyllä");

        Button peruuta = new Button("Peruuta");

        joo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                muuttaja.poistaKohde(arvo);
                stageV.close();

            }
        });
        peruuta.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                stageV.close();
            }
        });
        hbox2.setAlignment(Pos.CENTER);
        hbox2.getChildren().addAll(joo, peruuta);
        vbox.getChildren().addAll(hbox1, hbox2);
        alue.setCenter(vbox);

        Scene sceneV = new Scene(alue, 400, 100);
        stageV.setTitle("TUPA - TULOSPALVELU");
        stageV.setScene(sceneV);
        stageV.show();

    }

    public void annaUudenVarmistus() {
        Stage stageV = new Stage();
        BorderPane alue = new BorderPane();

        VBox vbox = new VBox();
        vbox.setPadding(new Insets(10));
        vbox.setSpacing(10);

        HBox hbox1 = new HBox();
        Label viesti = new Label("Haluatko tallentaa ennen uuden avaamista?");

        hbox1.setAlignment(Pos.CENTER);
        hbox1.getChildren().add(viesti);

        HBox hbox2 = new HBox();
        hbox2.setPadding(new Insets(10));
        hbox2.setSpacing(10);
        Button jooTallennus = new Button("Tallenna");
        Button eiTallennus = new Button("Älä tallenna");
        Button peruuta = new Button("Peruuta");
        eiTallennus.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //tyhjennetään kaikki tiedot 
                ikkuna.annaKohteet().clear();

                Kohde uusiTurnaus = new Turnaus();
                ikkuna.asetaTurnaus(uusiTurnaus);

                //vielä pitää tyhjentää puu
                TreeItem<Kohde> parentSarjat = ikkuna.annaRootSarjat();
                TreeItem<Kohde> parentTuomarit = ikkuna.annaRootTuomarit();
                parentSarjat.getChildren().clear();
                parentTuomarit.getChildren().clear();

                stageV.close();
            }
        });

        jooTallennus.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                Tallennus tallenna = new Tallennus(ikkuna);

                tallenna.suoritaTallennus();
                //tyhjennetään kaikki tiedot 
                ikkuna.annaKohteet().clear();

                Kohde uusiTurnaus = new Turnaus();
                ikkuna.asetaTurnaus(uusiTurnaus);

                //vielä pitää tyhjentää puu
                TreeItem<Kohde> parentSarjat = ikkuna.annaRootSarjat();
                TreeItem<Kohde> parentTuomarit = ikkuna.annaRootTuomarit();
                parentSarjat.getChildren().clear();
                parentTuomarit.getChildren().clear();

                stageV.close();
            }
        });
        peruuta.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                stageV.close();
            }
        });
        hbox2.setAlignment(Pos.CENTER);
        hbox2.getChildren().addAll(jooTallennus, eiTallennus, peruuta);
        vbox.getChildren().addAll(hbox1, hbox2);
        alue.setCenter(vbox);

        Scene sceneV = new Scene(alue, 400, 100);
        stageV.setTitle("TUPA - TULOSPALVELU");
        stageV.setScene(sceneV);
        stageV.show();

    }

    public void annaAvausVarmistus() {
        Stage stageV = new Stage();
        BorderPane alue = new BorderPane();

        VBox vbox = new VBox();
        vbox.setPadding(new Insets(10));
        vbox.setSpacing(10);

        HBox hbox1 = new HBox();
        Label viesti = new Label("Haluatko tallentaa ennen uuden avaamista?");

        hbox1.setAlignment(Pos.CENTER);
        hbox1.getChildren().add(viesti);

        HBox hbox2 = new HBox();
        hbox2.setPadding(new Insets(10));
        hbox2.setSpacing(10);
        Button jooTallennus = new Button("Tallenna");
        Button eiTallennus = new Button("Älä tallenna");
        Button peruuta = new Button("Peruuta");
        eiTallennus.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //tyhjennetään kaikki tiedot 
                ikkuna.annaKohteet().clear();

                Kohde uusiTurnaus = new Turnaus();
                ikkuna.asetaTurnaus(uusiTurnaus);

                //vielä pitää tyhjentää puu
                TreeItem<Kohde> parentSarjat = ikkuna.annaRootSarjat();
                TreeItem<Kohde> parentTuomarit = ikkuna.annaRootTuomarit();
                parentSarjat.getChildren().clear();
                parentTuomarit.getChildren().clear();

                //sitten vasta avaukseen
                //tuodaan tallennetut kohteet
                List<Kohde> kohdetk = new ArrayList<>();
                Avaus avaaja = new Avaus();
                kohdetk = avaaja.avaa();
                //talletetaan muistiin, mitä oli ennen "istunnon" aloittamista -> tarviiko kysyä tallennusta, jos käyttäjä sulkee näytön

                ikkuna.asetaKohteet(kohdetk);

                List<Sarja> sarjatk = new ArrayList<>();

                List<Tuomari> tuomaritk = new ArrayList<>();

                //viedään kohteet omiin listoihin
                TreeItem<Kohde> parent = new TreeItem<>();
                for (int i = 0; i < kohdetk.size(); i++) {

                    if (kohdetk.get(i) instanceof Sarja) {

                        sarjatk.add((Sarja) kohdetk.get(i));
                        ikkuna.annaSarjatk().add((Sarja) kohdetk.get(i));
                        parent = ikkuna.annaRootSarjat();
                        TreeItem<Kohde> newItem = new TreeItem<Kohde>(kohdetk.get(i));
                        parent.getChildren().add(newItem);

                    } else if (kohdetk.get(i) instanceof Tuomari) {
                        tuomaritk.add((Tuomari) kohdetk.get(i));
                        ikkuna.annaTuomaritk().add((Tuomari) kohdetk.get(i));
                        parent = ikkuna.annaRootTuomarit();
                        TreeItem<Kohde> newItem = new TreeItem<Kohde>(kohdetk.get(i));
                        parent.getChildren().add(newItem);
                    } else if (kohdetk.get(i) instanceof Turnaus) {

                        ikkuna.asetaTurnaus(kohdetk.get(i));

                    }
                }

                stageV.close();
            }
        });

        jooTallennus.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                Tallennus tallenna = new Tallennus(ikkuna);

                tallenna.suoritaTallennus();
                //tyhjennetään kaikki tiedot 
                ikkuna.annaKohteet().clear();

                Kohde uusiTurnaus = new Turnaus();
                ikkuna.asetaTurnaus(uusiTurnaus);

                //vielä pitää tyhjentää puu
                TreeItem<Kohde> parentSarjat = ikkuna.annaRootSarjat();
                TreeItem<Kohde> parentTuomarit = ikkuna.annaRootTuomarit();
                parentSarjat.getChildren().clear();
                parentTuomarit.getChildren().clear();
                //sitten vasta avaukseen
                //tuodaan tallennetut kohteet
                List<Kohde> kohdetk_vanha = new ArrayList<>();
                List<Kohde> kohdetk = new ArrayList<>();
                Avaus avaaja = new Avaus();
                kohdetk = avaaja.avaa();
                //talletetaan muistiin, mitä oli ennen "istunnon" aloittamista -> tarviiko kysyä tallennusta, jos käyttäjä sulkee näytön

                ikkuna.asetaKohteet(kohdetk);

                List<Sarja> sarjatk = new ArrayList<>();

                List<Tuomari> tuomaritk = new ArrayList<>();

                //viedään kohteet omiin listoihin
                TreeItem<Kohde> parent = new TreeItem<>();
                for (int i = 0; i < kohdetk.size(); i++) {

                    if (kohdetk.get(i) instanceof Sarja) {

                        sarjatk.add((Sarja) kohdetk.get(i));
                        ikkuna.annaSarjatk().add((Sarja) kohdetk.get(i));
                        parent = ikkuna.annaRootSarjat();
                        TreeItem<Kohde> newItem = new TreeItem<Kohde>(kohdetk.get(i));
                        parent.getChildren().add(newItem);

                    } else if (kohdetk.get(i) instanceof Tuomari) {
                        tuomaritk.add((Tuomari) kohdetk.get(i));
                        ikkuna.annaTuomaritk().add((Tuomari) kohdetk.get(i));
                        parent = ikkuna.annaRootTuomarit();
                        TreeItem<Kohde> newItem = new TreeItem<Kohde>(kohdetk.get(i));
                        parent.getChildren().add(newItem);
                    } else if (kohdetk.get(i) instanceof Turnaus) {

                        ikkuna.asetaTurnaus(kohdetk.get(i));

                    }
                }

                stageV.close();
            }
        });
        peruuta.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                stageV.close();
            }
        });
        hbox2.setAlignment(Pos.CENTER);
        hbox2.getChildren().addAll(jooTallennus, eiTallennus, peruuta);
        vbox.getChildren().addAll(hbox1, hbox2);
        alue.setCenter(vbox);

        Scene sceneV = new Scene(alue, 400, 100);
        stageV.setTitle("TUPA - TULOSPALVELU");
        stageV.setScene(sceneV);
        stageV.show();

    }

    public void annaOtteluPoistoVarmistus(Ottelu ottelu) {
        Stage stageV = new Stage();
        BorderPane alue = new BorderPane();

        VBox vbox = new VBox();
        vbox.setPadding(new Insets(10));
        vbox.setSpacing(10);

        HBox hbox1 = new HBox();
        Label viesti = new Label("Haluatko todella poistaa ottelun " + ottelu.toString() + " ?");

        hbox1.setAlignment(Pos.CENTER);
        hbox1.getChildren().add(viesti);

        HBox hbox2 = new HBox();
        hbox2.setPadding(new Insets(10));
        hbox2.setSpacing(10);
        Button joo = new Button("Kyllä");

        Button peruuta = new Button("Peruuta");

        joo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                muuttaja.poistaOttelu(ottelu);

                stageV.close();

            }
        });
        peruuta.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                stageV.close();
            }
        });
        hbox2.setAlignment(Pos.CENTER);
        hbox2.getChildren().addAll(joo, peruuta);
        vbox.getChildren().addAll(hbox1, hbox2);
        alue.setCenter(vbox);

        Scene sceneV = new Scene(alue, 400, 100);
        stageV.setTitle("TUPA - TULOSPALVELU");
        stageV.setScene(sceneV);
        stageV.show();

    }

    public void annaKaikkienOtteluidenPoistoVarmistus(Sarja sarja) {
        Stage stageV = new Stage();
        BorderPane alue = new BorderPane();

        VBox vbox = new VBox();
        vbox.setPadding(new Insets(10));
        vbox.setSpacing(10);

        HBox hbox1 = new HBox();
        Label viesti = new Label("Haluatko todella poistaa kaikki sarjan " + sarja.toString() + " ottelut?");

        hbox1.setAlignment(Pos.CENTER);
        hbox1.getChildren().add(viesti);

        HBox hbox2 = new HBox();
        hbox2.setPadding(new Insets(10));
        hbox2.setSpacing(10);
        Button joo = new Button("Kyllä");

        Button peruuta = new Button("Peruuta");

        joo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                List<Ottelu> poistettavat = (sarja.annaOttelut());

                for (int i = 0; i < poistettavat.size(); i++) {
                    System.out.println(poistettavat.get(i));
                }

                muuttaja.poistaKaikkiOttelut(poistettavat, sarja);
                sarjanakyma = nakyma.annaSarjanakyma();
                TreeItem<Kohde> mihin = new TreeItem<>(sarja);

                sarjanakyma.luoSarjaSivu(mihin);
                stageV.close();

            }
        });
        peruuta.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                stageV.close();
            }
        });
        hbox2.setAlignment(Pos.CENTER);
        hbox2.getChildren().addAll(joo, peruuta);
        vbox.getChildren().addAll(hbox1, hbox2);
        alue.setCenter(vbox);

        Scene sceneV = new Scene(alue, 400, 100);
        stageV.setTitle("TUPA - TULOSPALVELU");
        stageV.setScene(sceneV);
        stageV.show();

    }

    public void annaAutoVarmistus(Sarja sarja) {
        Stage stageV = new Stage();
        BorderPane alue = new BorderPane();

        VBox vbox = new VBox();
        vbox.setPadding(new Insets(10));
        vbox.setSpacing(10);

        HBox hbox1 = new HBox();
        Label viesti = new Label("Automaattinen otteluluettelon laadinta poistaa ensin kaikki sarjaan lisätyt ottelut. Haluatko jatkaa?");

        hbox1.setAlignment(Pos.CENTER);
        hbox1.getChildren().add(viesti);

        HBox hbox2 = new HBox();
        hbox2.setPadding(new Insets(10));
        hbox2.setSpacing(10);
        Button joo = new Button("Kyllä");

        Button peruuta = new Button("Peruuta");

        joo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                muuttaja.suoritaAutoOtteluLista(sarja);
                sarjanakyma = nakyma.annaSarjanakyma();
                TreeItem<Kohde> mihin = new TreeItem<>(sarja);

                sarjanakyma.luoSarjaSivu(mihin);
                stageV.close();

            }
        });
        peruuta.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                stageV.close();
            }
        });
        hbox2.setAlignment(Pos.CENTER);
        hbox2.getChildren().addAll(joo, peruuta);
        vbox.getChildren().addAll(hbox1, hbox2);
        alue.setCenter(vbox);

        Scene sceneV = new Scene(alue, 600, 100);
        stageV.setTitle("TUPA - TULOSPALVELU");
        stageV.setScene(sceneV);
        stageV.show();

    }

    public void annaKaikkienJoukkueidenPoistoVarmistus(Sarja sarja) {
        Stage stageV = new Stage();
        BorderPane alue = new BorderPane();

        VBox vbox = new VBox();
        vbox.setPadding(new Insets(10));
        vbox.setSpacing(10);

        HBox hbox1 = new HBox();
        Label viesti = new Label("Joukkueiden poisto poistaa samalla kaikki joukkueiden pelaajat järjestelmästä. Haluatko jatkaa?");

        hbox1.setAlignment(Pos.CENTER);
        hbox1.getChildren().add(viesti);

        HBox hbox2 = new HBox();
        hbox2.setPadding(new Insets(10));
        hbox2.setSpacing(10);
        Button joo = new Button("Kyllä");

        Button peruuta = new Button("Peruuta");

        joo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                List<Joukkue> poistettavat = (sarja.annaJoukkueet());

                muuttaja.poistaKaikkiJoukkueet(poistettavat, sarja);
                sarjanakyma = nakyma.annaSarjanakyma();
                TreeItem<Kohde> mihin = new TreeItem<>(sarja);

                sarjanakyma.luoSarjaSivu(mihin);
                stageV.close();

            }
        });
        peruuta.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                stageV.close();
            }
        });
        hbox2.setAlignment(Pos.CENTER);
        hbox2.getChildren().addAll(joo, peruuta);
        vbox.getChildren().addAll(hbox1, hbox2);
        alue.setCenter(vbox);

        Scene sceneV = new Scene(alue, 600, 100);
        stageV.setTitle("TUPA - TULOSPALVELU");
        stageV.setScene(sceneV);
        stageV.show();

    }

    public void annaJoukkueenPoistoVarmistus(Joukkue joukkue) {
        Stage stageV = new Stage();
        BorderPane alue = new BorderPane();

        VBox vbox = new VBox();
        vbox.setPadding(new Insets(10));
        vbox.setSpacing(10);

        HBox hbox1 = new HBox();
        Label viesti = new Label("Haluatko todella poistaa joukkueen " + joukkue.toString() + "?");

        hbox1.setAlignment(Pos.CENTER);
        hbox1.getChildren().add(viesti);

        HBox hbox2 = new HBox();
        hbox2.setPadding(new Insets(10));
        hbox2.setSpacing(10);
        Button joo = new Button("Kyllä");

        Button peruuta = new Button("Peruuta");

        joo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                muuttaja.poistaJoukkue(joukkue, joukkue.annaSarja());

                sarjanakyma = nakyma.annaSarjanakyma();

                sarjanakyma.luoJoukkueenLisaysSivu(joukkue.annaSarja());
                stageV.close();

            }
        });
        peruuta.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                stageV.close();
            }
        });
        hbox2.setAlignment(Pos.CENTER);
        hbox2.getChildren().addAll(joo, peruuta);
        vbox.getChildren().addAll(hbox1, hbox2);
        alue.setCenter(vbox);

        Scene sceneV = new Scene(alue, 600, 100);
        stageV.setTitle("TUPA - TULOSPALVELU");
        stageV.setScene(sceneV);
        stageV.show();

    }

    public void annaKaikkienPelaajienPoistoVarmitus(Joukkue joukkue) {
        Stage stageV = new Stage();
        BorderPane alue = new BorderPane();

        VBox vbox = new VBox();
        vbox.setPadding(new Insets(10));
        vbox.setSpacing(10);

        HBox hbox1 = new HBox();
        Label viesti = new Label("Haluatko todella poistaa kaikki pelaajat joukkueesta " + joukkue.toString() + "?");

        hbox1.setAlignment(Pos.CENTER);
        hbox1.getChildren().add(viesti);

        HBox hbox2 = new HBox();
        hbox2.setPadding(new Insets(10));
        hbox2.setSpacing(10);
        Button joo = new Button("Kyllä");

        Button peruuta = new Button("Peruuta");

        joo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                List<Pelaaja> poistettavat = (joukkue.annaPelaajat());

                muuttaja.poistaKaikkiPelaajat(poistettavat, joukkue);
                joukkuenakyma = nakyma.annaJoukkuenakyma();
                joukkuenakyma.luoJoukkueSivu(joukkue);
                stageV.close();

            }
        });
        peruuta.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                stageV.close();
            }
        });
        hbox2.setAlignment(Pos.CENTER);
        hbox2.getChildren().addAll(joo, peruuta);
        vbox.getChildren().addAll(hbox1, hbox2);
        alue.setCenter(vbox);

        Scene sceneV = new Scene(alue, 600, 100);
        stageV.setTitle("TUPA - TULOSPALVELU");
        stageV.setScene(sceneV);
        stageV.show();

    }

    void annaKaikkienToimarienPoistoVarmitus(Joukkue joukkue) {
        Stage stageV = new Stage();
        BorderPane alue = new BorderPane();

        VBox vbox = new VBox();
        vbox.setPadding(new Insets(10));
        vbox.setSpacing(10);

        HBox hbox1 = new HBox();
        Label viesti = new Label("Haluatko todella poistaa kaikki toimihenkilöt joukkueesta " + joukkue.toString() + "?");

        hbox1.setAlignment(Pos.CENTER);
        hbox1.getChildren().add(viesti);

        HBox hbox2 = new HBox();
        hbox2.setPadding(new Insets(10));
        hbox2.setSpacing(10);
        Button joo = new Button("Kyllä");

        Button peruuta = new Button("Peruuta");

        joo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                List<Toimihenkilo> poistettavat = (joukkue.annaToimarit());

                muuttaja.poistaKaikkiToimarit(poistettavat, joukkue);
                joukkuenakyma = nakyma.annaJoukkuenakyma();
                joukkuenakyma.luoJoukkueSivu(joukkue);
                stageV.close();

            }
        });
        peruuta.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                stageV.close();
            }
        });
        hbox2.setAlignment(Pos.CENTER);
        hbox2.getChildren().addAll(joo, peruuta);
        vbox.getChildren().addAll(hbox1, hbox2);
        alue.setCenter(vbox);

        Scene sceneV = new Scene(alue, 600, 100);
        stageV.setTitle("TUPA - TULOSPALVELU");
        stageV.setScene(sceneV);
        stageV.show();
    }

}
