package ir.mobasher.app.client.api.validateUser;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JwtResponse {

    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("type")
    @Expose
    private String type;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}