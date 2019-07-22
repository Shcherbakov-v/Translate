package ru.test.translate.translate;

import ru.test.translate.Language;
import ru.test.translate.Translation;

public class Presenter implements TranslateContract.Presenter, Model.OnFinishTranslateListener {

    private TranslateContract.View view;
    private TranslateContract.Model model;

    Presenter(TranslateContract.View view, TranslateContract.Model model) {
        this.view = view;
        this.model = model;
    }

    @Override
    public void onTextEntered(String text, Language language, Boolean newEntered) {
        view.startTranslation();
        model.loadTranslate(text, language, newEntered, this);
    }

    @Override
    public void onTranslationReceived(int position) {
        Translation translation = model.loadTranslate(position);
        if (translation != null) {
            view.showSaveTranslation(translation);
        }
    }

    @Override
    public void onOldTextReceived(String text, Language language) {
        Translation translation = model.loadTranslate(Model.LAST_TRANSLATE);
        if (translation != null && translation.getText().equals(text) && translation.getLanguage() == language) {
            view.showTranslation(translation);
        } else {
            onTextEntered(text, language, false);
        }
    }

    @Override
    public void onFinishTranslate(Translation translation) {
        view.showTranslation(translation);
    }

    @Override
    public void onErrorTranslate() {
        view.showErrorTranslate();
    }
}
