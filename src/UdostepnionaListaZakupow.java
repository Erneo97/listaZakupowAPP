import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UdostepnionaListaZakupow extends  ObslugaListZakupow implements Serializable{
    @Serial
    private static final long serialVersionUID = 1L;

    @Override
    String getObecnaListaZakupow() { return this.obecnaListaZakupow;}

    final public List<String> getNazwyListZakupow() {
        return null;
    }

    UdostepnionaListaZakupow(String nazwa, List<Produkt> produktyListy  ) {
        obecnaListaZakupow = "udostepniona_" + nazwa;
        this.produktyListy = produktyListy;

    }

    @Override
    public void zmienListeZakupow(String nazwaListy) {
       return;

    }

    public boolean skasujWybranaListeZakupow(String nazwa) {
        if ( nazwa == obecnaListaZakupow ) {
            // TODO: tu konczysz polaczenie
            return true;
        }

        return false;
    }

    @Override
    public boolean usunPorduktPoNazwie(String nazwaProduktu) {
        for (int i = 0; i < produktyListy.size() ; i++) {
            if(Objects.equals(nazwaProduktu, produktyListy.get(i).nazwa)) {
                produktyListy.remove(i);
                return true;
            }
        }
        return false;
    }
    @Override
    public void dodajProduktdoListyZakupow(Produkt produkt ) {
        // TODO: Ładnie poproś o dana modyfikacja
    }


    public static void main(String[] args) {

    }




}
