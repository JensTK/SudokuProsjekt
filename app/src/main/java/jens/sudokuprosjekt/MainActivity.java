package jens.sudokuprosjekt;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.Locale;

public class MainActivity extends Activity {
    public final static String merket = "merk";
    public final static String tall = "tall";
    public final static String disable = "dis";
    public final static String navn = "navn";
    public final static String diff = "vansk";
    public final static String ny = "new";
    public final static String lag = "lag";
    public final static String tagg = "tagg";

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String lokasjon = prefs.getString("loc", null);
        if (lokasjon != null) {
            Locale loc = new Locale(lokasjon);
            Locale.setDefault(loc);
            Configuration conf = getResources().getConfiguration();
            conf.locale = loc;
            getBaseContext().getResources().updateConfiguration(conf, getBaseContext().getResources().getDisplayMetrics());
        }
        startActivity(new Intent(".SpillActivity"));
    }
}
