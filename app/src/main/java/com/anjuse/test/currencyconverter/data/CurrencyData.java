package com.anjuse.test.currencyconverter.data;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.anjuse.test.currencyconverter.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.CountDownLatch;

public class CurrencyData {

    private String charset="UTF-8";
    private JSONObject JSONresponse = null;
    private CountDownLatch reqLatch;
    private String LOG_TAG = CurrencyData.class.getSimpleName();
    public Context appContext;

    public JSONObject request(String url, String base, Context c){
        appContext = c;
        reqLatch = new CountDownLatch(1);
        GetRequest req = new GetRequest();
        req.execute(url, base); //Aca hay que mandar los parametros que se recibieron (url y base) y recibir un JSONObject como respuesta
        try {
            reqLatch.await();
            Log.v("reqLatch", "doInBackground BEFORE " + reqLatch.getCount());
            return JSONresponse.getJSONObject("rates"); //Aca hay que retornar el JSONObject de los current del JSONObject que se recibio arriba
        } catch (InterruptedException | JSONException e) {
            Log.e(LOG_TAG, e.getMessage());
            e.printStackTrace();
        }
        return null;
    }


    private void urlConnection(String url, String parameters){
        URLConnection connection = null;
        try {
            InputStream response = new URL(url + "?" + parameters).openStream();
            BufferedReader streamReader = new BufferedReader(new InputStreamReader(response, "UTF-8"));
            StringBuilder responseStrBuilder = new StringBuilder();
            String inputStr;
            while ((inputStr = streamReader.readLine()) != null)
                responseStrBuilder.append(inputStr);
            JSONresponse = new JSONObject(responseStrBuilder.toString());
            //reqLatch.countDown();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private class GetRequest extends AsyncTask<String, Void, Boolean> {
        ProgressDialog pDialog;
        public GetRequest(){
            pDialog = new ProgressDialog(appContext);
            pDialog.setMessage(appContext.getResources().getString(R.string.dialog_message));
            pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pDialog.setCancelable(false);
        }

        protected void onPreExecute() {
            pDialog.show();
            Log.v("PRE", "onPreExecute");
        }

        //args[0] url
        //args[1] paramétro
        protected Boolean doInBackground(String... params) {
            Log.v("BACK", "doInBackground BEFORE");
            urlConnection(params[0], params[1]);
            reqLatch.countDown();
            if(JSONresponse != null){
                Log.v("BACK", "doInBackground AFTER " + JSONresponse.length());
                return true;
            }
            return false;
        }

        protected void onPostExecute(Boolean success) {
            Log.v("POST", "onPostExecute BEFORE " + success);
            pDialog.dismiss();
            Log.v("POST", "onPostExecute AFTER");
        }
    }

}
