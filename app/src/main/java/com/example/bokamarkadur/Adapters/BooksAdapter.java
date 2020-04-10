package com.example.bokamarkadur.Adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.bokamarkadur.POJO.Book;
import com.example.bokamarkadur.R;
import com.example.bokamarkadur.Activities.ViewBookActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.BookViewHolder> implements Filterable {

    // Notað fyrir debugging
    private static final String TAG = "BooksAdapter";

    private List<Book> books;
    private List<Book> booksListAll;
    private int rowLayout;
    private Context context;

    public class BookViewHolder extends RecyclerView.ViewHolder {

        LinearLayout booksLayout;
        TextView bookTitle;
        TextView bookAuthor;
        TextView bookStatus;
        ImageView image;

        public BookViewHolder(View v) {
            super(v);
            booksLayout = v.findViewById(R.id.books_layout);
            bookTitle = v.findViewById(R.id.title);
            bookAuthor = v.findViewById(R.id.author);
            bookStatus = v.findViewById(R.id.status);
            image = v.findViewById(R.id.image);
        }
    }

    public BooksAdapter(List<Book> books, int rowLayout, Context context) {
        this.books = books;
        booksListAll = new ArrayList<>();
        booksListAll.addAll(books);
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @Override
    public BooksAdapter.BookViewHolder onCreateViewHolder(ViewGroup parent,
                                                            int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new BookViewHolder(view);
    }

    // Tökum viðeigandi gildi fyrir hverja bók og tengjum við layout hluti.
    @Override
    public void onBindViewHolder(BookViewHolder holder, final int position) {

        String image = books.get(position).getImage();

        holder.bookTitle.setText(books.get(position).getTitle());
        holder.bookAuthor.setText(books.get(position).getAuthor());
        holder.bookStatus.setText(books.get(position).getStatus());

        // Ef image inniheldur tóma strenginn þá skilum við Noimage.jpg.
        if (image.equals("")) {
            image = "Noimage.jpg";
        }

        Picasso.get().load("https://fathomless-waters-17510.herokuapp.com/" + image).into(holder.image);

        // Sendum gildi áfram á ViewBookActivity.
        holder.booksLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: clicked on: " + books.get(position));

                Log.d(TAG, "Title: " + books.get(position).getTitle());
                Log.d(TAG, "Subject: " + books.get(position).getSubject());

                Toast.makeText(context, books.get(position).toString(), Toast.LENGTH_LONG);

                Intent intent = new Intent(context, ViewBookActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("bookTitle", books.get(position).getTitle());
                intent.putExtra("bookAuthor", books.get(position).getAuthor());
                intent.putExtra("bookEdition", books.get(position).getEdition());
                intent.putExtra("bookCondition", books.get(position).getCondition());
                intent.putExtra("bookPrice", books.get(position).getPrice());
                intent.putExtra("bookSubject", books.get(position).getSubject());
                intent.putExtra("bookStatus", books.get(position).getStatus());
                intent.putExtra("bookUser", books.get(position).getUser().getUsername());
                intent.putExtra("bookImage", books.get(position).getImage());
                intent.putExtra("phone", books.get(position).getUser().getPhonenumber());
                intent.putExtra("id", books.get(position).getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Book> filteredList = new ArrayList<>();
            if(constraint.toString().isEmpty()) {
                filteredList.addAll(booksListAll);
            } else {
                for (Book book: booksListAll) {
                    if(book.getTitle().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        filteredList.add(book);
                    }
                    if(book.getAuthor().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        filteredList.add(book);
                    }
                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;

            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            books.clear();
            books.addAll((Collection<? extends Book>) results.values);
            notifyDataSetChanged();
        }
    };
}
