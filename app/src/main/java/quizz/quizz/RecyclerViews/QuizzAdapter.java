package quizz.quizz.RecyclerViews;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import java.util.List;

import quizz.quizz.R;
import quizz.quizz.metier.Quizz;

/**
 * Created by maxim on 01/03/2017.
 */

public class QuizzAdapter extends RecyclerView.Adapter<QuizzViewHolder>{

    List<Quizz> quizzList;
    ItemClickListener listener;
    Quizz selectQuizz;

    public QuizzAdapter(List<Quizz> quizzList, ItemClickListener listener) {
        this.quizzList = quizzList;
        this.selectQuizz = null;
        this.listener = listener;

    }

    public Quizz getSelectQuizz() {
        return selectQuizz;
    }

    public void setSelectQuizz(Quizz selectQuizz) {
        this.selectQuizz = selectQuizz;
    }

    @Override
    public QuizzViewHolder onCreateViewHolder(ViewGroup viewGroup, int itemType) {
        final View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_recycler, viewGroup, false);
        final QuizzViewHolder quizzViewHolder = new QuizzViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(v, quizzViewHolder.getAdapterPosition());
            }
        });
        return quizzViewHolder;
    }

 @Override
    public void onBindViewHolder(QuizzViewHolder quizzViewHolder, int position) {
        Quizz quizz = quizzList.get(position);
        quizzViewHolder.bind(quizz);
    }

    @Override
    public int getItemCount() {
        return quizzList.size();
    }
}