package com.example.bokamarkadur.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bokamarkadur.Adapters.ReviewsAdapter;
import com.example.bokamarkadur.POJO.Review;
import com.example.bokamarkadur.POJO.ReviewsResponse;
import com.example.bokamarkadur.POJO.User;
import com.example.bokamarkadur.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReviewActivity extends AppCompatActivity {

    private static final String TAG = "ReviewActivity";

    ReviewsAdapter reviewsAdapter; //Allows us to look at reviews in an orderly fashion

    APIInterface apiInterface;

    private Button addReviewBtn; //Opens a view where the user can add a new review to the review list.
    public String loggedInUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        Log.d(TAG, "onCreate: started.");

        // Hide System UI for best experience
        hideSystemUI();

        setBottomNavigation();

        /**
         * Sets up an orderly review list
         */
        final RecyclerView recyclerView = findViewById(R.id.reviews_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new ReviewsAdapter(new ArrayList<Review>(), R.layout.list_reviews,
                getApplicationContext()));

        // Fetches the username of the user from UserInfoActivity.
        final String username = getIncomingIntent();
        Log.d(TAG, "username: " + username);

        //The header tells us which user the review are about.
        TextView user = findViewById(R.id.review_receiver);
        user.setText("Reviews for " + username + "  ");

        apiInterface = APIClient.getClient().create(APIInterface.class);

        /**
         * Retrieves the current logged in user, and sets up a button that allows the user to
         * write his own reviews.
         */
        Call<User> getLoggedInUser = apiInterface.getLoggedInUser("Bearer " + LoginActivity.token);
        getLoggedInUser.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Log.d(TAG, "Við fórum í onResponse");
                // This is the username of the currently logged in user.
                loggedInUsername = response.body().getUsername();
                setWriteReviewButton(username, loggedInUsername);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                // Log error here since request failed
                Log.d(TAG, "Við fórum í onFailure");
                Log.e(TAG, t.toString());
                call.cancel();
            }

        });


        /**
         * This function communicates with the server to get all reviews that have been written
         * about the user with the username username. The reviews are delivered wrapped up in a
         * convenient response.
         */
        final Call<ReviewsResponse> getReviews = apiInterface.viewReviews(username);
        getReviews.enqueue(new Callback<ReviewsResponse>() {
            @Override
            public void onResponse(Call<ReviewsResponse> call, Response<ReviewsResponse> response) {
                Log.d(TAG, "RESPONSE BODY: " + response.body().getClass());

                List<Review> reviews = response.body().viewReviews();

                reviewsAdapter = new ReviewsAdapter(reviews, R.layout.list_reviews,
                        getApplicationContext());

                // If reviews exist for the user, they are shown as an orderly list.
                if (reviewsAdapter.getItemCount() != 0) {
                    recyclerView.setAdapter(reviewsAdapter);
                }
                // If no reviews exist for the user, this is made clear with a message.
                else {
                    TextView noReviews = findViewById(R.id.no_reviews);
                    noReviews.setText("No Reviews available for " + username);
                }
            }

            @Override
            public void onFailure(Call<ReviewsResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
                call.cancel();
            }

        });

        Log.d(TAG, "ReviewActivity: loggedInUsername = " + loggedInUsername);
        Log.d(TAG, "ReviewActivity: username = " + username);

    }

    /**
     *
     * @return the username of the user that came from the userinfo activity.
     */
    private String getIncomingIntent(){

        String username = getIntent().getStringExtra("username");
        return username;
    }

    public void openLoginActivity() {
        Intent intent= new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    /**
     * Sets up a button that directs the logged in user to an activity where he can write a new
     * review about the user that has received the reviews that are being viewed.
     * @param username: The username of the user that has received the reviews that are being viewed.
     * @param loggedInUsername: The username of the currently logged in user.
     */
    private void setWriteReviewButton(final String username, final String loggedInUsername) {

        addReviewBtn = findViewById(R.id.add_review);

        addReviewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (username.equals(loggedInUsername)) {
                    //if (loggedInUsername.equals(username))
                    Toast.makeText(getApplicationContext(),
                            "You cannot review yourself.",
                            Toast.LENGTH_LONG).show();
                }
                else {
                    Intent intent = new Intent(ReviewActivity.this, WriteReviewActivity.class);
                    intent.putExtra("username", username); //þurfti að vera declared final til að vera accessible
                    startActivity(intent);
                }
            }
        });
    }

    private void setBottomNavigation() {
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