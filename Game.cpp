#include "Game.h"
#include <iostream>
#include <fstream>
using namespace std;

Game::Game(Player* p) : player(p), admin("Admin") {
    puzzleCount = 0;
    puzzleList = NULL;
    level1Complete = false;
    level2Complete = false;
}

Game::~Game() {
    delete[] puzzleList;
}

void Game::start() {
    int choice;
    while (true) {
        cout << "\n1. Play 2x2\n2. Play 3x3\n3. Play 4x4\n4. Exit\nChoice: ";
        cin >> choice;

        if (choice == 1) {
        	cout << "\n=== Welcome to Level 1 ===\n";
            cout << "Your goal is to arrange tiles as:\n";
             cout<<"^^^^^^^^^"<<endl;
            cout << "A B\nC _"<< endl; 
             cout<<"^^^^^^^^"<<endl;
            Puzzle2x2 p2;
            playGame(p2, 2);
            player->increaseScore();
            level1Complete = true;
            savePlayerData();
            
        } 
		else if (choice == 2) {
            if (!level1Complete) {
                cout << "You must complete level 1 (2x2) first!\n";
                continue;
            }
            
             cout << "\n=== Welcome to Level 2 ===\n";
            cout << "Your goal is to arrange tiles as:\n";
            cout<<"^^^^^^^^^^^^^"<<endl;
            cout << "A B C\nD E F\nG H _"<< endl;
             cout<<"^^^^^^^^^^^^^"<<endl;
            Puzzle3x3 p3;
            playGame(p3, 3);
            player->increaseScore();
            level2Complete = true;
            savePlayerData();
        } 
		else if (choice == 3) 
		{
            if (!level1Complete || !level2Complete) {
                cout << "You must complete previous levels first!\n";
                continue;
            }
            cout << "\n=== Welcome to Level 3 ===\n";
            cout << "Your goal is to arrange tiles as:\n";
             cout<<"^^^^^^^^^^^^^"<<endl;
            cout << "A B C D\nE F G H\nI J K L\nM N O _" << endl; 
             cout<<"^^^^^^^^^^^^^"<<endl;
            
            Puzzle4x4 p4;
            playGame(p4, 4);
            player->increaseScore();
            savePlayerData();
        } else if (choice == 4) {
            cout << "Thanks for playing!\n";
            break;
        } else {
            cout << "Invalid choice.\n";
        }
    }
}

void Game::savePlayerData() {
    ofstream file("players.txt", ios::app);
    if (file.is_open()) {
       file << player->getUsername() << "," 
     << player->getScore() << "," 
     << player->getage() << "," 
     << player->getemail() << "\n";
        file.close();
    }}

