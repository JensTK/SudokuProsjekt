package jens.sudokuprosjekt;

import android.content.Context;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.BaseAdapter;
import android.widget.EditText;

/**
 * Created by Jens on 22.10.2017.
 */

public class tallAdapter extends BaseAdapter {
    private Context cont;
    private int[] tallene;
    private boolean[] disabled;
    private boolean[] feil = new boolean[9];
    private EditText[] editTexts = new EditText[9];

    public tallAdapter(Context cont) {
        this.cont = cont;
    }
    public tallAdapter(Context cont, int[] tallene, boolean[] disabled, @Nullable boolean[] feil) {
        this.cont = cont;
        this.tallene = tallene;
        this.disabled = disabled;
        if (feil != null) {
            this.feil = feil;
        }
    }

    public int[] getTallene() {
        return tallene;
    }

    public boolean[] getDisabled() {
        return disabled;
    }

    public void setFeil(boolean[] feil) {
        this.feil = feil;
    }

    public boolean[] getFeil() {
        return feil;
    }

    @Override
    public int getCount() {
        return tallene.length;
    }

    @Override
    public Object getItem(int i) {
        return tallene[i];
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        final EditText nyEdit;
        if (view == null) {
            nyEdit = new EditText(cont);
        }
        else {
            nyEdit = (EditText)view;
        }
        if (tallene[i] >= 0) {
            nyEdit.setText(Integer.toString(tallene[i]));
        }
        else {
            nyEdit.getText().clear();
        }
        if (disabled[i]) {
            nyEdit.setEnabled(false);
            nyEdit.setFocusable(false);
        }

        nyEdit.setId(i);
        nyEdit.setBackgroundColor(Color.TRANSPARENT);
        nyEdit.setGravity(Gravity.CENTER);
        nyEdit.setInputType(InputType.TYPE_CLASS_NUMBER);
        InputFilter[] filts = { new InputFilter.LengthFilter(1) };
        nyEdit.setFilters(filts);
        nyEdit.setSelectAllOnFocus(true);
        nyEdit.setTextSize(TypedValue.COMPLEX_UNIT_SP, 32);
        nyEdit.setImeOptions(EditorInfo.IME_ACTION_DONE);
        nyEdit.setPadding(0, 0, 0, 0);
        if (feil[i]) {
            nyEdit.setBackgroundColor(Color.RED);
            //Log.i("tagg", "Setter farge på " + i);
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
                if (! text.equals("")) {
                    int tall = Integer.parseInt(nyEdit.getText().toString());
                    //Log.i("tagg", "Endret: " + tall);
                    tallene[i] = tall;
                }
                else {
                    tallene[i] = -1;
                }
            }
        });
        editTexts[i] = nyEdit;

        return editTexts[i];
    }

    @Override
    public CharSequence[] getAutofillOptions() {
        return new CharSequence[0];
    }
}
