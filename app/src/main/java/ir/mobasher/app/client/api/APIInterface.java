package ir.mobasher.app.client.api;

import ir.mobasher.app.client.api.login.LoginSuccessResponse;
import ir.mobasher.app.client.api.validateUser.ValidationSuccessResponse;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface APIInterface {

    /*user auth web services*/

    //Post User PhoneNumber
    @FormUrlEncoded
    @POST("/api/v1/clients/auth/signin")
    Call<LoginSuccessResponse> loginUser(@Field("mobilenumber") String phoneNumber);

    //Post ValidationCode
    @FormUrlEncoded
    @POST("/api/v1/clients/auth/validationcode")
    Call<ValidationSuccessResponse> validateUser(@Field("clientid") String clientId, @Field("code") String code);

    /*user auth web services*/

}
