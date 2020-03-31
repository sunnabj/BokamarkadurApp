package com.example.bokamarkadur.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
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
    private CardView AddBook;
    private CardView RequestBook;
    private CardView AllBooksBtn;
    private CardView aboutus;
    private CardView logOut;

    private ProgressDialog progressDialog;

    APIInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        // Hide System UI for best experience
        hideSystemUI();

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

        //add book 4 sale
        AddBook = findViewById(R.id.AddBook);
        AddBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddbookforsaleActivity();
            }
        });

        //Request book
        RequestBook = findViewById(R.id.RequestBook);
        RequestBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRequestBookActivity();
            }
        });


        /**
         * Logout - setur authorization token sem null þannig að notandi getur ekki gert
         * neitt lengur sem krefst innskráningar.
         */
        logOut = findViewById(R.id.logout);
        logOut.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                LoginActivity.token = null;
                openMainActivity();


            }

        });

/*
        // login button
        loginbutton = (Button) findViewById(R.id.login);
        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLoginActivity();
            }
        });
        //register
        registerbutton = (Button) findViewById(R.id.register);
        registerbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRegisterActivity();
            }
        });


 */
        //Aboutus
        aboutus = findViewById(R.id.about);
        aboutus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAboutusActivity();
            }
        });
        // Förum yfir í AllBooksActivity þar sem allar bækur eru birtar.
        AllBooksBtn = findViewById(R.id.all_books);
        AllBooksBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAllBooksActivity();
            }
        });
    }

    public void openMainActivity() {
        Intent intent= new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public void openAboutusActivity() {
        Intent intent= new Intent(this, AboutusActivity.class);
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
            Toast.makeText(getApplicationContext(), "You must login to add a book for sale",
                    Toast.LENGTH_LONG).show();
        } else {
            Intent intent= new Intent(this, AddBookForSaleActivity.class);
            startActivity(intent);
        }
    }
    public void openRequestBookActivity() {
        if (LoginActivity.token == null) {
            openLoginActivity();
            Toast.makeText(getApplicationContext(), "You must login to request a book", Toast.LENGTH_LONG).show();
        } else {
            Intent intent = new Intent(this, RequestBookActivity.class);
            startActivity(intent);
        }
    }
    public void openAllBooksActivity() {
        Intent intent = new Intent(this, AllBooksActivity.class);
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
