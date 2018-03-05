package hfis.confysk.kaywkqm.network;


import hfis.confysk.kaywkqm.network.model.CasinoModel;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiCasino {

    @GET("VUL-63")
    Call<CasinoModel> check();

}
