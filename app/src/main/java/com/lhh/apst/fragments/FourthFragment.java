package com.lhh.apst.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;

import com.lhh.apst.advancedpagerslidingtabstrip.BookHelper;
import com.lhh.apst.advancedpagerslidingtabstrip.R;


public class FourthFragment extends Fragment {

    private BookHelper bkHelper;
    public static FourthFragment instance() {
        FourthFragment view = new FourthFragment();
        return view;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fourth_fragment, null);
        if (bkHelper==null) {
            bkHelper = new BookHelper(getActivity());
        } else {}

        // /TAB過程
        TabHost host = (TabHost)view.findViewById(R.id.tabHost2);
        host.setup();
        host.addTab(host.newTabSpec("賣書專區").setIndicator("賣書專區").setContent(R.id.tabSelling)); //這裡直接帶出 values 裡 strings.xml 的字串
        host.addTab(host.newTabSpec("許願池").setIndicator("許願池").setContent(R.id.tabWishing));
        return view;
    }
}