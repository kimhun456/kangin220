package kim.hyunjaee.bicycle.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

import kim.hyunjaee.bicycle.R;
import kim.hyunjaee.bicycle.notification.NotificationModel;

public class SignInActivity extends AppCompatActivity {

    private final static String TAG = "SignInActivity";

    private EditText mNickNameEditText;
    private Button mConfirmButton;
    private Button mSendButton;
    private FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        mUser = FirebaseAuth.getInstance().getCurrentUser();

        mNickNameEditText = findViewById(R.id.user_name_edit);
        mConfirmButton = findViewById(R.id.confirm_button);
        mConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUserName();
            }
        });


        mSendButton = findViewById(R.id.send_message_button);
        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNotification();
            }
        });
    }

    private void createNotification(){

        NotificationModel notificationModel =  new NotificationModel(this);
        notificationModel.createNotification("친구요청이있습니다.", "ㅋㄷㅋㄷ");
    }

    private void updateUserName() {
        String userName;
        userName = mNickNameEditText.getText().toString();

        String token = FirebaseInstanceId.getInstance().getToken();

        if (TextUtils.isEmpty(userName)) {
            Toast.makeText(this, "EMPTY!", Toast.LENGTH_LONG).show();
            return;
        }

        if (mUser == null) {
            Log.e(TAG, "User is NULL");
            return;
        }

        mNickNameEditText.setText("");

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");
        myRef.setValue(userName);

        if (!TextUtils.isEmpty(token)) {
            Log.d(TAG, "FCM token : " + token);
            DatabaseReference device = database.getReference("token");
            device.setValue(token);
        }

    }
}
