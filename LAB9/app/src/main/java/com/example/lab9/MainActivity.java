package com.example.lab9;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnGoToAdd = findViewById(R.id.btnGoToAdd);
        Button btnGoToList = findViewById(R.id.btnGoToList);

        btnGoToAdd.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, AddEtudiant.class));
        });

        btnGoToList.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, ListEtudiant.class));
        });
    }
}