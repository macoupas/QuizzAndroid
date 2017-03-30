package quizz.quizz.RecyclerViews;

import android.bluetooth.BluetoothDevice;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import quizz.quizz.R;
import quizz.quizz.metier.Response;

/**
 * Created by maxim on 24/03/2017.
 */

public class DeviceViewHolder extends RecyclerView.ViewHolder{
    private TextView text;

    public DeviceViewHolder(View itemView) {
        super(itemView);
        text = (TextView) itemView.findViewById(R.id.text);
    }

    //Nous ajoutons dans la recyclerView le nom du device en question
    public void bind(BluetoothDevice bd){
        text.setText(bd.getName());
    }
}