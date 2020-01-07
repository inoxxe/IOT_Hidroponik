package com.example.ino.iot_hidroponik.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.ino.iot_hidroponik.Adapter.GalleryAdapter;
import com.example.ino.iot_hidroponik.DetailimageActivity;
import com.example.ino.iot_hidroponik.Model.Gallery;
import com.example.ino.iot_hidroponik.R;
import com.example.ino.iot_hidroponik.RecyclerTouchListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GalleryFragment extends Fragment {

    private List<Gallery> lstgallery;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RequestQueue requestQueue;
    private View view;

    public GalleryFragment() {

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.gallery_fragment, container, false);

        lstgallery = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(view.getContext());

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclergallery);

        getData();

        adapter = new GalleryAdapter(lstgallery, getActivity());
        recyclerView.setAdapter(adapter);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 3);
        recyclerView.setLayoutManager(mLayoutManager);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                final Gallery gallery = lstgallery.get(position);
                final String date = gallery.getDate();
                final String status = gallery.getStatus();
                final String url = gallery.getUrl();
                Intent intent = new Intent(getContext(), DetailimageActivity.class);
                intent.putExtra("date",date);
                intent.putExtra("url",url);
                intent.putExtra("status",status);
                startActivity(intent);


            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        return view;

    }

    private JsonArrayRequest getDataFromServer() {

        final ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.progressBar2);


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
                        Toast.makeText(getActivity(), "No More Items Available", Toast.LENGTH_SHORT).show();
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
            Gallery gallery = new Gallery();
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
            lstgallery.add(gallery);
        }

        //Notifying the adapter that data has been added or changed
        adapter.notifyDataSetChanged();
    }
}
