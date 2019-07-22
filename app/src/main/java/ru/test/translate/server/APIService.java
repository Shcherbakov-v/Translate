package ru.test.translate.server;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Query;
import ru.test.translate.Translation;

public interface APIService {

    @POST("api/v1.5/tr.json/translate")
    @FormUrlEncoded
    Call<Translation> translate(@Field("text") String text, @Query("lang") String lang, @Query("key") String key);
}
