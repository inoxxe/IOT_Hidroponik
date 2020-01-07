package com.example.ino.iot_hidroponik.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.ino.iot_hidroponik.R;

public class ShopFragment extends Fragment {

    private View view;

    public ShopFragment() {

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.shop_fragment, container, false);

        return view;

    }

}
