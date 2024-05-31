import java.io.Serial;
import java.io.Serializable;

public class Ilosc implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    public enum Jednostka{LITR, SZTUKI, KG, GRAM, DLUGOSC}
    private double ilosc;
    final private Jednostka jednostka;

    boolean scalIlosc(Ilosc dodatek) {
        if( dodatek.jednostka != this.jednostka)
            return false;
        else if( this.jednostka == Jednostka.KG && dodatek.jednostka == Jednostka.GRAM) {
            this.ilosc += dodatek.ilosc/1000;
            return true;
        }
        else if( this.jednostka == Jednostka.GRAM && dodatek.jednostka == Jednostka.KG) {
            this.ilosc += dodatek.ilosc * 1000;
            return true;
        }
        this.ilosc += dodatek.ilosc;
        return true;
    }
    public Ilosc(double ilosc, Jednostka jednostka) {
        ilosc = ilosc < 0 ? 0 : ilosc;
        this.ilosc = ilosc;
        this.jednostka = jednostka;
    }

    public double getIlosc() {
        return ilosc;
    }

    public void setIlosc(float ilosc) {
        if (ilosc < 0 )
            return;
        this.ilosc = ilosc;
    }

    public void increment() {
        ilosc++;
    }
    public void decrement() {
        ilosc--;
    }


    public String toString() {
        return (jednostka == Jednostka.SZTUKI || jednostka == Jednostka.GRAM
                ?  String.format("%d", (int) ilosc) :  ilosc) + " " + getJednostkaJakoString();
    }

    public String getJednostkaJakoString() {
        String ret;
        switch (jednostka) {
            case LITR -> ret = "l.";
            case KG -> ret = "kg";
            case GRAM ->  ret ="g";
            case DLUGOSC -> ret = "m";
            default ->  ret = "szt.";
        }
        return ret;
    }
    public Jednostka getJednostka() {return this.jednostka;}



}
