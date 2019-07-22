package ru.test.translate;

import android.app.Application;
import android.content.SharedPreferences;

public class TranslateApplication extends Application {

    private static SharedPreferences sharedPreferences;

    @Override
    public void onCreate() {
        super.onCreate();

        sharedPreferences = getSharedPreferences("Translations", MODE_PRIVATE);
    }

    public static synchronized SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }
}
