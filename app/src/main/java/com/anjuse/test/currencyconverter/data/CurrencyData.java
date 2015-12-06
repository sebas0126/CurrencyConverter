package com.anjuse.test.currencyconverter.data;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class CurrencyData {

    private String url = "http://api.fixer.io/latest";
    private String parameters = "base=USD";
    private String charset="UTF-8";
    private JSONObject JSONresponse;

    public JSONObject request(String url, String base, String[] rates){
        new GetRequest().execute(null, null, null); //Aca hay que mandar los parametros que se recibieron (url y base) y recibir un JSONObject como respuesta
        return JSONresponse.getJSONObject("rates"); //Aca hay que retornar el JSONObject de los current del JSONObject que se recibio arriba
    }


    public JSONObject urlConnection(String url, String parameters){
        URLConnection connection = null;
        try {
            InputStream response = new URL(url+"?"+parameters).openStream();
            BufferedReader streamReader = new BufferedReader(new InputStreamReader(response, "UTF-8"));
            StringBuilder responseStrBuilder = new StringBuilder();
            String inputStr;
            while ((inputStr = streamReader.readLine()) != null)
                responseStrBuilder.append(inputStr);
            JSONresponse = new JSONObject(responseStrBuilder.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return JSONresponse;
    }


    private class GetRequest extends AsyncTask<URL, Integer, JSONObject> {
        protected JSONObject doInBackground(URL... urls) {
            return urlConnection(url, parameters);
        }

        protected void onProgressUpdate(Integer... progress) {
        }

        protected void onPostExecute(Long result) {
        }
    }

}
