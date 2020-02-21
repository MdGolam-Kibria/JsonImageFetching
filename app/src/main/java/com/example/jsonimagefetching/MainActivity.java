package com.example.jsonimagefetching;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Create default options which will be used for every
//  displayImage(...) call if no options will be passed to this method
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()

           .cacheInMemory(true)
                .cacheOnDisk(true)

           .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())

           .defaultDisplayImageOptions(defaultOptions)

           .build();
        ImageLoader.getInstance().init(config); // Do it on Application start
        listView = (ListView) findViewById(R.id.listView);
        MyTask myTask  = new MyTask();
        myTask.execute();
    }

    public class MyTask extends AsyncTask<String, String, List<ModelClass>> {
        HttpURLConnection httpURLConnection = null;
        BufferedReader bufferedReader = null;

        @Override
        protected List<ModelClass> doInBackground(String... strings) {
            try {
                URL url = new URL("https://api.myjson.com/bins/aceey");
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.connect();
                InputStream inputStream = httpURLConnection.getInputStream();
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuffer stringBuffer = new StringBuffer();
                String line = "";
                String file;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuffer.append(line);
                }
                file = stringBuffer.toString();

                JSONObject mainObject = new JSONObject(file);
                JSONArray jsonArray = mainObject.getJSONArray("kibriaInfo");
                List<ModelClass> list = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++) {
                   JSONObject arrayItem =  jsonArray.getJSONObject(i);
                   ModelClass modelClass = new ModelClass();
                   modelClass.setName(arrayItem.getString("name"));
                   modelClass.setImg(arrayItem.getString("img"));
                   list.add(modelClass);
                }
                return list;


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }finally {
                httpURLConnection.disconnect();
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<ModelClass> s) {
            super.onPostExecute(s);
            CustomAdapter customAdapter = new CustomAdapter(getApplicationContext(),R.layout.simple,s);
            listView.setAdapter(customAdapter);
        }
    }
}
