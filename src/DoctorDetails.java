import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class DoctorDetails extends JPanel {

    Connection conn;
    int doctorId;

    JPanel personalPanel;
    JPanel professionalPanel;

    public DoctorDetails(int doctorId) {

        this.doctorId = doctorId;

        setLayout(new BorderLayout());
        setBackground(Color.LIGHT_GRAY);

        JLabel pageHeading = new JLabel("Doctor Details", JLabel.CENTER);
        pageHeading.setFont(new Font("SansSerif", Font.BOLD, 24));
        pageHeading.setBorder(BorderFactory.createEmptyBorder(20,0,20,0));
        add(pageHeading, BorderLayout.NORTH);

        JPanel mainPanel = new JPanel(new GridLayout(1,2,40,0));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20,40,20,40));
        mainPanel.setBackground(Color.LIGHT_GRAY);

        personalPanel = new JPanel();
        personalPanel.setLayout(new BoxLayout(personalPanel, BoxLayout.Y_AXIS));
        personalPanel.setBackground(Color.LIGHT_GRAY);



        professionalPanel = new JPanel();
        professionalPanel.setLayout(new BoxLayout(professionalPanel, BoxLayout.Y_AXIS));
        professionalPanel.setBackground(Color.LIGHT_GRAY);

        JLabel personalTitle = new JLabel("Personal Information");
        personalTitle.setFont(new Font("SansSerif", Font.BOLD, 22));
        personalTitle.setForeground(new Color(0,128,128));

        JLabel professionalTitle = new JLabel("Professional Information");
        professionalTitle.setFont(new Font("SansSerif", Font.BOLD, 22));
        professionalTitle.setForeground(new Color(0,128,128));

        personalPanel.add(personalTitle);
        personalPanel.add(Box.createVerticalStrut(20));

        professionalPanel.add(professionalTitle);
        professionalPanel.add(Box.createVerticalStrut(20));

        mainPanel.add(personalPanel);
        mainPanel.add(professionalPanel);

        add(mainPanel, BorderLayout.CENTER);

        connectDatabase();
        loadDoctor();
    }

    private void connectDatabase() {

        try {

            String url = "jdbc:mysql://localhost:3306/apexcare";
            String user = "root";
            String password = "1231202ya";

            conn = DriverManager.getConnection(url,user,password);

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private void loadDoctor() {

        try {

            String query = "SELECT * FROM doctors WHERE doctor_id = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, doctorId);

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {

                personalPanel.add(new JLabel("Name: " + rs.getString("full_name")));
                personalPanel.add(Box.createVerticalStrut(10));

                personalPanel.add(new JLabel("DOB: " + rs.getString("dob")));
                personalPanel.add(Box.createVerticalStrut(10));

                personalPanel.add(new JLabel("Gender: " + rs.getString("gender")));
                personalPanel.add(Box.createVerticalStrut(10));

                personalPanel.add(new JLabel("Contact: " + rs.getString("contact")));
                personalPanel.add(Box.createVerticalStrut(10));

                personalPanel.add(new JLabel("Email: " + rs.getString("email")));
                personalPanel.add(Box.createVerticalStrut(10));

                personalPanel.add(new JLabel("Address: " + rs.getString("clinic_address")));
                personalPanel.add(Box.createVerticalStrut(10));

                professionalPanel.add(new JLabel("Specialization: " + rs.getString("specialization")));
                professionalPanel.add(Box.createVerticalStrut(10));

                professionalPanel.add(new JLabel("Qualification: " + rs.getString("qualification")));
                professionalPanel.add(Box.createVerticalStrut(10));

                professionalPanel.add(new JLabel("Experience: " + rs.getInt("experience") + " years"));
                professionalPanel.add(Box.createVerticalStrut(10));

                professionalPanel.add(new JLabel("Department: " + rs.getString("department")));
                professionalPanel.add(Box.createVerticalStrut(10));

                professionalPanel.add(new JLabel("Consultation Fee: " + rs.getDouble("consultation_fee")));
                professionalPanel.add(Box.createVerticalStrut(10));

                professionalPanel.add(new JLabel("Availability: " + rs.getString("availability")));
                professionalPanel.add(Box.createVerticalStrut(10));
            }

        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}


