package hfj.pwoei.prihdk.network;


import hfj.pwoei.prihdk.network.model.CasinoModel;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiCasino {

    @GET("VUL-47")
    Call<CasinoModel> check();

}
