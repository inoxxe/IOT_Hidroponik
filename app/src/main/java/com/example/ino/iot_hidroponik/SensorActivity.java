package com.example.ino.iot_hidroponik;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class SensorActivity extends AppCompatActivity {
    TextView humidity,temperature,water,ph,nutrition,name;
    CardView card1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);
        Intent intent = getIntent();

        name = findViewById(R.id.sensor_plant_name);
        name.setText(intent.getStringExtra("name"));
        card1 = findViewById(R.id.card1);
        humidity = findViewById(R.id.humidity_percentage);
        temperature = findViewById(R.id.temperature_percentage);
        water = findViewById(R.id.water_percentage);
        ph = findViewById(R.id.ph_percentage);
        nutrition = findViewById(R.id.nutrition_percentage);
        getData();

        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inten = new Intent(getApplicationContext(), ActivityDetailSensor.class);
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
}
