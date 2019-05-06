package ir.mobasher.app.client.fragments;

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
import ir.mobasher.app.client.activity.FileRequestsActivity;
import ir.mobasher.app.client.adapter.FilesListAdapter;
import ir.mobasher.app.client.app.AppKey;


public class ViewFileFragment extends Fragment implements AdapterView.OnItemClickListener {

    ListView filesListView;
    ArrayList<HashMap<String, String>> filesArr;
    HashMap<String, String> filesMap;
    FilesListAdapter filesListAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the create_file_step_2 for this fragment
        View v = inflater.inflate(R.layout.fragment_view_file, container, false);

        filesListView = (ListView) v.findViewById(R.id.files_list);
        filesListView.setOnItemClickListener(this);

        initFilesList();

        return v;
    }

    public void initFilesList(){
        filesArr = new ArrayList<HashMap<String, String>>();

         filesListAdapter = new FilesListAdapter(getActivity(), filesArr,
                R.layout.files_list_row, new String[] {
                AppKey.KEY_FILE_NUMBER,
                AppKey.KEY_FILE_TITLE}, new int[] {
                R.id.fileNumberTv,
                R.id.fileNameTv});

        for (int i=0; i<15; i++){
            filesMap = new HashMap<String, String>();

            filesMap.put(AppKey.KEY_FILE_NUMBER, "125849");
            filesMap.put(AppKey.KEY_FILE_TITLE, "مشکل با کارفرما و عدم تمکین به رای دادگاه");
            filesArr.add(filesMap);
        }

        filesListView.setAdapter(filesListAdapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent i = new Intent(getContext(), FileRequestsActivity.class);
        i.putExtra(AppKey.KEY_FILE_TITLE, filesArr.get(position).get(AppKey.KEY_FILE_TITLE));
        i.putExtra(AppKey.KEY_FILE_NUMBER, filesArr.get(position).get(AppKey.KEY_FILE_NUMBER));
        startActivity(i);
    }
}
