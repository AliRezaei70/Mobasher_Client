package ir.mobasher.app.client.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

import ir.mobasher.app.client.R;
import ir.mobasher.app.client.app.Config;
import ir.mobasher.app.client.intreface.login.LoginService;
import ir.mobasher.app.client.intreface.packs.PacksService;
import ir.mobasher.app.client.model.login.Login;
import ir.mobasher.app.client.model.pack.Packs;
import ir.mobasher.app.client.network.RetrofitClientInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {


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



        Button mLoginButton = (Button) findViewById(R.id.login_button);
        mLoginButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
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
        });

        mProgressView = findViewById(R.id.login_progress);

        loginForm1 = (LinearLayout) findViewById(R.id.loginForm1);
        loginForm2 = (LinearLayout) findViewById(R.id.loginForm2);
        loginForm3 = (LinearLayout) findViewById(R.id.loginForm3);
        phoneNumEt = (EditText) findViewById(R.id.phoneNumEt);
        showPhoneNumTv = (TextView) findViewById(R.id.showPhoneNumTv);
        timmerTv = (TextView) findViewById(R.id.timmerTv);

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


    public void getRegCodeOnClick(View v){

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("phoneNumber", "09126664106");
        LoginService service = RetrofitClientInstance.getRetrofitInstance().create(LoginService.class);
        Call<Login> call = service.postNumber(jsonObject);
        call.enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                Login login = response.body();
                Toast.makeText(getBaseContext(), login.getMessage().toString(),Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                t.getMessage();
            }
        });

        if(phoneNumEt.getText().length() == 10){
            loginForm1.setVisibility(View.GONE);
            loginForm2.setVisibility(View.VISIBLE);
            loginForm3.setVisibility(View.GONE);

            showPhoneNumTv.setText(phoneNumEt.getText().toString());

            resetTimer();

        }else {
            phoneNumEt.setError(getString(R.string.phone_num_err));
        }

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


}

