package com.example.bokamarkadur.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.bokamarkadur.POJO.Review;
import com.example.bokamarkadur.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * This class helps to publish the review list in the Review Activity in an orderly fashion.
 * It connects with list_reviews.xml
 */
public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewViewHolder> {

    private static final String TAG = "ReviewsAdapter";

    private List<Review> reviews;
    private List<Review> reviewsListAll;
    private int rowLayout;
    private Context context;

    public class ReviewViewHolder extends RecyclerView.ViewHolder {

        LinearLayout reviewsLayout; // The layout for the list of reviews
        TextView reviewWriter; // The user that wrote a certain review in a list
        TextView reviewBody; // The text of a certain review

        public ReviewViewHolder(View v) {
            super(v);
            reviewsLayout = v.findViewById(R.id.reviews_layout);
            reviewWriter = v.findViewById(R.id.review_writer);
            reviewBody = v.findViewById(R.id.review_body);

        }
    }

    public ReviewsAdapter(List<Review> reviews, int rowLayout, Context context) {
        this.reviews = reviews;
        reviewsListAll = new ArrayList<>();
        reviewsListAll.addAll(reviews);
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @Override
    public ReviewsAdapter.ReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReviewViewHolder holder, final int position) {

        holder.reviewWriter.setText("Written by: " + reviews.get(position).getReviewer().getName());
        holder.reviewBody.setText(reviews.get(position).getReviewBody());

    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

}
