package ir.mobasher.app.client.activity;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import ir.mobasher.app.client.R;

public class CreateFileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_file_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.createFileToolbar);
//        toolbar.setTitleTextColor(0x000000);
        toolbar.setTitle(R.string.title_activity_create_file);
        setSupportActionBar(toolbar);


        forceRTLIfSupported();


    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void forceRTLIfSupported() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }
    }

    public void continueBtnOnClick(View v){

    }
}
