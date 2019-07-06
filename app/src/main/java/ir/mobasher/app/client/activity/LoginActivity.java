package ir.mobasher.app.client.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.mikhaellopez.circularimageview.CircularImageView;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import butterknife.ButterKnife;
import ir.mobasher.app.client.R;
import ir.mobasher.app.client.api.APIInterface;
import ir.mobasher.app.client.api.clientProfile.CreateClientProfileErrorResponse;
import ir.mobasher.app.client.api.clientProfile.CreateClientProfileSuccessResponse;
import ir.mobasher.app.client.api.login.LoginErrorResponse;
import ir.mobasher.app.client.api.login.LoginSuccessResponse;
import ir.mobasher.app.client.api.validateUser.JwtResponse;
import ir.mobasher.app.client.api.validateUser.ValidationErrorResponse;
import ir.mobasher.app.client.api.validateUser.ValidationSuccessResponse;
import ir.mobasher.app.client.app.AppTags;
import ir.mobasher.app.client.app.Config;
import ir.mobasher.app.client.manager.ProgressBarManager;
import ir.mobasher.app.client.model.clientProfile.ClientProfile;
import ir.mobasher.app.client.network.RetrofitClientInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import android.app.Activity;
import android.graphics.Bitmap;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;


public class LoginActivity extends AppCompatActivity implements TextView.OnEditorActionListener {


    private EditText userNameEt;
    private EditText nameEt;
    private EditText familyNameEt;
    private EditText emailEt;
    private View mProgressView;
    private View mLoginFormView;
    private LinearLayout loginForm1;
    private LinearLayout loginForm2;
    private LinearLayout loginForm3;
    private EditText phoneNumEt;
    private TextView showPhoneNumTv;
    private TextView timmerTv;
    private CountDownTimer countDownTimer;
    private EditText validationCodeEt;
    private ProgressBarManager progressBarManager;
    private SharedPreferences.Editor settingsPrefEditor;
    TelephonyManager telephonyManager;

    private static final String TAG = LoginActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.login_toolbar);
//        setSupportActionBar(toolbar);
        forceRTLIfSupported();

        // Set up the login form.
        userNameEt = (EditText) findViewById(R.id.username);
        nameEt = (EditText) findViewById(R.id.nameEt);
        familyNameEt = (EditText) findViewById(R.id.familyNameEt);
        emailEt = (EditText) findViewById(R.id.emailEt);

        validationCodeEt = (EditText) findViewById(R.id.validationCodeEt);
        validationCodeEt.setOnEditorActionListener(this);
        validationCodeEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (validationCodeEt.getText().length() == 5){
                    progressBarManager.showProgress((ProgressBar) mProgressView, LoginActivity.this);

                    validateUser(validationCodeEt.getText().toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mProgressView = findViewById(R.id.login_progress);

        loginForm1 = (LinearLayout) findViewById(R.id.loginForm1);
        loginForm2 = (LinearLayout) findViewById(R.id.loginForm2);
        loginForm3 = (LinearLayout) findViewById(R.id.loginForm3);
        phoneNumEt = (EditText) findViewById(R.id.phoneNumEt);
        showPhoneNumTv = (TextView) findViewById(R.id.showPhoneNumTv);
        timmerTv = (TextView) findViewById(R.id.timmerTv);

        deviceId();

        progressBarManager = new ProgressBarManager();

        settingsPrefEditor = getSharedPreferences(Config.SETTINGS_SHARED_PREF, MODE_PRIVATE).edit();


        ButterKnife.bind(this);
        // Clearing older images from cache directory
        // don't call this line if you want to choose multiple images in the same activity
        // call this once the bitmap(s) usage is over
        ImagePickerActivity.clearCache(this);


//        loginForm1.setVisibility(View.GONE);
//        loginForm2.setVisibility(View.GONE);
//        loginForm3.setVisibility(View.VISIBLE);

    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void forceRTLIfSupported() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }
    }

    private boolean attemptLogin() {


        // Reset errors.
        userNameEt.setError(null);
        nameEt.setError(null);
        familyNameEt.setError(null);
        emailEt.setError(null);

        // Store values at the time of the login attempt.
        String userName = userNameEt.getText().toString();
        String name = nameEt.getText().toString();
        String family = familyNameEt.getText().toString();
        String email = emailEt.getText().toString();

        View focusView = null;


        // Check for a valid userName address.
        if (TextUtils.isEmpty(userName)) {
            userNameEt.setError(getString(R.string.error_field_required));
            focusView = userNameEt;
            return false;
        }

        if (TextUtils.isEmpty(name)) {
            nameEt.setError(getString(R.string.error_field_required));
            focusView = nameEt;
            return false;
        }

        if (TextUtils.isEmpty(family)) {
            familyNameEt.setError(getString(R.string.error_field_required));
            focusView = familyNameEt;
            return false;
        }

        if (TextUtils.isEmpty(email)) {
            emailEt.setError(getString(R.string.error_field_required));
            focusView = emailEt;
            return false;
        }

        return true;
    }

    public void loginOnClick(View v) {
        if (attemptLogin()) {

            String username = userNameEt.getText().toString();
            String name = nameEt.getText().toString();
            String family = familyNameEt.getText().toString();
            String email = emailEt.getText().toString();

            SharedPreferences settingsPref = getSharedPreferences(Config.SETTINGS_SHARED_PREF, MODE_PRIVATE);
            String clientId = settingsPref.getString(Config.CLIENT_ID, Config.DEFAULT_STRING_NO_THING_FOUND);

            TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);



            String fireBaseId = settingsPref.getString(Config.FIRE_BASE_ID, Config.DEFAULT_STRING_NO_THING_FOUND);
            String imei = settingsPref.getString(Config.IMEI, Config.DEFAULT_STRING_NO_THING_FOUND);

            ClientProfile cp = new ClientProfile();
            cp.setClientId(clientId);
            cp.setFirstName(name);
            cp.setLastName(family);
            cp.setUserName(username);
            cp.setEmail(email);
            cp.setFirebaseid(fireBaseId);
            cp.setRegisterationcomplete(true);
            cp.setImei(imei);

            progressBarManager.showProgress((ProgressBar) mProgressView, this);
            registerUser(cp);
        }
    }

    private void deviceId() {
        telephonyManager = (TelephonyManager) getSystemService(this.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, 101);
            return;
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults){
        switch (requestCode) {
            case 101:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, 101);
                        return;
                    }
                    String imeiNumber = telephonyManager.getDeviceId();
                   // Toast.makeText(this,imeiNumber,Toast.LENGTH_LONG).show();
                    settingsPrefEditor.putString(Config.IMEI, imeiNumber).commit();
                } else {
                    Toast.makeText(this,"Without permission we check",Toast.LENGTH_LONG).show();
                    finish();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public void changePickOnClick(View v) {
//        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//        intent.addCategory(Intent.CATEGORY_OPENABLE);
//        intent.setType("image/*");
//        startActivityForResult(Intent.createChooser(intent, "Select Picture"),Config.REQUEST_GET_SINGLE_FILE);


        Dexter.withActivity(this)
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
        Intent intent = new Intent(LoginActivity.this, ImagePickerActivity.class);
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
        Intent intent = new Intent(LoginActivity.this, ImagePickerActivity.class);
        intent.putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_GALLERY_IMAGE);

        // setting aspect ratio
        intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true);
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1);
        startActivityForResult(intent, Config.REQUEST_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == Config.REQUEST_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                Uri uri = data.getParcelableExtra("path");
                try {
                    // You can update this bitmap to your server
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);

                    // loading profile image from local cache
                    loadProfile(uri.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void loadProfile(String url) {
        Log.d(TAG, "Image cache path: " + url);

        CircularImageView profileImageView = (CircularImageView) findViewById(R.id.profileImageView);


        Glide.with(this).load(url)
                .into(profileImageView);
        profileImageView.setColorFilter(ContextCompat.getColor(this, android.R.color.transparent));
    }

    /**
     * Showing Alert Dialog with Settings option
     * Navigates user to app settings
     * NOTE: Keep proper title and message depending on your app
     */
    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
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

    public void getRegCodeOnClick(View v){

        progressBarManager.showProgress((ProgressBar) mProgressView, this);

        signinUser(phoneNumEt.getText().toString());

        //TODO should be delete
//        loginForm1.setVisibility(View.GONE);
//        loginForm2.setVisibility(View.GONE);
//        loginForm3.setVisibility(View.VISIBLE);
    }

    public void editNumOnClick(View v){
        loginForm1.setVisibility(View.VISIBLE);
        loginForm2.setVisibility(View.GONE);
        loginForm3.setVisibility(View.GONE);

    }

    public void resendCodeOnClick(View v){
        resetTimer();

       // progressBarManager.showProgress((ProgressBar) mProgressView, this);

       // validateUser(validationCodeEt.getText().toString());

        getRegCodeOnClick(null);


    }

    public void resetTimer(){
        if (countDownTimer != null)
            countDownTimer.cancel();

        countDownTimer = new CountDownTimer(90000, 1000) {

            public void onTick(long millisUntilFinished) {

                timmerTv.setText(""+String.format("%02d:%02d",
                        TimeUnit.MILLISECONDS.toMinutes( millisUntilFinished),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
            }

            public void onFinish() {
                //timmerTv.setText("done!");
                //resend the activation code automatically

            }

        }.start();
    }

    @Override
    public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
        if (actionId == EditorInfo.IME_ACTION_GO) {

            InputMethodManager inputManager =
                    (InputMethodManager) getBaseContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);

            progressBarManager.showProgress((ProgressBar) mProgressView, this);

            validateUser(validationCodeEt.getText().toString());

            return true;
        }
        return false;
    }

    public String getPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }

    //service call methods
    public void signinUser(String phoneNumber){
        APIInterface service = RetrofitClientInstance.getRetrofitInstance().create(APIInterface.class);
        Call<LoginSuccessResponse> responseCall = service.loginUser(phoneNumber);
        responseCall.enqueue(new Callback<LoginSuccessResponse>() {
            @Override
            public void onResponse(Call<LoginSuccessResponse> call, Response<LoginSuccessResponse> response) {
                if (response.isSuccessful()){
                    LoginSuccessResponse loginResponse = response.body();
                    Log.i(AppTags.POST_USER_NUMBER_RESPONSE, loginResponse.getMessage());
                    Toast.makeText(getBaseContext(), loginResponse.getMessage(), Toast.LENGTH_SHORT).show();


                    settingsPrefEditor.putString(Config.CLIENT_ID, loginResponse.getClientId());
                    settingsPrefEditor.commit();

                    Toast.makeText(getBaseContext(), loginResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    loginForm1.setVisibility(View.GONE);
                    loginForm2.setVisibility(View.VISIBLE);
                    loginForm3.setVisibility(View.GONE);

                    showPhoneNumTv.setText(phoneNumEt.getText().toString());

                    resetTimer();


                }else {
                    Gson gson = new GsonBuilder().create();
                    LoginErrorResponse errorResponse = new LoginErrorResponse();
                    try {
                        errorResponse = gson.fromJson(response.errorBody().string(), LoginErrorResponse.class);
                        Log.i(AppTags.POST_USER_NUMBER_RESPONSE, errorResponse.getMessage());
                        Toast.makeText(getBaseContext(), errorResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.i(AppTags.POST_USER_NUMBER_RESPONSE, AppTags.UNKNOWN_ERROR);
                    }


                }

                progressBarManager.hideProgress((ProgressBar) mProgressView, LoginActivity.this);

            }

            @Override
            public void onFailure(Call<LoginSuccessResponse> call, Throwable t) {
                Log.e(AppTags.POST_USER_NUMBER_RESPONSE, t.getMessage());
                Toast.makeText(getBaseContext(), R.string.connection_error, Toast.LENGTH_SHORT).show();
                progressBarManager.hideProgress((ProgressBar) mProgressView, LoginActivity.this);
            }


        });
    }

    public void validateUser(String validationCode){
        SharedPreferences settingsPref = getSharedPreferences(Config.SETTINGS_SHARED_PREF, MODE_PRIVATE);
        String clientId = settingsPref.getString(Config.CLIENT_ID, Config.DEFAULT_STRING_NO_THING_FOUND);

        APIInterface service = RetrofitClientInstance.getRetrofitInstance().create(APIInterface.class);
        Call<ValidationSuccessResponse> responseCall = service.validateUser(clientId, validationCode);
        responseCall.enqueue(new Callback<ValidationSuccessResponse>() {
            @Override
            public void onResponse(Call<ValidationSuccessResponse> call, Response<ValidationSuccessResponse> response) {
                if (response.isSuccessful()){
                    ValidationSuccessResponse validationResponse = response.body();
                    Log.i(AppTags.VALIDATE_USER_RESPONSE, validationResponse.getMessage());
                    Toast.makeText(getBaseContext(), validationResponse.getMessage(), Toast.LENGTH_SHORT).show();

                    JwtResponse jwtResponse = validationResponse.getJwtResponse();


                    settingsPrefEditor.putString(Config.WALLET_ID, validationResponse.getWalletId());
                    settingsPrefEditor.commit();

                    settingsPrefEditor.putString(Config.JWT_TOKEN, jwtResponse.getToken());
                    settingsPrefEditor.commit();

                    boolean isRegistrationCompelete = validationResponse.getRegisterationcomplete();

                    if (!isRegistrationCompelete){
                        loginForm1.setVisibility(View.GONE);
                        loginForm2.setVisibility(View.GONE);
                        loginForm3.setVisibility(View.VISIBLE);
                    }else {
                        settingsPrefEditor.putBoolean(Config.IS_LOGIN, true).commit();

                        Intent i = new Intent(LoginActivity.this, HomeActivity.class);
                        startActivity(i);
                        finish();
                    }


                }else {
                    Gson gson = new GsonBuilder().create();
                    ValidationErrorResponse errorResponse = new ValidationErrorResponse();
                    try {
                        errorResponse = gson.fromJson(response.errorBody().string(), ValidationErrorResponse.class);
                        Log.i(AppTags.VALIDATE_USER_RESPONSE, errorResponse.getMessage());
                        Toast.makeText(getBaseContext(), errorResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.i(AppTags.VALIDATE_USER_RESPONSE, AppTags.UNKNOWN_ERROR);
                    }


                }

                progressBarManager.hideProgress((ProgressBar) mProgressView, LoginActivity.this);
            }

            @Override
            public void onFailure(Call<ValidationSuccessResponse> call, Throwable t) {
                Log.e(AppTags.VALIDATE_USER_RESPONSE, t.getMessage());
                Toast.makeText(getBaseContext(), R.string.connection_error, Toast.LENGTH_SHORT).show();
                progressBarManager.hideProgress((ProgressBar) mProgressView, LoginActivity.this);
            }
        });
    }

    public void registerUser(ClientProfile cp){
        APIInterface service = RetrofitClientInstance.getRetrofitInstance().create(APIInterface.class);
        Call<CreateClientProfileSuccessResponse> responseCall = service.signIn(cp);
        responseCall.enqueue(new Callback<CreateClientProfileSuccessResponse>() {
            @Override
            public void onResponse(Call<CreateClientProfileSuccessResponse> call, Response<CreateClientProfileSuccessResponse> response) {
                if (response.isSuccessful()){
                    CreateClientProfileSuccessResponse cpSuccessResponse = response.body();
                    Log.i(AppTags.VALIDATE_USER_RESPONSE, cpSuccessResponse.getMessage());
                    Toast.makeText(getBaseContext(), cpSuccessResponse.getMessage(), Toast.LENGTH_SHORT).show();

                    settingsPrefEditor.putBoolean(Config.IS_LOGIN, true).commit();

                    Intent i = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(i);
                    finish();
                } else {
                    Gson gson = new GsonBuilder().create();
                    CreateClientProfileErrorResponse errorResponse = new CreateClientProfileErrorResponse();
                    try {
                        errorResponse = gson.fromJson(response.errorBody().string(), CreateClientProfileErrorResponse.class);
                        Log.i(AppTags.REGISTER_USER_RESPONSE, errorResponse.getMessage());
                        Toast.makeText(getBaseContext(), errorResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.i(AppTags.REGISTER_USER_RESPONSE, AppTags.UNKNOWN_ERROR);
                    }
                }

                progressBarManager.hideProgress((ProgressBar) mProgressView, LoginActivity.this);
            }

            @Override
            public void onFailure(Call<CreateClientProfileSuccessResponse> call, Throwable t) {
                Log.e(AppTags.REGISTER_USER_RESPONSE, t.getMessage());
                Toast.makeText(getBaseContext(), R.string.connection_error, Toast.LENGTH_SHORT).show();
                progressBarManager.hideProgress((ProgressBar) mProgressView, LoginActivity.this);
            }
        });
    }
}
