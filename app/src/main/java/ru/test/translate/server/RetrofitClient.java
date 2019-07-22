package ru.test.translate.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.test.translate.DeserializeTranslation;
import ru.test.translate.Translation;

class RetrofitClient {

    private static Retrofit retrofit = null;

    static synchronized Retrofit getClient() {
        if (retrofit == null) {
            DeserializeTranslation deserializeTranslation = new DeserializeTranslation();

            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(Translation.class, deserializeTranslation)
                    .create();

            retrofit = new Retrofit.Builder()
                    .baseUrl(APITranslate.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }
}
