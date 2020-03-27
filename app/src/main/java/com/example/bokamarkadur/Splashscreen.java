package com.example.bokamarkadur;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.bokamarkadur.Activities.MainActivity;

public class Splashscreen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Hide System UI for best experience
        hideSystemUI();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
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
