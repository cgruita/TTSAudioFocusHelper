package com.gruita.testaudiofocus;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class MainActivity extends AppCompatActivity  {

    public static final String TAG = Const.TAG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        finish();

    }

    @Override
    protected void onResume() {
        Log.d(TAG, "++.MA.onStop: ");
        super.onResume();
    }
}
