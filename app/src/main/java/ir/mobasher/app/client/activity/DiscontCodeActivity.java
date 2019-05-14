package ir.mobasher.app.client.activity;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import ir.mobasher.app.client.R;

public class DiscontCodeActivity extends AppCompatActivity {

    EditText discountCodetEt;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discont_code);
        Toolbar toolbar = (Toolbar) findViewById(R.id.discountCodeToolbar);
        setSupportActionBar(toolbar);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        forceRTLIfSupported();

        discountCodetEt = (EditText) findViewById(R.id.discountCodetEt);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void forceRTLIfSupported() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1){
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void registerOnClick(View v){

    }

    public void cancelOnClick(View v) {
        onBackPressed();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();

    }
}