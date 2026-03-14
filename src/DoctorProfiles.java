import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class DoctorProfiles extends JPanel {

    JPanel container;

    public DoctorProfiles() {
        this.setLayout(null);
        this.setBackground(Color.LIGHT_GRAY);

        JLabel pageHeading = new JLabel("Doctor Profiles");
        pageHeading.setFont(new Font("SansSerif", Font.PLAIN, 24));
        pageHeading.setBounds(70, 18, 200, 50);
        this.add(pageHeading);

        /* Search filters (optional) */
        JLabel byId = new JLabel("ID:");
        byId.setBounds(50,70,80,30);
        this.add(byId);

        JTextField filterById = new JTextField();
        filterById.setBounds(80,70,100,30);
        this.add(filterById);

        JLabel name = new JLabel("Name:");
        name.setBounds(200,70,50,30);
        this.add(name);

        JTextField nameField = new JTextField();
        nameField.setBounds(250,70,120,30);
        this.add(nameField);

        JLabel speciality = new JLabel("Speciality:");
        speciality.setBounds(380,70,70,30);
        this.add(speciality);

        String[] specialities = {"Generalist","Cardiology","Neurology",
                "Orthopedics","Pediatrics","Dermatology"};
        JComboBox<String> specialityBox = new JComboBox<>(specialities);
        specialityBox.setBounds(450,70,130,30);
        this.add(specialityBox);

        JLabel availability = new JLabel("Availability:");
        availability.setBounds(600,70,80,30);
        this.add(availability);

        String[] availabilityOptions = {"Available","Vacation","Suspended","Retired"};
        JComboBox<String> availabilityBox = new JComboBox<>(availabilityOptions);
        availabilityBox.setBounds(680,70,130,30);
        this.add(availabilityBox);

        JButton searchButton = new JButton("Search");
        searchButton.setBounds(830,70,100,30);
        this.add(searchButton);

        /* -------- Container that will hold the rows -------- */
        container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setBackground(Color.WHITE);

        /* -------- Scroll Pane -------- */
        JScrollPane scrollPane = new JScrollPane(container);
        scrollPane.setBounds(50,110,880,500);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16); // smooth scroll
        this.add(scrollPane);

        /* -------- Load doctors -------- */
        loadDoctors(); // call after GUI components are ready
    }

    public void loadDoctors() {
        try {
            // Create an instance of your Database class
            Database db = new Database();
            Connection conn = db.getConnection(); // get the connection from that instance

            String sql = "SELECT doctor_id, full_name, specialization, availability FROM doctors";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                addDoctorRow(
                        rs.getString("doctor_id"),
                        rs.getString("full_name"),
                        rs.getString("specialization"),
                        rs.getString("availability")
                );
            }

            db.close(); // close connection when done

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addDoctorRow(String id, String fullName, String specialization, String availability) {

        JPanel row = new JPanel(new GridLayout(1,5,10,10));
        row.setMaximumSize(new Dimension(850,50));
        row.setBackground(new Color(240,240,240));
        row.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

        row.add(new JLabel(id));
        row.add(new JLabel(fullName));
        row.add(new JLabel(specialization));
        row.add(new JLabel(availability));

        JButton viewButton = new JButton("View");

        viewButton.addActionListener(e -> {
            new DoctorDetails(Integer.parseInt(id));
        });

        row.add(viewButton);

        container.add(row);
        container.add(Box.createVerticalStrut(5));

        container.revalidate();
        container.repaint();
    }

}