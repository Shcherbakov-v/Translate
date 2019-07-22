package ru.test.translate;

import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Translations {

    static String getText(JsonArray translations) {
        StringBuilder translationSB = new StringBuilder();
        for (JsonElement jsonElement : translations) {
            String string = jsonElement.getAsString();
            translationSB.append(string);
        }
        return new String(translationSB);
    }

    public static void saveData(List<Translation> translations) {
        SharedPreferences sharedPreferences = TranslateApplication.getSharedPreferences();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(translations);
        editor.putString("translations", json);
        editor.apply();
    }

    public static List<Translation> loadData() {
        SharedPreferences sharedPreferences = TranslateApplication.getSharedPreferences();
        String json = sharedPreferences.getString("translations", null);

        List<Translation> translations = null;
        if (json != null) {
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<Translation>>() {}.getType();
            translations = gson.fromJson(json, type);
        }

        if (translations == null) {
            translations = new ArrayList<>();
        }

        return translations;
    }
}
