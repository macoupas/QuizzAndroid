package quizz.quizz.RecyclerViews;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import quizz.quizz.R;
import quizz.quizz.metier.Response;

/**
 * Created by maxim on 14/03/2017.
 */

public class ResponseViewHolder extends RecyclerView.ViewHolder{
    private TextView text;

    public ResponseViewHolder(View itemView) {
        super(itemView);
        text = (TextView) itemView.findViewById(R.id.text);
    }

    //Nous ajoutons dans la recyclerView le nom de la reponse en question
    public void bind(Response r){
        text.setText(r.getContent());
    }
}