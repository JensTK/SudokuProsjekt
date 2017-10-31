package jens.sudokuprosjekt;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

/**
 * Created by jenstobiaskaarud on 10/23/17.
 */

public class FilBehandler {
    private int[][] tallene = new int[9][9];
    private boolean[][] disabled = new boolean[9][9];
    private Activity act;
    private final String filNavn = "brett";

    public FilBehandler(Activity act) {
        this.act = act;
    }

    public int[][] getTallene() {
        return tallene;
    }
    public boolean[][] getDisabled() {
        return disabled;
    }
    public void setTallene(int[][] tallene) {
        this.tallene = tallene;
    }
    public void setDisabled(boolean[][] disabled) {
        this.disabled = disabled;
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
    public boolean skrivTilFil(int vansk, String navn) {
        try {
            FileOutputStream fil = act.openFileOutput(filNavn, Context.MODE_APPEND);
            PrintWriter wrt = new PrintWriter(fil);
            wrt.println("<navn>:");
            wrt.println(navn);
            wrt.println("<diff>:");
            wrt.println(vansk);
            for (int[] i : tallene) {
                String s = "";
                for (int j : i) {
                    s += j + ",";
                }
                wrt.println(s);
            }
            wrt.println();
            return true;
        }
        catch (FileNotFoundException e) {
            Log.i("tagg", e.toString());
            return false;
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
