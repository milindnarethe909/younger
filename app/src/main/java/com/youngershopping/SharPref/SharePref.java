package com.youngershopping.SharPref;

import android.content.Context;
import android.content.SharedPreferences;

public class SharePref {
    public static final String Young = "young";

    public static void setLoginStatus(String key, String value, Context context){
        SharedPreferences preferences = context.getSharedPreferences(Young,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key,value);
        editor.commit();
    }

    public static String getetLoginStatus(String key, Context context){
        SharedPreferences preferences = context.getSharedPreferences(Young,Context.MODE_PRIVATE);
        String v = preferences.getString(key,"false");
        return v;
    }


    public static void setLoginName(String key, String value, Context context){
        SharedPreferences preferences = context.getSharedPreferences(Young,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key,value);
        editor.commit();
    }

    public static String getetLoginName(String key, Context context){
        SharedPreferences preferences = context.getSharedPreferences(Young,Context.MODE_PRIVATE);
        String v = preferences.getString(key,"");
        return v;
    }


    public static void setLoginEmail(String key, String value, Context context){
        SharedPreferences preferences = context.getSharedPreferences(Young,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key,value);
        editor.commit();
    }

    public static String getetLoginEmail(String key, Context context){
        SharedPreferences preferences = context.getSharedPreferences(Young,Context.MODE_PRIVATE);
        String v = preferences.getString(key,"");
        return v;
    }


    public static void setLoginMob(String key, String value, Context context){
        SharedPreferences preferences = context.getSharedPreferences(Young,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key,value);
        editor.commit();
    }

    public static String getetLoginMob(String key, Context context){
        SharedPreferences preferences = context.getSharedPreferences(Young,Context.MODE_PRIVATE);
        String v = preferences.getString(key,"");
        return v;
    }

    public static void setLoginId(String key, String value, Context context){
        SharedPreferences preferences = context.getSharedPreferences(Young,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key,value);
        editor.commit();
    }

    public static String getetLoginId(String key, Context context){
        SharedPreferences preferences = context.getSharedPreferences(Young,Context.MODE_PRIVATE);
        String v = preferences.getString(key,"");
        return v;
    }

    public static void setLoginphoto(String key, String value, Context context){
        SharedPreferences preferences = context.getSharedPreferences(Young,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key,value);
        editor.commit();
    }

    public static String getetLoginphoto(String key, Context context){
        SharedPreferences preferences = context.getSharedPreferences(Young,Context.MODE_PRIVATE);
        String v = preferences.getString(key,"");
        return v;
    }

    //    zip
    public static void setLoginzip(String key, String value, Context context){
        SharedPreferences preferences = context.getSharedPreferences(Young,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key,value);
        editor.commit();
    }

    public static String getetLoginzip(String key, Context context){
        SharedPreferences preferences = context.getSharedPreferences(Young,Context.MODE_PRIVATE);
        String v = preferences.getString(key,"");
        return v;
    }
    //    residency
    public static void setLoginresidency(String key, String value, Context context){
        SharedPreferences preferences = context.getSharedPreferences(Young,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key,value);
        editor.commit();
    }

    public static String getetLoginresidency(String key, Context context){
        SharedPreferences preferences = context.getSharedPreferences(Young,Context.MODE_PRIVATE);
        String v = preferences.getString(key,"");
        return v;
    }

//    city

    public static void setLogincity(String key, String value, Context context){
        SharedPreferences preferences = context.getSharedPreferences(Young,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key,value);
        editor.commit();
    }

    public static String getetLogincity(String key, Context context){
        SharedPreferences preferences = context.getSharedPreferences(Young,Context.MODE_PRIVATE);
        String v = preferences.getString(key,"");
        return v;
    }

//    address

    public static void setLoginaddress(String key, String value, Context context){
        SharedPreferences preferences = context.getSharedPreferences(Young,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key,value);
        editor.commit();
    }

    public static String getetLoginaddress(String key, Context context){
        SharedPreferences preferences = context.getSharedPreferences(Young,Context.MODE_PRIVATE);
        String v = preferences.getString(key,"");
        return v;
    }

//    district

    public static void setLogindistrict(String key, String value, Context context){
        SharedPreferences preferences = context.getSharedPreferences(Young,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key,value);
        editor.commit();
    }

    public static String getetLogindistrict(String key, Context context){
        SharedPreferences preferences = context.getSharedPreferences(Young,Context.MODE_PRIVATE);
        String v = preferences.getString(key,"");
        return v;
    }
//    wallet

    public static void setLoginwallet(String key, String value, Context context){
        SharedPreferences preferences = context.getSharedPreferences(Young,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key,value);
        editor.commit();
    }

    public static String getetLoginwallet(String key, Context context){
        SharedPreferences preferences = context.getSharedPreferences(Young,Context.MODE_PRIVATE);
        String v = preferences.getString(key,"");
        return v;
    }

//    gendor

    public static void setLogingendor(String key, String value, Context context){
        SharedPreferences preferences = context.getSharedPreferences(Young,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key,value);
        editor.commit();
    }

    public static String getetLogingendor(String key, Context context){
        SharedPreferences preferences = context.getSharedPreferences(Young,Context.MODE_PRIVATE);
        String v = preferences.getString(key,"");
        return v;
    }

//    status

    public static void setLoginstatus(String key, String value, Context context){
        SharedPreferences preferences = context.getSharedPreferences(Young,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key,value);
        editor.commit();
    }

    public static String getetLoginstatus(String key, Context context){
        SharedPreferences preferences = context.getSharedPreferences(Young,Context.MODE_PRIVATE);
        String v = preferences.getString(key,"");
        return v;
    }

    // password

    public static void setPassword(String key, String value, Context context){
        SharedPreferences preferences = context.getSharedPreferences(Young,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key,value);
        editor.commit();
    }

    public static String getPassword(String key, Context context){
        SharedPreferences preferences = context.getSharedPreferences(Young,Context.MODE_PRIVATE);
        String v = preferences.getString(key,"");
        return v;
    }

    public static void setwishlist(String key, String value, Context context){
        SharedPreferences preferences = context.getSharedPreferences(Young,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key,value);
        editor.commit();
    }

    public static String getwishlist(String key, Context context){
        SharedPreferences preferences = context.getSharedPreferences(Young,Context.MODE_PRIVATE);
        String v = preferences.getString(key,"false");
        return v;
    }

    public static void setProductId(String key, String value, Context context){
        SharedPreferences preferences = context.getSharedPreferences(Young,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key,value);
        editor.commit();
    }

    public static String getProductId(String key, Context context){
        SharedPreferences preferences = context.getSharedPreferences(Young,Context.MODE_PRIVATE);
        String v = preferences.getString(key,"");
        return v;
    }


}
