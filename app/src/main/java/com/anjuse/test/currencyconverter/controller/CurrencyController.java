package com.anjuse.test.currencyconverter.controller;

import android.content.Context;

import com.anjuse.test.currencyconverter.data.CurrencyData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CurrencyController {

    private CurrencyData data;
    private JSONObject currencyRates;
    private Context appContext;

    public CurrencyController(Context appContext){
        this.appContext = appContext;
    }

    public boolean getCurrencyRates(String url, String base){
        if(data == null){
            data = new CurrencyData();
        }

        currencyRates = data.request(url, base, appContext);
        if(currencyRates == null) {
            return false;
        }
        return true;
    }

    public double getRate(String currency){
        if(currencyRates==null) return 0;
        try {
            return currencyRates.getDouble(currency);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }

}
