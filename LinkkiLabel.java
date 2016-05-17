/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tupa;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author Omistaja
 */
public class LinkkiLabel extends Label  implements EventHandler<MouseEvent>
{
    private String nimi;
    private Tupa ikkuna;
    private PaaNakyma nakyma;
    
    LinkkiLabel(){
        this.getStyleClass().add("linkkilabel");
       
    }
    
    LinkkiLabel(Tupa ikkuna){
        this.getStyleClass().add("linkkilabel");
       this.ikkuna = ikkuna;
       nakyma = new PaaNakyma(ikkuna);
    }
    public void linkkiaKlikattu(){
        setOnMouseClicked(this);
    }
      		@Override
    public void handle (MouseEvent e)
	{
                    nimi = this.getText();
		
                  if(nimi.equals("Etusivu")){
                    nakyma.luoEtusivu();
                      return;
                  }

        }

  
}
