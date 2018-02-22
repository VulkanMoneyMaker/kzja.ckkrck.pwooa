package gak.hafawq.nva.network;


import gak.hafawq.nva.network.model.CasinoModel;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiCasino {

    @GET("VUL-48")
    Call<CasinoModel> check();

}
