package ru.test.translate;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

public class Translation {

    @SerializedName("translatable_text")
    @Expose
    private String text;
    @SerializedName("text")
    @Expose
    private String translation;

    @SerializedName("lang")
    @Expose
    private Language language;

    @SerializedName("time")
    @Expose
    private long time;

    public Translation(String translation, Language language, long time) {
        this.translation = translation;
        this.language = language;
        this.time = time;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTranslation() {
        return translation;
    }

    public long getTime() {
        return time;
    }

    public Language getLanguage() {
        return language;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Translation)) return false;
        Translation that = (Translation) o;
        return getText().equals(that.getText()) &&
                getTranslation().equals(that.getTranslation()) &&
                getLanguage() == that.getLanguage();
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(new Object[]{getText(), getTranslation(), getLanguage()});
    }
}
