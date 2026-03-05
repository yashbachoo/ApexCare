import javax.swing.*;
import java.awt.*;


// Doctors page
class DoctorsPage extends JPanel {
    public DoctorsPage() {
        setBackground(Color.PINK);
        setLayout(new BorderLayout());
        add(new JLabel("Welcome to Doctors Page", SwingConstants.CENTER), BorderLayout.CENTER);

        // Example additional content
        JPanel topPanel = new JPanel();
        topPanel.add(new JButton("View Schedule"));
        topPanel.add(new JButton("Add Appointment"));
        add(topPanel, BorderLayout.NORTH);
    }
}