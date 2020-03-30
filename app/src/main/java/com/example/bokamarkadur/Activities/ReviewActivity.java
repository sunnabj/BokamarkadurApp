package com.example.bokamarkadur.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.bokamarkadur.Adapters.ReviewsAdapter;
import com.example.bokamarkadur.POJO.Review;
import com.example.bokamarkadur.POJO.ReviewList;
import com.example.bokamarkadur.POJO.ReviewsResponse;
import com.example.bokamarkadur.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReviewActivity extends AppCompatActivity {

    private static final String TAG = "ReviewActivity";

    ReviewsAdapter reviewsAdapter;

    APIInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        Log.d(TAG, "onCreate: started.");

        // Hide System UI for best experience
        hideSystemUI();

        apiInterface = APIClient.getClient().create(APIInterface.class);


        final RecyclerView recyclerView = findViewById(R.id.reviews_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new ReviewsAdapter(new ArrayList<Review>(), R.layout.list_reviews,
                getApplicationContext()));


        final String username = getIncomingIntent();
        Log.d(TAG, "username: " + username);

        final Call<ReviewsResponse> getReviews = apiInterface.viewReviews(username);
        getReviews.enqueue(new Callback<ReviewsResponse>() {
            @Override
            public void onResponse(Call<ReviewsResponse> call, Response<ReviewsResponse> response) {
                Log.d(TAG, "RESPONSE BODY: " + response.body().getClass());
                List<Review> reviews = response.body().viewReviews();

                reviewsAdapter = new ReviewsAdapter(reviews, R.layout.list_reviews,
                        getApplicationContext());

                recyclerView.setAdapter(reviewsAdapter);

                //TODO: Virkar! Ekkert crash! En birtist samt ekki neitt. Skoða!
            }

            @Override
            public void onFailure(Call<ReviewsResponse> call, Throwable t) {
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
