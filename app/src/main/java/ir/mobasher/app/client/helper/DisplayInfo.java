package ir.mobasher.app.client.helper;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;

import ir.mobasher.app.client.core.DatabaseHelper;

public class DisplayInfo {

    private static DisplayInfo displayInfo;
    private int width;
    private int height;

    public static synchronized DisplayInfo getInstance(Activity activity) {
        if (displayInfo == null) {

            displayInfo = new DisplayInfo(activity);
        }

        return displayInfo;
    }

    public DisplayInfo(Activity activity) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        height = displayMetrics.heightPixels;
        width = displayMetrics.widthPixels;
    }

    public int getDisplayWidth(){
        return width;
    }

    public int getDisplayHeight(){
        return height;
    }
}
