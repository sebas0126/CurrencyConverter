package com.anjuse.test.currencyconverter.data;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.anjuse.test.currencyconverter.R;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.concurrent.CountDownLatch;

public class CurrencyData {

    private JSONObject JSONresponse = null; //The response in JSON object
    private CountDownLatch reqLatch;
    private String LOG_TAG = CurrencyData.class.getSimpleName();
    public Context appContext;

    /**
     * Instanciates the async task that will execute the request
     *
     * @param url URI of the service to be requested
     * @param base
     * @param c The context of the application
     * @return A JSONObject with the rates
     */
    public JSONObject request(String url, String base, Context c){
        appContext = c;
        reqLatch = new CountDownLatch(1);
        GetRequest req = new GetRequest();
        req.execute(url, base);
        try {
            // We wait until the request is complete to return a value
            reqLatch.await();
            return JSONresponse.getJSONObject("rates");
        } catch (InterruptedException | JSONException e) {
            Log.e(LOG_TAG, e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Make the data request to the rest api with the url, A string builder is used to form the response in JSON format
     * @param url Rest api url
     * @param parameters Base currency
     */
    private void urlConnection(String url, String parameters){
        try {
            InputStream response = new URL(url + "?" + parameters).openStream();
            BufferedReader streamReader = new BufferedReader(new InputStreamReader(response, "UTF-8"));
            StringBuilder responseStrBuilder = new StringBuilder();
            String inputStr;
            while ((inputStr = streamReader.readLine()) != null)
                responseStrBuilder.append(inputStr);
            JSONresponse = new JSONObject(responseStrBuilder.toString());
        } catch (JSONException | IOException e) {
            Log.e(LOG_TAG, e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Run the data request to the rest api in a different thread
     */
    private class GetRequest extends AsyncTask<String, Void, Boolean> {
        //args[0] url
        //args[1] paramétro
        protected Boolean doInBackground(String... params) {
            urlConnection(params[0], params[1]);
            reqLatch.countDown();
            if(JSONresponse != null)
                return true;
            return false;
        }

        protected void onPostExecute(Boolean success) {
            if(!success)
                Toast.makeText(appContext, appContext.getResources().getString(R.string.request_error_msg), Toast.LENGTH_SHORT).show();
        }
    }
}