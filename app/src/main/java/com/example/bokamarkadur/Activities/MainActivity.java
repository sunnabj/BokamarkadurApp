package com.example.bokamarkadur.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bokamarkadur.Adapters.AvailableSubjectsAdapter;
import com.example.bokamarkadur.Adapters.BooksAdapter;
import com.example.bokamarkadur.POJO.Book;
import com.example.bokamarkadur.POJO.BookList;
import com.example.bokamarkadur.POJO.SubjectsResponse;
import com.example.bokamarkadur.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    // Takkar neðst á síðu - Verður fært í menu.
    private CardView loginbutton;
    private CardView registerbutton;
    private CardView AddBook;
    private CardView RequestBook;
    private CardView AllBooksBtn;

    // Notað fyrir debugging
    private static final String TAG = MainActivity.class.getSimpleName();

    APIInterface apiInterface;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            // Hide SystemUI for best experience
            hideSystemUI();

            /**+
             * Bottom navigation
             */
            BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
            bottomNavigationView.setSelectedItemId(R.id.home);
            bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    switch (menuItem.getItemId()){
                        case R.id.dashboard:
                            startActivity(new Intent(getApplicationContext(),
                                    AllBooksActivity.class));
                            overridePendingTransition(0,0);
                            return true;
                        case R.id.home:
                            return true;
                        case R.id.about:
                            if (LoginActivity.token == null) {
                                openLoginActivity();
                                Toast.makeText(getApplicationContext(), "Please log in", Toast.LENGTH_LONG).show();
                            } else {
                            startActivity(new Intent(getApplicationContext(),
                                    MenuActivity.class));
                            overridePendingTransition(0,0);}
                            return true;
                    }
                    return false;
                }
            });


            // Tengjumst API Interface sem talar við bakendann okkar.
            apiInterface = APIClient.getClient().create(APIInterface.class);

            // RecyclerView - Birtir lista af bókum eins og skilgreint er í list_item.
            final RecyclerView BookrecyclerView = findViewById(R.id.newest_books_recycler_view);
            BookrecyclerView.setLayoutManager(new LinearLayoutManager(this));
            BookrecyclerView.setAdapter(new BooksAdapter(new ArrayList<Book>(), R.layout.list_item, getApplicationContext()));

            // RecyclerView - Birtir lista af mögulegum fögum (subjects).
            final RecyclerView SubjectsrecyclerView = findViewById(R.id.available_subjects_recycler_view);
            SubjectsrecyclerView.setLayoutManager(new LinearLayoutManager(this));
            SubjectsrecyclerView.setAdapter(new AvailableSubjectsAdapter(new ArrayList<String>(), R.layout.list_subjects, getApplicationContext()));


            /**
             GET kall sem skilar lista af 10 nýjustu bókunum.
             **/
            Call<BookList> getNewestBooks = apiInterface.getNewestBooks();
            getNewestBooks.enqueue(new Callback<BookList>() {
                @Override
                public void onResponse(Call<BookList> call, Response<BookList> response) {
                    List<Book> books = response.body().getNewestBooks();
                    BookrecyclerView.setAdapter(new BooksAdapter(books, R.layout.list_item, getApplicationContext()));

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

            /**
             GET kall sem skilar lista af mögulegum fögum (subjects).
             **/
            Call<SubjectsResponse> getAvailableSubjects = apiInterface.getAvailableSubjects();
            getAvailableSubjects.enqueue(new Callback<SubjectsResponse>() {
                @Override
                public void onResponse(Call<SubjectsResponse> call, Response<SubjectsResponse> response) {
                    List<String> subjects = response.body().getAvailableSubjects();
                    SubjectsrecyclerView.setAdapter(new AvailableSubjectsAdapter(subjects, R.layout.list_subjects,  getApplicationContext()));

                    // TODO: Debug virkni, má eyða síðar meir.
                    Log.d(TAG, "Number of books received: " + subjects.size());
                }

                @Override
                public void onFailure(Call<SubjectsResponse> call, Throwable t) {
                    // Log error here since request failed
                    Log.e(TAG, t.toString());
                    call.cancel();
                }
            });

            // TODO: Eyða tökkum hér að neðan þegar navigation er komið.



            // login button
            loginbutton = findViewById(R.id.login);
            loginbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                     openLoginActivity();
                }
            });
            //register
            registerbutton = findViewById(R.id.register);
            registerbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openRegisterActivity();
                }
            });

        }

    public void openLoginActivity() {
            Intent intent= new Intent(this, LoginActivity.class);
            startActivity(intent);
    }
    public void openRegisterActivity() {
        Intent intent= new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
    public void AddbookforsaleActivity() {
        if (LoginActivity.token == null) {
            openLoginActivity();
            Toast.makeText(getApplicationContext(), "You must login to request a book", Toast.LENGTH_LONG).show();
        } else {
            Intent intent= new Intent(this, AddBookForSaleActivity.class);
            startActivity(intent);
        }
    }
    public void openRequestBookActivity() {
        if (LoginActivity.token == null) {
            openLoginActivity();
            Toast.makeText(getApplicationContext(), "You must login to request a book", Toast.LENGTH_LONG).show();
        } else {
            Intent intent = new Intent(this, RequestBookActivity.class);
            startActivity(intent);
        }
    }
    public void openAllBooksActivity() {
        Intent intent = new Intent(this, AllBooksActivity.class);
        startActivity(intent);
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
