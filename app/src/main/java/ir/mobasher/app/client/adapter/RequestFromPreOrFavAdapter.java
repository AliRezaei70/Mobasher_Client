package ir.mobasher.app.client.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.mikhaellopez.circularimageview.CircularImageView;
import java.util.List;
import java.util.Map;
import ir.mobasher.app.client.R;
import ir.mobasher.app.client.activity.SendFeedbackActivity;
import ir.mobasher.app.client.app.AppKey;

public class RequestFromPreOrFavAdapter extends SimpleAdapter {
    Context context;
    List<Map<String, String>> data;


    public RequestFromPreOrFavAdapter(Context context,
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
            v = vi.inflate(R.layout.choose_from_pre_or_fav_advisers_list_row, null);
        }

        String score = data.get(position).get(AppKey.KEY_SCORE);
        String rate = data.get(position).get(AppKey.KEY_RATE);
        String name = data.get(position).get(AppKey.KEY_NAME);
        String type = data.get(position).get(AppKey.KEY_ADVISER_TYPE);
        String photoUrl = data.get(position).get(AppKey.KEY_PHOTO_URL);
        String level = data.get(position).get(AppKey.KEY_LEVEL);
        String price = data.get(position).get(AppKey.KEY_PRICE);
        String status = data.get(position).get(AppKey.KEY_STATUS);
        String isOnline = data.get(position).get(AppKey.KEY_IS_ONLINE);
        String selectOrDeselect = data.get(position).get(AppKey.KEY_SELECT_OR_DESELECT);


        CircularImageView lawyerImage = (CircularImageView) v.findViewById(R.id.lawyerImage);
        String url = "https://www.gravatar.com/avatar/d713829a7d72f9237e0850caf6f2cb48?s=328&d=identicon&r=PG";
        Glide.with(context).load(url)
                .into(lawyerImage);
        lawyerImage.setColorFilter(ContextCompat.getColor(context, android.R.color.transparent));

        TextView lawyerLevel = (TextView) v.findViewById(R.id.lawyerLevel);
        lawyerLevel.setText(level);

        TextView lawyerName = (TextView) v.findViewById(R.id.lawyerName);
        lawyerName.setText(name);

        RatingBar lawyerRatingBar = (RatingBar) v.findViewById(R.id.lawyerRatingBar);
        lawyerRatingBar.setRating(Float.parseFloat(rate));

        TextView lawyerScore = (TextView) v.findViewById(R.id.lawyerScore);
        lawyerScore.setText(score);

        TextView lawyerPrice = (TextView) v.findViewById(R.id.lawyerPrice);
        lawyerPrice.setText(price);

        TextView adviserType = (TextView) v.findViewById(R.id.lawyerAdviserType);
        adviserType.setText(type);

        ImageView bookmarkImg = (ImageView) v.findViewById(R.id.bookmarkImg);

        if (selectOrDeselect.equals(AppKey.KEY_SELECT)){
            bookmarkImg.setImageResource(R.drawable.bookmark_enable);
        }else {
            bookmarkImg.setImageResource(R.drawable.bookmark_disable);
        }


        Button selectOrDeselectBtn = (Button) v.findViewById(R.id.selectOrDeselectBtn);
        selectOrDeselectBtn.setText(status);

        selectOrDeselectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, SendFeedbackActivity.class));
            }
        });

        return v;
    }

}
