package com.lhh.apst.advancedpagerslidingtabstrip;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lhh.apst.fragments.FifthFragment;
import com.lhh.apst.fragments.FirstFragment;
import com.lhh.apst.fragments.FourthFragment;
import com.lhh.apst.fragments.SecondFragment;
import com.lhh.apst.fragments.ThirdFragment;
import com.lhh.apst.library.AdvancedPagerSlidingTabStrip;
import com.lhh.apst.library.Margins;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class TabActivity extends ActionBarActivity implements ViewPager.OnPageChangeListener{

    public AdvancedPagerSlidingTabStrip mAPSTS;
    public APSTSViewPager mVP;
    private LoginHelper helper;
    private TextView books;

    private static final int VIEW_FIRST 	= 0;
    private static final int VIEW_SECOND   = 1;
    private static final int VIEW_THIRD     = 2;
    private static final int VIEW_FOURTH   = 3;
    private static final int VIEW_FIFTH      = 4;

    private static final int VIEW_SIZE = 5;

    private FirstFragment mFirstFragment = null;
    private SecondFragment mSecondFragment = null;
    private ThirdFragment mThirdFragment = null;
    private FourthFragment mFourthFragment = null;
    private FifthFragment mFifthFragment = null;

    private BookHelper bkHelper;
    private List<Book> memberList = new ArrayList<>();
    private int mSize = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weibo_tab);
        findViews();
        init();
    }


    public void onStart() {
        super.onStart();
        if (bkHelper==null) {
            bkHelper = new BookHelper(this);
        } else {}
        memberList = bkHelper.getAllBooks();
    }

    private void findViews(){
        mAPSTS = (AdvancedPagerSlidingTabStrip)findViewById(R.id.tabs);
        mVP = (APSTSViewPager)findViewById(R.id.vp_main);
    }

    private void init(){
        mSize =30;
        mVP.setOffscreenPageLimit(VIEW_SIZE);
        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager());

        mVP.setAdapter(new FragmentAdapter(getSupportFragmentManager()));

        adapter.notifyDataSetChanged();
        mAPSTS.setViewPager(mVP);
        mAPSTS.setOnPageChangeListener(this);
        mVP.setCurrentItem(VIEW_FIRST);

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_logout) {
            onDelete();

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public class FragmentAdapter extends FragmentStatePagerAdapter implements
            AdvancedPagerSlidingTabStrip.IconTabProvider,
            AdvancedPagerSlidingTabStrip.LayoutProvider,
            AdvancedPagerSlidingTabStrip.TipsProvider{

        public FragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if(position >= 0 && position < VIEW_SIZE){
                switch (position){
                    case  VIEW_FIRST:
                        if(null == mFirstFragment)
                            mFirstFragment = FirstFragment.instance();
                        return mFirstFragment;

                    case VIEW_SECOND:
                        if(null == mSecondFragment)
                            mSecondFragment = SecondFragment.instance();
                        return mSecondFragment;

                    case VIEW_THIRD:
                        if(null == mThirdFragment)
                            mThirdFragment = ThirdFragment.instance();
                        return mThirdFragment;

                    case VIEW_FOURTH:
                        if(null == mFourthFragment)
                            mFourthFragment = FourthFragment.instance();
                        return mFourthFragment;

                    case VIEW_FIFTH:
                        if(null == mFifthFragment)
                            mFifthFragment = FifthFragment.instance();
                        return mFifthFragment;
                    default:
                        break;
                }
            }
            return null;
        }

        @Override
        public int getCount() {
            return VIEW_SIZE;
        }

        @Override
        //每個分類下面的字
        public CharSequence getPageTitle(int position) {
            if(position >= 0 && position < VIEW_SIZE){
                switch (position){
                    case  VIEW_FIRST:
                        return  "我的書櫃";
                    case  VIEW_SECOND:
                        return  "推薦一書";
                    case  VIEW_THIRD:
                        return  "建立書櫃";
                    case  VIEW_FOURTH:
                        return  "賣書專區";
                    case  VIEW_FIFTH:
                        return  "設定";
                    default:
                        break;
                }
            }
            return null;
        }

        @Override
        public float getPageWeight(int position) {
            if(position >= 0 && position < VIEW_SIZE){
                switch (position){
                    case  VIEW_FIRST:
                        return  0.92f;
                    case  VIEW_SECOND:
                        return  1.0f;
                    case  VIEW_THIRD:
                        return  1.0f;
                    case  VIEW_FOURTH:
                        return  1.0f;
                    case  VIEW_FIFTH:
                        return  0.92f;
                    default:
                        break;
                }
            }
            return 1.0f;
        }

        @Override
        public int[] getPageRule(int position) {
            if(position >= 0 && position < VIEW_SIZE){
                switch (position){
                    case  VIEW_FIRST:
                        return  new int[]{};
                    case  VIEW_SECOND:
                        return  new int[]{};
                    case  VIEW_THIRD:
                        return  new int[]{};
                    case  VIEW_FOURTH:
                        return  new int[]{};
                    case  VIEW_FIFTH:
                        return  new int[]{};
                    default:
                        break;
                }
            }
            return new int[0];
        }

        @Override
        public Margins getPageMargins(int position) {
            if(position >= 0 && position < VIEW_SIZE){
                switch (position){
                    case  VIEW_FIRST:
                        return  new Margins(0,0,0,0);
                    case VIEW_SECOND:
                        return  null;
                    case VIEW_THIRD:
                        return  null;
                    case VIEW_FOURTH:
                        return  null;
                    case VIEW_FIFTH:
                        return  new Margins(0,0,getResources().getDimensionPixelSize(R.dimen.home_bar_icon_margins),0);
                    default:
                        break;
                }
            }
            return null;
        }

        @Override
        public Integer getPageIcon(int index) {
            if(index >= 0 && index < VIEW_SIZE){
                switch (index){
                    case  VIEW_FIRST:
                        return  R.mipmap.f1;
                    case VIEW_SECOND:
                        return  R.mipmap.f2;
                    case VIEW_THIRD:
                        return  R.mipmap.f3;
                    case VIEW_FOURTH:
                        return  R.mipmap.f4;
                    case VIEW_FIFTH:
                        return  R.mipmap.f5;
                    default:
                        break;
                }
            }
            return 0;
        }

        @Override
        public Integer getPageSelectIcon(int index) {
            if(index >= 0 && index < VIEW_SIZE){
                switch (index){
                    case  VIEW_FIRST:
                        return  R.mipmap.f1_on;
                    case VIEW_SECOND:
                        return  R.mipmap.f2_on;
                    case VIEW_THIRD:
                        return  R.mipmap.f3_on;
                    case VIEW_FOURTH:
                        return  R.mipmap.f4_on;
                    case VIEW_FIFTH:
                        return  R.mipmap.f5_on;

                    default:
                        break;
                }
            }
            return 0;
        }

        @Override
        public Rect getPageIconBounds(int position) {
            return new Rect(0, 0, mSize, mSize);
        }

        @Override
        public int[] getTipsRule(int position) {
            if(position >= 0 && position < VIEW_SIZE){
                switch (position){
                    case  VIEW_FIRST:
                        return  new int[]{
                                RelativeLayout.ALIGN_PARENT_LEFT};
                    case  VIEW_SECOND:
                        return  new int[]{
                                RelativeLayout.ALIGN_PARENT_LEFT};
                    case  VIEW_THIRD:
                        return  new int[]{
                                RelativeLayout.ALIGN_PARENT_RIGHT};
                    case  VIEW_FOURTH:
                        return  new int[]{
                                RelativeLayout.ALIGN_PARENT_RIGHT};
                    case  VIEW_FIFTH:
                        return  new int[]{
                                RelativeLayout.ALIGN_PARENT_RIGHT};
                    default:
                        break;
                }
            }
            return new int[0];
        }

        @Override
        public Margins getTipsMargins(int position) {
            if(position >= 0 && position < VIEW_SIZE){
                switch (position){
                    case VIEW_FIRST:
                        return new Margins(5 *getResources().getDimensionPixelSize(R.dimen.psts_dot_m_right), 0, 0, 0);
                    default:
                        break;
                }
            }
            return null;
        }

        @Override
        public Drawable getTipsDrawable(int position) {
            return null;
        }
    }

    public void onDelete(){
        helper = new LoginHelper(TabActivity.this);
        helper.removeAll();
        helper.close();

        Intent intent;
        intent = new Intent(TabActivity.this, LoginActivity.class);
        startActivity(intent);
    }

}
