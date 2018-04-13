package com.slotclubparty.tophard;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmReceiver extends BroadcastReceiver {

    String TAG = "AlarmReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction() != null && context != null) {
            if (intent.getAction().equalsIgnoreCase(Intent.ACTION_BOOT_COMPLETED)) {
                NotificationScheduler.setReminder(context, AlarmReceiver.class,
                        ConstantTime.hour, ConstantTime.minute);
                return;
            }
        }

        //Trigger the notification
        NotificationScheduler.showNotification(context, SplashScreenActivityGame.class,
                "Твой \uD83C\uDFB0ДЖЕКПОТ\uD83C\uDFB0 уже здесь!", "\uD83C\uDF0BТы уже ПОБЕДИЛ, забирай свое \uD83C\uDF0B");

    }
}
