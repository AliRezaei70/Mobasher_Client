package ir.mobasher.app.client.api.clientProfile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClientProfileSuccessResponse {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;

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

}
