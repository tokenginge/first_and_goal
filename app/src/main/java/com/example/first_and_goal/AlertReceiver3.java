package com.example.first_and_goal;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

public class AlertReceiver3 extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {



        NotificationHelper notificationHelper2 = new NotificationHelper(context);
        NotificationCompat.Builder nb3 = notificationHelper2.getChannelNotification3();
        notificationHelper2.getManager().notify(1,nb3.build() );




    }
}
