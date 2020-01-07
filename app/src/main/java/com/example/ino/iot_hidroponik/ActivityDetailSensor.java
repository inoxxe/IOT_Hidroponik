package com.example.ino.iot_hidroponik;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.ino.iot_hidroponik.Model.Gallery;
import com.example.ino.iot_hidroponik.Model.Graph;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.PointsGraphSeries;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class ActivityDetailSensor extends AppCompatActivity {
    private RequestQueue requestQueue;
    private List<Graph> lstgraph;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailsensor);
        GraphView graph = (GraphView) findViewById(R.id.graph);
        PointsGraphSeries<DataPoint> series = new PointsGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 5),
                new DataPoint(1, 3),
                new DataPoint(2, 7),
                new DataPoint(3, 9),
                new DataPoint(4, 10)
        });
        graph.addSeries(series);

    }

    private JsonArrayRequest getDataFromServer() {

        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar2);


        progressBar.setVisibility(View.VISIBLE);
        progressBar.setIndeterminate(true);


        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest("http://iot.dinus.ac.id/api/api/galeri2", new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                //Calling method parseData to parse the json response
                parseData(response);
                //Hiding the progressbar
                progressBar.setVisibility(View.GONE);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.GONE);
                        //If an error occurs that means end of the list has reached
                        Toast.makeText(getApplicationContext(), "No More Items Available", Toast.LENGTH_SHORT).show();
                    }
                });

        //Returning the request
        return jsonArrayRequest;
    }

    //This method will get data from the web api
    private void getData() {
        //Adding the method to the queue by calling the method getDataFromServer
        requestQueue.add(getDataFromServer());
        //Incrementing the request counter
    }

    //This method will parse json data
    private void parseData(JSONArray array) {
        for (int i = 0; i < array.length(); i++) {
            //Creating the superhero object
            Graph graph = new Graph();
            JSONObject json = null;
            try {
                //Getting json
                json = array.getJSONObject(i);

                //Adding data to the superhero object
                gallery.setUrl("http://iot.dinus.ac.id/"+json.getString("path_file").substring(2));
                gallery.setDate(json.getString("RGB_name_file"));
                gallery.setStatus(json.getString("RGB_value_extract"));

            } catch (JSONException e) {
                e.printStackTrace();
            }
            //Adding the superhero object to the list
            lstgraph.add(graph);
        }

    }
}
