package ru.test.translate.translate;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.test.translate.Language;
import ru.test.translate.R;
import ru.test.translate.Translation;
import ru.test.translate.history.TranslationHistory;

public class TranslateActivity extends AppCompatActivity implements TranslateContract.View {

    @BindView(R.id.bStartTranslation)
    ImageButton bStartTranslation;

    @BindView(R.id.tvTextLanguage)
    TextView tvTextLanguage;
    @BindView(R.id.tvTranslationLanguage)
    TextView tvTranslationLanguage;

    @BindView(R.id.etTextInput)
    EditText etTextInput;

    @BindView(R.id.ibReverseTranslation)
    ImageButton ibReverseTranslation;

    @BindView(R.id.tvTranslationOutput)
    TextView tvTranslationOutput;

    @BindView(R.id.pbSend)
    ProgressBar pbSend;

    @BindView(android.R.id.content)
    View content;

    private MenuItem iHistory;

    private TranslateContract.Presenter presenter;

    private String textInput;
    private Language language = Language.RUSSIAN;
    private Language lastTextEnteredLanguage = Language.RUSSIAN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translate);
        ButterKnife.bind(this);
    }

    private boolean isTranslationReceived() {
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("positionTranslate")) {
            int position = intent.getIntExtra("positionTranslate", -1);
            if (position != -1) {
                presenter.onTranslationReceived(position);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.history, menu);
        iHistory = menu.findItem(R.id.iHistory);

        presenter = new Presenter(this, new Model());

        if (textInput != null && !textInput.equals("")) {
            presenter.onOldTextReceived(textInput, lastTextEnteredLanguage);
        }

        if (!isTranslationReceived()) {
            bStartTranslation.setOnClickListener(v -> {
                textInput = etTextInput.getText().toString();

                lastTextEnteredLanguage = language;

                presenter.onTextEntered(textInput, language, true);
            });
            ibReverseTranslation.setOnClickListener(v -> {
                language = language == Language.RUSSIAN ? Language.ENGLISH : Language.RUSSIAN;
                changeLanguage(language);
            });
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.iHistory:
                startActivity(new Intent(this, TranslationHistory.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("textInput", textInput);
        outState.putString("lang", language.getText());
        outState.putString("langLast", lastTextEnteredLanguage.getText());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            String lang = savedInstanceState.getString("lang");
            if (lang != null && lang.equals("en-ru")) {
                language = Language.ENGLISH;
                changeLanguage(language);
            }
            String langLast = savedInstanceState.getString("langLast");
            if (langLast != null && langLast.equals("en-ru")) {
                lastTextEnteredLanguage = Language.ENGLISH;
            }
            textInput = savedInstanceState.getString("textInput");
        }
    }

    @Override
    public void showTranslation(Translation translation) {
        setShowProgress(false);

        tvTranslationOutput.setText(translation.getTranslation());
    }

    @Override
    public void showSaveTranslation(Translation translation) {
        bStartTranslation.setVisibility(View.GONE);
        pbSend.setVisibility(View.GONE);
        ibReverseTranslation.setEnabled(false);
        iHistory.setVisible(false);
        etTextInput.setKeyListener(null);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        changeLanguage(translation.getLanguage());
        etTextInput.setText(translation.getText());
        tvTranslationOutput.setText(translation.getTranslation());
    }

    public void changeLanguage(Language language) {
        this.language = language;

        switch (language) {
            case RUSSIAN:
                tvTextLanguage.setText(R.string.ru);
                tvTranslationLanguage.setText(R.string.en);
                break;
            case ENGLISH:
                tvTextLanguage.setText(R.string.en);
                tvTranslationLanguage.setText(R.string.ru);
        }
    }

    @Override
    public void startTranslation() {
        hideKeyboard();

        setShowProgress(true);
    }

    @Override
    public void showErrorTranslate() {
        setShowProgress(false);
        textInput = null;

        Snackbar.make(content, R.string.not_load_translation, Snackbar.LENGTH_LONG).show();
    }

    public void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = getCurrentFocus();
        if (view == null) {
            view = new View(this);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void setShowProgress(boolean showProgress) {
        if (showProgress) {
            bStartTranslation.setVisibility(View.INVISIBLE);
            pbSend.setVisibility(View.VISIBLE);
        } else {
            bStartTranslation.setVisibility(View.VISIBLE);
            pbSend.setVisibility(View.INVISIBLE);
        }
    }
}
