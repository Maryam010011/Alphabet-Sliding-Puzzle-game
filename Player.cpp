#include "Player.h"
#include <fstream>
#include <iostream>
#include <iomanip>
#include <sstream>
using namespace std;

int Player::totalGames = 0; 
int Player::score = 0;

Player::Player( string uname, int a, string e) : User0(uname) {
	username = uname;
	age = a;
	email = e;
    totalGames++;
    cout << "Player created. Score: " << score << endl;
}

void Player::increaseScore() {
    Player::score++; 
}

void Player::display() {
   cout << "Player: " << username
         << ", Score: " << getScore()
         << ", Age: " << age
         << ", Email: " << email << endl;
}

int Player::getScore() const {
    return score;
}

string Player::getUsername() const {
    return username;
}
string Player::getemail() const{
	return email;
}
int Player::getage() const{
    	return age;
	}

 int Player::getTotalGames() {
    return totalGames;
}

void Player::storePlayers() {
    ofstream file("players.txt", ios::app);
    if (!file.is_open()) {
        cout << "Error: Could not open file to store player info!\n";
        return;
    }
file << username << "|" << age << "|" << email << "|" << score << "\n";

    file.close();
}

void Player::comparePlayers() {
    ifstream file("players.txt");
    if (!file.is_open()) {
        cout << "Error: File not found!" << endl;
        return;
    }

    const int MAX = 100;
    string names[MAX];
    int scores[MAX];
    int count = 0;

    string line;
    while (getline(file, line) && count < MAX) {
        stringstream ss(line);
      string uname, ageStr, email, scoreStr;
        int sc;

        getline(ss, uname, '|');
        getline(ss, ageStr, '|');
        getline(ss, email, '|');
        getline(ss, scoreStr, '|');

     
        stringstream scoreConvert(scoreStr);
        scoreConvert >> sc;

        if (!uname.empty() && !scoreConvert.fail()) {
            names[count] = uname;
            scores[count] = sc;
            count++;
        }
    }

    file.close();


    for (int i = 0; i < count - 1; ++i) {
        for (int j = i + 1; j < count; ++j) {
            if (scores[i] < scores[j]) {
                swap(scores[i], scores[j]);
                swap(names[i], names[j]);
            }
        }
    }

    cout << "\nTop 3 Players:\n";
    for (int i = 0; i < count && i < 3; ++i) {
        cout << names[i] << " - Score: " << scores[i] << "\n";
    }}
