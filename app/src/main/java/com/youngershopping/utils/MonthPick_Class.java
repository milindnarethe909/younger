package com.youngershopping.utils;

import android.util.Log;

public class MonthPick_Class {


    public static String Months(String m1){
        int m = Integer.parseInt(m1);
        String month = "";
        switch (m){
            case 1:
                Log.d("TAG","Month 1 "+m);
                month = "Jan";
                break;
            case 2:
                Log.d("TAG","Month 2 "+m);
                month =  "Feb";
            break;
            case 3:
                Log.d("TAG","Month  3 "+m);
                month =  "Mar";
            break;
            case 4:
                Log.d("TAG","Month 4 "+m);
                month =  "April";
            break;
            case 5:
                Log.d("TAG","Month  5 "+m);
                month =  "May";
            break;
            case 6:
                Log.d("TAG","Month  6 "+m);
                month =  "June";
            break;
            case 7:
                Log.d("TAG","Month  7 "+m);
                month =  "July";
            break;
            case 8:
                Log.d("TAG","Month 8 "+m);
                month =  "Aug";
            break;
            case 9:
                Log.d("TAG","Month  9 "+m);
                month =  "Sep";
            break;
            case 10:
                Log.d("TAG","Month 10 "+m);
                month =  "Oct";
            break;
            case 11:
                Log.d("TAG","Month  11 "+m);
                month =  "Nov";
            break;
            case 12:
                Log.d("TAG","Month 12 "+m);
                month =  "Dec";
            break;
            default:
                Log.d("TAG","Month Def "+m);
        }
        return month;
    }


}
