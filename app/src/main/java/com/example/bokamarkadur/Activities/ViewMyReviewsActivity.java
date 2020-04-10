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

public class ViewMyReviewsActivity extends AppCompatActivity {

    private static final String TAG = "MyReviewsActivity";

    //Allows us to look at reviews in an orderly fashion
    ReviewsAdapter receivedReviewsAdapter;
    ReviewsAdapter writtenReviewsAdapter;

    // Connection to backend created.
    APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);

    TextView receivedReviewsMessage;
    TextView writtenReviewsMessage;

    public String loggedInUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_my_reviews);

        Log.d(TAG, "onCreate: started.");

        // Hide System UI for best experience
        hideSystemUI();


        Call<User> getLoggedInUser = apiInterface.getLoggedInUser("Bearer " + LoginActivity.token);
        getLoggedInUser.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                // This is the username of the currently logged in user.
                loggedInUsername = response.body().getUsername();

                //The header tells us which user the review are about.
                TextView user = findViewById(R.id.review_receiver);
                user.setText("Reviews for " + loggedInUsername + "  ");

//                getReceivedReviews(loggedInUsername);
                getWrittenReviews(loggedInUsername);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
                call.cancel();
            }

        });


        // This function sets up and displays the bottom navigation.
        setBottomNavigation();
    }

//    private void getReceivedReviews(String username) {
//        /**
//         * Sets up an orderly review list
//         */
//        final RecyclerView ReceivedReviews_RecyclerView = findViewById(R.id.received_reviews_recycler_view);
//        ReceivedReviews_RecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        ReceivedReviews_RecyclerView.setAdapter(new ReviewsAdapter(new ArrayList<Review>(), R.layout.list_reviews,
//                getApplicationContext()));
//
//        /**
//         * This function communicates with the server to get all reviews that have been written
//         * about the user with the username username. The reviews are delivered wrapped up in a
//         * convenient response.
//         */
//        final Call<ReviewsResponse> getReviews = apiInterface.viewReviews(username);
//        getReviews.enqueue(new Callback<ReviewsResponse>() {
//            @Override
//            public void onResponse(Call<ReviewsResponse> call, Response<ReviewsResponse> response) {
//                Log.d(TAG, "RESPONSE BODY: " + response.body().getClass());
//
//                List<Review> reviews = response.body().viewReviews();
//
//                receivedReviewsAdapter = new ReviewsAdapter(reviews, R.layout.list_reviews,
//                        getApplicationContext());
//
//                // If reviews exist for the user, they are shown as an orderly list.
//                if (receivedReviewsAdapter.getItemCount() != 0) {
//                    ReceivedReviews_RecyclerView.setAdapter(receivedReviewsAdapter);
//                }
//                // If no reviews exist for the user, this is made clear with a message.
//                else {
//                    receivedReviewsMessage = findViewById(R.id.received_reviews_message);
//                    receivedReviewsMessage.setText("You have received no Reviews");
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ReviewsResponse> call, Throwable t) {
//                // Log error here since request failed
//                Log.e(TAG, t.toString());
//                call.cancel();
//            }
//        });
//    }
//

    private void getWrittenReviews(String username) {
        /**
         * Sets up an orderly review list
         */
        final RecyclerView WrittenReviews_RecyclerView = findViewById(R.id.written_reviews_recycler_view);
        WrittenReviews_RecyclerView.setLayoutManager(new LinearLayoutManager(this));
        WrittenReviews_RecyclerView.setAdapter(new ReviewsAdapter(new ArrayList<Review>(), R.layout.list_reviews,
                getApplicationContext()));

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

                writtenReviewsAdapter = new ReviewsAdapter(reviews, R.layout.list_reviews,
                        getApplicationContext());

                // If reviews exist for the user, they are shown as an orderly list.
                if (writtenReviewsAdapter.getItemCount() != 0) {
                    WrittenReviews_RecyclerView.setAdapter(writtenReviewsAdapter);
                }
                // If no reviews exist for the user, this is made clear with a message.
                else {
                    writtenReviewsMessage = findViewById(R.id.written_reviews_message);
                    writtenReviewsMessage.setText("You have written no Reviews");
                }
            }

            @Override
            public void onFailure(Call<ReviewsResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
                call.cancel();
            }
        });
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
