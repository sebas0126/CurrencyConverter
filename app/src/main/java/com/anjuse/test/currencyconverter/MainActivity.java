package com.anjuse.test.currencyconverter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.anjuse.test.currencyconverter.R;
import com.anjuse.test.currencyconverter.data.CurrencyData;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CurrencyData a=new CurrencyData();
        a.request();
    }
}
