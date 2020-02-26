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

public class RegisterActivity extends AppCompatActivity {

    private Button submit;
    private ProgressDialog progressDialog;
    private String baseUrl;
    private EditText edtUsername;
    private EditText edtPassword;
    private EditText edtReTypepassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        submit = (Button) findViewById(R.id.submit);
        baseUrl = "https://fathomless-waters-17510.herokuapp.com/";

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitData();
            }
        });
    }

    private void submitData() {

        EditText name = (EditText) findViewById(R.id.edtName);
        EditText email = (EditText) findViewById(R.id.edtEmail);
        EditText username = (EditText) findViewById(R.id.edtUsername);
        EditText password = (EditText) findViewById(R.id.edtPassword);
        EditText retypepassword = (EditText) findViewById(R.id.edtReTypepassword);
        progressDialog = new ProgressDialog(RegisterActivity.this);
        //progressDialog.setMessage(getString(R.string.loading));
        progressDialog.setCancelable(false);
        progressDialog.show();
        //Defining retrofit api service
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name", name.getText().toString());
        jsonObject.addProperty("email", email.getText().toString());
        jsonObject.addProperty("username", username.getText().toString());
        jsonObject.addProperty("password", password.getText().toString());
        jsonObject.addProperty("retypePassword", retypepassword.getText().toString());

        RegisterActivity.ApiService service = retrofit.create(RegisterActivity.ApiService.class);
        Call<RegisterActivity.PostResponse> call = service.postData(jsonObject);
        //calling the api
        call.enqueue(new Callback<RegisterActivity.PostResponse>() {
            @Override
            public void onResponse(Call<RegisterActivity.PostResponse> call, Response<RegisterActivity.PostResponse> response) {
                //hiding progress dialog
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), response.body().getMsg(), Toast.LENGTH_LONG).show();
                    openMainActivity();
                    Log.d("myTag", String.valueOf(response.body().getUser()));
                }
            }

            @Override
            public void onFailure(Call<RegisterActivity.PostResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private interface ApiService {
        @POST("newAccount")
        Call<RegisterActivity.PostResponse> postData(
                @Body JsonObject body);
    }

    private class PostResponse {

        @SerializedName("errors")
        @Expose
        private Object errors;
        @SerializedName("user")
        @Expose
        private User user;
        @SerializedName("msg")
        @Expose
        private String msg;
        @SerializedName("ok")
        @Expose
        private Boolean ok;

        public Object getErrors() {
            return errors;
        }

        public void setErrors(Object errors) {
            this.errors = errors;
        }

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public Boolean getOk() {
            return ok;
        }

        public void setOk(Boolean ok) {
            this.ok = ok;
        }
    }
}
