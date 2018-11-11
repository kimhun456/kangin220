package kim.hyunjaee.bicycle.database;

import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import kim.hyunjaee.bicycle.data.User;

public class UserDatabaseController {

    private static final String TAG = "UserDatabaseController";

    private static UserDatabaseController sInstance;
    private DatabaseReference mDatabase;

    public static UserDatabaseController getInstance() {
        if (sInstance == null) {
            sInstance = new UserDatabaseController();
        }
        return sInstance;
    }

    private UserDatabaseController() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public void insertNewUser(String userId, User user) {
        mDatabase.child("users").child(userId).setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "insert New User Success");
            }
        });
    }

}
