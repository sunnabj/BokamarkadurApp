package com.example.bokamarkadur;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bokamarkadur.POJO.Book;
import com.example.bokamarkadur.POJO.BookResponse;

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

//        /**
//         Create new book
//         **/
//        Book book = new Book("Testing", "Tester Author", 2);
//        Call<Book> call1 = apiInterface.createUser(book);
//        call1.enqueue(new Callback<Book>() {
//            @Override
//            public void onResponse(Call<Book> call, Response<Book> response) {
//                Book book1 = response.body();
//
//                Toast.makeText(getApplicationContext(), book1.title + " " + book1.author + " " + book1.edition, Toast.LENGTH_SHORT).show();
//
//            }
//
//            @Override
//            public void onFailure(Call<Book> call, Throwable t) {
//                call.cancel();
//            }
//        });

        /**
         GET kall sem skilar lista af öllum bókum.
         **/
        Call<BookResponse> getAllBooks = apiInterface.getBookList();
        getAllBooks.enqueue(new Callback<BookResponse>() {
            @Override
            public void onResponse(Call<BookResponse> call, Response<BookResponse> response) {

                List<Book> books = response.body().getBookList().getBooks();
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

