package quizz.quizz.metier;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by maxim on 01/03/2017.
 */

public class Quizz{

    /**
     * key est la clé qui servira à identifier le quizz dans la firebase
     */
    private String key;
    /**
     * Liste des questions du quizz
     */
    private List<Question> questions;
    /**
     * Nom du quizz
     */
    private String name;

    /**
     * Contructeur du quizz permettant d'affecter une liste de question ainsi qu'un nom à celui-ci
     * @param questionList
     * @param name
     */
    public Quizz (List<Question> questionList, String name){
        key = null;
        questions = new ArrayList<>();
        questions.addAll(questionList);
        this.name = name;
    }

    /**
     * Constructeur vide pour la firebase
     */
    public Quizz(){

    }

    /**
     * Permet de récupérer la liste des questions du quizz
     * @return liste de questions
     */
    public List<Question> getQuestions() {
        return questions;
    }

    /**
     * Permet d'affecter une liste de questions au quizz
     * @param questions
     */
    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
