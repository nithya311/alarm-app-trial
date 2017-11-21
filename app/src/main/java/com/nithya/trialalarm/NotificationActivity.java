package com.nithya.trialalarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by nithya on 16/3/16.
 */
public class NotificationActivity extends ActionBarActivity {

    Button stop;
    Context context;
    PendingIntent pendingIntent;
    AlarmManager alarmManager;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_notification);
       this.context = this;

       final Intent my_intent = new Intent(this.context, AlarmReceiver.class);

       alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
       pendingIntent = PendingIntent.getBroadcast(NotificationActivity.this, 0, my_intent, PendingIntent.FLAG_UPDATE_CURRENT);

       onNewIntent(getIntent());
       stop = (Button) findViewById(R.id.StopAlarmButton);
       stop.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Toast.makeText(getApplicationContext(), "hi", Toast.LENGTH_SHORT).show();

               alarmManager.cancel(pendingIntent);

               my_intent.putExtra("extra", "alarm off");

               sendBroadcast(my_intent);
           }
       });
   }

   /**
    public void onNewIntent(Intent intent){
        Bundle extras = intent.getExtras();
        if(extras != null){
            if(extras.containsKey("NotificationMessage"))
            {
                setContentView(R.layout.activity_notification);
                // extract the extra-data in the Notification
                String msg = extras.getString("NotificationMessage");

                stop.setText("finally!");
            }
        } **/

}
