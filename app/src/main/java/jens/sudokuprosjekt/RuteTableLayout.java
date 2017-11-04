package jens.sudokuprosjekt;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;

/**
 * Created by Jens on 03.11.2017.
 */

public class RuteTableLayout extends TableLayout {
    private Context cont;
    private EditText[] editTexts = new EditText[9];
    private int[] tallene;
    private boolean[] disabled;
    private boolean[] merket;
    private boolean[] feil;

    public RuteTableLayout(Context context) {
        super(context);
        this.cont = context;
    }
    public RuteTableLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.cont = context;
    }

    public EditText[] getEditTexts() {
        return editTexts;
    }

    public int[] getTallene() {
        return tallene;
    }
    public boolean[] getDisabled() {
        return disabled;
    }
    public boolean[] getMerket() {
        return merket;
    }
    public void setFeil(boolean[] feil) {
        this.feil = feil;
    }
    public boolean[] getFeil() {
        return feil;
    }
    public boolean erFylt() {
        for (int i : tallene) {
            if (i <= 0) {
                return false;
            }
        }
        return true;
    }

    public void setBrett(int[] tallene, boolean[] disabled, final boolean[] merket, boolean[] feil) {
        this.tallene = tallene;
        this.merket = merket;
        this.disabled = disabled;
        this.feil = feil;
    }

    public void oppdater() {
        this.removeAllViews();
        for (int i = 0; i < 3; i++) {
            TableRow rad = new TableRow(cont);
            TableRow.LayoutParams param = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT, 1f);
            rad.setLayoutParams(param);

            for (int j = 0; j < 3; j++) {
                final int pos = 3 * i + j;

                LayoutInflater inf = LayoutInflater.from(cont);
                final EditText nyEdit = (EditText) inf.inflate(R.layout.edittall, null);

                if (tallene[pos] >= 0) {
                    nyEdit.setText(Integer.toString(tallene[pos]));
                } else {
                    nyEdit.setText("");
                }
                nyEdit.setBackgroundColor(Color.TRANSPARENT);
                InputFilter[] filts = {new InputFilter.LengthFilter(1)};
                nyEdit.setFilters(filts);
                if (disabled[pos]) {
                    nyEdit.setEnabled(false);
                    nyEdit.setFocusable(false);
                }
                if (merket[pos]) {
                    Log.i(MainActivity.tagg, "Merker " + pos);
                    nyEdit.setBackgroundColor(Color.YELLOW);
                }
                if (feil[pos]) {
                    nyEdit.setBackgroundColor(Color.RED);
                    Log.i(MainActivity.tagg, "Setter feil på " + pos);
                    CountDownTimer timer = new CountDownTimer(2000, 2000) {
                        @Override
                        public void onTick(long l) {}
                        @Override
                        public void onFinish() {
                            nyEdit.setBackgroundColor(Color.TRANSPARENT);
                        }
                    };
                    timer.start();
                }
                nyEdit.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
                    @Override
                    public void afterTextChanged(Editable editable) {
                        String text = nyEdit.getText().toString();
                        if (!text.equals("")) {
                            int tall = Integer.parseInt(nyEdit.getText().toString());
                            //Log.i(MainActivity.tagg, "Endret: " + tall);
                            RuteTableLayout.this.tallene[pos] = tall;
                        } else {
                            RuteTableLayout.this.tallene[pos] = -1;
                        }
                    }
                });
                nyEdit.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        int farge = ((ColorDrawable) nyEdit.getBackground()).getColor();
                        if (farge == Color.YELLOW) {
                            nyEdit.setBackgroundColor(Color.TRANSPARENT);
                            RuteTableLayout.this.merket[pos] = false;
                        } else if (farge == Color.TRANSPARENT || farge == Color.RED) {
                            nyEdit.setBackgroundColor(Color.YELLOW);
                            RuteTableLayout.this.merket[pos] = true;
                        }
//                      Log.i(MainActivity.tagg, Arrays.toString(merket));
                        return true;
                    }
                });
                //nyEdit.setBackgroundColor(Color.RED);
                editTexts[pos] = nyEdit;
                rad.addView(nyEdit, j, new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
            }
            this.addView(rad, i, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
        }
    }
}
