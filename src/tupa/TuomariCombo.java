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
public class TuomariCombo extends TableCell<Ottelu, Tuomari>{
    
private ComboBox<Tuomari> comboBox;
private Sarja sarja;

    public TuomariCombo() {
    }
    
    public TuomariCombo(Sarja sarja) {
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

      setText(getTyp().toString());
        setGraphic(null);
    }

    @Override
    public void updateItem(Tuomari item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {
            setText(null);
            setGraphic(null);
        } else if (isEditing()) {
            if (comboBox != null) {
                comboBox.setValue(getTyp());
            }
            setText("www");
            setGraphic(comboBox);
        } else if(item != null) {
            //näytetään voimassa oleva arvo
            setText(getTyp().toString());
            setGraphic(null);
        }
    }

    private void createComboBox() {
  ComboBox<Tuomari> tuomari = new ComboBox();
       List<Tuomari> tuomarilista1 = new ArrayList<Tuomari>();

        for (int i = 0; i < sarja.annaTurnaus().annaTuomarit().size(); i++) {
            tuomarilista1.add(sarja.annaTurnaus().annaTuomarit().get(i));

        }
        ObservableList tuomarit = FXCollections.observableList(tuomarilista1);
        tuomari.setItems(tuomarit);

        comboBox = tuomari;
        comboBoxConverter(comboBox);
        
        
        //eka arvo valikossa on olemassa oleva arvo
         System.out.println(getTyp());
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
    
    private void comboBoxConverter(ComboBox<Tuomari> comboBox) {
        
        comboBox.setCellFactory((c) -> {
            return new ListCell<Tuomari>() {
                @Override
                protected void updateItem(Tuomari item, boolean empty) {
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

    private Tuomari getTyp() {
        return getItem() == null ? new Tuomari("") : getItem();
    }

}
