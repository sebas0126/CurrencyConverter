package com.anjuse.test.currencyconverter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.anjuse.test.currencyconverter.controller.CurrencyController;

public class MainActivity extends AppCompatActivity implements View.OnKeyListener {

    private TextView tvGbpCurrency, tvEurCurrency, tvJpyCurrency, tvBrlCurrency;
    private EditText etUsdCurrency;
    private CurrencyController controller;

    private final String STR_CURRENCY_GBP="GBP",
    STR_CURRENCY_EUR="EUR",
    STR_CURRENCY_JPY="JPY",
    STR_CURRENCY_BRL="BRL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeComponents();
        getCurrencies();
    }

    private void getCurrencies() {
        controller = new CurrencyController(MainActivity.this);
        if(!controller.getCurrencyRates("http://api.fixer.io/latest", "base=USD")){
            Toast.makeText(this, getResources().getString(R.string.request_error_msg), Toast.LENGTH_LONG).show();
            etUsdCurrency.setEnabled(false);
        }
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
            if(!etUsdCurrency.getText().toString().equals("")) {
                displayCurrencies();
            }else{
                clearRates();
            }
        }
        return false;
    }

    private void clearRates(){
        tvGbpCurrency.setText("0");
        tvEurCurrency.setText("0");
        tvJpyCurrency.setText("0");
        tvBrlCurrency.setText("0");
    }

    private void displayCurrencies(){
        double value=Double.parseDouble(etUsdCurrency.getText().toString());
        tvGbpCurrency.setText(""+controller.getRate(STR_CURRENCY_GBP) * value);
        tvEurCurrency.setText("" + controller.getRate(STR_CURRENCY_EUR) * value);
        tvJpyCurrency.setText("" + controller.getRate(STR_CURRENCY_JPY) * value);
        tvBrlCurrency.setText("" + controller.getRate(STR_CURRENCY_BRL) * value);
    }
}
