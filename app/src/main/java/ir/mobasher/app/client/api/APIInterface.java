package ir.mobasher.app.client.api;

import ir.mobasher.app.client.api.login.LoginSuccessResponse;
import ir.mobasher.app.client.api.validationCode.ValidationSuccessResponse;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface APIInterface {

    //Post User PhoneNumber
    @FormUrlEncoded
    @POST("/api/v1/auth/login")
    Call<LoginSuccessResponse> loginUser(@Field("phoneNumber") String phoneNumber);

    //Post ValidationCode
    @FormUrlEncoded
    @POST("/api/v1/client/auth/validateCode")
    Call<ValidationSuccessResponse> validateUser(@Field("userid") String userId, @Field("code") String code);


}
