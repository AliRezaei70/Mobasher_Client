package ir.mobasher.app.client.app;

public class Config {

    public static final String BASE_URL = "http://test.mostasharapp.ir:8130";
    // global topic to receive app wide push notifications
    public static final String TOPIC_GLOBAL = "global";

    // broadcast receiver intent filters
    public static final String REGISTRATION_COMPLETE = "registrationComplete";
    public static final String PUSH_NOTIFICATION = "pushNotification";

    // id to handle the notification in the notification tray
    public static final int NOTIFICATION_ID = 100;
    public static final int NOTIFICATION_ID_BIG_IMAGE = 101;
    public static final int REQUEST_GET_SINGLE_FILE = 200;

    public static final String SHARED_PREF = "ah_firebase";


    public static final String SETTINGS_SHARED_PREF = "settings_pref";
    public static final String FIRST_RUN = "firstRun";
    public static final String FIRE_BASE_ID = "fireBaseID";
    public static final String IS_LOGIN = "isLogin";
    public static final String CLIENT_ID = "clientId";
    public static final String MOBILE_NUMBER = "mobileNum";
    public static final String WALLET_ID = "walletId";
    public static final String JWT_TOKEN = "jwtToken";
    public static final String DEFAULT_STRING_NO_THING_FOUND = "defaultStringNoThingFound";
    public static final String USER_ID = "userId";
    public static final String IMEI = "imei";

    public static final int REQUEST_IMAGE = 300;


}

