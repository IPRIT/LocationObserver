package location.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    // UI Widgets.
    protected Button mStartUpdatesButton;
    protected Button mStopUpdatesButton;
    protected TextView mLastUpdateTimeTextView;
    protected TextView mLatitudeTextView;
    protected TextView mLongitudeTextView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Locate the UI widgets.
        mStartUpdatesButton = (Button) findViewById(R.id.start_updates_button);
        mStopUpdatesButton = (Button) findViewById(R.id.stop_updates_button);
        mLatitudeTextView = (TextView) findViewById(R.id.latitude_text);
        mLongitudeTextView = (TextView) findViewById(R.id.longitude_text);

        LocationService.setTest(new LocationService.Test() {
            @Override
            public void test(String latitude, String longitude) {
                mLatitudeTextView.setText(latitude);
                mLongitudeTextView.setText(longitude);
            }
        });

        this.startService(new Intent(MainActivity.this, LocationService.class));
    }

    public void startUpdatesButtonHandler(View view) {
        //locationService.
        if (!LocationService.isRunning(this)) {
            this.startService(new Intent(MainActivity.this, LocationService.class));
            Toast.makeText(this, String.valueOf(LocationService.isRunning(this)), Toast.LENGTH_SHORT).show();
        }
    }

    public void stopUpdatesButtonHandler(View view) {
        //locationService.stopUpdates();
        this.stopService(new Intent(MainActivity.this, LocationService.class));
        Toast.makeText(this, String.valueOf(LocationService.isRunning(this)), Toast.LENGTH_SHORT).show();
    }
}
