import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.regex.Pattern;

public class PatientProfiles extends JPanel {

    JPanel container;
    JPanel dashboardCenter;
    JPanel recentContainer; // for sliding panel

    public PatientProfiles(JPanel dashboardCenter) {
        this.dashboardCenter = dashboardCenter;

        this.setLayout(null);
        this.setBackground(Color.LIGHT_GRAY);

        JLabel pageHeading = new JLabel("Patient Profiles");
        pageHeading.setFont(new Font("SansSerif", Font.PLAIN, 24));
        pageHeading.setBounds(70, 18, 300, 40);
        this.add(pageHeading);

        // ===================== FILTER =====================

        JLabel byId = new JLabel("ID:");
        byId.setBounds(50,70,50,30);
        this.add(byId);

        JTextField filterById = new JTextField();
        filterById.setBounds(80,70,100,30);
        this.add(filterById);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(200,70,60,30);
        this.add(nameLabel);

        JTextField nameField = new JTextField();
        nameField.setBounds(250,70,150,30);
        this.add(nameField);

        JButton searchButton = new JButton("Search");
        searchButton.setBounds(420,70,100,30);
        this.add(searchButton);

        JButton reloadButton = new JButton("Reload");
        reloadButton.setBounds(530,70,100,30);
        this.add(reloadButton);

        // ===================== PATIENT LIST =====================

        container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setBackground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(container);
        scrollPane.setBounds(50,110,750,690);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        this.add(scrollPane);

        searchButton.addActionListener(e -> applyFilters(filterById,nameField));

        reloadButton.addActionListener(e -> {
            filterById.setText("");
            nameField.setText("");
            refreshList();
        });

        loadPatients();

        // ===================== RIGHT PANEL (RECENT PATIENTS) =====================

        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBounds(830,110,350,690);
        rightPanel.setBackground(Color.WHITE);
        this.add(rightPanel);

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        JLabel title = new JLabel("Recent Patients");
        title.setFont(new Font("SansSerif", Font.BOLD, 18));

        JButton reloadRecentBtn = new JButton("Reload");

// 👉 Action: reload recent patients
        reloadRecentBtn.addActionListener(e -> loadRecentPatients());

        headerPanel.add(title, BorderLayout.WEST);
        headerPanel.add(reloadRecentBtn, BorderLayout.EAST);

        rightPanel.add(headerPanel, BorderLayout.NORTH);

        recentContainer = new JPanel();
        recentContainer.setLayout(new BoxLayout(recentContainer, BoxLayout.Y_AXIS));
        recentContainer.setBackground(Color.WHITE);

        JScrollPane rightScroll = new JScrollPane(recentContainer);
        rightScroll.getVerticalScrollBar().setUnitIncrement(16);
        rightPanel.add(rightScroll, BorderLayout.CENTER);

        loadRecentPatients();

        // ===================== ADD PATIENT BUTTON =====================


    }

    // ===================== ADD PATIENT POPUP =====================



    // ===================== LOAD RECENT PATIENTS =====================

    private void loadRecentPatients(){

        recentContainer.removeAll();

        try (Connection conn = new Database().getConnection()) {

            String sql = "SELECT full_name, email, created_at FROM patients ORDER BY created_at DESC";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){

                JPanel card = new JPanel(new GridLayout(3,1));
                card.setMaximumSize(new Dimension(300,80));
                card.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                        BorderFactory.createEmptyBorder(5,10,5,10)
                ));
                card.setBackground(new Color(245,245,245));

                JLabel name = new JLabel("Name: " + rs.getString("full_name"));
                JLabel email = new JLabel("Email: " + rs.getString("email"));
                JLabel date = new JLabel("Added: " + rs.getString("created_at"));

                card.add(name);
                card.add(email);
                card.add(date);

                recentContainer.add(card);
                recentContainer.add(Box.createVerticalStrut(10));
            }

            recentContainer.revalidate();
            recentContainer.repaint();

        } catch(Exception e){
            e.printStackTrace();
        }
    }

    // ===================== REFRESH =====================

    private void refreshList(){
        container.removeAll();
        loadPatients();
        container.revalidate();
        container.repaint();
    }

    // ===================== LOAD PATIENTS =====================

    public void loadPatients(){

        try (Connection conn = new Database().getConnection()) {

            String sql = "SELECT patient_id, full_name, phone FROM patients";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){

                String id = rs.getString("patient_id");

                JPanel row = new JPanel(new GridLayout(1,4,0,10));
                row.setMaximumSize(new Dimension(700,40));
                row.setBackground(new Color(240,240,240));

                row.add(new JLabel(id));
                row.add(new JLabel(rs.getString("full_name")));
                row.add(new JLabel(rs.getString("phone")));

                JButton viewButton = new JButton("View");

                viewButton.addActionListener(e -> {
                    PatientDetails details = new PatientDetails(Integer.parseInt(id));
                    dashboardCenter.add(details, "patientDetails");
                    CardLayout cl = (CardLayout) dashboardCenter.getLayout();
                    cl.show(dashboardCenter, "patientDetails");
                });

                row.add(viewButton);

                container.add(row);
                container.add(Box.createVerticalStrut(5));
            }

        } catch(Exception e){
            e.printStackTrace();
        }
    }

    // ===================== FILTER =====================

    private void applyFilters(JTextField idField,JTextField nameField){

        try (Connection conn = new Database().getConnection()) {

            container.removeAll();

            String sql="SELECT patient_id,full_name,phone FROM patients WHERE 1=1";

            if(!idField.getText().isEmpty()){
                sql+=" AND patient_id=?";
            }

            if(!nameField.getText().isEmpty()){
                sql+=" AND full_name LIKE ?";
            }

            PreparedStatement ps=conn.prepareStatement(sql);

            int index=1;

            if(!idField.getText().isEmpty()){
                ps.setString(index++,idField.getText());
            }

            if(!nameField.getText().isEmpty()){
                ps.setString(index++,"%"+nameField.getText()+"%");
            }

            ResultSet rs=ps.executeQuery();

            while(rs.next()){

                String id = rs.getString("patient_id");

                JPanel row = new JPanel(new GridLayout(1,4,0,10));
                row.setMaximumSize(new Dimension(700,40));
                row.setBackground(new Color(240,240,240));

                row.add(new JLabel(id));
                row.add(new JLabel(rs.getString("full_name")));
                row.add(new JLabel(rs.getString("phone")));

                JButton viewButton = new JButton("View");

                viewButton.addActionListener(e -> {
                    PatientDetails details = new PatientDetails(Integer.parseInt(id));
                    dashboardCenter.add(details, "patientDetails");
                    CardLayout cl = (CardLayout) dashboardCenter.getLayout();
                    cl.show(dashboardCenter, "patientDetails");
                });

                row.add(viewButton); // ✅ THIS LINE WAS MISSING

                container.add(row);
                container.add(Box.createVerticalStrut(5));
            }

            container.revalidate();
            container.repaint();

        } catch(Exception e){
            e.printStackTrace();
        }
    }

    // ===================== UTIL =====================

    private boolean validateEmail(String email){
        String regex="^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return Pattern.matches(regex,email);
    }
}