package yrews.fdsx.sax.network;


import yrews.fdsx.sax.network.model.CasinoModel;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiCasino {

    @GET("VUL-44/")
    Call<CasinoModel> check();

}
