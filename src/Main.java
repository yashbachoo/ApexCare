import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;

public class Main {

    public static void main(String[] args) {
        ImageIcon appIcon = new ImageIcon("buttonIcons/Apexemblem.png");
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 500);
        frame.setTitle("Apex Care");
        frame.setIconImage(appIcon.getImage());
        frame.setLayout(new BorderLayout());


        // Top Navbar
        JPanel redPanel = new JPanel();
        redPanel.setBackground(new Color(0,116,122));
        redPanel.setPreferredSize(new Dimension(800,70));
        redPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10));


        // Left Sidebar
        JLabel logoContainer = new JLabel();
        JPanel bluePanel = new JPanel();
        bluePanel.setBackground(new Color(0, 116, 122, 208));
        bluePanel.setPreferredSize(new Dimension(250,430));
        bluePanel.setLayout(null);
        logoContainer.setBounds(0,0,250,110);
        logoContainer.setBackground(new Color(255,255,255));
        logoContainer.setOpaque(true);
        logoContainer.setIcon(IconUtils.resizeIcon(250,110,"buttonIcons/ApexCareLogo.png"));

       // initializing buttons on the left sidebar
        Color sideButtonscolor = new Color(0, 116, 122);
        JButton addDoctor = new JButton("Add Doctors");
        addDoctor.setBounds(0,130,250,80);
        addDoctor.setBackground(sideButtonscolor);
        addDoctor.setForeground(Color.WHITE);
        addDoctor.setFocusPainted(false);
        addDoctor.setRolloverEnabled(false);

        JButton addPatient = new JButton("Add Patients ");
        addPatient.setBounds(0,221,250,80);
        addPatient.setBackground(sideButtonscolor);
        addPatient.setForeground(Color.WHITE);
        addPatient.setFocusPainted(false);
        addPatient.setRolloverEnabled(false);

        JButton logout = new JButton("Log out");
        logout.setBounds(60,750,130,50);
        logout.setFocusPainted(false);
        logout.setRolloverEnabled(false);
        logout.setBackground(new Color(175, 225, 194));


        bluePanel.add(logoContainer);
        bluePanel.add(addDoctor);
        bluePanel.add(addPatient);
        bluePanel.add(logout);
        bluePanel.add(logout);
// ########################################################################



        // Center panel with CardLayout
        JPanel mainPanel = new JPanel(new CardLayout());
        CardLayout cardLayout = (CardLayout) mainPanel.getLayout();

        // Connect to database
        Database db = null;
        try {
            db = new Database();
        } catch(Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database connection failed!", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }

        // Add Login Page first
        LoginPage loginPage = new LoginPage(cardLayout, mainPanel, db);
        mainPanel.add(loginPage, "Login");

        //Add Signup page
        SignupPage signupPage = new SignupPage(cardLayout, mainPanel, db);
        mainPanel.add(signupPage, "Signup");

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
        String[] buttonNames = {"Administration", "Doctors", "Pharmacy", "Maintenance", };
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