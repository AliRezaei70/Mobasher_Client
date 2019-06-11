package ir.mobasher.app.client.api;

import ir.mobasher.app.client.api.adviseType.AdviseType;
import ir.mobasher.app.client.api.clientProfile.CreateClientProfileSuccessResponse;
import ir.mobasher.app.client.api.clientProfile.GetProfileSuccessResponse;
import ir.mobasher.app.client.api.login.LoginSuccessResponse;
import ir.mobasher.app.client.api.validateUser.ValidationSuccessResponse;
import ir.mobasher.app.client.model.clientProfile.ClientProfile;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
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
    @POST("/api/v1/clients/createprofile")
    Call<CreateClientProfileSuccessResponse> signIn(@Body ClientProfile cpForm);

    //get user profile
    @FormUrlEncoded
    @POST("/api/v1/clients/profile")
    Call<GetProfileSuccessResponse> getProfile(@Field("clientid") String clientId);

    @FormUrlEncoded
    @POST("/api/v1/clients/auth/signout")
    Call<GetProfileSuccessResponse> signOut(@Field("mobilenumber") String mobileNumber);

    @GET("/api/v1/advicetypes/tree")
    Call<AdviseType> getAdviseTypes();
}
