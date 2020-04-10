package com.example.bokamarkadur.Activities;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.bokamarkadur.Activities.LoginActivity;
import com.example.bokamarkadur.Adapters.BooksAdapter;
import com.example.bokamarkadur.POJO.Book;
import com.example.bokamarkadur.POJO.BookList;
import com.example.bokamarkadur.POJO.User;
import com.example.bokamarkadur.POJO.UserResponse;
import com.example.bokamarkadur.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewProfileActivity extends AppCompatActivity {

    // Connection to backend created.
    APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
    private Button updateProfile;
    private Button myReviews;

    String pUsername;
    public String ProfileUsername;

    TextView profileName;
    TextView profileInfo;
    TextView profileEmail;
    TextView profilePhonenumber;
    TextView profileUsername;
    TextView profilePassword;

    User UserProfile;
    List<Book> usersBooks;

    private static final String TAG = "ViewProfileActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);


        // Function that fetches the logged in user from backend
        // and nested function that retrieves User's info and displays it
        // where user can make changes to his information.
        getLoggedInUser();

        // This fetches all of user's books and displays them in a list.
        getMyBooks();

        // This function sets up and displays the bottom navigation.
        setBottomNavigation();

        // Hide System UI for best experience
        hideSystemUI();

//        // Unused, non-working function (see description by
//        // function's definition.
//        showUsersBooks();
    }

    // Show User's Profile Info
    private void showUserProfile(String name,
                                 String info,
                                 String email,
                                 String phonenumber,
                                 String username,
                                 String password){
//  Eftir að undirbúa List<Book> álíka og gert í öðrum klösum, en þá verður línan fyrir ofan svona:
//                              String password, String retypepassword, List<Book> usersBooks){

        Log.d(TAG, "BBO -->> showUserProfile: setting the title and author to widgets.");

        profileName = findViewById(R.id.edtName);
        profileName.setText(name);

        profileInfo = findViewById(R.id.edtInfo);
        profileInfo.setText(info);


        profileEmail = findViewById(R.id.edtEmail);
        profileEmail.setText(email);

        profilePhonenumber = findViewById(R.id.edtPhonenumber);
        profilePhonenumber.setText(phonenumber);


        profileUsername = findViewById(R.id.edtUsername);
        profileUsername.setText(username);

        profilePassword = findViewById(R.id.edtPassword);
        profilePassword.setText(password);

    }


    // Get currently logged in user.
    private void getLoggedInUser() {

        Call<User> getLoggedInUser = apiInterface.getLoggedInUser("Bearer " + LoginActivity.token);
        getLoggedInUser.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                // This is the username of the currently logged in user.
                pUsername   = response.body().getUsername();

                // This connects buttons for activity.
                connectButtons(pUsername);

                // Fetch profile info for this logged in user.
                getUserProfile(pUsername);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
                call.cancel();
            }

        });
    }


    // Buttons for
    //      "Update My Info" and
    //      "My Reviews"
    // connected to appropriate activities.
    private void connectButtons(String username) {

        final String profileUsername = username;
        // When user pushes "Update My Info" button
        // updateUsersProfile() is called.
        updateProfile = (Button) findViewById(R.id.updateProfile);
        updateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateUserProfile();
            }
        });

        // When user pushes "My Review" button
        // user is taken to ReviewActivity.
        myReviews = (Button) findViewById(R.id.myReviews);
        myReviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "\n*****************************************");
                Log.d(TAG, "\n*****************************************");
                Log.d(TAG, "\n\t\t final String profileUsername = username;" + profileUsername);
                Log.d(TAG, "\n\t\t ProfileUsername;" + ProfileUsername);
                Log.d(TAG, "\n!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                Log.d(TAG, "\n!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                Intent intent = new Intent(ViewProfileActivity.this, ReviewActivity.class);
                intent.putExtra("username", profileUsername);
                startActivity(intent);
            }
        });
    }


    // Users' Profile is fetched from REST Backend.
    private void getUserProfile(String profileusername) {
        ProfileUsername = profileusername;

        Call<User> getUserProfile = apiInterface.getUserProfile(ProfileUsername);
        getUserProfile.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                UserProfile    = response.body().getUser();

                String name         = UserProfile.getName();
                String info         = UserProfile.getInfo();
                String email        = UserProfile.getEmail();
                String phonenumber  = UserProfile.getPhonenumber();
                String username     = UserProfile.getUsername();
                String password     = UserProfile.getPassword();
                usersBooks          = UserProfile.getBooks();

                Log.d(TAG, "\n*****************************************");
                Log.d(TAG, "*****************************************");
                Log.d(TAG, "\t UserProfile:\n\n");
                Log.d(TAG, "\n \t response.body(); = *" + response.body() + "*");
                Log.d(TAG, "\n \t User UserProfile = response.body().getUser() = *" + UserProfile + "*");
                Log.d(TAG, "\n \t UserProfile.getName(); = *"        + UserProfile.getName()        + "*");
                Log.d(TAG, "\n \t UserProfile.getInfo(); = *"        + UserProfile.getInfo()        + "*");
                Log.d(TAG, "\n \t UserProfile.getEmail(); = *"       + UserProfile.getEmail()       + "*");
                Log.d(TAG, "\n \t UserProfile.getPhonenumber(); = *" + UserProfile.getPhonenumber() + "*");
                Log.d(TAG, "\n \t UserProfile.getUsername(); = *"    + UserProfile.getUsername()    + "*");
                Log.d(TAG, "\n \t UserProfile.getPassword(); = *"    + UserProfile.getPassword()    + "*\n");
                Log.d(TAG, "\n!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                Log.d(TAG, "\n!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

                showUserProfile(name, info, email, phonenumber, username, password);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
                call.cancel();
            }
        });
    }


    // If user pushes the "Update my info" button a @POST call is made to Backend
    // to update user's profile information.
    private void updateUserProfile() {
        /**
         * Upplýsingar eru fengnar úr formi í layout og JsonObject búinn til út frá þeim
         */
        EditText uName              = (EditText) findViewById(R.id.edtName);
        EditText uInfo              = (EditText) findViewById(R.id.edtInfo);
        EditText uEmail             = (EditText) findViewById(R.id.edtEmail);
        EditText uPhonenumber       = (EditText) findViewById(R.id.edtPhonenumber);
        EditText uUsername          = (EditText) findViewById(R.id.edtUsername);
        EditText uPassword          = (EditText) findViewById(R.id.edtPassword);

        String updateName           = uName.getText().toString();
        String updateInfo           = uInfo.getText().toString();
        String updateEmail          = uEmail.getText().toString();
        String updatePhonenumber    = uPhonenumber.getText().toString();
        String updateUsername       = uUsername.getText().toString();
        String updatePassword       = uPassword.getText().toString();

        // Updated info in JSON form.
        final JsonObject JSONupdatedProfile = new JsonObject();

        JSONupdatedProfile.addProperty("name", updateName);
        JSONupdatedProfile.addProperty("info", updateInfo);
        JSONupdatedProfile.addProperty("email", updateEmail);
        JSONupdatedProfile.addProperty("phonenumber", updatePhonenumber);
        JSONupdatedProfile.addProperty("username", updateUsername);


        // Password is only sent if non-empty.
        if (!updatePassword.isEmpty() ) {
            JSONupdatedProfile.addProperty("password", updatePassword);

            Log.d(TAG, "\n*****************************************");
            Log.d(TAG, "*****************************************");
            Log.d(TAG, "\n\n\n\t Password was added to the JSON file !! \n\n");
            Log.d(TAG, "\n!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            Log.d(TAG, "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        }

        Log.d(TAG, "\n*****************************************");
        Log.d(TAG, "*****************************************");
        Log.d(TAG, "\t UpdatedInfoForProfile:\n\n");
        Log.d(TAG, "\n\n\n \t updateName *"         + updateName        + "*");
        Log.d(TAG, "\n\n\n \t updateInfo *"         + updateInfo        + "*");
        Log.d(TAG, "\n\n\n \t updateEmail *"        + updateEmail       + "*");
        Log.d(TAG, "\n\n\n \t updatePhonenumber *"  + updatePhonenumber + "*");
        Log.d(TAG, "\n\n\n \t updateUsername *"     + updateUsername    + "*");
        Log.d(TAG, "\n\n\n \t updatePassword *"     + updatePassword    + "*");

        Log.d(TAG, "\n!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        Log.d(TAG, "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

        // A call is made to update backend
        Call<User> updateUserProfile = apiInterface.updateUserProfile(JSONupdatedProfile,
                "application/json",
                "Bearer " + LoginActivity.token);
        updateUserProfile.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                if (response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Profile Updated", Toast.LENGTH_LONG).show();
                    Log.d(TAG, "\n\n\n\t\t Profile Updated !!!! \n\n\n");
                    getLoggedInUser();
                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }


    // GET CALL to backend that fetches all books that
    // this user has added for sale or made requests for.
    private void getMyBooks() {

        /**
         @GET call that returns list of Books this (logged in) user has requested or put up for sale.
         **/
        final RecyclerView UsersBooksrecyclerView = findViewById(R.id.users_books_recycler_view);
        UsersBooksrecyclerView.setLayoutManager(new LinearLayoutManager(this));
        UsersBooksrecyclerView.setAdapter(new BooksAdapter(new ArrayList<Book>(), R.layout.list_item, getApplicationContext()));

        Call<BookList> getMyBooks = apiInterface.myBooks("Bearer " + LoginActivity.token);
        getMyBooks.enqueue(new Callback<BookList>() {
            @Override
            public void onResponse(Call<BookList> call, Response<BookList> response) {
                List<Book> myBooks = response.body().getBooks();
                int numberOfBooks = myBooks.size();

                if (numberOfBooks > 0) {
                    UsersBooksrecyclerView.setAdapter(new BooksAdapter(myBooks, R.layout.list_item, getApplicationContext()));
                }
                else {
                    // Set the header for BookListView = "All Books".
                    TextView noBooks = findViewById(R.id.no_books);
                    noBooks.setText("You have not added\n any books...\n\n ...YET...");
                }

                Log.d(TAG, "BBO -->> UsersBooks: " + myBooks);
                // TODO: Debug virkni, má eyða síðar meir.
                Log.d(TAG, "BBO -->> Number of books user has: " + myBooks.size());
            }

            @Override
            public void onFailure(Call<BookList> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
                call.cancel();
            }
        });
    }

    private void showMyBooks() {


    }


    // An unused function that is supposed to get users books
    // by setting UsersBooks = response.body().getUser().getBooks()
    // when fetching logged in user in getLoggedInUser() and
    // getUserProfile() functions, but it is not working.
    private void showUsersBooks() {
        /**
         Reynt að setja bækur í BooksAdapter útfrá User.getBooks() en það virkar ekki enn sem komið er.
         **/
        // RecyclerView - Birtir lista af bókum eins og skilgreint er í list_item.
        final RecyclerView UsersBooksrecyclerView = findViewById(R.id.users_books_recycler_view);
        UsersBooksrecyclerView.setLayoutManager(new LinearLayoutManager(this));
        UsersBooksrecyclerView.setAdapter(new BooksAdapter(new ArrayList<Book>(), R.layout.list_item, getApplicationContext()));

        Log.d(TAG, "***********************************************");
        Log.d(TAG, "***********************************************");
        Log.d(TAG, "***********************************************");
        Log.d(TAG, "***********************************************");
        Log.d(TAG, "***********************************************");
        Log.d(TAG, "***********************************************");
        Log.d(TAG, "\n\n\n\n \t\t\t usersBooks -> into adapter = *" + usersBooks + "*\n\n\n\n" );
        UsersBooksrecyclerView.setAdapter(new BooksAdapter(usersBooks, R.layout.list_item, getApplicationContext()));
        Log.d(TAG, "***********************************************");
        Log.d(TAG, "***********************************************");
        Log.d(TAG, "***********************************************");
        Log.d(TAG, "***********************************************");
        Log.d(TAG, "***********************************************");
        Log.d(TAG, "***********************************************");
    }


    // Take user to the LoginActivity.
    private void openLoginActivity() {
        Intent intent= new Intent(this, LoginActivity.class);
        startActivity(intent);
    }


    // This function sets up connections to other activities
    // and displays the bottom navigation.
    private void setBottomNavigation() {
        /**+
         *  Bottom navigation
         */
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.dashboard);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.dashboard:
                        return true;
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(),
                                MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.about:
                        if (LoginActivity.token == null) {
                            openLoginActivity();
                            Toast.makeText(getApplicationContext(), "You must login to request a book", Toast.LENGTH_LONG).show();
                        } else {
                            Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                            startActivity(intent);
                            overridePendingTransition(0,0);}
                        return true;
                }
                return false;
            }
        });
    }

    private void hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        // Set the content to appear under the system bars so that the
                        // content doesn't resize when the system bars hide and show.
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and status bar
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

}
