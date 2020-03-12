package com.example.bokamarkadur;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

        // Hide System UI for best experience
        hideSystemUI();

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
