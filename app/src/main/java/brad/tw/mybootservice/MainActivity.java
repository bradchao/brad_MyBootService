package brad.tw.mybootservice;

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
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
    private AccountManager amgr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_PHONE_STATE,
                            Manifest.permission.READ_SMS,
                            Manifest.permission.READ_CONTACTS,
                            Manifest.permission.GET_ACCOUNTS},
                    123);
        }

        amgr = (AccountManager) getSystemService(ACCOUNT_SERVICE);

        tmgr = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        tmgr.listen(new MyPhoneStateListener(),
                PhoneStateListener.LISTEN_CALL_STATE);


    }

    private class MyPhoneStateListener extends PhoneStateListener {
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            super.onCallStateChanged(state, incomingNumber);
            switch (state) {
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

    private void queryPhoneInfo() {
        String lineNumber = tmgr.getLine1Number();
        Log.v("brad", "LineNumber ==> " + lineNumber);
        String imei = tmgr.getDeviceId();
        Log.v("brad", "IMEI ==> " + imei);
        String imsi = tmgr.getSubscriberId();
        Log.v("brad", "IMSI ==> " + imsi);


    }


    public void test1(View v) {
        queryPhoneInfo();

    }

    public void test2(View v) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.GET_ACCOUNTS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Account[] accs = amgr.getAccounts();
        for (Account acc : accs){
            String name = acc.name;
            String type = acc.type;
            Log.v("brad", name + ":" + type);
        }

    }
    public void test3(View v){
        ContentResolver r = getContentResolver();
        // SQLite => query
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String name = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME;
        String tel = ContactsContract.CommonDataKinds.Phone.NUMBER;

        Cursor c = r.query(uri,new String[]{name,tel},null,null,null);
        while (c.moveToNext()){
            String cname = c.getString(0);
            String ctel = c.getString(1);
            Log.v("brad", cname + ":" + ctel);
        }
    }

}
