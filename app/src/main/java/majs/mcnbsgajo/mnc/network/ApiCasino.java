package majs.mcnbsgajo.mnc.network;


import majs.mcnbsgajo.mnc.network.model.CasinoModel;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiCasino {

    @GET("VUL-87")
    Call<CasinoModel> check();

}
