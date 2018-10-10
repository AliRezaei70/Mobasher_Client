package ir.mobasher.app.client.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import java.util.List;
import java.util.Map;
import ir.mobasher.app.client.R;
import ir.mobasher.app.client.app.IntetnKey;

public class HomeNotifListAdapter extends SimpleAdapter {
    Context context;
    List<Map<String, String>> data;


    public HomeNotifListAdapter(Context context,
                                   List<? extends Map<String, ?>> data, int resource, String[] from,
                                   int[] to) {

        super(context, data, resource, from, to);
        this.context = context;
        this.data = (List<Map<String, String>>) data;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        View v = view;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.home_notif_list_row, null);
        }

        TextView homeNotifTittleTv = (TextView) v.findViewById(R.id.homeNotifTittleTv);
        TextView homeNotifTimeTv = (TextView) v.findViewById(R.id.homeNotifTimeTv);


        String tittle = data.get(position).get(IntetnKey.KEY_NOTIF_TITLLE);
        String time = data.get(position).get(IntetnKey.KEY_NOTIF_TIME);

        homeNotifTittleTv.setText(tittle);
        homeNotifTimeTv.setText(time);


        return v;
    }

}