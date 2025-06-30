import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class Puzzle {
    protected int size;
    protected char[][] board;
    protected int emptyRow, emptyCol;
    protected JButton[][] buttons;
    protected JFrame frame;
    protected JPanel gamePanel;
    protected JLabel statusLabel;
    protected Color[] pastelColors = {
        new Color(255, 182, 193), // Light pink
        new Color(255, 218, 185), // Peach
        new Color(255, 250, 205), // Lemon chiffon
        new Color(230, 230, 250), // Lavender
        new Color(144, 238, 144), // Light green
        new Color(173, 216, 230), // Light blue
        new Color(255, 192, 203), // Pink
        new Color(221, 160, 221), // Plum
        new Color(176, 224, 230), // Powder blue
        new Color(255, 228, 196), // Bisque
        new Color(255, 222, 173), // Navajo white
        new Color(255, 218, 185), // Peach puff
        new Color(255, 228, 225), // Misty rose
        new Color(240, 255, 240), // Honeydew
        new Color(245, 245, 220), // Beige
        new Color(255, 245, 238)  // Seashell
    };

    public Puzzle(int s) {
        this.size = s;
        this.board = new char[size][size];
        this.buttons = new JButton[size][size];
        showGoal();
        initializeBoard();
        createGUI();
    }

    protected void showGoal() {
        StringBuilder goal = new StringBuilder("Your goal is to arrange tiles as:\n\n");
        char letter = 'A';
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (i == size - 1 && j == size - 1) {
                    goal.append("_ ");
                } else {
                    goal.append(letter++).append(" ");
                }
            }
            goal.append("\n");
        }
        goal.append("\nUse arrow keys or click adjacent tiles to move them.");

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(144, 238, 144));
        
        JTextArea textArea = new JTextArea(goal.toString());
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.BOLD, 16));
        textArea.setBackground(new Color(144, 238, 144));
        textArea.setForeground(Color.WHITE);
        
        JOptionPane.showMessageDialog(null, panel, "Puzzle Goal", 
            JOptionPane.INFORMATION_MESSAGE);
    }

    protected void initializeBoard() {
        char letter = 'A';
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (i == size - 1 && j == size - 1) {
                    board[i][j] = ' ';
                    emptyRow = i;
                    emptyCol = j;
                } else {
                    board[i][j] = letter++;
                }
            }
        }
        shuffleBoard();
    }

    protected void createGUI() {
        frame = new JFrame("Sliding Puzzle Game");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setBackground(new Color(144, 238, 144));

        gamePanel = new JPanel(new GridLayout(size, size, 2, 2));
        gamePanel.setBackground(new Color(144, 238, 144));
        gamePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        statusLabel = new JLabel("Welcome to the Sliding Puzzle Game!", JLabel.CENTER);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 16));
        statusLabel.setForeground(Color.WHITE);

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                buttons[i][j] = new JButton(String.valueOf(board[i][j]));
                buttons[i][j].setFont(new Font("Arial", Font.BOLD, 24));
                buttons[i][j].setForeground(Color.WHITE);
                buttons[i][j].setBackground(pastelColors[i * size + j]);
        
                
                final int row = i;
                final int col = j;
                buttons[i][j].addActionListener(e -> handleButtonClick(row, col));
                
                gamePanel.add(buttons[i][j]);
            }
        }

        frame.add(statusLabel, BorderLayout.NORTH);
        frame.add(gamePanel, BorderLayout.CENTER);
        
       
        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        if (emptyRow < size - 1) handleButtonClick(emptyRow + 1, emptyCol);
                        break;
                    case KeyEvent.VK_DOWN:
                        if (emptyRow > 0) handleButtonClick(emptyRow - 1, emptyCol);
                        break;
                    case KeyEvent.VK_LEFT:
                        if (emptyCol < size - 1) handleButtonClick(emptyRow, emptyCol + 1);
                        break;
                    case KeyEvent.VK_RIGHT:
                        if (emptyCol > 0) handleButtonClick(emptyRow, emptyCol - 1);
                        break;
                }
            }
        });
        frame.setFocusable(true);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    protected void handleButtonClick(int row, int col) {
        if (isValidMove(row, col)) {
            swapTiles(row, col);
            updateButtons();
            if (isSolved()) {
                statusLabel.setText("Congratulations! You solved the puzzle!");
                JOptionPane.showMessageDialog(frame, "Congratulations! You solved the puzzle!",
                    "Success", JOptionPane.INFORMATION_MESSAGE);
                frame.dispose();
            }
        }
    }

    protected boolean isValidMove(int row, int col) {
        return (Math.abs(row - emptyRow) == 1 && col == emptyCol) ||
               (Math.abs(col - emptyCol) == 1 && row == emptyRow);
    }

    protected void swapTiles(int row, int col) {
        board[emptyRow][emptyCol] = board[row][col];
        board[row][col] = ' ';
        emptyRow = row;
        emptyCol = col;
    }

    protected void updateButtons() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                buttons[i][j].setText(String.valueOf(board[i][j]));
                if (board[i][j] == ' ') {
                    buttons[i][j].setVisible(false);
                } else {
                    buttons[i][j].setVisible(true);
                }
            }
        }
    }

    public void shuffleBoard() {
        Random rand = new Random();
        for (int i = 0; i < size * size * 10; i++) {
            int move = rand.nextInt(4);
            switch (move) {
                case 0: if (emptyRow > 0) swapTiles(emptyRow - 1, emptyCol); break;
                case 1: if (emptyRow < size - 1) swapTiles(emptyRow + 1, emptyCol); break;
                case 2: if (emptyCol > 0) swapTiles(emptyRow, emptyCol - 1); break;
                case 3: if (emptyCol < size - 1) swapTiles(emptyRow, emptyCol + 1); break;
            }
        }
        updateButtons();
    }

    public boolean isSolved() {
        char letter = 'A';
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (i == size - 1 && j == size - 1) {
                    if (board[i][j] != ' ') return false;
                } else {
                    if (board[i][j] != letter++) return false;
                }
            }
        }
        return true;
    }
}

class Puzzle2x2 extends Puzzle {
    public Puzzle2x2() {
        super(2);
    }
}

class Puzzle3x3 extends Puzzle {
    public Puzzle3x3() {
        super(3);
    }
}

class Puzzle4x4 extends Puzzle {
    public Puzzle4x4() {
        super(4);
    }
}