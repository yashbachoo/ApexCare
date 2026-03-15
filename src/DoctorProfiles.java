import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class DoctorProfiles extends JPanel {

    JPanel container;
    JPanel dashboardCenter;

    public DoctorProfiles(JPanel dashboardCenter) {
        this.setLayout(null);
        this.setBackground(Color.LIGHT_GRAY);
        this.dashboardCenter = dashboardCenter; // IMPORTANT

        JLabel pageHeading = new JLabel("Doctor Profiles");
        pageHeading.setFont(new Font("SansSerif", Font.PLAIN, 24));
        pageHeading.setBounds(70, 18, 200, 50);
        this.add(pageHeading);

        JLabel Title = new JLabel("Available Doctors");
        Title.setFont(new Font("SansSerif", Font.PLAIN, 24));
        Title.setBounds(870, 70, 250, 40);
        this.add(Title);

        //########## Container that will hold doctor rows ##########
        container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setBackground(Color.WHITE);

        // Scroll pane for doctor rows
        JScrollPane scrollPane = new JScrollPane(container);
        scrollPane.setBounds(50, 110, 800, 690);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16); // smooth scroll
        this.add(scrollPane);

        //########## Search Filter ##########
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

        String[] specialities = {
                "--Select Speciality--",
                "Cardiologist",
                "Dermatologist",
                "Endocrinologist",
                "Gastroenterologist",
                "Hematologist",
                "Nephrologist",
                "Neurologist",
                "Oncologist",
                "Ophthalmologist",
                "Orthopedic Surgeon",
                "Otolaryngologist (ENT)",
                "Pediatrician",
                "Psychiatrist",
                "Pulmonologist",
                "Radiologist",
                "Rheumatologist",
                "Urologist",
                "Gynecologist",
                "Obstetrician",
                "Anesthesiologist",
                "Pathologist",
                "Plastic Surgeon",
                "Emergency Medicine",
                "Family Medicine",
                "Internal Medicine",
                "Infectious Disease Specialist",
                "Geriatrician",
                "Sports Medicine",
                "Allergist / Immunologist"};

        JComboBox<String> specialityBox = new JComboBox<>(specialities);
        specialityBox.setBounds(450,70,130,30);
        this.add(specialityBox);

        JButton searchButton = new JButton("Search");
        searchButton.setBounds(600,70,100,30);
        this.add(searchButton);

        // Apply filters on click
        searchButton.addActionListener(e -> applyFilters(filterById, nameField, specialityBox));

        //Reload buttons
        JButton reloadButton = new JButton("Reload");
        reloadButton.setBounds(710,70,100,30);
        this.add(reloadButton);

        // Reload data
        reloadButton.addActionListener(e -> {
            filterById.setText("");
            nameField.setText("");
            specialityBox.setSelectedIndex(0);

            container.removeAll();
            loadDoctors();

            container.revalidate();
            container.repaint();
        });

        //########## Load all doctors initially ##########
        loadDoctors();






        //################################################## Right-side panel for available doctors ##################################################


        JPanel availableDoctorsPanel = new JPanel();
        availableDoctorsPanel.setLayout(new BoxLayout(availableDoctorsPanel, BoxLayout.Y_AXIS));
        availableDoctorsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        availableDoctorsPanel.setBackground(Color.WHITE);





        JScrollPane availableScroll = new JScrollPane(availableDoctorsPanel);
        availableScroll.setBounds(870, 110, 250, 690);
        availableScroll.getVerticalScrollBar().setUnitIncrement(16);
        this.add(availableScroll);

        // Load available doctors
        try {
            Database db = new Database();
            Connection conn = db.getConnection();

            String sql = "SELECT doctor_id, full_name, specialization FROM doctors WHERE availability = 'yes'";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                JPanel row = new JPanel();
                row.setLayout(new BoxLayout(row, BoxLayout.X_AXIS));
                row.setMaximumSize(new Dimension(250, 50));
                row.setBackground(new Color(230, 250, 230));
                row.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
                row.setAlignmentX(Component.LEFT_ALIGNMENT);

                JLabel idLabel = new JLabel(rs.getString("doctor_id"));
                idLabel.setPreferredSize(new Dimension(50, 30));
                row.add(idLabel);
                row.add(Box.createHorizontalStrut(10));

                JLabel nameLabel = new JLabel(rs.getString("full_name"));
                nameLabel.setPreferredSize(new Dimension(150, 30));
                row.add(nameLabel);
                row.add(Box.createHorizontalStrut(10));

                JLabel specLabel = new JLabel(rs.getString("specialization"));
                specLabel.setPreferredSize(new Dimension(100, 30));
                row.add(specLabel);

                availableDoctorsPanel.add(row);
                availableDoctorsPanel.add(Box.createVerticalStrut(5));
            }

            db.close();
            availableDoctorsPanel.revalidate();
            availableDoctorsPanel.repaint();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //########## Load all doctors ##########
    public void loadDoctors() {
        try {
            Database db = new Database();
            Connection conn = db.getConnection();

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

            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //########## Add a doctor row ##########
    public void addDoctorRow(String id, String fullName, String specialization, String availability) {
        JPanel row = new JPanel(new GridLayout(1,5,0,10));
        row.setMaximumSize(new Dimension(850,50));
        row.setBackground(new Color(240,240,240));
        row.setBorder(BorderFactory.createEmptyBorder(5,0,5,5));

        row.add(new JLabel(id));
        row.add(new JLabel(fullName));
        row.add(new JLabel(specialization));
        row.add(new JLabel(availability));

        JButton viewButton = new JButton("View");
        viewButton.addActionListener(e -> {
            DoctorDetails details = new DoctorDetails(Integer.parseInt(id));
            dashboardCenter.add("doctorDetails", details);
            CardLayout cl = (CardLayout) dashboardCenter.getLayout();
            cl.show(dashboardCenter, "doctorDetails");
        });
        row.add(viewButton);

        container.add(row);
        container.add(Box.createVerticalStrut(5));
        container.revalidate();
        container.repaint();
    }

    //########## Apply search filters ##########
    private void applyFilters(JTextField idField, JTextField nameField, JComboBox<String> specialityBox) {
        try {
            container.removeAll();

            Database db = new Database();
            Connection conn = db.getConnection();

            String sql = "SELECT doctor_id, full_name, specialization, availability FROM doctors WHERE 1=1";

            if (!idField.getText().isEmpty()) {
                sql += " AND doctor_id = ?";
            }
            if (!nameField.getText().isEmpty()) {
                sql += " AND full_name LIKE ?";
            }
            if (specialityBox.getSelectedIndex() != 0) {
                sql += " AND specialization = ?";
            }

            PreparedStatement ps = conn.prepareStatement(sql);

            int paramIndex = 1;
            if (!idField.getText().isEmpty()) {
                ps.setString(paramIndex++, idField.getText());
            }
            if (!nameField.getText().isEmpty()) {
                ps.setString(paramIndex++, "%" + nameField.getText() + "%");
            }
            if (specialityBox.getSelectedIndex() != 0) {
                ps.setString(paramIndex++, (String) specialityBox.getSelectedItem());
            }

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                addDoctorRow(
                        rs.getString("doctor_id"),
                        rs.getString("full_name"),
                        rs.getString("specialization"),
                        rs.getString("availability")
                );
            }

            db.close();
            container.revalidate();
            container.repaint();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}