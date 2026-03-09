import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class SignupPage extends JPanel {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField emailField;
    private JButton signupButton;
    private Database db;

    public SignupPage(CardLayout cardLayout, JPanel mainPanel, Database db) {
        this.db = db;

        // --- Main container with two equal columns ---
        setLayout(new GridLayout(1, 2));

        // --- LEFT PANEL: Image ---
        ImagePanel imagePanel = new ImagePanel("Signup.jpg"); // or reuse Login.jpg
        add(imagePanel);

        // --- RIGHT PANEL: Signup Form ---
        ImageIcon hospitalLogo = new ImageIcon("Logo.png"); // replace with your path
        Image scaledImage = hospitalLogo.getImage().getScaledInstance(250, 70, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(0,116,122));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Logo at the top
        JLabel logoLabel = new JLabel(scaledIcon, SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        formPanel.add(logoLabel, gbc);

        // Title below logo
        JLabel title = new JLabel("Sign Up", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridy = 1;
        formPanel.add(title, gbc);

        // Username
        JLabel usernameLabel = new JLabel("Username:");
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        formPanel.add(usernameLabel, gbc);

        usernameField = new JTextField(15);
        gbc.gridx = 1;
        formPanel.add(usernameField, gbc);

        // Email
        JLabel emailLabel = new JLabel("Email:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(emailLabel, gbc);

        emailField = new JTextField(15);
        gbc.gridx = 1;
        formPanel.add(emailField, gbc);

        // Password
        JLabel passwordLabel = new JLabel("Password:");
        gbc.gridx = 0;
        gbc.gridy = 4;
        formPanel.add(passwordLabel, gbc);

        passwordField = new JPasswordField(15);
        gbc.gridx = 1;
        formPanel.add(passwordField, gbc);

        // Signup button
        signupButton = new JButton("Sign Up");
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        formPanel.add(signupButton, gbc);

        add(formPanel); // add formPanel to right column

        // --- Signup Action ---
        signupButton.addActionListener(e -> {
            String username = usernameField.getText();
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());

            // Email validation
            String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
            if (!email.matches(emailRegex)) {
                JOptionPane.showMessageDialog(this, "Invalid email format!", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Password validation
            String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$";
            if (!password.matches(passwordRegex)) {
                JOptionPane.showMessageDialog(this,
                        "Password must contain:\n- At least 8 characters\n- 1 uppercase letter\n- 1 lowercase letter\n- 1 number",
                        "Validation Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                String query = "INSERT INTO users (username, email, password) VALUES (?, ?, ?)";
                PreparedStatement stmt = db.getConnection().prepareStatement(query);
                stmt.setString(1, username);
                stmt.setString(2, email);
                stmt.setString(3, password);

                int rows = stmt.executeUpdate();

                if (rows > 0) {
                    JOptionPane.showMessageDialog(this,
                            "Account created successfully! Please login.",
                            "Success",
                            JOptionPane.INFORMATION_MESSAGE);

                    cardLayout.show(mainPanel, "Login");
                }

                stmt.close();

            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this,
                        "Error creating account!",
                        "Signup Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
