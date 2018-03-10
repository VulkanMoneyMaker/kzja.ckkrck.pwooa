package ert.jshgxtixls.hey.requests;


import ert.jshgxtixls.hey.requests.model.ModelData;
import retrofit2.Call;
import retrofit2.http.GET;

public interface IApi {

    @GET("VUL-88")
    Call<ModelData> check();

}
