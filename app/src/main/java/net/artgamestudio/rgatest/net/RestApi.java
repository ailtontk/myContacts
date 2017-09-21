package net.artgamestudio.rgatest.net;

import net.artgamestudio.rgatest.data.pojo.Contact;
import net.artgamestudio.rgatest.util.Param;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

/**
 * Created by ailtonramos on 21/09/2017.<br>
 *
 * Web services interfaces will be put here.
 */
public class RestApi {

    private static Services services;

    /**
     * Retrofit instance for communication
     */
    public static Retrofit getRetrofitService() {
        return new Retrofit.Builder()
                .baseUrl(Param.Net.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static Services getServicesInstance() {
        if (services == null) {
            services = getRetrofitService().create(Services.class);
        }
        return services;
    }

    public static <T>T callApi(Call<T> call) throws Exception {
        Response response = call.execute();
        return (T)response.body();
    }

    /**
     * All services relationated with contact will be placed here.
     */
    public interface Services {

        @GET("content.json")
        Call<List<Contact>> getContacts();
    }
}
