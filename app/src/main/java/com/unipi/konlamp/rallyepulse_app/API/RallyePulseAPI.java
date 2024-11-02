package com.unipi.konlamp.rallyepulse_app.API;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;
public interface RallyePulseAPI {

        @GET("/api/competitor/getbypasscode/{passcode}")
        Call<Competitor> getByPasscode(@Path("passcode") String passcode);

        @PUT("/api/competitor")
        Call<Competitor> updatecompetitor(@Body Competitor competitor);


}
