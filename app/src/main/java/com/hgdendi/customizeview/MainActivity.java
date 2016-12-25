package com.hgdendi.customizeview;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onRoundImageViewClicked(View view) {
        Snackbar.make(view, "Clicked RoundImageView", Snackbar.LENGTH_SHORT).show();
    }

    public void onTaijiClicked(View view) {
        Snackbar.make(view, "Clicked Taiji", Snackbar.LENGTH_SHORT).show();
    }
}
