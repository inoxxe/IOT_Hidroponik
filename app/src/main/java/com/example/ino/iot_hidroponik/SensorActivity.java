package com.example.ino.iot_hidroponik;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.ino.iot_hidroponik.Model.Graphic;
import com.example.ino.iot_hidroponik.Model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class SensorActivity extends AppCompatActivity {
    TextView humidity,temperature,water,ph,nutrition,name;
    CardView cardhum,cardph,cardtemp,cardwater,cardnut;
    private RequestQueue requestQueue;
    double hum[] = new double[100];
    double pharr[] = new double[100];
    double waterarr[] = new double[100];
    double nutarr[] = new double[100];
    double temparr[] = new double[100];

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);
        Intent intent = getIntent();
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        name = findViewById(R.id.sensor_plant_name);
        name.setText(intent.getStringExtra("name"));
        cardhum = findViewById(R.id.cardhum);
        cardph = findViewById(R.id.cardph);
        cardtemp = findViewById(R.id.cardtemp);
        cardwater = findViewById(R.id.cardwater);
        cardnut = findViewById(R.id.cardnut);
        humidity = findViewById(R.id.humidity_percentage);
        temperature = findViewById(R.id.temperature_percentage);
        water = findViewById(R.id.water_percentage);
        ph = findViewById(R.id.ph_percentage);
        nutrition = findViewById(R.id.nutrition_percentage);
        getData();
        isiData();

        cardnut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inten = new Intent(getApplicationContext(), ActivityDetailSensor.class);
                inten.putExtra("array",nutarr);
                inten.putExtra("value","ppm");
                inten.putExtra("title","Nutrisi");
                startActivity(inten);

            }
        });
        cardwater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inten = new Intent(getApplicationContext(), ActivityDetailSensor.class);
                inten.putExtra("array",waterarr);
                inten.putExtra("value","cm");
                inten.putExtra("title","Ketinggian Air");
                startActivity(inten);

            }
        });
        cardtemp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inten = new Intent(getApplicationContext(), ActivityDetailSensor.class);
                inten.putExtra("array",temparr);
                inten.putExtra("value","C");
                inten.putExtra("title","Suhu");
                startActivity(inten);

            }
        });
        cardph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inten = new Intent(getApplicationContext(), ActivityDetailSensor.class);
                inten.putExtra("array",pharr);
                inten.putExtra("value","Ph");
                inten.putExtra("title","Ph");
                startActivity(inten);

            }
        });
        cardhum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inten = new Intent(getApplicationContext(), ActivityDetailSensor.class);
                inten.putExtra("array",hum);
                inten.putExtra("value","%");
                inten.putExtra("title","Humidity");
                startActivity(inten);

            }
        });

    }


    private void getData() {

        Intent intent = getIntent();
        final String id = intent.getStringExtra("id");

        class GetData extends AsyncTask<Void, Void, String> {


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    //converting response to json object
                    JSONObject obj = new JSONObject(s);

                    humidity.setText(obj.getString("huminity").toString());
                    water.setText(obj.getString("jarak"));
                    ph.setText(obj.getString("ph"));
                    temperature.setText(obj.getString("temperatur"));
                    nutrition.setText(obj.getString("Tds"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("id", id);

                //returing the response
                return requestHandler.sendPostRequest("http://iot.dinus.ac.id/api/api/sensor3", params);
            }
        }

        GetData gd = new GetData();
        gd.execute();
    }

    public JsonArrayRequest getDataFromServer() {

        User user = SharedPrefManager.getInstance(this).getUser();
        final String id = user.getId();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,"http://iot.dinus.ac.id/api/api/sensor2?id="+id,null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                //Calling method parseData to parse the json response
                parseData(response);
                //Hiding the progressbar

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        //If an error occurs that means end of the list has reached
                        Toast.makeText(getApplicationContext(), "No More Items Available", Toast.LENGTH_SHORT).show();
                    }
                });

        //Returning the request
        return jsonArrayRequest;
    }

    //This method will get data from the web api
    public void isiData() {
        //Adding the method to the queue by calling the method getDataFromServer
        requestQueue.add(getDataFromServer());
        //Incrementing the request counter
    }

    //This method will parse json data
    public void parseData(JSONArray array) {
        for (int i = 0; i < array.length(); i++) {
            JSONObject json = null;
            try {
                //Getting json
                json = array.getJSONObject(i);


                    nutarr[i] = Double.parseDouble(json.getString("Tds"));


                    pharr[i] = Double.parseDouble(json.getString("ph"));

                    hum[i] = Double.parseDouble(json.getString("huminity"));

                    temparr[i] = Double.parseDouble(json.getString("temperatur"));

                    waterarr[i] = Double.parseDouble(json.getString("jarak"));



            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }
}
