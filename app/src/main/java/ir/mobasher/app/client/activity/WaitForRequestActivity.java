package ir.mobasher.app.client.activity;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;import ir.mobasher.app.client.R;

public class WaitForRequestActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wait_for_reaquest);
        forceRTLIfSupported();

//        ImageView waitImageView = (ImageView) findViewById(R.id.waitImageView);
//        Glide
//                .with(this) // replace with 'this' if it's in activity
//                .load("http://www.google.com/.../image.gif")
//                .asGif()
//                .error(R.drawable.bookmark) // show error drawable if the image is not a gif
//                .into(R.id.waitImageView);

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
}
