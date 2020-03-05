package com.youngershopping;

import android.app.Application;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by jigar moradiya on 6/17/2017.
 */

public class App extends Application {

    private String TAG = "Application";

    @Override
    public void onCreate() {
        super.onCreate();

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/TxtRegular.ttf") // open sans
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }

}
