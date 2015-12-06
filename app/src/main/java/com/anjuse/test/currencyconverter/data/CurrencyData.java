package com.anjuse.test.currencyconverter.data;

import android.os.AsyncTask;
import android.util.Log;


import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class CurrencyData {

    private String dataUrl = "http://api.fixer.io/latest";
    private String dataUrlParameters = "base="+"USD";
    private String charset="UTF-8";
    private JSONObject respuesta;

    public void request(){
        new GetRequest().execute(null, null, null);
    }

    public void urlConnecion(){
        URLConnection connection = null;
        try {
            InputStream response = new URL(dataUrl+"?"+dataUrlParameters).openStream();
            Log.d("Respuesta", response.toString());
            BufferedReader streamReader = new BufferedReader(new InputStreamReader(response, "UTF-8"));
            StringBuilder responseStrBuilder = new StringBuilder();
            String inputStr;
            while ((inputStr = streamReader.readLine()) != null)
                responseStrBuilder.append(inputStr);
            respuesta = new JSONObject(responseStrBuilder.toString());
            System.out.println(respuesta);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    private class GetRequest extends AsyncTask<URL, Integer, Long> {
        protected Long doInBackground(URL... urls) {
            urlConnecion();
            return null;
        }

        protected void onProgressUpdate(Integer... progress) {
        }

        protected void onPostExecute(Long result) {
        }
    }

}
