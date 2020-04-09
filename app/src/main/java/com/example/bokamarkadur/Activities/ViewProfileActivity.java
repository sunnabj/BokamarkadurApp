package com.example.bokamarkadur.Activities;

import android.Manifest;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.bokamarkadur.Adapters.BooksAdapter;
import com.example.bokamarkadur.POJO.Book;
import com.example.bokamarkadur.POJO.BookList;
import com.example.bokamarkadur.POJO.User;
import com.example.bokamarkadur.R;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewProfileActivity extends AppCompatActivity {

    APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
    private Button updateProfile;
    private Button myReviews;
    private Button backToMenu;
    String LoggedInUsername;
    String pUsername;
    public String ProfileUsername;
    String TOKEN;

    TextView profileName;
    TextView profileInfo;
    TextView profileEmail;
    TextView profilePhonenumber;
    TextView profileUsername;
    TextView profilePassword;

    User UserProfile;
    List<Book> usersBooks;
    String LoggedInUser = "";

    private static final String TAG = "ViewProfileActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);

        Log.d(TAG, "BBO -->> ViewProfileActivity onCreate: started.");

//        getLoggedInUser();
        getLoggedInUser();
//        getUserProfile();
//        getAllBooks();
//        getUsersBooks();

        getMyBooks();
//        showUsersBooks();

        updateProfile = (Button) findViewById(R.id.updateProfile);
        updateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "BUTTON PUSHED", Toast.LENGTH_LONG).show();
                Log.d(TAG, "\n\n\n\t\t BUTTON PUSHED !!!! \n\n\n");
                updateUserProfile();
            }
        });

        myReviews = (Button) findViewById(R.id.myReviews);
        myReviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Open My Reviews Activity", Toast.LENGTH_LONG).show();
                Log.d(TAG, "\n\n\n\t\t Open My Reviews Activity !!!! \n\n\n");
                updateUserProfile();
            }
        });

        backToMenu = (Button) findViewById(R.id.backToMenu);
        backToMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MenuActivity.class));
            }
        });

        // Hide System UI for best experience
        hideSystemUI();

        // Tengjumst API Interface sem talar við bakendann okkar.

        String loginToken = LoginActivity.token;
        //String LI_User = apiInterface.getLoggedInUser((LoginActivity.token));
    }

    /**
     * BBO: Kóðinn hér fyrir neðan birtir prófíl fyrir viðkomandi notanda
     * 		sem ýtti á "My Profile" og opnaði þar með þetta Activity (ViewProfileActivity).
     */


    // Set info for User Profile
    private void showUserProfile(String name, String info, String email, String phonenumber, String username, String password){
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


    private void getLoggedInUser() {
        Log.d(TAG, "***********************************************");
        Log.d(TAG, "***********************************************");
        Log.d("login", "\n\n\n BBO -->> Carried Over from MenuActivity?? ");
        LoggedInUsername = getIntent().getStringExtra("LoggedInUser");
        Log.d("login", "\n\n\t\t BBO -->> Username is: **" + LoggedInUsername + "** \n\n\n");
        Log.d("login","!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        Log.d("login","!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");


        Call<User> getLoggedInUser = apiInterface.getLoggedInUser("Bearer " + LoginActivity.token);
        getLoggedInUser.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                pUsername   = response.body().getUsername();
                Log.d(TAG, "*****************************************");
                Log.d(TAG, "*****************************************");
                Log.d(TAG, "\n\n\n\t onResponse:" +
                        "\n\n\t\t pUsername = response.body().getUsername(); *" + pUsername + "*\n\n");
                Log.d(TAG, "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                Log.d(TAG, "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

                getUserProfile(pUsername);
                setLoggedInUsername(pUsername);

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
                call.cancel();
            }

        });
        Log.d(TAG, "\n*****************************************");
        Log.d(TAG, "*****************************************");
        Log.d(TAG, "\n\n\n\t getLoggedInUser.enqueue(new Callback<User>():" +
                "\n\n\t\t pUsername = response.body().getUsername(); *" + pUsername + "*\n\n");
        Log.d(TAG, "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        Log.d(TAG, "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
    }

    private void setLoggedInUsername(String profileusername) {
        ProfileUsername = profileusername;
        Log.d(TAG, "\n*****************************************");
        Log.d(TAG, "*****************************************");
        Log.d(TAG, "\n\n\n\t setLoggedInUsername(String profileusername):" +
                "\n\n\t\t pUsername = response.body().getUsername(); *" + ProfileUsername + "*\n\n");
        Log.d(TAG, "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        Log.d(TAG, "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
    }
    private void getUserProfile(String profileusername) {
        ProfileUsername = profileusername;
        Log.d(TAG, "\n***********************************************");
        Log.d(TAG, "***********************************************");
        Log.d("login", "\n\n\n BBO -->> Carried Over from MenuActivity?? ");
        LoggedInUsername = getIntent().getStringExtra("LoggedInUser");
        Log.d("login", "\n\n\t\t BBO -->> LoggedInUsername: **"   + LoggedInUsername + "**" +
                "\n\t\t BBO -->> ProfileUsername:  **"   + ProfileUsername + "**\n\n\n");
        Log.d("login","!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        Log.d("login","!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");


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
                TOKEN               = UserProfile.getToken();
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

                Log.d(TAG, "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                Log.d(TAG, "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

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

        final JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("name", updateName);
        jsonObject.addProperty("info", updateInfo);
        jsonObject.addProperty("email", updateEmail);
        jsonObject.addProperty("phonenumber", updatePhonenumber);
        jsonObject.addProperty("username", updateUsername);
        jsonObject.addProperty("password", updatePassword);

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
        /**
         * JsonObjectinn er svo notaður til að búa til nýjan User í gagnagrunninn.
         */

//        Call<User> updateUserProfile = apiInterface.updateUserProfile(jsonObject);
        Call<User> updateUserProfile = apiInterface.updateUserProfile(jsonObject,
                "application/json",
                "Bearer " + LoginActivity.token
        );
        updateUserProfile.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                //hiding progress dialog
//                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Profile Updated: " + response.body(), Toast.LENGTH_LONG).show();

                    Log.d(TAG, "\n*****************************************");
                    Log.d(TAG, "*****************************************");
                    Log.d(TAG, "\n\n\n\t updateUserPRofile CALL seems to have been\n" +
                            "SUCCESSFUL ::::::)))))) :\n\n");

                    Log.d(TAG, "\n!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                    Log.d(TAG, "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                    getLoggedInUser();

                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
//                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();

                Log.d(TAG, "\n*****************************************");
                Log.d(TAG, "*****************************************");
                Log.d(TAG, "\n\n\n\t updateUserPRofile CALL was\n" +
                        "******NNNOOOOTTT  SUCCESSFUL aaaaaarrrrgggggg :\n\n");

                Log.d(TAG, "\n!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                Log.d(TAG, "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                getLoggedInUser();
            }
        });
    }

    public void getMyBooks() {
        Log.d(TAG, "\n\n*****************************************");
        Log.d(TAG, "*****************************************");
        Log.d(TAG, "\n\n\n\t getMyBooks():" +
                "\n\n\t\t pUsername = response.body().getUsername(); ******" + ProfileUsername + "*****\n\n");
        Log.d(TAG, "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        Log.d(TAG, "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!\n\n");
        /**
         GET kall sem skilar lista af bókum notanda.
         **/
        // RecyclerView - Birtir lista af bókum eins og skilgreint er í list_item.
        final RecyclerView UsersBooksrecyclerView = findViewById(R.id.users_books_recycler_view);
        UsersBooksrecyclerView.setLayoutManager(new LinearLayoutManager(this));
        UsersBooksrecyclerView.setAdapter(new BooksAdapter(new ArrayList<Book>(), R.layout.list_item, getApplicationContext()));

        Call<BookList> getMyBooks = apiInterface.myBooks("Bearer " + LoginActivity.token);
        getMyBooks.enqueue(new Callback<BookList>() {
            @Override
            public void onResponse(Call<BookList> call, Response<BookList> response) {
                List<Book> myBooks = response.body().getBooks();
                Log.d(TAG, "BBO -->> UsersBooks: " + myBooks);
                UsersBooksrecyclerView.setAdapter(new BooksAdapter(myBooks, R.layout.list_item, getApplicationContext()));

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

    public void showUsersBooks() {
        /**
         GET kall sem skilar lista af bókum notanda.
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
