package ir.mobasher.app.client.activity;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.mikhaellopez.circularimageview.CircularImageView;
import ir.mobasher.app.client.R;

public class SendFeedbackActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setContentView(R.layout.activity_send_feedback);

        CircularImageView lawyerImage = (CircularImageView) findViewById(R.id.lawyerImage);
        String url = "https://www.gravatar.com/avatar/d713829a7d72f9237e0850caf6f2cb48?s=328&d=identicon&r=PG";
        Glide.with(this).load(url)
                .into(lawyerImage);
        lawyerImage.setColorFilter(ContextCompat.getColor(this, android.R.color.transparent));

        ImageView bookmarkImg = (ImageView) findViewById(R.id.bookmarkImg);
        TextView lawyerNameTv = (TextView) findViewById(R.id.lawyerNameTv);
        TextView lawyerAdviserTypeTv = (TextView) findViewById(R.id.lawyerAdviserTypeTv);
        TextView scoreTv = (TextView) findViewById(R.id.scoreTv);
        scoreTv.setText(5 + " " + SendFeedbackActivity.this.getString(R.string.score));
        RatingBar ratingBar = (RatingBar) findViewById(R.id.lawyerRatingBar);
        ratingBar.setRating(5);
        EditText feedbackEt = (EditText) findViewById(R.id.feedbackEt);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                scoreTv.setText(String.format(rating + " " + SendFeedbackActivity.this.getString(R.string.score)));
            }
        });

    }

    public void sendFeedBackOnClick(View v){

    }
}
