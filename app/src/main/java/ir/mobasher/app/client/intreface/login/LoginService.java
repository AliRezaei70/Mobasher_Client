package ir.mobasher.app.client.intreface.login;

import com.google.gson.JsonObject;

import ir.mobasher.app.client.model.login.Login;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface LoginService {
    @FormUrlEncoded
    @POST("/api/v1/auth/login?phoneNumber={phoneNum}")
    Call<Login> postNumber(@Path("phoneNum") String phoneNum);
}
