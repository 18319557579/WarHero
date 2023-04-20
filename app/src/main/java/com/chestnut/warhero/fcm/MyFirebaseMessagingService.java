package com.chestnut.warhero.fcm;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
        Log.d("Daisy", "获取到token：" + token);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        Log.d("Daisy", "From：" + remoteMessage.getFrom());

        if (remoteMessage.getData().size() > 0) {
            Log.d("Daisy", "Message data payload: " + remoteMessage.getData());

            if (/* Check if data needs to be processed by long running job */ true) {
                // For long-running tasks (10 seconds or more) use WorkManager.
                Log.d("Daisy", "延迟执行");
            } else {
                // Handle message within 10 seconds
                Log.d("Daisy", "立即执行");
            }

        }

        if (remoteMessage.getNotification() != null) {
            Log.d("Daisy", "Message Notification Body: " + remoteMessage.getNotification().getBody() +
                    " title：" + remoteMessage.getNotification().getTitle());
        }
    }

    @Override
    public void onDeletedMessages() {
        super.onDeletedMessages();
    }
}
