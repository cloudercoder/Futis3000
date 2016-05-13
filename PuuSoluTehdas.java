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
import javafx.scene.control.TreeView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeCell;
import javafx.util.Callback;

public class PuuSoluTehdas implements Callback<TreeView<Kohde>, TreeCell<Kohde>>
{
	private Tupa ikkuna;
	
	PuuSoluTehdas (Tupa ikkuna)
	{
		this.ikkuna = ikkuna;
	}
	
	@Override
	public TreeCell<Kohde> call (TreeView<Kohde> p)
	{
		return new PuuSolu (ikkuna);
	}
}
