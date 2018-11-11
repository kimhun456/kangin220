package kim.hyunjaee.bicycle.data;

public class User {
    private String displayName;
    private String userID;
    private String currentDeviceToken;

    public User() {

    }

    public User(String userID, String displayName, String currentDeviceToken) {
        this(userID, displayName);
        setCurrentDeviceToken(currentDeviceToken);
    }

    public User(String userID, String displayName) {
        setDisplayName(displayName);
        setUserID(userID);
        currentDeviceToken = "";
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getCurrentDeviceToken() {
        return currentDeviceToken;
    }

    public void setCurrentDeviceToken(String currentDeviceToken) {
        this.currentDeviceToken = currentDeviceToken;
    }
}
