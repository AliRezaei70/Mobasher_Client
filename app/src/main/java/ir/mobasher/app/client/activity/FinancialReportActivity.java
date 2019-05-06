package ir.mobasher.app.client.activity;

import android.annotation.TargetApi;
import android.content.Intent;
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
import ir.mobasher.app.client.adapter.FinancialReportListAdapter;
import ir.mobasher.app.client.app.AppKey;

public class FinancialReportActivity extends AppCompatActivity {

    ArrayList<HashMap<String, String>> data;
    HashMap<String, String> map;
    FinancialReportListAdapter financialReportListAdapter;
    ListView financialReportListView;
    TextView availableCreditTv;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_financial_report);
        Toolbar toolbar = (Toolbar) findViewById(R.id.financialReportToolbar);
        setSupportActionBar(toolbar);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        forceRTLIfSupported();

        financialReportListView = (ListView) findViewById(R.id.finacialReportListView);
        availableCreditTv = (TextView) findViewById(R.id.availableCreditTv);
        availableCreditTv.setText(this.getString(R.string.available_credit) + " " + "10000" + " " + this.getString(R.string.rials));

        initList();
    }

    public void initList(){
        data = new ArrayList<HashMap<String, String>>();

        financialReportListAdapter = new FinancialReportListAdapter(this, data,
                R.layout.financial_report_list_row, new String[] {
                AppKey.KEY_DATE_AND_TIME,
                AppKey.KEY_DESCRIPTION,
                AppKey.KEY_NUMBER,
                AppKey.KEY_PRICE}, new int[] {
                R.id.dateAndTimeTv,
                R.id.descTv,
                R.id.priceTv,
                R.id.numberTv}, true);

        for (int i=0; i<15; i++){
            map = new HashMap<String, String>();

            map.put(AppKey.KEY_DATE_AND_TIME, "پنجشنبه 25 مهر 98 | 02:00");
            map.put(AppKey.KEY_DESCRIPTION, "بابت درخواست شماره 1717");
            map.put(AppKey.KEY_NUMBER, "6570");
            map.put(AppKey.KEY_PRICE, "12000" + " " + this.getString(R.string.rials));

            data.add(map);
        }

        financialReportListView.setAdapter(financialReportListAdapter);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void forceRTLIfSupported() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1){
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }
    }

    public void increaseCreditOnClick(View v){
        startActivity(new Intent(this, IncreaseCreditActivity.class));
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