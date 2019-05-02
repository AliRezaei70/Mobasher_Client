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

public class FilesListAdapter extends SimpleAdapter {
    Context context;
    List<Map<String, String>> data;


    public FilesListAdapter(Context context,
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
            v = vi.inflate(R.layout.files_list_row, null);
        }

        TextView fileNumberTv = (TextView) v.findViewById(R.id.fileNumberTv);
        TextView fileNameTv = (TextView) v.findViewById(R.id.fileNameTv);


        String number = data.get(position).get(IntetnKey.KEY_FILE_NUMBER);
        String title = data.get(position).get(IntetnKey.KEY_FILE_TITLE);

        fileNumberTv.setText(number);
        fileNameTv.setText(title);


        return v;
    }

}
