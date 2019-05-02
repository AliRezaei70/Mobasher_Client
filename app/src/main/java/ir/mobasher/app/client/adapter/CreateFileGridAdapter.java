package ir.mobasher.app.client.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import java.util.ArrayList;
import ir.mobasher.app.client.R;

public class CreateFileGridAdapter extends BaseAdapter {

    private Context context;
    private final ArrayList<String> gridValues;
    private final ArrayList<String> fileNames;
    private final ArrayList<Uri> uris;

    public CreateFileGridAdapter(Context context, ArrayList<String> gridValues, ArrayList<String> fileNames, ArrayList<Uri> uris) {
        this.context = context;
        this.gridValues = gridValues;
        this.uris = uris;
        this.fileNames = fileNames;
    }

    @Override
    public int getCount() {
        return gridValues.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;

        if (convertView == null) {
            gridView = new View(context);
            gridView = inflater.inflate( R.layout.create_file_grid_item , null);
            TextView textView = (TextView) gridView.findViewById(R.id.gridItemTextView);
            String data = gridValues.get(position);
            String text = data.substring(data.lastIndexOf("/"));
            //textView.setText(text.substring(1,text.length()));
            textView.setText(fileNames.get(position));
            ImageView imageView = (ImageView) gridView.findViewById(R.id.gridItemImageView);
            Glide.with(context).load(uris.get(position)).into(imageView);
            imageView.setColorFilter(ContextCompat.getColor(context, android.R.color.transparent));

        } else {
            gridView = (View) convertView;
        }

        return gridView;
    }
}
