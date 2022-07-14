package com.example.microbank.Control;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class SyncService extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // Sync function
        AppController appController = new AppController(context);
        appController.getTransactionDAO().updateServer();
        Toast.makeText(context, "Update Central Server!!!!!!!!!", Toast.LENGTH_SHORT).show();
        Log.d("TRIGTRIGTRIG", "TriggerReceived");
    }
}
