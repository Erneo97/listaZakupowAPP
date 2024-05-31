import java.io.Serial;
import java.io.Serializable;

public class Produkt implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    public String nazwa;
    public Ilosc ilosc;

    Produkt(String nazwa, Ilosc ilosc) {
        this.ilosc = ilosc;
        this.nazwa = nazwa;
    }
    boolean scalPodobneProdukty(Produkt produkt) {
        return this.ilosc.scalIlosc(produkt.ilosc);
    }
    @Override
    public String toString() {
       StringBuilder nazwaKustomizowania = new StringBuilder(nazwa);
       while ( nazwaKustomizowania.length() < 20) {
           nazwaKustomizowania.append(" ");
       }
        return nazwaKustomizowania.toString() + " " + ilosc;
    }
}
