package ir.mobasher.app.client.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.HashMap;
import ir.mobasher.app.client.R;
import ir.mobasher.app.client.adapter.LawyerCommentsAdapter;
import ir.mobasher.app.client.app.AppKey;


public class LawyerCommentFragment extends Fragment {

    ListView lawyerCommentsList;
    ArrayList<HashMap<String, String>> data;
    HashMap<String, String> map;
    LawyerCommentsAdapter lawyerCommentsAdapter;
    public LawyerCommentFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lawyer_comment, container, false);

        lawyerCommentsList = (ListView) view.findViewById(R.id.lawyerCommentsList);

        initList();

        return view;
    }

    public void initList(){
        data = new ArrayList<HashMap<String, String>>();

        lawyerCommentsAdapter = new LawyerCommentsAdapter(getContext(), data,
                R.layout.lawyer_comments_list_row, new String[] {
                AppKey.KEY_NAME,
                AppKey.KEY_ADVISER_TYPE,
                AppKey.KEY_PHOTO_URL,
                AppKey.KEY_COMMENT}, new int[] {
                R.id.lawyerName,
                R.id.lawyerAdviserType,
                R.id.lawyerImage,
                R.id.lawyerCommnetTv});

        for (int i=0; i<15; i++){
            map = new HashMap<String, String>();

            map.put(AppKey.KEY_NAME, "علی محمدرضایی");
            map.put(AppKey.KEY_ADVISER_TYPE, "مشاور حقوقی");
            map.put(AppKey.KEY_PHOTO_URL, "");
            map.put(AppKey.KEY_COMMENT, getActivity().getString(R.string.sample_comment));

            data.add(map);
        }

        lawyerCommentsList.setAdapter(lawyerCommentsAdapter);
    }

}
