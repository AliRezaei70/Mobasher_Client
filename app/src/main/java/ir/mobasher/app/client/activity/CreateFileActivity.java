package ir.mobasher.app.client.activity;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import ir.mobasher.app.client.R;
import ir.mobasher.app.client.adapter.CreateFileGridAdapter;
import ir.mobasher.app.client.helper.DisplayInfo;

public class CreateFileActivity extends AppCompatActivity {

    int stepNumber = 1;
    LinearLayout step2Layout;
    RelativeLayout step1Layout;
    GridView gridView;
    static final String[ ] GRID_DATA = new String[] {
            "Windows" ,
            "iOS",
            "Android" ,
            "Blackberry",
            "Java" ,
            "JQuery",
            "Phonegap",
            "SQLite",
            "Thread" ,
            "Video"
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_file_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.createFileToolbar);
//        toolbar.setTitleTextColor(0x000000);
        toolbar.setTitle(R.string.title_activity_create_file);
        setSupportActionBar(toolbar);

        step1Layout = (RelativeLayout) findViewById(R.id.step1Layout);
        step2Layout = (LinearLayout) findViewById(R.id.step2Layout);

        forceRTLIfSupported();


        gridView = (GridView) findViewById(R.id.filesGV);

        gridView.setColumnWidth(DisplayInfo.getInstance(this).getDisplayWidth()/3);

        // Set custom adapter (GridAdapter) to gridview

        gridView.setAdapter(  new CreateFileGridAdapter( this, GRID_DATA ) );

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {


                Toast.makeText(
                        getApplicationContext(),
                        ((TextView) v.findViewById( R.id.gridItemTextView ))
                                .getText(), Toast.LENGTH_SHORT).show();

            }
        });

    }




    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void forceRTLIfSupported() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }
    }

    public void continueBtnOnClick(View v){
        step1Layout.setVisibility(View.GONE);
        step2Layout.setVisibility(View.VISIBLE);
        stepNumber = 2;
    }

    public void createFileBtnOnClick(View v){

    }

    @Override
    public void onBackPressed() {
        if(stepNumber == 2){
            step1Layout.setVisibility(View.VISIBLE);
            step2Layout.setVisibility(View.GONE);
            stepNumber = 1;
        }else if(stepNumber == 1){
            finish();
        }

    }
}
