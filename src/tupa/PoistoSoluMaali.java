package tupa;

import com.sun.prism.impl.Disposer.Record;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;

/**
 *
 * @author Marianne
 */
public class PoistoSoluMaali extends TableCell<Record, Boolean> {
        final Button cellButton = new Button("X");
          
        
         PoistoSoluMaali(){
            
        }   
           
        PoistoSoluMaali(ObservableList<Maali> data){
            
        	 cellButton.setId("button-poisto");  
            cellButton.setOnAction(new EventHandler<ActionEvent>(){

                @Override
                public void handle(ActionEvent t) {
                 
                	Maali maali = (Maali) PoistoSoluMaali.this.getTableView().getItems().get(PoistoSoluMaali.this.getIndex());
                	
                     
                        maali.annaMaalinTekija().annaMaaliLista().remove(maali);
                	 maali.annaSyottaja().annaMaaliLista().remove(maali);
                         maali.annaOttelu().annaMaalit().remove(maali);
                         
                }
            });
        }
  

        @Override
        protected void updateItem(Boolean t, boolean empty) {
            super.updateItem(t, empty);
            if(!empty){
                setGraphic(cellButton);
            }
        }
    }