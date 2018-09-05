package ir.mobasher.app.client.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.sql.SQLException;
import java.util.List;

import ir.mobasher.app.client.core.DatabaseHelper;
import ir.mobasher.app.client.model.users.User;

public class LauncherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            List<User> user = DatabaseHelper.getInstance(getApplicationContext()).getUsersDao().queryForAll();
            if(user.size() == 0 || user.get(0).isLogin() == false){
                startActivity(new Intent(this, LoginActivity.class));
            }else {
                startActivity(new Intent(this, MainActivity.class));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }



        finish();
    }
}
