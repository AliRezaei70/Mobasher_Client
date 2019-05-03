package ir.mobasher.app.client.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mikhaellopez.circularimageview.CircularImageView;
import java.util.List;
import java.util.Map;
import ir.mobasher.app.client.R;

public class ChooseAdviserAdapter extends SimpleAdapter {
    Context context;
    List<Map<String, String>> data;


    public ChooseAdviserAdapter(Context context,
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
            v = vi.inflate(R.layout.choose_adviser_list_row, null);
        }

        CircularImageView lawyerImage = (CircularImageView) v.findViewById(R.id.lawyerImage);
        String url = "https://www.gravatar.com/avatar/d713829a7d72f9237e0850caf6f2cb48?s=328&d=identicon&r=PG";
        Glide.with(context).load(url)
                .into(lawyerImage);
        lawyerImage.setColorFilter(ContextCompat.getColor(context, android.R.color.transparent));

        TextView lawyerLevel = (TextView) v.findViewById(R.id.lawyerLevel);

        TextView lawyerName = (TextView) v.findViewById(R.id.lawyerName);

        RatingBar lawyerRatingBar = (RatingBar) v.findViewById(R.id.lawyerRatingBar);

        TextView lawyerScore = (TextView) v.findViewById(R.id.lawyerScore);

        TextView lawyerPrice = (TextView) v.findViewById(R.id.lawyerPrice);

        Button requestBtn = (Button) v.findViewById(R.id.requestBtn);

        return v;
    }

}
