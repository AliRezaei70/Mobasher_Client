package ir.mobasher.app.client.fragments.home;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ir.mobasher.app.client.R;



public class AddFileFragment extends Fragment {


    public AddFileFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the create_file_step_2 for this fragment
        return inflater.inflate(R.layout.fragment_add_file, container, false);
    }
}
