package ir.mobasher.app.client.intreface;

import java.util.List;

import ir.mobasher.app.client.model.photo.RetroPhoto;
import retrofit2.Call;
import retrofit2.http.GET;

public interface GetDataService {

    @GET("/api/v1/client/packs/111c7528-1d44-4ae3-9dc0-c3b8213d45a6")
    Call<List<RetroPhoto>> getAllPhotos();
}
