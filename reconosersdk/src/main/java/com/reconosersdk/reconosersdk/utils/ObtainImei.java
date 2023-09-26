package com.reconosersdk.reconosersdk.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.Log;

import static android.content.Context.TELEPHONY_SERVICE;

public class ObtainImei {
    @SuppressLint("HardwareIds")
    public static String getImei(Context c) {
        // Log.e("Pruebas finales", obtenerDireccionMaccapturarIdDispositivo());
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            final String androidId;
            androidId = "" + android.provider.Settings.Secure.getString(c.getContentResolver(),
                    android.provider.Settings.Secure.ANDROID_ID);
            String deviceId = String.valueOf(((long) androidId.hashCode() << 24));
            String validDeviceID = deviceId.replace("-", "");
            Log.e("ID Android Code ", "" + deviceId + "primera " + androidId);
            return validDeviceID;
        } else {
            TelephonyManager telephonyManager = (TelephonyManager) c.getSystemService(TELEPHONY_SERVICE);
            return telephonyManager.getDeviceId();
        }
    }

}
