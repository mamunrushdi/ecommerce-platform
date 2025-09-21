package org.example.swing;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginFrame(){
        setTitle("E-Commerce Login");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(3,2,10,10));
        panel.add(new JLabel("User name: "));
        usernameField = new JTextField();
        panel.add(usernameField);

        panel.add(new JLabel("Password"));
        passwordField = new JPasswordField();
        panel.add(passwordField);

        JButton loginButton = new JButton("Login");
        panel.add(loginButton);

        add(panel);

        loginButton.addActionListener( e -> {
            String user = usernameField.getText();
            String pass = new String(passwordField.getPassword());

            if(user.equals("admin") && pass.equals("admin")){
                JOptionPane.showMessageDialog(this, "Login successful!");
                new ProductCatalogFrame().setVisible(true);
                dispose();
            } else{
                JOptionPane.showMessageDialog(this, "Invalid credentials.");
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }
}
