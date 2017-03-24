package com.hgdendi.customizeview;

import android.graphics.drawable.Animatable;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LayoutAnimationController;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ScaleAnimation sa = new ScaleAnimation(0, 1, 0, 1);
        sa.setDuration(500);
        LayoutAnimationController lac = new LayoutAnimationController(sa, 0.5f);
        lac.setOrder(LayoutAnimationController.ORDER_NORMAL);
        ((ViewGroup) findViewById(R.id.linear_layout)).setLayoutAnimation(lac);

        findViewById(R.id.animated_vector).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Animatable) ((ImageView) v).getDrawable()).start();
            }
        });
    }

    public void onRoundImageViewClicked(View view) {
        Snackbar.make(view, "Clicked RoundImageView", Snackbar.LENGTH_SHORT).show();
    }

    public void onTaijiClicked(View view) {
        Snackbar.make(view, "Clicked Taiji", Snackbar.LENGTH_SHORT).show();
    }
}
