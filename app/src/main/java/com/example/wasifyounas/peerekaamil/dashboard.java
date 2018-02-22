package com.example.wasifyounas.peerekaamil;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class dashboard extends AppCompatActivity {


    Button btnRead, btnRateUs, btnBookShelf, btnExit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        btnRead = (Button) findViewById(R.id.btnRead);
        btnRateUs = (Button) findViewById(R.id.btnRate);
        btnBookShelf = (Button) findViewById(R.id.btnShelf);
        btnExit = (Button) findViewById(R.id.btnExit);

        btnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(dashboard.this , MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


        btnRateUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(dashboard.this, "Rate us screen will come", Toast.LENGTH_SHORT).show();
            }
        });

        btnBookShelf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(dashboard.this, "Book shelf screen come here", Toast.LENGTH_SHORT).show();
            }
        });

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(dashboard.this, "Exit screen come here", Toast.LENGTH_SHORT).show();
            }
        });


    }
}
