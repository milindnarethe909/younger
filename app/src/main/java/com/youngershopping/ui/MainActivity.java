package com.youngershopping.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.youngershopping.BaseApp;
import com.youngershopping.R;
import com.youngershopping.adapter.MainAdapter;
import com.youngershopping.databinding.ActivityMainBinding;
import com.youngershopping.databinding.DialogSupportBinding;
import com.youngershopping.webapi.APIUrls;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseApp implements View.OnClickListener {
    private ActivityMainBinding binding;
    private Activity activity = MainActivity.this;
    private String TAG = MainActivity.class.getSimpleName();
    private MainAdapter mainAdapter;
    private List<String> listMain;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(activity,R.layout.activity_main);
        init();
    }

    private void init() {

        binding.cardHelp.setOnClickListener(this);

        binding.commanRecyclerview.recyclerView.setLayoutManager(new LinearLayoutManager(activity));

        listMain = new ArrayList<>();
        listMain.add(getResources().getString(R.string.page1));
        listMain.add(getResources().getString(R.string.page2));
        listMain.add(getResources().getString(R.string.page3));
        listMain.add(getResources().getString(R.string.page4));
        listMain.add(getResources().getString(R.string.page5));
        listMain.add(getResources().getString(R.string.page6));
        listMain.add(getResources().getString(R.string.page7));
        listMain.add(getResources().getString(R.string.page8));
        listMain.add(getResources().getString(R.string.page9));
        listMain.add(getResources().getString(R.string.page10));
        listMain.add(getResources().getString(R.string.page11));
        listMain.add(getResources().getString(R.string.page12));
        listMain.add(getResources().getString(R.string.page13));
        listMain.add(getResources().getString(R.string.page14));
        listMain.add(getResources().getString(R.string.page15));
        listMain.add(getResources().getString(R.string.page16));
        listMain.add(getResources().getString(R.string.page17));
        listMain.add(getResources().getString(R.string.page18));
        listMain.add(getResources().getString(R.string.page19));
        listMain.add(getResources().getString(R.string.page20));
        listMain.add(getResources().getString(R.string.page21));
        listMain.add(getResources().getString(R.string.page22));
        listMain.add(getResources().getString(R.string.page23));
        listMain.add(getResources().getString(R.string.page24));
        listMain.add(getResources().getString(R.string.page25));
        listMain.add(getResources().getString(R.string.page26));
        listMain.add(getResources().getString(R.string.page27));
        listMain.add(getResources().getString(R.string.page28));

        mainAdapter = new MainAdapter(activity, listMain);
        binding.commanRecyclerview.recyclerView.setAdapter(mainAdapter);
    }


    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.cardHelp:
                showDialog();
                break;
        }
    }

    public AlertDialog showDialog() {

        LayoutInflater flater = this.getLayoutInflater();
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity);
        final DialogSupportBinding alertLayout = DataBindingUtil.inflate(flater, R.layout.dialog_support, null, false);

        dialogBuilder.setView(alertLayout.getRoot());
        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        alertLayout.btnPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        alertLayout.txtNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    PackageManager pm = activity.getPackageManager();
                    pm.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES);
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(APIUrls.WhatsAPi));
                    startActivity(i);
                } catch (PackageManager.NameNotFoundException e) {
                    showToast(getResources().getString(R.string.whatsapp_not_install));
                    e.printStackTrace();
                }
            }
        });
        alertLayout.txtEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL, new String[]{getResources().getText(R.string.email) + ""});
                email.putExtra(Intent.EXTRA_SUBJECT, getResources().getText(R.string.app_name) + "");
                email.putExtra(Intent.EXTRA_TEXT, "");
                email.setType("message/rfc822");
                startActivity(Intent.createChooser(email, getResources().getString(R.string.txt_support_help)));
            }
        });

        alertDialog.setCanceledOnTouchOutside(false);
        return alertDialog;
    }
}
