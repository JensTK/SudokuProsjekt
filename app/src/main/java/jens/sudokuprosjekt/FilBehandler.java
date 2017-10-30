package jens.sudokuprosjekt;

import android.app.Activity;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by jenstobiaskaarud on 10/23/17.
 */

public class FilBehandler {
    private int[][] tallene = new int[9][9];
    private boolean[][] disabled = new boolean[9][9];
    private Activity act;

    public FilBehandler(Activity act) {
        this.act = act;
    }

    public int[][] getTallene() {
        return tallene;
    }
    public boolean[][] getDisabled() {
        return disabled;
    }

    public void lesFraFil(int vansk) {
        try {
            InputStream is = act.getResources().openRawResource(R.raw.preset_lett);
            if (vansk == 1) {
                is = act.getResources().openRawResource(R.raw.preset_middels);
            }
            else if (vansk == 2) {
                is = act.getResources().openRawResource(R.raw.preset_vanskelig);
            }
            BufferedReader les = new BufferedReader(new InputStreamReader(is));
            String line = les.readLine();
            int i = 0;
            while (line != null && i < 9) {
                tallene[i] = lesTallFraString(line);
                line = les.readLine();
                i++;
            }
            for (int j = 0; j < tallene.length; j++) {
                for (int k = 0; k < tallene[j].length; k++) {
                    if (tallene[j][k] >= 0) {
                        disabled[j][k] = true;
                    }
                }
            }
        }
        catch (Exception e) {
            Log.i("tagg", e.toString());
        }
    }
    private int[] lesTallFraString(String linje) {
        String[] les = linje.split(",");
        int[] ret = new int[9];
        for (int j = 0; j < les.length; j++) {
            try {
                ret[j] = Integer.parseInt(String.valueOf(les[j]));
            }
            catch (Exception e) {
                Log.i("tagg", e.toString());
            }
        }
        return ret;
    }
}
