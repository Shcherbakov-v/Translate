package ru.test.translate.history;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.test.translate.R;
import ru.test.translate.Translation;
import ru.test.translate.translate.TranslateActivity;

public class TranslationHistory extends AppCompatActivity implements HistoryContract.View {

    @BindView(R.id.rvTranslations)
    RecyclerView rvTranslations;

    @BindView(android.R.id.content)
    View content;

    private HistoryContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translation_history);
        ButterKnife.bind(this);

        presenter = new HPresenter(this);
    }

    @Override
    public void showTranslations(List<Translation> translations) {
        rvTranslations.setAdapter(new HistoryAdapter(translations, position -> presenter.onItemClick(position)));
    }

    @Override
    public void openTranslate(int position) {
        startActivity(new Intent(TranslationHistory.this, TranslateActivity.class)
                .putExtra("positionTranslate", position));
    }
}
