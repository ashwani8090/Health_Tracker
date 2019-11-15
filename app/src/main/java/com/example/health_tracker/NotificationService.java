package com.example.health_tracker;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.provider.Settings;
import android.support.constraint.solver.widgets.Helper;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class NotificationService extends IntentService {


    private Context context;
    private static final String CHANNEL_ID = "1";

    public NotificationService() {
        super("string");
    }

    public NotificationService(String name) {
        super(name);
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        context=getApplicationContext();
        createNotificationChannel();

    }


    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Health Tracker";
            String description = "Track your health..keep healthy";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);

        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_pulse)
                .setContentTitle("Health Tracker")
                .setContentText("Track your health..keep healthy.")
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                .setVibrate(new long[]{100, 100})
                .setColor(Color.RED)

                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        if (!Helper.isAppRunning(context, "com.example.health_tracker")) {
            Intent intent = new Intent(context, SplashPageActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
            builder.setContentIntent(pendingIntent);

        }
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(1, builder.build());
    }


    public static class Helper {

        public static boolean isAppRunning(final Context context, final String packageName) {
            final ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            final List<ActivityManager.RunningAppProcessInfo> procInfos = activityManager.getRunningAppProcesses();
            if (procInfos != null)
            {
                for (final ActivityManager.RunningAppProcessInfo processInfo : procInfos) {
                    if (processInfo.processName.equals(packageName)) {
                        return true;
                    }
                }
            }
            return false;
        }
    }
}
