import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


class ObslugaListZakupow {
    protected List<Produkt> produktyListy = new ArrayList<>();
    private final List<String> nazwyListZakupow = new ArrayList<>();
    private final String sciezkaPlikow ="src/pliki/";
    protected String obecnaListaZakupow;

    String getObecnaListaZakupow() { return this.obecnaListaZakupow;}

    public List<String> getNazwyListZakupow() {
        return nazwyListZakupow;
    }

    public List<Produkt> getProduktyListy() {return produktyListy;}



    public void zmienListeZakupow(String nazwaListy) throws IOException {
        boolean modyfikowac = false;
        for (int i = 0; i < nazwyListZakupow.size(); i++) {
            if(!nazwyListZakupow.get(i).equals(nazwaListy)) {
                modyfikowac = true;
            }
        }
        if( modyfikowac ) {
            nazwyListZakupow.add(nazwaListy);
            System.out.println("Dodaje : " + nazwaListy);
            zapiszNazwyListZakopow();
        }
        pobierzListeZakupow(nazwaListy);

    }

    public boolean skasujWybranaListeZakupow(String nazwa) throws IOException {
        for (int i = 0; i < nazwyListZakupow.size(); i++) {
            if( Objects.equals(nazwyListZakupow.get(i), nazwa)) {
                usunIstniejacyPlik(nazwa);
                nazwyListZakupow.remove(i);
                zapiszNazwyListZakopow();
                return true;
            }
        }
        return false;
    }


    public boolean usunPorduktPoNazwie(String nazwaProduktu) {
        for (int i = 0; i < produktyListy.size() ; i++) {
            if(Objects.equals(nazwaProduktu, produktyListy.get(i).nazwa)) {
                produktyListy.remove(i);
                return true;
            }
        }
        return false;
    }

    public void dodajProduktdoListyZakupow(Produkt produkt ) {
        for (Produkt value : produktyListy) {
            if (Objects.equals(produkt.nazwa, value.nazwa)) {
                value.scalPodobneProdukty(produkt);
                return;
            }
        }
        produktyListy.add(produkt);
    }
    private void daneTestowe() {
        produktyListy.add(new Produkt("Mleko", new Ilosc(2, Ilosc.Jednostka.LITR)));
        produktyListy.add(new Produkt("Kawa", new Ilosc(1, Ilosc.Jednostka.KG)));
        produktyListy.add(new Produkt("Liczi", new Ilosc(250, Ilosc.Jednostka.GRAM)));
        produktyListy.add(new Produkt("Ziemiaki", new Ilosc(2.5, Ilosc.Jednostka.KG)));
    }

    public static void main(String[] args) throws IOException {
        ObslugaListZakupow obsluga = new ObslugaListZakupow();
        /*
            System.out.println("Current Working Directory: " + System.getProperty("user.dir"));


        System.out.println(obsluga.produktyListy);
        obsluga.daneTestowe();
        System.out.println(obsluga.produktyListy);

        obsluga.pobieranieNazwListZapupow();
        obsluga.zapiszListeZakupow(obsluga.nazwyListZakupow.get(1));
        obsluga.pobierzListeZakupow(obsluga.nazwyListZakupow.get(1));

        obsluga.zapiszListeZakupow(obsluga.nazwyListZakupow.get(0));
        obsluga.pobierzListeZakupow(obsluga.nazwyListZakupow.get(0));

        System.out.println(obsluga.produktyListy);


        */
        obsluga.obecnaListaZakupow = "lista1.dat";
        System.out.println(obsluga.produktyListy);
        obsluga.dodajProduktdoListyZakupow( new Produkt("testowy", new Ilosc(2, Ilosc.Jednostka.GRAM)));
        obsluga.pobierzListeZakupow("lista1.dat");

        System.out.println(obsluga.produktyListy);
    }

    protected void pobieranieNazwListZapupow( ) throws IOException {
        nazwyListZakupow.clear();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(sciezkaPlikow + "nazwaPlikow.txt" ));
            String line;
            while ((line = br.readLine()) != null) {
                nazwyListZakupow.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if( br != null )
                br.close();
        }
        System.out.println(nazwyListZakupow);
    }
    protected void zapiszNazwyListZakopow() throws IOException {
        BufferedWriter bufferedWriter = null ;
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(sciezkaPlikow + "nazwaPlikow.txt" ));
            for (String nazwa : nazwyListZakupow) {
                System.out.println(nazwa);
                bufferedWriter.write(nazwa);
                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        finally {
            if(  bufferedWriter!= null)
                bufferedWriter.close();
        }

    }
    protected boolean dodajNazweListyZakupow(String nazwa) {
        for (int i = 0; i < nazwyListZakupow.size(); i++ ) {
            if(nazwyListZakupow.get(i).equals(nazwa)) {
                obecnaListaZakupow = nazwa;
                return false;
            }
        }
        if( nazwa.split("\\.").length > 2) {
            return false;
        }

        nazwyListZakupow.add(nazwa);
        obecnaListaZakupow = nazwa;
        return true;
    }
    @SuppressWarnings("unchecked")
    protected boolean pobierzListeZakupow(String nazwaListy) throws IOException {
        this.obecnaListaZakupow = nazwaListy;

        if( !(new File(sciezkaPlikow + nazwaListy).exists())   ) {

            stworzPlikONazwie( nazwaListy);
        }
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(sciezkaPlikow + nazwaListy));
            produktyListy.clear();
            produktyListy = (List<Produkt>) ois.readObject();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        finally {
            if( ois != null )
                ois.close();
        }
        return true;
    }
    protected boolean czyPlikONazwieIstnieje(String nazwa) {
        return (new File(sciezkaPlikow + nazwa).exists());
    }
    protected boolean zapiszListeZakupow(String nazwa) throws IOException {
        if( !(new File(sciezkaPlikow + nazwa).exists()) && !stworzPlikONazwie(nazwa)) {
            return false;
        }
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(new FileOutputStream(sciezkaPlikow + nazwa));
            oos.writeObject(produktyListy);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        finally {
            if( oos != null )
                oos.close();
        }
        zapiszNazwyListZakopow();
        return true;
    }

    protected boolean stworzPlikONazwie(String nazwa) throws IOException {
        if( nazwa.split("\\.").length > 2)
            return false;

        File plik = new File(sciezkaPlikow + nazwa);
        ObjectOutputStream oos = null;
        try {
            boolean ret = plik.createNewFile();
            List<Produkt> czysta = new ArrayList<>();
             oos = new ObjectOutputStream(new FileOutputStream(sciezkaPlikow + nazwa));
            oos.writeObject(czysta);

            return ret;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        finally {
            if( oos != null)
                oos.close();
        }
    }

    protected boolean usunIstniejacyPlik(String nazwa) {
        File plikDoUsuniecia = new File(sciezkaPlikow + nazwa);
        System.out.println("Plik " + nazwa + " przekazany do skasowania");
        if( plikDoUsuniecia.exists() && plikDoUsuniecia.delete() ) {
            System.out.println("Plik " + nazwa + " został usunięty");
            return  true;
         }
        return false;
    }



}
