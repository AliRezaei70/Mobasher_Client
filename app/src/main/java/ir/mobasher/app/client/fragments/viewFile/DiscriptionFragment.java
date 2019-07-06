package ir.mobasher.app.client.fragments.viewFile;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;

import ir.mobasher.app.client.R;


public class DiscriptionFragment extends Fragment {

    public DiscriptionFragment() {
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_discription, container, false);

        TextView filePreviewDescTv = (TextView) view.findViewById(R.id.filePreviewDescTv);
        InputStream input = null;
        try {
            input = getActivity().getAssets().open("aboutus.txt");
            int size = input.available();
            byte[] buffer = new byte[size];
            input.read(buffer);
            input.close();
            String text = new String(buffer);
            //rulesTextView.setText(text, true);
            filePreviewDescTv.setText(text);
            filePreviewDescTv.setJustificationMode(Layout.JUSTIFICATION_MODE_INTER_WORD);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return view;
    }
}
