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

import com.example.bokamarkadur.data.model.User;
import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;

public class RequestBookActivity extends AppCompatActivity {

    private Button submit;
    private ProgressDialog progressDialog;
    private String baseUrl;
    private EditText edtUsername;
    private EditText edtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_book);
        submit = (Button) findViewById(R.id.submit);
        baseUrl = "https://fathomless-waters-17510.herokuapp.com/";

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitData();
            }
        });
    }

    private void submitData(){

        EditText username = (EditText) findViewById(R.id.edtUsername);
        EditText password = (EditText) findViewById(R.id.edtPassword);
        progressDialog = new ProgressDialog(RequestBookActivity.this);
        //progressDialog.setMessage(getString(R.string.loading));
        progressDialog.setCancelable(false);
        progressDialog.show();
        //Defining retrofit api service
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("username", username.getText().toString());
        jsonObject.addProperty("password", password.getText().toString());

        RequestBookActivity.ApiService service = retrofit.create(RequestBookActivity.ApiService.class);
        Call<RequestBookActivity.PostResponse> call = service.postData(jsonObject);
        //calling the api
        call.enqueue(new Callback<RequestBookActivity.PostResponse>() {
            @Override
            public void onResponse(Call<RequestBookActivity.PostResponse> call, Response<RequestBookActivity.PostResponse> response) {
                //hiding progress dialog
                progressDialog.dismiss();
                if(response.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "Book: "+response.body().getTitle()+" has been added", Toast.LENGTH_LONG).show();
                    openRequestBookActivity();
                    Log.d("myTag", String.valueOf(response.body().getUser()));
                }
            }

            @Override
            public void onFailure(Call<RequestBookActivity.PostResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void openRequestBookActivity() {
        Intent intent= new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private interface ApiService {
        @POST("addrequestbook")
        Call<RequestBookActivity.PostResponse> postData(
                @Body JsonObject body);
    }

    private class PostResponse {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("author")
        @Expose
        private String author;
        @SerializedName("edition")
        @Expose
        private Object edition;
        @SerializedName("condition")
        @Expose
        private Object condition;
        @SerializedName("price")
        @Expose
        private Object price;
        @SerializedName("image")
        @Expose
        private String image;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("date")
        @Expose
        private Object date;
        @SerializedName("user")
        @Expose
        private User user;
        @SerializedName("messages")
        @Expose
        private Object messages;
        @SerializedName("subjects")
        @Expose
        private Object subjects;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public Object getEdition() {
            return edition;
        }

        public void setEdition(Object edition) {
            this.edition = edition;
        }

        public Object getCondition() {
            return condition;
        }

        public void setCondition(Object condition) {
            this.condition = condition;
        }

        public Object getPrice() {
            return price;
        }

        public void setPrice(Object price) {
            this.price = price;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public Object getDate() {
            return date;
        }

        public void setDate(Object date) {
            this.date = date;
        }

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }

        public Object getMessages() {
            return messages;
        }

        public void setMessages(Object messages) {
            this.messages = messages;
        }

        public Object getSubjects() {
            return subjects;
        }

        public void setSubjects(Object subjects) {
            this.subjects = subjects;
        }

    }
}
