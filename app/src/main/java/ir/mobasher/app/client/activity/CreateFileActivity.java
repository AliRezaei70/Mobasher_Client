package ir.mobasher.app.client.activity;

import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import ir.mobasher.app.client.R;
import ir.mobasher.app.client.adapter.CreateFileGridAdapter;
import ir.mobasher.app.client.helper.DisplayInfo;

public class CreateFileActivity extends AppCompatActivity {

    Intent intent ;
    int stepNumber = 1;
    LinearLayout step2Layout;
    RelativeLayout step1Layout;
    GridView gridView;
    ArrayList<String> gridData;
    ArrayList<Bitmap> bitmaps;

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

        gridData = new ArrayList<String>();
        bitmaps = new ArrayList<Bitmap>();

        gridView.setAdapter(  new CreateFileGridAdapter( this, gridData, bitmaps) );

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {


                Toast.makeText(
                        getApplicationContext(),
                        ((TextView) v.findViewById( R.id.gridItemTextView ))
                                .getText(), Toast.LENGTH_SHORT).show();

            }
        });

        final String[] mimeTypes =
                {"application/msword","application/vnd.openxmlformats-officedocument.wordprocessingml.document", // .doc & .docx
                        "application/vnd.ms-powerpoint","application/vnd.openxmlformats-officedocument.presentationml.presentation", // .ppt & .pptx
                        "application/vnd.ms-excel","application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", // .xls & .xlsx
                        "text/plain",
                        "application/pdf",
                        "application/zip",
                        "image/*",
                        "audio/*",
                        "video/*"
                };

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.createFilefab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
               // intent.setType("*/*");
               // intent.setType("image/*|application/pdf|audio/*|application/x-excel|");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    intent.setType(mimeTypes.length == 1 ? mimeTypes[0] : "*/*");
                    if (mimeTypes.length > 0) {
                        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
                    }
                } else {
                    String mimeTypesStr = "";
                    for (String mimeType : mimeTypes) {
                        mimeTypesStr += mimeType + "|";
                    }
                    intent.setType(mimeTypesStr.substring(0,mimeTypesStr.length() - 1));
                }
                startActivityForResult(intent, 7);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub

        switch(requestCode){

            case 7:

                if(resultCode==RESULT_OK){

                    String PathHolder = data.getData().getPath();

                    Toast.makeText(CreateFileActivity.this, PathHolder , Toast.LENGTH_LONG).show();

                    gridData.add(PathHolder);


                    ContentResolver crThumb = getContentResolver();
                    BitmapFactory.Options options=new BitmapFactory.Options();
                    options.inSampleSize = 1;
                    Bitmap curThumb = MediaStore.Video.Thumbnails.getThumbnail(crThumb, ContentUris.parseId(intent.getData()), MediaStore.Video.Thumbnails.MICRO_KIND, options);

                    bitmaps.add(curThumb);

                    gridView.setAdapter(  new CreateFileGridAdapter( this, gridData, bitmaps) );

                }
                break;

        }
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
