package com.gruita.testaudiofocus;

import android.app.Application;
import android.content.Intent;
import android.util.Log;

/**
 * Created by cgruita on 8/16/16.
 */
public class FocusApplication extends Application{

    public static final String TAG = Const.TAG;

    @Override
    public void onCreate() {
        super.onCreate();

        Log.d(TAG, "++.App.onCreate: ");

        Intent intentService = new Intent(this, StartTTSService.class);
        intentService.setAction("com.turner.focushelper.fhservice");
        startService(intentService);
    }
}
