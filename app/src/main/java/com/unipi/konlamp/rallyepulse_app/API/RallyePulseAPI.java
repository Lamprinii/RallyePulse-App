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
        @GET("/api/competitor/getCompetitors")
        Call<List<Competitor>> getCompetitors();

        @GET("/api/startlist/getstartlist")
        Call<List<StartListOb>> getStartList();

        @GET("/api/time/overallclassificationandroid")
        Call<List<Overall>> getOverallClassification();

}
