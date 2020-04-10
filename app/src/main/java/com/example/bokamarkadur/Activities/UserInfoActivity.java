package com.example.bokamarkadur.Activities;

import android.content.Intent;
import android.os.Bundle;

import com.example.bokamarkadur.POJO.UserResponse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bokamarkadur.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserInfoActivity extends AppCompatActivity {

    private static final String TAG = "UserInfoActivity";
    APIInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        Log.d(TAG, "onCreate: started.");

        // Hide System UI for best experience
        hideSystemUI();

        /**+
         * Bottom navigation
         */
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.home);
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
                        if (LoginActivity.token == null) {
                            openLoginActivity();
                            Toast.makeText(getApplicationContext(), "Please log in", Toast.LENGTH_LONG).show();
                        } else {
                            startActivity(new Intent(getApplicationContext(),
                                    MenuActivity.class));
                            overridePendingTransition(0,0);}
                        return true;
                }
                return false;
            }
        });

        apiInterface = APIClient.getClient().create(APIInterface.class);

        // Get the username of the user we want to show, from viewBookActivity
        final String username = getIncomingIntent();
        Log.d(TAG, "username: " + username);

        /**
         * A function that fetches the user with the username acquired from the intent,
         * wrapped in an appropriate response.
         * The user's name, email and phone number are shown, and a button redirects to a list of
         * reviews that have been written about the user.
         */
        final Call<UserResponse> viewUser = apiInterface.viewUser(username);
        viewUser.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {

                Log.d(TAG, "RESPONSE BODY: " + response.body().getUser().getUsername());

                TextView userName = findViewById(R.id.view_user_name);
                userName.setText(response.body().getUser().getName());

                TextView userEmail = findViewById(R.id.view_user_email);
                userEmail.setText("Email: " + response.body().getUser().getEmail());

                TextView userPhone = findViewById(R.id.view_user_phone);
                userPhone.setText("Phone number: " + response.body().getUser().getPhonenumber());

                TextView userInfo = findViewById(R.id.view_user_info);
                userInfo.setText("About me: \n" + response.body().getUser().getInfo());

                Button viewReviews = findViewById(R.id.viewReviews);

                final String username = response.body().getUser().getUsername();

                /**
                 * This button opens up a new activity which shows the reviews that have been
                 * written about the user.
                 */
                viewReviews.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(UserInfoActivity.this, ReviewActivity.class);
                        intent.putExtra("username", username); //þurfti að vera declared final til að vera accessible
                        startActivity(intent);
                    }
                });

           }

           @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
                call.cancel();
            }

        });

    }

    /**
     *
     * @return the username of the user that added the book that was viewed in the ViewBookActivity.
     */
    private String getIncomingIntent(){

        String username = getIntent().getStringExtra("username");
        return username;
    }

    public void openLoginActivity() {
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
