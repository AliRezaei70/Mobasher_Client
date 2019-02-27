package ir.mobasher.app.client.intreface;

import java.util.List;

import ir.mobasher.app.client.model.example.Example;
import retrofit2.Call;
import retrofit2.http.GET;

public interface GetExampleService {
    @GET("/posts/42")
    Call<Example> getExample();
}
