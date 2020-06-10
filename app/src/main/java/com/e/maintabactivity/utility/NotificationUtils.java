package com.e.maintabactivity.utility;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.os.Build;
import android.provider.Settings;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;


import com.e.maintabactivity.MainActivity;
import com.e.maintabactivity.R;
import com.e.maintabactivity.models.AnnouncementModel;
import com.e.maintabactivity.models.AnnouncementNotificationModel;

import java.util.concurrent.atomic.AtomicInteger;

public class NotificationUtils {


    public static final String CHANNEL_ID = "Sca_App";
    public static final CharSequence CHANNEL_NAME = "SCA App";
    public static final String CHANNEL_DESC = "Study Connections Academy Notifications";

    private final static AtomicInteger c = new AtomicInteger(0);

    public static int getNewNotificationID() {
        return c.incrementAndGet();
    }


    public static int displayAnnouncementNotification(Context context, AnnouncementNotificationModel announcementNotificationModel) {

        int notificationId = getNewNotificationID();
        announcementNotificationModel.setNotificationId(notificationId);
        NotificationCompat.Builder mBuilder = getAnnouncementNotificationBuilder(context, announcementNotificationModel);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mBuilder.setChannelId(CHANNEL_ID);
        }
        NotificationManagerCompat.from(context).notify(CHANNEL_ID,notificationId, mBuilder.build());

        return notificationId;
    }

    public static NotificationCompat.Builder getAnnouncementNotificationBuilder(Context context, AnnouncementNotificationModel announcementNotificationModel) {

        Intent notifyIntent = new Intent(context, MainActivity.class);
        notifyIntent.putExtra("AnnouncementModel", announcementNotificationModel.getAnnouncement());

        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);

        PendingIntent notifyPendingIntent = PendingIntent.getActivity(
                context, 0, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT
        );

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context, CHANNEL_ID)
                        .setSmallIcon(R.drawable.logo)
                        .setAutoCancel(true)
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                        .setContentText(announcementNotificationModel.getAnnouncement().getBody())
                        .setContentTitle("Announcement")
                        .setCategory(Notification.CATEGORY_MESSAGE)
                        .setContentIntent(notifyPendingIntent)
                        .setFullScreenIntent(PendingIntent.getService(context, 0, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT), true)
                        .setSound(Settings.System.DEFAULT_NOTIFICATION_URI, AudioManager.STREAM_NOTIFICATION);
        return mBuilder;

    }


    public static void createNotificationChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription(CHANNEL_DESC);
            channel.enableLights(true);
            channel.setLightColor(Color.RED);
            channel.enableVibration(true);
            channel.setSound(Settings.System.DEFAULT_NOTIFICATION_URI,
                    new AudioAttributes.Builder()
                            .setLegacyStreamType(AudioManager.STREAM_NOTIFICATION)
                            .build()
            );

            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }


}
