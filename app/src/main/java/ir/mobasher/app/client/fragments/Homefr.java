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
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ir.mobasher.app.client.R;
import ir.mobasher.app.client.adapter.HomeNotifListAdapter;
import ir.mobasher.app.client.adapter.ViewPagerAdapter;
import ir.mobasher.app.client.app.IntetnKey;

public class Homefr extends Fragment implements AdapterView.OnItemClickListener{

    private TextView stickyView;

    private ListView listView;

    private View heroImageView;

    private View stickyViewSpacer;

    private int MAX_ROWS = 20;

    public Homefr() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fr_home2, container, false);


        /* Initialise list view, hero image, and sticky view */

        listView = (ListView) view.findViewById(R.id.listView);

        heroImageView = view.findViewById(R.id.heroImageView);

        stickyView = (TextView) view.findViewById(R.id.stickyView);

        /* Inflate list header layout */

        LayoutInflater in = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View listHeader = in.inflate(R.layout.list_header, null);

        stickyViewSpacer = listHeader.findViewById(R.id.stickyViewPlaceholder);

        /* Add list view header */

        listView.addHeaderView(listHeader);

        /* Handle list View scroll events */

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override

            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override

            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                /* Check if the first item is already reached to top.*/

                if (listView.getFirstVisiblePosition() == 0) {

                    View firstChild = listView.getChildAt(0);

                    int topY = 0;

                    if (firstChild != null) {

                        topY = firstChild.getTop();

                    }

                    int heroTopY = stickyViewSpacer.getTop();

                    stickyView.setY(Math.max(0, heroTopY + topY));

                    /* Set the image to scroll half of the amount that of ListView */

                    heroImageView.setY(topY * 0.5f);

                }

            }

        });

        /* Populate the ListView with sample data */

        List<String> modelList = new ArrayList<>();

        for (int i = 0; i < MAX_ROWS; i++) {

            modelList.add("List item " + i);

        }

        ArrayAdapter adapter = new ArrayAdapter(getActivity(), R.layout.list_row, modelList);

        listView.setAdapter(adapter);

        return view;


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