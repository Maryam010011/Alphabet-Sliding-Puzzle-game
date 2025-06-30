#include "Puzzle.h"
#include <cstdlib>
#include <ctime>

Puzzle::Puzzle(int s) : size(s) {
    char letter = 'A';
    for (int i = 0; i < size; i++) {
        for (int j = 0; j < size; j++) {
            if (i == size - 1 && j == size - 1) {
                board[i][j] = ' ';
                emptyRow = i;
                emptyCol = j;
            }
            else {
                board[i][j] = letter++;
            }
        }
    }
    shuffleBoard();
}

void Puzzle::shuffleBoard() {
    srand(time(0));
    for (int i = 0; i < size * size * 10; i++) {
        int move = rand() % 4;
        if (move == 0) moveTile('U');
        if (move == 1) moveTile('D');
        if (move == 2) moveTile('L');
        if (move == 3) moveTile('R');
    }
}

void Puzzle::displayBoard() {
    for (int i = 0; i < size; i++) {
        for (int j = 0; j < size; j++) {
            cout << board[i][j] << " ";
        }
        cout << endl;
    }
}

bool Puzzle::moveTile(char move) {
    int newRow = emptyRow, newCol = emptyCol;
    if (move == 'U') newRow--;
    else if (move == 'D') newRow++;
    else if (move == 'L') newCol--;
    else if (move == 'R') newCol++;
    else return false;

    if (newRow >= 0 && newRow < size && newCol >= 0 && newCol < size) {
        swap(board[emptyRow][emptyCol], board[newRow][newCol]);
        emptyRow = newRow;
        emptyCol = newCol;
        return true;
    }
    return false;
}

bool Puzzle::isSolved() {
    char letter = 'A';
    for (int i = 0; i < size; i++) {
        for (int j = 0; j < size; j++) {
            if (i == size - 1 && j == size - 1) {
                if (board[i][j] != ' ') return false;
            }
            else {
                if (board[i][j] != letter++) return false;
            }
        }
    }
    return true;
}

Puzzle2x2::Puzzle2x2() : Puzzle(2) {}
Puzzle3x3::Puzzle3x3() : Puzzle(3) {}
Puzzle4x4::Puzzle4x4() : Puzzle(4) {}

void logWin(int size) {
    ofstream file("log.txt", ios::app);
    file << "Player won the " << size << "x" << size << " puzzle!" << endl;
    file.close();
}

void playGame(Puzzle& puzzle, int size) {
    while (true) {
        puzzle.displayBoard();
        cout << "Controls: U = Up | D = Down | L = Left | R = Right\n";
        cout << "Enter your move (Q to quit): ";
        char move;
        cin >> move;
        move = toupper(move); 
         if (move == 'Q') {
            cout << "You exited the game. See you next time!\n";
            return;
        }
        
        if (!puzzle.moveTile(move)) {
            cout << "Invalid move! Try again.\n";
        }
        
        if (puzzle.isSolved()) {
            cout << "Congratulations! You solved the " << size << "x" << size << " puzzle!\n";
            logWin(size);
            break;
        }
    }
}

