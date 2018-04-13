package com.slotclubparty.tophard;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class WakeUp extends BroadcastReceiver {

    String TAG = "WakeUp";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction() != null && context != null) {
            if (intent.getAction().equalsIgnoreCase(Intent.ACTION_BOOT_COMPLETED)) {
                NotificationScheduler.setReminder(context, WakeUp.class,
                        ConstantTime.hour, ConstantTime.minute);
                return;
            }
        }

        //Trigger the notification
        NotificationScheduler.showNotification(context, Opened.class,
                "Элитные \uD83C\uDF81ПРИЗЫ \uD83C\uDF81", "Время ограниченно! ⏱⏱⏱");

    }
}
