package ir.mobasher.app.client.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import ir.mobasher.app.client.R;


public class ViewFileFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the create_file_step_2 for this fragment
        View v = inflater.inflate(R.layout.fragment_view_file, container, false);


        return v;
    }


}
