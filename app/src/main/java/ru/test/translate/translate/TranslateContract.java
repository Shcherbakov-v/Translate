package ru.test.translate.translate;

import androidx.annotation.Nullable;

import ru.test.translate.Language;
import ru.test.translate.Translation;
import ru.test.translate.translate.Model.OnFinishTranslateListener;

public interface TranslateContract {

    interface View {
        void showTranslation(Translation translation);

        void showSaveTranslation(Translation translation);

        void startTranslation();

        void showErrorTranslate();
    }

    interface Presenter {
        void onTextEntered(String text, Language language, Boolean newEntered);

        void onTranslationReceived(int position);

        void onOldTextReceived(String text, Language language);
    }

    interface Model {
        void loadTranslate(String text, Language language, boolean newTranslate, OnFinishTranslateListener onFinishTranslateListener);

        @Nullable
        Translation loadTranslate(int position);
    }
}
