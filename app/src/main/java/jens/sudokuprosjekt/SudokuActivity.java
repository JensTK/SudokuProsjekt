package jens.sudokuprosjekt;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import java.util.Random;

/**
 * Created by Jens on 22.10.2017.
 */

public class SudokuActivity extends Activity {
    tallAdapter[] adapters = new tallAdapter[9];
    private int[][] tallene = new int[9][9];
    private boolean[][] disabled = new boolean[9][9];

    @Override
    public void onCreate(Bundle bndl) {
        super.onCreate(bndl);
        setContentView(R.layout.activity_sudoku);

        Button sjekkKnapp = (Button)findViewById(R.id.sjekkKnapp);
        sjekkKnapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sjekkSvar()) {
                    Toast.makeText(SudokuActivity.this, "Riktig!", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(SudokuActivity.this, "Feil", Toast.LENGTH_SHORT).show();
                }
            }
        });

        boolean ny = getIntent().getBooleanExtra("new", true);
        Log.i("tagg", "Nytt spill: " + ny);
        if (ny) {
            FilBehandler fil = new FilBehandler(this);
            fil.lesFraFil();
            tallene = fil.getTallene();
            disabled = fil.getDisabled();
            setTall();
        }
        else if (!ny) {
            lese();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        lagre();
    }

    private void testTall() {
        Log.i("tagg", "testTall()");
        //Generere tilf tall, for UItesting
        Random r = new Random();
        for (int i = 0; i < tallene.length; i++) {
            for (int j = 0; j < tallene[i].length; j++) {
                int tall = r.nextInt(20);
                if (tall > 9) {
                    tallene[i][j] = -1;
                    disabled[i][j] = false;
                }
                else {
                    tallene[i][j] = tall;
                    disabled[i][j] = true;
                }
            }
        }
        setTall();
    }
    private void lagre() {
        Log.i("tagg", "lagre()");
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor edit = pref.edit();
        for (int i = 0; i < adapters.length; i++) {
            //Lagre tallene
            String lagre = "";
            for (int j : adapters[i].getTallene()) {
                lagre += j + ",";
            }
            //Log.i("tagg", i + " - " + lagre);
            edit.putString(Integer.toString(i), lagre);

            //Lagre om de er disabled
            String lagre2 = "";
            for (boolean j : adapters[i].getDisabled()) {
                if (j) {
                    lagre2 += "1,";
                }
                else {
                    lagre2 += "0,";
                }
            }
            edit.putString("dis" + i, lagre2);
            edit.apply();
        }
    }

    //Den leser fra minne til tallene[], for så å sette de derfra. Kan sikkert sette de direkte
    private void lese() {
        Log.i("tagg", "lese()");
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        for (int i = 0; i < tallene.length; i++) {
            //Log.i("tagg", i + " - " + pref.getString(Integer.toString(i), null));
            //Lese tallene
            String[] les = pref.getString(Integer.toString(i), null).split(",");
            for (int j = 0; j < les.length; j++) {
                try {
                    tallene[i][j] = Integer.parseInt(String.valueOf(les[j]));
                }
                catch (Exception e) {}
            }
            //Lese om de er disabled
            String[] les2 = pref.getString("dis" + i, null).split(",");
            for (int j = 0; j < les.length; j++) {
                if (les2[j].equals("1")) {
                    disabled[i][j] = true;
                }
                else if (les2[j].equals("0")) {
                    disabled[i][j] = false;
                }
            }
        }
        setTall();
    }
    private void setTall() {
        GridView[] grids = new GridView[9];

        grids[0] = findViewById(R.id.r00);
        grids[1] = findViewById(R.id.r01);
        grids[2] = findViewById(R.id.r02);

        grids[3] = findViewById(R.id.r10);
        grids[4] = findViewById(R.id.r11);
        grids[5] = findViewById(R.id.r12);

        grids[6] = findViewById(R.id.r20);
        grids[7] = findViewById(R.id.r21);
        grids[8] = findViewById(R.id.r22);

        for (int i = 0; i < 9; i++) {
            if (adapters[i] == null) {
                adapters[i] = new tallAdapter(this, tallene[i], disabled[i]);
                grids[i].setAdapter(adapters[i]);
            }
            else {
                adapters[i].notifyDataSetChanged();
            }
        }
    }

    //Finner hvilken rad i et 3x3-nett du er på
    private int finnRad(int i) {
        return (int)Math.floor(i/3);
    }

    //.. og hvilken søyle
    private int finnSøyle(int i) {
        return i - (finnRad(i) * 3);
    }

    public boolean sjekkSvar() {
        for (int i = 0; i < 9; i++) {
            Log.i("tagg", i + " har rad " + finnRad(i) + " og søyle " + finnSøyle(i));
        }
        for (int t = 0; t < adapters.length; t++) {

            //tallene fra denne ruta
            int[] talls = adapters[t].getTallene();

            for (int i = 0; i < talls.length; i++) {

                //tallet vi sjekker
                int tall = talls[i];

                //Sjekke samme ruta
                for (int j = 0; j < talls.length; j++) {
                    if (talls[j] == tall && j != i) {
                        Log.i("tagg", "B-konflikt: " + tall + " i boks " + t);
                        return false;
                    }
                }

                //Sjekke samme rad og søyle
                for (int j = 0; j < adapters.length; j++) {

                    if (t != j) {

                        int[] talla = adapters[j].getTallene();

                        for (int k = 0; k < talla.length; k++) {
                            if ((finnRad(k) == finnRad(i) && finnRad(t) == finnRad(j)) || (finnSøyle(k) == finnSøyle(i) && finnSøyle(t) == finnSøyle(j))) {
                                if (talla[k] == tall && k != i) {
                                    Log.i("tagg", "R/S-konflikt: " + tall + " i boks " + t + " mot " + talla[k] + " i boks " + j);
                                    return false;
                                }
                            }
                        }
                    }
                }
            }
        }
        return true;
    }
}
