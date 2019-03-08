package ir.mobasher.app.client.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import ir.mobasher.app.client.R;
import ir.mobasher.app.client.api.APIInterface;
import ir.mobasher.app.client.api.login.LoginErrorResponse;
import ir.mobasher.app.client.api.login.LoginSuccessResponse;
import ir.mobasher.app.client.app.AppTags;
import ir.mobasher.app.client.app.Config;
import ir.mobasher.app.client.manager.ProgressBarManager;
import ir.mobasher.app.client.network.RetrofitClientInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity implements TextView.OnEditorActionListener {


    private EditText userNameEt;
    private EditText nameEt;
    private EditText familyNameEt;
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
        familyNameEt = (EditText) findViewById(R.id.familyNamEt);

        validationCodeEt = (EditText) findViewById(R.id.validationCodeEt);
        validationCodeEt.setOnEditorActionListener(this);

        mProgressView = findViewById(R.id.login_progress);

        loginForm1 = (LinearLayout) findViewById(R.id.loginForm1);
        loginForm2 = (LinearLayout) findViewById(R.id.loginForm2);
        loginForm3 = (LinearLayout) findViewById(R.id.loginForm3);
        phoneNumEt = (EditText) findViewById(R.id.phoneNumEt);
        showPhoneNumTv = (TextView) findViewById(R.id.showPhoneNumTv);
        timmerTv = (TextView) findViewById(R.id.timmerTv);

        progressBarManager = new ProgressBarManager();

    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void forceRTLIfSupported()
    {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1){
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }
    }

    private boolean attemptLogin() {


        // Reset errors.
        userNameEt.setError(null);
        nameEt.setError(null);
        familyNameEt.setError(null);

        // Store values at the time of the login attempt.
        String userName = userNameEt.getText().toString();
        String name = nameEt.getText().toString();
        String family = familyNameEt.getText().toString();

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

        return true;
    }

    public void loginOnClick(View v) {
        if (attemptLogin()) {

            String username = userNameEt.getText().toString();
            String name = nameEt.getText().toString();
            String family = familyNameEt.getText().toString();

            SharedPreferences.Editor settingsPrefEditor = getSharedPreferences(Config.SETTINGS_SHARED_PREF, MODE_PRIVATE).edit();


            settingsPrefEditor.putString(Config.USERNAME, username);
            settingsPrefEditor.putString(Config.NAME, name);
            settingsPrefEditor.putString(Config.FAMILYNAME, family);
            settingsPrefEditor.putBoolean(Config.IS_LOGIN, true);
            settingsPrefEditor.commit();

            Intent i = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(i);
            finish();
        }
    }

    public void getRegCodeOnClick(View v){

        progressBarManager.showProgress((ProgressBar) mProgressView, this);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        registerUser(phoneNumEt.getText().toString());
    }

    public void editNumOnClick(View v){
        loginForm1.setVisibility(View.VISIBLE);
        loginForm2.setVisibility(View.GONE);
        loginForm3.setVisibility(View.GONE);

    }

    public void resendCodeOnClick(View v){
        resetTimer();

        loginForm1.setVisibility(View.GONE);
        loginForm2.setVisibility(View.GONE);
        loginForm3.setVisibility(View.VISIBLE);
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
            Toast.makeText(getBaseContext(), "Go",Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    //service call methods
    public void registerUser(String phoneNumber){
        APIInterface service = RetrofitClientInstance.getRetrofitInstance().create(APIInterface.class);
        Call<LoginSuccessResponse> responseCall = service.loginUser(phoneNumber);
        responseCall.enqueue(new Callback<LoginSuccessResponse>() {
            @Override
            public void onResponse(Call<LoginSuccessResponse> call, Response<LoginSuccessResponse> response) {
                if (response.isSuccessful()){
                    LoginSuccessResponse loginResponse = response.body();
                    Log.i(AppTags.POST_USER_NUMBER_RESPONSE, loginResponse.getMessage());
                    Toast.makeText(getBaseContext(), loginResponse.getMessage(), Toast.LENGTH_SHORT).show();

                    SharedPreferences.Editor settingsPrefEditor = getSharedPreferences(Config.SETTINGS_SHARED_PREF, MODE_PRIVATE).edit();
                    settingsPrefEditor.putString(Config.CLIENT_ID, loginResponse.getClientId());
                    settingsPrefEditor.commit();
                    Toast.makeText(getBaseContext(), loginResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    loginForm1.setVisibility(View.GONE);
                    loginForm2.setVisibility(View.VISIBLE);
                    loginForm3.setVisibility(View.GONE);

                    showPhoneNumTv.setText(phoneNumEt.getText().toString());

                    resetTimer();

                    SharedPreferences settingsPref = getSharedPreferences(Config.SETTINGS_SHARED_PREF, MODE_PRIVATE);
                    String userid = settingsPref.getString(Config.CLIENT_ID, Config.DEFAULT_STRING_NO_THING_FOUND);
                    Toast.makeText(getBaseContext(), userid, Toast.LENGTH_SHORT).show();

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

    public void validateUser(String validationCode){}

}
