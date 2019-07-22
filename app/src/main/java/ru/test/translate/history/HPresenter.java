package ru.test.translate.history;

import androidx.annotation.NonNull;

import java.util.List;

import ru.test.translate.Translation;
import ru.test.translate.Translations;

public class HPresenter implements HistoryContract.Presenter {

    private HistoryContract.View view;

    HPresenter(@NonNull HistoryContract.View view) {
        this.view = view;

        List<Translation> translations = Translations.loadData();
        view.showTranslations(translations);
    }

    @Override
    public void onItemClick(int position) {
        view.openTranslate(position);
    }
}
