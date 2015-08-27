package location.myapplication;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class LocationService extends Service {

    LocationManager locationManager;

    private static final String LOG_TAG = LocationService.class.getSimpleName();

    static Test mTest;

    public static void setTest(Test test) {
        mTest = test;
    }

    public interface Test {

        void test(String latitude, String longitude);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        Log.d(LOG_TAG, "Служба создана");
        locationManager = LocationManager.getInstance();
        locationManager.init(getApplicationContext());
    }

    @Override
    public void onStart(Intent intent, int startid) {
        Log.d(LOG_TAG, "Служба запущена (onStart)");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(LOG_TAG, "Служба запущена (onStartCommand)");

        locationManager.start();
        locationManager.startUpdates();
        locationManager.setOnLocationListener(new LocationManager.OnLocationReceive() {
            @Override
            public void onReceive(String latitude, String longitude) {
                Log.d("Coords: ", latitude + " " + longitude);
                mTest.test(latitude, longitude);
            }
        });
        return super.onStartCommand(intent, flags, startId);
    }

    public static boolean isRunning(Context ctx) {
        ActivityManager manager = (ActivityManager) ctx.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (LocationService.class.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onDestroy() {
        Log.d(LOG_TAG, "Служба остановлена");
        locationManager.stopUpdates();
        super.onDestroy();
    }
}
