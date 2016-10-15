package brad.tw.mybootservice;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private TelephonyManager tmgr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_PHONE_STATE},
                    123);
        }
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_SMS)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_SMS},
                    123);
        }
        tmgr = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        tmgr.listen(new MyPhoneStateListener(),
                PhoneStateListener.LISTEN_CALL_STATE);


    }

    private class MyPhoneStateListener extends PhoneStateListener {
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            super.onCallStateChanged(state, incomingNumber);
            switch (state){
                case TelephonyManager.CALL_STATE_IDLE:
                    Log.v("brad", "等候中");
                    break;
                case TelephonyManager.CALL_STATE_RINGING:
                    Log.v("brad", "響鈴..." + incomingNumber);
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    Log.v("brad", "接通了");
                    break;

            }
        }




    }

    private void queryPhoneInfo(){
        String lineNumber = tmgr.getLine1Number();
        Log.v("brad", "LineNumber ==> " + lineNumber);
        String imei = tmgr.getDeviceId();
        Log.v("brad", "IMEI ==> " + imei);
        String imsi = tmgr.getSubscriberId();
        Log.v("brad", "IMSI ==> " + imsi);



    }


    public void test1(View v){
        queryPhoneInfo();

    }

}
