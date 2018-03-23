package com.lhh.apst.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lhh.apst.Camera.TakePicture;
import com.lhh.apst.advancedpagerslidingtabstrip.Book;
import com.lhh.apst.advancedpagerslidingtabstrip.BookHelper;
import com.lhh.apst.advancedpagerslidingtabstrip.CircleImageView;
import com.lhh.apst.advancedpagerslidingtabstrip.LoginActivity;
import com.lhh.apst.advancedpagerslidingtabstrip.LoginHelper;
import com.lhh.apst.advancedpagerslidingtabstrip.Logins;
import com.lhh.apst.advancedpagerslidingtabstrip.R;
import com.lhh.apst.advancedpagerslidingtabstrip.ShowAllImageActivity;
import com.lhh.apst.advancedpagerslidingtabstrip.TabActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FirstFragment extends Fragment {
    //宣告
    private List<Book> memberList = new ArrayList<>();
    private DisplayMetrics mPhone;
    private Logins member;
    private LoginHelper helper;
    private BookHelper bkhelper;
    private TextView books;
    private TextView Case;
    private TextView tvName;
    private Button logout;
    private final static int CAMERA = 66;
    private final static int PHOTO = 99;
    private TabHost mTabHost = null;
    private TabLayout tabLayout;
    private LinearLayout container;
    private List<String> tabIndicators;
    private List<Fragment> tabFragments;
    private int INT_TOP=199;
    private CircleImageView Picture;
    private String sname="";

    Context ctx;
    public static FirstFragment instance() {
        FirstFragment view = new FirstFragment();
        return view;
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.first_fragment, null);


        helper = new LoginHelper(getActivity());
        tvName = (TextView) view.findViewById(R.id.tvName);
        int r=(helper.getAllLogins().size())-1;
        String st = String.valueOf(helper.getAllLogins().get(r).getName());
        tvName.setText(st);

     //   helper.newMember();
      //  Cursor c = helper.newMember();
       // tvName = (TextView) view.findViewById(R.id.tvName);
        //String str = c.getString(c.getColumnIndex("name"));
        //tvName.setText(str);


        bkhelper = new BookHelper(getActivity());
        books = (TextView) view.findViewById(R.id.booksqty);
        String s = String.valueOf(bkhelper.getAllBooks().size());
        books.setText(s);

        Picture=(CircleImageView)view.findViewById(R.id.imageView) ;

        SharedPreferences preferences = this.getActivity().getSharedPreferences("preFile",Context.MODE_PRIVATE);

        //讀取preFile裡面的字串name
        sname = preferences.getString("name","");
        if ((sname.equals(""))) {
            Picture.setImageResource(R.drawable.logo);
        } else {
            Glide.with(getContext()).load(new File(sname)).diskCacheStrategy(DiskCacheStrategy.ALL).centerCrop().into(Picture);
        }

        //更換頭貼
        Picture.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent intent=new Intent(getActivity(),ShowAllImageActivity.class);
                startActivityForResult(intent, INT_TOP);
            }});

        tabLayout = (TabLayout) view.findViewById(R.id.tab_layout);
        container = (LinearLayout) view.findViewById(R.id.fragment_container);

        initContent();
        initTab();
        replaceFragment(new tab03());

        Case = (TextView) view.findViewById(R.id.caseqty);
        Case.setText("3");



        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            // @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    replaceFragment(new tab03());
                } else if (tab.getPosition() == 1) {
                    replaceFragment(new tab02());
                } else {
                    replaceFragment(new tab01());
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        return view;
    }



    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);

        transaction.commit();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode==INT_TOP){
            String path = data.getStringExtra("YA");
            Glide.with(getContext()).load(new File(path)).diskCacheStrategy(DiskCacheStrategy.ALL).centerCrop().into(Picture);
            //更換頭貼設定
            SharedPreferences preferences =this. getActivity().getSharedPreferences("preFile",Context.MODE_PRIVATE);
            preferences.edit()
                    .putString("name", path)
                    .commit();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    private void initTab(){
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
    }

    private void initContent(){
        tabIndicators = new ArrayList<>();
        for (int i = 1; i < 4; i++) {
            tabLayout.addTab(tabLayout.newTab().setText("書櫃"+i));
            tabIndicators.add("Tab " + i);
        }
    }

}






