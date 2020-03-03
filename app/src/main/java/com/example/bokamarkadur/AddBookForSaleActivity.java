package com.example.bokamarkadur;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bokamarkadur.POJO.Book;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddBookForSaleActivity extends AppCompatActivity {

    private Button submit;
    private ProgressDialog progressDialog;

    APIInterface apiInterface;

    String imagePath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book_for_sale);
        submit = (Button) findViewById(R.id.submit);

        // Tengjumst API Interface sem talar við bakendann okkar.
        apiInterface = APIClient.getClient().create(APIInterface.class);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitData();
            }
        });

    }

    private void submitData() {

        EditText title = (EditText) findViewById(R.id.edtTitle);
        EditText author = (EditText) findViewById(R.id.edtAuthor);
        EditText edition = (EditText) findViewById(R.id.edtEdition);
        EditText condition = (EditText) findViewById(R.id.edtCondition);
        EditText price = (EditText) findViewById(R.id.edtPrice);
        EditText subject = (EditText) findViewById(R.id.edtSubject);
        progressDialog = new ProgressDialog(AddBookForSaleActivity.this);
        //progressDialog.setMessage(getString(R.string.loading));
        progressDialog.setCancelable(false);
        progressDialog.show();

        //File file = new  File("/sdcard/Images/test.png");
        //RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        //MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName());
        //Call<FileInfo> call1 = fileService.upload(body);


        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("title", title.getText().toString());
        jsonObject.addProperty("author", author.getText().toString());
        jsonObject.addProperty("edition", edition.getText().toString());
        jsonObject.addProperty("condition", condition.getText().toString());
        jsonObject.addProperty("price", price.getText().toString());
        jsonObject.addProperty("subject", subject.getText().toString());
        //jsonObject.addProperty("file", "null");

        Call<Book> newBookForSale = apiInterface.addBookForSale(jsonObject);
        newBookForSale.enqueue(new Callback<Book>() {
            @Override
            public void onResponse(Call<Book> call, Response<Book> response) {
                //hiding progress dialog
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Title: " +
                            response.body().getTitle(), Toast.LENGTH_LONG).show();
                    openMainActivity();
                    Log.d("myTag", String.valueOf(response.body()));
                }
            }

            @Override
            public void onFailure(Call<Book> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                Log.d("myTag", "HELPHLEP");
            }
        });
    }

    public void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
