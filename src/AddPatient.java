import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.regex.Pattern;

public class AddPatient extends JPanel {

    JPanel dashboardCenter;

    // Fields
    JTextField fullName, nationalId, phone, email, address;
    JSpinner ageSpinner;
    JComboBox<String> genderBox, maritalStatusBox, bloodGroupBox, allergiesBox, diseasesBox, medicationBox;
    JTextArea messageArea;

    public AddPatient(JPanel dashboardCenter) {
        this.dashboardCenter = dashboardCenter;





        setLayout(null);
        setBackground(new Color(230, 230, 230));

        // Main white panel
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.WHITE);
        panel.setBounds(20, 50, 1150, 770);
        add(panel);

        // Title
        JLabel title = new JLabel("ADD PATIENT");
        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setBounds(40, 20, 400, 40);
        panel.add(title);

        JLabel subTitle = new JLabel("Patient Registration");
        subTitle.setFont(new Font("Arial", Font.BOLD, 18));
        subTitle.setBounds(450, 80, 300, 30);
        panel.add(subTitle);

        int leftX = 50, rightX = 650;
        int y = 150, gap = 50;

        // ---------------- LEFT COLUMN ----------------

        fullName = createField(panel, "Full Name:", leftX, y); y += gap;
        ageSpinner = createSpinner(panel, "Age:", leftX, y, 1, 1, 120, 1); y += gap;
        genderBox = createDropdown(panel, "Gender:", leftX, y, new String[]{"Male","Female","Other"}); y += gap;
        nationalId = createField(panel, "National ID:", leftX, y); y += gap;
        maritalStatusBox = createDropdown(panel, "Marital Status:", leftX, y, new String[]{"Single","Married","Divorced","Widowed"}); y += gap;
        phone = createField(panel, "Phone:", leftX, y); y += gap;
        email = createField(panel, "Email:", leftX, y); y += gap;
        address = createField(panel, "Address:", leftX, y); y += gap;

        // ---------------- RIGHT COLUMN ----------------

        int ry = 150;

        bloodGroupBox = createDropdown(panel, "Blood Group:", rightX, ry, new String[]{"A+","A-","B+","B-","AB+","AB-","O+","O-"}); ry += gap;
        allergiesBox = createDropdown(panel, "Allergies:", rightX, ry, new String[]{"None","Dust","Pollen","Food","Medicine"}); ry += gap;
        diseasesBox = createDropdown(panel, "Chronic Disease:", rightX, ry, new String[]{"None","Diabetes","Asthma","Hypertension"}); ry += gap;
        medicationBox = createDropdown(panel, "Medication:", rightX, ry, new String[]{"None","Insulin","Inhaler","Blood Pressure"}); ry += gap;

        // Message
        JLabel messageLabel = new JLabel("Message:");
        messageLabel.setBounds(rightX, ry, 120, 25);
        messageLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        panel.add(messageLabel);

        messageArea = new JTextArea();
        JScrollPane scroll = new JScrollPane(messageArea);
        scroll.setBounds(rightX, ry + 30, 350, 100);
        panel.add(scroll);
        ry += 140;

        // ---------------- BUTTONS ----------------

        JButton saveBtn = new JButton("Save Patient");
        saveBtn.setBounds(450, 680, 150, 40);
        saveBtn.setBackground(new Color(0, 123, 255));
        saveBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        panel.add(saveBtn);

        saveBtn.addActionListener(e -> savePatient());




// Back button (anticlockwise)
        JButton backBtn = new JButton("⟲");
        backBtn.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 20));
        backBtn.setBounds(50, 5, 40, 30);
        add(backBtn);

// Optional Forward button (clockwise)
        JButton forwardBtn = new JButton("⟳");
        forwardBtn.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 20));
        forwardBtn.setBounds(120, 5, 40, 30);
        add(forwardBtn);

// Back action
        backBtn.addActionListener(e -> {
            CardLayout cl = (CardLayout) dashboardCenter.getLayout();
            cl.show(dashboardCenter, "Administration");
        });

// Forward action (optional - change target if needed)
        forwardBtn.addActionListener(e -> {
            CardLayout cl = (CardLayout) dashboardCenter.getLayout();
            cl.next(dashboardCenter); // or use cl.show(...)
        });


    }

    // ---------------- SAVE PATIENT ----------------
    private void savePatient() {
        if(!validateEmail(email.getText())){
            JOptionPane.showMessageDialog(this,"Invalid Email");
            return;
        }

        try (Connection conn = new Database().getConnection()) {

            String sql = "INSERT INTO patients(full_name,age,gender,national_id,marital_status,phone,email,address,blood_group,allergies,chronic_disease,medication,message,created_at)"
                    + " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,NOW())";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, fullName.getText());
            ps.setInt(2, (Integer) ageSpinner.getValue());
            ps.setString(3, (String) genderBox.getSelectedItem());
            ps.setString(4, nationalId.getText());
            ps.setString(5, (String) maritalStatusBox.getSelectedItem());
            ps.setString(6, phone.getText());
            ps.setString(7, email.getText());
            ps.setString(8, address.getText());
            ps.setString(9, (String) bloodGroupBox.getSelectedItem());
            ps.setString(10, (String) allergiesBox.getSelectedItem());
            ps.setString(11, (String) diseasesBox.getSelectedItem());
            ps.setString(12, (String) medicationBox.getSelectedItem());
            ps.setString(13, messageArea.getText());

            ps.executeUpdate();
            JOptionPane.showMessageDialog(this,"Patient Saved Successfully!");

            // Back to profile list
            CardLayout cl = (CardLayout) dashboardCenter.getLayout();
            cl.show(dashboardCenter, "patientProfiles");

        } catch(Exception ex){
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this,"Database Error");
        }
    }





    // ---------------- UTIL ----------------
    private JTextField createField(JPanel panel, String labelText, int x, int y){
        JLabel label = new JLabel(labelText);
        label.setBounds(x, y, 120, 25);
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        panel.add(label);

        JTextField field = new JTextField();
        field.setBounds(x + 150, y, 350, 30);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200,200,200), 1, true),
                BorderFactory.createEmptyBorder(5,10,5,10)
        ));
        panel.add(field);
        return field;
    }

    private JComboBox<String> createDropdown(JPanel panel, String labelText, int x, int y, String[] values){
        JLabel label = new JLabel(labelText);
        label.setBounds(x, y, 120, 25);
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        panel.add(label);

        JComboBox<String> box = new JComboBox<>(values);
        box.setBounds(x + 150, y, 350, 30);
        box.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        panel.add(box);
        return box;
    }

    private JSpinner createSpinner(JPanel panel, String labelText, int x, int y, int value, int min, int max, int step){
        JLabel label = new JLabel(labelText);
        label.setBounds(x, y, 120, 25);
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        panel.add(label);

        JSpinner spinner = new JSpinner(new SpinnerNumberModel(value, min, max, step));
        spinner.setBounds(x + 150, y, 350, 30);
        panel.add(spinner);
        return spinner;
    }

    private boolean validateEmail(String email){
        String regex="^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return Pattern.matches(regex,email);
    }


}