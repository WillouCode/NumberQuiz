package com.test.dynseo.numberquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

//Projet NumberQuiz
//Version : 2
//IDE : Android Studio
//Langage : Java / XML
//Appareil Test : Virtual Pixel API 24 / Android 7.0

public class MainActivity extends AppCompatActivity {

    //Cle predefinie pour passage valeurs dans Intent
    public static final String DIFFICULTY = "com.test.dynseo.numberquiz.DIFFICULTY";
    //Valeurs agissant comme Enum si selection de difficuletee
    public static final String EASY = "FACILE";
    public static final String MEDIUM = "NORMAL";
    public static final String HARD = "DIFFICILE";

    //Lanceur du Quiz
    private Button startButton;
    //Choix de la Difficulte
    private Spinner difficultySpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startButton = findViewById(R.id.startButton);
        difficultySpinner = findViewById(R.id.difficultySpinner);
        String[] choices = {EASY,MEDIUM,HARD};
        //Adapteur est comme le Model pour les ComboBox et Silder Java/Swing
        ArrayAdapter<String> difficultyChoices = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, choices);
        difficultySpinner.setAdapter(difficultyChoices);
        startButton.setOnClickListener(new View.OnClickListener() {
            //Lanceur du Quiz
            @Override
            public void onClick(View view) {
                String selectedDifficulty = difficultySpinner.getSelectedItem().toString();
                Intent intent = new Intent(getApplicationContext(),QuizActivity.class);
                intent.putExtra(DIFFICULTY,selectedDifficulty);
                startActivity(intent);
            }
        });
    }
}