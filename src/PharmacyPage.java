import javax.swing.*;
import java.awt.*;

// Pharmacy page
class PharmacyPage extends JPanel {
    public PharmacyPage() {
        setBackground(Color.ORANGE);
        setLayout(new BorderLayout());
        add(new JLabel("Welcome to Pharmacy Page", SwingConstants.CENTER), BorderLayout.CENTER);

        // Example additional content
        JPanel topPanel = new JPanel();
        topPanel.add(new JButton("Add Medicine"));
        topPanel.add(new JButton("Check Stock"));
        add(topPanel, BorderLayout.NORTH);
    }
}

