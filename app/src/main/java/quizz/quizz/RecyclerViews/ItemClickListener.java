package quizz.quizz.RecyclerViews;

import android.view.View;

/**
 * Created by maxim on 02/03/2017.
 * Interface permettant de cliquer sur un item dans une recyclerView
 */

public interface ItemClickListener {
    public void onItemClick(View v, int position);
}
