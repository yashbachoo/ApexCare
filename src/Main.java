import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;


public class Main {

    public static void main(String[] args) {
        ImageIcon appIcon = new ImageIcon("buttonIcons/Apexemblem.png");
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1400, 900);
        frame.setTitle("Apex Care");
        frame.setIconImage(appIcon.getImage());
        frame.setLayout(new BorderLayout());


        // Top Navbar
        JPanel redPanel = new JPanel();
        redPanel.setBackground(new Color(0,116,122));
        redPanel.setPreferredSize(new Dimension(800,60));
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
//        Color sideButtonscolor = new Color(0, 116, 122);
        JButton addDoctor = new JButton("Add Doctors");
        addDoctor.setBounds(0,130,250,60);
//        addDoctor.setBackground(Color.BLACK);
        addDoctor.setOpaque(true);
//        addDoctor.setForeground(Color.WHITE);
        addDoctor.setFocusPainted(false);
        addDoctor.setRolloverEnabled(false);


        JButton addPatientBtn = new JButton("Add Patients ");
        addPatientBtn.setBounds(0,221,250,60);
//        addPatient.setBackground(new Color(0, 116, 122));
        addPatientBtn.setOpaque(true);
//        addPatient.setForeground(Color.WHITE);
        addPatientBtn.setFocusPainted(false);
        addPatientBtn.setRolloverEnabled(false);




        JButton logout = new JButton("Log out");
        logout.setBounds(60,750,130,50);
        logout.setFocusPainted(false);
        logout.setRolloverEnabled(false);
        logout.setBackground(new Color(175, 225, 194));


        bluePanel.add(logoContainer);
        bluePanel.add(addDoctor);
        bluePanel.add(addPatientBtn);
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

        // Dashboard top panel with pages
        JPanel dashboardCenter = new JPanel(new CardLayout());

        AdministrationPage adminPage = new AdministrationPage(dashboardCenter);
        DoctorsPage doctorsPage = new DoctorsPage();
        PharmacyPage pharmacyPage = new PharmacyPage(dashboardCenter);
        MaintenancePage maintenancePage = new MaintenancePage();
        AddDoctorPage addDoctorPage = new AddDoctorPage();
        AddPatient addPatient = new AddPatient(dashboardCenter);
        DoctorDetails doctorDetails = new DoctorDetails(1);
        PharmacyPurchase pharmacyPurchase = new PharmacyPurchase();
        PharmacyStocks pharmacyStocks = new PharmacyStocks(dashboardCenter);
        AddPharmacyStockPage addPharmacyStockPage = new AddPharmacyStockPage();
        PatientDetails patientDetails = new PatientDetails(1);

        //Dashboard center panel with pages
        DoctorProfiles doctorProfilesPage = new DoctorProfiles(dashboardCenter);
        PatientProfiles patientProfilesPage = new PatientProfiles(dashboardCenter);
        Appointments AppointmentsPage = new Appointments();
        Ambulances AmbulancesPage = new Ambulances();
        Admissions AdmissionsPage = new Admissions();
        Staff StaffPage = new Staff();


        dashboardCenter.add(adminPage, "Administration");
        dashboardCenter.add(doctorsPage, "Doctors");
        dashboardCenter.add(addPatient, "AddPatient");
        dashboardCenter.add(pharmacyPage, "Pharmacy");
        dashboardCenter.add(maintenancePage, "Maintenance");
        dashboardCenter.add(addDoctorPage, "AddDoctorPage");
        dashboardCenter.add("doctorDetails", doctorDetails);
        dashboardCenter.add(pharmacyPurchase,"PharmacyPurchase");
        dashboardCenter.add(pharmacyStocks,"PharmacyStocks");
        dashboardCenter.add(addPharmacyStockPage,"AddPharmacyStockPage");

        dashboardCenter.add(doctorDetails, "doctorDetails");
        dashboardCenter.add(patientDetails, "patientDetails");

        dashboardCenter.add(doctorProfilesPage, "DoctorProfiles");
        dashboardCenter.add(patientProfilesPage, "PatientProfiles");
        dashboardCenter.add(AppointmentsPage, "Appointments");
        dashboardCenter.add(AmbulancesPage, "Ambulances");
        dashboardCenter.add(AdmissionsPage, "Admissions");
        dashboardCenter.add(StaffPage, "Staff");



        dashboardPanel.add(dashboardCenter, BorderLayout.CENTER);

        // Buttons
        String[] buttonNames = {"Administration", "Doctors", "Pharmacy", "Maintenance", "AddDoctor"};
        JButton[] buttons = new JButton[buttonNames.length];
        JButton reloadBtn = new JButton("Reload");

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

        reloadBtn.setForeground(Color.WHITE);
        reloadBtn.setBackground(new Color(0,116,122));
        reloadBtn.setFocusPainted(false);
        reloadBtn.setBorder(new EmptyBorder(7, 30, 7, 30));
        reloadBtn.setOpaque(true);


        //Action to direct buttons to another page
        addDoctor.addActionListener(e -> {
            CardLayout cl = (CardLayout) dashboardCenter.getLayout();
            cl.show(dashboardCenter, "AddDoctorPage");
        });

        //Action to direct to AddPatient page
        addPatientBtn.addActionListener(e -> {
            CardLayout cl = (CardLayout) dashboardCenter.getLayout();
            cl.show(dashboardCenter, "AddPatient");
        });

        mainPanel.add(dashboardPanel, "Dashboard");

        // Show Login page first
        cardLayout.show(mainPanel, "Login");

        frame.add(mainPanel, BorderLayout.CENTER);

        frame.setVisible(true);

    }

}