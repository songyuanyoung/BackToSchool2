package com.example.rjt.backtoschool.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rjt.backtoschool.R;

/**
 * Created by zhangwenpurdue on 7/17/2017.
 */

public class DriversInfoFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_driver_info, container, false);
        return view;
    }
}
