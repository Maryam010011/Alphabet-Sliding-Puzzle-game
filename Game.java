import javax.swing.*;
import java.awt.*;
import java.io.*;

public class Game {
    private Player player;
    private Admin admin;
    private boolean level1Complete;
    private boolean level2Complete;
    private JFrame mainFrame;

    public Game(Player p) {
        this.player = p;
        this.admin = new Admin("Admin");
        this.level1Complete = false;
        this.level2Complete = false;
        createMainFrame();
    }

    private void createMainFrame() {
        mainFrame = new JFrame("Sliding Puzzle Game");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setBackground(new Color(144, 238, 144));

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(144, 238, 144));

        JLabel welcomeLabel = new JLabel("Welcome to the Sliding Puzzle Game!", JLabel.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        welcomeLabel.setForeground(Color.WHITE);
        mainPanel.add(welcomeLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        buttonPanel.setBackground(new Color(144, 238, 144));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        JButton startGameButton = createStyledButton("Start Game");
        JButton playerInfoButton = createStyledButton("Show Player Info");
        JButton compareButton = createStyledButton("Compare with Other Players");
        JButton exitButton = createStyledButton("Exit");

        startGameButton.addActionListener(e -> start());
        playerInfoButton.addActionListener(e -> player.display());
        compareButton.addActionListener(e -> player.comparePlayers());
        exitButton.addActionListener(e -> System.exit(0));

        buttonPanel.add(startGameButton);
        buttonPanel.add(playerInfoButton);
        buttonPanel.add(compareButton);
        buttonPanel.add(exitButton);

        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        mainFrame.add(mainPanel);
        mainFrame.pack();
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(70, 130, 180));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        return button;
    }

    public void start() {
        String[] options = {"2x2", "3x3", "4x4", "Back"};
        int choice = JOptionPane.showOptionDialog(mainFrame,
            "Select puzzle size:",
            "Puzzle Selection",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.PLAIN_MESSAGE,
            null,
            options,
            options[0]);

        if (choice == 3) return; // Back option

        if (choice == 1 && !level1Complete) {
            JOptionPane.showMessageDialog(mainFrame,
                "You must complete level 1 (2x2) first!",
                "Level Locked",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (choice == 2 && (!level1Complete || !level2Complete)) {
            JOptionPane.showMessageDialog(mainFrame,
                "You must complete previous levels first!",
                "Level Locked",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        Puzzle puzzle = null;
        switch (choice) {
            case 0:
                puzzle = new Puzzle2x2();
                level1Complete = true;
                break;
            case 1:
                puzzle = new Puzzle3x3();
                level2Complete = true;
                break;
            case 2:
                puzzle = new Puzzle4x4();
                break;
        }

        if (puzzle != null) {
            player.increaseScore();
            savePlayerData();
        }
    }

    private void savePlayerData() {
        player.storePlayers();
    }
}