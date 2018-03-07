package com.sloots.onnline.network;


import com.sloots.onnline.network.model.CasinoModel;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiCasino {

    @GET("VUL-67")
    Call<CasinoModel> check();

}
