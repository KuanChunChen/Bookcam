package com.lhh.apst.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.lhh.apst.Camera.TakePicture;
import com.lhh.apst.advancedpagerslidingtabstrip.BookHelper;
import com.lhh.apst.advancedpagerslidingtabstrip.R;

public class ThirdFragment  extends Fragment {
    private BookHelper bkHelper;


    public static ThirdFragment instance() {
        ThirdFragment view = new ThirdFragment();
        return view;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.third_fragment, null);
        if (bkHelper==null) {
            bkHelper = new BookHelper(getActivity());
        } else {}

        Button button=(Button)view.findViewById(R.id.btInsertBook);
        button.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
               startActivity(new Intent().setClass(getActivity(), TakePicture.class));
               getActivity().finish();
            }
        });

        return view;
    }
}