package ir.mobasher.app.client.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import ir.mobasher.app.client.R;


public class HelpViewPagerAdapter extends PagerAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
//    private Integer [] images = {R.mipmap.slide1,R.mipmap.slide2,R.mipmap.slide3};
    private Integer [] images;
    private String [] tille;
    private String [] description;
    private Activity activity;
    public HelpViewPagerAdapter(Activity activity, Context context, Integer[] images, String [] title, String [] description) {
        this.activity = activity;
        this.context = context;
        this.images = images;
        this.tille = title;
        this.description = description;
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {


        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;

        View view = layoutInflater.inflate(R.layout.help_item_layout, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.help_item_imageView);

        imageView.getLayoutParams().height = height/2;
        imageView.getLayoutParams().width = width;
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);

        imageView.setImageResource(images[position]);


        TextView helpItemTitleTv = (TextView) view.findViewById(R.id.helpItemTitleTv);
        helpItemTitleTv.setText(tille[position]);

        TextView helpItemDescTv = (TextView) view.findViewById(R.id.helpItemDescTv);
        helpItemDescTv.setText(description[position]);

        ViewPager vp = (ViewPager) container;
        vp.addView(view, 0);
        return view;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        ViewPager vp = (ViewPager) container;
        View view = (View) object;
        vp.removeView(view);

    }

    
}
