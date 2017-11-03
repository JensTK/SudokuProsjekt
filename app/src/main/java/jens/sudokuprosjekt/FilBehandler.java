package jens.sudokuprosjekt;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by jenstobiaskaarud on 10/23/17.
 */

public class FilBehandler {
    private final static String filNavn = "brett";
    private ArrayList<Brett> brettene;
    private Activity act;
    private HashMap<String, String> byttDefault = new HashMap<>();

    public FilBehandler(Activity act) {
        this.act = act;
        brettene = new ArrayList<>();

        byttDefault.put("pres-easy", act.getString(R.string.lettPresNavn));
        byttDefault.put("pres-med", act.getString(R.string.medPresNavn));
        byttDefault.put("pres-diff", act.getString(R.string.diffPresNavn));

        try {
            lesFraFil(new BufferedReader(new InputStreamReader(act.getResources().openRawResource(R.raw.preset))), byttDefault);
            lesFraFil(new BufferedReader(new InputStreamReader(act.openFileInput(filNavn))), null);
        }
        catch (IOException e) {
            Log.e(MainActivity.tagg, e.toString());
        }
        Log.i(MainActivity.tagg, toString());
    }

    public ArrayList<Brett> getBrettene() {
        return brettene;
    }

    public ArrayList<Brett> getBrettene(int vansk, boolean defaultBrett) {
        ArrayList<Brett> ret = new ArrayList<>();
        for (Brett b : brettene) {
            if (b.getDiff() == vansk) {
                if (defaultBrett) {
                    ret.add(b);
                }
                else if (!byttDefault.containsValue(b.getNavn())) {
                    ret.add(b);
                }
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
    public String[] getNavnene(int vansk, boolean defaultBrett) {
        ArrayList<Brett> brt = getBrettene(vansk, defaultBrett);
        String[] ret = new String[brt.size()];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = brt.get(i).getNavn();
        }
        return ret;
    }
    public boolean putBrett(Brett brett) {
        for (Brett b : brettene) {
            if (b.getNavn().equals(brett.getNavn())) {
                Toast.makeText(act, R.string.filFinnes, Toast.LENGTH_LONG).show();
                return false;
            }
        }
        brettene.add(brett);
        return true;
    }

    private void lesFraFil(BufferedReader read, @Nullable HashMap<String, String> byttNavn) {
        Log.i(MainActivity.tagg, "lesFraFil()");
        try {
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
                if (byttNavn != null && byttNavn.containsKey(navn)) {
                    navn = byttNavn.get(navn);
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
                if (b != null && !byttDefault.containsValue(b.getNavn())) {
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
                    Log.e(MainActivity.tagg, "skrivTilFil(): brettet == null eller finnes fra før");
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
    public void slettBrett(String navn) {
        for (Brett b : brettene) {
            if (b.getNavn().equals(navn)) {
                brettene.remove(b);
            }
        }
    }
    public void slettAlle() {
        brettene = new ArrayList<>();
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
    public String toString() {
        String ret = "";
        for (Brett b : brettene) {
            ret += "\n" + b;
        }
        return ret;
    }
}
