package com.example.wasifyounas.peerekaamil;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class welcome_screen extends AppCompatActivity {

    Button btnRead;
    Button btnResumeReading;
    Button btnGetMore;
    SharedPreferences prefs;
    String lastPageNo = "LastPageNo";

    int defaultPage = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);

        getSupportActionBar().hide();

        btnRead = (Button) findViewById(R.id.btnRead);
        btnResumeReading = (Button) findViewById(R.id.btnResume);
        btnGetMore = (Button) findViewById(R.id.btnGetMoreBooks);

        prefs = this.getSharedPreferences(
                "myPref", Context.MODE_PRIVATE);

        btnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(welcome_screen.this , MainActivity.class);
                intent.putExtra("default_page", 2);
                startActivity(intent);
                finish();
            }
        });

        btnResumeReading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (prefs.contains(lastPageNo))
                {
                    defaultPage = prefs.getInt(lastPageNo , 2);
                }
                Intent intent = new Intent(welcome_screen.this , MainActivity.class);
                intent.putExtra("default_page", defaultPage);
                startActivity(intent);
                finish();
            }
        });

        btnGetMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });





      //  Window window = this.getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
      //  window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
       // window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
      //  window.setStatusBarColor(ContextCompat.getColor(this,R.color.my_statusbar_color));


    }
}
