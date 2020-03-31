package com.example.bokamarkadur.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bokamarkadur.POJO.Review;
import com.example.bokamarkadur.R;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WriteReviewActivity extends AppCompatActivity {

    private static final String TAG = "WriteReviewActivity";

    APIInterface apiInterface;

    private Button submitReviewBtn; // Submits the written review and adds it to the database
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_review);

        Log.d(TAG, "onCreate: started");

        // Hide System UI for best experience
        hideSystemUI();

        apiInterface = APIClient.getClient().create(APIInterface.class);

        // The username of the user that all the reviews from the review list in the Review activity
        // were written about is fetched from there.
        final String username = getIncomingIntent();
        Log.d(TAG, "username: " + username);

        // It is made clear what user you are about to review.
        TextView user = findViewById(R.id.write_review_receiver);
        user.setText("Write a review for " + username);

        // When this button is pushed, the new review is published and saved.
        submitReviewBtn = findViewById(R.id.submit_review);

        submitReviewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitData(username);
            }
        });

    }

    /**
     * When the submit button is pushed, a new Review is created, with the text from the text box.
     * It is saved to the database and added to the list of reviews for the particular user.
     * The user is redirected to the list of reviews from before.
     * @param username: The username of the user which the review is about.
     */
    private void submitData(final String username) {
        EditText reviewBox = findViewById(R.id.edt_add_review);

        String reviewBody = reviewBox.getText().toString();

        progressDialog = new ProgressDialog(WriteReviewActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.show();

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("reviewBody", reviewBody);


        Call<Review> newReview = apiInterface.writeReview("Bearer " + LoginActivity.token,
                "application/json", username,jsonObject);
        newReview.enqueue(new Callback<Review>() {
            @Override
            public void onResponse(Call<Review> call, Response<Review> response) {
                progressDialog.dismiss();
                Log.d("onResponse: ", String.valueOf(response.body()));
                if (response.isSuccessful()) {
                    openReviewActivity(username);
                    Log.d("Success: ", "Your review has been added.");
                } else {
                    try {
                        Log.d("error", response.errorBody().string());
                    } catch (Exception e) {
                        Log.d("error: ", e.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<Review> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                Log.d("myTag", "HELPHELP");
            }
        });

    }

    /**
     * Incoming intent is fetched from the ReviewActivity.
     * @return The username of the user about which the reviews from the review list were written.
     */
    private String getIncomingIntent(){

        String username = getIntent().getStringExtra("username");
        return username;
    }

    /**
     * Opens the ReviewActivity again, with the correct username so that the correct list of reviews
     * is shown.
     */
    public void openReviewActivity(String username) {
        Intent intent = new Intent(this, ReviewActivity.class);
        intent.putExtra("username", username);
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
