package kim.hyunjaee.bicycle.service;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

import kim.hyunjaee.bicycle.notification.NotificationModel;

public class FireBaseMessageHandleService extends FirebaseMessagingService {

    private static final String TAG = "FireBaseMessageService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, "onMessageReceived()");
        // Handle data payload of FCM messages.
        Log.d(TAG, "FCM Message Id: " + remoteMessage.getMessageId());
        Log.d(TAG, "FCM Notification Message: " +
                remoteMessage.getNotification());
        Log.d(TAG, "FCM Data Message: " + remoteMessage.getData());

        Map<String, String> data = remoteMessage.getData();
        Log.d(TAG, data.toString());
        NotificationModel notificationModel = new NotificationModel(this);
        notificationModel.createNotification(data.get("title"), data.get("message"));
    }

    @Override
    public void onDeletedMessages() {
        Log.d(TAG, "onDeletedMessages()");
        super.onDeletedMessages();
    }

    @Override
    public void onMessageSent(String s) {
        Log.d(TAG, "onMessageSent()");
        super.onMessageSent(s);
    }

    @Override
    public void onSendError(String s, Exception e) {
        Log.d(TAG, "onSendError()");
        super.onSendError(s, e);
    }
}