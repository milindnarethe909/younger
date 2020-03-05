package com.youngershopping.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ExpandableListView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatImageView;

import com.youngershopping.R;
import com.youngershopping.adapter.home.category.expandable.CategoryExpandableListAdapter;
import com.youngershopping.view.transitionalimageview.TransitionalImageView;
import com.youngershopping.view.transitionalimageview.model.TransitionalImage;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
    public static boolean isTimeAfter(Date startTime, Date endTime) {
        if (endTime.before(startTime)) { //Same way you can check with after() method also.
            return false;
        } else {
            return true;
        }
    }
    public static String ChangeDatePattern(String str_date, String inputPattern, String outputPattern) {
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(str_date);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

    public static void setListViewHeight(ExpandableListView listView,
                                         int group) {
        CategoryExpandableListAdapter listAdapter = (CategoryExpandableListAdapter) listView.getExpandableListAdapter();
        int totalHeight = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(),
                View.MeasureSpec.EXACTLY);
        for (int i = 0; i < listAdapter.getGroupCount(); i++) {
            View groupItem = listAdapter.getGroupView(i, false, null, listView);
            groupItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);

            totalHeight += groupItem.getMeasuredHeight();

            if (((listView.isGroupExpanded(i)) && (i != group))
                    || ((!listView.isGroupExpanded(i)) && (i == group))) {
                for (int j = 0; j < listAdapter.getChildrenCount(i); j++) {
                    View listItem = listAdapter.getChildView(i, j, false, null,
                            listView);
                    listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);

                    totalHeight += listItem.getMeasuredHeight();

                }
            }
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        int height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getGroupCount() - 1));
        if (height < 10)
            height = 200;
        params.height = height;
        listView.setLayoutParams(params);
        listView.requestLayout();

    }

    public static boolean isStatusTrue(String status, String orderStatus) {
        return status.equalsIgnoreCase(orderStatus);
    }

    public static void showToast(Context con, String string) {
        Toast.makeText(con, string, Toast.LENGTH_SHORT).show();
    }

    public static void showToast_onThread(final Activity con, final String string) {
        con.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(con, string, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void storeStateOfFloat(SharedPreferences mediaPrefs,
                                         String prefsKeys, float prefsValue) {
        SharedPreferences.Editor prefEditor = mediaPrefs.edit();
        prefEditor.putFloat(prefsKeys, prefsValue);
        prefEditor.commit();
    }

    public static void storeStateOfString(SharedPreferences mediaPrefs,
                                          String prefsKeys, String prefsValue) {
        SharedPreferences.Editor prefEditor = mediaPrefs.edit();
        prefEditor.putString(prefsKeys, prefsValue);
        prefEditor.commit();
    }

    public static void storeStateOfLong(SharedPreferences mediaPrefs,
                                        String prefsKeys, long prefsValue) {
        SharedPreferences.Editor prefEditor = mediaPrefs.edit();
        prefEditor.putLong(prefsKeys, prefsValue);
        prefEditor.commit();
    }

    public static void storeStateOfint(SharedPreferences mediaPrefs,
                                       String prefsKeys, int prefsValue) {
        SharedPreferences.Editor prefEditor = mediaPrefs.edit();
        prefEditor.putInt(prefsKeys, prefsValue);
        prefEditor.commit();
    }

    public static void storeStateOfBoolean(SharedPreferences mediaPrefs,
                                           String prefsKeys, boolean prefsValue) {
        SharedPreferences.Editor prefEditor = mediaPrefs.edit();
        prefEditor.putBoolean(prefsKeys, prefsValue);
        prefEditor.commit();
    }

    public static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static boolean hasConnection(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo wifiNetwork = cm
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiNetwork != null && wifiNetwork.isConnected()) {
            return true;
        }

        NetworkInfo mobileNetwork = cm
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (mobileNetwork != null && mobileNetwork.isConnected()) {
            return true;
        }

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null && activeNetwork.isConnected()) {
            return true;
        }
        return false;
    }

    public static void hideKeyboard(final Activity activity) {
        // Check if no view has focus:
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    activity.getWindow().setSoftInputMode(
                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
//
                    View view = activity.getCurrentFocus();
                    if (view != null) {
                        InputMethodManager inputManager = (InputMethodManager) activity
                                .getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputManager.hideSoftInputFromWindow(view.getWindowToken(),
                                InputMethodManager.HIDE_NOT_ALWAYS);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void TransitionalImageView(final int path,
                                             final TransitionalImageView img, final Activity activity) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {

                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        TransitionalImage transitionalImage = new TransitionalImage.Builder()
                                .duration(Constants.TransitionImageAnimation)
                                .image(path)
                                .create();
                        img.setTransitionalImage(transitionalImage);
//                                bitmap.recycle();
                    }
                });
            }
        });
    }

    public static void TransitionalImageLoader(ImageLoader imageLoader,
                                               DisplayImageOptions options, final String path,
                                               final TransitionalImageView img, final Activity activity) {

        try {
            imageLoader.displayImage(path, img, options, new SimpleImageLoadingListener() {
                @Override
                public void onLoadingStarted(String imageUri, View view) {
                }

                @Override
                public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                    String message = null;
                    switch (failReason.getType()) {
                        case IO_ERROR:
                            message = "Input/Output error";
                            break;
                        case DECODING_ERROR:
                            message = "Image can't be decoded";
                            break;
                        case NETWORK_DENIED:
                            message = "Downloads are denied";
                            break;
                        case OUT_OF_MEMORY:
                            message = "Out Of Memory error";
                            break;
                        case UNKNOWN:
                            message = "Unknown error";
                            break;
                    }
                }

                @Override
                public void onLoadingComplete(String imageUri, View view, final Bitmap bitmap) {
                    if (bitmap != null) {
                        AsyncTask.execute(new Runnable() {
                            @Override
                            public void run() {
                                activity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        TransitionalImage transitionalImage = new TransitionalImage.Builder()
                                                .duration(Constants.TransitionImageAnimation)
                                                .image(bitmap)
                                                .create();
                                        img.setTransitionalImage(transitionalImage);
                                        bitmap.recycle();
                                    }
                                });
                            }
                        });
                    } else {
                        try {
                            img.setImageResource(R.mipmap.placeholder);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
//            Log.e("ImageLoader: ","Utils: "+e.getMessage());
        }
    }

    public static void ImageLoader(ImageLoader imageLoader,
                                   DisplayImageOptions options, String path,
                                   final AppCompatImageView view_id) {

        try {
            imageLoader.displayImage(path, view_id, options, new SimpleImageLoadingListener() {
                @Override
                public void onLoadingStarted(String imageUri, View view) {
                }

                @Override
                public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                    String message = null;
                    switch (failReason.getType()) {
                        case IO_ERROR:
                            message = "Input/Output error";
                            break;
                        case DECODING_ERROR:
                            message = "Image can't be decoded";
                            break;
                        case NETWORK_DENIED:
                            message = "Downloads are denied";
                            break;
                        case OUT_OF_MEMORY:
                            message = "Out Of Memory error";
                            break;
                        case UNKNOWN:
                            message = "Unknown error";
                            break;
                    }
                }

                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap bitmap) {
                    if (bitmap != null) {
                    } else {
                        try {
                            view_id.setImageResource(R.mipmap.placeholder);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
//            Log.e("ImageLoader: ","Utils: "+e.getMessage());
        }
    }

    public static void ImageLoaderCircular(ImageLoader imageLoader, DisplayImageOptions options, String path,
                                           final AppCompatImageView view_id) {

        try {
            imageLoader.displayImage(path, view_id, options, new SimpleImageLoadingListener() {
                @Override
                public void onLoadingStarted(String imageUri, View view) {
                }

                @Override
                public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                    String message = null;
                    switch (failReason.getType()) {
                        case IO_ERROR:
                            message = "Input/Output error";
                            break;
                        case DECODING_ERROR:
                            message = "Image can't be decoded";
                            break;
                        case NETWORK_DENIED:
                            message = "Downloads are denied";
                            break;
                        case OUT_OF_MEMORY:
                            message = "Out Of Memory error";
                            break;
                        case UNKNOWN:
                            message = "Unknown error";
                            break;
                    }
                }

                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap bitmap) {
                    if (bitmap != null) {
                        view_id.setImageBitmap(getCircularBitmap(bitmap));
                    } else {
                        view_id.setImageResource(R.mipmap.placeholder);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
//            Log.e("ImageLoader: ","Utils: "+e.getMessage());
        }
    }

    public static Bitmap getCircularBitmap(Bitmap bitmap) {
        Bitmap output;

        if (bitmap.getWidth() > bitmap.getHeight()) {
            output = Bitmap.createBitmap(bitmap.getHeight(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        } else {
            output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getWidth(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

        float r = 0;

        if (bitmap.getWidth() > bitmap.getHeight()) {
            r = bitmap.getHeight() / 2;
        } else {
            r = bitmap.getWidth() / 2;
        }

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawCircle(r, r, r, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }

    public static String printKeyHash(Activity context) {
        PackageInfo packageInfo;
        String key = null;
        try {
            //getting application package name, as defined in manifest
            String packageName = context.getApplicationContext().getPackageName();

            //Retriving package info
            packageInfo = context.getPackageManager().getPackageInfo(packageName,
                    PackageManager.GET_SIGNATURES);

            Log.e("PackageName=", context.getApplicationContext().getPackageName());

            for (Signature signature : packageInfo.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                key = new String(Base64.encode(md.digest(), 0));

                // String key = new String(Base64.encodeBytes(md.digest()));
                Log.e("KeyHash=", "jigar: " + key);
            }
        } catch (PackageManager.NameNotFoundException e1) {
            Log.e("Name not found", e1.toString());
        } catch (NoSuchAlgorithmException e) {
            Log.e("No such an algorithm", e.toString());
        } catch (Exception e) {
            Log.e("Exception", e.toString());
        }

        return key;
    }

    public static boolean checkStatus(JSONObject object) {
        boolean status = false;
        try {
            status = object.getBoolean(Constants.param_status);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return status;
    }


}
