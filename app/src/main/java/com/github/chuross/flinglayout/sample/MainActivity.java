package com.github.chuross.flinglayout.sample;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.github.chrisbanes.photoview.OnScaleChangedListener;
import com.github.chrisbanes.photoview.PhotoView;
import com.github.chuross.flinglayout.FlingLayout;
import com.squareup.picasso.Picasso;


public class MainActivity extends AppCompatActivity {

    private static final String IMAGE_URL = "https://www.pakutaso.com/shared/img/thumb/043sukagamino17103_TP_V.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final FlingLayout flingLayout = findViewById(R.id.fling_layout);
        flingLayout.setDismissListener(new FlingLayout.DismissListener() {

            @Override
            public void onDismiss() {
                Toast.makeText(MainActivity.this, "dismiss!!", Toast.LENGTH_LONG).show();
            }
        });
        flingLayout.setPositionChangeListener(new FlingLayout.PositionChangeListener() {

            @Override
            public void onPositionChanged(int top, int left, float dragRangeRate) {
                flingLayout.setBackgroundColor(Color.argb(Math.round(255 * (1.0F - dragRangeRate)), 0, 0, 0));
            }
        });

        PhotoView imageView = findViewById(R.id.image);
        imageView.setOnScaleChangeListener(new OnScaleChangedListener() {
            @Override
            public void onScaleChange(float scaleFactor, float focusX, float focusY) {
                flingLayout.setDragEnabled(scaleFactor <= 1F);
            }
        });

        Picasso.with(this)
                .load(IMAGE_URL)
                .fit()
                .centerInside()
                .into(imageView);
    }
}
