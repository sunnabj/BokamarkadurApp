package com.example.bokamarkadur;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.mbms.FileInfo;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bokamarkadur.POJO.Book;
import com.google.gson.JsonObject;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequestBookActivity extends AppCompatActivity {

    private Button submit;
    private ProgressDialog progressDialog;

    APIInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_book);
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

    private void submitData(){

        EditText title = (EditText) findViewById(R.id.edtTitle);
        EditText author = (EditText) findViewById(R.id.edtAuthor);
        EditText edition = (EditText) findViewById(R.id.edtEdition);
        progressDialog = new ProgressDialog(this);
        //progressDialog.setMessage(getString(R.string.loading));
        progressDialog.setCancelable(false);
        progressDialog.show();

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("title", "1");
        jsonObject.addProperty("author", "1");
        jsonObject.addProperty("edition", "1");
        jsonObject.addProperty("subject", "COMPUTERSCIENCE");
        //jsonObject.addProperty("file", "null");

        Call<Book> newBookRequested = apiInterface.addBookRequested(jsonObject);
        newBookRequested.enqueue(new Callback<Book>() {
            @Override
            public void onResponse(Call<Book> call, Response<Book> response) {
                //hiding progress dialog
                progressDialog.dismiss();
                if(response.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "Book: "+response.body().getTitle()+" has been added", Toast.LENGTH_LONG).show();
                    openMainActivity();
                    Log.d("myTag", String.valueOf(response.body().getUser()));
                }
            }

            @Override
            public void onFailure(Call<Book> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void openMainActivity() {
        Intent intent= new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
