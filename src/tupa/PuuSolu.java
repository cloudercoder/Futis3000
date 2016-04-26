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
	private TUPA ikkuna;
	
	PuuSolu (TUPA ikkuna)
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
		MenuItem item1 = new MenuItem ("Lisää");
		menu.getItems ().add (item1);
		
                MenuItem item2 = new MenuItem ("Poista");
		menu.getItems ().add (item1);
                MenuItem item3 = new MenuItem ("Vie");
		menu.getItems ().add (item1);
                MenuItem item4 = new MenuItem ("Vie");
		menu.getItems ().add (item1);
                
                // tähän mitä tapahtuu
//		juureksi.setOnAction ((EventHandler<ActionEvent>) ikkuna);
		
		return menu;
	}
}
