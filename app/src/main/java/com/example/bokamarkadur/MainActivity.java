package com.example.bokamarkadur;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bokamarkadur.POJO.Book;
import com.example.bokamarkadur.POJO.MultipleResource;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    TextView responseText;
    APIInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        responseText = (TextView) findViewById(R.id.responseText);
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
         GET List Books
         **/
        Call<MultipleResource> call2 = apiInterface.doGetBookList();
        call2.enqueue(new Callback<MultipleResource>() {
            @Override
            public void onResponse(Call<MultipleResource> call, Response<MultipleResource> response) {

                MultipleResource bookResList = response.body();

                for (Book book : bookResList.books.book) {
                    responseText.append(book.title);
                    Toast.makeText(getApplicationContext(), "title : " + book.title + " author: " + book.author + " " + book.edition, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<MultipleResource> call, Throwable t) {
                call.cancel();
            }
        });


    }
}

