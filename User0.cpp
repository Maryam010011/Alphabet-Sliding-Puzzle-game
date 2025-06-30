#include "User0.h"
#include <iostream>
using namespace std;

User0::User0(string uname) : username(uname) {}

void User0::display() {
    cout << "Username: " << username << endl;
}

string User0::getUsername() {
    return username;
}

