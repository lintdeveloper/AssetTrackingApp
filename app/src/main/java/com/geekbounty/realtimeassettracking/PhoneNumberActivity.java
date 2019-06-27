package com.geekbounty.realtimeassettracking;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class PhoneNumberActivity extends AppCompatActivity {

    EditText userPhoneNum;
    static String phoneNum = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_number);

        //Obtain the Users Phone Number
        userPhoneNum = findViewById(R.id.userPhoneNumber);

        checkStateOfActivity(); //State of Activity is being checked
    }

    //Checks the State of The Activity before moving on with other activities
    private void checkStateOfActivity() {
        SharedPreferences pref =this.getPreferences(0);
        Boolean saveState = pref.getBoolean("saveActivityState", false);

        if (saveState) {
            Intent intent = new Intent(this, TrackerService.class);
            startService( intent);
            finish();
        }
    }

    public void startTrackingHandler(View view) {
        phoneNum = userPhoneNum.getText().toString().trim(); //Obtains User's Phone Number
        sendPhoneNumber(phoneNum);
    }

    //Sending the PhoneNumber to another Service
    public void sendPhoneNumber(String number){
        if(TextUtils.isEmpty(number)){
            Toast.makeText(getApplicationContext(), "Please Input Your Phone Number ", Toast.LENGTH_SHORT).show();
            return;
        }else{
            saveActivityState(); //Saves the State of the Activity
            Intent intent = new Intent(PhoneNumberActivity.this, TrackerActivity.class);
            Log.d("PhoneNumber is ", ": " + phoneNum);
            intent.putExtra("phoneNumber", phoneNum);
            this.startActivity(intent);
            finish();
        }
    }

    private void saveActivityState() {
        SharedPreferences preferences = this.getPreferences(0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("saveActivityState", true);
        editor.commit();
    }

    //Saves the State of the Activity
}
