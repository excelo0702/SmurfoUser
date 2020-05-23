package com.example.chhots;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;

import com.sarnava.textwriter.TextWriter;

public class SplashScreen_Activity extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGTH = 2500;
    TextWriter text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        text=findViewById(R.id.textwriter);
        text .setWidth(12)
                .setDelay(30)
                .setColor(Color.RED)
                .setConfig(TextWriter.Configuration.INTERMEDIATE)
                .setSizeFactor(30f)
                .setLetterSpacing(25f)
                .setText("SMURFO")
                .setListener(new TextWriter.Listener() {
                    @Override
                    public void WritingFinished() {

                        //do stuff after animation is finished
                    }
                })
                .startAnimation();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainIntent = new Intent(SplashScreen_Activity.this,MainActivity.class);
                SplashScreen_Activity.this.startActivity(mainIntent);
                SplashScreen_Activity.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
