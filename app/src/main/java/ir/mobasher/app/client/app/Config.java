package ir.mobasher.app.client.app;

public class Config {

    // global topic to receive app wide push notifications
    public static final String TOPIC_GLOBAL = "global";

    // broadcast receiver intent filters
    public static final String REGISTRATION_COMPLETE = "registrationComplete";
    public static final String PUSH_NOTIFICATION = "pushNotification";

    // id to handle the notification in the notification tray
    public static final int NOTIFICATION_ID = 100;
    public static final int NOTIFICATION_ID_BIG_IMAGE = 101;

    public static final String SHARED_PREF = "ah_firebase";


    public static final String SETTINGS_SHARED_PREF = "settings_pref";
    public static final String FISRT_RUN = "firstRun";
    public static final String IS_LOGIN = "isLogin";
    public static final String USERNAME = "username";
    public static final String NAME = "name";
    public static final String FAMILYNAME = "familyName";
    public static final String PASSWORD = "password";
    public static final String DEFAULT_STRING_NO_THING_FOUND = "defaultStringNoThingFound";
}

