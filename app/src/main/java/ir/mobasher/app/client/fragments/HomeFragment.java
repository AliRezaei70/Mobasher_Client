package ir.mobasher.app.client.fragments;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;

import ir.mobasher.app.client.R;
import ir.mobasher.app.client.adapter.HomeNotifListAdapter;
import ir.mobasher.app.client.adapter.HomeViewPagerAdapter;
import ir.mobasher.app.client.app.AppKey;


public class HomeFragment extends Fragment implements AdapterView.OnItemClickListener{

    LinearLayout sliderDotspanel;
    int dotscount;
    ImageView[] dots;
    ViewPager viewPager;
    ListView homeNotifList;
    ArrayList<HashMap<String, String>> notifArr;
    HashMap<String, String> notifMap;
    HomeNotifListAdapter homeNotifListAdapter;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        viewPager = (ViewPager) view.findViewById(R.id.home_viewPager);
        sliderDotspanel = (LinearLayout) view.findViewById(R.id.home_slider_dots);
        homeNotifList = (ListView) view.findViewById(R.id.home_notif_list);
        homeNotifList.setOnItemClickListener(this);
        initViewPager();
        initNotifList();
        forceRTLIfSupported();

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;

//        viewPager.getLayoutParams().height = height/2;
//        viewPager.getLayoutParams().width = width;

        return view;


    }

    public void initNotifList(){
        notifArr = new ArrayList<HashMap<String, String>>();

        homeNotifListAdapter = new HomeNotifListAdapter(getActivity(), notifArr,
                R.layout.home_notif_list_row, new String[] {
                AppKey.KEY_NOTIF_TITLLE,
                AppKey.KEY_NOTIF_TIME,}, new int[] {
                R.id.homeNotifTittleTv,
                R.id.homeNotifTimeTv});

        for (int i=0; i<10; i++){
            notifMap = new HashMap<String, String>();

            notifMap.put(AppKey.KEY_NOTIF_TITLLE, "first notification");
            notifMap.put(AppKey.KEY_NOTIF_TIME, "10:22");
            notifArr.add(notifMap);
        }

        homeNotifList.setAdapter(homeNotifListAdapter);
    }

    public void initViewPager(){
        Integer [] images = {R.mipmap.vakil1,R.mipmap.vakil2,R.mipmap.vakil3};

        HomeViewPagerAdapter helpViewPagerAdapter = new HomeViewPagerAdapter(getContext(),images);

        viewPager.setAdapter(helpViewPagerAdapter);

        dotscount = helpViewPagerAdapter.getCount();
        dots = new ImageView[dotscount];

        for(int i = 0; i < dotscount; i++){

            dots[i] = new ImageView(getContext());
            dots[i].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.nonactive_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(8, 0, 8, 0);

            sliderDotspanel.addView(dots[i], params);

        }

        viewPager.setCurrentItem(0);
        dots[dotscount - 1].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.active_dot));

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                for(int i = 0; i< dotscount; i++){
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.nonactive_dot));
                }

                dots[dotscount - position - 1].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.active_dot));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void forceRTLIfSupported()
    {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1){
            getActivity().getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
