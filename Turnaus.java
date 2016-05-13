package tupa;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Marianne
 */
public class Turnaus extends Kohde{
    
    private static int turLaskuri;
    private List<Sarja> sarjat = new ArrayList<>();
    private List<Tuomari> tuomarit = new ArrayList<>();
    
    Turnaus(){
        super("Uusi turnaus");
    }
    
    public int annaMaara(){
        return turLaskuri;
    }
    
    public List<Sarja> annaSarjat(){
        return sarjat;
    }
    
    public List<Tuomari> annaTuomarit(){
        return tuomarit;
    }
}
