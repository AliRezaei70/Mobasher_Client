package ir.mobasher.app.client.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;

import ir.mobasher.app.client.R;
import ir.mobasher.app.client.app.Config;
import ir.mobasher.app.client.libraries.TextViewEx;

public class RulesActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        forceRTLIfSupported();

        TextView rulesTextView = (TextView) findViewById(R.id.rulesTextTv);
        InputStream input = null;
        try {
            input = getAssets().open("rules.txt");
            int size = input.available();
            byte[] buffer = new byte[size];
            input.read(buffer);
            input.close();
            String text = new String(buffer);
            //rulesTextView.setText(text, true);
            rulesTextView.setText(text);
            rulesTextView.setJustificationMode(Layout.JUSTIFICATION_MODE_INTER_WORD);
        } catch (IOException e) {
            e.printStackTrace();
        }


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
        setFirstRun(false);
    }

    public void setFirstRun(boolean firstRun){
        SharedPreferences settingsPref = getSharedPreferences(Config.SETTINGS_SHARED_PREF, MODE_PRIVATE);

        boolean isFirstRun = settingsPref.getBoolean(Config.FIRST_RUN, firstRun);
        settingsPref.edit().putBoolean(Config.FIRST_RUN, firstRun).commit();

    }

    @Override
    public void onBackPressed() {
        setFirstRun(true);
        super.onBackPressed();
    }

    @Override
    protected void onRestart() {
        setFirstRun(true);
        super.onRestart();

    }

    @Override
    protected void onDestroy() {
        setFirstRun(true);
        super.onDestroy();

    }

    @Override
    protected void onResume() {
        setFirstRun(true);
        super.onResume();

    }
}
