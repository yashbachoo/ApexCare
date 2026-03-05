import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 500);
        frame.setLayout(new BorderLayout());

        // Top Navbar
        JPanel redPanel = new JPanel();
        redPanel.setBackground(new Color(0,116,122));
        redPanel.setPreferredSize(new Dimension(800,50));
        redPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10));

        // Left Sidebar
        JPanel bluePanel = new JPanel();
        bluePanel.setBackground(Color.BLUE);
        bluePanel.setPreferredSize(new Dimension(200,450));

        // Center panel with CardLayout
        JPanel mainPanel = new JPanel(new CardLayout());
        CardLayout cardLayout = (CardLayout) mainPanel.getLayout();

        // Add Login Page first
        LoginPage loginPage = new LoginPage(cardLayout, mainPanel);
        mainPanel.add(loginPage, "Login");

        // Create Dashboard panel (wrap your buttons and pages here)
        JPanel dashboardPanel = new JPanel(new BorderLayout());

        // Add Navbar and Sidebar only inside Dashboard
        dashboardPanel.add(redPanel, BorderLayout.NORTH);
        dashboardPanel.add(bluePanel, BorderLayout.WEST);

        // Dashboard center panel with pages
        JPanel dashboardCenter = new JPanel(new CardLayout());

        AdministrationPage adminPage = new AdministrationPage();
        DoctorsPage doctorsPage = new DoctorsPage();
        PharmacyPage pharmacyPage = new PharmacyPage();
        MaintenancePage maintenancePage = new MaintenancePage();

        dashboardCenter.add(adminPage, "Administration");
        dashboardCenter.add(doctorsPage, "Doctors");
        dashboardCenter.add(pharmacyPage, "Pharmacy");
        dashboardCenter.add(maintenancePage, "Maintenance");

        dashboardPanel.add(dashboardCenter, BorderLayout.CENTER);

        // Buttons
        String[] buttonNames = {"Administration", "Doctors", "Pharmacy", "Maintenance"};
        JButton[] buttons = new JButton[buttonNames.length];

        for (int i = 0; i < buttonNames.length; i++) {
            String pageName = buttonNames[i];
            JButton btn = new JButton(pageName);
            btn.setForeground(Color.WHITE);
            btn.setBackground(new Color(0,116,122));
            btn.setFocusPainted(false);
            btn.setBorder(new EmptyBorder(7, 30, 7, 30));
            btn.setOpaque(true);

            buttons[i] = btn;

            btn.addActionListener(e -> {
                // Reset button colors
                for (JButton b : buttons) {
                    b.setBackground(new Color(0,116,122));
                }
                btn.setBackground(new Color(0,150,160));

                // Show selected page
                CardLayout cl = (CardLayout)(dashboardCenter.getLayout());
                cl.show(dashboardCenter, pageName);
            });

            redPanel.add(btn);
        }

        mainPanel.add(dashboardPanel, "Dashboard");

        // Show Login page first
        cardLayout.show(mainPanel, "Login");

        frame.add(mainPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }
}