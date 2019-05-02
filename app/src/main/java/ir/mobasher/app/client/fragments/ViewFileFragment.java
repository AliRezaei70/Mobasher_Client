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


public class ViewFileFragment extends Fragment implements View.OnClickListener {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the create_file_step_2 for this fragment
        View v = inflater.inflate(R.layout.fragment_view_file, container, false);

        LinearLayout viewFileLayout = (LinearLayout) v.findViewById(R.id.viewFileLayout);
        viewFileLayout.setOnClickListener(this);

        LinearLayout phoneConsultantLayout = (LinearLayout) v.findViewById(R.id.phoneConsultantLayout);
        phoneConsultantLayout.setOnClickListener(this);

        LinearLayout recentConsultantLayout = (LinearLayout) v.findViewById(R.id.recentConsultantLayout);
        recentConsultantLayout.setOnClickListener(this);

        LinearLayout faivoritesConsultantLayout = (LinearLayout) v.findViewById(R.id.faivoritesConsultantLayout);
        faivoritesConsultantLayout.setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.viewFileLayout:
                Toast.makeText(getContext(), getText(R.string.view_file), Toast.LENGTH_SHORT).show();
                return;
            case R.id.phoneConsultantLayout:
                Toast.makeText(getContext(), getText(R.string.phone_consultant), Toast.LENGTH_SHORT).show();
                return;
            case R.id.recentConsultantLayout:
                Toast.makeText(getContext(), getText(R.string.recent_consultant), Toast.LENGTH_SHORT).show();
                return;
            case R.id.faivoritesConsultantLayout:
                Toast.makeText(getContext(), getText(R.string.faivorites_consultant), Toast.LENGTH_SHORT).show();
                return;

        }
    }
}
