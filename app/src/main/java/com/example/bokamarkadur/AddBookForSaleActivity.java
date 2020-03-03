package com.example.bokamarkadur;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bokamarkadur.POJO.Book;
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
        progressDialog = new ProgressDialog(AddBookForSaleActivity.this);
        //progressDialog.setMessage(getString(R.string.loading));
        progressDialog.setCancelable(false);
        progressDialog.show();

        //File file = new  File("/sdcard/Images/test.png");
        //RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        //MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName());
        //Call<FileInfo> call1 = fileService.upload(body);



        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("title", "1");
        jsonObject.addProperty("author", "1");
        jsonObject.addProperty("edition", "1");
        jsonObject.addProperty("price", "1");
        jsonObject.addProperty("subject", "COMPUTERSCIENCE");
        //jsonObject.addProperty("file", "null");

        Call<Book> newBookForSale = apiInterface.addBookForSale(jsonObject);
        newBookForSale.enqueue(new Callback<Book>() {
            @Override
            public void onResponse(Call<Book> call, Response<Book> response) {
                //hiding progress dialog
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Title: " + response.body().getTitle() + " Price: " + response.body().getPrice(), Toast.LENGTH_LONG).show();
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
