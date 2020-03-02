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
import com.example.bokamarkadur.POJO.User;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import java.io.File;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private Button submit;
    private Button submit1;
    private ProgressDialog progressDialog;

    APIInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        submit = (Button) findViewById(R.id.submit);
        submit1 = (Button) findViewById(R.id.submit1);

        // Tengjumst API Interface sem talar við bakendann okkar.
        apiInterface = APIClient.getClient().create(APIInterface.class);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitData();
            }
        });
        submit1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitData1();
            }
        });

    }

    private void submitData(){

        EditText username = (EditText) findViewById(R.id.edtUsername);
        EditText password = (EditText) findViewById(R.id.edtPassword);
        progressDialog = new ProgressDialog(LoginActivity.this);
        //progressDialog.setMessage(getString(R.string.loading));
        progressDialog.setCancelable(false);
        progressDialog.show();

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("username", username.getText().toString());
        jsonObject.addProperty("password", password.getText().toString());

        Call<User> loginUser = apiInterface.login(jsonObject);
        loginUser.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                //hiding progress dialog
                progressDialog.dismiss();
                if(response.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "success: "+response.body(), Toast.LENGTH_LONG).show();
                            //openMainActivity();
                    Log.d("myTag", String.valueOf(response.body()));
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void submitData1() {

        EditText title = (EditText) findViewById(R.id.edtTitle);
        EditText author = (EditText) findViewById(R.id.edtAuthor);
        EditText edition = (EditText) findViewById(R.id.edtEdition);
        progressDialog = new ProgressDialog(LoginActivity.this);
        //progressDialog.setMessage(getString(R.string.loading));
        progressDialog.setCancelable(false);
        progressDialog.show();

        File file = new  File("");
        //RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", "");
        //Call<FileInfo> call1 = fileService.upload(body);



        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("title", "1");
        jsonObject.addProperty("author", "1");
        jsonObject.addProperty("edition", "1");
        jsonObject.addProperty("price", "1");
        jsonObject.addProperty("subject", "COMPUTERSCIENCE");
        //jsonObject.addProperty("file", "");
        jsonObject.add("file",new JsonPrimitive(file.toString()));
        //jsonObject.addMultipartFile("file", "");


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
        Intent intent= new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    // Færa Response í bakenda eða annað?

//    private class PostResponse {
//        @SerializedName("errors")
//        @Expose
//        private Object errors;
//        @SerializedName("user")
//        @Expose
//        private User user;
//        @SerializedName("ok")
//        @Expose
//        private Boolean ok;
//        @SerializedName("msg")
//        @Expose
//        private String msg;
//
//        public Object getErrors() {
//            return errors;
//        }
//
//        public void setErrors(Object errors) {
//            this.errors = errors;
//        }
//
//        public User getUser() {
//            return user;
//        }
//
//        public void setUser(User user) {
//            this.user = user;
//        }
//
//        public Boolean getOk() {
//            return ok;
//        }
//
//        public void setOk(Boolean ok) {
//            this.ok = ok;
//        }
//
//        public String getMsg() {
//            return msg;
//        }
//
//        public void setMsg(String msg) {
//            this.msg = msg;
//        }
//    }
}
