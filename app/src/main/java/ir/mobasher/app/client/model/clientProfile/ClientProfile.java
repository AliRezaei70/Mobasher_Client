package ir.mobasher.app.client.model.clientProfile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClientProfile {

    @SerializedName("clientId")
    @Expose
    private String clientId;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("firebaseid")
    @Expose
    private String firebaseid;
    @SerializedName("firstName")
    @Expose
    private String firstName;
    @SerializedName("imei")
    @Expose
    private String imei;
    @SerializedName("lastName")
    @Expose
    private String lastName;
    @SerializedName("registerationcomplete")
    @Expose
    private Boolean registerationcomplete;
    @SerializedName("userName")
    @Expose
    private String userName;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirebaseid() {
        return firebaseid;
    }

    public void setFirebaseid(String firebaseid) {
        this.firebaseid = firebaseid;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Boolean getRegisterationcomplete() {
        return registerationcomplete;
    }

    public void setRegisterationcomplete(Boolean registerationcomplete) {
        this.registerationcomplete = registerationcomplete;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}