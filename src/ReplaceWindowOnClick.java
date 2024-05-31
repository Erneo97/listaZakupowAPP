import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
// JComboBox
public class ReplaceWindowOnClick extends JFrame {
    int count = 0;
    Label licznik = new Label("Licznik: " + String.valueOf(count));
    private JPanel cardPanel;
    private CardLayout cardLayout;
    private void  methode() {
        count++;
        System.out.println(count);
        licznik.setText("Licznik: " + String.valueOf(count));
    }
    public ReplaceWindowOnClick() {
        setTitle("Main Window");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Inicjalizacja panelu z CardLayout
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // Panel zawierający przycisk "Open New Window"
        JPanel mainPanel = new JPanel();
        JButton openButton = new JButton("Open New Window");
        openButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Wywołanie akcji przycisku
                methode();
                cardLayout.show(cardPanel, "newWindow");
            }
        });
        mainPanel.add(licznik);
        mainPanel.add(openButton);

        // Panel zastępujący aktualny widok
        JPanel newWindowPanel = new JPanel();
        newWindowPanel.add(new JLabel("This is the new window"));
        JButton backButton = new JButton("Back");
        newWindowPanel.add(backButton);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Wywołanie akcji przycisku
                cardLayout.show(cardPanel, "main");
            }
        });
        // Dodanie paneli do panelu z CardLayout
        cardPanel.add(mainPanel, "main");
        cardPanel.add(newWindowPanel, "newWindow");

        getContentPane().add(cardPanel);
        pack();
        setLocationRelativeTo(null); // Wyśrodkowanie okna głównego
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ReplaceWindowOnClick().setVisible(true);
            }
        });
    }
}
