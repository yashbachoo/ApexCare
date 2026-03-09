import javax.swing.*;
import java.awt.*;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

// Example: Administration page
public class AdministrationPage extends JPanel {
    JButton doctorButton;
    public JButton getDoctorButton() { return doctorButton; }

    JButton patientButton;
    public JButton getPatientButton() { return patientButton; }

    JButton appointmentButton;
    public JButton getAppointmentButton() { return appointmentButton; }

    JButton ambulancesButton;
    public JButton getAmbulancesButton() { return ambulancesButton; }

    JButton admissionButton;
    public JButton getAdmissionButton() { return admissionButton; }

    JButton staffButton;
    public JButton getStaffButton() { return staffButton; }

    public AdministrationPage(JPanel dashboardCenter) {
        this.setBackground(Color.LIGHT_GRAY);
        this.setLayout(null);

        Border roundBorder = BorderFactory.createLineBorder(new Color(73, 126, 100), 2, true);

        // --- First row ---
        doctorButton = new JButton("Doctor Profiles", IconUtils.resizeIcon(200, 200, "buttonIcons/doctorediited.png"));
        doctorButton.setBounds(110, 100, 250, 250);
        doctorButton.setOpaque(true);
        doctorButton.setBackground(Color.WHITE);
        doctorButton.setHorizontalTextPosition(SwingConstants.CENTER);
        doctorButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        doctorButton.setFont(new Font("SansSerif", Font.BOLD, 18));
        doctorButton.setBorder(roundBorder);
        doctorButton.setFocusPainted(false);
        this.add(doctorButton);

        patientButton = new JButton("Patient Profiles", IconUtils.resizeIcon(200, 200, "buttonIcons/patient.png"));
        patientButton.setBounds(450, 100, 250, 250);
        patientButton.setOpaque(true);
        patientButton.setBackground(Color.WHITE);
        patientButton.setHorizontalTextPosition(SwingConstants.CENTER);
        patientButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        patientButton.setFont(new Font("SansSerif", Font.BOLD, 18));
        patientButton.setBorder(roundBorder);
        patientButton.setFocusPainted(false);
        this.add(patientButton);

        appointmentButton = new JButton("Appointments", IconUtils.resizeIcon(220, 200, "buttonIcons/AppointmentIcon.png"));
        appointmentButton.setBounds(800, 100, 250, 250);
        appointmentButton.setOpaque(true);
        appointmentButton.setBackground(Color.WHITE);
        appointmentButton.setHorizontalTextPosition(SwingConstants.CENTER);
        appointmentButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        appointmentButton.setFont(new Font("SansSerif", Font.BOLD, 18));
        appointmentButton.setBorder(roundBorder);
        appointmentButton.setFocusPainted(false);
        this.add(appointmentButton);

        // --- Second row ---
        ambulancesButton = new JButton("Ambulance Fleet", IconUtils.resizeIcon(220, 200, "buttonIcons/ambulance.png"));
        ambulancesButton.setBounds(110, 450, 250, 250);
        ambulancesButton.setOpaque(true);
        ambulancesButton.setBackground(Color.WHITE);
        ambulancesButton.setHorizontalTextPosition(SwingConstants.CENTER);
        ambulancesButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        ambulancesButton.setFont(new Font("SansSerif", Font.BOLD, 18));
        ambulancesButton.setBorder(roundBorder);
        ambulancesButton.setFocusPainted(false);
        this.add(ambulancesButton);

        admissionButton = new JButton("Admissions", IconUtils.resizeIcon(220, 200, "buttonIcons/admission.png"));
        admissionButton.setBounds(450, 450, 250, 250);
        admissionButton.setOpaque(true);
        admissionButton.setBackground(Color.WHITE);
        admissionButton.setHorizontalTextPosition(SwingConstants.CENTER);
        admissionButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        admissionButton.setFont(new Font("SansSerif", Font.BOLD, 18));
        admissionButton.setBorder(roundBorder);
        admissionButton.setFocusPainted(false);
        this.add(admissionButton);

        staffButton = new JButton("Staff", IconUtils.resizeIcon(210, 200, "buttonIcons/staff.png"));
        staffButton.setBounds(800, 450, 250, 250);
        staffButton.setOpaque(true);
        staffButton.setBackground(Color.WHITE);
        staffButton.setHorizontalTextPosition(SwingConstants.CENTER);
        staffButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        staffButton.setFont(new Font("SansSerif", Font.BOLD, 18));
        staffButton.setBorder(roundBorder);
        staffButton.setFocusPainted(false);
        this.add(staffButton);

        // --- Button ActionListeners ---
        doctorButton.addActionListener(e -> {
            CardLayout cl = (CardLayout) (dashboardCenter.getLayout());
            cl.show(dashboardCenter, "DoctorProfiles");
        });

        if (dashboardCenter != null) {
            patientButton.addActionListener(e -> {
                CardLayout cl = (CardLayout) (dashboardCenter.getLayout());
                cl.show(dashboardCenter, "PatientProfiles");
            });


            appointmentButton.addActionListener(e -> {
                CardLayout cl = (CardLayout) (dashboardCenter.getLayout());
                cl.show(dashboardCenter, "Appointments");
            });

            ambulancesButton.addActionListener(e -> {
                CardLayout cl = (CardLayout) (dashboardCenter.getLayout());
                cl.show(dashboardCenter, "Ambulances");
            });

            admissionButton.addActionListener(e -> {
                CardLayout cl = (CardLayout) (dashboardCenter.getLayout());
                cl.show(dashboardCenter, "Admissions");
            });

            staffButton.addActionListener(e -> {
                CardLayout cl = (CardLayout) (dashboardCenter.getLayout());
                cl.show(dashboardCenter, "Staff");
            });
        }
    }
}