package com.example.bokamarkadur;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.bokamarkadur.POJO.Book;
import com.example.bokamarkadur.POJO.BookList;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookBySubjectActivity extends AppCompatActivity {

    // Notað fyrir debugging
    private static final String TAG = "BookBySubjectAct";

    APIInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_books);

        apiInterface = APIClient.getClient().create(APIInterface.class);

        // RecyclerView - Birtir lista af bókum eftir fagi (subjects).
        // Notum sama layout list layout og all-books.
        final RecyclerView recyclerView = findViewById(R.id.books_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new BooksAdapter(new ArrayList<Book>(), R.layout.list_item, getApplicationContext()));

        final String subject = getIncomingIntent();

        /**
         GET kall sem skilar lista af öllum bókum eftir völdu fagi.
         **/
        Call<BookList> getBooksBySubject = apiInterface.getBooksBySubject(subject);
        getBooksBySubject.enqueue(new Callback<BookList>() {
            @Override
            public void onResponse(Call<BookList> call, Response<BookList> response) {
                List<Book> books = response.body().getBooksBySubject();
                recyclerView.setAdapter(new BooksAdapter(books, R.layout.list_item, getApplicationContext()));
            }

            @Override
            public void onFailure(Call<BookList> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
                call.cancel();
            }
        });
    }

    // Sækjum gildi frá mainActivity - hér valið fag (subject).
    private String getIncomingIntent(){
        String subject = getIntent().getStringExtra("viewSubject");
        return subject;
    }
}
