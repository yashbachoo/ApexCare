import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class PatientDetails extends JPanel {

    int patientId;

    JPanel personalPanel;
    JPanel medicalPanel;

    public PatientDetails(int patientId) {

        this.patientId = patientId;

        setLayout(new BorderLayout());
        setBackground(Color.LIGHT_GRAY);

        // ===================== TITLE =====================
        JLabel pageHeading = new JLabel("Patient Details", JLabel.CENTER);
        pageHeading.setFont(new Font("SansSerif", Font.BOLD, 24));
        pageHeading.setBorder(BorderFactory.createEmptyBorder(20,0,20,0));
        add(pageHeading, BorderLayout.NORTH);

        // ===================== MAIN PANEL =====================
        JPanel mainPanel = new JPanel(new GridLayout(1,2,40,0));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20,40,20,40));
        mainPanel.setBackground(Color.LIGHT_GRAY);

        // ===================== PERSONAL PANEL =====================
        personalPanel = new JPanel();
        personalPanel.setLayout(new BoxLayout(personalPanel, BoxLayout.Y_AXIS));
        personalPanel.setBackground(Color.WHITE);
        personalPanel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        JLabel personalTitle = new JLabel("Personal Information");
        personalTitle.setFont(new Font("SansSerif", Font.BOLD, 20));
        personalTitle.setForeground(new Color(0,128,128));

        personalPanel.add(personalTitle);
        personalPanel.add(Box.createVerticalStrut(20));

        // ===================== MEDICAL PANEL =====================
        medicalPanel = new JPanel();
        medicalPanel.setLayout(new BoxLayout(medicalPanel, BoxLayout.Y_AXIS));
        medicalPanel.setBackground(Color.WHITE);
        medicalPanel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        JLabel medicalTitle = new JLabel("Medical Information");
        medicalTitle.setFont(new Font("SansSerif", Font.BOLD, 20));
        medicalTitle.setForeground(new Color(0,128,128));

        medicalPanel.add(medicalTitle);
        medicalPanel.add(Box.createVerticalStrut(20));

        // ===================== SCROLL SUPPORT =====================
        JScrollPane leftScroll = new JScrollPane(personalPanel);
        JScrollPane rightScroll = new JScrollPane(medicalPanel);

        leftScroll.setBorder(null);
        rightScroll.setBorder(null);

        mainPanel.add(leftScroll);
        mainPanel.add(rightScroll);

        add(mainPanel, BorderLayout.CENTER);

        // ===================== LOAD DATA =====================
        loadPatient();
    }

    // ===================== LOAD PATIENT =====================
    private void loadPatient() {

        try (Connection conn = new Database().getConnection()) {

            String query = "SELECT * FROM patients WHERE patient_id = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, patientId);

            ResultSet rs = ps.executeQuery();

            if(rs.next()) {

                // PERSONAL INFO
                addLabel(personalPanel, "Name: " + safe(rs.getString("full_name")));
                addLabel(personalPanel, "Age: "+ safe(rs.getString("age")));
                addLabel(personalPanel, "Gender: " + safe(rs.getString("gender")));
                addLabel(personalPanel, "Phone: " + safe(rs.getString("phone")));
                addLabel(personalPanel, "Email: " + safe(rs.getString("email")));
                addLabel(personalPanel, "Address: " + safe(rs.getString("address")));

                // MEDICAL INFO
                addLabel(medicalPanel, "Blood Group: " + safe(rs.getString("blood_group")));
                addLabel(medicalPanel, "Allergies: " + safe(rs.getString("allergies")));
                addLabel(medicalPanel,"Chronic Disease: " + safe(rs.getString("Chronic_Disease")));
                addLabel(medicalPanel, "Medication: " + safe(rs.getString("medication")));

            }

            personalPanel.revalidate();
            personalPanel.repaint();
            medicalPanel.revalidate();
            medicalPanel.repaint();

        } catch(Exception e){
            e.printStackTrace();
        }
    }

    // ===================== HELPER: ADD LABEL =====================
    private void addLabel(JPanel panel, String text){
        JLabel label = new JLabel(text);
        label.setFont(new Font("SansSerif", Font.PLAIN, 14));
        label.setBorder(BorderFactory.createEmptyBorder(5,0,5,0));
        panel.add(label);
    }

    // ===================== HELPER: NULL SAFE =====================
    private String safe(String value){
        return (value == null || value.isEmpty()) ? "N/A" : value;
    }
}