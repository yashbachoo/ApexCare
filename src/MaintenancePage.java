import javax.swing.*;
import java.awt.*;


// Maintenance page
class MaintenancePage extends JPanel {
    public MaintenancePage() {
        setBackground(Color.CYAN);
        setLayout(new BorderLayout());
        add(new JLabel("Welcome to Maintenance Page", SwingConstants.CENTER), BorderLayout.CENTER);

        // Example additional content
        JPanel topPanel = new JPanel();
        topPanel.add(new JButton("Report Issue"));
        topPanel.add(new JButton("Check Tasks"));
        add(topPanel, BorderLayout.NORTH);
    }
}
