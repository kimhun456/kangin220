package kim.hyunjaee.bicycle.database;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

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

    public void getAllUsers(final CompleteListener listener) {
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d(TAG, "onDataChange");
                List<User> users = new ArrayList<>();
                for (DataSnapshot dataSnap : dataSnapshot.child("users").getChildren()) {
                    User user = dataSnap.getValue(User.class);
                    users.add(user);
                    if (user != null) {
                        Log.d(TAG, "User Name : "
                                + user.getDisplayName()
                                + " token : "
                                + user.getCurrentDeviceToken());
                    }
                }
                listener.onDataChangeComplete(users);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(TAG, "onCancelled");
                listener.onCancelled();
            }
        });
    }

    public interface CompleteListener {
        void onDataChangeComplete(List<User> users);
        void onCancelled();
    }

}
