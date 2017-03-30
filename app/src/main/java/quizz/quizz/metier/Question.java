package quizz.quizz.metier;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


/**
 * Created by frfauret on 09/02/17.
 */
public class Question {
    private String ask;
    /*
    private String response;
    private String false1;
    private String false2;
    private String false3;
    */

    private List<Response> responseList;

    public Question(String ask, List<Response> responses/*String response*/) {
        this.ask = ask;
        //this.response = response;
        responseList = new ArrayList<>();
        responseList.addAll(responses);
    }
    public Question(){

    }

    public String getAsk() {
        return ask;
    }

    /*
    public String getReponse() {
        return response;
    }

    public String getFalse1() {return false1;}

    public String getFalse2() {return false2;}

    public String getFalse3() {return false3;}

    public void setFalse1(String false1) {
        this.false1 = false1;
    }

    public void setFalse2(String false2) {
        this.false1 = false2;
    }

    public void setFalse3(String false3) {
        this.false1 = false3;
    }

    public void setResponse(String response) {
        this.response = response;
    }
    */

    public void setAsk(String ask) {
        this.ask = ask;
    }

    public List<Response> getResponseList() {
        return responseList;
    }

    public void setResponseList(List<Response> responseList) {
        this.responseList = responseList;
    }
}
