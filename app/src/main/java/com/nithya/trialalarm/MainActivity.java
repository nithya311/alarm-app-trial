package com.nithya.trialalarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import static java.util.Arrays.asList;


public class MainActivity extends ActionBarActivity {

    Context context;
    AlarmManager alarmManager;
    TextView updateText;
    EditText hourSp;
    PendingIntent pendingIntent;
    static long seconds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.context = this;

        hourSp = (EditText) findViewById(R.id.time_interval_sp);

        final Calendar calendar = Calendar.getInstance();

        final Intent my_intent = new Intent(this.context, AlarmReceiver.class);

        Button startbtn = (Button) findViewById(R.id.startbtn);
        startbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               if(hourSp.getText().toString().trim().length() == 0)
                {
                    Toast.makeText(getApplicationContext(), "select time period", Toast.LENGTH_SHORT).show();
                }
                else {
                   alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

                   updateText = (TextView) findViewById(R.id.update_text);
                   Toast.makeText(getApplicationContext(), "alarm On", Toast.LENGTH_SHORT).show();


                   calendar.set(calendar.HOUR_OF_DAY, Integer.parseInt(String.valueOf(hourSp.getText())) / 60);

                   calendar.set(calendar.MINUTE, Integer.parseInt(String.valueOf(hourSp.getText())) % 60);

                   int hour = Integer.parseInt(String.valueOf(hourSp.getText())) / 60;
                   int minute = Integer.parseInt(String.valueOf(hourSp.getText())) % 60;
                   seconds = (long) (hour * 3600 + minute * 60);

                   String hourString = String.valueOf(hour);
                   String minuteString = String.valueOf(minute);

                   updateText.setText("alarm set: " + hourString + "hours, and" + minuteString + "minutes");

                   my_intent.putExtra("extra", "alarm on");


                   pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, my_intent, PendingIntent.FLAG_UPDATE_CURRENT);

                   // alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);


                   alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), seconds, pendingIntent);

               }}});

        Button stopbtn = (Button) findViewById(R.id.stopbtn);
        stopbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (hourSp.getText().toString().trim().length() == 0) {
                    Toast.makeText(getApplicationContext(), "alarm stop", Toast.LENGTH_SHORT).show();

                } else {
                    updateText.setText("alarm unset");
                    Log.e("alright till now", "main act onclick 1");

                    alarmManager.cancel(pendingIntent);
                    Log.e("alright till now", "main act onclick 2");

                    my_intent.putExtra("extra", "alarm off");
                    Log.e("alright till now", "main act onclick 3");

                    sendBroadcast(my_intent);
                    Log.e("alright till now", "main act onclick 4");

                }}});
    }
}