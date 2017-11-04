package jens.sudokuprosjekt;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
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

                InputFilter[] filts = {new InputFilter.LengthFilter(1)};
                nyEdit.setFilters(filts);

                nyEdit.setBackgroundColor(Color.TRANSPARENT);

                if (feil[pos]) {
                    Log.i(MainActivity.tagg, "Setter feil på " + pos);
                    nyEdit.setTextColor(Color.RED);
                    CountDownTimer timer = new CountDownTimer(4000, 1000) {
                        @Override
                        public void onTick(long l) {}
                        @Override
                        public void onFinish() {
                            nyEdit.setTextColor(Color.BLACK);
                        }
                    };
                    timer.start();
                }
                if (merket[pos]) {
                    //Log.i(MainActivity.tagg, "Merker " + pos);
                    nyEdit.setBackgroundColor(Color.YELLOW);
                }

                if (tallene[pos] >= 0) {
                    nyEdit.setText(Integer.toString(tallene[pos]));
                } else {
                    nyEdit.getText().clear();
                }
                if (disabled[pos]) {
                    nyEdit.setEnabled(false);
                    nyEdit.setFocusable(false);
                }
                //Log.i(MainActivity.tagg, "Farge i " + pos + " = " + ((ColorDrawable)nyEdit.getBackground()).getColor());

                editTexts[pos] = nyEdit;
                rad.addView(nyEdit, j, new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
            }

            this.addView(rad, i, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
        }
    }
}
