package ir.mobasher.app.client.intreface.login;

import com.google.gson.JsonObject;

import ir.mobasher.app.client.model.login.Login;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface LoginService {
    @FormUrlEncoded
    @POST("/api/v1/auth/login")
    Call<Login> postNumber(JsonObject jsonObject);
}
