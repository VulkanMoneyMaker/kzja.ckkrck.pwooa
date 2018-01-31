package kzja.ckkrck.pwooa.network;


import kzja.ckkrck.pwooa.network.model.CasinoModel;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiCasino {

    @GET("test7")
    Call<CasinoModel> check();

}
