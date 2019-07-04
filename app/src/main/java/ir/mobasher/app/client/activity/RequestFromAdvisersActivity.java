package ir.mobasher.app.client.activity;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.HashMap;
import ir.mobasher.app.client.R;
import ir.mobasher.app.client.adapter.ChooseAdviserAdapter;
import ir.mobasher.app.client.adapter.RequestFromPreOrFavAdapter;
import ir.mobasher.app.client.app.AppKey;

public class RequestFromAdvisersActivity extends AppCompatActivity {

    ListView chooseFromAdviserList;
    ArrayList<HashMap<String, String>> data;
    HashMap<String, String> map;
    RequestFromPreOrFavAdapter adviserListAdapter;
    String favoritesOrPrevious;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_from_advisers);

        Toolbar toolbar = (Toolbar) findViewById(R.id.ReqFromAdviserToolbar);


        favoritesOrPrevious = getIntent().getStringExtra(AppKey.KEY_REQ_FROM_PREVIOUS_OR_FAVORITES);

        if (favoritesOrPrevious.equals(AppKey.KEY_REQ_FROM_PREVIOUS)) {
            toolbar.setTitle(this.getString(R.string.req_from_previous_advisers));
        }else {
            toolbar.setTitle(this.getString(R.string.req_from_favorites_advisers));
        }
        setSupportActionBar(toolbar);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        forceRTLIfSupported();

        chooseFromAdviserList = (ListView) findViewById(R.id.chooseFromAdviserList);

        initList();
    }


    public void initList(){
        data = new ArrayList<HashMap<String, String>>();

        adviserListAdapter = new RequestFromPreOrFavAdapter(this, data,
                R.layout.choose_adviser_list_row, new String[] {
                AppKey.KEY_SCORE,
                AppKey.KEY_RATE,
                AppKey.KEY_NAME,
                AppKey.KEY_ADVISER_TYPE,
                AppKey.KEY_PHOTO_URL,
                AppKey.KEY_LEVEL,
                AppKey.KEY_PRICE,
                AppKey.KEY_STATUS,
                AppKey.KEY_IS_ONLINE}, new int[] {
                R.id.lawyerScore,
                R.id.lawyerRatingBar,
                R.id.lawyerName,
                R.id.lawyerAdviserType,
                R.id.lawyerImage,
                R.id.lawyerLevel,
                R.id.lawyerPrice,
                R.id.requestBtn,
                R.id.isOnlineImageView});

        for (int i=0; i<15; i++){
            map = new HashMap<String, String>();

            map.put(AppKey.KEY_SCORE, "امتیازکل: 70");
            map.put(AppKey.KEY_RATE, "4.5");
            map.put(AppKey.KEY_NAME, "علی محمدرضایی");
            map.put(AppKey.KEY_ADVISER_TYPE, "مشاور حقوقی");
            map.put(AppKey.KEY_PHOTO_URL, "");
            map.put(AppKey.KEY_LEVEL, "سطح یک");
            map.put(AppKey.KEY_PRICE, "7000 تومان");
            map.put(AppKey.KEY_STATUS, "ارتباط");
            map.put(AppKey.KEY_IS_ONLINE, "");
            if (favoritesOrPrevious.equals(AppKey.KEY_REQ_FROM_PREVIOUS)) {
                map.put(AppKey.KEY_SELECT_OR_DESELECT, AppKey.KEY_DESELECT);
            }else {
                map.put(AppKey.KEY_SELECT_OR_DESELECT, AppKey.KEY_SELECT);
            }


            data.add(map);
        }

        chooseFromAdviserList.setAdapter(adviserListAdapter);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void forceRTLIfSupported()
    {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1){
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();

    }

}
