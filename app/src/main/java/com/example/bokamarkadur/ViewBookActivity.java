package com.example.bokamarkadur;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.picasso.Picasso;

public class ViewBookActivity extends AppCompatActivity {

    private static final String TAG = "ViewBookActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_book);
        Log.d(TAG, "onCreate: started.");

        getIncomingIntent();

    }

    private void getIncomingIntent(){

        // TODO: Fá NoImage til að birtast.

        if(getIntent().hasExtra("bookTitle") && getIntent().hasExtra("bookAuthor")){
            Log.d(TAG, "getIncomingIntent: found intent extras.");

            String title = getIntent().getStringExtra("bookTitle");
            String author = getIntent().getStringExtra("bookAuthor");
            int edition = getIntent().getIntExtra("bookEdition", 0);
            String condition = getIntent().getStringExtra("bookCondition");
            int price = getIntent().getIntExtra("bookPrice", 0);
            String subject = getIntent().getStringExtra("bookSubject");
            String status = getIntent().getStringExtra("bookStatus");
            String user = getIntent().getStringExtra("bookUser");
            String image = getIntent().getStringExtra("bookImage");

            if (condition == null) {
                condition = "Unknown";
            }

            if (image.equals("")) {
                image = "Noimage.jpg";
            }

            setBookInfo(title, author, edition, condition, price, subject, status, user, image);
        }
    }

    private void setBookInfo(String title, String author, int edition, String condition,
                             int price, String subject, String status, String user, String image){
        Log.d(TAG, "setBookInfo: setting the title and author to widgets.");

        TextView bookTitle = findViewById(R.id.view_book_title);
        bookTitle.setText(title);

        TextView bookAuthor = findViewById(R.id.view_book_author);
        bookAuthor.setText("By " + author);

        TextView bookEdition = findViewById(R.id.view_book_edition);
        bookEdition.setText("Edition: " + edition);

        TextView bookCondition = findViewById(R.id.view_book_condition);
        bookCondition.setText("Condition: " + condition);

        TextView bookPrice = findViewById(R.id.view_book_price);
        bookPrice.setText("Price: " + price);

        TextView bookSubject = findViewById(R.id.view_book_subject);
        bookSubject.setText("Subject: " + subject);

        TextView bookStatus = findViewById(R.id.view_book_status);
        bookStatus.setText(status);

        TextView bookUser = findViewById(R.id.view_book_user);
        bookUser.setText("Posted by: " + user);

        ImageView bookImage = findViewById(R.id.view_book_image);
        Picasso.get().load("https://fathomless-waters-17510.herokuapp.com/" + image).into(bookImage);
    }
}
