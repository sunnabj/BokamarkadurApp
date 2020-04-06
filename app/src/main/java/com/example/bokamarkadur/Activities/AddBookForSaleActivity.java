package com.example.bokamarkadur.Activities;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.bokamarkadur.POJO.Book;
import com.example.bokamarkadur.R;
import java.io.File;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddBookForSaleActivity extends AppCompatActivity {

    private Button uploadImage;
    private Button submit;
    private Uri selectedImage;
    private String imgDecodableString = "";
    private ImageView viewUploadedImage;
    private ProgressDialog progressDialog;
    private static final int GALLERY_REQUEST_CODE = 1888;
    Spinner subjectSpinner;
    String LoggedInUsername = "";

    APIInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book_for_sale);
        uploadImage = findViewById(R.id.btnUploadImage);
        submit = findViewById(R.id.submit);
        viewUploadedImage = findViewById(R.id.uploadImage);

        // Hide System UI for best experience
        hideSystemUI();

        // Dropdown list with subjects
        subjectSpinner = findViewById(R.id.edtSubject);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.subject_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        subjectSpinner.setAdapter(adapter);

        // Tengjumst API Interface sem talar við bakendann okkar.
        apiInterface = APIClient.getClient().create(APIInterface.class);

        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickFromGallery();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitData();
            }
        });
    }

    private void pickFromGallery() {
        try {
            if (ActivityCompat.checkSelfPermission(AddBookForSaleActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(AddBookForSaleActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, GALLERY_REQUEST_CODE);
            } else {
                //Create an Intent with action as ACTION_PICK
                Intent intent=new Intent(Intent.ACTION_PICK);
                // Sets the type as image/*. This ensures only components of type image are selected
                intent.setType("image/*");
                //We pass an extra array with the accepted mime types. This will ensure only components with these MIME types as targeted.
                String[] mimeTypes = {"image/jpeg", "image/png"};
                intent.putExtra(Intent.EXTRA_MIME_TYPES,mimeTypes);
                // Launching the Intent
                startActivityForResult(intent, GALLERY_REQUEST_CODE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onActivityResult(int requestCode,int resultCode,Intent data){
        // Result code is RESULT_OK only if the user selects an Image
        if (resultCode == Activity.RESULT_OK)
            switch (requestCode){
                case GALLERY_REQUEST_CODE:
                    // Get user permission to access gallery.
                    if (ContextCompat.checkSelfPermission(AddBookForSaleActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {
                        // Permission is not granted
                    } else {
                        //data.getData returns the content URI for the selected Image
                        selectedImage = data.getData();

                        String[] filePathColumn = { MediaStore.Images.Media.DATA };
                        // Get the cursor
                        Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                        // Move to first row
                        cursor.moveToFirst();
                        //Get the column index of MediaStore.Images.Media.DATA
                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        //Gets the String value in the column
                        imgDecodableString = cursor.getString(columnIndex);
                        cursor.close();

                        viewUploadedImage.setImageURI(selectedImage);
                    }
                    break;
            }
    }

    private void submitData() {

        /**
         * Values are obtained from the form and processed so that it can make up a book object.
         */
        EditText title =  findViewById(R.id.edtTitle);
        EditText author = findViewById(R.id.edtAuthor);
        EditText edition = findViewById(R.id.edtEdition);
        EditText price = findViewById(R.id.edtPrice);
        EditText condition = findViewById(R.id.edtCondition);

        String titlePart = title.getText().toString();
        String authorPart = author.getText().toString();
        int editionPart = Integer.parseInt(edition.getText().toString());
        String conditionPart = condition.getText().toString();
        int pricePart = Integer.parseInt(price.getText().toString());
        String subjectPart = subjectSpinner.getSelectedItem().toString();

        progressDialog = new ProgressDialog(AddBookForSaleActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.show();


        File file = new File(imgDecodableString);
        Log.d("filepath: ", imgDecodableString);
        // Create a request body with file and image media type
        okhttp3.RequestBody fileReqBody = okhttp3.RequestBody.create(
                okhttp3.MediaType.parse("image/*"), file);
        // Create MultipartBody.Part using file request-body,file name and part name
        MultipartBody.Part imagePart = MultipartBody.Part.createFormData(
                "file", file.getName(), fileReqBody);

        if (imgDecodableString.equals("")) {
            /**
             * A new book object is created with the values from the form - No image.
             */
            Call<Book> newBookForSaleNoImg = apiInterface.addBookForSaleNoImg("application/json", "Bearer " + LoginActivity.token,
                    titlePart, authorPart, editionPart, conditionPart, pricePart, subjectPart);
            newBookForSaleNoImg.enqueue(new Callback<Book>() {
                @Override
                public void onResponse(Call<Book> call, Response<Book> response) {
                    //hiding progress dialog
                    progressDialog.dismiss();
                    Log.d("onResponse: ", String.valueOf(response.body()));
                    if (response.isSuccessful()) {

                        openMainActivity();

                    } else {
                        try {
                            Log.d("error", response.errorBody().string());
                        } catch (Exception e) {
                            Log.d("error: ", e.getMessage());
                        }
                    }
                }

                @Override
                public void onFailure(Call<Book> call, Throwable t) {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                    Log.d("onFailure: ", "failure");
                }
            });
        } else {
            /**
             * A new book object is created with the values from the form.
             */
            Call<Book> newBookForSale = apiInterface.addBookForSale("application/json", "Bearer " + LoginActivity.token,
                    imagePart, titlePart, authorPart, editionPart, conditionPart, pricePart, subjectPart);
            newBookForSale.enqueue(new Callback<Book>() {
                @Override
                public void onResponse(Call<Book> call, Response<Book> response) {
                    //hiding progress dialog
                    progressDialog.dismiss();
                    Log.d("onResponse: ", String.valueOf(response.body()));
                    if (response.isSuccessful()) {
                        openMainActivity();
                        //Toast.makeText
                              //  (getApplicationContext(), "Selected : " + subjectSpinner.getSelectedItem().toString(), Toast.LENGTH_SHORT)
                             //   .show();
                        Log.d("Success: ", response.body().getTitle() + " has been added.");
                    } else {
                        try {
                            Log.d("error", response.errorBody().string());
                        } catch (Exception e) {
                            Log.d("error: ", e.getMessage());
                        }
                    }
                }

                @Override
                public void onFailure(Call<Book> call, Throwable t) {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                    Log.d("onFailure: ", "failure");
                }
            });
        }
    }

    public void openMainActivity() {
        Log.d("login","***********************************************");
        Log.d("login","***********************************************");
        LoggedInUsername = getIntent().getStringExtra("LoggedInUsername");
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("LoggedInUsername", LoggedInUsername);
        Log.d("login", "\n\n\n BBO -->> Logged in Username is: **" + LoggedInUsername + "** \n\n\n");
        Log.d("login","!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        Log.d("login","!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
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