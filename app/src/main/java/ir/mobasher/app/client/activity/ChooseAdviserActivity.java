package ir.mobasher.app.client.activity;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.HashMap;
import ir.mobasher.app.client.R;
import ir.mobasher.app.client.adapter.ChooseAdviserAdapter;
import ir.mobasher.app.client.app.IntetnKey;

public class ChooseAdviserActivity extends AppCompatActivity {

    ListView chooseAdviserListView;
    ArrayList<HashMap<String, String>> data;
    HashMap<String, String> map;
    ChooseAdviserAdapter adviserListAdapter;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_adviser);
        Toolbar toolbar = (Toolbar) findViewById(R.id.chooseAdviserToolbar);
        setSupportActionBar(toolbar);
        forceRTLIfSupported();

        chooseAdviserListView = (ListView) findViewById(R.id.chooseAdviserList);

        initFilesList();
    }

    public void initFilesList(){
        data = new ArrayList<HashMap<String, String>>();

        adviserListAdapter = new ChooseAdviserAdapter(this, data,
                R.layout.files_list_row, new String[] {
                IntetnKey.KEY_FILE_NUMBER,
                IntetnKey.KEY_FILE_TITLE}, new int[] {
                R.id.fileNumberTv,
                R.id.fileNameTv});

        for (int i=0; i<15; i++){
            map = new HashMap<String, String>();

            map.put(IntetnKey.KEY_FILE_NUMBER, "125849");
            map.put(IntetnKey.KEY_FILE_TITLE, "مشکل با کارفرما و عدم تمکین به رای دادگاه");
            data.add(map);
        }

        chooseAdviserListView.setAdapter(adviserListAdapter);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void forceRTLIfSupported()
    {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1){
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
