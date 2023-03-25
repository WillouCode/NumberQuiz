package com.test.dynseo.numberquiz;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Propositions {
    //Tableau des propositions generees
    //Avec la difficulte on peut envisager un nombres de propositions alternatives plus grand
    private List<Integer> answersPropositions;
    //Random pour calculs
    private Random random;

    //Generer une Liste de Possibilitees qui seront affectees dans QuizActivity
    //Les Methodes plusTenAnswer, minusTenAnswer et closeAnswer garantissent au minium trois choix simples a calculer distincts de Reponse
    public Propositions(Question question){
        this.answersPropositions = new ArrayList<Integer>(4);
        this.random = new Random();
        //Calculer des Propositions
        //On ajoute dans un Set avant ajout dans la Liste comme etape intermediaire pour assurer aucun Doublon
        int questionAnswer = question.getAnswer();
        Set<Integer> answersPropositionsSet = new HashSet<Integer>(10);
        answersPropositionsSet.add(plusTenAnswer(questionAnswer));
        answersPropositionsSet.add(minusTenAnswer(questionAnswer));
        answersPropositionsSet.add(closeAnswer(questionAnswer));
        answersPropositionsSet.add(nextSyracuseAnswer(questionAnswer));
        answersPropositionsSet.add(percentAnswer(questionAnswer));
        answersPropositionsSet.add(reverseAnswer(questionAnswer));
        answersPropositionsSet.add(randomAnswer(questionAnswer));
        answersPropositionsSet.add(randomAnswer(questionAnswer));
        answersPropositionsSet.add(randomAnswer(questionAnswer));
        //On appel answersPropositionsSet.remove(Integer.valueOf(questionAnswer)) avant la conversion en Liste pour assurer que le Set des Propositions alternatives ne contiennent pas deja la Reponse
        //Le Integer.valueOf(questionAnswer) sert a ne pas confondre entre Index et Element dans une Liste Int
        answersPropositionsSet.remove(Integer.valueOf(questionAnswer));
        //Conversion en Liste pour Filtrer certaines valeurs
        List<Integer> answersPropositionsFilter = new ArrayList<Integer>(answersPropositionsSet.size());
        Iterator<Integer> itSet = answersPropositionsSet.iterator();
        while(itSet.hasNext()){
            answersPropositionsFilter.add(itSet.next());
        }
        //Retirer les Valeurs Negatives
        answersPropositionsFilter.removeIf(val -> val<0);
        //Retirer les Valeurs trop evidentes
        answersPropositionsFilter.removeIf(val -> {
            switch(question.getOperator()) {
                case Question.ADD:
                case Question.MUL:
                    return val < question.getValueB();
                case Question.SUB:
                case Question.DIV:
                    return val > question.getValueA();
            }
            return false;
        });
        //Melange avant selection
        Collections.shuffle(answersPropositionsFilter);
        //Selection de maximum 3 Valeurs Alternatives
        for(int i=0;i<Math.min(3,answersPropositionsFilter.size());i++){
            this.answersPropositions.add(answersPropositionsFilter.get(i));
        }
        //Remplissage si manque Elements
        int emergencyAnswerFiller = Math.max(questionAnswer-10,0);
        while(this.answersPropositions.size()<3){
            if(!this.answersPropositions.contains(Integer.valueOf(emergencyAnswerFiller))){
                this.answersPropositions.add(Integer.valueOf(emergencyAnswerFiller));
            }
            emergencyAnswerFiller += 2;
        }
        //Enfin ajout de la Reponse et Tri
        this.answersPropositions.add(questionAnswer);
        Collections.sort(this.answersPropositions, new Comparator<Integer>() {
            @Override
            public int compare(Integer a, Integer b) {
                if(a>b){
                    return 1;
                }else if(a<b){
                    return -1;
                }
                return 0;
            }
        });
    }

    public List<Integer> getAnswersPropositions() {
        return answersPropositions;
    }

    //Methode plusTenAnswer : Ajoute 10 a la Reponse
    public int plusTenAnswer(int questionAnswer){
        int answerPlusTen = questionAnswer + 10;
        return answerPlusTen;
    }

    //Methode minusTenAnswer : Ajoute 10 a la Reponse
    public int minusTenAnswer(int questionAnswer){
        int answerMinusTen = questionAnswer - 10;
        return answerMinusTen;
    }

    //Methode closeAnswer : Modifie legerement la Reponse
    public int closeAnswer(int questionAnswer){
        double offsetRolledProba = random.nextDouble();
        int offset;
        if(offsetRolledProba<0.20){
            offset = 1;
        }else if(offsetRolledProba<0.40){
            offset = 2;
        }else if(offsetRolledProba<0.60){
            offset = 3;
        }else if(offsetRolledProba<0.80){
            offset = 4;
        }else{
            offset = 5;
        }
        int answerClose = questionAnswer;
        switch (random.nextInt(2)){
            case 0:
                answerClose += offset;
                break;
            case 1:
                answerClose -= offset;
                break;
        }
        return answerClose;
    }

    //Methode nextSyracuseAnswer : Donne le prochain nombre de la suite de Syracuse de Reponse
    public int nextSyracuseAnswer(int questionAnswer){
        if(questionAnswer<1){
            return -1;
        }
        int answerNextSyracuse;
        if((questionAnswer%2)==0){
            answerNextSyracuse = questionAnswer / 2;
        }else{
            answerNextSyracuse = (questionAnswer * 3) + 1;
        }
        return answerNextSyracuse;
    }

    //Methode percentAnswer : Donne un Pourcentage de Valeur de la Reponse
    public int percentAnswer(int questionAnswer) {
        double percent = random.nextDouble();
        int answerPercent = (int) (questionAnswer * percent);
        return answerPercent;
    }

    //Methode reverseAnswer : Inverse les Chiffres de la Reponse
    public int reverseAnswer(int questionAnswer){
        String answerString = String.valueOf(questionAnswer);
        String reverseAnswerString = "";
        for(int i=0;i<answerString.length();i++){
            reverseAnswerString = answerString.charAt(i) + reverseAnswerString;
        }
        int answerReverse = Math.min(Integer.parseInt(reverseAnswerString),Integer.MAX_VALUE);
        return answerReverse;
    }

    //Methode randomAnswer : Reponse Aleatoire
    public int randomAnswer(int questionAnswer){
        int limit = (questionAnswer+1)*2;
        int answerRandom = random.nextInt(limit);
        return answerRandom;
    }

    /*
    //Methode binaryComplementAnswer : Complement Binaire de la Reponse
    //answerBinary[Index] est la valeur du Bit 2 puissance Index
    public int binaryComplementAnswer(int questionAnswer){
        byte[] answerBinary = this.binaryOfInt(questionAnswer);
        byte[] answerComplementBinary = this.binaryComplementOfBinary(answerBinary);
        int countOneBinary = this.binaryCountOne(answerBinary);
        int countZeroComplement = 0;
        int currentBitValue = 1;
        int answerComplement = 0;
        for(int i=0;i<answerComplementBinary.length;i++){
            if(answerComplementBinary[i]==1){
                answerComplement = answerComplement + currentBitValue;
            }else{
                countZeroComplement++;
            }
            currentBitValue *= 2;
            //Autant de 1 dans answerBinary que de 0 dans son complement
            //Si on a deja verifie autant de 0 dans le Complement que de 1 Original, alors on a vu tout les 1 significatifs de Original
            //Pour garder les Valeurs assez proches, on reste sur la Valeur actuelle answerComplement
            if(countOneBinary==countZeroComplement){
                break;
            }
        }
        return answerComplement;
    }

    //Methode binaryOfInt : La representation Bits de Int value
    //valueBinary[Index] est la valeur du Bit 2 puissance Index
    public byte[] binaryOfInt(int value){
        byte[] valueBinary = new byte[32];
        int currentBitValue = 1;
        for(int i=0;i<valueBinary.length;i++){
            if((value & currentBitValue)==currentBitValue){
                valueBinary[i] = 1;
            }else{
                valueBinary[i] = 0;
            }
            currentBitValue *= 2;
        }
        return valueBinary;
    }

    //Methode binaryComplementOfBinary : Inverse les Bits de representation Bits valueBinary
    public byte[] binaryComplementOfBinary(byte[] valueBinary){
        byte[] complementBinary = new byte[32];
        for(int i=0;i<complementBinary.length;i++){
            if(valueBinary[i]==0){
                complementBinary[i] = 1;
            }else{
                complementBinary[i] = 0;
            }
        }
        return complementBinary;
    }

    //Methode binaryCountZero : Le Nombre Bit 0 dans La representation Bits de valueBinary
    public int binaryCountZero(byte[] valueBinary){
        int countZero = 0;
        for(int i=0;i<valueBinary.length;i++){
            if(valueBinary[i]==0){
                countZero++;
            }
        }
        return countZero;
    }

    //Methode binaryCountOne : Le Nombre Bit 1 dans La representation Bits de valueBinary
    public int binaryCountOne(byte[] valueBinary){
        int countOne = 0;
        for(int i=0;i<valueBinary.length;i++){
            if(valueBinary[i]==1){
                countOne++;
            }
        }
        return countOne;
    }
    */
}
