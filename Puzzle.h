#ifndef PUZZLE_H
#define PUZZLE_H
#include <iostream>
#include <fstream>
using namespace std;

class Puzzle {
protected:
    int size;
    char board[4][4];
    int emptyRow, emptyCol;

public:
    Puzzle(int s);
    void shuffleBoard();
    void displayBoard();
    bool moveTile(char move);
    bool isSolved();
    void logWin(int size);
void playGame(Puzzle& puzzle, int size);
};

class Puzzle2x2 : public Puzzle {
public:
    Puzzle2x2();
};

class Puzzle3x3 : public Puzzle {
public:
    Puzzle3x3();
};

class Puzzle4x4 : public Puzzle {
public:
    Puzzle4x4();
};


#endif

