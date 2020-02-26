package com.example.bokamarkadur;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {
    private Button button;

    private TextView textViewResult;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            // login button
            button = (Button) findViewById(R.id.button);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                     openLoginActivity();
                }
            });

            textViewResult = findViewById(R.id.text_view_result);

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://notendur.hi.is/hvp5/books/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

            Call<List<Books>> call = jsonPlaceHolderApi.getBooks();
            call.enqueue(new Callback<List<Books>>() {
                @Override
                public void onResponse(Call<List<Books>> call, Response<List<Books>> response) {

                    if (!response.isSuccessful()) {
                        textViewResult.setText("Code: " + response.code());
                        return;
                    }

                    List<Books> book = response.body();

                    for (Books books : book) {
                        String content = "";
                        content += "Title: " + books.getTitle() + "\n";
                        content += "Author: " + books.getAuthor() + "\n";
                        content += "Edition: " + books.getEdition() + "\n";
                        content += "Price: " + books.getPrice() + "\n\n";

                        textViewResult.append(content);
                    }
                }

                @Override
                public void onFailure(Call<List<Books>> call, Throwable t) {
                    textViewResult.setText(t.getMessage());
                }
            });
        }

    public void openLoginActivity() {
            Intent intent= new Intent(this, LoginActivity.class);
            startActivity(intent);
    }
}
