package jens.sudokuprosjekt;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileReader;
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

    public void lesFraFil() {
        try {
            BufferedReader les = new BufferedReader(new InputStreamReader(act.getResources().openRawResource(R.raw.presets)));
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
