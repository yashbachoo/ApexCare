import javax.swing.*;
import java.awt.*;


public class PatientProfiles extends JPanel {
    public PatientProfiles() {
        this.setBackground(Color.LIGHT_GRAY);
        this.setLayout(null);

        JLabel pageHeading = new JLabel("Patient Profiles");
        pageHeading.setFont(new Font("SansSerif",Font.PLAIN,24));
        pageHeading.setBounds(70,18,200,50);
        this.add(pageHeading);



        JScrollPane tupleContainer = new JScrollPane();
        tupleContainer.setBounds(50,110 ,1180,690 );
        tupleContainer.setBackground(new Color(255,255,255));
        this.add(tupleContainer);

        JLabel byId = new JLabel("id:");
        byId.setBounds(50, 70, 30, 30);
        this.add(byId);

        JTextField idField = new JTextField();
        idField.setBounds(85, 70, 100, 30); // ends ~185
        this.add(idField);

        JLabel name = new JLabel("name:");
        name.setBounds(200, 70, 50, 30);
        this.add(name);

        JTextField nameField = new JTextField();
        nameField.setBounds(250, 70, 120, 30); // ends ~370
        this.add(nameField);

        JLabel surname = new JLabel("surname:");
        surname.setBounds(380, 70, 70, 30);
        this.add(surname);

        JTextField surnameField = new JTextField();
        surnameField.setBounds(455, 70, 120, 30); // ends ~575
        this.add(surnameField);

        JButton searchButton = new JButton("Search");
        searchButton.setBounds(590, 70, 100, 30); // neatly after surname
        this.add(searchButton);



    }
}