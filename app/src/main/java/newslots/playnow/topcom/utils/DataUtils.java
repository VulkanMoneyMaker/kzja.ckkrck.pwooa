package newslots.playnow.topcom.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

public class DataUtils {

    public static boolean isOnline(final Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public static boolean checkSimCard(Context context) {
        TelephonyManager tm = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);  //gets the current TelephonyManager
        if (tm != null) {
            if (tm.getSimState() != TelephonyManager.SIM_STATE_ABSENT){
                //the phone has a sim card
                return true;
            } else {
                //no sim card available
                return false;
            }
        } else {
            return false;
        }

    }

    public static boolean checkAll(Context context) {
        return isOnline(context) && checkSimCard(context);
    }
}
