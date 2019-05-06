package ir.mobasher.app.client.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import java.util.List;
import java.util.Map;
import ir.mobasher.app.client.R;
import ir.mobasher.app.client.app.AppKey;

public class FinancialReportListAdapter extends SimpleAdapter {
    Context context;
    List<Map<String, String>> data;
    boolean isIncrease;


    public FinancialReportListAdapter(Context context,
                                      List<? extends Map<String, ?>> data, int resource, String[] from,
                                      int[] to, boolean isIncrease) {

        super(context, data, resource, from, to);
        this.context = context;
        this.data = (List<Map<String, String>>) data;
        this.isIncrease = isIncrease;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        View v = view;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.financial_report_list_row, null);
        }

        String dateAndTime = data.get(position).get(AppKey.KEY_DATE_AND_TIME);
        String desc = data.get(position).get(AppKey.KEY_DESCRIPTION);
        String number = data.get(position).get(AppKey.KEY_NUMBER);
        String price = data.get(position).get(AppKey.KEY_PRICE);

        TextView dateAndTimeTv = (TextView) v.findViewById(R.id.dateAndTimeTv);
        dateAndTimeTv.setText(dateAndTime);

        TextView numberTv = (TextView) v.findViewById(R.id.numberTv);
        RelativeLayout dateAndTimeLayout = (RelativeLayout) v.findViewById(R.id.dateAndTimeLayout);
        TextView increaseOrDecreaseTv = (TextView) v.findViewById(R.id.increaseOrDecreaseTv);

        if (isIncrease) {
            increaseOrDecreaseTv.setText(R.string.increase_credit);
            dateAndTimeLayout.setBackgroundColor(Color.parseColor("#08dd91"));
            numberTv.setText(context.getString(R.string.transaction_num) + " " + number);
        }else{
            increaseOrDecreaseTv.setText(R.string.decrease_credit);
            dateAndTimeLayout.setBackgroundColor(Color.parseColor("#c14242"));
            numberTv.setText(context.getString(R.string.factor) + " " + number);
        }
        TextView priceTv = (TextView) v.findViewById(R.id.priceTv);
        priceTv.setText(price);

        TextView descTv = (TextView) v.findViewById(R.id.descTv);
        descTv.setText(desc);


        return v;
    }

}

