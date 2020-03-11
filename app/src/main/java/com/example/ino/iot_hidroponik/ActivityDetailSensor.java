package com.example.ino.iot_hidroponik;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.ino.iot_hidroponik.Model.Gallery;
import com.example.ino.iot_hidroponik.Model.Graphic;
import com.example.ino.iot_hidroponik.Model.User;
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.UniqueLegendRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.PointsGraphSeries;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ActivityDetailSensor extends AppCompatActivity {
    private RequestQueue requestQueue;
    private List<Graphic> lstgraph;
    DataPoint[] points = new DataPoint[100];
    double average1 = 0;
    TextView textrata,value1,value2,kondisi1,kondisi2,day1;
    String satuan,title,textavg,hasil1,hasil2;
    LinearLayout kesimpulan,kesimpulan1,kesimpulan2;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailsensor);
        Bundle extras = getIntent().getExtras();
        double[] hum = extras.getDoubleArray("array");
        satuan = extras.getString("value");
        title = extras.getString("title");
        day1 = findViewById(R.id.day1);
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        lstgraph = new ArrayList<>();
        textrata = findViewById(R.id.average);
        kesimpulan = findViewById(R.id.kesimpulan);
        kesimpulan1 = findViewById(R.id.kesimpulan1);
        kesimpulan2 = findViewById(R.id.kesimpulan2);
        value1 = findViewById(R.id.value1);
        value2 = findViewById(R.id.value2);
        kondisi1 = findViewById(R.id.kondisi1);
        kondisi2 = findViewById(R.id.kondisi2);
        DecimalFormat df = new DecimalFormat("#.##");
        GraphView graph = (GraphView) findViewById(R.id.graph);
        for (int i = 0; i < 100; i++) {
            average1 += hum[i];
        }
        average1/=100;
        if(title.equals("Humidity") || title.equals("Suhu")){
            kesimpulan.setVisibility(View.INVISIBLE);
        }
        if (title.equals("Ph")){
            day1.setText("- Hari 1-34 : ");
            value1.setText("Ph ");
            kesimpulan2.setVisibility(View.INVISIBLE);
        }
        if(title.equals("Ketinggian Air")){
            value1.setText("Ketinggian Airnya ");
            day1.setText("- Hari 1-34 : ");
            kesimpulan2.setVisibility(View.INVISIBLE);
        }
        if(title.equals("Nutrisi")){
            value1.setText("PPm ");
            value2.setText("PPm ");
            if(average1 <700){
                kondisi1.setText("Kurang");
            }else if(average1 > 750){
                kondisi1.setText("Lebih");
            }else {
                kondisi1.setText("Normal");
            }

            if(average1 < 950){
                kondisi2.setText("Kurang");
            }else if(average1 > 1050){
                kondisi2.setText("Lebih");
            }else {
                kondisi2.setText("Normal");
            }
        }
        if(title.equals("Ph")){
            if(average1 < 7 ){
                kondisi1.setText("Kurang");
            }else if(average1 > 8){
                kondisi1.setText("Lebih");
            }else {
                kondisi1.setText("Normal");
            }
        }
        if(title.equals("Ketinggian Air")){
            if(average1 < 150 ){
                kondisi1.setText("Kurang");
            }else if(average1 > 200){
                kondisi1.setText("Lebih");
            }else {
                kondisi1.setText("Normal");
            }
        }
        df.setRoundingMode(RoundingMode.UP);

        textavg = String.valueOf(average1);

        textrata.setText(df.format(average1));

        for (int i = 0,j=1 , k =99; i < 100 && j<101 && k>=0; i++,j++,k--) {
            points[i] = new DataPoint(j, hum[k]);
        }

        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(points);


        graph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {
            @Override
            public String formatLabel(double value, boolean isValueX) {
                if (isValueX) {
                    // show normal x values
                    return super.formatLabel(value, isValueX);
                } else {
                    // show currency for y values
                    return super.formatLabel(value, isValueX) + " "+satuan;
                }
            }
        });
        series.setTitle("Grafik "+title);
        series.setDrawDataPoints(true);

        graph.addSeries(series);
        graph.getViewport().setScalable(true);
        graph.getViewport().setScalableY(true);
        graph.setLegendRenderer(new UniqueLegendRenderer(graph));
        graph.getLegendRenderer().setVisible(true);
        graph.getGridLabelRenderer().setHorizontalAxisTitle("Data");

        graph.getGridLabelRenderer().setVerticalAxisTitleColor(Color.BLUE);
        graph.getGridLabelRenderer().setHorizontalAxisTitleColor(Color.BLUE);

    }

}
