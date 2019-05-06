package ir.mobasher.app.client.activity;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.io.IOException;

import ir.mobasher.app.client.R;
import ir.mobasher.app.client.adapter.FinancialReportListAdapter;
import ir.mobasher.app.client.api.APIInterface;
import ir.mobasher.app.client.api.clientProfile.GetProfileErrorResponse;
import ir.mobasher.app.client.api.clientProfile.GetProfileSuccessResponse;
import ir.mobasher.app.client.app.AppTags;
import ir.mobasher.app.client.app.Config;
import ir.mobasher.app.client.fragments.FavoriteLawyersFragment;
import ir.mobasher.app.client.fragments.HomeFragment;
import ir.mobasher.app.client.fragments.ViewFileFragment;
import ir.mobasher.app.client.fragments.WalletFragment;
import ir.mobasher.app.client.manager.ProgressBarManager;
import ir.mobasher.app.client.network.RetrofitClientInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ProgressDialog progressDoalog;
    Toolbar toolbar;
    Fragment fragment;
    TextView scoreTv;
    TextView creditTv;
    CircularImageView profileImageView;
    TextView userNameTv;
    TextView phoneNumTv;
    ProgressBarManager progressBarManager;
    View mProgressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(0x000000);
        setSupportActionBar(toolbar);
        forceRTLIfSupported();

        progressBarManager = new ProgressBarManager();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        navigationView.setNavigationItemSelectedListener(this);

        scoreTv = (TextView) headerView.findViewById(R.id.scoreTv);
        creditTv = (TextView) headerView.findViewById(R.id.creditTv);
        userNameTv = (TextView) headerView.findViewById(R.id.usernameTv);
        phoneNumTv = (TextView) headerView.findViewById(R.id.phoneNumTv);
        profileImageView = (CircularImageView) headerView.findViewById(R.id.profileImageView);
        String url = "https://www.gravatar.com/avatar/d713829a7d72f9237e0850caf6f2cb48?s=328&d=identicon&r=PG";
        Glide.with(this).load(url)
                .into(profileImageView);
        profileImageView.setColorFilter(ContextCompat.getColor(this, android.R.color.transparent));


        ImageView menuSettingBtn = (ImageView) headerView.findViewById(R.id.menuSettingBtn);
        menuSettingBtn.setOnClickListener(menuSettingsOnClick);
        ImageView menuRefreshBtn = (ImageView) headerView.findViewById(R.id.menuRefreshBtn);
        menuRefreshBtn.setOnClickListener(menuRefreshOnClick);




        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        toolbar.setTitle(R.string.home);
        fragment = new HomeFragment();
        loadFragment(fragment);

        mProgressView = findViewById(R.id.home_progress);

        SharedPreferences settingsPref = getSharedPreferences(Config.SETTINGS_SHARED_PREF, MODE_PRIVATE);
        String clientId = settingsPref.getString(Config.CLIENT_ID, Config.DEFAULT_STRING_NO_THING_FOUND);

        initProfile(clientId);


    }

    private View.OnClickListener menuSettingsOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Toast.makeText(HomeActivity.this, "settings", Toast.LENGTH_SHORT).show();
            closeDrawer();
        }
    };

    private View.OnClickListener menuRefreshOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Toast.makeText(HomeActivity.this, "refresh", Toast.LENGTH_SHORT).show();
        }
    };

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    toolbar.setTitle(R.string.home);
                    fragment = new HomeFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_view_files:
                    toolbar.setTitle(R.string.view_file);
                    fragment = new ViewFileFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_add_file:
//                    toolbar.setTitle(R.string.add_file);
//                    fragment = new AddFileFragment();
//                    loadFragment(fragment);
                    Intent i = new Intent(HomeActivity.this, CreateFileActivity.class);
                    startActivity(i);
                    return true;
                case R.id.navigation_favorite_lawyers:
                    toolbar.setTitle(R.string.favorite_lawyers);
                    fragment = new FavoriteLawyersFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_wallet:
                    toolbar.setTitle(R.string.wallet);
                    fragment = new WalletFragment();
                    loadFragment(fragment);
                    return true;
            }

            return false;
        }
    };



    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentManager fm = this.getSupportFragmentManager();
        for(int i = 0; i < fm.getBackStackEntryCount(); ++i) {
            fm.popBackStack();
        }

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragments_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();


    }

    public void closeDrawer(){
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void forceRTLIfSupported() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.call_report) {
            startActivity(new Intent(this, CallReportActivity.class));
        } else if (id == R.id.financial_report) {
            startActivity(new Intent(this, FinancialReportActivity.class));

        } else if (id == R.id.add_credit) {
            startActivity(new Intent(this, IncreaseCreditActivity.class));

        } else if (id == R.id.invite) {

        } else if (id == R.id.discount_code) {

        } else if (id == R.id.help_usage) {
            startActivity(new Intent(this, HelpActivity.class));

        } else if (id == R.id.about_us) {

        } else if (id == R.id.exit) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //service call methods
    public void initProfile(String clientId){
        progressBarManager.showProgress((ProgressBar) mProgressView, HomeActivity.this);

        APIInterface service = RetrofitClientInstance.getRetrofitInstance().create(APIInterface.class);
        Call<GetProfileSuccessResponse> responseCall = service.getProfile(clientId);
        responseCall.enqueue(new Callback<GetProfileSuccessResponse>() {
            @Override
            public void onResponse(Call<GetProfileSuccessResponse> call, Response<GetProfileSuccessResponse> response) {
                if (response.isSuccessful()){
                    GetProfileSuccessResponse getProfileResponse = response.body();
                    Log.i(AppTags.GET_PROFILE_RESPONSE, getProfileResponse.getMessage());

                    scoreTv.setText(HomeActivity.this.getString(R.string.score) + " " + 6570);
                    creditTv.setText(HomeActivity.this.getString(R.string.wallet) + " " + 50000);
                    userNameTv.setText(getProfileResponse.getFirstName() + " " + getProfileResponse.getLastName());
                    phoneNumTv.setText(getProfileResponse.getMobileNumber());
                }else {
                    Gson gson = new GsonBuilder().create();
                    GetProfileErrorResponse errorResponse = new GetProfileErrorResponse();
                    try {
                        errorResponse = gson.fromJson(response.errorBody().string(), GetProfileErrorResponse.class);
                        Log.i(AppTags.GET_PROFILE_RESPONSE, errorResponse.getMessage());
                        Toast.makeText(getBaseContext(), errorResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.i(AppTags.GET_PROFILE_RESPONSE, AppTags.UNKNOWN_ERROR);
                    }
                }

                progressBarManager.hideProgress((ProgressBar) mProgressView, HomeActivity.this);
            }

            @Override
            public void onFailure(Call<GetProfileSuccessResponse> call, Throwable t) {
                Log.e(AppTags.GET_PROFILE_RESPONSE, t.getMessage());
                Toast.makeText(getBaseContext(), R.string.connection_error, Toast.LENGTH_SHORT).show();
                progressBarManager.hideProgress((ProgressBar) mProgressView, HomeActivity.this);
            }
        });
    }
}
