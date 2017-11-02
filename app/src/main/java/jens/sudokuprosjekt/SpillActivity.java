package jens.sudokuprosjekt;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;

/**
 * Created by Jens on 02.11.2017.
 */

public class SpillActivity extends Activity {
    private Button fortsKnapp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button nyKnapp = (Button)findViewById(R.id.nyKnapp);
        nyKnapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment frag = new VanskeligFrag();
                Bundle bndl = new Bundle();
                bndl.putBoolean(MainActivity.lag, false);
                frag.setArguments(bndl);
                frag.show(getFragmentManager(), "");
            }
        });

        fortsKnapp = (Button)findViewById(R.id.fortsKnapp);

        fortsKnapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(".SudokuActivity");
                intent.putExtra(MainActivity.ny, false);
                startActivity(intent);
            }
        });

        Button optKnapp = (Button)findViewById(R.id.optKnapp);
        optKnapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(".OptionsActivity"));
            }
        });

        Button lagKnapp = (Button) findViewById(R.id.lagKnapp);
        lagKnapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(".LagActivity"));
            }
        });

        Button hjelpKnapp = (Button) findViewById(R.id.hjelpKnapp);
        hjelpKnapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] tekst = getResources().getStringArray(R.array.hjelpTekst);
                String txt = tekst[0] + "\n\n" + tekst[1] + "\n\n" + tekst[2];
                new AlertDialog.Builder(SpillActivity.this)
                        .setMessage(txt)
                        .setNegativeButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        })
                        .show();
            }
        });

    }
    @Override
    protected void onResume() {
        Log.i(MainActivity.tagg, "MainActivity.onResume()");
        super.onResume();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        Log.i(MainActivity.tagg, "Rute 0 = " + prefs.getString("0", null));
        if (prefs.getString("0", null) == null) {
            fortsKnapp.setEnabled(false);
            fortsKnapp.setFocusable(false);
        } else {
            fortsKnapp.setEnabled(true);
            fortsKnapp.setFocusable(true);
        }
    }
}
