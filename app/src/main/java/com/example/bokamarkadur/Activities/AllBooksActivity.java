package com.example.bokamarkadur.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
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

public class AllBooksActivity extends AppCompatActivity {

    // Used for debugging
    private static final String TAG = MainActivity.class.getSimpleName();

    // Adapter to display list of books.
    BooksAdapter booksAdapter;

    // Connection to backend created.
    APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);

    // Connection to layout.
    // Header text set to match list type.
    // Call function that displays book list.
    // Bottom navigation setup.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_book_list);

        // Set the header for BookListView = "All Books".
        TextView bookListView = findViewById(R.id.view_book_list);
        bookListView.setText("All Books");

        // Hide System UI for best experience
        hideSystemUI();

        // This function creates a RecyclerView to display the book list.
        ViewAllBooks();

        // This function sets up and displays the bottom navigation.
        setBottomNavigation();

    }

    // Set up a RecyclerView and makes a Get Call to backend
    // to fetch all listed books.
    private void ViewAllBooks() {
        final RecyclerView recyclerView = findViewById(R.id.books_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        /**
         GET call that returns list of all listed books.
         **/
        Call<BookList> getAllBooks = apiInterface.getBooks();
        getAllBooks.enqueue(new Callback<BookList>() {
            @Override
            public void onResponse(Call<BookList> call, Response<BookList> response) {
                List<Book> books = response.body().getBooks();

                booksAdapter = new BooksAdapter(books, R.layout.list_item, getApplicationContext());

                recyclerView.setAdapter(booksAdapter);

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

    // Take user to the LoginActivity.
    private void openLoginActivity() {
        Intent intent= new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    // Create a search bar in top navigation.
    // @param menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        androidx.appcompat.widget.SearchView searchView = (androidx.appcompat.widget.SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener((new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                booksAdapter.getFilter().filter(newText);
                return false;
            }
        }));

        return super.onCreateOptionsMenu(menu);
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

    private void hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        // Set the content to appear under the system bars so that the
                        // content doesn't resize when the system bars hide and show.
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and status bar
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }
}
