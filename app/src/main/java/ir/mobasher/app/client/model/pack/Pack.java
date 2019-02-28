package ir.mobasher.app.client.model.pack;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Pack {

    @SerializedName("active")
    @Expose
    private Boolean active;
    @SerializedName("uid")
    @Expose
    private String uid;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("priceTotal")
    @Expose
    private Integer priceTotal;

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPriceTotal() {
        return priceTotal;
    }

    public void setPriceTotal(Integer priceTotal) {
        this.priceTotal = priceTotal;
    }

}
