package jens.sudokuprosjekt;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;

/**
 * Created by Jens on 03.11.2017.
 */

public class BrettManageActivity extends MainActivity {
    private Button slettBrettKnapp;
    private ExpandableListView liste;
    private ExpListeAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brettmanage);

        slettBrettKnapp = findViewById(R.id.slettBrettKnapp);
        liste = findViewById(R.id.brettManageListe);

        final FilBehandler filer = new FilBehandler(this);

        String[] vanskeligheter = getResources().getStringArray(R.array.vanskeligArray);
        String[][] brettNavn = new String[3][];

        for (int i = 0; i < vanskeligheter.length; i++) {
            brettNavn[i] = filer.getNavnene(i, false);
        }

        adapter = new ExpListeAdapter(this, brettNavn, vanskeligheter);
        liste.setAdapter(adapter);

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
                                boolean[][] selected = adapter.getSelected();
                                for (int i = 0; i < selected.length; i++) {
                                    for (int j = 0; j < selected[i].length; j++) {
                                        if (selected[i][j]) {
                                            String navn = (String) adapter.getChild(i, j);
                                            filer.slettBrett(navn);
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
        for (int i = 0; i < adapter.getGroupCount(); i++) {
            liste.expandGroup(i, false);
        }
        enableKnapp();
    }
    public void enableKnapp() {
        boolean[][] selected = adapter.getSelected();
        for (boolean[] b : selected) {
            for (boolean c : b) {
                if (c) {
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
