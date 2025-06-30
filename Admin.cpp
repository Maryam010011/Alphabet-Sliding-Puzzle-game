#include "Admin.h"
#include <iostream>
#include <fstream>
#include <sstream>
using namespace std;

Admin::Admin(const string& name) : User0(name) {}

void Admin::display() {
    cout << "Admin: " << username << endl;
}

bool Admin::login() {
    string inputName, inputPass;
    cout << "Enter Admin Name: ";
    cin >> inputName;
    cout << "Enter Admin Password: ";
    cin >> inputPass;

    if (inputName =="maryam" && inputPass == "1019") {
        cout << "Admin logged in successfully.\n";
        return true;
    } else {
        cout << "Invalid credentials.\n";
        return false;
    }
}
void Admin::displayAllPlayers() {
    ifstream file("players.txt");
    if (!file.is_open()) {
        cout << "Error opening player data file!\n";
        return;
    }

    string line;
    cout << "\n=== All Players Info ===\n";
    while (getline(file, line)) {
        stringstream ss(line);
        string name, ageStr, email, scoreStr;

        getline(ss, name, '|');
        getline(ss, ageStr, '|');
        getline(ss, email, '|');
        getline(ss, scoreStr, '|');

        if (name.empty() || ageStr.empty() || email.empty() || scoreStr.empty()) {
            continue;
        }

        cout << "Name: " << name
             << ", Age: " << ageStr
             << ", Email: " << email
             << ", Score: " << scoreStr << endl;
    }

    file.close();
}

