package ru.test.translate.server;

public class APITranslate {

    private static APIService apiService;

    private APITranslate() {
    }

    static final String BASE_URL = "https://translate.yandex.net/";

    public static synchronized APIService getAPIService() {
        if (apiService == null) {
            apiService = RetrofitClient.getClient().create(APIService.class);
        }
        return apiService;
    }
}
