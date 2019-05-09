package ir.mobasher.app.client.api.validateUser;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ValidationSuccessResponse {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("active")
    @Expose
    private Boolean active;
    @SerializedName("jwtResponse")
    @Expose
    private JwtResponse jwtResponse;
    @SerializedName("walletId")
    @Expose
    private String walletId;
    @SerializedName("clientId")
    @Expose
    private String clientId;
    @SerializedName("registerationcomplete")
    @Expose
    private Boolean registerationcomplete;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public JwtResponse getJwtResponse() {
        return jwtResponse;
    }

    public void setJwtResponse(JwtResponse jwtResponse) {
        this.jwtResponse = jwtResponse;
    }

    public String getWalletId() {
        return walletId;
    }

    public void setWalletId(String walletId) {
        this.walletId = walletId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public Boolean getRegisterationcomplete() {
        return registerationcomplete;
    }

    public void setRegisterationcomplete(Boolean registerationcomplete) {
        this.registerationcomplete = registerationcomplete;
    }
}
