/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
 * @author Omistaja
 */
class ComboBoxEditingCell extends TableCell<Ottelu, Integer> {

    private ComboBox<Integer> comboBox;


    public ComboBoxEditingCell() {
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
    public void updateItem(Integer item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {
            setText(null);
            setGraphic(null);
        } else if (isEditing()) {
            if (comboBox != null) {
                comboBox.setValue(0000);
            }
            setText("www");
            setGraphic(comboBox);
        } else {
            //näytetään voimassa oleva arvo
            setText(item.toString());
            setGraphic(null);
        }
    }

    private void createComboBox() {

        ComboBox<Integer> kierros = new ComboBox();
        List<Integer> kierroslista = new ArrayList();

        for (int i = 0; i < 99; i++) {
            kierroslista.add(i);
        }

        ObservableList kierrokset = FXCollections.observableList(kierroslista);
        kierros.setItems(kierrokset);

        comboBox = kierros;
        comboBoxConverter(comboBox);
        //eka arvo valikossa on olemassa oleva arvo
         System.out.println(getTyp());
        comboBox.setValue(getItem());
        comboBox.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
//        comboBox.setOnAction((e) -> {
//            System.out.println("Committed: " + comboBox.getSelectionModel().getSelectedItem());
//            commitEdit(comboBox.getSelectionModel().getSelectedItem());
//        });
         
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
    
    private void comboBoxConverter(ComboBox<Integer> comboBox) {
        // Define rendering of the list of values in ComboBox drop down. 
        comboBox.setCellFactory((c) -> {
            return new ListCell<Integer>() {
                @Override
                protected void updateItem(Integer item, boolean empty) {
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

    private Integer getTyp() {
        return getItem() == null ? new Integer("") : getItem();
    }

}
