package com.rubahapi.iklaniku;

import android.app.DatePickerDialog;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import static com.rubahapi.iklaniku.R.id.birthday_edit;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView birthdayTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        birthdayTextView = (TextView) findViewById(R.id.birthday_edit);
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

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                birthdayTextView.setText(dayOfMonth + "-" + (monthOfYear-1) + "-" + year);
            }
        },mYear, mMonth, mDay);

        datePickerDialog.show();
    }
}
