import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Vector;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class Admissions extends JPanel {
    private JTextField patientNameField;
    private JTextField patientIdField;
    private JTextField ageField;
    private JComboBox<String> genderCombo;
    private JComboBox<String> bloodTypeCombo;
    private JTextField contactField;
    private JTextField emergencyContactField;
    private JTextField emailField;
    private JTextArea addressArea;
    private JComboBox<String> wardTypeCombo;
    private JComboBox<String> roomNumberCombo;
    private JSpinner admissionDateSpinner;
    private JSpinner expectedDischargeDateSpinner;
    private JComboBox<String> doctorCombo;
    private JComboBox<String> diagnosisCombo;
    private JTextArea symptomsArea;
    private JTextArea medicalHistoryArea;
    private JComboBox<String> insuranceProviderCombo;
    private JTextField insuranceNumberField;
    private JComboBox<String> paymentMethodCombo;
    private JCheckBox termsCheckBox;
    private JComboBox<String> admissionTypeCombo;

    // Database connection parameters
    private static final String DB_URL = "jdbc:mysql://localhost:3306/hospital_db";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "password";

    private CardLayout cardLayout;
    private JPanel mainContainer;
    private JTable admittedTable;
    private JTextField searchField;

    // Colors
    private final Color PRIMARY_COLOR = new Color(0, 116, 122);
    private final Color SECONDARY_COLOR = new Color(175, 225, 194);
    private final Color BACKGROUND_COLOR = new Color(240, 242, 245);
    private final Color CARD_COLOR = Color.WHITE;
    private final Color TEXT_COLOR = new Color(51, 51, 51);
    private final Color BORDER_COLOR = new Color(224, 224, 224);

    public Admissions() {
        setLayout(new BorderLayout());
        setBackground(BACKGROUND_COLOR);

        initializeDatabase();

        cardLayout = new CardLayout();
        mainContainer = new JPanel(cardLayout);
        mainContainer.setBackground(BACKGROUND_COLOR);

        mainContainer.add(createSelectionPanel(), "SELECTION");
        mainContainer.add(createCompleteFormPanel(), "FORM");
        mainContainer.add(createAdmittedPersonsPanel(), "ADMITTED");

        add(mainContainer, BorderLayout.CENTER);
    }

    private JPanel createSelectionPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(BACKGROUND_COLOR);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);

        JLabel titleLabel = new JLabel("Hospital Management System");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        titleLabel.setForeground(PRIMARY_COLOR);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);

        JLabel subtitleLabel = new JLabel("Please select an option to continue");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        subtitleLabel.setForeground(TEXT_COLOR);
        gbc.gridy = 1;
        panel.add(subtitleLabel, gbc);

        JButton newAdmissionBtn = createLargeStyledButton("📝 New Admission",
                "Register a new patient admission", PRIMARY_COLOR, Color.WHITE);
        newAdmissionBtn.addActionListener(e -> {
            cardLayout.show(mainContainer, "FORM");
        });
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(20, 20, 20, 20);
        panel.add(newAdmissionBtn, gbc);

        JButton admittedPersonsBtn = createLargeStyledButton("👥 Admitted Persons",
                "View currently admitted patients", new Color(76, 175, 80), Color.WHITE);
        admittedPersonsBtn.addActionListener(e -> {
            loadAdmittedPatients();
            cardLayout.show(mainContainer, "ADMITTED");
        });
        gbc.gridx = 1;
        panel.add(admittedPersonsBtn, gbc);

        return panel;
    }

    private JButton createLargeStyledButton(String title, String description, Color bgColor, Color fgColor) {
        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.setBackground(bgColor);
        buttonPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(bgColor.darker(), 1),
                new EmptyBorder(20, 30, 20, 30)
        ));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(fgColor);

        JLabel descLabel = new JLabel(description);
        descLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        descLabel.setForeground(new Color(255, 255, 255, 200));

        buttonPanel.add(titleLabel, BorderLayout.NORTH);
        buttonPanel.add(descLabel, BorderLayout.SOUTH);

        JButton button = new JButton();
        button.setLayout(new BorderLayout());
        button.add(buttonPanel, BorderLayout.CENTER);
        button.setBackground(bgColor);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(bgColor.darker());
            }
            public void mouseExited(MouseEvent e) {
                button.setBackground(bgColor);
            }
        });

        return button;
    }

    private JPanel createCompleteFormPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BACKGROUND_COLOR);

        // 🔙 Back button (TOP)
        JButton backBtn = createStyledButton("← Back to Selection", new Color(108, 117, 125), Color.BLUE);
        backBtn.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(panel,
                    "Are you sure you want to go back? Any unsaved data will be lost.",
                    "Confirm Navigation",
                    JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                cardLayout.show(mainContainer, "SELECTION");
            }
        });

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBackground(BACKGROUND_COLOR);
        topPanel.setBorder(new EmptyBorder(10, 20, 10, 20));
        topPanel.add(backBtn);
        panel.add(topPanel, BorderLayout.NORTH);

        // 📝 FORM PANEL (SCROLLABLE)
        JPanel formPanel = createFormPanel();

        JScrollPane scrollPane = new JScrollPane(formPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        panel.add(scrollPane, BorderLayout.CENTER);

        // ✅ FOOTER (FIXED - ALWAYS VISIBLE)
        JPanel footerPanel = createFooterPanel();
        panel.add(footerPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createFormPanel() {
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(BACKGROUND_COLOR);
        formPanel.setBorder(new EmptyBorder(30, 50, 30, 50));

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(CARD_COLOR);
        headerPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR, 1),
                new EmptyBorder(20, 25, 20, 25)
        ));
        JLabel titleLabel = new JLabel("Patient Admission Form");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(PRIMARY_COLOR);
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        formPanel.add(headerPanel);
        formPanel.add(Box.createRigidArea(new Dimension(0, 25)));

        formPanel.add(createSectionCard("Personal Information", createPersonalInfoPanel()));
        formPanel.add(Box.createRigidArea(new Dimension(0, 25)));

        formPanel.add(createSectionCard("Contact Information", createContactInfoPanel()));
        formPanel.add(Box.createRigidArea(new Dimension(0, 25)));

        formPanel.add(createSectionCard("Admission Details", createAdmissionDetailsPanel()));
        formPanel.add(Box.createRigidArea(new Dimension(0, 25)));

        formPanel.add(createSectionCard("Medical Information", createMedicalInfoPanel()));
        formPanel.add(Box.createRigidArea(new Dimension(0, 25)));

        formPanel.add(createSectionCard("Insurance & Payment", createInsurancePaymentPanel()));

        return formPanel;
    }

    private JPanel createSectionCard(String title, JPanel contentPanel) {
        JPanel cardPanel = new JPanel(new BorderLayout());
        cardPanel.setBackground(CARD_COLOR);
        cardPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR, 1),
                new EmptyBorder(20, 25, 20, 25)
        ));

        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setOpaque(false);
        titlePanel.setBorder(new EmptyBorder(0, 0, 15, 0));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setForeground(PRIMARY_COLOR);

        JSeparator separator = new JSeparator();
        separator.setForeground(BORDER_COLOR);

        titlePanel.add(titleLabel, BorderLayout.NORTH);
        titlePanel.add(separator, BorderLayout.SOUTH);

        cardPanel.add(titlePanel, BorderLayout.NORTH);
        cardPanel.add(contentPanel, BorderLayout.CENTER);

        return cardPanel;
    }

    private JPanel createPersonalInfoPanel() {
        JPanel panel = new JPanel(new GridLayout(0, 2, 15, 15));
        panel.setOpaque(false);

        panel.add(createLabeledField("Patient Full Name *", () -> {
            patientNameField = createStyledTextField();
            return patientNameField;
        }));

        panel.add(createLabeledField("Patient ID *", () -> {
            patientIdField = createStyledTextField();
            return patientIdField;
        }));

        panel.add(createLabeledField("Age *", () -> {
            ageField = createStyledTextField();
            return ageField;
        }));

        panel.add(createLabeledField("Gender *", () -> {
            String[] genders = {"Select Gender", "Male", "Female", "Other"};
            genderCombo = createStyledComboBox(genders);
            return genderCombo;
        }));

        panel.add(createLabeledField("Blood Type", () -> {
            String[] bloodTypes = {"Select Blood Type", "A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"};
            bloodTypeCombo = createStyledComboBox(bloodTypes);
            return bloodTypeCombo;
        }));

        panel.add(createLabeledField("Date of Birth", () -> {
            JTextField dobField = createStyledTextField();
            dobField.setToolTipText("DD/MM/YYYY");
            return dobField;
        }));

        return panel;
    }

    private JPanel createContactInfoPanel() {
        JPanel panel = new JPanel(new GridLayout(0, 2, 15, 15));
        panel.setOpaque(false);

        panel.add(createLabeledField("Contact Number *", () -> {
            contactField = createStyledTextField();
            return contactField;
        }));

        panel.add(createLabeledField("Emergency Contact Number *", () -> {
            emergencyContactField = createStyledTextField();
            return emergencyContactField;
        }));

        panel.add(createLabeledField("Email Address", () -> {
            emailField = createStyledTextField();
            return emailField;
        }));

        JPanel addressPanel = new JPanel(new BorderLayout());
        addressPanel.setOpaque(false);
        JLabel addressLabel = new JLabel("Address *");
        addressLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        addressLabel.setForeground(TEXT_COLOR);
        addressArea = new JTextArea(3, 20);
        addressArea.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        addressArea.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR),
                new EmptyBorder(8, 10, 8, 10)
        ));
        JScrollPane addressScroll = new JScrollPane(addressArea);
        addressScroll.setBorder(null);
        addressPanel.add(addressLabel, BorderLayout.NORTH);
        addressPanel.add(addressScroll, BorderLayout.CENTER);

        panel.add(addressPanel);
        panel.add(new JPanel());

        return panel;
    }

    private JPanel createAdmissionDetailsPanel() {
        JPanel panel = new JPanel(new GridLayout(0, 2, 15, 15));
        panel.setOpaque(false);

        panel.add(createLabeledField("Ward Type *", () -> {
            String[] wards = {"Select Ward Type", "General Ward", "Semi-Private Room", "Private Room", "ICU", "VIP Suite"};
            wardTypeCombo = createStyledComboBox(wards);
            return wardTypeCombo;
        }));

        panel.add(createLabeledField("Room Number *", () -> {
            String[] rooms = {"Select Room", "101", "102", "103", "104", "105", "201", "202", "203"};
            roomNumberCombo = createStyledComboBox(rooms);
            return roomNumberCombo;
        }));

        panel.add(createLabeledField("Admission Date *", () -> {
            SpinnerDateModel model = new SpinnerDateModel();
            admissionDateSpinner = new JSpinner(model);
            JSpinner.DateEditor editor = new JSpinner.DateEditor(admissionDateSpinner, "dd/MM/yyyy");
            admissionDateSpinner.setEditor(editor);
            admissionDateSpinner.setValue(new java.util.Date());
            styleComponent(admissionDateSpinner);
            return admissionDateSpinner;
        }));

        panel.add(createLabeledField("Expected Discharge Date", () -> {
            SpinnerDateModel model = new SpinnerDateModel();
            expectedDischargeDateSpinner = new JSpinner(model);
            JSpinner.DateEditor editor = new JSpinner.DateEditor(expectedDischargeDateSpinner, "dd/MM/yyyy");
            expectedDischargeDateSpinner.setEditor(editor);
            styleComponent(expectedDischargeDateSpinner);
            return expectedDischargeDateSpinner;
        }));

        panel.add(createLabeledField("Assigned Doctor *", () -> {
            String[] doctors = {"Select Doctor", "Dr. Sarah Johnson", "Dr. Michael Chen", "Dr. Emily Brown", "Dr. James Wilson", "Dr. Maria Garcia"};
            doctorCombo = createStyledComboBox(doctors);
            return doctorCombo;
        }));

        panel.add(createLabeledField("Admission Type *", () -> {
            String[] types = {"Select Type", "Emergency", "Scheduled", "Transfer", "Walk-in"};
            admissionTypeCombo = createStyledComboBox(types);
            return admissionTypeCombo;
        }));

        return panel;
    }

    private JPanel createMedicalInfoPanel() {
        JPanel panel = new JPanel(new GridLayout(0, 1, 15, 15));
        panel.setOpaque(false);

        JPanel diagnosisPanel = new JPanel(new BorderLayout());
        diagnosisPanel.setOpaque(false);
        JLabel diagnosisLabel = new JLabel("Primary Diagnosis *");
        diagnosisLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        diagnosisLabel.setForeground(TEXT_COLOR);
        String[] diagnoses = {"Select Diagnosis", "Hypertension", "Diabetes", "Pneumonia", "Fracture", "Heart Disease", "COVID-19", "Stroke", "Other"};
        diagnosisCombo = createStyledComboBox(diagnoses);
        diagnosisPanel.add(diagnosisLabel, BorderLayout.NORTH);
        diagnosisPanel.add(diagnosisCombo, BorderLayout.CENTER);
        panel.add(diagnosisPanel);

        JPanel symptomsPanel = new JPanel(new BorderLayout());
        symptomsPanel.setOpaque(false);
        JLabel symptomsLabel = new JLabel("Symptoms / Presenting Complaints");
        symptomsLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        symptomsLabel.setForeground(TEXT_COLOR);
        symptomsArea = new JTextArea(3, 20);
        symptomsArea.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        symptomsArea.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR),
                new EmptyBorder(8, 10, 8, 10)
        ));
        JScrollPane symptomsScroll = new JScrollPane(symptomsArea);
        symptomsScroll.setBorder(null);
        symptomsPanel.add(symptomsLabel, BorderLayout.NORTH);
        symptomsPanel.add(symptomsScroll, BorderLayout.CENTER);
        panel.add(symptomsPanel);

        JPanel historyPanel = new JPanel(new BorderLayout());
        historyPanel.setOpaque(false);
        JLabel historyLabel = new JLabel("Medical History (Previous conditions, surgeries, allergies)");
        historyLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        historyLabel.setForeground(TEXT_COLOR);
        medicalHistoryArea = new JTextArea(4, 20);
        medicalHistoryArea.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        medicalHistoryArea.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR),
                new EmptyBorder(8, 10, 8, 10)
        ));
        JScrollPane historyScroll = new JScrollPane(medicalHistoryArea);
        historyScroll.setBorder(null);
        historyPanel.add(historyLabel, BorderLayout.NORTH);
        historyPanel.add(historyScroll, BorderLayout.CENTER);
        panel.add(historyPanel);

        return panel;
    }

    private JPanel createInsurancePaymentPanel() {
        JPanel panel = new JPanel(new GridLayout(0, 2, 15, 15));
        panel.setOpaque(false);

        panel.add(createLabeledField("Insurance Provider", () -> {
            String[] providers = {"Select Provider", "None", "Medicare", "Medicaid", "Blue Cross", "Aetna", "Cigna", "UnitedHealth"};
            insuranceProviderCombo = createStyledComboBox(providers);
            return insuranceProviderCombo;
        }));

        panel.add(createLabeledField("Insurance Policy Number", () -> {
            insuranceNumberField = createStyledTextField();
            return insuranceNumberField;
        }));

        panel.add(createLabeledField("Payment Method *", () -> {
            String[] methods = {"Select Payment Method", "Cash", "Credit Card", "Insurance", "Bank Transfer", "Online Payment"};
            paymentMethodCombo = createStyledComboBox(methods);
            return paymentMethodCombo;
        }));

        JPanel termsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        termsPanel.setOpaque(false);
        termsCheckBox = new JCheckBox("I confirm that all information provided is accurate and I agree to the terms and conditions");
        termsCheckBox.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        termsCheckBox.setForeground(TEXT_COLOR);
        termsPanel.add(termsCheckBox);

        panel.add(termsPanel);
        panel.add(new JPanel());

        return panel;
    }

    private JPanel createFooterPanel() {
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        footerPanel.setBackground(Color.BLUE);
        footerPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 0, 0, 0, BORDER_COLOR),
                new EmptyBorder(15, 0, 15, 0)
        ));

        JButton submitButton = createStyledButton("Submit Admission", PRIMARY_COLOR, Color.WHITE);
        JButton resetButton = createStyledButton("Reset Form", new Color(108, 117, 125), Color.WHITE);

        addHoverEffect(submitButton, PRIMARY_COLOR, new Color(0, 80, 85));
        addHoverEffect(resetButton, new Color(108, 117, 125), new Color(90, 100, 110));

        submitButton.addActionListener(e -> submitAdmission());
        resetButton.addActionListener(e -> resetForm());

        footerPanel.add(submitButton);
        footerPanel.add(resetButton);

        return footerPanel;
    }

    private JPanel createAdmittedPersonsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BACKGROUND_COLOR);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(BACKGROUND_COLOR);
        topPanel.setBorder(new EmptyBorder(20, 20, 10, 20));

        JButton backBtn = createStyledButton("← Back to Selection", new Color(108, 117, 125), Color.BLUE);
        backBtn.addActionListener(e -> cardLayout.show(mainContainer, "SELECTION"));
        topPanel.add(backBtn, BorderLayout.WEST);

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        searchPanel.setBackground(BACKGROUND_COLOR);
        JLabel searchLabel = new JLabel("Search: ");
        searchLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        searchField = new JTextField(20);
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        topPanel.add(searchPanel, BorderLayout.EAST);

        panel.add(topPanel, BorderLayout.NORTH);

        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(CARD_COLOR);
        tablePanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        admittedTable = new JTable();
        admittedTable.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        admittedTable.setRowHeight(30);
        admittedTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        admittedTable.getTableHeader().setBackground(PRIMARY_COLOR);
        admittedTable.getTableHeader().setForeground(Color.WHITE);
        admittedTable.setSelectionBackground(SECONDARY_COLOR);

        JScrollPane tableScrollPane = new JScrollPane(admittedTable);
        tableScrollPane.setBorder(BorderFactory.createLineBorder(BORDER_COLOR));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton dischargeBtn = createStyledButton("Discharge Patient", new Color(220, 53, 69), Color.WHITE);
        JButton refreshBtn = createStyledButton("Refresh List", PRIMARY_COLOR, Color.WHITE);
        buttonPanel.add(dischargeBtn);
        buttonPanel.add(refreshBtn);

        tablePanel.add(tableScrollPane, BorderLayout.CENTER);
        tablePanel.add(buttonPanel, BorderLayout.SOUTH);
        panel.add(tablePanel, BorderLayout.CENTER);

        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) { searchTable(); }
            @Override
            public void removeUpdate(DocumentEvent e) { searchTable(); }
            @Override
            public void changedUpdate(DocumentEvent e) { searchTable(); }
        });

        dischargeBtn.addActionListener(e -> {
            int selectedRow = admittedTable.getSelectedRow();
            if (selectedRow >= 0) {
                int patientId = (int) admittedTable.getValueAt(selectedRow, 0);
                dischargePatient(patientId);
                loadAdmittedPatients();
            } else {
                JOptionPane.showMessageDialog(panel, "Please select a patient to discharge");
            }
        });

        refreshBtn.addActionListener(e -> loadAdmittedPatients());

        return panel;
    }

    private void searchTable() {
        if (admittedTable.getModel() instanceof DefaultTableModel) {
            TableRowSorter<TableModel> sorter = new TableRowSorter<>(admittedTable.getModel());
            admittedTable.setRowSorter(sorter);
            if (searchField.getText().trim().length() == 0) {
                sorter.setRowFilter(null);
            } else {
                sorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchField.getText()));
            }
        }
    }

    private void loadAdmittedPatients() {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT id, patient_name, patient_id, age, gender, ward_type, room_number, " +
                    "DATE_FORMAT(admission_date, '%d/%m/%Y') as admission_date, doctor_name, diagnosis, contact_number " +
                    "FROM admissions WHERE discharge_date IS NULL ORDER BY admission_date DESC";

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            Vector<String> columnNames = new Vector<>();
            columnNames.add("ID");
            columnNames.add("Patient Name");
            columnNames.add("Patient ID");
            columnNames.add("Age");
            columnNames.add("Gender");
            columnNames.add("Ward Type");
            columnNames.add("Room No");
            columnNames.add("Admission Date");
            columnNames.add("Doctor");
            columnNames.add("Diagnosis");
            columnNames.add("Contact");

            Vector<Vector<Object>> data = new Vector<>();

            while (rs.next()) {
                Vector<Object> row = new Vector<>();
                row.add(rs.getInt("id"));
                row.add(rs.getString("patient_name"));
                row.add(rs.getString("patient_id"));
                row.add(rs.getInt("age"));
                row.add(rs.getString("gender"));
                row.add(rs.getString("ward_type"));
                row.add(rs.getString("room_number"));
                row.add(rs.getString("admission_date"));
                row.add(rs.getString("doctor_name"));
                row.add(rs.getString("diagnosis"));
                row.add(rs.getString("contact_number"));
                data.add(row);
            }

            DefaultTableModel model = new DefaultTableModel(data, columnNames) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

            admittedTable.setModel(model);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void dischargePatient(int admissionId) {
        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to discharge this patient?",
                "Confirm Discharge",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                String query = "UPDATE admissions SET discharge_date = CURDATE() WHERE id = ?";
                PreparedStatement pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, admissionId);
                pstmt.executeUpdate();
                JOptionPane.showMessageDialog(this, "Patient discharged successfully!");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void initializeDatabase() {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String createTableSQL = """
                CREATE TABLE IF NOT EXISTS admissions (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    patient_name VARCHAR(100) NOT NULL,
                    patient_id VARCHAR(50) NOT NULL UNIQUE,
                    age INT NOT NULL,
                    gender VARCHAR(20) NOT NULL,
                    blood_type VARCHAR(10),
                    contact_number VARCHAR(20) NOT NULL,
                    emergency_contact VARCHAR(20) NOT NULL,
                    email VARCHAR(100),
                    address TEXT,
                    ward_type VARCHAR(50) NOT NULL,
                    room_number VARCHAR(10) NOT NULL,
                    admission_date DATE NOT NULL,
                    expected_discharge_date DATE,
                    doctor_name VARCHAR(100) NOT NULL,
                    admission_type VARCHAR(50),
                    diagnosis VARCHAR(100) NOT NULL,
                    symptoms TEXT,
                    medical_history TEXT,
                    insurance_provider VARCHAR(100),
                    insurance_number VARCHAR(50),
                    payment_method VARCHAR(50) NOT NULL,
                    discharge_date DATE,
                    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                )
            """;
            Statement stmt = conn.createStatement();
            stmt.execute(createTableSQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void submitAdmission() {
        if (!validateForm()) return;

        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to admit this patient?",
                "Confirm Admission",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                String insertSQL = """
                    INSERT INTO admissions (
                        patient_name, patient_id, age, gender, blood_type,
                        contact_number, emergency_contact, email, address,
                        ward_type, room_number, admission_date, expected_discharge_date,
                        doctor_name, admission_type, diagnosis, symptoms,
                        medical_history, insurance_provider, insurance_number,
                        payment_method
                    ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;

                PreparedStatement pstmt = conn.prepareStatement(insertSQL);
                pstmt.setString(1, patientNameField.getText().trim());
                pstmt.setString(2, patientIdField.getText().trim());
                pstmt.setInt(3, Integer.parseInt(ageField.getText().trim()));
                pstmt.setString(4, (String) genderCombo.getSelectedItem());
                pstmt.setString(5, bloodTypeCombo.getSelectedIndex() > 0 ? (String) bloodTypeCombo.getSelectedItem() : null);
                pstmt.setString(6, contactField.getText().trim());
                pstmt.setString(7, emergencyContactField.getText().trim());
                pstmt.setString(8, emailField.getText().trim().isEmpty() ? null : emailField.getText().trim());
                pstmt.setString(9, addressArea.getText().trim());
                pstmt.setString(10, (String) wardTypeCombo.getSelectedItem());
                pstmt.setString(11, (String) roomNumberCombo.getSelectedItem());
                pstmt.setDate(12, new java.sql.Date(((java.util.Date) admissionDateSpinner.getValue()).getTime()));

                if (expectedDischargeDateSpinner.getValue() != null) {
                    pstmt.setDate(13, new java.sql.Date(((java.util.Date) expectedDischargeDateSpinner.getValue()).getTime()));
                } else {
                    pstmt.setNull(13, java.sql.Types.DATE);
                }

                pstmt.setString(14, (String) doctorCombo.getSelectedItem());
                pstmt.setString(15, (String) admissionTypeCombo.getSelectedItem());
                pstmt.setString(16, (String) diagnosisCombo.getSelectedItem());
                pstmt.setString(17, symptomsArea.getText().trim().isEmpty() ? null : symptomsArea.getText().trim());
                pstmt.setString(18, medicalHistoryArea.getText().trim().isEmpty() ? null : medicalHistoryArea.getText().trim());
                pstmt.setString(19, insuranceProviderCombo.getSelectedIndex() > 0 ? (String) insuranceProviderCombo.getSelectedItem() : null);
                pstmt.setString(20, insuranceNumberField.getText().trim().isEmpty() ? null : insuranceNumberField.getText().trim());
                pstmt.setString(21, (String) paymentMethodCombo.getSelectedItem());

                pstmt.executeUpdate();

                JOptionPane.showMessageDialog(this, "Patient admitted successfully!");
                clearForm();
                cardLayout.show(mainContainer, "SELECTION");

            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
            }
        }
    }

    private JTextField createStyledTextField() {
        JTextField field = new JTextField();
        styleComponent(field);
        return field;
    }

    private JComboBox<String> createStyledComboBox(String[] items) {
        JComboBox<String> comboBox = new JComboBox<>(items);
        styleComponent(comboBox);
        return comboBox;
    }

    private void styleComponent(JComponent component) {
        component.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        component.setBackground(Color.WHITE);
        component.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR),
                new EmptyBorder(8, 10, 8, 10)
        ));
    }

    private JPanel createLabeledField(String labelText, ComponentSupplier componentSupplier) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        label.setForeground(TEXT_COLOR);
        label.setBorder(new EmptyBorder(0, 0, 5, 0));

        JComponent component = componentSupplier.getComponent();

        panel.add(label, BorderLayout.NORTH);
        panel.add(component, BorderLayout.CENTER);

        return panel;
    }

    private JButton createStyledButton(String text, Color bgColor, Color fgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(bgColor);
        button.setForeground(fgColor);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(bgColor.darker(), 1),
                new EmptyBorder(10, 25, 10, 25)
        ));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        if (bgColor != Color.WHITE) {
            button.setBorderPainted(false);
        }

        return button;
    }

    private void addHoverEffect(JButton button, Color normalColor, Color hoverColor) {
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(hoverColor);
            }
            public void mouseExited(MouseEvent e) {
                button.setBackground(normalColor);
            }
        });
    }

    private boolean validateForm() {
        if (patientNameField.getText().trim().isEmpty()) { showError("Please enter patient name"); return false; }
        if (patientIdField.getText().trim().isEmpty()) { showError("Please enter patient ID"); return false; }
        if (ageField.getText().trim().isEmpty()) { showError("Please enter patient age"); return false; }
        if (genderCombo.getSelectedIndex() == 0) { showError("Please select gender"); return false; }
        if (contactField.getText().trim().isEmpty()) { showError("Please enter contact number"); return false; }
        if (emergencyContactField.getText().trim().isEmpty()) { showError("Please enter emergency contact"); return false; }
        if (addressArea.getText().trim().isEmpty()) { showError("Please enter address"); return false; }
        if (wardTypeCombo.getSelectedIndex() == 0) { showError("Please select ward type"); return false; }
        if (roomNumberCombo.getSelectedIndex() == 0) { showError("Please select room number"); return false; }
        if (doctorCombo.getSelectedIndex() == 0) { showError("Please select doctor"); return false; }
        if (admissionTypeCombo.getSelectedIndex() == 0) { showError("Please select admission type"); return false; }
        if (diagnosisCombo.getSelectedIndex() == 0) { showError("Please select diagnosis"); return false; }
        if (paymentMethodCombo.getSelectedIndex() == 0) { showError("Please select payment method"); return false; }
        if (!termsCheckBox.isSelected()) { showError("Please agree to terms"); return false; }
        return true;
    }

    private void resetForm() {
        int confirm = JOptionPane.showConfirmDialog(this, "Reset form?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) clearForm();
    }

    private void clearForm() {
        if (patientNameField != null) patientNameField.setText("");
        if (patientIdField != null) patientIdField.setText("");
        if (ageField != null) ageField.setText("");
        if (genderCombo != null) genderCombo.setSelectedIndex(0);
        if (bloodTypeCombo != null) bloodTypeCombo.setSelectedIndex(0);
        if (contactField != null) contactField.setText("");
        if (emergencyContactField != null) emergencyContactField.setText("");
        if (emailField != null) emailField.setText("");
        if (addressArea != null) addressArea.setText("");
        if (wardTypeCombo != null) wardTypeCombo.setSelectedIndex(0);
        if (roomNumberCombo != null) roomNumberCombo.setSelectedIndex(0);
        if (admissionDateSpinner != null) admissionDateSpinner.setValue(new java.util.Date());
        if (expectedDischargeDateSpinner != null) expectedDischargeDateSpinner.setValue(null);
        if (doctorCombo != null) doctorCombo.setSelectedIndex(0);
        if (admissionTypeCombo != null) admissionTypeCombo.setSelectedIndex(0);
        if (diagnosisCombo != null) diagnosisCombo.setSelectedIndex(0);
        if (symptomsArea != null) symptomsArea.setText("");
        if (medicalHistoryArea != null) medicalHistoryArea.setText("");
        if (insuranceProviderCombo != null) insuranceProviderCombo.setSelectedIndex(0);
        if (insuranceNumberField != null) insuranceNumberField.setText("");
        if (paymentMethodCombo != null) paymentMethodCombo.setSelectedIndex(0);
        if (termsCheckBox != null) termsCheckBox.setSelected(false);
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Validation Error", JOptionPane.ERROR_MESSAGE);
    }

    @FunctionalInterface
    private interface ComponentSupplier {
        JComponent getComponent();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Hospital Management System - Admissions");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1200, 800);
            frame.setLocationRelativeTo(null);
            frame.add(new Admissions());
            frame.setVisible(true);
        });
    }
}