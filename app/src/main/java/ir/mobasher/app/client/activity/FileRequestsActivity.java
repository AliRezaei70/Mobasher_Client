package ir.mobasher.app.client.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;
import ir.mobasher.app.client.R;
import ir.mobasher.app.client.app.AppKey;

public class FileRequestsActivity extends AppCompatActivity implements View.OnClickListener {

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_files);

        String fileName = getIntent().getExtras().getString(AppKey.KEY_FILE_TITLE);
        String fileNumber = getIntent().getExtras().getString(AppKey.KEY_FILE_NUMBER);

        Toolbar toolbar = (Toolbar) findViewById(R.id.fileRequestsToolbar);
        toolbar.setTitle(fileName);
        setSupportActionBar(toolbar);

        forceRTLIfSupported();

        LinearLayout viewFileLayout = (LinearLayout) findViewById(R.id.viewFileLayout);
        viewFileLayout.setOnClickListener(this);

        LinearLayout phoneConsultantLayout = (LinearLayout) findViewById(R.id.phoneConsultantLayout);
        phoneConsultantLayout.setOnClickListener(this);

        LinearLayout recentConsultantLayout = (LinearLayout) findViewById(R.id.recentConsultantLayout);
        recentConsultantLayout.setOnClickListener(this);

        LinearLayout faivoritesConsultantLayout = (LinearLayout) findViewById(R.id.faivoritesConsultantLayout);
        faivoritesConsultantLayout.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.viewFileLayout:
                Toast.makeText(this, getText(R.string.view_file), Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, FilePreviewActivity.class));

                return;
            case R.id.phoneConsultantLayout:
                Intent i = new Intent(this, AdviserRequestActivity.class);
                startActivity(i);
                finish();
                return;
            case R.id.recentConsultantLayout:
                Toast.makeText(this, getText(R.string.recent_consultant), Toast.LENGTH_SHORT).show();
                return;
            case R.id.faivoritesConsultantLayout:
                Toast.makeText(this, getText(R.string.faivorites_consultant), Toast.LENGTH_SHORT).show();
                return;

        }
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
