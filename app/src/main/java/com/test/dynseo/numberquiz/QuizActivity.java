package com.test.dynseo.numberquiz;

import androidx.appcompat.app.AppCompatActivity;

//import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

//GSON : Utilise apres videos explicatives de GSON de Tech Projects et CodingWithFlow sur Youtube
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class QuizActivity extends AppCompatActivity {

    //Cles predefinies pour Sauvegarde et Chargement valeurs de Bundle/SharedPreferences
    public static final String SHARED_PREFERENCES = "com.test.dynseo.numberquiz.SHARED_PREFERENCES";
    public static final String QUESTION_CURRENT = "com.test.dynseo.numberquiz.QUESTION_CURRENT";
    public static final String QUESTION_PROPOSITIONS = "com.test.dynseo.numberquiz.QUESTION_PROPOSITIONS";
    public static final String QUESTION_NUMBER = "com.test.dynseo.numberquiz.QUESTION_NUMBER";
    public static final String QUESTION_CORRECT = "com.test.dynseo.numberquiz.QUESTION_CORRECT";

    //TextView affichage de la Question
    private TextView questionView;
    //Boutons affichage et Selection de la Proposition
    private Button answerButtonA;
    private Button answerButtonB;
    private Button answerButtonC;
    private Button answerButtonD;
    //TextView affichage du Score
    private TextView scoreView;
    //La Question Actuelle contenant Reponse et Representation
    private Question questionCurrent;
    //Les Propositions Alternatives pour la Question
    private List<Integer> questionCurrentPropositions;
    //Numero de la Question Actuelle
    private int questionCurrentNumber;
    //Nombre de bonnes Reponses
    private int questionCorrectNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Le Quiz utilise 4 Boutons pour Proposer les Reponses
        //Autrement on peut envisager utiliser un Spinner contenant la Liste des Propositions et un Bouton de Validation
        //La Liste des Propositions peut se Sauvegarder avec GSON (Utilise apres videos explicatives de GSON de Tech Projects et CodingWithFlow sur Youtube)
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        this.catchViews();
        this.setViewsListeners();
        if (savedInstanceState != null) {
            Gson gson = new Gson();
            String jsonQuestionString = savedInstanceState.getString(QUESTION_CURRENT,null);
            String jsonPropositionsString = savedInstanceState.getString(QUESTION_PROPOSITIONS,null);
            //Type Objet a Reconstruire
            Type questionType = new TypeToken<Question>(){}.getType();
            Type propositionsType = new TypeToken<List<Integer>>(){}.getType();
            this.questionCurrent = gson.fromJson(jsonQuestionString,questionType);
            this.questionCurrentPropositions = gson.fromJson(jsonPropositionsString,propositionsType);
            if(this.questionCurrent==null){
                this.questionCurrent = new Question();
                this.questionCurrentPropositions = new Propositions(this.questionCurrent).getAnswersPropositions();
            }
            if(this.questionCurrentPropositions==null){
                this.questionCurrentPropositions = new Propositions(this.questionCurrent).getAnswersPropositions();
            }
            this.questionCurrentNumber = savedInstanceState.getInt(QUESTION_NUMBER,0);
            this.questionCorrectNumber = savedInstanceState.getInt(QUESTION_CORRECT,0);
            this.displayQuestion();
        }else {
            this.questionCurrentNumber = 0;
            this.questionCorrectNumber = 0;
            this.nextQuestion();
        }
    }

    //Methode catchViews : Permet de grouper la Recuperation des Vues depuis Layout
    public void catchViews() {
        this.questionView = findViewById(R.id.question);
        this.answerButtonA = findViewById(R.id.answerA);
        this.answerButtonB = findViewById(R.id.answerB);
        this.answerButtonC = findViewById(R.id.answerC);
        this.answerButtonD = findViewById(R.id.answerD);
        this.scoreView = findViewById(R.id.score);
    }

    //Methode setViewsListeners : Permet de grouper les Affectation des Listeners
    public void setViewsListeners() {
        View.OnClickListener answerListener = new View.OnClickListener() {
            //Lanceur du code de verification et generation
            @Override
            public void onClick(View view) {
                Button selectedAnswerButton = (Button) view;
                //Toast.makeText(getApplicationContext(), "Clic sur "+selectedAnswerButton.getText().toString(), Toast.LENGTH_SHORT).show();
                checkAnswer(Integer.parseInt(selectedAnswerButton.getText().toString()));
            }
        };
        this.answerButtonA.setOnClickListener(answerListener);
        this.answerButtonB.setOnClickListener(answerListener);
        this.answerButtonC.setOnClickListener(answerListener);
        this.answerButtonD.setOnClickListener(answerListener);
    }

    public void checkAnswer(int selectedAnswer){
        if(selectedAnswer==this.questionCurrent.getAnswer()){
            this.questionCorrectNumber++;
            Toast.makeText(getApplicationContext(), "Bonne Réponse!", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getApplicationContext(), "La Réponse était : "+String.valueOf(this.questionCurrent.getAnswer()), Toast.LENGTH_SHORT).show();
        }
        this.nextQuestion();
    }

    //Methode nextQuestion : Permet de passer a la Question suivante ou afficher resultats finaux
    public void nextQuestion(){
        this.questionCurrentNumber++;
        this.generateQuestion();
        this.displayQuestion();
    }

    //Methode generateQuestion : Permet de Creer une Nouvelle Question et ses Propositions
    public void generateQuestion(){
        this.questionCurrent = new Question();
        this.questionCurrentPropositions = new Propositions(this.questionCurrent).getAnswersPropositions();
    }

    //Methode displayQuestion : Permet de Afficher la Question Actuelle et ses Propositions
    public void displayQuestion(){
        StringBuilder sb = new StringBuilder();
        sb.append("Question "+this.questionCurrentNumber+":\n");
        sb.append(this.questionCurrent.toString());
        this.questionView.setText(sb.toString());
        this.displayAnswers();
        sb.delete(0,sb.length());
        sb.append("Score : "+String.valueOf(this.questionCorrectNumber)+" sur "+String.valueOf(this.questionCurrentNumber-1));
        this.scoreView.setText(sb.toString());
        Log.i("NUMBER","Question : "+String.valueOf(this.questionCurrentNumber));
        Log.i("CORRECT","Correctes : "+String.valueOf(this.questionCorrectNumber));
        Log.i("VALUE_A","Valeur A : "+String.valueOf(this.questionCurrent.getValueA()));
        Log.i("VALUE_B","Valeur B : "+String.valueOf(this.questionCurrent.getValueB()));
        Log.i("OPERATOR","Operateur : "+this.questionCurrent.getOperator());
        Log.i("ANSWER","Réponse : "+String.valueOf(this.questionCurrent.getAnswer()));
        Log.i("PROPOSITIONS","Propositions : "+this.questionCurrentPropositions.toString());
    }

    public void displayAnswers(){
        this.answerButtonA.setText(String.valueOf(this.questionCurrentPropositions.get(0)));
        this.answerButtonB.setText(String.valueOf(this.questionCurrentPropositions.get(1)));
        this.answerButtonC.setText(String.valueOf(this.questionCurrentPropositions.get(2)));
        this.answerButtonD.setText(String.valueOf(this.questionCurrentPropositions.get(3)));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //super appellable au Debut ou a la Fin, semble tout aussi valable dans un contexte de Sauvegarde
        Gson gson = new Gson();
        String jsonQuestionString = gson.toJson(this.questionCurrent);
        String jsonPropositionsString = gson.toJson(this.questionCurrentPropositions);
        outState.putString(QUESTION_CURRENT,jsonQuestionString);
        outState.putString(QUESTION_PROPOSITIONS,jsonPropositionsString);
        outState.putInt(QUESTION_NUMBER,this.questionCurrentNumber);
        outState.putInt(QUESTION_CORRECT,this.questionCorrectNumber);
        Log.i("SAVE_QUESTION","QuestionJSON : "+jsonQuestionString);
        Log.i("SAVE_PROPOSITIONS","PropositionsJSON : "+jsonPropositionsString);
        Log.i("SAVE_NUMBER","Number : "+this.questionCurrentNumber);
        Log.i("SAVE_CORRECT","Correct : "+this.questionCorrectNumber);
        super.onSaveInstanceState(outState);
    }

    /*
    //Si on utilise SahredPreference pour revenir exactement sur la Question en Cours apres Interruption
    //Utilise apres videos explicatives de GSON de Tech Projects et CodingWithFlow sur Youtube
    private void saveDataWithGSON(){
        SharedPreferences sharedPreferences =  getSharedPreferences(SHARED_PREFERENCES,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String jsonQuestionString = gson.toJson(this.questionCurrent);
        String jsonPropositionsString = gson.toJson(this.questionCurrentPropositions);
        editor.putString(QUESTION_CURRENT,jsonQuestionString);
        editor.putString(QUESTION_PROPOSITIONS,jsonPropositionsString);
        editor.putInt(QUESTION_NUMBER,this.questionCurrentNumber);
        editor.putInt(QUESTION_CURRENT,this.questionCorrectNumber);
        editor.apply();
    }

    //Utilise apres videos explicatives de GSON de Tech Projects et CodingWithFlow sur Youtube
    private void loadDataWithGSON(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES,MODE_PRIVATE);
        Gson gson = new Gson();
        String jsonQuestionString = sharedPreferences.getString(QUESTION_CURRENT,null);
        String jsonPropositionsString = sharedPreferences.getString(QUESTION_PROPOSITIONS,null);
        //Type Objet a Reconstruire
        Type questionType = new TypeToken<Question>(){}.getType();
        Type propositionsType = new TypeToken<List<Integer>>(){}.getType();
        this.questionCurrent = gson.fromJson(jsonQuestionString,questionType);
        this.questionCurrentPropositions = gson.fromJson(jsonPropositionsString,propositionsType);
        if(this.questionCurrent==null){
            this.questionCurrent = new Question();
        }
        if(this.questionCurrentPropositions==null){
            this.questionCurrentPropositions = new Propositions(this.questionCurrent).getAnswersPropositions();
        }
        this.questionCurrentNumber = sharedPreferences.getInt(QUESTION_NUMBER,0);
        this.questionCorrectNumber = sharedPreferences.getInt(QUESTION_CORRECT,0);
    }
    */
}
