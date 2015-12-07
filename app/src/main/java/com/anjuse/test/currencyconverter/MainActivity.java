package com.anjuse.test.currencyconverter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.anjuse.test.currencyconverter.R;
import com.anjuse.test.currencyconverter.controller.CurrencyController;
import com.anjuse.test.currencyconverter.data.CurrencyData;

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

        getCurrencies();

        initializeComponents();
    }

    /**
     * Initialize the controller, and calls the getCurrencyRates in the controller, sends url and the base currency
     * If it fails disable the edittext and shows an alert
     */
    private void getCurrencies() {
        controller = new CurrencyController(MainActivity.this);
        if(!controller.getCurrencyRates("http://api.fixer.io/latest", "base=USD")){
            Toast.makeText(this, "No se puede consultar la informacion en este momento", Toast.LENGTH_LONG);
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

    /**
     * When key is pressed, the imput number is converted and shows the currency rates, if the edittext is blank, clears the currency textviews
     * @param v
     * @param keyCode
     * @param event
     * @return
     */
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if(event.getAction() == KeyEvent.ACTION_UP){
            Log.v("KEY", "Key Up ");
            if(!etUsdCurrency.getText().toString().equals("")) {
                displayCurrencies();
            }else{
                clearRates();
            }
        }
        return false;
    }

    /**
     * Set the currency textviews empty
     */
    private void clearRates(){
        tvGbpCurrency.setText("");
        tvEurCurrency.setText("");
        tvJpyCurrency.setText("");
        tvBrlCurrency.setText("");
    }

    /**
     * Calculate and shows each of the currency rates in it respective textview
     */
    private void displayCurrencies(){
        double value=Double.parseDouble(etUsdCurrency.getText().toString());
        tvGbpCurrency.setText(""+controller.getRate(STR_CURRENCY_GBP)*value);
        tvEurCurrency.setText("" + controller.getRate(STR_CURRENCY_EUR) * value);
        tvJpyCurrency.setText(""+controller.getRate(STR_CURRENCY_JPY)*value);
        tvBrlCurrency.setText(""+controller.getRate(STR_CURRENCY_BRL)*value);
        Log.v("KEY", String.valueOf(controller.getRate("BRL")));
    }
}
