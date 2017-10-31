package jens.sudokuprosjekt;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by jenstobiaskaarud on 10/23/17.
 */

public class FilBehandler {
    private final static String filNavn = "brett";
    private ArrayList<Brett> brettene;
    private Activity act;

    public FilBehandler(Activity act) {
        this.act = act;
        lesFraFil();
    }

    public ArrayList<Brett> getBrettene() {
        return brettene;
    }
    public ArrayList<Brett> getBrettene(int vansk) {
        ArrayList<Brett> ret = new ArrayList<>();
        for (Brett b : brettene) {
            if (b.getDiff() == vansk) {
                ret.add(b);
            }
        }
        return ret;
    }
    public Brett getBrett(String navn) {
        for (Brett b : brettene) {
            if (b.getNavn().equals(navn)) {
                return b;
            }
        }
        return null;
    }
    public String[] getNavnene(int vansk) {
        ArrayList<Brett> brt = getBrettene(vansk);
        String[] ret = new String[brt.size()];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = brt.get(i).getNavn();
        }
        return ret;
    }
    public void putBrett(Brett brett) {
        brettene.add(brett);
    }

    private void lesFraFil() {
        brettene = new ArrayList<>();
        try {
            BufferedReader read = new BufferedReader(new InputStreamReader(act.openFileInput(filNavn)));
            String linje = "";
            while (linje != null) {
                while (!linje.equals("---")) {
                    linje = read.readLine();
                }
                String navn = read.readLine();
                int diff = Integer.parseInt(read.readLine());
                int[][] tallene = new int[9][9];
                for (int i = 0; i < 9; i++) {
                    tallene[i] = lesTallFraString(read.readLine());
                }
                brettene.add(new Brett(act, tallene, diff, navn));
                linje = read.readLine();
            }
            read.close();
        }
        catch (NullPointerException | IOException e) {
            Log.e(MainActivity.tagg, "lesFraFil(): " + e.toString());
        }
    }

    public boolean skrivTilFil() {
        try {
            FileOutputStream fil = act.openFileOutput(filNavn, Context.MODE_PRIVATE);
            PrintWriter wrt = new PrintWriter(fil);
            for (Brett b : brettene) {
                if (b != null) {
                    wrt.println("---");
                    wrt.println(b.getNavn());
                    wrt.println(b.getDiff());
                    for (tallAdapter i : b.getAdaptere()) {
                        String s = "";
                        for (int j : i.getTallene()) {
                            s += j + ",";
                        }
                        wrt.println(s);
                    }
                    wrt.flush();
                }
                else {
                    Log.e(MainActivity.tagg, "skrivTilFil(): brettet == null");
                }
            }
            wrt.close();
            fil.close();
            return true;
        }
        catch (IOException e) {
            Log.i(MainActivity.tagg, e.toString());
            return false;
        }
    }

    private static int[] lesTallFraString(String linje) {
        String[] les = linje.split(",");
        int[] ret = new int[9];
        for (int j = 0; j < les.length; j++) {
            try {
                ret[j] = Integer.parseInt(String.valueOf(les[j]));
            }
            catch (Exception e) {
                Log.i(MainActivity.tagg, e.toString());
            }
        }
        return ret;
    }
}
