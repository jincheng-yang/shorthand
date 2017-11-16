package com.example.datacollector;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {

    Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentManager fragmentManager = getFragmentManager();
        fragment = new Sketch();
        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .commit();

        final SeekBar sb = findViewById(R.id.seekBar);
        sb.setMax(Sketch.strokes.length - 1);

        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                getSupportActionBar().setTitle("Stroke: " + Sketch.strokes[sb.getProgress()]);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        final View cl = findViewById(R.id.container);

        ViewTreeObserver viewTreeObserver = cl.getViewTreeObserver();
        if (viewTreeObserver.isAlive()) {
            viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    cl.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    int width = cl.getWidth();
                    int height = cl.getHeight();
                    Sketch.canvasWidth = width;
                    Sketch.canvasHeight = height;
                }
            });
        }
    }

    /** Called when the user taps the Send button */
    public void saveStrokes(View view) {
        Sketch sketch = (Sketch) fragment;
        sketch.save();

        String filename = "data.json";
        Uri uri = Uri.parse("content://com.example.datacollector/" + filename);

        try {
            Intent emailIntent = new Intent(Intent.ACTION_SEND);
            emailIntent.setType("text/plain");
            emailIntent.putExtra(Intent.EXTRA_STREAM, uri);
            startActivity(emailIntent);
        } catch (Exception e) {
            e.printStackTrace();
            ((EditText) findViewById(R.id.editText)).setText(e.toString());
        }
    }

}
