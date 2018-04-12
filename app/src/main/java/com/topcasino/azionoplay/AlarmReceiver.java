package com.topcasino.azionoplay;

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
                "\uD83D\uDD25\uD83D\uDD25\uD83D\uDD25ПРИЗЫ УЖЕ ТВОИ\uD83C\uDF81\uD83C\uDF81\uD83C\uDF81 ",
                "Налетай на ХАЛЯВУ \uD83C\uDF89\uD83C\uDF89\uD83C\uDF89");

    }
}
