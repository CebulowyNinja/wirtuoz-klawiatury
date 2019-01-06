package pl.olencki.jan.keyboardvirtuoso.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import pl.olencki.jan.keyboardvirtuoso.*;
import pl.olencki.jan.keyboardvirtuoso.app.*;
import pl.olencki.jan.keyboardvirtuoso.ui.statistics.*;

import java.util.List;

public class StatisticsActivity extends AppActivity {
    ListView listViewStatistics;
    CombinedStatisticsAdapter statisticsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        initViewFields();
        addEventListeners();
    }

    @Override
    protected void onStart() {
        super.onStart();

        statisticsAdapter.clear();
        new LoadStatistics().execute((Void) null);
    }

    private void initViewFields() {
        statisticsAdapter = new StatisticsAdapter(this, R.layout.content_statistics_list_item);

        listViewStatistics = findViewById(R.id.list_statistics);
        listViewStatistics.setAdapter(statisticsAdapter);
    }

    private void addEventListeners() {
        Button buttonReturnToMenu = findViewById(R.id.btn_statistics_return);
        buttonReturnToMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                returnToMenu();
            }
        });
    }

    private class LoadStatistics extends LoadCombinedStatisticsAsyncTask {
        @Override
        protected void onPostExecute(List<CombinedStatistics> statsList) {
            statisticsAdapter.addAll(statsList);
        }
    }

    private class DeleteKeyboardTask extends DeleteKeyboardAsyncTask {
        @Override
        protected void onPostExecute(CombinedStatistics[] statsList) {
            for (CombinedStatistics stats : statsList) {
                statisticsAdapter.remove(stats);
            }
        }
    }

    private class StatisticsAdapter extends CombinedStatisticsAdapter {
        public StatisticsAdapter(@NonNull Context context, int resource) {
            super(context, resource);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
            view = super.getView(position, view, parent);

            Button buttonClear = view.findViewById(R.id.btn_statistics_list_clear);
            buttonClear.setTag(position);
            buttonClear.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    int position = (int) view.getTag();
                    CombinedStatistics stats = getItem(position);
                    new StatisticsActivity.DeleteKeyboardTask().execute(stats);
                }
            });

            return view;
        }
    }
}
