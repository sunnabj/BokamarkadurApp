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


    private CardView AllBooks;
    private CardView NewestBooks;

    private CardView AddBook;
    private CardView RequestBook;

    private CardView MyProfile;
    private CardView Something;

    private CardView AboutUs;
    private CardView Logout;

    private static final String TAG = MenuActivity.class.getSimpleName();

    // Connection to layout.
    // Call function that connects menu options to activities.
    // Bottom navigation setup.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        // Hide System UI for best experience
        hideSystemUI();

        // This functions makes the appropriate connections
        // from menu cards to activities.
        connectMenuCards();

        // This function sets up and displays the bottom navigation.
        setBottomNavigation();
    }

    // Connections made for menu cards to appropriate actvities.
    public void connectMenuCards() {
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
        NewestBooks = (CardView) findViewById(R.id.MenuCard2);
        NewestBooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewestBooksActivity();
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
        Something = (CardView) findViewById(R.id.MenuCard6);
        Something.setOnClickListener(new View.OnClickListener() {
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

    // Go back to homescreen.
    public void openMainActivity() {
        Intent intent= new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    // Open AboutUsActivity where the user
    // can read about the developers.
    public void openAboutUsActivity() {
        startActivity(new Intent(getApplicationContext(), AboutusActivity.class));
    }

    // Take user to the LoginActivity.
    public void openLoginActivity() {
        Intent intent= new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    // Take user AddBookforsaleActivity where he
    // can add books that he wants to sell.
    public void AddbookforsaleActivity() {
        if (LoginActivity.token == null) {
            openLoginActivity();
            Toast.makeText(getApplicationContext(), "You must logged in to request a book", Toast.LENGTH_LONG).show();
        } else {
            startActivity(new Intent(getApplicationContext(), AddBookForSaleActivity.class));
        }
    }

    // Take user RequestBookActivity where he
    // can request books that he wants.
    public void openRequestBookActivity() {
        if (LoginActivity.token == null) {
            openLoginActivity();
            Toast.makeText(getApplicationContext(), "You must login to request a book", Toast.LENGTH_LONG).show();
        } else {
            startActivity(new Intent(getApplicationContext(), RequestBookActivity.class));
        }
    }

    // Take user to a list of all listed books.
    public void openAllBooksActivity() {
        startActivity(new Intent(getApplicationContext(), AllBooksActivity.class));
    }

    // Take user to a list of the newest books.
    public void openNewestBooksActivity() {
        startActivity(new Intent(getApplicationContext(), NewestBooksActivity.class));
    }

    // Take user to view his profile.
    public void openViewProfileActivity() {
        startActivity(new Intent(getApplicationContext(), ViewProfileActivity.class));
    }

    // This function sets up connections to other activities
    // and displays the bottom navigation.
    public void setBottomNavigation() {
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
                        // If user is not logged in and attempts to open the Menu,
                        // the user is taken to the LoginActivity.
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
