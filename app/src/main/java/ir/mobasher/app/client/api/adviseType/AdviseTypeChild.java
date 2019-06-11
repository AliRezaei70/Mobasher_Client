package ir.mobasher.app.client.api.adviseType;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AdviseTypeChild {

    @SerializedName("active")
    @Expose
    private Boolean active;
    @SerializedName("adviceTypeId")
    @Expose
    private String adviceTypeId;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getAdviceTypeId() {
        return adviceTypeId;
    }

    public void setAdviceTypeId(String adviceTypeId) {
        this.adviceTypeId = adviceTypeId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}