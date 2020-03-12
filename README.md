
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
* A search bar is visible on top of the screen - there the user can put a search string and books that contain this string either in their title or author's name are shown.

### ViewBookActivity ###

* Shows all information about a particular book. It is possible to push a button to request/offer the book, which sends a text message to the user that added the book. This function is still in progress.

### LoginActivity ###

* Allows a user to login.

### MenuActivity ###

* Provides a menu at the bottom of the screen, where the user can navigate to major activities. Some of this is still in progress.

### AboutUsActivity ###

* Shows information about the developers.

### RegisterActivity ###

* Allows a user to create an account.
* However, a new user cannot log in. This is yet to be fixed.

### RequestBookActivity ###

* Allows a logged in user to add a book request to the application.
* The user needs to add an image, but it cannot be more than about 100 kB.

### AddBookForSaleActivity ###

* Allows a logged in user to add a book for sale to the application.
* The user needs to add an image, but it cannot be more than about 100 kB.

## Helper classes ##

### BooksAdapter ###

* Takes a list of books and processes it so that the user sees a well ordered list view layout in the application.

### AvailableSubjectsAdapter ###

* Does the same as BooksAdapter, but with subjects.

### SliderAdapter ###

* Works with AboutUsActivity to show information about the developers in a nice manner.

### Book ###

* Helps Android work with Book objects, retrieved from the backend (and ultimately the database).

### User ###

* Helps Android work with User objects, retrieved from the backend (and ultimately the database).

### BookList ###

* Makes it easier for Android to work with Book objects together in a list.

### SubjectsReponse ###

* Makes it easier to work with subjects that are retrieved by calling the availableSubjects function.

### APIInterface ###

* Defines the pages each function on the client side is working with from the server side.

### APIClient ###

* Connects the client to the server.


## Expected Activities for future releases ##

### UserInfoActivity ###

* This activity will provide information about users of the application, so that users can learn about users that are selling or requesting books, for example get their contact information.

### UserProfileActivity ###

* This activity will allow users to update their profile information and get an overlook of the books they have put up for sale or requested in the application.

### ReviewActivity ###

* This will allow users to write reviews of users they have had business with, and look at reviews for users they might plan to conduct business with.

### MessageActivity ###

* This activity is supposed to let users communicate with each other regarding certain books, but might not be implemented - the text message function might be sufficient.


## Developers ##

* _Baldvin Blær Oddsson_
    * Email: bbo1@hi.is
* _Hieu Van Phan_
    * Email: hvp5@hi.is
* _Sunna Björnsdóttir_
    * Email: sub4@hi.is
* _Þórdís Pétursdóttir_
    * Email: thp44@hi.is


