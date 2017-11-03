package jens.sudokuprosjekt;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

/**
 * Created by Jens on 03.11.2017.
 */

public class BrettManageActivity extends MainActivity {
    private Button slettBrettKnapp;
    private ListView[] views = new ListView[3];

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brettmanage);

        slettBrettKnapp = findViewById(R.id.slettBrettKnapp);

        final FilBehandler filer = new FilBehandler(this);

        final String[][] navnene = {
                filer.getNavnene(0, false),
                filer.getNavnene(1, false),
                filer.getNavnene(2, false)
        };
        views[0] = findViewById(R.id.lettList);
        views[1] = findViewById(R.id.medList);
        views[2] = findViewById(R.id.vanskList);
        for (int i = 0; i < views.length; i++) {
            views[i].setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
            views[i].setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice, navnene[i]));
            final int j = i;
            views[i].setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int k, long l) {
                    Log.i(MainActivity.tagg, "Item clicked");
                    enableKnapp();
                }
            });
        }

        Button lagKnapp = findViewById(R.id.lagKnapp);
        lagKnapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Brett brettet = new Brett(BrettManageActivity.this);
                BrettManager.lagreTilMinne(BrettManageActivity.this, brettet, false);
                startActivity(new Intent(".LagActivity"));
            }
        });

        slettBrettKnapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(BrettManageActivity.this)
                        .setMessage(getString(R.string.sikkerMelding))
                        .setPositiveButton(getString(R.string.ja), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int z) {
                                final ListView[] vs = views;
                                for (int i = 0; i < vs.length; i++) {
                                    SparseBooleanArray valgt = vs[i].getCheckedItemPositions();
                                    for (int j = 0; j < navnene[i].length; j++) {
                                        if (valgt.get(j)) {
                                            filer.slettBrett(navnene[i][j]);
                                        }
                                    }
                                }
                                filer.skrivTilFil();
                                BrettManageActivity.this.recreate();
                            }
                        })
                        .setNegativeButton(getString(R.string.avbryt), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        })
                        .show();
            }
        });
        enableKnapp();
    }
    private void enableKnapp() {
        for (ListView l : views) {
            SparseBooleanArray valgt = l.getCheckedItemPositions();
            for (int i = 0; i < valgt.size(); i++) {
                if (valgt.get(i)) {
                    slettBrettKnapp.setEnabled(true);
                    slettBrettKnapp.setFocusable(true);
                    return;
                }
            }
        }
        slettBrettKnapp.setEnabled(false);
        slettBrettKnapp.setFocusable(false);
    }
}
