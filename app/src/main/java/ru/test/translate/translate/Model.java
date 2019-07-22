package ru.test.translate.translate;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.test.translate.Language;
import ru.test.translate.Translation;
import ru.test.translate.Translations;
import ru.test.translate.server.APITranslate;

public class Model implements TranslateContract.Model {

    static int LAST_TRANSLATE = 0;

    interface OnFinishTranslateListener {
        void onFinishTranslate(Translation translation);

        void onErrorTranslate();
    }

    @Override
    public void loadTranslate(final String text, final Language language, final boolean newTranslate, final OnFinishTranslateListener onFinishTranslateListener) {

        Call<Translation> metadataCall = APITranslate.getAPIService().translate(text, language.getText(),
                "trnsl.1.1.20190718T220200Z.53bd9d48d9d9646c.47bfa2d6b3c5927f14695341c130181b902642f2");

        metadataCall.enqueue(new Callback<Translation>() {
            @Override
            public void onResponse(Call<Translation> call, Response<Translation> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Translation translation = response.body();

                    translation.setText(text);

                    onFinishTranslateListener.onFinishTranslate(translation);

                    List<Translation> translations = Translations.loadData();

                    if (newTranslate || !translations.contains(translation)) {
                        translations.add(0, translation);
                        Translations.saveData(translations);
                    }
                } else {
                    onFinishTranslateListener.onErrorTranslate();
                }
            }

            @Override
            public void onFailure(Call<Translation> call, Throwable t) {
                onFinishTranslateListener.onErrorTranslate();
            }
        });
    }

    @Override
    public Translation loadTranslate(int position) {
        List<Translation> translations = Translations.loadData();
        if (position < translations.size()) {
            return translations.get(position);
        }
        return null;
    }
}
