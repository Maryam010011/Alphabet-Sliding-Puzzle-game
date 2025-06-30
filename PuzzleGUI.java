
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;
import java.util.Random;

public class PuzzleGUI {
    private int size;
    private Puzzle puzzle;
    private JFrame frame;
    private JButton[][] buttons;
    private Player player;
    private JLabel movesLabel;
    private JLabel timeLabel;
    private int moves;
    private Timer timer;
    private int seconds;
    
    // Updated color scheme with dark green theme
    private static final Color BACKGROUND_COLOR = new  rgb(22, 136, 22);  // Dark green
    private static final Color TILE_COLOR = new Color(46, 139, 87);  // Sea green
    private static final Color EMPTY_TILE_COLOR = new Color(60, 179, 113);  // Medium sea green
    private static final Color TEXT_COLOR = Color.rgb(184, 49, 172);
    private static final Color BORDER_COLOR = new Color(144, 238, 144);  // Light green
    private static final Color LABEL_COLOR = Color.BLACK;

    public PuzzleGUI(int size, Player player) {
        this.size = size;
        this.player = player;
        this.puzzle = new Puzzle(size);
        this.moves = 0;
        this.seconds = 0;
    }

    public void start() {
        frame = new JFrame(size + "x" + size + " Puzzle");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(500, 600);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(BACKGROUND_COLOR);

        // Create status panel
        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        statusPanel.setBackground(BACKGROUND_COLOR);
        
        movesLabel = new JLabel("Moves: 0");
        timeLabel = new JLabel("Time: 0s");
        movesLabel.setFont(new Font("Arial", Font.BOLD, 16));
        timeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        movesLabel.setForeground(LABEL_COLOR);
        timeLabel.setForeground(LABEL_COLOR);
        
        statusPanel.add(movesLabel);
        statusPanel.add(timeLabel);
        
        // Create board panel
        JPanel boardPanel = new JPanel(new GridLayout(size, size, 5, 5));
        boardPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        boardPanel.setBackground(BACKGROUND_COLOR);
        buttons = new JButton[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                JButton btn = new JButton();
                btn.setFont(new Font("Arial", Font.BOLD, 32));
                btn.setFocusPainted(false);
                btn.setBackground(TILE_COLOR);
                btn.setForeground(TEXT_COLOR);
                btn.setBorder(BorderFactory.createLineBorder(BORDER_COLOR, 2));
                
                final int row = i, col = j;
                btn.addActionListener(e -> handleMove(row, col));
                buttons[i][j] = btn;
                boardPanel.add(btn);
            }
        }

        // Create control panel
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        controlPanel.setBackground(BACKGROUND_COLOR);
        
        JButton newGameButton = createStyledButton("New Game");
        JButton exitButton = createStyledButton("Exit");
        
        newGameButton.addActionListener(e -> resetGame());
        exitButton.addActionListener(e -> frame.dispose());
        
        controlPanel.add(newGameButton);
        controlPanel.add(exitButton);

        frame.add(statusPanel, BorderLayout.NORTH);
        frame.add(boardPanel, BorderLayout.CENTER);
        frame.add(controlPanel, BorderLayout.SOUTH);

        // Start timer
        startTimer();
        
        updateBoard();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setFocusPainted(false);
        button.setBackground(TILE_COLOR);
        button.setForeground(TEXT_COLOR);
        button.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        return button;
    }

    private void startTimer() {
        timer = new Timer(1000, e -> {
            seconds++;
            timeLabel.setText("Time: " + seconds + "s");
        });
        timer.start();
    }

    private void resetGame() {
        puzzle = new Puzzle(size);
        moves = 0;
        seconds = 0;
        movesLabel.setText("Moves: 0");
        timeLabel.setText("Time: 0s");
        updateBoard();
    }

    private void handleMove(int row, int col) {
        if (puzzle.moveTile(row, col)) {
            moves++;
            movesLabel.setText("Moves: " + moves);
            updateBoard();
            
            if (puzzle.isSolved()) {
                timer.stop();
                int score = calculateScore();
                player.updateScore(score);
                
                JPanel messagePanel = new JPanel(new GridLayout(3, 1, 5, 5));
                messagePanel.setBackground(BACKGROUND_COLOR);
                
                JLabel congratsLabel = new JLabel("Congratulations! You solved the puzzle!");
                JLabel movesLabel = new JLabel("Moves: " + moves);
                JLabel timeLabel = new JLabel("Time: " + seconds + "s");
                
                congratsLabel.setForeground(LABEL_COLOR);
                movesLabel.setForeground(LABEL_COLOR);
                timeLabel.setForeground(LABEL_COLOR);
                
                messagePanel.add(congratsLabel);
                messagePanel.add(movesLabel);
                messagePanel.add(timeLabel);
                
                JOptionPane.showMessageDialog(frame, messagePanel, "Puzzle Solved!", 
                    JOptionPane.INFORMATION_MESSAGE);
                frame.dispose();
            }
        }
    }

    private int calculateScore() {
        // Base score for completing the puzzle
        int baseScore = 100;
        // Bonus for fewer moves
        int moveBonus = Math.max(0, 50 - moves);
        // Bonus for faster completion
        int timeBonus = Math.max(0, 50 - (seconds / 10));
        return baseScore + moveBonus + timeBonus;
    }

    private void updateBoard() {
        char[][] board = puzzle.getBoard();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                JButton btn = buttons[i][j];
                if (board[i][j] == ' ') {
                    btn.setText("");
                    btn.setBackground(EMPTY_TILE_COLOR);
                } else {
                    btn.setText(String.valueOf(board[i][j]));
                    btn.setBackground(TILE_COLOR);
                }
            }
        }
    }
} 