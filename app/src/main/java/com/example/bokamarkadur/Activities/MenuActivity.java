package com.example.bokamarkadur.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.bokamarkadur.R;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MenuActivity extends AppCompatActivity {

    private Button loginbutton;
    private Button registerbutton;

    private CardView AllBooks;
    private CardView NewestBooks;

    private CardView AddBook;
    private CardView RequestBook;

    private CardView MyProfile;
    private CardView Something;

    private CardView AboutUs;
    private CardView Logout;

    private static final String TAG = MenuActivity.class.getSimpleName();
    LoginActivity LOGGEDIN;
    public String LoggedInUsername;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        // Hide System UI for best experience
        hideSystemUI();

        LoggedInUsername = getIntent().getStringExtra("LoggedInUsername");

        /**+
         * Bottom navigation
         */
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.about);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.dashboard:
                        startActivity(new Intent(getApplicationContext(),
                                AllBooksActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(),
                                MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.about:
                        return true;
                }
                return false;
            }
        });

        // View All Listed Books    ---         Card 1
        //              --> Location in MENU:   Row 1 / Column 1
        //
        AllBooks = (CardView) findViewById(R.id.MenuCard1);
        AllBooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAllBooksActivity();
            }
        });

        // View Newest Books    ---             Card 2
        //              --> Location in MENU:   Row 1 / Column 2
        //
        NewestBooks = (CardView) findViewById(R.id.MenuCard1);
        NewestBooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAllBooksActivity();
            }
        });

        //  Add a Book For Sale  ---            Card 3
        //              --> Location in MENU:   Row 2 / Column 1
        AddBook = (CardView) findViewById(R.id.MenuCard3);
        AddBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddbookforsaleActivity();
            }
        });

        // Request a Book       ---             Card 4
        //              --> Location in MENU:   Row 2 / Column 2
        //
        RequestBook = (CardView) findViewById(R.id.MenuCard4);
        RequestBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRequestBookActivity();
            }
        });

        //  My Profile          ---             Card 5
        //              --> Location in MENU:   Row 3 / Column 1
        //
        MyProfile = (CardView) findViewById(R.id.MenuCard5);
        MyProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openViewProfileActivity();
            }
        });

        //  Something           ---             Card 6
        //              --> Location in MENU:   Row 3 / Column 2
        //
        AboutUs = (CardView) findViewById(R.id.MenuCard6);
        AboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // openActivity(); fyrir hvað sem hér kemur.
            }
        });

        //  About Us            ---             Card 7
        //              --> Location in MENU:   Row 4 / Column 1
        //
        AboutUs = (CardView) findViewById(R.id.MenuCard7);
        AboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAboutUsActivity();
            }
        });

        //  Logout               ---            Card 8
        //              --> Location in MENU:   Row 4 / Column 2
        Logout = (CardView) findViewById(R.id.MenuCard8);
        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginActivity.token = null;
                openMainActivity();
            }
        });
    }

    public void openMainActivity() {
        Intent intent= new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void openAboutUsActivity() {
        Log.d("login","***********************************************");
        Log.d("login","***********************************************");
        LoggedInUsername = getIntent().getStringExtra("LoggedInUsername");
        Intent intent = new Intent(getApplicationContext(), AboutusActivity.class);
        intent.putExtra("LoggedInUsername", LoggedInUsername);
        Log.d("login", "\n\n\n BBO -->> Logged in Username is: **" + LoggedInUsername + "** \n\n\n");
        Log.d("login","!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        Log.d("login","!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        startActivity(intent);
    }
    public void openLoginActivity() {
        Intent intent= new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void openRegisterActivity() {
        Intent intent= new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    public void AddbookforsaleActivity() {
        if (LoginActivity.token == null) {
            openLoginActivity();
            Toast.makeText(getApplicationContext(), "You must logged in to request a book", Toast.LENGTH_LONG).show();
        } else {
            Log.d("login","***********************************************");
            Log.d("login","***********************************************");
            LoggedInUsername = getIntent().getStringExtra("LoggedInUsername");
            Intent intent = new Intent(getApplicationContext(), AddBookForSaleActivity.class);
            intent.putExtra("LoggedInUsername", LoggedInUsername);
            Log.d("login", "\n\n\n BBO -->> Logged in Username is: **" + LoggedInUsername + "** \n\n\n");
            Log.d("login","!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            Log.d("login","!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            startActivity(intent);
        }
    }
    public void openRequestBookActivity() {
        if (LoginActivity.token == null) {
            openLoginActivity();
            Toast.makeText(getApplicationContext(), "You must login to request a book", Toast.LENGTH_LONG).show();
        } else {
            Log.d("login","***********************************************");
            Log.d("login","***********************************************");
            Intent intent = new Intent(getApplicationContext(), RequestBookActivity.class);
            intent.putExtra("LoggedInUsername", LoggedInUsername);
            Log.d("login", "\n\n\n BBO -->> Logged in Username is: **" + LoggedInUsername + "** \n\n\n");
            Log.d("login","!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            Log.d("login","!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            startActivity(intent);
        }
    }
    public void openAllBooksActivity() {
        Log.d("login","***********************************************");
        Log.d("login","***********************************************");
        Intent intent = new Intent(getApplicationContext(), AllBooksActivity.class);
        intent.putExtra("LoggedInUsername", LoggedInUsername);
        Log.d("login", "\n\n\n BBO -->> Logged in Username is: **" + LoggedInUsername + "** \n\n\n");
        Log.d("login","!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        Log.d("login","!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        startActivity(intent);
    }

    public void openViewProfileActivity() {

        Log.d("login","***********************************************");
        Log.d("login","***********************************************");
        Intent intent = new Intent(getApplicationContext(), ViewProfileActivity.class);
        intent.putExtra("LoggedInUsername", LoggedInUsername);
        Log.d("login", "\n\n\n BBO -->> Logged in Username is: **" + LoggedInUsername + "** \n\n\n");
        Log.d("login","!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        Log.d("login","!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        startActivity(intent);
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
