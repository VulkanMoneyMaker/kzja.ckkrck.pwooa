package ert.jshgxtixls.hey.requests;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Whatz on 31.01.2018.
 */

public class Request {

    private static final String BASE_URL = " http://digyrew.ru/api/v1/links/swap/";

    public static IApi provideApiModule() {

        return provideRetrofitBuilder().create(IApi.class);
    }



    private static Retrofit provideRetrofitBuilder() {
        return new Retrofit.Builder()
                .addConverterFactory(provideConverterFactory(provideGson()))
                .client(new OkHttpClient())
                .baseUrl(BASE_URL)
                .build();
    }

    private static Converter.Factory provideConverterFactory(Gson gson) {
        return GsonConverterFactory.create(gson);
    }

    private static Gson provideGson() {
        return new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
                .setPrettyPrinting()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .serializeNulls()
                .create();
    }

}
