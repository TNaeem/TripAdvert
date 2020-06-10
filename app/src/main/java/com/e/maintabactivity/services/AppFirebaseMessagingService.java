package com.e.maintabactivity.services;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.e.maintabactivity.apiServises.RetrofitInstance;
import com.e.maintabactivity.apiServises.UserApiInterface;
import com.e.maintabactivity.models.AnnouncementModel;
import com.e.maintabactivity.models.AnnouncementNotificationModel;
import com.e.maintabactivity.models.PersonModel;
import com.e.maintabactivity.utility.NotificationUtils;
import com.e.maintabactivity.utility.UserSharedPreference;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;


import java.io.IOException;

public class AppFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = AppFirebaseMessagingService.class.getSimpleName();
    private Context mContext;
    private AnnouncementNotificationModel mAnnouncementNotificationModel;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        PersonModel mUser = UserSharedPreference.getUser(mContext);

        Log.d(TAG, "onMessageReceived: " );
        if (remoteMessage.getData() != null && mUser != null ) {

            //if (remoteMessage.getData().get("topic").equals(mUser.getType().toString())) {

                AnnouncementModel announcementModel = new AnnouncementModel();
                announcementModel.setBody(remoteMessage.getData().get("body"));
                announcementModel.setTitle(remoteMessage.getData().get("title"));
                announcementModel.setTopic(remoteMessage.getData().get("topic"));
                mAnnouncementNotificationModel = new AnnouncementNotificationModel();
                mAnnouncementNotificationModel.setAnnouncement(announcementModel);

                NotificationUtils.displayAnnouncementNotification(mContext, mAnnouncementNotificationModel);

           // }
        }
    }

    @Override
    public void onDeletedMessages() {
        super.onDeletedMessages();
    }

    @Override
    public void onMessageSent(String s) {
        super.onMessageSent(s);
    }

    @Override
    public void onSendError(String s, Exception e) {
        super.onSendError(s, e);
        Log.d(TAG, "onSendError: " + e);
    }

    @SuppressLint("WrongThread")
    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null) {
            new UpdateFirebaseTokenAsync().execute(token);
        }
    }

    private class UpdateFirebaseTokenAsync extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... strings) {
            //try {
                UserApiInterface service = RetrofitInstance.getRetrofitInstance().create(UserApiInterface.class);

                PersonModel userModel = new PersonModel();
                //userModel.setFirebaseInstanceId(strings[0]);
                userModel.setId(UserSharedPreference.getUser(mContext).getId());

                //service.updateFirebaseInstanceId(userModel).execute();

            //} catch (IOException e) {
                //Log.d(TAG, "doInBackground: " + e);
            //}
            return null;
        }
    }
}
