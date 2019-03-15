package ir.mobasher.app.client.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import ir.mobasher.app.client.R;
import ir.mobasher.app.client.app.Config;

public class RulesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        forceRTLIfSupported();

    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void forceRTLIfSupported()
    {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1){
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }
    }

    public void disagreeOnClick(View v){
        SharedPreferences settingsPref = getSharedPreferences(Config.SETTINGS_SHARED_PREF, MODE_PRIVATE);

        boolean isFirstRun = settingsPref.getBoolean(Config.FIRST_RUN, true);
        settingsPref.edit().putBoolean(Config.FIRST_RUN, true).commit();
        finish();
    }

    public void agreeOnClick(View v){
        startActivity(new Intent(this, HelpActivity.class));
        finish();
    }

    @Override
    public void onBackPressed() {
        SharedPreferences settingsPref = getSharedPreferences(Config.SETTINGS_SHARED_PREF, MODE_PRIVATE);

        boolean isFirstRun = settingsPref.getBoolean(Config.FIRST_RUN, true);
        settingsPref.edit().putBoolean(Config.FIRST_RUN, true).commit();
        super.onBackPressed();
    }
}
