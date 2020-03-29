package com.example.bokamarkadur.Activities;

import android.os.Bundle;

import com.example.bokamarkadur.POJO.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
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

        Call<User> viewUser = apiInterface.viewUser(username);
        System.out.println("User úr apiInterface: " + viewUser.getClass());
        viewUser.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                //TODO: Hér er allt null
                // Textview-in virka - en það sem ég reyni að ná í úr responseBody er null.
                // Responsebody sjálft er af tagi User, en er null!

                //System.out.println("RESPONSE BODY: " + response.body());
                System.out.println("getname:" + response.body().getName());

                TextView userName = findViewById(R.id.view_user_name);
                userName.setText(response.body().getName());

                TextView userEmail = findViewById(R.id.view_user_email);
                userEmail.setText(response.body().getEmail());

                TextView userPhone = findViewById(R.id.view_user_phone);
                userPhone.setText(response.body().getPhonenumber());
           }

           @Override
            public void onFailure(Call<User> call, Throwable t) {
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
