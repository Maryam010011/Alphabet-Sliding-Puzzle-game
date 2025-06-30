#ifndef  Admin_H
#define  Admin_H
#include "User0.h"

class Admin : public User0 {
public:
    Admin(const string& name);
    void display();
    bool login();
    void displayAllPlayers();

};

#endif

