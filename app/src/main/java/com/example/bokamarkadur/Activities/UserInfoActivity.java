package com.example.bokamarkadur.Activities;

import android.content.Intent;
import android.os.Bundle;

import com.example.bokamarkadur.POJO.User;
import com.example.bokamarkadur.POJO.UserResponse;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.bokamarkadur.R;

import java.io.IOException;

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

        apiInterface = APIClient.getClient().create(APIInterface.class);

        //VIRKAR
        final String username = getIncomingIntent();
        Log.d(TAG, "username: " + username);


        // String title = getIntent().getStringExtra("bookTitle");

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

                Button viewReviews = findViewById(R.id.viewReviews);

                final String username = response.body().getUser().getUsername();

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


    private String getIncomingIntent(){

        String username = getIntent().getStringExtra("username");
        return username;
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
