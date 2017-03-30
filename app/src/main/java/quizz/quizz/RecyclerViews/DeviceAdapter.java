package quizz.quizz.RecyclerViews;

import android.bluetooth.BluetoothDevice;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import quizz.quizz.R;

/**
 * Created by maxim on 24/03/2017.
 */

public class DeviceAdapter extends RecyclerView.Adapter<DeviceViewHolder>{

    List<BluetoothDevice> deviceList;
    ItemClickListener listener;

    public DeviceAdapter(List<BluetoothDevice> deviceList, ItemClickListener listener) {
        this.deviceList = deviceList;
        this.listener = listener;

    }

    @Override
    public DeviceViewHolder onCreateViewHolder(ViewGroup viewGroup, int itemType) {
        final View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_devices, viewGroup, false);
        final DeviceViewHolder deviceViewHolder = new DeviceViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(v, deviceViewHolder.getAdapterPosition());
            }
        });
        return deviceViewHolder;
    }

    @Override
    public void onBindViewHolder(DeviceViewHolder deviceViewHolder, int position) {
        BluetoothDevice device = deviceList.get(position);
        deviceViewHolder.bind(device);
    }

    @Override
    public int getItemCount() {
        return deviceList.size();
    }
}