import javax.swing.*;
import java.awt.*;

// Example: Administration page
public class AdministrationPage extends JPanel {
    JButton doctorButton;
    JButton patientButton;
    JButton appointmentButton;
    JButton ambulancesButton;
    JButton admissionButton;
    JButton staffButton;

    public AdministrationPage() {

        this.setBackground(Color.LIGHT_GRAY);
        this.setLayout(null);

        // first row
        doctorButton = new JButton();
        doctorButton.setBounds(110,100,250,250);
        this.add(doctorButton);

        patientButton = new JButton();
        patientButton.setBounds(450,100,250,250);
        this.add(patientButton);

        appointmentButton  = new JButton();
        appointmentButton.setBounds(800,100,250,250);
        this.add(appointmentButton);


        // second row
        ambulancesButton = new JButton();
        ambulancesButton.setBounds(110,450,250,250);
        this.add(ambulancesButton);

        admissionButton = new JButton();
        admissionButton.setBounds(450,450,250,250);
        this.add(admissionButton);

        staffButton = new JButton();
        staffButton.setBounds(800,450,250,250);
        this.add(staffButton);











    }
}