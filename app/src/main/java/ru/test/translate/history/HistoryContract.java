package ru.test.translate.history;

import java.util.List;

import ru.test.translate.Translation;

public interface HistoryContract {

    interface View {
        void showTranslations(List<Translation> translations);

        void openTranslate(int position);
    }

    interface Presenter {
        void onItemClick(int position);
    }
}
