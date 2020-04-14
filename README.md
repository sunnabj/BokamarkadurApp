
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

### AllBooksActivity ###

* Shows a list of all available books. 
* When a book is pushed, the viewBookActivity is activated.
* A search bar is visible on top of the screen - there the user can put a search string and books that contain this string either in their title or author's name are shown.
* Can be reached by the left button in the bottom navigation bar.

### BookBySubjectActivity

* Shows a list of books from the same subject.
* It is reached when a particular subject is clicked from the available subjects in the MainActivity.

### ViewBookActivity ###

* Shows all information about a particular book. It is possible to write a message to request/offer the book, which is sent as a text message to the phone of the user that added the book.
* A button can be clicked to view information about the user that added the book. It redirects to UserInfoActivity.

### LoginActivity ###

* Allows a user to login.
* For testing, one user always exists, which logs in with the username Tester and password 12345678.
* Can be reached with the right button in the bottom navigation bar.

### MenuActivity ###

* Provides a menu where a logged in user can navigate to major activities such as adding a book for sale or requesting a book, logging out, and changing profile information.
* Is reached with the right button in the bottom navigation bar if the user is logged in. 

### AboutUsActivity ###

* Shows information about the developers.

### RegisterActivity ###

* Allows a user to create an account.

### RequestBookActivity ###

* Allows a logged in user to add a book request to the application.
* The user can add an image, but it cannot be more than about 100 KB.

### AddBookForSaleActivity ###

* Allows a logged in user to add a book for sale to the application.
* The user can add an image, but it cannot be more than about 100 KB.

### UserInfoActivity ###

* Shows information about a particular user.
* Is reached from the ViewBookActivity.
* A button can be pushed to view available reviews for the user. It redirects to the ReviewActivity.

### ReviewActivity ###

* Shows a list of reviews for a particular user.
* Is reached from UserInfoActivity.
* A button can be pushed to write a review about that user. It redirects to WriteReviewActivity. If the user is not logged in, WriteReviewActivity cannot be opened.

### WriteReviewActivity ###

* Allows the user to write a review about a particular user.
* It is reachable from ReviewActivity.
* When the review is submitted, the user is redirected to the ReviewActivity.


## Helper classes ##

### BooksAdapter ###

* Takes a list of books and processes it so that the user sees a well ordered list view layout in the application.

### AvailableSubjectsAdapter ###

* Does the same as BooksAdapter, but with subjects.

### ReviewsAdapter ###

* Does the same as BooksAdapter, but with reviews.

### SliderAdapter ###

* Works with AboutUsActivity to show information about the developers in a nice manner.

### Book ###

* Helps Android work with Book objects, retrieved from the backend (and ultimately the database).

### User ###

* Helps Android work with User objects, retrieved from the backend (and ultimately the database).

### Review ###

* Helps Android work with Review objects, retrieved from the backend (and ultimately the database). 

### BookList ###

* Makes it easier for Android to work with Book objects together in a list.

### ReviewList ###

* Makes it easier for Android to work with Review objects together in a list.

### SubjectsReponse ###

* Makes it easier to work with subjects that are retrieved by calling the availableSubjects function.

### UserResponse ###

* Makes it easier to wrap a User object acquired as JSON from the backend to a format Android can easily work with.

### ReviewsResponse ###

* Makes it easier to wrap a Review List acquired as JSON from the backend to a format Android can easily work with.

### APIInterface ###

* Defines the pages each function on the client side is working with from the server side.

### APIClient ###

* Connects the client to the server.


## Expected Activities for future releases ##

### ViewProfileActivity ###

* This activity will allow users to update their profile information and get an overlook of the books they have put up for sale or requested in the application.

### ViewMyReceivedReviewsActivity ###
* This is a variation of ReviewActivity where there is no button to add review (as you can't review yourself).

## Gif images showing some of the App's UI and flow between Activities ##


### Logging in and checking "My Profile" for logged in User: ### 
![](/LoginAndMyProfile.gif)


### User cannot add a review for himself: ### 
![](/CanNotReviewYourSelf.gif)


### User adds a book he wants through "Request a Book": ### 
![](/AddingBook.gif)


## Developers ##

* _Baldvin Blær Oddsson_
    * Email: bbo1@hi.is
* _Hieu Van Phan_
    * Email: hvp5@hi.is
* _Sunna Björnsdóttir_
    * Email: sub4@hi.is
* _Þórdís Pétursdóttir_
    * Email: thp44@hi.is


