package com.example.bokamarkadur.Activities;

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
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bokamarkadur.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ViewBookActivity extends AppCompatActivity {

    Button btSMS;
    private static final String TAG = "ViewBookActivity";
    private static final int REQUEST_SMS = 0;
    private static final int REQ_PICK_CONTACT = 2 ;
    private EditText phoneEditText;
    private EditText messageEditText;
    private String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if ((getIntent().getStringExtra("bookStatus").equals("For sale"))) {
            setContentView(R.layout.activity_view_book);
        } else {
            setContentView(R.layout.activity_view_requested_book);
        }
        Log.d(TAG, "onCreate: started.");

        // Hide System UI for best experience
        hideSystemUI();

        getIncomingIntent();

        /**
         * A text can be written and a button then pushed at the bottom of the page which sends
         * the text as an SMS to the person that added the book.
         * This is only possible if the user is logged in.
         */
        messageEditText = findViewById(R.id.message_edit_text);
        btSMS = findViewById(R.id.bt_sms);

        btSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    int hasSMSPermission = checkSelfPermission(Manifest.permission.SEND_SMS);
                    if (hasSMSPermission != PackageManager.PERMISSION_GRANTED) {
                        if (!shouldShowRequestPermissionRationale(Manifest.permission.SEND_SMS)) {
                            showMessageOKCancel("Will you let this app access your SMS text" +
                                            "messenger?",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermissions(new String[] {Manifest.permission.SEND_SMS},
                                                        REQUEST_SMS);
                                            }
                                        }
                                    });
                            return;
                        }
                        requestPermissions(new String[] {Manifest.permission.SEND_SMS},
                                REQUEST_SMS);
                        return;
                    }
                    sendMySMS();
                }
            }
        });

        // This function sets up and displays the bottom navigation.
        setBottomNavigation();
    }


    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(ViewBookActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }
    /**
    * Sms-ið er sent til þess sem á/biður um bókina.
     */
    public void sendMySMS() {

        String message = messageEditText.getText().toString();

        if (LoginActivity.token != null) {
            SmsManager sms = SmsManager.getDefault();
            // if message length is too long messages are divided
            List<String> messages = sms.divideMessage(message);
            for (String msg : messages) {

                PendingIntent sentIntent = PendingIntent.getBroadcast(this, 0, new Intent("SMS_SENT"), 0);
                PendingIntent deliveredIntent = PendingIntent.getBroadcast(this, 0, new Intent("SMS_DELIVERED"), 0);
                sms.sendTextMessage(phone, null, msg, sentIntent, deliveredIntent);
                Toast.makeText(getApplicationContext(), "Message sent to " + phone, Toast.LENGTH_SHORT).show();

            }
        } else {
            Toast.makeText(getApplicationContext(),
                    "You have to be logged in to send a message",
                    Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Kóðinn hér fyrir neðan birtir þá bók sem notandi ýtti á til að komast inn í þetta Activity.
     */

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
            String phone = getIntent().getStringExtra("phone");

            if (condition == null) {
                condition = "Unknown";
            }

            if (image.equals("")) {
                image = "Noimage.jpg";
            }

            if ((getIntent().getStringExtra("bookStatus").equals("For sale"))) {
                this.phone = phone;
                setBookInfoFS(title, author, edition, condition, price, subject, status, user, image, phone);
            } else {
                this.phone = phone;
                setBookInfoR(title, author, edition, subject, status, user, image, phone);
            }

        }
    }

    // Set info for books that are for sale
    private void setBookInfoFS(String title, String author, int edition, String condition,
                             int price, String subject, String status, final String user, String image, String phone){
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

        //TextView phoneUser = findViewById(R.id.view_phone);
        //phoneUser.setText("Phone number: " + phone);

        Log.d("Tag", "asdasd"+phone);

        /**
         * Listener on a button that opens an activity with information
         * about the user that added the book.
         * Username is sent there by an intent.
         * A user has to be logged in to be able to see the user's info.
         */
        Button viewUser = findViewById(R.id.bt_view_user);
        viewUser.setText("  View " + user + "'s info");
        viewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (LoginActivity.token != null) {
                    Intent intent = new Intent(ViewBookActivity.this, UserInfoActivity.class);
                    intent.putExtra("username", user); //þurfti að vera declared final til að vera accessible
                    startActivity(intent);
                }
                else {
                 Toast.makeText(getApplicationContext(),
                    "You have to be logged in to get information about or contact the user",
                    Toast.LENGTH_LONG).show();
                }
            }
        });


        ImageView bookImage = findViewById(R.id.view_book_image);
        Picasso.get().load("https://fathomless-waters-17510.herokuapp.com/" + image).into(bookImage);
    }

    // Set info for books that are requested
    private void setBookInfoR(String title, String author, int edition, String subject,
                               String status, final String user, String image, String phone){
        Log.d(TAG, "setBookInfo: setting the title and author to widgets.");

        TextView bookTitle = findViewById(R.id.view_book_title);
        bookTitle.setText(title);

        TextView bookAuthor = findViewById(R.id.view_book_author);
        bookAuthor.setText("By " + author);

        TextView bookEdition = findViewById(R.id.view_book_edition);
        bookEdition.setText("Edition: " + edition);

        TextView bookSubject = findViewById(R.id.view_book_subject);
        bookSubject.setText("Subject: " + subject);

        TextView bookStatus = findViewById(R.id.view_book_status);
        bookStatus.setText(status);

        TextView bookUser = findViewById(R.id.view_book_user);
        bookUser.setText("Posted by: " + user);

        //TextView phoneUser = findViewById(R.id.view_phone);
        //phoneUser.setText("Phone number: "+phone);

        Log.d("Tag", "asdasd"+phone);

        /**
         * Listener á takka sem opnar activity með upplýsingum
         * um notandann sem setti bókina inn.
         * Username er sent þangað með intent.
         */
        Button viewUser = findViewById(R.id.bt_view_user);
        //viewUser.setText("View information about " + user);
        viewUser.setText("View " + user + "'s info");
        viewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewBookActivity.this, UserInfoActivity.class); //eða bara this)
                intent.putExtra("username", user); //þurfti að vera declared final til að vera accessible
                startActivity(intent);
            }
        });

        ImageView bookImage = findViewById(R.id.view_book_image);
        Picasso.get().load("https://fathomless-waters-17510.herokuapp.com/" + image).into(bookImage);
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

    // Take user to the LoginActivity.
    private void openLoginActivity() {
        Intent intent= new Intent(this, LoginActivity.class);
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
