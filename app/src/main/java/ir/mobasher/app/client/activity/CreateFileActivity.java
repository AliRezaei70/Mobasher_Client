package ir.mobasher.app.client.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import java.util.ArrayList;
import java.util.List;
import butterknife.ButterKnife;
import ir.mobasher.app.client.R;
import ir.mobasher.app.client.adapter.CreateFileGridAdapter;
import ir.mobasher.app.client.app.Config;
import ir.mobasher.app.client.app.IntetnKey;
import ir.mobasher.app.client.helper.DisplayInfo;

public class CreateFileActivity extends AppCompatActivity {

    Intent intent ;
    int stepNumber = 1;
    LinearLayout step2Layout;
    RelativeLayout step1Layout;
    GridView gridView;
    ArrayList<String> gridData;
    ArrayList<String> fileNames;
    ArrayList<Uri> uris;
    EditText fileTitleEt;
    EditText fileDescEt;

    private static final String TAG = CreateFileActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_file_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.createFileToolbar);
        toolbar.setTitle(R.string.title_activity_create_file);
        setSupportActionBar(toolbar);

        step1Layout = (RelativeLayout) findViewById(R.id.step1Layout);
        step2Layout = (LinearLayout) findViewById(R.id.step2Layout);

        fileTitleEt = (EditText) findViewById(R.id.titleEt);
        fileDescEt = (EditText) findViewById(R.id.descEt);

        forceRTLIfSupported();

        gridView = (GridView) findViewById(R.id.filesGV);

        gridView.setColumnWidth(DisplayInfo.getInstance(this).getDisplayWidth()/3);

        // Set custom adapter (GridAdapter) to gridview

        gridData = new ArrayList<String>();
        fileNames = new ArrayList<String>();
        uris = new ArrayList<Uri>();

        gridView.setAdapter(  new CreateFileGridAdapter( this, gridData, fileNames, uris) );

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {


                Toast.makeText(
                        getApplicationContext(),
                        ((TextView) v.findViewById( R.id.gridItemTextView ))
                                .getText(), Toast.LENGTH_SHORT).show();

            }
        });

        ButterKnife.bind(this);
        // Clearing older images from cache directory
        // don't call this line if you want to choose multiple images in the same activity
        // call this once the bitmap(s) usage is over
        ImagePickerActivity.clearCache(this);



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.createFilefab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dexter.withActivity(CreateFileActivity.this)
                        .withPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .withListener(new MultiplePermissionsListener() {
                            @Override
                            public void onPermissionsChecked(MultiplePermissionsReport report) {
                                if (report.areAllPermissionsGranted()) {
                                    showImagePickerOptions();
                                }

                                if (report.isAnyPermissionPermanentlyDenied()) {
                                    showSettingsDialog();
                                }
                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                                token.continuePermissionRequest();
                            }
                        }).check();
            }
        });

    }

    private void showImagePickerOptions() {
        ImagePickerActivity.showImagePickerOptions(this, new ImagePickerActivity.PickerOptionListener() {
            @Override
            public void onTakeCameraSelected() {
                launchCameraIntent();
            }

            @Override
            public void onChooseGallerySelected() {
                launchGalleryIntent();
            }
        });
    }

    private void launchCameraIntent() {
        Intent intent = new Intent(CreateFileActivity.this, ImagePickerActivity.class);
        intent.putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_IMAGE_CAPTURE);

        // setting aspect ratio
        intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true);
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1);

        // setting maximum bitmap width and height
        intent.putExtra(ImagePickerActivity.INTENT_SET_BITMAP_MAX_WIDTH_HEIGHT, true);
        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_WIDTH, 1000);
        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_HEIGHT, 1000);

        startActivityForResult(intent, Config.REQUEST_IMAGE);
    }

    private void launchGalleryIntent() {
        Intent intent = new Intent(CreateFileActivity.this, ImagePickerActivity.class);
        intent.putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_GALLERY_IMAGE);

        // setting aspect ratio
        intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true);
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1);
        startActivityForResult(intent, Config.REQUEST_IMAGE);
    }

    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(CreateFileActivity.this);
        builder.setTitle(getString(R.string.dialog_permission_title));
        builder.setMessage(getString(R.string.dialog_permission_message));
        builder.setPositiveButton(getString(R.string.go_to_settings), (dialog, which) -> {
            dialog.cancel();
            openSettings();
        });
        builder.setNegativeButton(getString(android.R.string.cancel), (dialog, which) -> dialog.cancel());
        builder.show();
    }

    // navigating user to app settings
    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == Config.REQUEST_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                Uri uri = data.getParcelableExtra("path");
                try {

                    final AlertDialog dialogBuilder = new AlertDialog.Builder(this).create();
                    LayoutInflater inflater = this.getLayoutInflater();
                    View dialogView = inflater.inflate(R.layout.add_file_caption_dialog, null);

                    final EditText nameEt = (EditText) dialogView.findViewById(R.id.dialog_et);
                    Button acceptBtn = (Button) dialogView.findViewById(R.id.dialog_acceptBtn);
                    Button cancelBtn = (Button) dialogView.findViewById(R.id.dialog_cancelBtn);
                    ImageView dialogImageView = (ImageView) dialogView.findViewById(R.id.dialog_imageview);
                    Glide.with(CreateFileActivity.this).load(uri).into(dialogImageView);
                    dialogImageView.setColorFilter(ContextCompat.getColor(CreateFileActivity.this, android.R.color.transparent));


                    acceptBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            if (TextUtils.isEmpty(nameEt.getText().toString())) {
                                nameEt.setError(getString(R.string.error_field_required));
                            }else {
                                gridData.add(uri.getPath());
                                uris.add(uri);
                                fileNames.add(nameEt.getText().toString());
                                gridView.setAdapter(new CreateFileGridAdapter( CreateFileActivity.this, gridData, fileNames, uris));
                                dialogBuilder.dismiss();
                            }

                        }
                    });
                    cancelBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // DO SOMETHINGS
                            dialogBuilder.dismiss();
                        }
                    });

                    dialogBuilder.setView(dialogView);
                    dialogBuilder.show();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void forceRTLIfSupported() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }
    }

    public void continueBtnOnClick(View v){

        if (TextUtils.isEmpty(fileTitleEt.getText().toString())) {
            fileTitleEt.setError(this.getString(R.string.error_field_required));
            return;
        }

        if (TextUtils.isEmpty(fileDescEt.getText().toString())) {
            fileDescEt.setError(this.getString(R.string.error_field_required));
            return;
        }

        step1Layout.setVisibility(View.GONE);
        step2Layout.setVisibility(View.VISIBLE);
        stepNumber = 2;
    }

    public void createFileBtnOnClick(View v){
        Intent i = new Intent(this, FileRequestsActivity.class);
        i.putExtra(IntetnKey.KEY_FILE_TITLE, fileTitleEt.getText().toString());
        i.putExtra(IntetnKey.KEY_FILE_NUMBER, 135248);
        startActivity(i);
        finish();
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
