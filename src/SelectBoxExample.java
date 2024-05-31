import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SelectBoxExample {

    public static void main(String[] args) {
        // Tworzymy główne okno
        JFrame frame = new JFrame("Select Box Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);
        frame.setLayout(new FlowLayout());

        // Tworzymy select box (JComboBox) z kilkoma opcjami
        String[] options = {"Option 1", "Option 2", "Option 3", "Option 4"};
        JComboBox<String> comboBox = new JComboBox<>(options);

        // Dodajemy listener do select boxa
        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedOption = (String) comboBox.getSelectedItem();
                System.out.println("Selected: " + selectedOption);
            }
        });

        // Dodajemy select box do okna
        frame.add(comboBox);

        // Wyświetlamy okno
        frame.setVisible(true);
    }
}
