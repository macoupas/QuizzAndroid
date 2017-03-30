package quizz.quizz.activity;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import quizz.quizz.R;
import quizz.quizz.RecyclerViews.DeviceAdapter;
import quizz.quizz.RecyclerViews.ItemClickListener;

/**
 * Created by maxim on 23/03/2017.
 */

public class SearchActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_PERMISSION_CHECK = 1;
    private static final int REQUEST_ENABLE_BT = 1;
    private ArrayAdapter<String> mArrayAdapter;
    public BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    private TextView textDef;
    private RecyclerView recyclerView;
    private List<BluetoothDevice> deviceList = new ArrayList<>();
    private DeviceAdapter deviceAdapter;
    private View oldView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerDevice);
        setListBluetooth();

        deviceAdapter = new DeviceAdapter(deviceList, new ItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Log.d("DeviceName", deviceList.get(position).getName());
                if(oldView != null) {
                    oldView.setBackgroundColor(Color.WHITE);
                }
                v.setBackgroundColor(Color.CYAN);
                oldView = v;
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(deviceAdapter);

        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(mReceiver, filter);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_CODE_PERMISSION_CHECK);
            }

        } else {
            doBluetoothJob();
        }
    }

    // Partie sur le Bluetooth


    // On ajoute les appareils détectés dans une liste

    private void setListBluetooth() {
        mArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        //Une recycler view viendra ici mais je sais pas faire pour le moment
        // ListView list = ((ListView) findViewById(R.id.listView));
        //list.setAdapter(mArrayAdapter);


        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        // If there are paired devices
        if (pairedDevices.size() > 0) {
            // Loop through paired devices
            for (BluetoothDevice device : pairedDevices) {
                // Add the name and address to an array adapter to show in a ListView
                deviceList.add(device);
                mArrayAdapter.add(device.getName());
                mArrayAdapter.notifyDataSetChanged();
            }
        }
        mArrayAdapter.notifyDataSetChanged();
    }


    // ACTIVER MULTIJOUEUR
    public void clickmulti(View sender) {
        doBluetoothJob();
    }

    /**
     * Pour paramétrer les permissions du bluetooth et localisation
     */
    private void doBluetoothJob() {
        if (mBluetoothAdapter == null) {
            Log.i("bluetooth", "bluetooth inexistant");
        }

        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        } else {
            mBluetoothAdapter.startDiscovery();
        }

        enableDiscoverability();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_PERMISSION_CHECK: {
                // If request is cancelled, the result arrays are empty.

                //si oui
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    doBluetoothJob();
                    Log.i("tag", "Permission acceptée");
                    // commencer la connection bluetooth
                } else {
                    // permission denied Disable the
                    // functionality that depends on this permission.
                    Log.i("tag", "Rejet de permission");
                }
                break;
            }
            case 123:
        }
    }

    /**
     * Permet de d'activer la détection du téléphone
     */

    private void enableDiscoverability() {
        Intent discoverableIntent = new
                Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
        startActivity(discoverableIntent);
    }

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            // When discovery finds a device
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Get the BluetoothDevice object from the Intent
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                // Add the name and address to an array adapter to show in a ListView
                mArrayAdapter.add(device.getName() + "\n" + device.getAddress());
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBluetoothAdapter.cancelDiscovery();
        unregisterReceiver(mReceiver);
    }
}
