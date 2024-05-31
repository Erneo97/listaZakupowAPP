import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class InterfejsUzytkownika extends JFrame {
    private final int rozmiarCzcionki = 20;
    private final int rozmiarCzcionkiPrzyciski = 16;

    ObslugaListZakupow obsluga;
    private final JPanel listaWidokow;
    final private CardLayout cardLayout;
    private final List<String> listaNazwWidokow = new ArrayList<>();

    private String wybranaListaZakupow;
    DefaultListModel<String> listyZakopowDostepne = new DefaultListModel<>();


    private String wybranyProduktZListy;
    DefaultListModel<String> listyProduktowZListyDostepne = new DefaultListModel<>();


    InterfejsUzytkownika(ObslugaListZakupow obsluga) throws IOException {
        this.obsluga = obsluga;
        obsluga.pobieranieNazwListZapupow();

        cardLayout = new CardLayout();
        listaWidokow = new JPanel(cardLayout);

        setTitle("APP_MK");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);


        utworzPanelGlowny();
       // wybranaListaZakupow = "lista1.dat";
        utworzPanelListyZakupow();

        pokazPanelGlowy();

        getContentPane().add(listaWidokow);
        pack();
        setLocationRelativeTo(null);
    }

    private void utworzPanelGlowny() {
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Tworzenie panelu, który będzie zawierał dwa panele obok siebie
        JPanel topPanel = new JPanel(new GridLayout(1, 2));

       JPanel leftPanel = utworzLewaCzescWidokuGlownego(mainPanel);

        JPanel rightPanel =utworzPrawaCzescWidokuGlownego();

        // Dodawanie paneli bocznych do panelu topPanel
        topPanel.add(leftPanel);
        topPanel.add(rightPanel);


        // Dodawanie panelu topPanel do głównego panela
        mainPanel.add(topPanel, BorderLayout.CENTER);



    }
    private JPanel utworzLewaCzescWidokuGlownego(JPanel mainPanel) {
        // Tworzenie paneli bocznych (obok siebie)
        JPanel leftPanel = new JPanel();


        leftPanel.setBackground(Color.lightGray); // TODO: zmienic bo debugowaniu
        leftPanel.setPreferredSize(new Dimension(200, 300));

        JLabel twojaListaList = new JLabel("Twoje listy zakupów");

        twojaListaList.setFont(new Font("Arial", Font.BOLD, rozmiarCzcionki));
        leftPanel.add(twojaListaList, BorderLayout.CENTER);


        przeladujListyZakopowDostepne();
        JList<String>  listyZakupow = new JList<>(listyZakopowDostepne);
        listyZakupow.setFont(new Font("Arial", Font.BOLD, rozmiarCzcionki-2));

        listyZakupow.setPreferredSize(new Dimension(300, 350));
        listyZakupow.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    wybranaListaZakupow = listyZakupow.getSelectedValue();
                    aktualizujNazweWybranejListyLabel(wybranaListaZakupow);
                    System.out.println(wybranaListaZakupow);
                }
            }
        });
        JScrollPane skrolPane = new JScrollPane(listyZakupow);
        leftPanel.add(skrolPane,BorderLayout.CENTER);
        setLocationRelativeTo(null);

        listaNazwWidokow.add("glownyWidok");
        listaWidokow.add(mainPanel, listaNazwWidokow.get(listaNazwWidokow.size()-1));

        return leftPanel;
    }
    private void przeladujListyZakopowDostepne() {
        listyZakopowDostepne.clear();
        for (String nazwaListy : obsluga.getNazwyListZakupow()) {
            listyZakopowDostepne.addElement(nazwaListy);
        }
    }
    private JPanel utworzPrawaCzescWidokuGlownego() {
        JPanel rightPanel = new JPanel(new GridLayout(4, 2));
        rightPanel.setBackground(Color.LIGHT_GRAY);// TODO: zmienic bo debugowaniu

        JLabel nazwaListy = new JLabel("  Wybrana lista: ");
        nazwaListy.setFont(new Font("Arial", Font.BOLD, rozmiarCzcionki));
        rightPanel.add(nazwaListy);


        wybranaListaZakupowLabel = new JLabel("Brak");
        wybranaListaZakupowLabel.setFont(new Font("Arial", Font.BOLD, rozmiarCzcionki));

        rightPanel.add(wybranaListaZakupowLabel, BorderLayout.CENTER);
                    //TODO: obsługa przycisków


        JButton utworzNowaListe = new JButton("Utworz nową listę");
        utworzNowaListe.setFont(new Font("Arial", Font.ITALIC, rozmiarCzcionkiPrzyciski));
        rightPanel.add(utworzNowaListe, BorderLayout.SOUTH);
        utworzNowaListe.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Dodajmy nowa liste");
                wybranaListaZakupow = "nowaLista.dat";
                try {
                    obsluga.stworzPlikONazwie(wybranaListaZakupow);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                aktualizujNazweWybranejListyLabel(wybranaListaZakupow);
                try {
                    aktualizujListyProduktowZListyDostepne();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                textFieldNazwaListyModyfikacja.setText(wybranaListaZakupow);
                cardLayout.show(listaWidokow, listaNazwWidokow.get(1));
                edytujWidocznoscPrzyciskowUdostepnianai(false);
            }
        });

        JButton skasujWybranaListe = new JButton("Skasuj wybraną listę");
        skasujWybranaListe.setFont(new Font("Arial", Font.ITALIC, rozmiarCzcionkiPrzyciski));
        rightPanel.add(skasujWybranaListe);
        skasujWybranaListe.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if( wybranaListaZakupow == null ) {
                    JOptionPane.showMessageDialog(null, "Należy wybrać listę przed skasowaniem");
                }
                else {
                    System.out.println("Skasuj ta liste");
                    try {
                        obsluga.skasujWybranaListeZakupow(wybranaListaZakupow);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }

                    przeladujListyZakopowDostepne();
                    wybranaListaZakupow = null;
                    aktualizujNazweWybranejListyLabel(null);
                }

            }
        });

        JButton modyfikuj = new JButton("Przeglądaj / Modyfikuj");  // TODO: Jak starczy chęci to zmodyfikować go na klika pól
        modyfikuj.setFont(new Font("Arial", Font.ITALIC, rozmiarCzcionkiPrzyciski));
        rightPanel.add(modyfikuj);
        modyfikuj.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if( wybranaListaZakupow != null) {
                    try {
                        aktualizujListyProduktowZListyDostepne();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    textFieldNazwaListyModyfikacja.setText(wybranaListaZakupow);
                    cardLayout.show(listaWidokow, listaNazwWidokow.get(1));
                    edytujWidocznoscPrzyciskowUdostepnianai(true);
                }
                else {
                    JOptionPane.showMessageDialog(null, "Wybież listę któą chcesz zmodyfikować");
                }

            }
        });


        return rightPanel;
    }

    private JLabel wybranaListaZakupowLabel;
    private void aktualizujNazweWybranejListyLabel(String nowyTekst) {
        wybranaListaZakupowLabel.setText(nowyTekst == null ? "Brak" : nowyTekst);
    }
    JTextField textFieldNazwaListyModyfikacja;
    private void utworzPanelListyZakupow() {
        JPanel mainPanel = new JPanel(new GridLayout(4,1));

        JPanel tekstowy = new JPanel();
        JLabel nazwa = new JLabel("Nazwa Listy: ");
        nazwa.setFont(new Font("Arial", Font.BOLD, rozmiarCzcionki));
        tekstowy.add(nazwa);

         textFieldNazwaListyModyfikacja = new JTextField(prawidlowyTekstDoWyswietlenia(), 20);
         // tutaj
        textFieldNazwaListyModyfikacja.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                textFieldNazwaListyModyfikacja.setText("");
            }

            @Override
            public void focusLost(FocusEvent e) {
                String wprowadzonyTekst = textFieldNazwaListyModyfikacja.getText();
                wybranaListaZakupow = wprowadzonyTekst + ".dat";
                try {
                    obsluga.zmienListeZakupow(wybranaListaZakupow);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                textFieldNazwaListyModyfikacja.setText(wybranaListaZakupow);
                System.out.println(wybranaListaZakupow);
                przeladujListyZakopowDostepne();
            }
        });
        tekstowy.add(textFieldNazwaListyModyfikacja);
        mainPanel.add(tekstowy);

        dodajJScrolListyProduktow(mainPanel);
        dodajPokazanieProduktuIOpcjaUsuniecia(mainPanel);


        JPanel przyciski = new JPanel(new GridLayout(5,2));
        dodajWprowadzenieNazwy(przyciski);
        dodajComboBoxWyboruJednostki(przyciski);
        dodajWprowadzenieJednostki(przyciski);

        dodajPrzyciskiFunckyjne(przyciski);

        mainPanel.add(przyciski);

        // TODO: wszystko XDD X2

        listaNazwWidokow.add("listZakupowWidok");
        listaWidokow.add(mainPanel, listaNazwWidokow.get(listaNazwWidokow.size()-1));
    }
    JLabel zaznaczonyProduktJL;
    private void dodajPokazanieProduktuIOpcjaUsuniecia(JPanel mainPanel) {
        JPanel ogolny = new JPanel(new GridLayout(1,3));
        JPanel gora = new JPanel();

        JLabel tekst = new JLabel("Wybrany Produkt: ");
        zaznaczonyProduktJL = new JLabel("Brak");
        zaznaczonyProduktJL.setFont(new Font("Arial", Font.BOLD, rozmiarCzcionki));
        gora.add(tekst);
        gora.add(zaznaczonyProduktJL);

        ogolny.add(gora);

        JButton usunProdukt = new JButton("Usun zaznaczony produkt");
        usunProdukt.setFont(new Font("Arial", Font.ITALIC, rozmiarCzcionkiPrzyciski));
        usunProdukt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                zaznaczonyProduktJL.setText("Brak");
                try {
                    if( usunProduktZListyKompleks()) {
                        System.out.println("Usunieto " + wybranyProduktZListy);
                    }
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

                wybranyProduktZListy = null;
            }
        });
        ogolny.add(usunProdukt);
        mainPanel.add(ogolny);
    }
    boolean usunProduktZListyKompleks() throws IOException {
        boolean ret = obsluga.usunPorduktPoNazwie(wybranyProduktZListy);
        listyProduktowZListyDostepne.removeElement(wybranyProduktZListy);
        if( obsluga.zapiszListeZakupow(wybranaListaZakupow) )
            aktualizujListyProduktowZListyDostepne();
        return ret;
    }
    static private class WprowadzanyProdukt {
        JTextField textFieldNazwa;
        JTextField textFieldIlosc;
        JComboBox<String> wyborJednostki;

        void wyczyscPola() {
            textFieldNazwa.setText("");
            textFieldIlosc.setText("");
        }
    }
    final private WprowadzanyProdukt wprowadzanyProdukt = new WprowadzanyProdukt();
    private void dodajJScrolListyProduktow(JPanel mainPanel) {

        //aktualizujListyProduktowZListyDostepne();
        JList<String> listyProduktowJList  = new JList<>(listyProduktowZListyDostepne);
        listyProduktowJList.setFont(new Font("Arial", Font.BOLD, rozmiarCzcionki-2));
        listyProduktowJList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    wybranyProduktZListy = listyProduktowJList.getSelectedValue();
                    String[] pom = wybranyProduktZListy.split(" ");
                    if(!pom[1].equals(" ")) {
                        wybranyProduktZListy = pom[0] + " " + pom[1];
                    }
                    else {
                        wybranyProduktZListy = pom[0];
                    }
                    zaznaczonyProduktJL.setText(wybranyProduktZListy);
                    System.out.println(wybranyProduktZListy);
                }
            }
        });
        JScrollPane skrolPane = new JScrollPane(listyProduktowJList);
        mainPanel.add(skrolPane,BorderLayout.CENTER);

    }
    private void aktualizujListyProduktowZListyDostepne() throws IOException {
        listyProduktowZListyDostepne.clear();
        System.out.println(obsluga.pobierzListeZakupow(wybranaListaZakupow) + " Czy udalo się pobać liste zakupow");
        for (Produkt produkt : obsluga.getProduktyListy()) {
            listyProduktowZListyDostepne.addElement(String.valueOf(produkt));
        }
    }
    private void dodajProduktdoListyProduktowZListyDostepne() throws IOException {
        double ilosc = Double.parseDouble(wprowadzanyProdukt.textFieldIlosc.getText());
        System.out.println(wprowadzanyProdukt.textFieldNazwa.getText());
        Produkt nowy = new Produkt( wprowadzanyProdukt.textFieldNazwa.getText(),
                new Ilosc(ilosc,
                        Ilosc.Jednostka.valueOf((String) wprowadzanyProdukt.wyborJednostki.getSelectedItem()))
                        );

        obsluga.dodajProduktdoListyZakupow(nowy);
        aktualizujListyProduktowZListyDostepne();
        obsluga.pobierzListeZakupow(wybranaListaZakupow);


    }
    private void dodajComboBoxWyboruJednostki(JPanel mainPanel) {
        JLabel jednostki = new JLabel("Jednostka: ");
        jednostki.setFont(new Font("Arial", Font.BOLD, rozmiarCzcionki));
        mainPanel.add(jednostki);

        String[] options = new String[Ilosc.Jednostka.values().length];
        int i= 0;
        for (Ilosc.Jednostka jednostka : Ilosc.Jednostka.values()) {
            options[i] = jednostka.name();
            i++;
        }
        wprowadzanyProdukt.wyborJednostki = new JComboBox<>(options);

        // Dodajemy listener do select boxa
        wprowadzanyProdukt.wyborJednostki.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedOption = (String) wprowadzanyProdukt.wyborJednostki.getSelectedItem();
                System.out.println("Selected: " + selectedOption);
                System.out.println("Selected Jednostka: " + Ilosc.Jednostka.valueOf(selectedOption));
            }
        });
        mainPanel.add(wprowadzanyProdukt.wyborJednostki);

    }
    private void dodajWprowadzenieNazwy(JPanel mainPanel) {
        JLabel jednostki = new JLabel("Nazwa Produktu: ");
        jednostki.setFont(new Font("Arial", Font.BOLD, rozmiarCzcionki));
        mainPanel.add(jednostki);

        wprowadzanyProdukt.textFieldNazwa = new JTextField("");

        mainPanel.add(wprowadzanyProdukt.textFieldNazwa);
    }
    private void dodajWprowadzenieJednostki(JPanel mainPanel) {
        JLabel jednostki = new JLabel("Ilosc : ");
        jednostki.setFont(new Font("Arial", Font.BOLD, rozmiarCzcionki));
        mainPanel.add(jednostki);

        wprowadzanyProdukt.textFieldIlosc = new JTextField();
        wprowadzanyProdukt.textFieldIlosc.setColumns(10);
        wprowadzanyProdukt.textFieldIlosc.setInputVerifier(new InputVerifier() {
            @Override
            public boolean verify(JComponent input) {
                JTextField textField = (JTextField) input;
                try {
                    if(!isInteger(textField.getText()) && wprowadzanyProdukt.wyborJednostki.getSelectedItem().equals(Ilosc.Jednostka.SZTUKI.name())) {
                        JOptionPane.showMessageDialog( null, "Ten format wymaga liczby całkowitej" );
                        return false;
                    }

                    return true;
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog( null, "Nalezy wprowadzic liczbe" );
                    return false;
                }
            }
        });

        mainPanel.add( wprowadzanyProdukt.textFieldIlosc);
    }
    private static boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    private String prawidlowyTekstDoWyswietlenia() {
        return wybranaListaZakupow == null ? "Wprowadz Nazwe" : wybranaListaZakupow;
    }

    JButton udostepnij;
    JButton koniecUdoistepniania;
    private void edytujWidocznoscPrzyciskowUdostepnianai(boolean czyWidoczny) {
        udostepnij.setVisible(czyWidoczny);
        koniecUdoistepniania.setVisible(czyWidoczny);
    }

    private void dodajPrzyciskiFunckyjne(JPanel mainPanel) {
        JButton zatwierdz = new JButton("Dodaj Produkt");
        zatwierdz.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    dodajProduktdoListyProduktowZListyDostepne();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                wprowadzanyProdukt.wyczyscPola();
            }
        });
        mainPanel.add(zatwierdz);

        JButton menu = new JButton("Menu");
        menu.setFont(new Font("Arial", Font.ITALIC, rozmiarCzcionkiPrzyciski));
        menu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if( !obsluga.czyPlikONazwieIstnieje(wybranaListaZakupow)) {
                    try {
                        if( obsluga.stworzPlikONazwie(wybranaListaZakupow) ) {
                            obsluga.dodajNazweListyZakupow(wybranaListaZakupow);
                            przeladujListyZakopowDostepne();
                        }
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                wybranaListaZakupow = null;
                aktualizujNazweWybranejListyLabel(null);

                cardLayout.show(listaWidokow, listaNazwWidokow.get(0));
                System.out.println("Klikam Menu: " + listaNazwWidokow.get(0));
            }
        });
        mainPanel.add(menu);

         udostepnij = new JButton("Udostepnij Liste");
         udostepnij.setFont(new Font("Arial", Font.ITALIC, rozmiarCzcionkiPrzyciski));
        udostepnij.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: na 5 udoistępnianie
                utworzOknoUdostepnianiaListyZakupow(wybranaListaZakupow);
            }
        });
        mainPanel.add(udostepnij);
        koniecUdoistepniania = new JButton("Zakoncz Udostepnianie");
        koniecUdoistepniania.setFont(new Font("Arial", Font.ITALIC, rozmiarCzcionkiPrzyciski));
        koniecUdoistepniania.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: na 5 udoistępnianie
            }
        });
        mainPanel.add(koniecUdoistepniania);
    }

    private void utworzOknoUdostepnianiaListyZakupow(String nazwa) {
        // TODO: to będzie dla wysyłania danej listy do kogos

        JFrame newFrame = new JFrame("Udostępnij Listę " + nazwa);
        newFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        newFrame.setSize(400, 500);
        newFrame.setLocationRelativeTo(null); // Wyśrodkowanie okna
        newFrame.setVisible(true);
    }

    private void pokazPanelGlowy() throws IOException {
        obsluga.pobieranieNazwListZapupow();
        przeladujListyZakopowDostepne();
        cardLayout.show(listaWidokow, "glownyWidok");
    }

    public static void main(String[] args) {
        try {
            InterfejsUzytkownika iu = new InterfejsUzytkownika(new  ObslugaListZakupow());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }


}
