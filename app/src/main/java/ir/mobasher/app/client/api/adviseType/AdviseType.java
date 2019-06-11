package ir.mobasher.app.client.api.adviseType;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AdviseType {

    @SerializedName("data")
    @Expose
    private List<AdviseTypeParents> data = null;

    public List<AdviseTypeParents> getData() {
        return data;
    }

    public void setData(List<AdviseTypeParents> data) {
        this.data = data;
    }

}