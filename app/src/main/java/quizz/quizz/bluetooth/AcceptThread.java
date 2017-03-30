package quizz.quizz.bluetooth;

/**
 * Created by frfauret on 16/02/17.
 */

import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;
import java.util.UUID;
import quizz.quizz.activity.SearchActivity;

// Permet d'accepter la connexion coté serveur.

public class AcceptThread extends Thread{

    private final BluetoothServerSocket mmServerSocket;
    private final UUID MY_UUID = UUID.randomUUID();
    private BluetoothSocket socket = null;
    private ConnectedThread connectedThread;

    public AcceptThread(SearchActivity activity) {
        // Use a temporary object that is later assigned to mmServerSocket,
        // because mmServerSocket is final
        BluetoothServerSocket tmp = null;
        try {
            // MY_UUID is the app's UUID string, also used by the client code
            tmp = activity.mBluetoothAdapter.listenUsingRfcommWithServiceRecord("quizz", MY_UUID);
        } catch (IOException e) { }
        mmServerSocket = tmp;
    }

    public void run() {
        ConnectedThread connectedThread = new ConnectedThread(socket);
        // Keep listening until exception occurs or a socket is returned
        while (true) {
            try {
                socket = mmServerSocket.accept();
            } catch (IOException e) {
                break;
            }
            // If a connection was accepted
            if (socket != null) {
                // Do work to manage the connection (in a separate thread)
                manageConnectedSocket(socket);
                try {
                    mmServerSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    public void cancel() {
        try{
            mmServerSocket.close();
        }catch (IOException e){}
    }

    private void manageConnectedSocket(BluetoothSocket socket){
        Log.d("ServerThread", "trying to start connection thread");
        if (connectedThread!=null) connectedThread.cancel();
        ConnectedThread conn = new ConnectedThread(socket);
        conn.start();
        Log.d("ServerThread","Finished work on server thread");
    }
}
