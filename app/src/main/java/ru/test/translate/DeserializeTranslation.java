package ru.test.translate;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.Calendar;

public class DeserializeTranslation implements JsonDeserializer<Translation> {
    @Override
    public Translation deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();

        JsonArray textArray = jsonObject.get("text").getAsJsonArray();
        String translationLanguage = Translations.getText(textArray);

        long time = Calendar.getInstance().getTimeInMillis();

        String lang = jsonObject.get("lang").getAsString();
        Language language = lang.equals("ru-en") ? Language.RUSSIAN : Language.ENGLISH;

        return new Translation(
                translationLanguage,
                language,
                time
        );
    }
}
