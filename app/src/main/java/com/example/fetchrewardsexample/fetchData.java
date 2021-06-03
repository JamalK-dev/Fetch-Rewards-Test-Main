package com.example.fetchrewardsexample;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.ContactsContract;
import android.util.Log;

import androidx.annotation.RequiresApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class fetchData extends AsyncTask<Void,Void,Void> {
    String data ="";
    String dataParsed = "";
    String singleParsed ="";



    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected Void doInBackground(Void... voids) {
        try {
            URL url = new URL("https://fetch-hiring.s3.amazonaws.com/hiring.json");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while(line != null){
                line = bufferedReader.readLine();
                data = data + line;

            }

            JSONArray JA = new JSONArray(data);
            ArrayList<JSONObject> array = new ArrayList<JSONObject>();
            ArrayList<JSONObject> arrayname = new ArrayList<JSONObject>();
            JSONArray jsonArray = new JSONArray();
            JSONArray jsonArray2= new JSONArray();
            List<String> list = new ArrayList<String>();
            for(int i =0 ;i < JA.length(); i++){


                    array.add(JA.getJSONObject(i));




                arrayname.add(JA.getJSONObject(i));

                    Log.i("add", "obj added");



            }

            Collections.sort(array, new Comparator<JSONObject>() {
                @Override
                public int compare(JSONObject o1, JSONObject o2) {


                   try{

                       for(int i = 0; i < JA.length(); i++) {
                           JSONObject JN = array.get(i);
                           String nameValue = JN.getString("name");
                           if((nameValue!=null) || ("".equals(nameValue))) {
                               return (o1.getString("listId").compareTo(o2.getString("listId")));
                           }
                       }
                    }
                    catch (JSONException e){
                        e.printStackTrace();
                    }
                    return 0;

                }
            });



            for(int i =0 ;i < JA.length(); i++){



                JSONObject JO = array.get(i);
                String nameValue = JO.getString("name");
                String listValue = JO.getString("listId");


                            if ((nameValue == "null") || ("".equals(nameValue)))
                            {

                                System.out.println("ourString " + nameValue);
                            }
                            else {
                                System.out.println("Real " + nameValue);
                                singleParsed =
                                        "listId:" + JO.get("listId") + "\n"
                                              + "name:" +(JO.get("name")) + "\n";
                           }



                dataParsed = dataParsed + singleParsed +"\n";



            }



        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }


    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        MainActivity.data.setText(this.dataParsed);

    }
}