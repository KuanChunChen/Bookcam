package com.lhh.apst.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.lhh.apst.advancedpagerslidingtabstrip.ImageAdapter;
import com.lhh.apst.advancedpagerslidingtabstrip.R;


public class tab02 extends Fragment {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tab02, container, false);

        final Integer[] mThumb= new Integer[] {
        };
        GridView gridview2 = (GridView) v.findViewById(R.id.gridView2);
        gridview2.setAdapter(new ImageAdapter(getContext(),mThumb));


        gridview2.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) {
                GridView gridView = (GridView) parent.getItemAtPosition(position);
                ImageView imageView = new ImageView(getContext());

                //長按顯示大圖
                imageView.setScaleType(ImageView.ScaleType.CENTER);
                imageView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                imageView.setImageResource(mThumb[position]);
                Dialog dialog = new AlertDialog.Builder(getContext())
                        .setView(imageView)
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        }).create();
                dialog.show();
                return false;
            }
        });
        return v;
    }
}

