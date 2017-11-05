package jens.sudokuprosjekt;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.os.Bundle;
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
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String lokasjon = prefs.getString("loc", null);
        if (lokasjon != null) {
            Locale loc = new Locale(lokasjon);
            Locale.setDefault(loc);
            Configuration conf = getResources().getConfiguration();
            conf.locale = loc;
            getBaseContext().getResources().updateConfiguration(conf, getBaseContext().getResources().getDisplayMetrics());
        }
    }
}
