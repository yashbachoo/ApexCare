import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Date;


public class AddDoctorPage extends JPanel {

    JTextField nameField, dobField, specializationField, qualificationField,
            experienceField, departmentField, contactField, emailField,
            feeField, availabilityField, addressField;

    JSpinner dobSpinner;

    JComboBox<String> genderBox;
    JTextArea notesArea;

    public AddDoctorPage() {

        setLayout(null);
        setBackground(new Color(230,230,230));

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.WHITE);
        panel.setBounds(20,115,1100,700);
        add(panel);

        // TITLE
        JLabel title = new JLabel("ADD DOCTOR");
        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setBounds(40,20,400,40);
        panel.add(title);

        JLabel doctorID = new JLabel("Doctor Registration");
        doctorID.setFont(new Font("Arial", Font.BOLD, 18));
        doctorID.setBounds(450,80,300,30);
        panel.add(doctorID);

        // LEFT SIDE

        //Name
        JLabel nameLabel = new JLabel("Full Name:");
        nameLabel.setBounds(50,150,150,30);
        panel.add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(200,150,350,35);
        panel.add(nameField);

        //Date of birth
        JLabel dobLabel = new JLabel("Date of Birth:");
        dobLabel.setBounds(50,200,150,30);
        panel.add(dobLabel);



// In constructor
        dobSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor dobEditor = new JSpinner.DateEditor(dobSpinner, "yyyy-MM-dd");
        dobSpinner.setEditor(dobEditor);
        dobSpinner.setBounds(200,200,350,35);
        panel.add(dobSpinner);


        //###########################################################################################################
        //Dropdown for gender

        //Gender
        JLabel genderLabel = new JLabel("Gender:");
        genderLabel.setBounds(50,250,150,30);
        panel.add(genderLabel);

        String[] gender = {"Male","Female","Other"};
        genderBox = new JComboBox<>(gender);
        genderBox.setBounds(200,250,350,35);
        panel.add(genderBox);

        //Specialization
        JLabel specializationLabel = new JLabel("Specialization:");
        specializationLabel.setBounds(50,300,150,30);
        panel.add(specializationLabel);

        specializationField = new JTextField();
        specializationField.setBounds(200,300,350,35);
        panel.add(specializationField);

        //Qualification
        JLabel qualificationLabel = new JLabel("Qualification:");
        qualificationLabel.setBounds(50,350,150,30);
        panel.add(qualificationLabel);

        qualificationField = new JTextField();
        qualificationField.setBounds(200,350,350,35);
        panel.add(qualificationField);

        JLabel experienceLabel = new JLabel("Experience (Years):");
        experienceLabel.setBounds(50,400,150,30);
        panel.add(experienceLabel);

        experienceField = new JTextField();
        experienceField.setBounds(200,400,350,35);
        panel.add(experienceField);

        // RIGHT SIDE

        JLabel departmentLabel = new JLabel("Department:");
        departmentLabel.setBounds(650,150,150,30);
        panel.add(departmentLabel);

        departmentField = new JTextField();
        departmentField.setBounds(800,150,250,35);
        panel.add(departmentField);

        JLabel contactLabel = new JLabel("Contact:");
        contactLabel.setBounds(650,200,150,30);
        panel.add(contactLabel);

        contactField = new JTextField();
        contactField.setBounds(800,200,250,35);
        panel.add(contactField);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(650,250,150,30);
        panel.add(emailLabel);

        emailField = new JTextField();
        emailField.setBounds(800,250,250,35);
        panel.add(emailField);

        JLabel feeLabel = new JLabel("Consultation Fee:");
        feeLabel.setBounds(650,300,150,30);
        panel.add(feeLabel);

        feeField = new JTextField();
        feeField.setBounds(800,300,250,35);
        panel.add(feeField);

        JLabel availabilityLabel = new JLabel("Availability:");
        availabilityLabel.setBounds(650,350,150,30);
        panel.add(availabilityLabel);

        availabilityField = new JTextField();
        availabilityField.setBounds(800,350,250,35);
        panel.add(availabilityField);

        JLabel addressLabel = new JLabel("Clinic Address:");
        addressLabel.setBounds(650,400,150,30);
        panel.add(addressLabel);

        addressField = new JTextField();
        addressField.setBounds(800,400,250,35);
        panel.add(addressField);

        // NOTES

        JLabel notesLabel = new JLabel("Notes:");
        notesLabel.setBounds(650,450,150,30);
        panel.add(notesLabel);

        notesArea = new JTextArea();
        JScrollPane scroll = new JScrollPane(notesArea);
        scroll.setBounds(650,480,400,120);
        panel.add(scroll);

        // BUTTON

        JButton addDoctorBtn = new JButton("Add Doctor");
        addDoctorBtn.setBounds(850,620,200,40);
        panel.add(addDoctorBtn);

        // BUTTON ACTION

        addDoctorBtn.addActionListener(e -> addDoctorToDatabase());
    }

    // DATABASE INSERT METHOD

    private void addDoctorToDatabase(){
        try {
            String url = "jdbc:mysql://localhost:3306/ApexCare";
            String user = "root";
            String password = "1231202ya";

            Connection con = DriverManager.getConnection(url,user,password);

            String sql = "INSERT INTO doctors(full_name,dob,gender,specialization,qualification,experience,department,contact,email,consultation_fee,availability,clinic_address,notes) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";

            PreparedStatement ps = con.prepareStatement(sql);

            // Convert the spinner value here
            Date dobDate = (Date) dobSpinner.getValue();        // java.util.Date from spinner
            java.sql.Date sqlDob = new java.sql.Date(dobDate.getTime()); // convert to java.sql.Date

            ps.setDate(2, sqlDob);  // save DOB in database


            ps.setString(1,nameField.getText());
            ps.setDate(2, sqlDob); // <--- Use java.sql.Date here
            ps.setString(3,genderBox.getSelectedItem().toString());
            ps.setString(4,specializationField.getText());
            ps.setString(5,qualificationField.getText());
            ps.setInt(6,Integer.parseInt(experienceField.getText()));
            ps.setString(7,departmentField.getText());
            ps.setString(8,contactField.getText());
            ps.setString(9,emailField.getText());
            ps.setDouble(10,Double.parseDouble(feeField.getText()));
            ps.setString(11,availabilityField.getText());
            ps.setString(12,addressField.getText());
            ps.setString(13,notesArea.getText());

            ps.executeUpdate();

            JOptionPane.showMessageDialog(this,"Doctor Added Successfully!");

            con.close();

        }
        catch(Exception ex){
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this,"Database Error");
        }
    }
}