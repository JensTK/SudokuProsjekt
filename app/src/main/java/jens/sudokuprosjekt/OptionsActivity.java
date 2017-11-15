package jens.sudokuprosjekt;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Path;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import java.util.Locale;

/**
 * Created by jenstobiaskaarud on 10/23/17.
 */

public class OptionsActivity extends MainActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        Button slettKnapp = (Button)findViewById(R.id.slettKnapp);
        slettKnapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(OptionsActivity.this)
                        .setMessage(getString(R.string.sikkerMelding))
                        .setPositiveButton(getString(R.string.ja), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                BrettManager.slettFraMinne(OptionsActivity.this);
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

        Button slettAlleKnapp = (Button)findViewById(R.id.slettAlleKnapp);
        slettAlleKnapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(OptionsActivity.this)
                        .setMessage(getString(R.string.sikkerMelding))
                        .setPositiveButton(getString(R.string.ja), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                FilBehandler filer = new FilBehandler(OptionsActivity.this);
                                filer.slettAlle();
                                filer.skrivTilFil();
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

        Button byttSpråkKnapp = (Button)findViewById(R.id.byttSprakKnapp);
        byttSpråkKnapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(OptionsActivity.this)
                        .setItems(getResources().getStringArray(R.array.spraak), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String lokasjon;
                                if (i == 0) {
                                    lokasjon = "no";
                                }
                                else {
                                    lokasjon = "en";
                                }
                                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(OptionsActivity.this);
                                SharedPreferences.Editor editor = prefs.edit();
                                editor.putString("loc", lokasjon);
                                editor.apply();

                                Locale loc = new Locale(lokasjon);
                                Locale.setDefault(loc);
                                Configuration conf = getResources().getConfiguration();
                                conf.locale = loc;
                                getBaseContext().getResources().updateConfiguration(conf, getBaseContext().getResources().getDisplayMetrics());

                                startActivity(new Intent(OptionsActivity.this, SpillActivity.class));
                            }
                        })
                        .show();
            }
        });
    }
}
