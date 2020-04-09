package com.example.bokamarkadur.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bokamarkadur.Adapters.BooksAdapter;
import com.example.bokamarkadur.POJO.Book;
import com.example.bokamarkadur.POJO.BookList;
import com.example.bokamarkadur.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookBySubjectActivity extends AppCompatActivity {

    // Used for debugging
    private static final String TAG = "BookBySubjectAct";

    // Connection to backend created.
    APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);

    // Connection to layout.
    // Header text set to match list type.
    // Call function that displays book list.
    // Bottom navigation setup.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_books);

        // Hide System UI for best experience
        hideSystemUI();

        // Subject was selected in MainActivity.
        final String subject = getIncomingIntent();

        // Set the header for BookListView = *selected subject*
        TextView bookListView = findViewById(R.id.view_book_list);
        bookListView.setText(subject);

        // This function creates a RecyclerView to display the book list.
        viewBooksBySubject(subject);

        // This function sets up and displays the bottom navigation.
        setBottomNavigation();
    }

    // Fetch selected subject from mainActivity.
    private String getIncomingIntent(){
        String subject = getIntent().getStringExtra("viewSubject");
        return subject;
    }

    // Sets up a RecyclerView and makes a Get Call to backend
    // to fetch books for selected subject.
    private void viewBooksBySubject(String subject) {
        // RecyclerView - for displaying all books for selected subject.
        // Notum sama layout list layout og all-books.
        final RecyclerView recyclerView = findViewById(R.id.books_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        /**
         GET call that returns list of books for selected subject.
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

    // Take user to the LoginActivity.
    public void openLoginActivity() {
        Intent intent= new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    // This function sets up connections to other activities
    // and displays the bottom navigation.
    public void setBottomNavigation() {
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
                        // If user is not logged in and attempts to open the Menu,
                        // the user is taken to the LoginActivity.
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
