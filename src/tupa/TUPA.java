/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tupa;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Observable;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import static javafx.geometry.Pos.BASELINE_CENTER;
import static javafx.geometry.Pos.BOTTOM_CENTER;
import static javafx.geometry.Pos.CENTER;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.TextFieldTreeCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.layout.Priority;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import static javafx.scene.layout.Priority.ALWAYS;
import javafx.stage.WindowEvent;

/**
 *
 * @author Omistaja
 */
public class TUPA extends Application {

    //kaikki kohteet tallennetaan tähän
    private List<Kohde> kohdetk = new ArrayList<>();

    //jotka siirretään ohjelman alkaessa omiin listoihin
    private List<Sarja> sarjatk = new ArrayList<>();
    private List<Joukkue> joukkuetk = new ArrayList<>();
    private List<Pelaaja> pelaajatk = new ArrayList<>();
    private List<Tuomari> tuomaritk = new ArrayList<>();
    private List<Valmentaja> valmentajatk = new ArrayList<>();
    private List<Joukkueenjohtaja> jojotk = new ArrayList<>();

    //yläosaan valikko
    private Valikko valikko;

    //alaosan loki
    private ListView loki = new ListView();
    //sivulle puurakenne, jossa valikko
    private VBox sivu = new VBox();
    private TreeView<Kohde> sivuPuu = new TreeView<>();

    //keskiosaan ylös logo ja keskelle varsinainen näyttö
    private VBox keski = new VBox();

    //nayttö on stackpane että asettaa uudet näkymät päällekkäin siten, ettei vanhat enää näy
    private StackPane naytto = new StackPane();

    //puurakenteen "juuret"
    private TreeItem<Kohde> rootSarjat;
    private TreeItem<Kohde> rootJoukkueet;
    private TreeItem<Kohde> rootTuomarit;
    private TreeItem<Kohde> rootPelaajat;
    private TreeItem<Kohde> rootToimarit;
    private TreeItem<Kohde> rootToimarit1;
    private TreeItem<Kohde> rootToimarit2;

    public static void main(String[] args) {
        launch(args);
    }

    @Override

    public void start(Stage primaryStage) {

        //tuodaan tallennetut kohteet
        Avaus avaaja = new Avaus();
        kohdetk = avaaja.avaa();
        //viedään kohteet omiin listoihin
        for (int i = 0; i < kohdetk.size(); i++) {

            if (kohdetk.get(i) instanceof Sarja) {

                sarjatk.add((Sarja) kohdetk.get(i));
            } else if (kohdetk.get(i) instanceof Joukkue) {
                joukkuetk.add((Joukkue) kohdetk.get(i));
            } else if (kohdetk.get(i) instanceof Pelaaja) {
                pelaajatk.add((Pelaaja) kohdetk.get(i));
            } else if (kohdetk.get(i) instanceof Tuomari) {
                tuomaritk.add((Tuomari) kohdetk.get(i));
            } else if (kohdetk.get(i) instanceof Valmentaja) {
                valmentajatk.add((Valmentaja) kohdetk.get(i));
            } else if (kohdetk.get(i) instanceof Joukkueenjohtaja) {
                jojotk.add((Joukkueenjohtaja) kohdetk.get(i));
            }
        }

        BorderPane border = new BorderPane();

        Nakymat nakyma = new Nakymat(this);
        //keskinäytön tyylittely
        naytto.setStyle("-fx-background-color: white;");
        //aloitusnäkymä
        nakyma.setEkaOhje("Valitse vasemmalta haluamasi kohde.");

        //muodostaan ylävalikko
        MenuBar menuBar = new MenuBar();
        valikko = new Valikko(menuBar, this);
        menuBar = valikko.buildMenuBar();
        border.setTop(menuBar);

        //alaosaan tulee toimintaloki
        Pysyvat osiot = new Pysyvat(this);
        border.setBottom(osiot.rakennaAlaosa());

        //sivulaitaan puurakenne, joka sisältää kohteet
        sivu.setStyle("-fx-background-color: linear-gradient(to right, #00b300, 	 #33ff33); -fx-border-color: BLACK; -fx-border-width: 1px 1px 0px 1px");
        sivu.setPadding(new Insets(100, 10, 0, 10));
        sivu.setSpacing(10);
        Puurakenne puu = new Puurakenne(this);
        sivuPuu = puu.rakennaPuu();
        	PuuSoluTehdas tehdas = new PuuSoluTehdas (this);
		sivuPuu.setCellFactory (tehdas);
        // juuri näkymättömäksi
        sivuPuu.setShowRoot(false);
        sivu.getChildren().addAll(sivuPuu);
        sivu.setVgrow(sivuPuu, Priority.ALWAYS);

        SivuKuuntelija tarkkailija = new SivuKuuntelija(this);
        sivuPuu.getSelectionModel().selectedItemProperty().addListener(tarkkailija);

        border.setLeft(sivu);

        //ylaosan "logo"
        keski.getChildren().add(osiot.rakennaYlaosa());
        keski.getChildren().add(naytto);
        border.setCenter(keski);

        Scene scene = new Scene(border, 500, 500);
        primaryStage.setTitle("TUPA \t - \t Tulospalvelu");
        scene.getStylesheets().add(TUPA.class.getResource("tyylit.css").toExternalForm());

        primaryStage.getIcons().add(new Image(TUPA.class.getResourceAsStream("icon.png")));
        primaryStage.setScene(scene);
        Platform.setImplicitExit(false);
        primaryStage.show();
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {

            public void handle(WindowEvent we) {
                we.consume();
                Varmistus varmista = new Varmistus(kohdetk);
                varmista.annaVarmistus();
            }
        });

    }

    public List<Sarja> annaSarjatk() {
        return sarjatk;
    }

    public List<Joukkue> annaJoukkuetk() {
        return joukkuetk;
    }

    public List<Pelaaja> annaPelaajatk() {
        return pelaajatk;
    }

    public List<Tuomari> annaTuomaritk() {
        return tuomaritk;
    }

    public List<Valmentaja> annaValmentajatk() {
        return valmentajatk;
    }

    public List<Joukkueenjohtaja> annaJojotk() {
        return jojotk;
    }

    public void setRootSarjat(TreeItem<Kohde> rs) {
        rootSarjat = rs;
    }

    public void setRootJoukkueet(TreeItem<Kohde> rj) {
        rootJoukkueet = rj;
    }

    public void setRootPelaajat(TreeItem<Kohde> rp) {
        rootPelaajat = rp;
    }

    public void setRootTuomarit(TreeItem<Kohde> rt) {
        rootTuomarit = rt;
    }

    public void setRootToimarit(TreeItem<Kohde> rto) {
        rootToimarit = rto;
    }

    public void setRootToimarit1(TreeItem<Kohde> rto1) {
        rootToimarit1 = rto1;
    }

    public void setRootToimarit2(TreeItem<Kohde> rto2) {
        rootToimarit2 = rto2;
    }

    public TreeItem<Kohde> annaRootSarjat() {
        return rootSarjat;
    }

    public TreeItem<Kohde> annaRootJoukkueet() {
        return rootJoukkueet;
    }

    public TreeItem<Kohde> annaRootPelaajat() {
        return rootPelaajat;
    }

    public TreeItem<Kohde> annaRootTuomarit() {
        return rootTuomarit;
    }

    public TreeItem<Kohde> annaRootToimarit() {
        return rootToimarit;
    }

    public TreeItem<Kohde> annaRootToimarit1() {
        return rootToimarit1;
    }

    public TreeItem<Kohde> annaRootToimarit2() {
        return rootToimarit2;
    }

    public List<Kohde> annaKohteet() {
        return kohdetk;
    }

    public ListView annaLoki() {
        return loki;
    }

    public StackPane annaNaytto() {
        return naytto;
    }
   
    public TreeView<Kohde> annaSivuPuu() {
        return sivuPuu;
    }

}
