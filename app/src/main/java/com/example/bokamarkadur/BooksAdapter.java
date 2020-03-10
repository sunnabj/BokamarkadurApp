package com.example.bokamarkadur;

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
        TextView price;
        ImageView image;

        public BookViewHolder(View v) {
            super(v);
            booksLayout = (LinearLayout) v.findViewById(R.id.books_layout);
            bookTitle = (TextView) v.findViewById(R.id.title);
            bookAuthor = (TextView) v.findViewById(R.id.author);
            price = (TextView) v.findViewById(R.id.price);
            image = (ImageView) v.findViewById(R.id.image);
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
        if (books.get(position).getPrice() == null) {
            holder.price.setText("Ekkert verð");
        } else {
            holder.price.setText("Verð: " + books.get(position).getPrice().toString() + " kr");
        }

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

                Toast.makeText(context, books.get(position).toString(), Toast.LENGTH_LONG);

                Intent intent = new Intent(context, ViewBookActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("bookTitle", books.get(position).getTitle());
                intent.putExtra("bookAuthor", books.get(position).getAuthor());
                intent.putExtra("bookEdition", books.get(position).getEdition());
                intent.putExtra("bookCondition", books.get(position).getCondition());
                intent.putExtra("bookPrice", books.get(position).getPrice());
                intent.putExtra("bookSubject", books.get(position).getSubjects());
                intent.putExtra("bookStatus", books.get(position).getStatus());
                intent.putExtra("bookUser", books.get(position).getUser().getUsername());
                intent.putExtra("bookImage", books.get(position).getImage());
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
