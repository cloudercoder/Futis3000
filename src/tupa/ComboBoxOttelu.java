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
class ComboBoxOttelu extends TableCell<Ottelu, Integer> {

    private ComboBox<Integer> comboBox;


    public ComboBoxOttelu() {
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
    
    private void comboBoxConverter(ComboBox<Integer> comboBox) {

        comboBox.setCellFactory((c) -> {
            return new ListCell<Integer>() {
                @Override
                protected void updateItem(Integer item, boolean empty) {
                    super.updateItem(item, empty);

                    if (item == null || empty) {
                        setText(null);
                    } else {
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
