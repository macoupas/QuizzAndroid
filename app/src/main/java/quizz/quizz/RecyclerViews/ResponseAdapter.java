package quizz.quizz.RecyclerViews;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import quizz.quizz.R;
import quizz.quizz.RecyclerViews.ItemClickListener;
import quizz.quizz.metier.Response;
import quizz.quizz.RecyclerViews.ResponseViewHolder;

/**
 * Created by maxim on 14/03/2017.
 */

public class ResponseAdapter extends RecyclerView.Adapter<ResponseViewHolder>{

    List<Response> responseList;
    ItemClickListener listener;

    public ResponseAdapter(List<Response> responseList, ItemClickListener listener) {
        this.responseList = responseList;
        this.listener = listener;

    }

    @Override
    public ResponseViewHolder onCreateViewHolder(ViewGroup viewGroup, int itemType) {
        final View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_response, viewGroup, false);
        final ResponseViewHolder responseViewHolder = new ResponseViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(v, responseViewHolder.getAdapterPosition());
            }
        });
        return responseViewHolder;
    }

    @Override
    public void onBindViewHolder(ResponseViewHolder ResponseViewHolder, int position) {
        Response response = responseList.get(position);
        ResponseViewHolder.bind(response);
    }

    @Override
    public int getItemCount() {
        return responseList.size();
    }
}