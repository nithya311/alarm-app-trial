package com.nithya.trialalarm;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;


/**
 * Created by nithya on 10/3/16.
 */
public class RingtoneService extends Service {

    MediaPlayer mediaPlayer;
    boolean isRunning;
    int startId;
    Context context;



    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public int onStartCommand (Intent intent,int flags, int startId){

        Log.e("in ringtone service", "received startid:" + startId + "," + intent);

        String state = intent.getExtras().getString("extra");
        this.context = this;

        assert state != null;
        switch (state) {
            case "alarm on":
                startId = 1;
                Log.e("in ringtone service", "start id = 1");

                break;
            case "alarm off":
                startId = 0;
                Log.e("in ringtone service", "start id = 0");

                break;
            default:
                startId = 0;
                Log.e("in ringtone service", "start id = 1 default");
                break;
        }



        if (!this.isRunning && startId == 1) {

            mediaPlayer = MediaPlayer.create(this, R.raw.chirp);
            mediaPlayer.start();

            this.isRunning = true;
            this.startId = 1;

            Log.e("there is no music", "and u want start");
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

             Intent intent_notify = new Intent(this, NotificationActivity.class);
             PendingIntent pendingIntent_MainActivity = PendingIntent.getActivity(this, 0, intent_notify, PendingIntent.FLAG_UPDATE_CURRENT|PendingIntent.FLAG_ONE_SHOT);



         /**   Intent notificationIntent = new Intent(getApplicationContext(), NotificationActivity.class);
            notificationIntent.putExtra("NotificationMessage", "click");
            notificationIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingNotificationIntent = PendingIntent.getActivity(getApplicationContext(), 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            Notification notification = null;
            notification.flags |= Notification.FLAG_AUTO_CANCEL;
            notification.setLatestEventInfo(getApplicationContext(), "alarm ringing", "click", pendingNotificationIntent);

          **/
           Notification notification = new Notification.Builder(this)
                    .setContentTitle("alarm ringing")
                    .setContentText("click me!")
                    .setContentIntent(pendingIntent_MainActivity)
                    .setSmallIcon(R.drawable.notification_icon)
                    .setAutoCancel(true)
                    .build();

            notificationManager.notify(0, notification);
            Log.e("in ringtone", "notified");


        } else if (this.isRunning && startId == 0) {

            Log.e("there is music", "and u want end");
            mediaPlayer.stop();
            mediaPlayer.reset();
            Log.e("in ringtone service", "music stopped n reset");
            this.isRunning = false;
            this.startId = 0;
            Log.e("in ringtone service", "parameters set");
        } else if (!this.isRunning && startId == 0) {

            this.isRunning = false;
            this.startId = 0;
            Log.e("there is no music", "and u want end");

        } else if (this.isRunning && startId == 1) {

            Log.e("there is music", "and u want start");
            this.isRunning = true;
            this.startId = 1;
            mediaPlayer.start();

            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

            Intent intent_notify = new Intent(this, NotificationActivity.class);
            PendingIntent pendingIntent_MainActivity = PendingIntent.getActivity(this, 0, intent_notify, PendingIntent.FLAG_UPDATE_CURRENT|PendingIntent.FLAG_ONE_SHOT);


            Notification notification = new Notification.Builder(this)
                    .setContentTitle("alarm ringing")
                    .setContentText("click me!")
                    .setContentIntent(pendingIntent_MainActivity)
                    .setSmallIcon(R.drawable.notification_icon)
                    .setAutoCancel(true)
                    .build();

            notificationManager.notify(0, notification);


        } else {

            Log.e("somehow you reached", "else");
        }



        return START_NOT_STICKY;
    }


    @Override
    public  void onDestroy()
    {

        super.onDestroy();
        this.isRunning = false;
        this.startId = 0;
    }


  /**  final  int REFRESH=0;
    Context context;


    @Override
    public void onCreate() {
        super.onCreate();
        context=this;
        //==============================================


        TimerTask refresher;
        // Initialization code in onCreate or similar:
        Timer timer = new Timer();
        refresher = new TimerTask() {
            public void run() {
                handler.sendEmptyMessage(0);
                onStartCommand(PendingIntentmethod(), 0, 1);
            };
        };

        // first event immediately,  following after 1 seconds each
        timer.scheduleAtFixedRate(refresher, 0, MainActivity.seconds);
        //=======================================================

    }

    final Handler handler = new Handler() {


        public void handleMessage(Message msg) {
            switch (msg.what) {
                case REFRESH:
                    PendingIntentmethod();


                    break;
                default:
                    break;
            }
        }
    };

         Intent PendingIntentmethod()
         {
         Intent myIntent = new Intent(context, MainActivity.class);
             PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, myIntent, 0);
         AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();

             return myIntent;

         }

**/


}





