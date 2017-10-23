package jens.sudokuprosjekt;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

/**
 * Created by jenstobiaskaarud on 10/23/17.
 */

public class OptionsActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        Button slettKnapp = (Button)findViewById(R.id.slettKnapp);
        slettKnapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(OptionsActivity.this);
                SharedPreferences.Editor edit = prefs.edit();
                for (int i = 0; i < 9; i++) {
                    edit.remove(Integer.toString(i));
                }
                edit.apply();
            }
        });
    }
}
