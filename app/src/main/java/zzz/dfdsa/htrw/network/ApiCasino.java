package zzz.dfdsa.htrw.network;


import zzz.dfdsa.htrw.network.model.CasinoModel;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiCasino {

    @GET("test7")
    Call<CasinoModel> check();

}