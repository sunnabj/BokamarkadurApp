package com.example.bokamarkadur;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class MenuActivity extends AppCompatActivity {

    // Takkar neðst á síðu - Verður fært í menu.
    private Button loginbutton;
    private Button registerbutton;
    private CardView AddBook;
    private CardView RequestBook;
    private CardView AllBooksBtn;
    private CardView aboutus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        //add book 4 sale
        AddBook = (CardView) findViewById(R.id.AddBook);
        AddBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddbookforsaleActivity();
            }
        });

        //Request book
        RequestBook = (CardView) findViewById(R.id.RequestBook);
        RequestBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRequestBookActivity();
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
        aboutus = (CardView) findViewById(R.id.about);
        aboutus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAboutusActivity();
            }
        });
        // Förum yfir í AllBooksActivity þar sem allar bækur eru birtar.
        AllBooksBtn = (CardView) findViewById(R.id.all_books);
        AllBooksBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAllBooksActivity();
            }
        });
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
        Intent intent= new Intent(this, AddBookForSaleActivity.class);
        startActivity(intent);
    }
    public void openRequestBookActivity() {
        Intent intent = new Intent(this, RequestBookActivity.class);
        startActivity(intent);
    }
    public void openAllBooksActivity() {
        Intent intent = new Intent(this, AllBooksActivity.class);
        startActivity(intent);
    }

}
