import javax.swing.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class PlaceholderTextFieldExample {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Placeholder TextField Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Utworzenie pola tekstowego z szarym tekstem wskazującym
        JTextField textField = new JTextField("Wpisz nazwę...", 20);

        // Dodanie słuchacza FocusListener do pola tekstowego
        textField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                // Gdy pole tekstowe zyskuje focus, usuń szary tekst
                if (textField.getText().equals("Wpisz nazwę...")) {
                    textField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                // Gdy pole tekstowe traci focus i jest puste, przywróć szary tekst
                if (textField.getText().isEmpty()) {
                    textField.setText("Wpisz nazwę...");
                }
            }
        });

        frame.getContentPane().add(textField);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
