#ifndef PLAYER_H
#define PLAYER_H
#include "User0.h"

class Player : public User0 {
  static int score;
    int age;
    string email;
    static int totalGames;
public:
    Player( string uname, int a, string e);
    void increaseScore();
    void display();
    int getScore() const;
    string getUsername() const;
    int getage() const;
    string getemail() const;	
    static int getTotalGames();
    void comparePlayers();
     void storePlayers();
};

#endif

