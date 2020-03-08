
# Project Information #

## General ##

This program is an Android application that provides the functions of a book exchange market. 
The backend for the application runs on Java Spring, and is available on Github through https://github.com/sunnabj/Bokamarkadur/tree/rest - that is the rest branch of github.com/sunnabj/Bokamarkadur. It is deployed on a Heroku server accessible by https://fathomless-waters-17510.herokuapp.com/

## How to run ##

When the project is fetched, it might be necessary to do a git pull command following git clone.

```bash
git clone https://github.com/sunnabj/BokamarkadurApp
git pull
```

The project should be uploaded to Android Studio, and then run either by setting up a virtual device, or connecting with an Android phone through USB.


## Current Activities ##


### MainActivity ###

* This is the starting point of the app.
* It shows a list of the 10 most recently added books (getNewestBooks function) and a list of all available subjects (getAvailableSubjects function)
* It also has temporary buttons that redirect to the login, register, add book and request book activities.

### AllBooksActivity ###

* Shows a list of all available books. 
* When a book is pushed, the viewBookActivity is activated.

### ViewBookActivity ###

* Shows all information about a particular book. It is possible to push a button to request/offer the book, which sends a sms to the user that added the book. This function is still in progress.

### LoginActivity ###

* Allows a user to login.

### MenuActivity ###

* Provides a menu at the bottom of the screen, where the user can navigate to major activities. Some of this is still in progress.

### RegisterActivity ###

* Allows a user to create an account.

### RequestBookActivity ###

* Allows a logged in user to add a book request to the application.
* This function is still under construction. An error occurs if a user enters the activity when not logged in, and if logged in, the book he adds is not added to the database. 

### AddBookForSaleActivity ###

* Allows a logged in user to add a book for sale to the application.
* This function is still under construction. An error occurs if a user enters the activity when not logged in, and if logged in, the book he adds is not added to the database. 




## Expected Activities for future releases ##

### UserInfoActivity ###

* asdfasdf

### UserProfileActivity ###

* asdfasdf

### ReviewActivity ###

* asdfasdf

### MessageActivity ###

* asdfasdf


## Developers ##

* _Baldvin Blær Oddsson_
    * Email: bbo1@hi.is
* _Hieu Van Phan_
    * Email: hvp5@hi.is
* _Sunna Björnsdóttir_
    * Email: sub4@hi.is
* _Þórdís Pétursdóttir_
    * Email: thp44@hi.is


