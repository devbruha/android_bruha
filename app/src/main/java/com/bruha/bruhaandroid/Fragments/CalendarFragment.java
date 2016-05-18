package com.bruha.bruhaandroid.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bruha.bruhaandroid.R;

/**
 * Created by ArhamRazaMac on 16-05-17.
 */

public class CalendarFragment extends Fragment{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_whats_hot, container, false);
        return view;
    }
}
