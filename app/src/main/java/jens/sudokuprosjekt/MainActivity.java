package jens.sudokuprosjekt;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {
    private Button fortsKnapp;

    public final static String tall = "tall";
    public final static String disable = "dis";
    public final static String navn = "navn";
    public final static String diff = "vansk";
    public final static String ny = "new";
    public final static String lag = "lag";
    public final static String tagg = "tagg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button nyKnapp = (Button)findViewById(R.id.nyKnapp);
        nyKnapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment frag = new VanskeligFrag();
                Bundle bndl = new Bundle();
                bndl.putBoolean(lag, false);
                frag.setArguments(bndl);
                frag.show(getFragmentManager(), "");
            }
        });

        fortsKnapp = (Button)findViewById(R.id.fortsKnapp);

        fortsKnapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(".SudokuActivity");
                intent.putExtra(ny, false);
                startActivity(intent);
            }
        });

        Button optKnapp = (Button)findViewById(R.id.optKnapp);
        optKnapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(".OptionsActivity"));
            }
        });

        Button lagKnapp = (Button) findViewById(R.id.lagKnapp);
        lagKnapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(".LagActivity"));
            }
        });
    }

    @Override
    protected void onResume() {
        Log.i(MainActivity.tagg, "MainActivity.onResume()");
        super.onResume();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        Log.i(MainActivity.tagg, "Rute 0 = " + prefs.getString("0", null));
        if (prefs.getString("0", null) == null) {
            fortsKnapp.setEnabled(false);
            fortsKnapp.setFocusable(false);
        }
        else {
            fortsKnapp.setEnabled(true);
            fortsKnapp.setFocusable(true);
        }
    }
}
