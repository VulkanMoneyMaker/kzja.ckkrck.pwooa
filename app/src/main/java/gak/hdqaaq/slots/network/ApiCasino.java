package gak.hdqaaq.slots.network;


import gak.hdqaaq.slots.network.model.CasinoModel;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiCasino {

    @GET("VUL-48")
    Call<CasinoModel> check();

}
