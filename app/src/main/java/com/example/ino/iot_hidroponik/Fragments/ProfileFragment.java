package com.example.ino.iot_hidroponik.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.ino.iot_hidroponik.Model.User;
import com.example.ino.iot_hidroponik.R;
import com.example.ino.iot_hidroponik.SharedPrefManager;

public class ProfileFragment extends Fragment {

    ImageView imageView;
    TextView textViewname;
    Button btnlogout;
    private View view;

    public ProfileFragment() {

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.profile_fragment, container, false);
        imageView = view.findViewById(R.id.profile_image);
        textViewname = view.findViewById(R.id.profile_name);
        btnlogout = view.findViewById(R.id.buttonLogout);

        User user = SharedPrefManager.getInstance(getActivity()).getUser();
        final String username = user.getUsername();

        textViewname.setText(username);

        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPrefManager.getInstance(getActivity()).logout();
            }
        });



        return view;

    }
}
