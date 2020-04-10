package com.example.bokamarkadur.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bokamarkadur.POJO.User;
import com.example.bokamarkadur.R;
import com.google.gson.JsonObject;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.mobsandgeeks.saripaar.annotation.Pattern;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity implements com.mobsandgeeks.saripaar.Validator.ValidationListener {

    private ProgressDialog progressDialog;

    APIInterface apiInterface;

    @NotEmpty
    @Length(min = 3, max = 10)
    private EditText name;

    @NotEmpty
    @Length(min = 3, max = 10)
    private EditText username;

    @NotEmpty
    @Password
    @Length(min = 8, max = 20)
    private EditText password;

    @ConfirmPassword
    private EditText retypepassword;

    @NotEmpty
    @Pattern(regex =  "^\\(?([0-9]{3})\\)?[-.\\s]?([0-9]{4})$")
    private EditText phonenumber;

    @NotEmpty
    @Email
    private EditText email;

    private Button submit;

    private com.mobsandgeeks.saripaar.Validator validator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        submit = findViewById(R.id.submit);
        initView();
        validator = new Validator(this);
        validator.setValidationListener(this);

        // Tengjumst API Interface sem talar við bakendann okkar.
        apiInterface = APIClient.getClient().create(APIInterface.class);

        // Hide System UI for best experience
        hideSystemUI();

    }

    private void initView() {
        name = findViewById(R.id.edtName);
        email = findViewById(R.id.edtEmail);
        phonenumber = findViewById(R.id.edtPhone);
        username = findViewById(R.id.edtUsername);
        password = findViewById(R.id.edtPassword);
        retypepassword = findViewById(R.id.edtReTypepassword);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitData();
            }
        });
    }

    private void submitData() {
        validator.validate();
    }

    public void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void openLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    private void hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        // Set the content to appear under the system bars so that the
                        // content doesn't resize when the system bars hide and show.
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and status bar
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    @Override
    public void onValidationSucceeded() {

        /**
         * Upplýsingar eru fengnar úr formi í layout og JsonObject búinn til út frá þeim
         */

        progressDialog = new ProgressDialog(RegisterActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.show();

        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name", name.getText().toString());
        jsonObject.addProperty("email", email.getText().toString());
        jsonObject.addProperty("phonenumber", phonenumber.getText().toString());
        jsonObject.addProperty("username", username.getText().toString());
        jsonObject.addProperty("password", password.getText().toString());
        jsonObject.addProperty("retypePassword", retypepassword.getText().toString());

        /**
         * JsonObjectinn er svo notaður til að búa til nýjan User í gagnagrunninn.
         */

        Call<User> registerUser = apiInterface.register(jsonObject);
        registerUser.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                Log.d("RegisterResponse", String.valueOf(response.code()));
                if (String.valueOf(response.code()).equals("400")) {
                    username.setError(getText(R.string.username_already_exists));
                }

                //hiding progress dialog
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "success: " + response.body(), Toast.LENGTH_LONG).show();
                    openLoginActivity();
                    Log.d("myTag", String.valueOf(response.body()));
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d("RegisterOnFailure", t.getMessage());
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);

            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
        }
    }
}
