package ir.mobasher.app.client.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.bumptech.glide.Glide;
import java.util.Timer;
import java.util.TimerTask;

import ir.mobasher.app.client.R;
import ir.mobasher.app.client.helper.DisplayInfo;

public class WaitForRequestActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wait_for_reaquest);
        forceRTLIfSupported();

        ImageView waitImageView = (ImageView) findViewById(R.id.waitImageView);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        params.height = DisplayInfo.getInstance(this).getDisplayHeight() / 3;

        waitImageView.setLayoutParams(params);

        String urlGif = "https://cdn.dribbble.com/users/263558/screenshots/1337078/dvsd.gif";
        Uri uri = Uri.parse(urlGif);
        Glide.with(getApplicationContext()).load(uri).into(waitImageView);
        Button cancelRequestBtn = (Button) findViewById(R.id.cancelRequestBtn);

        new Timer().schedule(new TimerTask() {
            public void run() {
                startActivity(new Intent(WaitForRequestActivity.this,
                        ChooseAdviserActivity.class));
                finish();
            }
        }, 5000);


    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void forceRTLIfSupported() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
