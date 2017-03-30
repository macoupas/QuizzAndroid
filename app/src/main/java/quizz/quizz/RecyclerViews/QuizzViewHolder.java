package quizz.quizz.RecyclerViews;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import quizz.quizz.R;
import quizz.quizz.metier.Quizz;

/**
 * Created by maxim on 01/03/2017.
 */

public class QuizzViewHolder extends RecyclerView.ViewHolder{
    private TextView text;

    public QuizzViewHolder(View view) {
        super(view);
        text = (TextView) itemView.findViewById(R.id.text);
}

    //Nous ajoutons dans la recyclerView le nom du Quizz en question
    public void bind(Quizz q){
        text.setText(q.getName());
    }
}
