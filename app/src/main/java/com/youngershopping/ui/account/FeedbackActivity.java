package com.youngershopping.ui.account;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.youngershopping.BaseApp;
import com.youngershopping.R;
import com.youngershopping.SharPref.SharePref;
import com.youngershopping.adapter.account.FeedbackRatingAdapter;
import com.youngershopping.databinding.ActivityFeedbackBinding;
import com.youngershopping.databinding.ActivityNotificationBinding;
import com.youngershopping.pojo.feedback_item_click;
import com.youngershopping.pojo.feedback_response;
import com.youngershopping.retrofit.APIClient;
import com.youngershopping.retrofit.ApiInterface;
import com.youngershopping.utils.Constants;
import com.youngershopping.utils.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FeedbackActivity extends BaseApp implements View.OnClickListener {
    private ActivityFeedbackBinding binding;
    private Activity activity = FeedbackActivity.this;
    private String TAG = FeedbackActivity.class.getSimpleName();
    private FeedbackRatingAdapter feedbackRatingAdapter;
    private List<String> listFeedback;

    private Calendar c;
    private int hh, mm;
    private String str_StartTime = "", str_EndTime = "";

    List<feedback_item_click> list;

    RadioButton genderradioButton;

    String st_1 = "",st_2 = "",st_3 = "",st_4 = "",st_5 = "",st_6 = "";

    ApiInterface apiInterface;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(activity, R.layout.activity_feedback);
        list = new ArrayList<>();
        apiInterface = APIClient.getClient().create(ApiInterface.class);
        init();
        setlistner();
    }

    private void init() {
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        binding.commanRecyclerview.recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        binding.commanRecyclerview.recyclerView.setNestedScrollingEnabled(false);
        fillData();
    }

    private void setlistner() {
        binding.btnSubmit.setOnClickListener(this);
        binding.txtSelectTime.setOnClickListener(this);
    }

    private void fillData() {
        listFeedback = new ArrayList<>();
        listFeedback.add(getResources().getString(R.string.txt_store_ambience));
        listFeedback.add(getResources().getString(R.string.txt_product_range));
        listFeedback.add(getResources().getString(R.string.txt_product_availability));
        listFeedback.add(getResources().getString(R.string.txt_staff_attitude));
        listFeedback.add(getResources().getString(R.string.txt_product_knowledge));
        listFeedback.add(getResources().getString(R.string.txt_billing_payment));


        for (int i=0;i<listFeedback.size();i++){
            feedback_item_click itemClick =new feedback_item_click();
            itemClick.setTitle(listFeedback.get(i));
            itemClick.setTag("");
            list.add(itemClick);
        }

        Log.d("TAG","Size = "+list.size());
        feedbackRatingAdapter = new FeedbackRatingAdapter(activity, listFeedback,list);
        binding.commanRecyclerview.recyclerView.setAdapter(feedbackRatingAdapter);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txtSelectTime:
                showTimePicker();
                break;
            case R.id.btnSubmit:
                getData();
                break;
        }
    }



    private void getData() {
        for (int i = 0;i<list.size();i++){
            Log.d("TAG"," Poistion = "+i+" Name = "+list.get(i).getTitle()+" Tag = "+list.get(i).getTag());
            if (list.get(i).getTitle().equals("Store ambience/decor/layout")){
                st_1 = list.get(i).getTag();
            }

            if (list.get(i).getTitle().equals("Product range")){
                st_2 = list.get(i).getTag();
            }

            if (list.get(i).getTitle().equals("Product availability")){
                st_3 = list.get(i).getTag();
            }

            if (list.get(i).getTitle().equals("Staff attitude")){
                st_4 = list.get(i).getTag();
            }

            if (list.get(i).getTitle().equals("Product Knowledge of staff")){
                st_5 = list.get(i).getTag();
            }

            if (list.get(i).getTitle().equals("Billing and payment")){
                st_6 = list.get(i).getTag();
            }
        }

        int selectedId = binding.radioGroup.getCheckedRadioButtonId();
        genderradioButton = (RadioButton) findViewById(selectedId);


        if (st_1.equals("")){
            Toast.makeText(FeedbackActivity.this,"Store ambience/decor/layout Nothing selected", Toast.LENGTH_SHORT).show();
        }else if (st_2.equals("")){
            Toast.makeText(FeedbackActivity.this,"Product range Nothing selected", Toast.LENGTH_SHORT).show();
        }else if (st_3.equals("")){
            Toast.makeText(FeedbackActivity.this,"Product availability Nothing selected", Toast.LENGTH_SHORT).show();
        }else if (st_4.equals("")){
            Toast.makeText(FeedbackActivity.this,"Staff attitude Nothing selected", Toast.LENGTH_SHORT).show();
        }else if (st_5.equals("")){
            Toast.makeText(FeedbackActivity.this,"Product Knowledge of staff Nothing selected", Toast.LENGTH_SHORT).show();
        }else if (st_6.equals("")){
            Toast.makeText(FeedbackActivity.this,"Billing and payment Nothing selected", Toast.LENGTH_SHORT).show();
        }else if (binding.edtname.getText().toString().trim().isEmpty()){
            binding.edtname.requestFocus();
            binding.edtname.setError("Name is required");

        }else if (binding.edtinvoicenumber.getText().toString().trim().isEmpty()){
            binding.edtinvoicenumber.requestFocus();
            binding.edtinvoicenumber.setError("Older Id is required");
        }else if (binding.edtnumber.getText().toString().trim().isEmpty()){
            binding.edtnumber.requestFocus();
            binding.edtnumber.setError("Mobile No is required");
        }else if (!isValidMobile(binding.edtnumber.getText().toString().trim())){
            binding.edtnumber.requestFocus();
            binding.edtnumber.setError("Mobile No is minimum 10 digit");
        }else if (binding.edtComment.getText().toString().trim().isEmpty()){
            binding.edtComment.requestFocus();
            binding.edtComment.setError("Comment is required");
        }else if (selectedId==-1){
            Toast.makeText(FeedbackActivity.this,"Best time to reach you Nothing selected", Toast.LENGTH_SHORT).show();
        }else{
            sendDataFeedBack();

        }

    }

    private void sendDataFeedBack() {
        Call<feedback_response> feedbackResponseCall = apiInterface.sendFeedBack(binding.edtinvoicenumber.getText().toString().trim(),
                st_1,st_2,st_3,st_4,st_5,st_6,
                binding.edtname.getText().toString().trim(),
                binding.edtnumber.getText().toString().trim(),
                binding.edtComment.getText().toString().trim(),
                ""+genderradioButton.getText(),
                SharePref.getetLoginId("c_id",getApplicationContext()));
        feedbackResponseCall.enqueue(new Callback<feedback_response>() {
            @Override
            public void onResponse(Call<feedback_response> call, Response<feedback_response> response) {
                feedback_response feedbackResponse = response.body();
                String status = feedbackResponse.getStatus();
                if (status.equals("true")){
                    Toast.makeText(getApplicationContext(),"Feedback Send Successfully",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<feedback_response> call, Throwable t) {
                Log.d("TAG","Error Feedbackb = "+t.getMessage());
            }
        });
    }

    private boolean isValidMobile(String phone) {
        if(!Pattern.matches("[a-zA-Z]+", phone)) {
            return phone.length() > 9 && phone.length() <= 10;
        }
        return false;
    }

    private void showTimePicker() {
        c = Calendar.getInstance();
        hh = c.get(Calendar.HOUR_OF_DAY);
        mm = c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(FeedbackActivity.this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        str_StartTime = String.format("%02d:%02d", selectedHour, selectedMinute);

                        TimePickerDialog timePickerDialog1 = new TimePickerDialog(FeedbackActivity.this,
                                new TimePickerDialog.OnTimeSetListener() {
                                    @Override
                                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                                        str_EndTime = String.format("%02d:%02d", selectedHour, selectedMinute);
//                                        str_EndTime = selectedHour + ":" + selectedMinute;

                                        SimpleDateFormat sdf = new SimpleDateFormat(Constants.Time_Formate);
                                        try {
                                            Date inTime = sdf.parse(str_StartTime);
                                            Date outTime = sdf.parse(str_EndTime);
                                            if (Utils.isTimeAfter(inTime, outTime)) {
                                                binding.txtSelectTime.setText(Utils.ChangeDatePattern(str_StartTime,Constants.Time_Formate,Constants.Time_Formate12) + " - " + Utils.ChangeDatePattern(str_EndTime,Constants.Time_Formate,Constants.Time_Formate12));
                                            } else {
                                                str_StartTime = "";
                                                str_EndTime = "";
                                                showToast(getResources().getString(R.string.txt_select_time));
                                            }
                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                        }

                                    }
                                }, hh, mm, true);
                        timePickerDialog1.show();

                    }
                }, hh, mm, true);

        // Set the Calendar new date as maximum date of date picker
        timePickerDialog.show();
    }
}
