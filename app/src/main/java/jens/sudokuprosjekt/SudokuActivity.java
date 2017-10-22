package jens.sudokuprosjekt;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.GridView;
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
        if (bndl == null) {
            testTall();
        }
        else {
            lese();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        lagre();
    }

    private void testTall() {
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
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor edit = pref.edit();
        for (int i = 0; i < adapters.length; i++) {
            //Lagre tallene
            String lagre = "";
            for (int j : adapters[i].getTallene()) {
                lagre += j + ",";
            }
            Log.i("tagg", i + " - " + lagre);
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

    //Den leser fra UI til tallene[], for så å sette de derfra. Kan sikkert sette de direkte
    private void lese() {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        for (int i = 0; i < tallene.length; i++) {
            Log.i("tagg", i + " - " + pref.getString(Integer.toString(i), null));
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
}
