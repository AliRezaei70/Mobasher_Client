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
import ir.mobasher.app.client.app.AppKey;

public class CallReportAdapter extends SimpleAdapter {
    Context context;
    List<Map<String, String>> data;


    public CallReportAdapter(Context context,
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
            v = vi.inflate(R.layout.call_report_list_row, null);
        }

        String dateAndTime = data.get(position).get(AppKey.KEY_DATE_AND_TIME);
        String fileName = data.get(position).get(AppKey.KEY_FILE_NAME);
        String lawyerName = data.get(position).get(AppKey.KEY_LAWYER_NAME);
        String totalTime = data.get(position).get(AppKey.KEY_TOTAL_TIME);
        String price = data.get(position).get(AppKey.KEY_PRICE);

        TextView dateAndTimeTv = (TextView) v.findViewById(R.id.dateAndTimeTv);
        dateAndTimeTv.setText(dateAndTime);

        TextView fileNameTv = (TextView) v.findViewById(R.id.fileNameTv);
        fileNameTv.setText(fileName);

        TextView lawyerNameTv = (TextView) v.findViewById(R.id.lawyerNameTv);
        lawyerNameTv.setText(lawyerName);


        TextView priceTv = (TextView) v.findViewById(R.id.priceTv);
        priceTv.setText(price);

        TextView totalTimeTv = (TextView) v.findViewById(R.id.totalTimeTv);
        totalTimeTv.setText(totalTime);


        return v;
    }

}

