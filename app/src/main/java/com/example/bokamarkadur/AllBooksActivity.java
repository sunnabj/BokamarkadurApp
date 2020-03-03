package com.example.bokamarkadur;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bokamarkadur.POJO.Book;
import com.example.bokamarkadur.POJO.BookList;
import com.example.bokamarkadur.POJO.BooksAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllBooksActivity extends AppCompatActivity {

    // Notað fyrir debugging
    private static final String TAG = MainActivity.class.getSimpleName();

    APIInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_books);


        /**+
         * Þetta er navigation bar Sensei minn !!!
         */
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        // set home selected :D
        bottomNavigationView.setSelectedItemId(R.id.dashboard);
        // Virkjað það ??
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
                        startActivity(new Intent(getApplicationContext(),
                                AboutusActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });


        apiInterface = APIClient.getClient().create(APIInterface.class);

        final RecyclerView recyclerView = findViewById(R.id.books_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new BooksAdapter(new ArrayList<Book>(), R.layout.list_item, getApplicationContext()));

        /**
         GET kall sem skilar lista af öllum bókum.
         **/
        Call<BookList> getAllBooks = apiInterface.getBooks();
        getAllBooks.enqueue(new Callback<BookList>() {
            @Override
            public void onResponse(Call<BookList> call, Response<BookList> response) {
                List<Book> books = response.body().getBooks();
                recyclerView.setAdapter(new BooksAdapter(books, R.layout.list_item, getApplicationContext()));

                // TODO: Debug virkni, má eyða síðar meir.
                Log.d(TAG, "Number of books received: " + books.size());
            }

            @Override
            public void onFailure(Call<BookList> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
                call.cancel();
            }
        });
    }
}
