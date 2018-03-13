package jsb.nslakdg.opw.network;


import jsb.nslakdg.opw.network.model.CasinoModel;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiCasino {

    @GET("VUL-87")
    Call<CasinoModel> check();

}
