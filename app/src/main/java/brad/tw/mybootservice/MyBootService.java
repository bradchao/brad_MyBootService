package brad.tw.mybootservice;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.util.Log;

public class MyBootService extends Service {

    public MyBootService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return  null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.v("brad", "MyBootService:onCreate");




    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.v("brad", "MyBootService:onStart");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.v("brad", "MyBootService:onDestroy");
    }
}
