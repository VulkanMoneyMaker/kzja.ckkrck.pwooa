package kak.jfskoagk.vlj.network;


import kak.jfskoagk.vlj.network.model.CasinoModel;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiCasino {

    @GET("VUL-63")
    Call<CasinoModel> check();

}
