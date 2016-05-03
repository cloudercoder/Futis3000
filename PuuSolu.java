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
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeCell;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;

public class PuuSolu extends TreeCell<Kohde>
{
	private Tupa ikkuna;
	
	PuuSolu (Tupa ikkuna)
	{
		this.ikkuna = ikkuna;
	}
	
	@Override
	public void updateItem (Kohde item, boolean empty)
	{
		super.updateItem (item, empty);

		if (empty)
		{
			setText (null);
			setGraphic (null);
			setContextMenu (null);
		}
		else
		{
			setText (item.toString ());
			setGraphic (getTreeItem ().getGraphic ());
                        
                        //treeitemien sisältö pitää tutkia
                        
                        //käyttäjäoikeudet tulee tutkia
//			
//			if (item.onHakemisto ())
//			{
				ContextMenu valikko = rakennaHakemistoValikko ();
				setContextMenu (valikko);
//			}
//			else setContextMenu (null);
		}
	}
	
	private ContextMenu rakennaHakemistoValikko ()
	{
		ContextMenu menu = new ContextMenu ();
		
                //uusi item, jonka nimeä voi muuttaa
                MenuItem item1 = new MenuItem ("Lisää");
		menu.getItems ().add (item1);
		
                //item muuttuu klikkattavaksi
                MenuItem item2 = new MenuItem ("Muokkaa");
		menu.getItems ().add (item2);
                
                //tiedostoon tallennus
                MenuItem item3 = new MenuItem ("Vie");
		menu.getItems ().add (item3);
                
                //tiedostosta luku, VAIN PÄÄITEMIIN
                MenuItem item4 = new MenuItem ("Tuo");
		menu.getItems ().add (item4);
                
                //TÄHÄN TULEE TEHDÄ VARMISTUS!!!!!!
                MenuItem item5 = new MenuItem ("Poista");
		menu.getItems ().add (item2);

                
                // tähän mitä tapahtuu
//		juureksi.setOnAction ((EventHandler<ActionEvent>) ikkuna);
		
		return menu;
	}
}
