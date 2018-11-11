package kim.hyunjaee.bicycle.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.iid.FirebaseInstanceId;

import kim.hyunjaee.bicycle.R;
import kim.hyunjaee.bicycle.data.User;
import kim.hyunjaee.bicycle.database.UserDatabaseController;
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
        initView();
    }

    private void initView(){

        mNickNameEditText = findViewById(R.id.user_name_edit);
        mConfirmButton = findViewById(R.id.confirm_button);
        mConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDatabase();
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

    private void updateDatabase() {
        User user = new User(mUser.getUid(), mUser.getDisplayName());
        String token = FirebaseInstanceId.getInstance().getToken();
        user.setCurrentDeviceToken(token);
        UserDatabaseController.getInstance().insertNewUser(mUser.getUid(),user);
    }
}
