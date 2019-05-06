package ir.mobasher.app.client.activity;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.HashMap;
import ir.mobasher.app.client.R;
import ir.mobasher.app.client.adapter.CallReportAdapter;
import ir.mobasher.app.client.app.AppKey;

public class CallReportActivity extends AppCompatActivity {

    ArrayList<HashMap<String, String>> data;
    HashMap<String, String> map;
    CallReportAdapter callReportListAdapter;
    ListView callReportListView;
    TextView totalRequestsTv;
    TextView totalCallsTv;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_report);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        forceRTLIfSupported();

        callReportListView = (ListView) findViewById(R.id.callReportListView);

        totalRequestsTv = (TextView) findViewById(R.id.totalRequestTv);
        totalRequestsTv.setText("7");

        totalCallsTv = (TextView) findViewById(R.id.totalCallTv);
        totalCallsTv.setText("12:30:50");

        initList();
    }

    public void initList(){
        data = new ArrayList<HashMap<String, String>>();

        callReportListAdapter = new CallReportAdapter(this, data,
                R.layout.financial_report_list_row, new String[] {
                AppKey.KEY_DATE_AND_TIME,
                AppKey.KEY_FILE_NAME,
                AppKey.KEY_LAWYER_NAME,
                AppKey.KEY_PRICE,
                AppKey.KEY_TOTAL_TIME}, new int[] {
                R.id.dateAndTimeTv,
                R.id.fileNameTv,
                R.id.lawyerNameTv,
                R.id.priceTv,
                R.id.totalTimeTv});

        for (int i=0; i<15; i++){
            map = new HashMap<String, String>();

            map.put(AppKey.KEY_DATE_AND_TIME, "شنبه 25 مهر 98 | 02:00");
            map.put(AppKey.KEY_FILE_NAME, "مشکل با کارفرما");
            map.put(AppKey.KEY_LAWYER_NAME, "علی رضایی");
            map.put(AppKey.KEY_PRICE, "15000" + " " + this.getString(R.string.rials));
            map.put(AppKey.KEY_TOTAL_TIME, "20" + " " + this.getString(R.string.minute));

            data.add(map);
        }

        callReportListView.setAdapter(callReportListAdapter);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void forceRTLIfSupported() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1){
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();

    }
}