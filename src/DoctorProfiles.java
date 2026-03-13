import javax.swing.*;
import java.awt.*;


public class DoctorProfiles extends JPanel {
    public DoctorProfiles() {
        this.setLayout(null);

        this.setBackground(Color.LIGHT_GRAY);



        JLabel pageHeading = new JLabel("Doctor Profiles");
        pageHeading.setFont(new Font("SansSerif",Font.PLAIN,24));
        pageHeading.setBounds(70,18,200,50);

        JLabel  byId = new JLabel("id:");
        JTextField filterID = new JTextField();
        byId.setBounds(50,70,80,30 );
        this.add(byId);

        JTextField filterbyid = new JTextField();
        filterbyid.setBounds(80, 70 , 100,30 );
        this.add(filterbyid);

        JLabel name = new JLabel("name:");
        name.setBounds(200, 70, 50, 30);
        this.add(name);

        JTextField nameField = new JTextField();
        nameField.setBounds(250, 70, 220, 30);
        this.add(nameField);





        // Speciality combo box
        JLabel speciality = new JLabel("speciality:");
        speciality.setBounds(580, 70, 70, 30);
        this.add(speciality);

        String[] specialities = {"Generalist","Cardiology", "Neurology", "Orthopedics", "Pediatrics", "Dermatology"};
        JComboBox<String> specialityBox = new JComboBox<>(specialities);
        specialityBox.setBounds(650, 70, 130, 30);
        this.add(specialityBox);

        // Availability combo box
        JLabel availability = new JLabel("availability:");
        availability.setBounds(790, 70, 80, 30);
        this.add(availability);

        String[] availabilityOptions = {"Available", "Vacation", "Suspended", "Retired"};
        JComboBox<String> availabilityBox = new JComboBox<>(availabilityOptions);
        availabilityBox.setBounds(870, 70, 130, 30);
        this.add(availabilityBox);

        // Search button aligned at the end
        JButton searchButton = new JButton("Search");
        searchButton.setBounds(1010, 70, 100, 30);
        this.add(searchButton);















        JScrollPane tupleContainer = new JScrollPane();
        tupleContainer.setBounds(50,110 ,1180,690 );
        tupleContainer.setBackground(new Color(255,255,255));
        this.add(pageHeading);
        this.add(tupleContainer);





    }
}