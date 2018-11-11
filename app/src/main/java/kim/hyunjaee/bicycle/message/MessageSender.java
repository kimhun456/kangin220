package kim.hyunjaee.bicycle.message;

import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONObject;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MessageSender {

    // USE YOUR SERVER KEY!!!!!!!
    private static final String SERVER_KEY =
            "AAAArPuzX8M:APA91bFVCPg9TK5_ztuML1Sqm0VyQeWyfDmqhDvouVEfnbJX9YgF_duQ2kgyf-S7q7puuhhKZDeIrACkAkMdvxt7wpwm3vbSFWbBeB8zQmykTeEnqh_5e2xYV8XAVXWPCTq6rFkAmrNk";
    private static final String FCM_MESSAGE_URL = "https://fcm.googleapis.com/fcm/send";
    private static MessageSender sInstance;

    private static final String DATA = "data";
    private static final String TO = "to";
    private static final String MESSAGE = "message";
    private static final String TITLE = "title";
    private static final String SENDER_ID = "sender_id";

    public static MessageSender getsInstance() {
        if (sInstance == null) {
            sInstance = new MessageSender();
        }
        return sInstance;
    }

    private MessageSender() {

    }

    public void inviteFriend(final String token) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject root = new JSONObject();
                    JSONObject data = new JSONObject();
                    data.put(MESSAGE, "받아줘...");
                    data.put(TITLE, "친구요청이 도착하였습니다.");

                    String senderId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    data.put(SENDER_ID, senderId);
                    root.put(DATA, data);
                    root.put(TO, token);

                    URL Url = new URL(FCM_MESSAGE_URL);
                    HttpURLConnection conn = (HttpURLConnection) Url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);
                    conn.addRequestProperty("Authorization", "key=" + SERVER_KEY);
                    conn.setRequestProperty("Accept", "application/json");
                    conn.setRequestProperty("Content-type", "application/json");
                    OutputStream os = conn.getOutputStream();
                    os.write(root.toString().getBytes("utf-8"));
                    os.flush();
                    conn.getResponseCode();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
