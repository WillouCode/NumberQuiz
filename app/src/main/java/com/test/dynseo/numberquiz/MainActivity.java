package com.test.dynseo.numberquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

//Projet NumberQuiz
//Version : 1
//IDE : Android Studio
//Langage : Java / XML
//Appareil Test : Virtual Pixel API 24 / Android 7.0

public class MainActivity extends AppCompatActivity {

    //Valeurs agissant comme Enum si selection de difficuletee
    //public static final String EASY = "com.test.dynseo.numberquiz.EASY"
    //public static final String MEDIUM = "com.test.dynseo.numberquiz.MEDIUM"
    //public static final String HARD = "com.test.dynseo.numberquiz.HARD"

    //Lanceur du Quiz
    private Button startButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startButton = findViewById(R.id.startButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            //Lanceur du Quiz
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),QuizActivity.class);
                startActivity(intent);
            }
        });
    }
}