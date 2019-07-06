package ir.mobasher.app.client.fragments.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.HashMap;
import ir.mobasher.app.client.R;
import ir.mobasher.app.client.activity.AdviserProfileActivity;
import ir.mobasher.app.client.adapter.FavoriteLawyerAdapter;
import ir.mobasher.app.client.app.AppKey;

public class FavoriteLawyersFragment extends Fragment implements AdapterView.OnItemClickListener {

    ListView favoritesLawyerList;
    ArrayList<HashMap<String, String>> data;
    HashMap<String, String> map;
    FavoriteLawyerAdapter favoritesLawyerAdapter;

    public FavoriteLawyersFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_favorite_lawyers, container, false);

        favoritesLawyerList = (ListView) v.findViewById(R.id.favoriteLawyerList);

        initList();

        return v;
    }

    public void initList(){
        data = new ArrayList<HashMap<String, String>>();

        favoritesLawyerAdapter = new FavoriteLawyerAdapter(getContext(), data,
                R.layout.choose_adviser_list_row, new String[] {
                AppKey.KEY_SCORE,
                AppKey.KEY_NAME,
                AppKey.KEY_ADVISER_TYPE,
                AppKey.KEY_PHOTO_URL,
                AppKey.KEY_LEVEL,
                AppKey.KEY_IS_ONLINE}, new int[] {
                R.id.lawyerScore,
                R.id.lawyerName,
                R.id.lawyerAdviserType,
                R.id.lawyerImage,
                R.id.lawyerLevel,
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
            map.put(AppKey.KEY_SELECT_OR_DESELECT, AppKey.KEY_SELECT);

            data.add(map);
        }

        favoritesLawyerList.setAdapter(favoritesLawyerAdapter);
        favoritesLawyerList.setOnItemClickListener(this);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        startActivity(new Intent(getContext(), AdviserProfileActivity.class).putExtra(AppKey.KEY_LAWYER_NAME, "علی محمدرضایی"));
    }
}
