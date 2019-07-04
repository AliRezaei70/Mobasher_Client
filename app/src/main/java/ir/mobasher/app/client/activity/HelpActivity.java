package ir.mobasher.app.client.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import ir.mobasher.app.client.R;
import ir.mobasher.app.client.adapter.HelpViewPagerAdapter;
import ir.mobasher.app.client.app.Config;

public class HelpActivity extends AppCompatActivity {

    ViewPager viewPager;
    LinearLayout sliderDotspanel;
    private int dotscount;
    private ImageView[] dots;
    private boolean isLastPage = false;
    Button nextBtn;
    Button signInOrSignUpBtn;
    SharedPreferences settingsPref;
    boolean isLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.help_toolbar);
//        setSupportActionBar(toolbar);
        forceRTLIfSupported();

        nextBtn = (Button) findViewById(R.id.nextBtn);
        signInOrSignUpBtn = (Button) findViewById(R.id.signInOrSignUpBtn);

        settingsPref = getSharedPreferences(Config.SETTINGS_SHARED_PREF, MODE_PRIVATE);
        isLogin = settingsPref.getBoolean(Config.IS_LOGIN, false);
        if (isLogin == true) {
            signInOrSignUpBtn.setVisibility(View.INVISIBLE);
        }

        viewPager = (ViewPager) findViewById(R.id.viewPager);

        sliderDotspanel = (LinearLayout) findViewById(R.id.SliderDots);

        Integer[] images = {R.drawable.slide1, R.drawable.slide2, R.drawable.slide3};
        String[] title = {this.getString(R.string.help_item_title_1), this.getString(R.string.help_item_title_2), this.getString(R.string.help_item_title_3)};
        String[] description = {this.getString(R.string.help_item_desc_1), this.getString(R.string.help_item_desc_2), this.getString(R.string.help_item_desc_3)};

        HelpViewPagerAdapter HelpViewPagerAdapter = new HelpViewPagerAdapter(this, this, images, title, description);

        viewPager.setAdapter(HelpViewPagerAdapter);

        dotscount = HelpViewPagerAdapter.getCount();
        dots = new ImageView[dotscount];

        for (int i = 0; i < dotscount; i++) {

            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.nonactive_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(8, 0, 8, 0);

            sliderDotspanel.addView(dots[i], params);

        }

        viewPager.setCurrentItem(0);
        dots[dotscount - 1].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                for (int i = 0; i < dotscount; i++) {
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.nonactive_dot));
                }

                dots[dotscount - position - 1].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));

                if (viewPager.getCurrentItem() == dotscount - 1) {
                    signInOrSignUpBtn.setText(R.string.start);
                    isLastPage = true;
                    nextBtn.setVisibility(View.GONE);
                } else {
                    nextBtn.setVisibility(View.VISIBLE);
                    nextBtn.setText(R.string.next);
                    isLastPage = false;
                    signInOrSignUpBtn.setVisibility(View.VISIBLE);
                    signInOrSignUpBtn.setText(R.string.signin_or_signup);

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void forceRTLIfSupported() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }
    }

    public void nextOnClick(View view) {

        viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);

//        if (isLastPage) {
//
//            if (isLogin == true) {
//                finish();
//            } else {
//                startActivity(new Intent(this, RulesActivity.class));
//                finish();
//            }
//        } else {
//            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
//        }

    }

    public void signInOrSignUpOnClick(View view) {
        if (isLogin == true) {
            finish();
        }else {
            //startActivity(new Intent(this, LoginActivity.class));
            startActivity(new Intent(this, HomeActivity.class));
            finish();
        }

    }

}
