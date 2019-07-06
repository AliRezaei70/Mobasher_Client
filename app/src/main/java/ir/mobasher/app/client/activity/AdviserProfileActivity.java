package ir.mobasher.app.client.activity;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import com.bumptech.glide.Glide;
import com.mikhaellopez.circularimageview.CircularImageView;
import java.util.ArrayList;
import java.util.List;
import ir.mobasher.app.client.R;
import ir.mobasher.app.client.app.AppKey;
import ir.mobasher.app.client.fragments.lawyerProfile.AboutLawyerFragment;
import ir.mobasher.app.client.fragments.lawyerProfile.CommentsFragment;
import ir.mobasher.app.client.fragments.lawyerProfile.LawyerResponseFragment;


public class AdviserProfileActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adviser_profile);

        String lawyerName = getIntent().getStringExtra(AppKey.KEY_LAWYER_NAME);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(lawyerName);
        setSupportActionBar(toolbar);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        forceRTLIfSupported();

        CircularImageView lawyerImage = (CircularImageView) findViewById(R.id.lawyerImage);
        String url = "https://www.gravatar.com/avatar/d713829a7d72f9237e0850caf6f2cb48?s=328&d=identicon&r=PG";
        Glide.with(getBaseContext()).load(url)
                .into(lawyerImage);
        lawyerImage.setColorFilter(ContextCompat.getColor(getBaseContext(), android.R.color.transparent));
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void forceRTLIfSupported() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1){
            toolbar.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
            LinearLayout headerLayout = (LinearLayout) findViewById(R.id.headerLayout);
            headerLayout.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
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

    private void setupViewPager(ViewPager viewPager) {
        AdviserProfileActivity.ViewPagerAdapter adapter = new AdviserProfileActivity.ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new AboutLawyerFragment(), this.getString(R.string.about_lawyer));
        adapter.addFragment(new CommentsFragment(), this.getString(R.string.comments));
        adapter.addFragment(new LawyerResponseFragment(), this.getString(R.string.response));

        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(adapter.getCount()-1);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

}
