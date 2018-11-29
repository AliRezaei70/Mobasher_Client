package ir.mobasher.app.client.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import ir.mobasher.app.client.R;

public class CreateFileGridAdapter extends BaseAdapter {

    private Context context;
    private final ArrayList<String> gridValues;

    //Constructor to initialize values
    public CreateFileGridAdapter(Context context, ArrayList<String> gridValues) {

        this.context        = context;
        this.gridValues     = gridValues;
    }

    @Override
    public int getCount() {

        // Number of times getView method call depends upon gridValues.length
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


    // Number of times getView method call depends upon gridValues.length

    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;

        if (convertView == null) {

            gridView = new View(context);

            gridView = inflater.inflate( R.layout.create_file_grid_item , null);

            TextView textView = (TextView) gridView.findViewById(R.id.gridItemTextView);

            String data = gridValues.get(position);
            String text = data.substring(data.lastIndexOf("/"));
            textView.setText(text.substring(1,text.length()));

            // set image based on selected text

            ImageView imageView = (ImageView) gridView.findViewById(R.id.gridItemImageView);

            imageView.setImageResource(R.drawable.change_pic);

        } else {

            gridView = (View) convertView;
        }

        return gridView;
    }
}
