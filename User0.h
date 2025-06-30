#ifndef USER0_H
#define USER0_H
#include <string>
using namespace std;

class User0 {
protected:
    string username;
public:
    User0(string uname);
    virtual void display();
    string getUsername();
};

#endif

