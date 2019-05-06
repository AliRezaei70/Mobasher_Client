package ir.mobasher.app.client.fragments;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import ir.mobasher.app.client.R;
import ir.mobasher.app.client.adapter.HomeNotifListAdapter;
import ir.mobasher.app.client.adapter.HomeViewPagerAdapter;
import ir.mobasher.app.client.app.AppKey;

public class Homefr extends Fragment implements AdapterView.OnItemClickListener{

    private TextView stickyView;
    private View stickyViewSpacer;
    private int MAX_ROWS = 20;
    LinearLayout sliderDotspanel;
    int dotscount;
    ImageView[] dots;
    ViewPager viewPager;
    ListView homeNotifList;
    ArrayList<HashMap<String, String>> notifArr;
    HashMap<String, String> notifMap;
    HomeNotifListAdapter homeNotifListAdapter;

    public Homefr() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fr_home2, container, false);


        /* Initialise list view, hero image, and sticky view */

        homeNotifList = (ListView) view.findViewById(R.id.home_notif_list);
        sliderDotspanel = (LinearLayout) view.findViewById(R.id.home_slider_dots);
        viewPager = view.findViewById(R.id.home_viewPager);
        initViewPager();
        initNotifList();
        stickyView = (TextView) view.findViewById(R.id.stickyView);

        /* Inflate list header create_file_step_2 */

        LayoutInflater in = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View listHeader = in.inflate(R.layout.list_header, null);

        stickyViewSpacer = listHeader.findViewById(R.id.stickyViewPlaceholder);

        /* Add list view header */

        homeNotifList.addHeaderView(listHeader);

        /* Handle list View scroll events */

        homeNotifList.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override

            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override

            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                /* Check if the first item is already reached to top.*/

                if (homeNotifList.getFirstVisiblePosition() == 0) {

                    View firstChild = homeNotifList.getChildAt(0);

                    int topY = 0;

                    if (firstChild != null) {

                        topY = firstChild.getTop();

                    }

                    int heroTopY = stickyViewSpacer.getTop();

                    stickyView.setY(Math.max(0, heroTopY + topY));

                    /* Set the image to scroll half of the amount that of ListView */

                    viewPager.setY(topY * 0.5f);

                }

            }

        });


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
        Integer [] images = {R.drawable.slide1,R.drawable.slide2,R.drawable.slide3};

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

        dots[0].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.active_dot));

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                for(int i = 0; i< dotscount; i++){
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.nonactive_dot));
                }

                dots[position].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.active_dot));
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