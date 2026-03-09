import javax.swing.*;
import java.awt.*;
import java.sql.*;

class ImagePanel extends JPanel {
    private Image image;

    public ImagePanel(String path) {
        image = new ImageIcon(path).getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw the image stretched to fill the panel
        g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
    }
}

public class LoginPage extends JPanel {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton SignupButton;
    private Database db;

    public LoginPage(CardLayout cardLayout, JPanel mainPanel, Database db) {
        this.db = db;

        // --- Main container with two equal columns ---
        setLayout(new GridLayout(1, 2));

        // --- LEFT PANEL: Image ---
        ImagePanel imagePanel = new ImagePanel("Login.jpg");
        add(imagePanel);

// --- RIGHT PANEL: Login Form ---
        ImageIcon hospitalLogo = new ImageIcon("Logo.png"); // replace with your path
        Image scaledImage = hospitalLogo.getImage().getScaledInstance(250, 70, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(0,116,122));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

//  Logo at the top
        JLabel logoLabel = new JLabel(scaledIcon, SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        formPanel.add(logoLabel, gbc);

// Title below logo
        JLabel title = new JLabel("Hospital Management Login", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridy = 1;
        formPanel.add(title, gbc);

//  Username
        JLabel usernameLabel = new JLabel("Username:");
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        formPanel.add(usernameLabel, gbc);

        usernameField = new JTextField(15);
        gbc.gridx = 1;
        formPanel.add(usernameField, gbc);

//  Password
        JLabel passwordLabel = new JLabel("Password:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(passwordLabel, gbc);

        passwordField = new JPasswordField(15);
        gbc.gridx = 1;
        formPanel.add(passwordField, gbc);

// Login Button
        loginButton = new JButton("Login");
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        formPanel.add(loginButton, gbc);

//  Signup Button
        SignupButton = new JButton("Sign Up");
        gbc.gridx= 1;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        formPanel.add(SignupButton, gbc);

// Forgot Password
        JLabel forgotLabel = new JLabel("Forgot your password?", SwingConstants.CENTER);
        forgotLabel.setForeground(new Color(92, 225, 230));
        gbc.gridy = 5;
        formPanel.add(forgotLabel, gbc);

// Add the right panel to the main container
        add(formPanel);


        SignupButton.addActionListener(e -> {
            cardLayout.show(mainPanel, "Signup"); // switch to Signup page
        });

        // --- Login Action ---
        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            try {
                String query = "SELECT * FROM users WHERE username=? AND password=?";
                PreparedStatement stmt = db.getConnection().prepareStatement(query);
                stmt.setString(1, username);
                stmt.setString(2, password); // hash in production
                ResultSet rs = stmt.executeQuery();

                if(rs.next()) {
                    cardLayout.show(mainPanel, "Dashboard");
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid credentials or email not verified!", "Login Error", JOptionPane.ERROR_MESSAGE);
                }

                rs.close();
                stmt.close();
            } catch(SQLException ex) {
                ex.printStackTrace();
            }
        });
    }
}