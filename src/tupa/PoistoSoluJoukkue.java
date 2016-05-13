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
public class PoistoSoluJoukkue extends TableCell<Record, Boolean> {
        final Button cellButton = new Button("X");
          
        
         PoistoSoluJoukkue(){
            
        }   
           
        PoistoSoluJoukkue(ObservableList<Joukkue> data, Varmistaja varmistaja){
            
        	 cellButton.setId("button-poisto");  
            cellButton.setOnAction(new EventHandler<ActionEvent>(){

                @Override
                public void handle(ActionEvent t) {
                 
                	Joukkue joukkue = (Joukkue) PoistoSoluJoukkue.this.getTableView().getItems().get(PoistoSoluJoukkue.this.getIndex());
                	
                	varmistaja.annaJoukkueenPoistoVarmistus(joukkue);
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