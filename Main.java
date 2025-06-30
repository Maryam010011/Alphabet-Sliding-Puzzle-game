import javax.swing.*;
import java.awt.*;


public class Main {
    public static void main(String[] args) {
        

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Welcome to Sliding Puzzle Game");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setBackground(new Color(144, 238, 144)); 

            JPanel mainPanel = new JPanel(new BorderLayout());
            mainPanel.setBackground(new Color(144, 238, 144));

            JLabel welcomeLabel = new JLabel("Welcome to the Sliding Puzzle Game!", JLabel.CENTER);
            welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
            welcomeLabel.setForeground(Color.WHITE);
            mainPanel.add(welcomeLabel, BorderLayout.NORTH);

            JPanel buttonPanel = new JPanel(new GridLayout(2, 1, 10, 10));
            buttonPanel.setBackground(new Color(144, 238, 144));
            buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

            JButton adminButton = createStyledButton("Admin Login");
            JButton playerButton = createStyledButton("Player Login");

            adminButton.addActionListener(e -> handleAdminLogin(frame));
            playerButton.addActionListener(e -> handlePlayerLogin(frame));

            buttonPanel.add(adminButton);
            buttonPanel.add(playerButton);

            mainPanel.add(buttonPanel, BorderLayout.CENTER);
            frame.add(mainPanel);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

    private static JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(70, 130, 180)); // Steel blue
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        return button;
    }

    private static void handleAdminLogin(JFrame parentFrame) {
        Admin admin = new Admin("Admin");
        if (admin.login()) {
            admin.displayAllPlayers();
        }
    }

    private static void handlePlayerLogin(JFrame parentFrame) {
        JPanel panel = new JPanel(new GridLayout(3, 2, 5, 5));
        panel.setBackground(new Color(144, 238, 144));

        JTextField nameField = new JTextField();
        JTextField ageField = new JTextField();
        JTextField emailField = new JTextField();

        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Age:"));
        panel.add(ageField);
        panel.add(new JLabel("Email:"));
        panel.add(emailField);

        int result = JOptionPane.showConfirmDialog(parentFrame, panel,
            "Player Registration", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            try {
                String name = nameField.getText();
                int age = Integer.parseInt(ageField.getText());
                String email = emailField.getText();

                if (name.isEmpty() || email.isEmpty()) {
                    JOptionPane.showMessageDialog(parentFrame,
                        "Please fill in all fields!",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Player player = new Player(name, age, email);
                parentFrame.dispose();
                new Game(player);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(parentFrame,
                    "Please enter a valid age!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
} 