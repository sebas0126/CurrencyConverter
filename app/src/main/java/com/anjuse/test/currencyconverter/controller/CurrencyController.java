package com.anjuse.test.currencyconverter.controller;

import android.content.Context;
import com.anjuse.test.currencyconverter.data.CurrencyData;
import org.json.JSONException;
import org.json.JSONObject;

public class CurrencyController {

    private CurrencyData data; //Data layer
    private JSONObject currencyRates; //JSON Object response
    private Context appContext;

    public CurrencyController(Context appContext){
        this.appContext = appContext;
    }

    /**
     * Initialize the data layer and make the data request, sending the url of the rest api and the currency base
     * @param url //Rest api
     * @param base //Base currency
     * @return true if data is returned, false if data is null
     */
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

    /**
     * Search the currency in the JSON object to get the exchange rate
     * @param currency
     * @return exchange rate
     */
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