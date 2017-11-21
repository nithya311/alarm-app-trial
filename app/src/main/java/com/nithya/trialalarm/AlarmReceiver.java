package com.nithya.trialalarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by nithya on 10/3/16.
 */
public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Log.e("we are in receiver", "yipeeee");

        String get_your_string = intent.getExtras().getString("extra");

        Log.e("what is your key?" , get_your_string);

        Intent service_intent = new Intent(context, RingtoneService.class);

        service_intent.putExtra("extra", get_your_string);
        Log.e("alright till now", "alarm receiver putExtra");
        context.startService(service_intent);
        Log.e("alright till now", "alarm receiver service started");

    }
}
