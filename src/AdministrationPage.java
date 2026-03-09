import javax.swing.*;
import java.awt.*;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import java.util.Objects;

// Example: Administration page
public class AdministrationPage extends JPanel {
    JButton doctorButton;
    public JButton getDoctorButton(){return doctorButton;}
    JButton patientButton;
    JButton appointmentButton;
    JButton ambulancesButton;
    JButton admissionButton;
    JButton staffButton;

    public AdministrationPage() {

        this.setBackground(Color.LIGHT_GRAY);
        this.setLayout(null);

        Border roundborder = BorderFactory.createLineBorder(new Color(73, 126, 100),2,true);

        // first row

        doctorButton = new JButton("Doctor Profiles",IconUtils.resizeIcon(200,200,"buttonIcons/doctorediited.png"));

        doctorButton.setBounds(110,100,250,250);
        doctorButton.setBackground(new Color(255, 255, 255));
        doctorButton.setHorizontalTextPosition(SwingConstants.CENTER);
        doctorButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        doctorButton.setFont(new Font("SansSerif",Font.BOLD,18));
        doctorButton.setBorder(roundborder);
        doctorButton.setFocusPainted(false);



        this.add(doctorButton);

        patientButton = new JButton("Patient Profiles",IconUtils.resizeIcon(200,200,"buttonIcons/patient.png"));
        patientButton.setBackground(new Color(255, 255, 255));
        patientButton.setHorizontalTextPosition(SwingConstants.CENTER);
        patientButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        patientButton.setFont(new Font("SansSerif",Font.BOLD,18));
        patientButton.setBorder(roundborder);
        patientButton.setFocusPainted(false);
        patientButton.setBounds(450,100,250,250);

        this.add(patientButton);

        appointmentButton  = new JButton("Appointments",IconUtils.resizeIcon(220,200,"buttonIcons/AppointmentIcon.png"));
        appointmentButton.setBackground(new Color(255, 255, 255));
        appointmentButton.setHorizontalTextPosition(SwingConstants.CENTER);
        appointmentButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        appointmentButton.setFont(new Font("SansSerif",Font.BOLD,18));
        appointmentButton.setBorder(roundborder);
        appointmentButton.setFocusPainted(false);
        appointmentButton.setBounds(800,100,250,250);
        this.add(appointmentButton);


        // second row
        ambulancesButton = new JButton("Ambulance Fleet",IconUtils.resizeIcon(220,200,"buttonIcons/ambulance.png"));
        ambulancesButton.setBackground(new Color(255, 255, 255));
        ambulancesButton.setHorizontalTextPosition(SwingConstants.CENTER);
        ambulancesButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        ambulancesButton.setFont(new Font("SansSerif",Font.BOLD,18));
        ambulancesButton.setBorder(roundborder);
        ambulancesButton.setFocusPainted(false);
        ambulancesButton.setBounds(110,450,250,250);
        this.add(ambulancesButton);

        admissionButton = new JButton("Admissions",IconUtils.resizeIcon(220,200,"buttonIcons/admission.png"));
        admissionButton.setBackground(new Color(255, 255, 255));
        admissionButton.setHorizontalTextPosition(SwingConstants.CENTER);
        admissionButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        admissionButton.setFont(new Font("SansSerif",Font.BOLD,18));
        admissionButton.setBorder(roundborder);
        admissionButton.setFocusPainted(false);
        admissionButton.setBounds(450,450,250,250);
        this.add(admissionButton);

        staffButton = new JButton("Staff",IconUtils.resizeIcon(210,200,"buttonIcons/staff.png"));
        staffButton.setBackground(new Color(255, 255, 255));
        staffButton.setHorizontalTextPosition(SwingConstants.CENTER);
        staffButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        staffButton.setFont(new Font("SansSerif",Font.BOLD,18));
        staffButton.setBorder(roundborder);
        staffButton.setFocusPainted(false);
        staffButton.setBounds(800,450,250,250);
        this.add(staffButton);











    }
}