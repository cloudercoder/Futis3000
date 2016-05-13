package tupa;

/**
 *
 * @author Marianne
 */
public class Tarkistaja {

    private Tupa ikkuna;
    private Tiedottaja tiedottaja;

    Tarkistaja() {

    }

    Tarkistaja(Tupa ikkuna) {
        this.ikkuna = ikkuna;
        this.tiedottaja = new Tiedottaja(ikkuna);
    }

    public boolean nimiOK(String nimi) {
        if (onTyhja(nimi)) {
            return false;
        } else if (nimi.length() > 64) {
            tiedottaja.annaVaroitus("Sekä etu- että sukunimi saa sisältää korkeintaan 64 merkkiä.");
            return false;
        } else {

            for (char c : nimi.toCharArray()) {

                if (!Character.isLetter(c)) {
                    if (!Character.toString(c).equals("-")) {

                        tiedottaja.annaVaroitus("Sekä etu- että sukunimi saa sisältää vain kirjaimia ja tavuviivoja.");
                        return false;
                    }

                }
            }
            return true;
        }
    }

    public boolean onTyhja(String merkkijono) {
        if (merkkijono.trim().isEmpty()) {
            tiedottaja.annaVaroitus("Et voi antaa tyhjää kenttää.");
            return true;
        } else {
            return false;
        }
    }

    public boolean pelinumeroOK(Integer nro, Joukkue joukkue) {
        for (int i = 0; i < joukkue.annaPelaajat().size(); i++) {

            if (joukkue.annaPelaajat().get(i).annaPelinumero() == nro) {

                tiedottaja.annaVaroitus("Valitsemasi pelinumero on jo käytössä toisella joukkueen pelaajalla.");
                return false;
            }

        }
        return true;
    }

    public boolean kotijoukkueOK(Joukkue joukkue, Ottelu ottelu) {

        if (joukkue == null) {
            tiedottaja.annaVaroitus("Et voi antaa tyhjää kenttää.");
            return false;
        } else if (ottelu.annaVierasjoukkue().equals(joukkue)) {

            tiedottaja.annaVaroitus("Koti- ja vierasjoukkue eivät voi olla samoja.");
            return false;
        } else {
            return true;
        }
    }

    public boolean vierasjoukkueOK(Joukkue joukkue, Ottelu ottelu) {

        if (joukkue == null) {
            tiedottaja.annaVaroitus("Et voi antaa tyhjää kenttää.");
            return false;
        } else if (ottelu.annaKotijoukkue().equals(joukkue)) {

            tiedottaja.annaVaroitus("Koti- ja vierasjoukkue eivät voi olla samoja.");
            return false;
        } else {
            return true;
        }
    }

    public boolean erotuomariOK(Tuomari tuomari, Ottelu ottelu) {
        if (tuomari == null) {
            tiedottaja.annaVaroitus("Valitse sekä koti- että vierasjoukkue.");
            return false;
        }
        else{
            if(ottelu.annaAvustava1().equals(tuomari)){
                tiedottaja.annaVaroitus("Sama henkilö ei voi olla kuin yhdessä tuomarin roolissa.");
                return false;
            }
            else if(ottelu.annaAvustava2().equals(tuomari)){
                tiedottaja.annaVaroitus("Sama henkilö ei voi olla kuin yhdessä tuomarin roolissa.");
                return false;
            } 
        }
        return true;
    }

    public boolean avustava1OK(Tuomari tuomari, Ottelu ottelu) {
             if (tuomari == null) {
            tiedottaja.annaVaroitus("Valitse sekä koti- että vierasjoukkue.");
            return false;
        }
        else{
            if(ottelu.annaErotuomari().equals(tuomari)){
                tiedottaja.annaVaroitus("Sama henkilö ei voi olla kuin yhdessä tuomarin roolissa.");
                return false;
            }
            else if(ottelu.annaAvustava2().equals(tuomari)){
                tiedottaja.annaVaroitus("Sama henkilö ei voi olla kuin yhdessä tuomarin roolissa.");
                return false;
            } 
        }
        return true;
    }

    public boolean avustava2OK(Tuomari tuomari, Ottelu ottelu) {
             if (tuomari == null) {
            tiedottaja.annaVaroitus("Valitse sekä koti- että vierasjoukkue.");
            return false;
        }
        else{
            if(ottelu.annaErotuomari().equals(tuomari)){
                tiedottaja.annaVaroitus("Sama henkilö ei voi olla kuin yhdessä tuomarin roolissa.");
                return false;
            }
            else if(ottelu.annaAvustava1().equals(tuomari)){
                tiedottaja.annaVaroitus("Sama henkilö ei voi olla kuin yhdessä tuomarin roolissa.");
                return false;
            } 
        }
        return true;
    }
}
