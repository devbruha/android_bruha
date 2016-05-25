package com.bruha.bruhaandroid.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bruha.bruhaandroid.Model.User;
import com.bruha.bruhaandroid.Processing.RetrieveMyPHP;
import com.bruha.bruhaandroid.R;


public class ProfileFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_profile, container, false);


        RetrieveMyPHP retrieveMyPHP = new RetrieveMyPHP();
        User me = retrieveMyPHP.getUserInfo("");

        TextView emailtext = (TextView) view.findViewById(R.id.profilePageUserEmail);
        TextView nametext = (TextView) view.findViewById(R.id.profilePageUserName);

        emailtext.setText(me.getEmail());
        nametext.setText(me.getName().toUpperCase());

        return view;
    }

}
