package com.youngershopping.ui.home.ui1.fragment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;

import com.youngershopping.R;
import com.youngershopping.ui.home.ui1.HomeActivity;
import com.youngershopping.utils.Constants;

public class Team_conditionActivity extends AppCompatActivity implements View.OnClickListener {
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_condition);
        getSupportActionBar().hide();
        WebView webview = (WebView) findViewById(R.id.webview);
        webview.loadUrl("file:///android_asset/teams/teams.html");
        ImageView backArrow = (ImageView) findViewById(R.id.backArrow);
        backArrow.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        intent = new Intent(this, HomeActivity.class);
        intent.putExtra(Constants.temp, "3");
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.backArrow:
                intent = new Intent(this, HomeActivity.class);
                intent.putExtra(Constants.temp, "3");
                startActivity(intent);
                break;
        }
    }
}
