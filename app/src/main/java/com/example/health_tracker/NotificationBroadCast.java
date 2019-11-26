package com.example.health_tracker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class NotificationBroadCast extends BroadcastReceiver {

    public NotificationBroadCast() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        Intent serviceIntent = new Intent(context, NotificationService.class);
        context.startService(serviceIntent);
    }


}
