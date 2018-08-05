package ir.mobasher.app.client.intreface;

import java.util.List;

import ir.mobasher.app.client.model.photo.RetroPhoto;
import retrofit2.Call;
import retrofit2.http.GET;

public interface GetDataService {

    @GET("/photos")
    Call<List<RetroPhoto>> getAllPhotos();
}
