package com.youngershopping.webapi;

import org.json.JSONObject;

import java.io.IOException;

public interface APICallbackMethod {

    void onSuccess(JSONObject object);

    void onFail(IOException e);
}
