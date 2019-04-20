package com.example.first_and_goal;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

public class NotificationHelper2 extends ContextWrapper {
    public static final String channelID = "channelID";
    public static final String channelName = "Channel Name";

    private NotificationManager mManager;

    public NotificationHelper2(Context base){
        super(base);

            createChannel();

    }

    private void createChannel(){
        NotificationChannel channel = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            channel = new NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_HIGH);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            getManager().createNotificationChannel(channel);
        }
    }

    public NotificationManager getManager(){
        if (mManager == null){
            mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return  mManager;
    }

    public NotificationCompat.Builder getChannelNotification(){
        Intent intent = new Intent(this, Running.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, intent, 0);
        return new NotificationCompat.Builder(getApplicationContext(), channelID).setContentText("Well done!")
                .setContentText("Reached 1000 steps!").setSmallIcon(R.drawable.logo).setAutoCancel(true).setContentIntent(pendingIntent).setVibrate(new long[] {1000, 1000});


    }




}
