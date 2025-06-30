#ifndef GAME_H
#define GAME_H
#include "Player.h"
#include "Admin.h"
#include "Puzzle.h"

class Game {
    Player* player;
    Admin admin;
    char (*puzzleList)[100];
    int puzzleCount;
    bool level1Complete;
    bool level2Complete;

public:
    Game(Player* p);
    ~Game();
    void start();
    void savePlayerData();
};

#endif

