package ir.mobasher.app.client.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import ir.mobasher.app.client.app.Config;

public class LauncherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences settingsPref = getSharedPreferences(Config.SETTINGS_SHARED_PREF, MODE_PRIVATE);

        boolean isFirstRun = settingsPref.getBoolean(Config.FISRT_RUN, true);
        if (isFirstRun){
            settingsPref.edit().putBoolean(Config.FISRT_RUN, false).commit();
        }

        boolean isLogin = settingsPref.getBoolean(Config.IS_LOGIN, false);
        if (isLogin == false){
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }else {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }

        finish();
    }
}
