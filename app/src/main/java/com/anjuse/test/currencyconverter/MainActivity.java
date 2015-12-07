package com.anjuse.test.currencyconverter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.anjuse.test.currencyconverter.R;
import com.anjuse.test.currencyconverter.controller.CurrencyController;
import com.anjuse.test.currencyconverter.data.CurrencyData;

public class MainActivity extends AppCompatActivity implements View.OnKeyListener {

    private TextView tvGbpCurrency, tvEurCurrency, tvJpyCurrency, tvBrlCurrency;
    private EditText etUsdCurrency;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeComponents();
    }

    private void initializeComponents(){
        etUsdCurrency = (EditText) findViewById(R.id.et_input_value);
        etUsdCurrency.setOnKeyListener(this);

        tvGbpCurrency = (TextView) findViewById(R.id.tv_gbp_currency);
        tvEurCurrency = (TextView) findViewById(R.id.tv_eur_currency);
        tvJpyCurrency = (TextView) findViewById(R.id.tv_jpy_currency);
        tvBrlCurrency = (TextView) findViewById(R.id.tv_brl_currency);
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if(event.getAction() == KeyEvent.ACTION_UP){
            Log.v("KEY", "Key Up ");
            CurrencyController controller = new CurrencyController(MainActivity.this);
            if(controller.getCurrencyRates("http://api.fixer.io/latest", "base=USD")){
                displayCurrencies(controller);
            }
        }
        return false;
    }

    private void displayCurrencies(CurrencyController controller){

        Log.v("KEY", String.valueOf(controller.getRate("BRL")));
    }
}
