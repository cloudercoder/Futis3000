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

    private List<Sarja> sarjatk = new ArrayList<>();
    private List<Joukkue> joukkuetk = new ArrayList<>();
    private List<Pelaaja> pelaajatk = new ArrayList<>();
    private List<Tuomari> tuomaritk = new ArrayList<>();
    private List<Valmentaja> valmentajatk = new ArrayList<>();
    private List<Joukkueenjohtaja> jojotk = new ArrayList<>();
 
    //kaikki kohteet tallennetaan tähän
    private List<Kohde> kohdetk = new ArrayList<>();
    

    //yläosaan valikko
    private Valikko valikko;

    //alaosaan toimintaloki
    private ListView loki = new ListView();

    //sivulle puurakenne, jossa valikko
    private VBox sivu = new VBox();
    private TreeView<Kohde> sivuPuu = new TreeView<>();

    //keskiosaan ylös logo ja keskelle varsinainen näyttö
    private VBox keski = new VBox();
    private HBox kuvaotsikko = new HBox();
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
    
    //uusien kohtien lisäystä varten
    private TextField textField = new TextField();

    //kohteiden tallennus tiedostoon
    private static String testitiedosto="TUPA_kohteet";
    
    public static void main(String[] args) {
        launch(args);
    }

    @Override

    public void start(Stage primaryStage) {

        //tuodaan tallennetut kohteet
        
        ObjectInputStream sisaan2 = null;

        try {
            sisaan2 = new ObjectInputStream(new FileInputStream(testitiedosto));
                while (true) {
                    kohdetk.add((Kohde)sisaan2.readObject());         
            }
        } catch (EOFException e) {
            
        } catch (ClassNotFoundException e) {
            
            System.out.println("\n**********************************************************");
            System.out.println("\nOngelmia kohteiden löytämisessä. ");
            System.out.println("\n**********************************************************");
            
        } catch (IOException e) {
             
            System.out.println("\n**********************************************************");
            System.out.println("\nOngelmia tiedoston avauksessa.");
            System.out.println("\n**********************************************************");
           
        }

        //viedään kohteet omiin listoihin
        
        for(int i=0; i<kohdetk.size(); i++){
            
            if(kohdetk.get(i) instanceof Sarja){
                
                sarjatk.add((Sarja)kohdetk.get(i));
            }
            else if(kohdetk.get(i) instanceof Joukkue){
                 joukkuetk.add((Joukkue)kohdetk.get(i));
            }
            else if(kohdetk.get(i) instanceof Pelaaja){
                 pelaajatk.add((Pelaaja)kohdetk.get(i));
            }
             else if(kohdetk.get(i) instanceof Tuomari){
                 tuomaritk.add((Tuomari)kohdetk.get(i));
            }
             else if(kohdetk.get(i) instanceof Valmentaja){
                 valmentajatk.add((Valmentaja)kohdetk.get(i));
            }
             else if(kohdetk.get(i) instanceof Joukkueenjohtaja){
                 jojotk.add((Joukkueenjohtaja)kohdetk.get(i));
            }
        }
        
        
     
        BorderPane border = new BorderPane();

        //keskinäytön tyylittely
        naytto.setStyle("-fx-background-color: white;");
        setEkaOhje("Valitse vasemmalta haluamasi kohde.");

        //muodostaan ylävalikko
        MenuBar menuBar = new MenuBar();
        valikko = new Valikko(menuBar);
        menuBar = valikko.buildMenuBar();
        border.setTop(menuBar);

        //alaosaan tulee toimintaloki
        VBox alaosio = new VBox();
        alaosio.setStyle("-fx-background-color: linear-gradient(to bottom, #1a1a1a, #404040); -fx-border-color: BLACK; -fx-border-width: 1px; -fx-text-fill: #ff0099 ");
        alaosio.setPadding(new Insets(10));
        alaosio.setSpacing(10);
        Label alaotsikko = new Label("Tapahtumatiedot:");
        alaotsikko.setStyle("-fx-text-fill: #fff ");
        loki.setPrefHeight(100);
        loki.setPrefWidth(25);
        alaosio.getChildren().addAll(alaotsikko, loki);

        border.setBottom(alaosio);

        //sivulaitaan puurakenne, joka sisältää kohteet
        sivu.setStyle("-fx-background-color: linear-gradient(to right, #00b300, 	 #33ff33); -fx-border-color: BLACK; -fx-border-width: 1px 1px 0px 1px");
        sivu.setPadding(new Insets(100, 10, 0, 10));
        sivu.setSpacing(10);
        
        PuuTehdas puutehdas = new PuuTehdas(sarjatk, joukkuetk, pelaajatk, tuomaritk, valmentajatk, jojotk);

        ArrayList<TreeItem<Kohde>> sarjat = puutehdas.getSarjat();
        ArrayList<TreeItem<Kohde>> joukkueet = puutehdas.getJoukkueet();
        ArrayList<TreeItem<Kohde>> pelaajat = puutehdas.getPelaajat();
        ArrayList<TreeItem<Kohde>> tuomarit = puutehdas.getTuomarit();
        ArrayList<TreeItem<Kohde>> valmentajat = puutehdas.getValmentajat();
        ArrayList<TreeItem<Kohde>> joukkueenjohtajat = puutehdas.getJoukkueenjohtajat();

        Kohde rootS = new Sarja("Sarjat");

        rootSarjat = new TreeItem<>(rootS);
        rootSarjat.getChildren().addAll(sarjat);

        //tehdään keinotekonen väli
        Kohde v1 = new Kohde("");
        TreeItem<Kohde> vikaSarjat = new TreeItem<>(v1);

        Kohde rootJ = new Joukkue("Joukkueet");
        rootJoukkueet = new TreeItem<>(rootJ);
        rootJoukkueet.getChildren().addAll(joukkueet);

        //tehdään keinotekonen väli
        Kohde v2 = new Kohde("");
        TreeItem<Kohde> vikaJoukkueet = new TreeItem<>(v2);

        Kohde rootP = new Pelaaja("Pelaajat");
        rootPelaajat = new TreeItem<>(rootP);
        rootPelaajat.getChildren().addAll(pelaajat);

        //tehdään keinotekonen väli
        Kohde v3 = new Kohde("");
        TreeItem<Kohde> vikaP = new TreeItem<>(v3);

        Kohde rootT = new Tuomari("Tuomarit");
        rootTuomarit = new TreeItem<>(rootT);
        rootTuomarit.getChildren().addAll(tuomarit);

        //tehdään keinotekonen väli
        Kohde v4 = new Kohde("");
        TreeItem<Kohde> vikaT = new TreeItem<>(v4);

        Kohde rootTo = new Toimihenkilo("Toimihenkilöt");
        rootToimarit = new TreeItem<>(rootTo);

        Kohde rootTo1 = new Valmentaja("Valmentajat");
        rootToimarit1 = new TreeItem<>(rootTo1);

        Kohde rootTo2 = new Joukkueenjohtaja("Joukkueenjohtajat");
        rootToimarit2 = new TreeItem<>(rootTo2);

        rootToimarit1.getChildren().addAll(valmentajat);
        rootToimarit2.getChildren().addAll(joukkueenjohtajat);
        rootToimarit.getChildren().addAll(rootToimarit1, rootToimarit2);

        // näkymätön juuri
        Kohde rootK = new Kohde("Menu");
        TreeItem<Kohde> rootSivuPuu = new TreeItem<>(rootK);
        rootSivuPuu.getChildren().addAll(rootSarjat, vikaSarjat, rootJoukkueet, vikaJoukkueet, rootPelaajat, vikaP, rootTuomarit, vikaT, rootToimarit);

        sivuPuu.setRoot(rootSivuPuu);

        // seuraavat käsittelee tapahtumia, kun käyttäjä klikkaa sivuvalikon kohteita
        rootSarjat.addEventHandler(TreeItem.<Kohde>branchExpandedEvent(), new EventHandler<TreeItem.TreeModificationEvent<Kohde>>() {
            @Override
            public void handle(TreeItem.TreeModificationEvent<Kohde> event) {
                branchExpended(event);
            }
        });

        rootSarjat.addEventHandler(TreeItem.<Kohde>branchCollapsedEvent(), new EventHandler<TreeItem.TreeModificationEvent<Kohde>>() {
            @Override
            public void handle(TreeItem.TreeModificationEvent<Kohde> event) {
                branchCollapsed(event);
            }
        });

        rootJoukkueet.addEventHandler(TreeItem.<Kohde>branchExpandedEvent(), new EventHandler<TreeItem.TreeModificationEvent<Kohde>>() {
            @Override
            public void handle(TreeItem.TreeModificationEvent<Kohde> event) {
                branchExpended(event);
            }
        });

        rootJoukkueet.addEventHandler(TreeItem.<Kohde>branchCollapsedEvent(), new EventHandler<TreeItem.TreeModificationEvent<Kohde>>() {
            @Override
            public void handle(TreeItem.TreeModificationEvent<Kohde> event) {
                branchCollapsed(event);
            }
        });

        rootPelaajat.addEventHandler(TreeItem.<Kohde>branchExpandedEvent(), new EventHandler<TreeItem.TreeModificationEvent<Kohde>>() {
            @Override
            public void handle(TreeItem.TreeModificationEvent<Kohde> event) {
                branchExpended(event);
            }
        });

        rootPelaajat.addEventHandler(TreeItem.<Kohde>branchCollapsedEvent(), new EventHandler<TreeItem.TreeModificationEvent<Kohde>>() {
            @Override
            public void handle(TreeItem.TreeModificationEvent<Kohde> event) {
                branchCollapsed(event);
            }
        });

        // juuri näkymättömäksi
        sivuPuu.setShowRoot(false);
        sivu.getChildren().addAll(sivuPuu);
        sivu.setVgrow(sivuPuu, Priority.ALWAYS);

        SivuKuuntelija tarkkailija = new SivuKuuntelija(this);
        sivuPuu.getSelectionModel().selectedItemProperty().addListener(tarkkailija);

        border.setLeft(sivu);

        kuvaotsikko.setPadding(new Insets(10, 10, 15, 0));
        kuvaotsikko.setSpacing(10);

        ImageView selectedImage = new ImageView();
        Image image1 = new Image(TUPA.class.getResourceAsStream("pallo.jpg"));
        selectedImage.setImage(image1);
        selectedImage.setFitHeight(20);
        selectedImage.setFitWidth(20);

        ImageView selectedImage2 = new ImageView();
        Image image2 = new Image(TUPA.class.getResourceAsStream("pallo.jpg"));
        selectedImage2.setImage(image2);
        selectedImage2.setFitHeight(20);
        selectedImage2.setFitWidth(20);

        Label logo = new Label("TUPA \t - \t Tulospalvelu ");
        logo.setFont(Font.font("Papyrus", FontWeight.BOLD, 28));

        kuvaotsikko.setStyle("-fx-background-color:  linear-gradient(to bottom, #00b300, 	 #33ff33); -fx-border-color: BLACK; -fx-border-width: 1px 0px 1px 0px;");
        kuvaotsikko.setPadding(new Insets(20));
        kuvaotsikko.setSpacing(30);
        kuvaotsikko.setAlignment(Pos.CENTER);
        kuvaotsikko.getChildren().addAll(selectedImage, logo, selectedImage2);

        keski.getChildren().add(kuvaotsikko);
        keski.getChildren().add(naytto);

        border.setCenter(keski);

        Scene scene = new Scene(border, 500, 500);
        primaryStage.setTitle("TUPA \t - \t Tulospalvelu");
        scene.getStylesheets().add(TUPA.class.getResource("tyylit.css").toExternalForm());

        primaryStage.getIcons().add(new Image(TUPA.class.getResourceAsStream("icon.png")));
        primaryStage.setScene(scene);

        primaryStage.show();

        //käyttäjä painaa ikkunan x-nappulaa
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {

                tallenna();
                System.out.println("Stage is closing");
            }
        });
    }

    public void tallenna() {
        ObjectOutputStream ulos = null;

        try {
            ulos = new ObjectOutputStream(new FileOutputStream(testitiedosto));

            if (!kohdetk.isEmpty()) {
               
                for (int i = 0; i < kohdetk.size(); i++) {
                    
                    ulos.writeObject(kohdetk.get(i));
                }
            }

        } catch (IOException e) {
            

            System.out.println("\n****************************************************************");
            System.out.println("\nKohteiden tallentaminen ei onnistunut.");
            System.out.println("\n****************************************************************");

        } finally {
            try {
                ulos.close();
            } catch (IOException e) {
               

                System.out.println("\n************************************************************");
                System.out.println("Tiedoston sulkeminen ei onnistunut.");
                System.out.println("\n***********************************************************");

            }
        }


    }

    private void setEkaOhje(String uusiohje) {

        HBox ohjepalkki = new HBox();
        ohjepalkki.setStyle("-fx-background-color: blue;");
        ohjepalkki.setPadding(new Insets(10, 30, 10, 30));
        Text ohje = new Text(uusiohje);
        ohje.setFont(Font.font("Papyrus", FontWeight.BOLD, 20));

        ohjepalkki.getChildren().add(ohje);

        VBox peitto = new VBox();
        peitto.setStyle("-fx-background-color: white;");
        naytto.getChildren().add(peitto);

        GridPane grid = new GridPane();

        grid.setPadding(new Insets(0, 100, 0, 300));
        grid.setVgap(10);
        grid.add(ohjepalkki, 1, 5);

        naytto.getChildren().add(grid);

    }

    private void setOhje(String uusiohje, TreeItem<Kohde> arvo) {

        HBox ohjepalkki = new HBox();
        ohjepalkki.setStyle("-fx-background-color: blue;");
        ohjepalkki.setPadding(new Insets(10, 30, 10, 30));
        Text ohje = new Text(uusiohje);
        ohje.setFont(Font.font("Papyrus", FontWeight.BOLD, 20));

        ohjepalkki.getChildren().add(ohje);

        VBox peitto = new VBox();
        peitto.setStyle("-fx-background-color: white;");
        naytto.getChildren().add(peitto);

        GridPane grid = new GridPane();

        grid.setPadding(new Insets(0, 100, 0, 300));
        grid.setVgap(10);
        grid.add(ohjepalkki, 1, 5);

        Button uusi = new Button();

        if (arvo.getValue() instanceof Sarja) {
            uusi.setText("Lisää uusi sarja");

        } else if (arvo.getValue() instanceof Joukkue) {
            uusi.setText("Lisää uusi joukkue");

        }
        uusi.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

                VBox peitto = new VBox();
                peitto.setStyle("-fx-background-color: white;");
                naytto.getChildren().add(peitto);

                if (arvo.getValue() instanceof Sarja) {
                    naytto.getChildren().add(luoPaanayttoSarjat());
                } else if (arvo.getValue() instanceof Joukkue) {
                    naytto.getChildren().add(luoPaanayttoJoukkueet());
                }

            }
        });

        grid.add(uusi, 3, 2);
        naytto.getChildren().add(grid);
    }

    private void branchExpended(TreeItem.TreeModificationEvent<Kohde> event) {
        String nodeValue = event.getSource().getValue().toString();
        this.writeMessage("Kohde " + nodeValue + " valittu.");

    }

    private void branchCollapsed(TreeItem.TreeModificationEvent<Kohde> event) {
        String nodeValue = event.getSource().getValue().toString();
        this.writeMessage("Kohde " + nodeValue + " suljettu.");

    }

    private void writeMessage(String msg) {
        ObservableList<String> viesti = FXCollections.observableArrayList(msg);
        loki.setItems(viesti);

    }

    public void valittuKohde(TreeItem<Kohde> arvo) {

        String ohje = "";
        if (arvo == rootSarjat) {

            ohje = ("Valitse vasemmalta haluamasi sarja.");
            setOhje(ohje, arvo);

        } else if (arvo == rootJoukkueet) {
            ohje = ("Valitse vasemmalta haluamasi joukkue.");
            setOhje(ohje, arvo);

        } else if (arvo == rootPelaajat) {
            ohje = ("Valitse vasemmalta haluamasi pelaaja.");
            setOhje(ohje, arvo);

        } else if (arvo == rootTuomarit) {
            ohje = ("Valitse vasemmalta haluamasi tuomari.");
            setOhje(ohje, arvo);

        } else if (arvo == rootToimarit) {
            ohje = ("Valitse vasemmalta haluamasi toimihenkilö.");
            setOhje(ohje, arvo);

        } else if (arvo == rootToimarit1) {
            ohje = ("Valitse vasemmalta haluamasi valmentaja.");
            setOhje(ohje, arvo);

        } else if (arvo == rootToimarit2) {
            ohje = ("Valitse vasemmalta haluamasi joukkueenjohtaja.");
            setOhje(ohje, arvo);

        } else {
            //tähän mitä tapahtuu, jos alakohteita klikattu
        }

    }

    public StackPane getNaytto() {
        return naytto;
    }

    public GridPane luoPaanayttoSarjat() {

        Button addItemBtn = new Button("Lisää uusi sarja");
        addItemBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (textField.getText().trim().isEmpty()) {

                    writeMessage("Et voi antaa tyhjää kenttää.");
                } else {

                    Kohde uusi = new Sarja(textField.getText());
                    
                    addItem(uusi);
                }

            }
        });

        // tämän vois laittaa kunkin sarjan omaan näyttöön
        Button removeItemBtn = new Button("Poista valittu sarja");
        removeItemBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                removeItem();
            }
        });

        HBox hbox1 = new HBox();
        Label label1 = new Label("Sarjan nimi:");
        hbox1.setSpacing(10);
        hbox1.getChildren().addAll(label1, textField, addItemBtn);

        VBox hbox2 = new VBox();
        Label label2 = new Label("Valitse vasemman puolen valikosta haluamasi sarja ja paina oheista painiketta poistaaksesi sen.");

        hbox2.setSpacing(10);
        hbox2.getChildren().addAll(label2, removeItemBtn);

        GridPane grid = new GridPane();

        grid.setPadding(new Insets(0, 100, 0, 300));
        grid.setVgap(10);

        Label otsikko = new Label("Lisää uusi sarja tai poista olemassa oleva.");
        otsikko.setFont(Font.font("Papyrus", 28));

        grid.add(otsikko, 1, 5);
        grid.add(hbox1, 1, 7);
        grid.add(hbox2, 1, 9);

    
            return grid;
    }
    
        public GridPane luoPaanayttoJoukkueet() {

        Button addItemBtn = new Button("Lisää uusi joukkue");
        addItemBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (textField.getText().trim().isEmpty()) {

                    writeMessage("Et voi antaa tyhjää kenttää.");
                } else {

                    Kohde uusi = new Joukkue(textField.getText());
                    
                    addItem(uusi);
                }

            }
        });

        // tämän vois laittaa kunkin sarjan omaan näyttöön
        Button removeItemBtn = new Button("Poista valittu joukkue");
        removeItemBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                removeItem();
            }
        });

        HBox hbox1 = new HBox();
        Label label1 = new Label("Joukkueen nimi:");
        hbox1.setSpacing(10);
        hbox1.getChildren().addAll(label1, textField, addItemBtn);

        VBox hbox2 = new VBox();
        Label label2 = new Label("Valitse vasemman puolen valikosta haluamasi joukkue ja paina oheista painiketta poistaaksesi sen.");

        hbox2.setSpacing(10);
        hbox2.getChildren().addAll(label2, removeItemBtn);

        GridPane grid = new GridPane();

        grid.setPadding(new Insets(0, 100, 0, 300));
        grid.setVgap(10);

        Label otsikko = new Label("Lisää uusi joukkue tai poista olemassa oleva.");
        otsikko.setFont(Font.font("Papyrus", 28));

        grid.add(otsikko, 1, 5);
        grid.add(hbox1, 1, 7);
        grid.add(hbox2, 1, 9);

    
            return grid;
    }
    
    private void addItem(Kohde value) {

        TreeItem<Kohde> parent = new TreeItem<>();

        if (value instanceof Sarja) {
            parent = rootSarjat;
        } else if (value instanceof Joukkue) {
            parent = rootJoukkueet;
        } else if (value instanceof Pelaaja) {
            parent = rootPelaajat;
        } else if (value instanceof Valmentaja) {
            parent = rootToimarit1;
        } else if (value instanceof Joukkueenjohtaja) {
            parent = rootToimarit2;
        } else if (value instanceof Tuomari) {
            parent = rootTuomarit;
        }

        for (TreeItem<Kohde> child : parent.getChildren()) {
            if (child.getValue().toString().equals(value.toString())) {

                if (value instanceof Sarja) {
                    this.writeMessage("Tämänniminen sarja on jo olemassa ");
                } else if (value instanceof Joukkue) {
                    this.writeMessage("Tämänniminen joukkue on jo olemassa ");
                }

                return;
            }
        }

        // MITÄ MUUTA PITÄÄ TARKISTAA??!?!
        //LISÄYKSET:
        // puuhun:
        TreeItem<Kohde> newItem = new TreeItem<Kohde>(value);
        parent.getChildren().add(newItem);

        // kohteen omat:
        //yleiseen tietokantaan:
        if (value instanceof Sarja) {
            sarjatk.add((Sarja) value);
          
        } else if (value instanceof Joukkue) {
            joukkuetk.add((Joukkue) value);
        } else if (value instanceof Pelaaja) {
            pelaajatk.add((Pelaaja) value);
        } else if (value instanceof Tuomari) {
            tuomaritk.add((Tuomari) value);
        } else if (value instanceof Valmentaja) {
            valmentajatk.add((Valmentaja) value);
        } else if (value instanceof Joukkueenjohtaja) {
            jojotk.add((Joukkueenjohtaja) value);
        }

       kohdetk.add(value);    
        //avataan se valikko, mihin uusi kohde on lisätty
        if (!parent.isExpanded()) {
            parent.setExpanded(true);
        }
    }

    private void removeItem() {
        TreeItem<Kohde> item = sivuPuu.getSelectionModel().getSelectedItem();

        if (item == null) {
            this.writeMessage("Valitse sarja, jonka haluat poistaa.");
            return;
        }

        TreeItem<Kohde> parent = item.getParent();
        if (parent == null) {
            this.writeMessage("Sarjaa ei voida poistaa.");
        } else {
            parent.getChildren().remove(item);
        }
    }

    public List<TreeItem<Kohde>> annaJuuret() {
        List<TreeItem<Kohde>> juuret = new ArrayList<TreeItem<Kohde>>();

        juuret.add(rootSarjat);
        return juuret;
    }

}
