package com.example.first_and_goal;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

public class AlertReceiver2 extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {



        NotificationHelper2 notificationHelper1 = new NotificationHelper2(context);
        NotificationCompat.Builder nb2 = notificationHelper1.getChannelNotification();
        notificationHelper1.getManager().notify(1, nb2.build());




    }
}
