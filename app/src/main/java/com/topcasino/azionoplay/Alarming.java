package com.topcasino.azionoplay;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class Alarming extends BroadcastReceiver {

    String TAG = "Alarming";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction() != null && context != null) {
            if (intent.getAction().equalsIgnoreCase(Intent.ACTION_BOOT_COMPLETED)) {
                Scheduler.setReminder(context, Alarming.class,
                        Times.hour, Times.minute);
                return;
            }
        }

        //Trigger the notification
        Scheduler.showNotification(context, ScreenActivityGame.class,
                "\uD83D\uDD25\uD83D\uDD25\uD83D\uDD25ПРИЗЫ УЖЕ ТВОИ\uD83C\uDF81\uD83C\uDF81\uD83C\uDF81 ",
                "Налетай на ХАЛЯВУ \uD83C\uDF89\uD83C\uDF89\uD83C\uDF89");

    }
}
