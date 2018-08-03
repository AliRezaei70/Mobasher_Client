package ir.mobasher.app.client.model.users;

import com.j256.ormlite.field.DatabaseField;

/**
 * Created by ALI_REZAEI on 12/24/2017.
 */

public class User {
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField
    private String userName;
    @DatabaseField
    private String password;
    @DatabaseField
    private boolean enabled;
    @DatabaseField
    private boolean remember;

    public int getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isRemember() {
        return remember;
    }

    public void setRemember(boolean remember) {
        this.remember = remember;
    }
}
