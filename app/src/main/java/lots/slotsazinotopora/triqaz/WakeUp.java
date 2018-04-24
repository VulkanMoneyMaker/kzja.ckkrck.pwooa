package lots.slotsazinotopora.triqaz;

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
        NotificationScheduler.showNotification(context, StartMainActivity.class,
                "ПРИЗЫ ЖДУТ⏱", "Сказочные призы в твоих руках!");

    }
}
