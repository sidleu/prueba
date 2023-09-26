package com.reconosersdk.reconosersdk.checkPermissions;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.reconosersdk.reconosersdk.R;
import com.reconosersdk.reconosersdk.http.api.TelephoneParamethers;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import io.reactivex.annotations.NonNull;
import timber.log.Timber;

public class CheckPermissionsActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 101;

    //To get the telephone parameters
    private TelephoneParamethers telephoneParamethers;

    //TelephonyManager
    private TelephonyManager telephonyManager;

    //Context
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_permissions);

        //Simple singleton
        telephoneParamethers = TelephoneParamethers.getInstance();

        context = getApplicationContext();

        getTelephoneParameters();
    }

    @SuppressLint({"HardwareIds", "MissingPermission"})
    private void getTelephoneParameters() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(CheckPermissionsActivity.this, new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_CODE);
            return;
        }
        if (Build.VERSION.SDK_INT < 29) {
            telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

            if (telephonyManager.getDeviceId() == null || telephonyManager.getDeviceId().isEmpty()) {
                telephoneParamethers.setNumberIMEI(FirebaseMessaging.getInstance().getToken().getResult());
            } else {
                telephoneParamethers.setNumberIMEI(telephonyManager.getDeviceId());
            }
        } else {
            FirebaseMessaging.getInstance().getToken()
                    .addOnCompleteListener(new OnCompleteListener<String>() {
                        @Override
                        public void onComplete(@NonNull Task<String> task) {
                            if (!task.isSuccessful()) {
                                Timber.e(task.getException(), "Fetching FCM registration token failed");
                                return;
                            }

                            // Get new FCM registration token
                            String token = task.getResult();
                            telephoneParamethers.setNumberIMEI(token);
                        }
                    });
        }
        telephoneParamethers.setCurrentIP(getLocalIpAddress());
        finish();
    }

    public static String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (SocketException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.i("Success", "Permission granted.");

                } else {
                    Log.i("Failed", "Permission denied.");
                    finish();
                }
                getTelephoneParameters();
                //setTelephoneValues();
            }
        }
    }



    private void setTelephoneValues() {
        getTelephoneParameters();
    }
}
