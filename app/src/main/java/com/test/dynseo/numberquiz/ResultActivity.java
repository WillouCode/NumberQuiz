package com.test.dynseo.numberquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    //Cles predefinies pour Sauvegarde et Chargement valeurs de Bundle/SharedPreferences
    public static final String TOTAL_NUMBER = "com.test.dynseo.numberquiz.TOTAL_NUMBER";
    public static final String TOTAL_CORRECT = "com.test.dynseo.numberquiz.TOTAL_CORRECT";
    public static final String TOTAL_TIMEOUT = "com.test.dynseo.numberquiz.TOTAL_TIMEOUT";

    //Resutats
    private int total;
    private int correct;
    private int timeOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        //Back Stack automatiquement gere par Android, permet de revenir sur MainActivity en appuyant sur le Back Button de Appareil
        TextView totalScoreView = findViewById(R.id.totalScore);
        TextView totalTimeOutView = findViewById(R.id.totalTimeOut);
        if (savedInstanceState != null) {
            this.total = savedInstanceState.getInt(TOTAL_NUMBER,0);
            this.correct = savedInstanceState.getInt(TOTAL_CORRECT,0);
            this.timeOut = savedInstanceState.getInt(TOTAL_TIMEOUT,0);
        }else {
            Intent launchIntent = getIntent();
            this.total = launchIntent.getIntExtra(QuizActivity.QUESTION_NUMBER,0);
            this.correct = launchIntent.getIntExtra(QuizActivity.QUESTION_CORRECT,0);
            this.timeOut = launchIntent.getIntExtra(QuizActivity.QUESTION_TIMEOUT,0);
        }
        totalScoreView.setText("Score Final :\n"+String.valueOf(this.correct)+"/"+String.valueOf(this.total));
        totalTimeOutView.setText("Nombre de TimeOut :\n"+String.valueOf(this.timeOut));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //super appellable au Debut ou a la Fin, semble tout aussi valable dans un contexte de Sauvegarde
        outState.putInt(TOTAL_NUMBER,this.total);
        outState.putInt(TOTAL_CORRECT,this.correct);
        outState.putInt(TOTAL_TIMEOUT,this.timeOut);
        Log.i("SAVE_NUMBER","Total : "+this.total);
        Log.i("SAVE_CORRECT","Correct : "+this.correct);
        Log.i("SAVE_TIMEOUT","TimeOut : "+this.timeOut);
        super.onSaveInstanceState(outState);
    }
}
