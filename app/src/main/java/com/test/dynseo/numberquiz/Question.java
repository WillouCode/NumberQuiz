package com.test.dynseo.numberquiz;

import java.util.Random;

public class Question {
    //Operateurs predefinis pour Operation de Question
    public static final String ADD = "com.test.dynseo.numberquiz.ADD";
    public static final String SUB = "com.test.dynseo.numberquiz.SUB";
    public static final String MUL = "com.test.dynseo.numberquiz.MUL";
    public static final String DIV = "com.test.dynseo.numberquiz.DIV";
    public static final String NO = "com.test.dynseo.numberquiz.NO";
    //Le Modificateur pour definir Operation
    //Avec la difficulte on a plus de types operations
    private int levelMod;
    //Les Bornes du Generateur
    //Avec la difficulte on a des valeurs Max plus grandes
    private int valueMin;
    private int valueMax;
    //Valeur A de la Question
    private int valueA;
    //Valeur B de la Question
    private int valueB;
    //Operateur de la Question
    //Avec la difficulte on peut envisager des probabilites de Multiplication et Division plus grandes
    private String operator;
    //Reponse de la Question
    private int answer;
    //Choix
    private Random random;

    public Question(String questionDifficulty){
        //Random pour generer la Question
        //La classe Random choisit deja la Graine, autrement utiliser System.currentTimeMillis()
        this.random = new Random();
        this.applyDifficulty(questionDifficulty);
        //random.nextInt(borne) genere un Nombre entre 0 et borne (exclu)
        //Alors random.nextInt(max-min)+min genere un Nombre entre min et max (exclu)
        this.valueA = random.nextInt(valueMax-valueMin)+valueMin;
        this.valueB = random.nextInt(valueMax-valueMin)+valueMin;
        //On fait en sorte que A soit la plus grande valeur
        if(valueA<valueB){
            int temp= valueA;
            valueA = valueB;
            valueB = temp;
        }
        this.selectOperation();
        this.computeAnswer();
    }

    public void selectOperation(){
        //Valeur Verifiable cas erreur : NO
        //Chaque cas asocie a une Plage de Valeur Distincte selon Difficulte
        double operatorRolledProba = this.random.nextDouble();
        switch(levelMod){
            case 0:
                if(operatorRolledProba<0.50){
                    this.operator = ADD;
                }else{
                    this.operator = SUB;
                }
                break;
            case 1:
                if(operatorRolledProba<0.25){
                    this.operator = ADD;
                }else if(operatorRolledProba<0.50){
                    this.operator = SUB;
                }else if(operatorRolledProba<0.75){
                    this.operator = MUL;
                }else{
                    this.operator = DIV;
                }
                break;
            case 2:
                if(operatorRolledProba<0.10){
                    this.operator = ADD;
                }else if(operatorRolledProba<0.40){
                    this.operator = SUB;
                }else if(operatorRolledProba<0.70){
                    this.operator = MUL;
                }else{
                    this.operator = DIV;
                }
                break;
        }
    }

    public void applyDifficulty(String selectedDifficulty) {
        switch (selectedDifficulty) {
            case MainActivity.EASY:
                this.valueMin = 10;
                this.valueMax = 40;
                this.levelMod = 0;
                break;
            case MainActivity.MEDIUM:
                this.valueMin = 10;
                this.valueMax = 50;
                this.levelMod = 1;
                break;
            case MainActivity.HARD:
                this.valueMin = 20;
                this.valueMax = 50;
                this.levelMod = 2;
                break;
        }
    }

    public void computeAnswer(){
        //Valeur Verifiable cas erreur : MAX_VALUE
        //Chaque cas associe a une Operation Distincte
        switch(this.operator){
            case ADD:
                this.answer = this.valueA + this.valueB;
                break;
            case SUB:
                this.answer = this.valueA - this.valueB;
                break;
            case MUL:
                this.answer = this.valueA * this.valueB;
                break;
            case DIV:
                this.answer = this.valueA / this.valueB;
                break;
            default:
                this.answer = Integer.MAX_VALUE;
        }
    }

    public int getValueA() {
        return this.valueA;
    }

    public int getValueB() {
        return this.valueB;
    }

    public String getOperator() {
        return this.operator;
    }

    public String getOperatorSymbol() {
        switch(this.operator){
            case ADD:
                return "+";
            case SUB:
                return "-";
            case MUL:
                return "*";
            case DIV:
                return "/";
            default:
                return "Erreur";
        }
    }

    public int getAnswer(){
        return this.answer;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(this.valueA+" ");
        switch(this.operator){
            case ADD:
                sb.append("+");
                break;
            case SUB:
                sb.append("-");
                break;
            case MUL:
                sb.append("*");
                break;
            case DIV:
                sb.append("/");
                break;
            default:
                return "Erreur";
        }
        sb.append(" "+this.valueB+" = ?");
        return sb.toString();
    }
}
