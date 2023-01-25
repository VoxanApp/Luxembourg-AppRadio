package lu.voxhost.LuxoRadio.activity;

import static lu.voxhost.LuxoRadio.utils.Methods.errorDialog;
import static lu.voxhost.LuxoRadio.utils.Methods.runApp;
import static lu.voxhost.LuxoRadio.utils.Prefs.APP_CHECK;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import lu.voxhost.LuxoRadio.R;
import lu.voxhost.LuxoRadio.utils.Methods;
import lu.voxhost.LuxoRadio.utils.Prefs;

public class SplashActivity extends AppCompatActivity {
    View mContentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mContentView = findViewById(R.id.splash);
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        if (Methods.isNetworkAvailable(this)) {
            if (Prefs.getPreference(this,Prefs.SET_PREFERENCES,APP_CHECK,"").isEmpty()){
                runApp(SplashActivity.this);
            }else{
                runApp(SplashActivity.this);
            }
        }else {
            errorDialog(SplashActivity.this,getString(R.string.connection_msg),getString(R.string.check_internet));
        }
    }
}
