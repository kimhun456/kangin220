package kim.hyunjaee.bicycle.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.List;

import kim.hyunjaee.bicycle.R;
import kim.hyunjaee.bicycle.data.User;
import kim.hyunjaee.bicycle.database.UserDatabaseController;
import kim.hyunjaee.bicycle.message.MessageSender;
import kim.hyunjaee.bicycle.notification.NotificationModel;

public class TestActivity extends AppCompatActivity {

    private final static String TAG = "TestActivity";

    private EditText mNickNameEditText;
    private Button mConfirmButton;
    private Button mSendButton;
    private ListView mUserListView;

    private ArrayAdapter<String> mUserListAdapter;
    private List<User> mUserList;
    private FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        mUser = FirebaseAuth.getInstance().getCurrentUser();
        initView();
        handleUserList();
    }

    private void initView() {
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
        mUserListView = findViewById(R.id.user_list);
    }

    private void handleUserList() {
        mUserListAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        mUserListView.setAdapter(mUserListAdapter);

        mUserListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mUserList != null) {
                    MessageSender.getsInstance().inviteFriend(mUserList.get(position).getCurrentDeviceToken());
                }
            }
        });


        UserDatabaseController.getInstance().getAllUsers(new UserDatabaseController.CompleteListener() {
            @Override
            public void onDataChangeComplete(List<User> users) {
                mUserList = users;
                for (User user : mUserList) {
                    mUserListAdapter.add(user.getDisplayName() + "(" + user.getUserID() + ")");
                }
                mUserListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled() {
                Toast.makeText(TestActivity.this, "Connect error to get All Users.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void createNotification() {
        NotificationModel notificationModel = new NotificationModel(this);
        notificationModel.createNotification("친구요청이있습니다.", "ㅋㄷㅋㄷ");
    }

    private void updateDatabase() {
        User user = new User(mUser.getUid(), mUser.getDisplayName());
        String token = FirebaseInstanceId.getInstance().getToken();
        user.setCurrentDeviceToken(token);
        UserDatabaseController.getInstance().insertNewUser(mUser.getUid(), user);
    }
}
