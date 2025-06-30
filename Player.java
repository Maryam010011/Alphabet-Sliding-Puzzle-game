import User.User0;
import java.io.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;


public class Player extends User0 {
    private static int score = 0;
    private int age;
    private String email;
    private static int totalGames = 0;

    public Player(String uname, int a, String e) {
        super(uname);
        this.age = a;
        this.email = e;
        totalGames++;
    }

    public void increaseScore() {
        score++;
        storePlayers(); 
    }

    @Override
    public void display() {
        JPanel panel = new JPanel(new GridLayout(4, 1, 5, 5));
        panel.setBackground(new Color(144, 238, 144));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel nameLabel = new JLabel("Name: " + username);
        JLabel scoreLabel = new JLabel("Score: " + getScore());
        JLabel ageLabel = new JLabel("Age: " + age);
        JLabel emailLabel = new JLabel("Email: " + email);

        Font labelFont = new Font("Arial", Font.BOLD, 14);
        nameLabel.setFont(labelFont);
        scoreLabel.setFont(labelFont);
        ageLabel.setFont(labelFont);
        emailLabel.setFont(labelFont);

        nameLabel.setForeground(Color.BLACK);
        scoreLabel.setForeground(Color.BLACK);
        ageLabel.setForeground(Color.BLACK);
        emailLabel.setForeground(Color.BLACK);

        panel.add(nameLabel);
        panel.add(scoreLabel);
        panel.add(ageLabel);
        panel.add(emailLabel);

        JOptionPane.showMessageDialog(null, panel, "Player Information", 
            JOptionPane.INFORMATION_MESSAGE);
    }

    public int getScore() {
        return score;
    }

    public String getEmail() {
        return email;
    }

    public int getAge() {
        return age;
    }

    public static int getTotalGames() {
        return totalGames;
    }

    public void storePlayers() {
        try (FileWriter file = new FileWriter("players.txt", true);
             BufferedWriter writer = new BufferedWriter(file)) {
            writer.write(username + "|" + age + "|" + email + "|" + score + "\n");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error storing player data: " + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void comparePlayers() {
        List<PlayerScore> scores = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("players.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 4) {
                    scores.add(new PlayerScore(parts[0], Integer.parseInt(parts[3])));
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error reading player data: " + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        scores.sort((a, b) -> b.score - a.score);

        JPanel panel = new JPanel(new GridLayout(4, 1, 5, 5));
        panel.setBackground(new Color(144, 238, 144));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel("Top 3 Players", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE);
        panel.add(titleLabel);

        for (int i = 0; i < Math.min(3, scores.size()); i++) {
            PlayerScore ps = scores.get(i);
            JLabel playerLabel = new JLabel(String.format("%d. %s - Score: %d", 
                i + 1, ps.name, ps.score), JLabel.CENTER);
            playerLabel.setFont(new Font("Arial", Font.BOLD, 14));
            playerLabel.setForeground(Color.WHITE);
            panel.add(playerLabel);
        }

        JOptionPane.showMessageDialog(null, panel, "Player Rankings", 
            JOptionPane.INFORMATION_MESSAGE);
    }

    private static class PlayerScore {
        String name;
        int score;

        PlayerScore(String name, int score) {
            this.name = name;
            this.score = score;
        }
    }
}