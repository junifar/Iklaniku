package com.rubahapi.iklaniku;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.TextView;

import com.rubahapi.iklaniku.listener.OnDatePickerClickListener;

import java.util.Calendar;

import static com.rubahapi.iklaniku.R.id.birthday_edit;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener, View.OnFocusChangeListener, OnDatePickerClickListener {

    private TextView birthdayTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        birthdayTextView = (TextView) findViewById(R.id.birthday_edit);
        birthdayTextView.setOnClickListener(this);
        birthdayTextView.setOnFocusChangeListener(this);

        birthdayTextView.setInputType(InputType.TYPE_NULL);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case birthday_edit:
                birthday_edit_OnClick();
                break;
        }
    }

    private void birthday_edit_OnClick() {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
//
//        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
//            @Override
//            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
//                birthdayTextView.setText(dayOfMonth + "-" + (monthOfYear-1) + "-" + year);
//            }
//        },mYear, mMonth, mDay);
//
//        datePickerDialog.show();
        String birthday = birthdayTextView.getText().toString();

        DialogFragment newFragment = new DatePickerFragment();
        Bundle bundle = new Bundle();
        if(!birthday.equals("")){
            bundle.putInt("year", Integer.parseInt(splitString(birthday,2)));
            bundle.putInt("month",Integer.parseInt(splitString(birthday,1)));
            bundle.putInt("day",Integer.parseInt(splitString(birthday,0)));
        }else{
            bundle.putInt("year", mYear);
            bundle.putInt("month",mMonth);
            bundle.putInt("day",mDay);
        }

//        Log.d("RESULT : ", splitString(birthdayTextView.getText().toString(),0) );

//        bundle.putInt("year", 2017);
//        bundle.putInt("month",1);
//        bundle.putInt("day",1);
        newFragment.setArguments(bundle);
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    private String splitString(String value, int retNum){
        String[] result = value.split("-");
        return result[retNum];
    }

    @Override
    public void onFocusChange(View view, boolean b) {
        switch (view.getId()){
            case birthday_edit:
                birthday_edit_OnClick();
                break;
        }
    }

    @Override
    public void onDatePickerClickListener(int year, int month, int date) {
        birthdayTextView.setText(date + "-" + month + "-" + year);
    }
}
