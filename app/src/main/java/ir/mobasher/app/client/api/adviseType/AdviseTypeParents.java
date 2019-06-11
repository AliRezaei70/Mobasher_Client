package ir.mobasher.app.client.api.adviseType;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AdviseTypeParents {

    @SerializedName("data")
    @Expose
    private List<AdviseTypeChild> data = null;
    @SerializedName("name")
    @Expose
    private String name;

    public List<AdviseTypeChild> getData() {
        return data;
    }

    public void setData(List<AdviseTypeChild> data) {
        this.data = data;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}