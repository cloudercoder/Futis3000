/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tupa;

/**
 *
 * @author Omistaja
 */
import java.util.List;
import javafx.scene.control.TreeItem;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.layout.VBox;

public class SivuKuuntelija implements ChangeListener<TreeItem<Kohde>> {

    private Tupa ikkuna;

    SivuKuuntelija(Tupa ikkuna) {
        this.ikkuna = ikkuna;

    }

    @Override
    public void changed(ObservableValue<? extends TreeItem<Kohde>> observable, TreeItem<Kohde> oldValue, TreeItem<Kohde> newValue) {
        TreeItem<Kohde> selectedItem = newValue;

        Kasittelija kasittelija = new Kasittelija(ikkuna);
        kasittelija.valittuKohde(selectedItem);

    }

}
