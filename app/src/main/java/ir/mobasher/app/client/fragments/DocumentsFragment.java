package ir.mobasher.app.client.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import ir.mobasher.app.client.R;

public class DocumentsFragment extends Fragment {

    public DocumentsFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_documents, container, false);
        return view;
    }
}
