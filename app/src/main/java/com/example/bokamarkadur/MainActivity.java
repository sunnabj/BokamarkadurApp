package com.example.bokamarkadur;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bokamarkadur.POJO.Book;
import com.example.bokamarkadur.POJO.BookResponse;
import com.example.bokamarkadur.POJO.BooksAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    APIInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        apiInterface = APIClient.getClient().create(APIInterface.class);

        final RecyclerView recyclerView = findViewById(R.id.books_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new BooksAdapter(new ArrayList<Book>(), R.layout.list_item, getApplicationContext()));


        /**
         GET kall sem skilar lista af öllum bókum.
         **/
        Call<BookResponse> getAllBooks = apiInterface.getBookList();
        getAllBooks.enqueue(new Callback<BookResponse>() {
            @Override
            public void onResponse(Call<BookResponse> call, Response<BookResponse> response) {
                int statusCode = response.code();
                List<Book> books = response.body().getBookList().getBooks();
                recyclerView.setAdapter(new BooksAdapter(books, R.layout.list_item, getApplicationContext()));


                Log.d(TAG, "Number of books received: " + books.size());


                // Má eyða - Birtir toast ef svar hefur borist.
                Toast.makeText(getApplicationContext(), "Response received", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<BookResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
                call.cancel();
            }
        });

    }
}

