package com.lhh.apst.fragments;


import android.annotation.TargetApi;
import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lhh.apst.advancedpagerslidingtabstrip.Book;
import com.lhh.apst.advancedpagerslidingtabstrip.R;
import com.lhh.apst.advancedpagerslidingtabstrip.SetList;

import java.util.ArrayList;
import java.util.List;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class Wishing extends Fragment {

    private List<Book> memberList = new ArrayList<>();
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.wishing, container, false);




        return v;
    }
}