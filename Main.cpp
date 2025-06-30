#include "Game.h"
#include "Player.h"
#include "Admin.h"
#include <iostream>
#include <fstream>

using namespace std;

int main() {
    cout << "Welcome to the Sliding Puzzle Game!\n";

    while (true) {
        int userType;

        while (true) {
            cout << "\nAre you:\n1. Admin\n2. Player\nEnter choice: ";
            if (cin >> userType && (userType == 1 || userType == 2)) {
                cin.ignore(); 
                break;
            } else {
                cout << "Invalid input! Please enter 1 or 2.\n";
                cin.clear();
                cin.ignore(10000, '\n');
            }
        }

        if (userType == 1) {
            cout << "Admin section selected.\n";
            Admin admin("maryam");
            if (admin.login()) {
                admin.displayAllPlayers();
                cout << "\nReturning to main menu...\n";
            } else {
                cout << "Admin login failed. Returning to main menu...\n";
            }
            continue; 
        }

      cout << "Player section selected.\n";
string playerName, email;
int age;

cout << "\nEnter your player name: ";
getline(cin, playerName);

cout << "Enter your age: ";
while (!(cin >> age)) {
    cout << "Invalid input! Please enter a numeric age: ";
    cin.clear();
    cin.ignore(10000, '\n');
}
cin.ignore(); 

cout << "Enter your Email: ";
getline(cin, email);


        Player p(playerName, age, email);
        p.storePlayers();

        Game game(&p);

        int mainChoice;
        while (true) {
            cout << "\n=== Main Menu ===\n";
            cout << "1. Start Game\n";
            cout << "2. Show Player Info\n";
            cout << "3. Compare with Another Player\n";
            cout << "4. Exit\n";
            cout << "Enter choice: ";

            if (!(cin >> mainChoice)) {
                cout << "Invalid input! Please enter a number.\n";
                cin.clear();
                cin.ignore(10000, '\n');
                continue;
            }

            if (mainChoice == 1) {
                game.start();
            } else if (mainChoice == 2) {
                p.display();
            } else if (mainChoice == 3) {
                p.comparePlayers();
            } else if (mainChoice == 4) {
                cout << "Exiting game. Goodbye!\n";
                return 0;
            } else {
                cout << "Invalid choice! Try again.\n";
            }
        }
    }
}

