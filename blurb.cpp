#include<iostream>

using namespace std;

bool Whoozit(string X, int index);
bool Whatzit(string X, int index);
bool Blurb(string X);

bool Whoozit(string X, int index)
{
    if(X.length() >= 2)
    {
        if (X[index] == 'x' && X[index+1] == 'y')
        {
            index += 2;

            while(X[index] == 'y')
            {
                index++;
            }

            if(X[index] == ' ')
            {
                return true;

            } else if(Whatzit(X, index))
            {
                return true;

            } else
            {
                return false;
            }

        } else
        {
            return false;
        }

    } else
    {
        return false;
    }
}

bool Whatzit(string X, int index)
{
    if(X[index] == 'q')
    {
        index++;

        if(X[index] == 'd' || X[index] == 'z')
        {
            index++;

            if(Whoozit(X, index))
            {
                return true;

            } else
            {
                return false;
            }

        } else
        {
            return false;
        }

    } else
    {
        return false;
    }
}

bool Blurb(string X)
{
    int index = 0;

    if(Whoozit(X, index))
    {
        return true;

    } else
    {
        return false;
    }
}

int main()
{
    string input;
    int endLoop = 0;

    while(endLoop == 0)
    {
        cout << "Enter an alien word:";
        cin >> input;
        input = input + ' ';

        bool Cont = Blurb(input);

        if(Cont)
        {
            cout << "The word is fine.";
            endLoop = 1;

        } else
        {
            cout << "The word is a mess!\n";
        }
    }

    return 0;
}
