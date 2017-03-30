package quizz.quizz.metier;

/**
 * Created by maxim on 14/03/2017.
 */

public class Player {
    private String name;
    private int points;
    private Response responseSelect;

    public Player(String name){
        points = 0;
        responseSelect = null;
    }

    public String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public Response getResponseSelect() {
        return responseSelect;
    }

    public void setResponseSelect(Response responseSelect) {
        this.responseSelect = responseSelect;
    }

    public boolean goodResponse(){
        if(responseSelect.isTrue()){
            ++points;
            return true;
        }
        return false;
    }
}
