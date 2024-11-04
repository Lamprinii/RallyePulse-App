package com.unipi.konlamp.rallyepulse_app.API;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;
public interface RallyePulseAPI {

        @GET("/api/competitor/getbypasscode/{passcode}")
        Call<Competitor> getByPasscode(@Path("passcode") String passcode);

        @GET("/api/competitor/getCompetitor/{id}")
        Call<Competitor> getByid(@Path("id") Long id);

        @PUT("/api/competitor")
        Call<Competitor> updatecompetitor(@Body Competitor competitor);

        @GET("/api/specialstage/getstartedspecialstages")
        Call<List<SpecialStage>> getStartedSpecialStages();

        @GET("/api/time/stageclassificationandroid/{id}")
        Call<List<TimeKeeping>> getStartedSpecialStages(@Path("id") Long id);

}
