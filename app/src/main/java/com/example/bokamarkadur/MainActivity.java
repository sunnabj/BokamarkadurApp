package com.example.bokamarkadur;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bokamarkadur.POJO.Book;
import com.example.bokamarkadur.POJO.MultipleResource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    // Listar fyrir öll gildi í Book.
    ArrayList<String> ListviewImage = new ArrayList<String>();
    ArrayList<String> ListviewTitle = new ArrayList<String>();
    ArrayList<String> ListviewAuthor = new ArrayList<String>();

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
        Call<MultipleResource> call2 = apiInterface.doGetBookList();
        call2.enqueue(new Callback<MultipleResource>() {
            @Override
            public void onResponse(Call<MultipleResource> call, Response<MultipleResource> response) {

                MultipleResource bookResList = response.body();

                for (Book book : bookResList.books.book) {

                    ListviewImage.add(book.image);
                    ListviewTitle.add(book.title);
                    ListviewAuthor.add(book.author);

                }

                // Má eyða - Birtir toast ef svar hefur borist.
                Toast.makeText(getApplicationContext(), "Response received", Toast.LENGTH_LONG).show();

                final List<HashMap<String, String>> aList = new ArrayList<HashMap<String, String>>();

                // Fyrir hverja bók, færum við viðeigandi gildi inn í ArrayList.
                for (int i = 0; i < ListviewTitle.size(); i++) {
                    HashMap<String, String> hm = new HashMap<String, String>();
                    hm.put("ListImage", ListviewImage.get(i));
                    hm.put("ListTitle", ListviewTitle.get(i));
                    hm.put("ListAuthor", ListviewAuthor.get(i));
                    aList.add(hm);
                }

                final String[] from = {
                        "ListImage", "ListTitle", "ListAuthor"
                };

                final int[] to = {
                        R.id.image, R.id.title, R.id.author
                };

                final SimpleAdapter simpleAdapter = new SimpleAdapter(getBaseContext(), aList, R.layout.list_item, from, to);
                ListView simpleListview = (ListView)findViewById(R.id.list);
                simpleListview.setAdapter(simpleAdapter);
            }

            @Override
            public void onFailure(Call<MultipleResource> call, Throwable t) {
                call.cancel();
            }
        });

    }
}

