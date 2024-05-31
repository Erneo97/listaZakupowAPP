import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class TestListy extends JFrame {
    Object[][] data = {
            {"Parametr 1", "Wartość 1"},
            {"Param 2", "Waość 2"},
            {"Parametr 3", "Wartość 3"},
            {"ametr 4", "Wać 4"},
            {"Parametr 5", "Wartość 5"},
            {"Param 6", "Wartość 6"},
            {"Parametr 1", "Wartość 1"},
            {"Param 2", "Waość 2"},
            {"Parametr 3", "Wartość 3"},
            {"ametr 4", "Wać 4"},
            {"Parametr 5", "Wartość 5"},
            {"Param 6", "Wartość 6"},
            {"Parametr 1", "Wartość 1"},
            {"Param 2", "Waość 2"},
            {"Parametr 3", "Wartość 3"},
            {"ametr 4", "Wać 4"},
            {"Parametr 5", "Wartość 5"},
            {"Param 6", "Wartość 6"},
            {"Parametr 1", "Wartość 1"},
            {"Param 2", "Waość 2"},
            {"Parametr 3", "Wartość 3"},
            {"ametr 4", "Wać 4"},
            {"Parametr 5", "Wartość 5"},
            {"Param 6", "Wartość 6"},{"Parametr 1", "Wartość 1"},
            {"Param 2", "Waość 2"},
            {"Parametr 3", "Wartość 3"},
            {"ametr 4", "Wać 4"},
            {"Parametr 5", "Wartość 5"},
            {"Param 6", "Wartość 6"},

            // Dodaj więcej danych, jeśli jest to potrzebne
    };
    public TestListy() {
        setTitle("Multi-Column Scrollable Table Example");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Dane do wyświetlenia w tabeli


        // Nagłówki kolumn
        String[] columnHeaders = {"Parametr", "Wartość"};

        // Tworzenie modelu tabeli z danymi
        DefaultTableModel model = new DefaultTableModel(data, columnHeaders) {
            @Override
            public boolean isCellEditable(int row, int column) { // zabraniam ręcznego modyfikowania tabeli (listy elementow)
                return false;
            }
        };

        // Tworzenie tabeli na podstawie modelu
        JTable table = new JTable(model);
        ustawienieParametrowTabeli(table);


        // Tworzenie JScrollPane z tabelą
        JScrollPane scrollPane = new JScrollPane(table);

        // Ustawienie preferowanej wielkości JScrollPane
        //scrollPane.setPreferredSize(new Dimension(300, 200));

        // Dodanie JScrollPane do okna głównego

        JPanel okienko = new JPanel();

        okienko.add(scrollPane, BorderLayout.WEST);
        okienko.setBorder(BorderFactory.createLineBorder(Color.black));

        Button przycisk = new Button("Ale program");
        okienko.add(przycisk, BorderLayout.EAST );
        Button przycisk1 = new Button("Ale program2");
        okienko.add(przycisk1, BorderLayout.EAST );
        Button przycisk2 = new Button("Ale program3");
        okienko.add(przycisk2, BorderLayout.SOUTH );
        przycisk2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: to będzie dla wysyłania danej listy do kogos
                // Tworzenie i wyświetlanie nowego okna
                JFrame newFrame = new JFrame("New Window");
                newFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                newFrame.setSize(300, 200);
                newFrame.setLocationRelativeTo(null); // Wyśrodkowanie okna
                newFrame.setVisible(true);
            }
        });
        getContentPane().add(okienko, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void ustawienieParametrowTabeli(JTable table ) {

        Font font = new Font("Arial", Font.ITALIC, 16);
        table.setFont(font);

        JTableHeader header = table.getTableHeader(); // naglowek tabeli
        header.setFont(font);



        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //System.out.println("pyk");
                if (e.getClickCount() == 1) { // Reakcja na podwójne kliknięcie
                    // Pobranie zaznaczonego elementu listy
                    int index = table.getSelectedRow();


                        Object[] selectedValue = data[index];
                        // Wyświetlenie okna dialogowego z informacją o klikniętym elemencie
                        System.out.println(selectedValue[0] + "  " + selectedValue[1]);

                }

            }
        });
        // Ustawienie szerokości kolumn
        table.getColumnModel().getColumn(0).setPreferredWidth(150);
        table.getColumnModel().getColumn(1).setPreferredWidth(50);
    }

    // Przykładowa klasa reprezentująca obiekt w liście
    class MyObject {
        private String name;
        private int value;

        public MyObject(String name, int value) {
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public int getValue() {
            return value;
        }

        public void inkrement() { this.value++;}

        @Override
        public String toString() {
            return name + " - " + value;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TestListy::new);

        Ilosc ilosc = new Ilosc(0.5, Ilosc.Jednostka.LITR);

        ilosc.increment();
        ilosc.decrement();
        System.out.println(ilosc);

    }
}
