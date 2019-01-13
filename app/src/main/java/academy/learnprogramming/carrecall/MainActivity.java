package academy.learnprogramming.carrecall;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import academy.learnprogramming.carrecall.R;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {

    private String TAG = MainActivity.class.getSimpleName();
    private ListView lv;

    ArrayList<HashMap<String, String>> carList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        carList = new ArrayList<>();
        lv = (ListView) findViewById(R.id.list);

        new GetCars().execute();
    }

    private class GetCars extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(MainActivity.this,"Json Data is downloading",Toast.LENGTH_LONG).show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {

            RecallClient call = new RecallClient();
            String information = call.searchRecall("toyota");


            //Log.e(TAG, "Response from url: " + information);
            if (information != null) {
                try {

                    JSONObject jsonObj = new JSONObject(information);

                    JSONArray resultSet = jsonObj.getJSONArray("ResultSet");

                    for(int i = 0; i < resultSet.length(); i++) {
                        JSONArray vals = (JSONArray) resultSet.get(i);
                        for (int j = 0; j < vals.length(); j++) {
                            JSONObject c = vals.getJSONObject(j);
                            String name = c.getString("Name");

                            // for node Value
                            JSONObject jsoVal = c.getJSONObject("Value");
                            //String type = value.getString("Type");
                            String val = jsoVal.getString("Literal");

                            // tmp hash map for single contact
                            HashMap<String, String> car = new HashMap<>();
                            //if (name.equals("Make name")) {
                                car.put("Name", name);
                                //car.put("Type", type);
                                car.put("Literal", val);
                                Log.e(TAG, name + ":" + val);
                            //}

                            carList.add(car);
                        }
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });

                }

            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            ListAdapter adapter = new SimpleAdapter(MainActivity.this, carList, R.layout.list_item, new String[]{ "Name","Literal"},
                    new int[]{R.id.name, R.id.type});
            lv.setAdapter(adapter);
        }
    }
}