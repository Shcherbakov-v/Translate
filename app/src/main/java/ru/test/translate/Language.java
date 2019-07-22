package ru.test.translate;

import com.google.gson.annotations.SerializedName;

public enum Language {

    @SerializedName("ru-en")
    RUSSIAN("ru-en"),

    @SerializedName("en-ru")
    ENGLISH("en-ru");

    private String text;

    Language(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
