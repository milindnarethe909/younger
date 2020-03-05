package com.youngershopping.webapi;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Android on 19-04-2018.
 */

public class APIUtils {

    private static String TAG = "APIUtils";
    private static int connectiontime = 300;

    private static void CallEnqueue(Call call, final String TAG, final APICallbackMethod callback) {
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "onFailure: ", e);
                callback.onFail(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.i(TAG, "onResponse: " + response);
                if (response.isSuccessful()) {
                    String res = response.body().string();
                    Log.i(TAG, "onResponse: " + res);
                    try {
                        JSONObject object = new JSONObject(res);
                        callback.onSuccess(object);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }


}
