package com.test.dynseo.numberquiz;

import java.util.Random;

public class Question {
    //Operateurs predefinis pour Operation de Question
    public static final String ADD = "com.test.dynseo.numberquiz.ADD";
    public static final String SUB = "com.test.dynseo.numberquiz.SUB";
    public static final String MUL = "com.test.dynseo.numberquiz.MUL";
    public static final String DIV = "com.test.dynseo.numberquiz.DIV";
    public static final String NO = "com.test.dynseo.numberquiz.NO";
    //Les Bornes du Generateur
    //Avec la difficulte on peut envisager des valeurs Max plus grandes
    private static final int valueMin = 10;
    private static final int valueMax = 50;
    //Valeur A de la Question
    private int valueA;
    //Valeur B de la Question
    private int valueB;
    //Operateur de la Question
    //Avec la difficulte on peut envisager des probabilites de Multiplication et Division plus grandes
    private String operator;
    //Reponse de la Question
    private int answer;

    public Question(){
        //Random pour generer la Question
        //La classe Random choisit deja la Graine, autrement utiliser System.currentTimeMillis()
        Random random = new Random();
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
        //Valeur Verifiable cas erreur : NO
        //Chaque cas asocie a une Valeur Distincte
        switch(random.nextInt(4)){
            case 0:
                this.operator = ADD;
                break;
            case 1:
                this.operator = SUB;
                break;
            case 2:
                this.operator = MUL;
                break;
            case 3:
                this.operator = DIV;
                break;
            default:
                this.operator = NO;
        }
        this.computeAnswer();
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
