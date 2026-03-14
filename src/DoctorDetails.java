import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class DoctorDetails extends JFrame {

    public DoctorDetails(int doctorId) {

        setTitle("Doctor Profile");
        setSize(500,500);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(0,2,10,10));

        try{

            Database db = new Database();
            Connection conn = db.getConnection();

            String sql = "SELECT * FROM doctors WHERE doctor_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, doctorId);

            ResultSet rs = ps.executeQuery();

            if(rs.next()){

                add(new JLabel("Doctor ID:"));
                add(new JLabel(rs.getString("doctor_id")));

                add(new JLabel("Full Name:"));
                add(new JLabel(rs.getString("full_name")));

                add(new JLabel("DOB:"));
                add(new JLabel(rs.getString("dob")));

                add(new JLabel("Gender:"));
                add(new JLabel(rs.getString("gender")));

                add(new JLabel("Specialization:"));
                add(new JLabel(rs.getString("specialization")));

                add(new JLabel("Qualification:"));
                add(new JLabel(rs.getString("qualification")));

                add(new JLabel("Experience:"));
                add(new JLabel(rs.getString("experience")));

                add(new JLabel("Department:"));
                add(new JLabel(rs.getString("department")));

                add(new JLabel("Contact:"));
                add(new JLabel(rs.getString("contact")));

                add(new JLabel("Email:"));
                add(new JLabel(rs.getString("email")));

                add(new JLabel("Consultation Fee:"));
                add(new JLabel(rs.getString("consultation_fee")));

                add(new JLabel("Availability:"));
                add(new JLabel(rs.getString("availability")));

                add(new JLabel("Clinic Address:"));
                add(new JLabel(rs.getString("clinic_address")));

                add(new JLabel("Notes:"));
                add(new JLabel(rs.getString("notes")));

            }

            db.close();

        }catch(Exception e){
            e.printStackTrace();
        }

        setVisible(true);
    }
}

