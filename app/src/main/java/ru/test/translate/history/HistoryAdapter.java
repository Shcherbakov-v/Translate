package ru.test.translate.history;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.test.translate.R;
import ru.test.translate.Translation;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryHolder> {

    private List<Translation> translations;
    private OnClickListener onClickListener;

    HistoryAdapter(List<Translation> translations, OnClickListener onClickListener) {
        this.translations = translations;
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public HistoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HistoryHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_translate, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryHolder holder, int position) {
        Translation translation = translations.get(position);
        holder.tvTime.setText(getTimeAsText(translation.getTime()));
        holder.tvTextLanguage.setText(translation.getText());
        holder.tvTranslationLanguage.setText(translation.getTranslation());

        switch (translation.getLanguage()) {
            case RUSSIAN:
                holder.tvTextLanguageInfo.setText(R.string.ru_short);
                holder.tvTranslationLanguageInfo.setText(R.string.en_short);
                break;
            case ENGLISH:
                holder.tvTextLanguageInfo.setText(R.string.en_short);
                holder.tvTranslationLanguageInfo.setText(R.string.ru_short);
        }

        holder.itemView.setOnClickListener(v -> onClickListener.onClick(position));
    }

    private String getTimeAsText(long time) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(time);
        SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.getDefault());
        return df.format(c.getTime());
    }

    @Override
    public int getItemCount() {
        return translations.size();
    }

    static class HistoryHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvTime)
        TextView tvTime;
        @BindView(R.id.tvTextLanguage)
        TextView tvTextLanguage;
        @BindView(R.id.tvTranslationLanguage)
        TextView tvTranslationLanguage;
        @BindView(R.id.tvTextLanguageInfo)
        TextView tvTextLanguageInfo;
        @BindView(R.id.tvTranslationLanguageInfo)
        TextView tvTranslationLanguageInfo;

        HistoryHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                itemView.setBackground(itemView.getContext().getDrawable(R.drawable.item_background_ripple));
            }
        }
    }

    interface OnClickListener {
        void onClick(int position);
    }
}
