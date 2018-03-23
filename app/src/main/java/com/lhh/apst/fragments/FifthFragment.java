package com.lhh.apst.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.lhh.apst.advancedpagerslidingtabstrip.SetList;
import com.lhh.apst.advancedpagerslidingtabstrip.LoginActivity;
import com.lhh.apst.advancedpagerslidingtabstrip.R;
import com.lhh.apst.advancedpagerslidingtabstrip.TabActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FifthFragment extends Fragment {


    public static FifthFragment instance() {
        FifthFragment view = new FifthFragment();
        return view;
    }
    private List<SetList> memberList = new ArrayList<>();
    private List<SetList> notiList = new ArrayList<>();
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fifth_fragment, container, false);

        ListView listview3 = (ListView) v.findViewById(R.id.listView2);
        ListView listview = (ListView) v.findViewById(R.id.listView3);
        listview3.setAdapter(new MemberAdapter(v.getContext(), memberList));
        listview.setAdapter(new MemberAdapters(v.getContext(), notiList));
        Button logout = (Button)v.findViewById(R.id.button2);
        logout.setOnClickListener(buttonListener);
        Toolbar toolbar = (Toolbar)v.findViewById(R.id.toolbar);
        ((TabActivity)getActivity()).setSupportActionBar(toolbar);

        return v;
    }

    private Button.OnClickListener buttonListener = new Button.OnClickListener() {
        @Override
        public void onClick(View view) {

            final AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
            dialog.setTitle("登出");
            dialog.setMessage("你確定要登出？");
            dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    // TODO Auto-generated method stub

                }

            });
            dialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    File file = new File("/data/data/com.example.user.loginandout/shared_prefs","LoginInfo.xml");
                    file.delete();
                    Intent i = new Intent();
                    i.setClass(getActivity(), LoginActivity.class);
                    startActivity(i);
                    getActivity().finish();
                }

            });
            dialog.show();
        }
    };


    private class MemberAdapter extends BaseAdapter {

        private LayoutInflater layoutInflater;

        public MemberAdapter(Context context, List<SetList> memberList) {
            layoutInflater = LayoutInflater.from((Context) context);
            memberList.add(new SetList(1,R.mipmap.f6, "帳號"));
            memberList.add(new SetList(2, R.mipmap.f7, "佈景"));
            memberList.add(new SetList(3, R.mipmap.f8, "通知"));
        }


        @Override
        public int getCount() {
            return memberList.size();
        }

        @Override
        public Object getItem(int position) {
            return memberList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return memberList.get(position).getId();
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = layoutInflater.inflate(R.layout.listview_item, parent, false);
            }

            final SetList member = memberList.get(position);


           ImageView ivImage = (ImageView) convertView
                    .findViewById(R.id.ivImage);
            ivImage.setImageResource(member.getImage());

            TextView tvName = (TextView) convertView
                    .findViewById(R.id.tvName);
            tvName.setText(member.getName());

            return convertView;
        }
    }

    private class MemberAdapters extends BaseAdapter {

        private LayoutInflater layoutInflater;

        public MemberAdapters(Context context, List<SetList> notiList) {
            layoutInflater = LayoutInflater.from((Context) context);
            notiList.add(new SetList(4, R.mipmap.f9, "關於我們"));
            notiList.add(new SetList(5, R.mipmap.f10, "問題回報"));
            notiList.add(new SetList(6, R.mipmap.f11, "評分BookCam"));
        }


        @Override
        public int getCount() {
            return notiList.size();
        }

        @Override
        public Object getItem(int position) {
            return notiList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return notiList.get(position).getId();
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = layoutInflater.inflate(R.layout.listview_item, parent, false);
            }

            final SetList member = notiList.get(position);

           ImageView ivImage = (ImageView) convertView
                    .findViewById(R.id.ivImage);
            ivImage.setImageResource(member.getImage());

            TextView tvName = (TextView) convertView
                    .findViewById(R.id.tvName);
            tvName.setText(member.getName());

            return convertView;
        }
    }


}


