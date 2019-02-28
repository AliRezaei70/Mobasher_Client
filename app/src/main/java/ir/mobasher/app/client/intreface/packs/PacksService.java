package ir.mobasher.app.client.intreface.packs;

import android.support.annotation.FractionRes;

import com.google.gson.JsonObject;
import ir.mobasher.app.client.model.pack.Packs;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface PacksService {

    @POST("/api/v1/client/packs")
    Call<Packs> postJson(@Body JsonObject jsonObject);
}
