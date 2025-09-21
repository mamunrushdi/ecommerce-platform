package org.example.swing;

import org.example.swing.model.BeautifulLogin;

import javax.swing.*;

public class LoginFrame extends BeautifulLogin {
    public LoginFrame() {
        super();
        super.getLoginButton().addActionListener(e -> {
            String user = super.getUsernameField().getText();
            String pass = new String(super.getPasswordField().getPassword());

            if (user.equals("admin") && pass.equals("admin")) {
                JOptionPane.showMessageDialog(this, "Login successful!");
                new ProductCatalogFrame().setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid credentials.");
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }
}
