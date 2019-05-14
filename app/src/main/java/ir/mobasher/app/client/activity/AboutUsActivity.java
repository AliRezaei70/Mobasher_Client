package ir.mobasher.app.client.activity;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.view.View;
import android.widget.TextView;
import java.io.IOException;
import java.io.InputStream;
import ir.mobasher.app.client.R;

public class AboutUsActivity  extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutus);
        Toolbar toolbar = (Toolbar) findViewById(R.id.aboutUsToolbar);
        setSupportActionBar(toolbar);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        forceRTLIfSupported();

        TextView aboutUsTv = (TextView) findViewById(R.id.aboutUsTv);
        InputStream input = null;
        try {
            input = getAssets().open("aboutus.txt");
            int size = input.available();
            byte[] buffer = new byte[size];
            input.read(buffer);
            input.close();
            String text = new String(buffer);
            //rulesTextView.setText(text, true);
            aboutUsTv.setText(text);
            aboutUsTv.setJustificationMode(Layout.JUSTIFICATION_MODE_INTER_WORD);
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();

    }
}
