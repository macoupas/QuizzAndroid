package quizz.quizz.metier;

/**
 * Created by macoupas on 14/02/17.
 */
public class Response {
    private String content;
    private boolean isTrue;
    private boolean isSelect;

    public Response(String content, boolean isTrue){
        this.content = content;
        this.isTrue = isTrue;
        isSelect = false;
    }
    public Response(){

    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isTrue() {
        return isTrue;
    }

    public void setTrue(boolean aTrue) {
        isTrue = aTrue;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
}
