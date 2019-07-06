package ir.mobasher.app.client.fragments.lawyerProfile;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ir.mobasher.app.client.R;


public class LawyerResponseFragment extends Fragment {

    public LawyerResponseFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_lawyer_response, container, false);
        return view;
    }
}