package com.anjuse.test.currencyconverter.data;

import android.os.AsyncTask;
import android.util.Log;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class CurrencyData {

    private String dataUrl = "http://api.fixer.io/latest";
    private String dataUrlParameters = "base="+"USD";
    private String charset="UTF-8";
    //private URL url;
    //private static HttpURLConnection connection=null;

    public void request(){
        new GetRequest().execute(null, null, null);
    }

    public void urlConnecion(){
        URLConnection connection = null;
        try {
            connection = new URL(dataUrl + "?" + dataUrlParameters).openConnection();
            connection.setRequestProperty("Accept-Charset", charset);
            InputStream response = connection.getInputStream();
            System.out.println(response);
            Log.d("Responde", response.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /*public void httpCall() {
        try {
            url=new URL(dataUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            connection.setRequestProperty("Content-Length","" + Integer.toString(dataUrlParameters.getBytes().length));
            connection.setRequestProperty("Content-Language", "en-US");
            connection.setUseCaches(false);
            connection.setDoInput(true);
            connection.setDoOutput(true);
            // Send request
            DataOutputStream wr = new DataOutputStream(
                    connection.getOutputStream());
            wr.writeBytes(dataUrlParameters);
            wr.flush();
            wr.close();
            // Get Response
            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            String line;
            StringBuffer response = new StringBuffer();
            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();
            String responseStr = response.toString();
            Log.d("Server response", responseStr);
            System.out.println(responseStr);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

    private class GetRequest extends AsyncTask<URL, Integer, Long> {
        protected Long doInBackground(URL... urls) {
            //httpCall();
            urlConnecion();
            return null;
        }

        protected void onProgressUpdate(Integer... progress) {
        }

        protected void onPostExecute(Long result) {
        }
    }

}
