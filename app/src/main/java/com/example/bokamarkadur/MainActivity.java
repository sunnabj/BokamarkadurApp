package com.example.bokamarkadur;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    private Button loginbutton;
    private Button registerbutton;
    private Button AddBook;
    private Button RequestBook;

    private TextView textViewResult;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            //add book 4 sale
            AddBook = (Button) findViewById(R.id.AddBook);
            AddBook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AddbookforsaleActivity();
                }
            });

            //Request book
            RequestBook = (Button) findViewById(R.id.RequestBook);
            RequestBook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openRequestBookActivity();
                }
            });

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

            textViewResult = findViewById(R.id.text_view_result);
            textViewResult.setText("Allar Bækur hérnar :D"+"\n"+
                    "Bækur 1"+"\n"+"Bækur 2"+"\n"+"Bækur 3"+"\n"+"Bækur 4"+"\n"+"Bækur 5"+"\n");
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
        Intent intent= new Intent(this, RequestBookActivity.class);
        startActivity(intent);
    }
}
