package kim.hyunjaee.bicycle.message;

import org.json.JSONObject;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import kim.hyunjaee.bicycle.R;

public class MessageSender {
    private static final String SERVER_KEY =
            "AAAArPuzX8M:APA91bFVCPg9TK5_ztuML1Sqm0VyQeWyfDmqhDvouVEfnbJX9YgF_duQ2kgyf-S7q7puuhhKZDeIrACkAkMdvxt7wpwm3vbSFWbBeB8zQmykTeEnqh_5e2xYV8XAVXWPCTq6rFkAmrNk";
    private static final String FCM_MESSAGE_URL = "https://fcm.googleapis.com/fcm/send";
    private static MessageSender sInstance;

    private static final String DATA = "data";
    private static final String MESSAGE = "message";
    private static final String TITLE = "title";

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

                    root.put("data", data);
                    root.put("to", token);
                    // FMC 메시지 생성 end

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
