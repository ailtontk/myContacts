package net.artgamestudio.rgatest.net;

import net.artgamestudio.rgatest.util.Param;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

/**
 * Created by ailtonramos on 21/09/2017.<br>
 *
 * Web services interfaces will be put here.
 */
public class RestApi {

    /**
     * Retrofit instance for communication
     */
    public static Retrofit getRetrofitService() {
        return new Retrofit.Builder()
                .baseUrl(Param.Net.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public interface ContactServices {


    }
}
