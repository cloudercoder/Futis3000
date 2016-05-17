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
public class KloCombo2 extends TableCell<Ottelu, String> {

 private ComboBox<String> comboBox;


    public KloCombo2() {
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
    public void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {
            setText(null);
            setGraphic(null);
        } else if (isEditing()) {
            if (comboBox != null) {
                comboBox.setValue(getString());
            }
             setText(null);
            setGraphic(comboBox);
        } else {
            //näytetään voimassa oleva arvo
            setText(getString());
            setGraphic(null);
        }
    }

    private void createComboBox() {


      
        
           ComboBox<String> kellominuutit = new ComboBox();
        List<String> minuuttilista = new ArrayList();

        for (int i = 0; i < 10; i++) {

            minuuttilista.add("0" + i);
        }
        for (int i = 10; i < 60; i++) {

            minuuttilista.add("" + i);
        }

        ObservableList minuutit = FXCollections.observableList(minuuttilista);
        kellominuutit.setItems(minuutit);
        
          comboBox = kellominuutit;
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
    
    private void comboBoxConverter(ComboBox<String> comboBox) {

        comboBox.setCellFactory((c) -> {
            return new ListCell<String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
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

    private String getString() {
        return getItem() == null ? new String("") : getItem();
    }

}
