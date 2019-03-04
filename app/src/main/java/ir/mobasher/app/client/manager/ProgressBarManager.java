package ir.mobasher.app.client.manager;

import android.app.Activity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;

public class ProgressBarManager {

    public void showProgress(ProgressBar progressBar, Activity activity){
        progressBar.setVisibility(View.VISIBLE);

        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    public void hideProgress(ProgressBar progressBar, Activity activity){
        progressBar.setVisibility(View.GONE);

        activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }
}
