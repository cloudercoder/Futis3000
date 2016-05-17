package tupa;

/**
 *
 * @author Marianne
 */
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TreeCell;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;

public class PuuSolu extends TreeCell<Kohde> {

    private Tupa ikkuna;

    PuuSolu(Tupa ikkuna) {
        this.ikkuna = ikkuna;
    }

    @Override
    public void updateItem(Kohde item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {
            setText(null);
            setGraphic(null);
            setContextMenu(null);
        } else {
            setText(item.toString());
            setGraphic(getTreeItem().getGraphic());

            //treeitemien sisältö pitää tutkia
            //käyttäjäoikeudet tulee tutkia
//			
            boolean onJuuri = false;

            if (ikkuna.annaRootSarjat().getValue().equals(item) || ikkuna.annaRootTuomarit().getValue().equals(item)) {
                onJuuri = true;
            }

            if (!onJuuri) {
                ContextMenu valikko = rakennaSolmuValikko(item);
                setContextMenu(valikko);
            } else {
                ContextMenu valikko = rakennaJuuriValikko(item);
                setContextMenu(valikko);
            }
        }
    }

    private ContextMenu rakennaSolmuValikko(Kohde item) {
        ContextMenu menu = new ContextMenu();

        MenuItem item2 = new MenuItem("Muokkaa");
        menu.getItems().add(item2);

        MenuItem item5 = new MenuItem("Poista");
        menu.getItems().add(item5);

        Varmistaja varmistaja = new Varmistaja(ikkuna, ikkuna.annaPaaNakyma());
        
        
          item5.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    varmistaja.annaPoistoVarmistus(item);

                }
            });

        if (item instanceof Sarja) {
            item2.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    Sarja sarja =(Sarja) item;
                    SarjaNakyma sarjanakyma = new SarjaNakyma();
                    sarjanakyma = ikkuna.annaPaaNakyma().annaSarjanakyma();
                    sarjanakyma.luoSarjaMuokkaus(sarja);

                }
            });
        }
          if (item instanceof Tuomari) {
            item2.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    Tuomari tuomari = (Tuomari) item;
                          TuomariNakyma tuomarinakyma = new TuomariNakyma();
                    tuomarinakyma = ikkuna.annaPaaNakyma().annaTuomarinakyma();
                    tuomarinakyma.luoTuomariMuokkaus(tuomari);

                }
            });
        }
        
        
        
        return menu;
    }

    private ContextMenu rakennaJuuriValikko(Kohde item) {
        ContextMenu menu = new ContextMenu();

        MenuItem item1 = new MenuItem("Lisää");
        menu.getItems().add(item1);

        //tiedostoon tallennus
        MenuItem item3 = new MenuItem("Vie");
        menu.getItems().add(item3);

        //tiedostosta luku, VAIN PÄÄITEMIIN
        MenuItem item4 = new MenuItem("Tuo");
        menu.getItems().add(item4);
        if (item instanceof Sarja) {
            item1.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    SarjaNakyma sarjanakyma = new SarjaNakyma();
                    sarjanakyma = ikkuna.annaPaaNakyma().annaSarjanakyma();
                    sarjanakyma.luoSarjanLisaysSivu();

                }
            });

            item3.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    //TIETOKANTA --> TIEDOSTO
                }
            });
            item4.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    //TIEDOSTO --> TIETOKANTA
                }
            });

        }
        if (item instanceof Tuomari) {
            item1.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    TuomariNakyma tuomarinakyma = new TuomariNakyma();
                    tuomarinakyma = ikkuna.annaPaaNakyma().annaTuomarinakyma();
                    tuomarinakyma.luoTuomarinLisaysSivu();

                }
            });

            item3.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    //TIETOKANTA --> TIEDOSTO
                }
            });
            item4.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    //TIEDOSTO --> TIETOKANTA
                }
            });

        }

        return menu;
    }
}
