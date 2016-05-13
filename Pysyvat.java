/*
Luokka, joka rakentaa näkymien samanlaisena pysyvät osiot
 */
package tupa;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 *
 * @author Marianne
 */
public class Pysyvat {

    private Tupa ikkuna;

    Pysyvat() {

    }

    Pysyvat(Tupa ikkuna) {
        this.ikkuna = ikkuna;

    }

    public HBox rakennaYlaosa() {
        HBox kuvaotsikko = new HBox();

        kuvaotsikko.setPrefWidth(400);
        ImageView selectedImage = new ImageView();
        Image image1 = new Image(Tupa.class.getResourceAsStream("pallo.jpg"));
        selectedImage.setImage(image1);
        selectedImage.setFitHeight(20);
        selectedImage.setFitWidth(20);

        ImageView selectedImage2 = new ImageView();
        Image image2 = new Image(Tupa.class.getResourceAsStream("pallo.jpg"));
        selectedImage2.setImage(image2);
        selectedImage2.setFitHeight(20);
        selectedImage2.setFitWidth(20);

        Label logo = new Label("TUPA \t - \t Tulospalvelu ");
        logo.setFont(Font.font("Papyrus", FontWeight.BOLD, 28));

        kuvaotsikko.setStyle("-fx-background-color:  linear-gradient(to bottom, #00b300, 	 #33ff33);");
        kuvaotsikko.setPadding(new Insets(10));
        kuvaotsikko.setSpacing(30);
        kuvaotsikko.setAlignment(Pos.CENTER);
        kuvaotsikko.getChildren().addAll(selectedImage, logo, selectedImage2);
        return kuvaotsikko;
    }

    public VBox rakennaAlaosa() {
        ListView loki = ikkuna.annaLoki();
        VBox alaosio = new VBox();
        alaosio.setStyle("-fx-background-color: linear-gradient(to bottom, #1a1a1a, #404040); -fx-border-color: BLACK; -fx-border-width: 1px; -fx-text-fill: #ff0099 ");
        alaosio.setPadding(new Insets(5, 10, 5, 10));
        alaosio.setSpacing(10);

        alaosio.setPrefWidth(600);
        alaosio.setPrefHeight(120);
        Label alaotsikko = new Label("Tapahtumatiedot:");
        alaotsikko.setStyle("-fx-text-fill: #fff ");
        alaosio.setVgrow(loki, Priority.ALWAYS);
        alaosio.getChildren().addAll(alaotsikko, loki);

        return alaosio;
    }

    public VBox rakennaVasensivu(TreeView<Kohde> spuu) {
        //sivulaitaan puurakenne, joka sisältää kohteet

        VBox sivu = new VBox();
        sivu.setStyle("-fx-background-color: linear-gradient(to right, #00b300, 	 #33ff33);");
        sivu.setPadding(new Insets(70, 5, 0, 5));
        sivu.setSpacing(10);

        //hakutoiminto HBox
        // Etusivu-linkki
        HBox etusivu = new HBox();
        LinkkiLabel etusivuteksti = new LinkkiLabel(ikkuna);
        etusivuteksti.setText("Etusivu");

        etusivuteksti.linkkiaKlikattu();

        etusivu.setPadding(new Insets(10));
        etusivu.setSpacing(10);
        etusivu.getChildren().addAll(etusivuteksti);

        sivu.setPrefWidth(180);
        sivu.getChildren().addAll(etusivu, spuu);
        sivu.setVgrow(spuu, Priority.ALWAYS);

        return sivu;
    }

}
