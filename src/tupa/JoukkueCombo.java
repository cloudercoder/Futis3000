package tupa;

import java.util.ArrayList;
import java.util.List;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.TableCell;

/**
 *
 * @author Marianne
 */
public class JoukkueCombo extends TableCell<Ottelu, Joukkue>{
 private ComboBox<Joukkue> comboBox;
private Sarja sarja;

    public JoukkueCombo() {
    }
      public JoukkueCombo(Sarja sarja) {
          this.sarja = sarja;
    }

    @Override
    public void startEdit() {
        if (!isEmpty()) {
            super.startEdit();
            createComboBox();
            setText(null);
            setGraphic(comboBox);
        }
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();

      setText(getItem().toString());
        setGraphic(null);
    }

    @Override
    public void updateItem(Joukkue item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {
            setText(null);
            setGraphic(null);
        } else if (isEditing()) {
            if (comboBox != null) {
                comboBox.setValue(item);
            }
             setText(null);
            setGraphic(comboBox);
        } else {
            //näytetään voimassa oleva arvo
            setText(getString().toString());
            setGraphic(null);
        }
    }

    private void createComboBox() {

        ComboBox<Joukkue> joukkue = new ComboBox();
        List<Joukkue> joukkuelista = new ArrayList();

        for (int i = 0; i < sarja.annaJoukkueet().size(); i++) {
            joukkuelista.add(sarja.annaJoukkueet().get(i));
        }

        ObservableList joukkueet1 = FXCollections.observableList(joukkuelista);
        joukkue.setItems(joukkueet1);

        comboBox = joukkue;
        
      
        
        
        comboBoxConverter(comboBox);
        //eka arvo valikossa on olemassa oleva arvo
        
        comboBox.setValue(getItem());
        comboBox.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);

        comboBox.focusedProperty().addListener(new ChangeListener<Boolean>(){
                @Override
                public void changed(ObservableValue<? extends Boolean> arg0, 
                    Boolean arg1, Boolean arg2) {
                        if (!arg2) {
                            commitEdit(comboBox.getSelectionModel().getSelectedItem());
                        }
                }
            });
    }
    
    private void comboBoxConverter(ComboBox<Joukkue> comboBox) {
    
        comboBox.setCellFactory((c) -> {
            return new ListCell<Joukkue>() {
                @Override
                protected void updateItem(Joukkue item, boolean empty) {
                    super.updateItem(item, empty);

                    if (item == null || empty) {
                        setText(null);
                    } else {
                        // tää on se mikä valikkoon tulee

                        setText(item.toString());
                    }
                }
            };
        });
    }

    private Joukkue getString() {
        return getItem() == null ? new Joukkue("") : getItem();
    }

}
