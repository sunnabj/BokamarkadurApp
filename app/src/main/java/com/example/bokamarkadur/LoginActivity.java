package com.example.bokamarkadur;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bokamarkadur.POJO.User;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private Button submit;
    private ProgressDialog progressDialog;
    private TextView signup;
    public static String token;

    APIInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        submit = (Button) findViewById(R.id.submit);
        signup = (TextView) findViewById(R.id.bt_signup);
        // Tengjumst API Interface sem talar við bakendann okkar.
        apiInterface = APIClient.getClient().create(APIInterface.class);

        // Hide System UI for best experience
        hideSystemUI();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitData();
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRegisterActivity();
            }
        });
    }

    private void submitData(){

        /**
         * Gögn sem notandi slær inn í form í layout eru tekin og JsonObject búinn til úr þeim.
         */
        EditText username = (EditText) findViewById(R.id.edtUsername);
        EditText password = (EditText) findViewById(R.id.edtPassword);
        progressDialog = new ProgressDialog(LoginActivity.this);
        //progressDialog.setMessage(getString(R.string.loading));
        progressDialog.setCancelable(false);
        progressDialog.show();

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("username", username.getText().toString());
        jsonObject.addProperty("password", password.getText().toString());

        /**
         * JsonObjectinn er notaður til að ákvarða hvaða notandi í gagnagrunninnum er nú
         * loggaður inn. Fáum response á skjá sem segir til um success.
         */
        Call<User> loginUser = apiInterface.login(jsonObject);
        loginUser.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                //hiding progress dialog
                progressDialog.dismiss();
                if(response.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "success: "+response.body(), Toast.LENGTH_LONG).show();
                            openMainActivity();
                    Log.d("login", String.valueOf(response.body()));

                    token = response.body().getToken();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void openMainActivity() {
        Intent intent= new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public void openRegisterActivity() {
        Intent intent= new Intent(this, RegisterActivity.class);
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
}
