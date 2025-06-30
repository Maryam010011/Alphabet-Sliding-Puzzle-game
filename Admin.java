import User.User0;
import javax.swing.*;
import java.awt.*;
import java.io.*;

public class Admin extends User0 {
    public Admin(String name) {
        super(name);
    }

    @Override
    public void display() {
        System.out.println("Admin: " + username);
    }

    public boolean login() {
        JPanel panel = new JPanel(new GridLayout(2, 2, 5, 5));
        panel.setBackground(new Color(144, 238, 144)); 
        
        JTextField nameField = new JTextField();
        JPasswordField passField = new JPasswordField();

        JLabel l1 = new JLabel("Username:");
        panel.add(l1);
        panel.add(nameField);
        JLabel l2 = new JLabel("Password:");
        panel.add(l2);
        panel.add(passField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Admin Login",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String inputName = nameField.getText();
            String inputPass = new String(passField.getPassword());
            
            if (inputName.equals("maryam") && inputPass.equals("1019")) {
                JOptionPane.showMessageDialog(null, "Admin logged in successfully.",
                    "Success", JOptionPane.INFORMATION_MESSAGE);
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Invalid credentials.",
                    "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        return false;
    }

    public void displayAllPlayers() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(144, 238, 144)); 
        
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(new Font("Arial", Font.PLAIN, 14));
        textArea.setBackground(new Color(144, 238, 144));
        textArea.setForeground(Color.BLACK);
        
        JScrollPane scrollPane = new JScrollPane(textArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        try (BufferedReader reader = new BufferedReader(new FileReader("players.txt"))) {
            String line;
            StringBuilder content = new StringBuilder("=== All Players Info ===\n\n");
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 4) {
                    content.append(String.format("Name: %s\nAge: %s\nEmail: %s\nScore: %s\n\n",
                        parts[0], parts[1], parts[2], parts[3]));
                }
            }
            textArea.setText(content.toString());
        } catch (IOException e) {
            textArea.setText("Error reading player data: " + e.getMessage());
        }

        JOptionPane.showMessageDialog(null, panel, "All Players Information",
            JOptionPane.INFORMATION_MESSAGE);
    }
} 