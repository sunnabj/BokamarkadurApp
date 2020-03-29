package com.example.bokamarkadur;

import android.Manifest;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.bokamarkadur.POJO.Book;
import com.example.bokamarkadur.POJO.BookList;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewProfileActivity extends AppCompatActivity {

    private EditText phoneEditText;
    private EditText messageEditText;

    APIInterface apiInterface;

    private static final String TAG = "ViewProfileActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if ((getIntent().getStringExtra("bookStatus").equals("For sale"))) {
            setContentView(R.layout.activity_view_book);
        } else {
            setContentView(R.layout.activity_view_requested_book);
        }
        Log.d(TAG, "BBO -->> onCreate: started.");

        // Hide System UI for best experience
        hideSystemUI();

        getIncomingIntent();

        // Tengjumst API Interface sem talar við bakendann okkar.
        apiInterface = APIClient.getClient().create(APIInterface.class);

        // RecyclerView - Birtir lista af bókum eins og skilgreint er í list_item.
        final RecyclerView UsersBooksrecyclerView = findViewById(R.id.users_books_recycler_view);
        UsersBooksrecyclerView.setLayoutManager(new LinearLayoutManager(this));
        UsersBooksrecyclerView.setAdapter(new BooksAdapter(new ArrayList<Book>(), R.layout.list_item, getApplicationContext()));

        final String username = getIncomingIntent();

        /**
         GET kall sem skilar lista af bókum notanda.
         **/
        Call<BookList> getUsersBooks = apiInterface.getUsersBooks(username);
        getUsersBooks.enqueue(new Callback<BookList>() {
            @Override
            public void onResponse(Call<BookList> call, Response<BookList> response) {
                List<Book> UsersBooks = response.body().getUsersBooks();
                UsersBooksrecyclerView.setAdapter(new BooksAdapter(UsersBooks, R.layout.list_item, getApplicationContext()));

                // TODO: Debug virkni, má eyða síðar meir.
                Log.d(TAG, "BBO -->> Number of books user has: " + UsersBooks.size());
            }

            @Override
            public void onFailure(Call<BookList> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
                call.cancel();
            }
        });

    }
/*
    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(ViewProfileActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }
*/


    /**
     * BBO: Kóðinn hér fyrir neðan birtir prófíl fyrir viðkomandi notanda
     * 		sem ýtti á "My Profile" og opnaði þar með þetta Activity (ViewProfileActivity).
     */

    private String getIncomingIntent(){

        // TODO:

        Log.d(TAG, "BBO -->> getIncomingIntent: found intent extras.");

        String name = getIntent().getStringExtra("Name");
        String info = getIntent().getStringExtra("bookAuthor");

        String email = getIntent().getStringExtra("bookCondition");
        int phonenumber = getIntent().getIntExtra("phoneNuber", 0);

        String username = getIntent().getStringExtra("userName");
        String password = getIntent().getStringExtra("Password");
        String retypepassword = getIntent().getStringExtra("retypePassword");

        String usersbooks = getIntent().getStringExtra("usersBooks");

        setProfileInfo(name, info, email, phonenumber, username, password, retypepassword);
        // bætist svo við aftast "usersbooks" og (mögulega) "id" fremst og verður þá:
        // setProfileInfo(id, name, info, email, phonenumber, username, password, retypepassword, usersbooks);

        return username;
    }


    // Set info for User Profile
    private void setProfileInfo(String name, String info, String email, int phonenumber, String username,
                                String password, String retypepassword){
//  Eftir að undirbúa List<Book> álíka og gert í öðrum klösum, en þá verður línan fyrir ofan svona:
//                              String password, String retypepassword, List<Book> usersBooks){

        Log.d(TAG, "BBO -->> setProfileInfo: setting the title and author to widgets.");

        TextView profileName = findViewById(R.id.edtName);
        profileName.setText("Name: " + name);

        TextView profileInfo = findViewById(R.id.edtInfo);
        profileInfo.setText("Info: " + info);


        TextView profileEmail = findViewById(R.id.edtEmail);
        profileEmail.setText("Email " + email);

        TextView profilePhonenumber = findViewById(R.id.edtPhonenumber);
        profilePhonenumber.setText("Phonenumber " + phonenumber);


        TextView profileUserName = findViewById(R.id.edtUsername);
        profileUserName.setText("Username: " + username);

        TextView profilePassWord = findViewById(R.id.edtPassword);
        profilePassWord.setText("Password: " + password);

        TextView profileRetypePassword = findViewById(R.id.view_book_condition);
        profileRetypePassword.setText("re-type Password: " + retypepassword);

//        TextView usersBooks = findViewById(R.id.view_book_price);
//        usersBooks.setText("*My Books*");

    }

//      @REST Heroku: BookServiceImplementation.java
//      @Override
//      public List<Book> findByUser(User user) { return repository.findByUser(user); };


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
