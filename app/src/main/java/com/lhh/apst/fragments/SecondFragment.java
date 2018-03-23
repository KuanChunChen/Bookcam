package com.lhh.apst.fragments;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.lhh.apst.advancedpagerslidingtabstrip.Book;
import com.lhh.apst.advancedpagerslidingtabstrip.BookHelper;
import com.lhh.apst.advancedpagerslidingtabstrip.R;
import com.lhh.apst.advancedpagerslidingtabstrip.SetList;
import com.lhh.apst.advancedpagerslidingtabstrip.SpecialBook;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class SecondFragment extends Fragment {


    public static SecondFragment instance() {
        SecondFragment view = new SecondFragment();
        return view;
    }

    private BookHelper bkHelper;
    private List<Book> newsList = new ArrayList<>();
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    int[] listIv; //圖示
    List<String> listDataHeader; //標題
    HashMap<String, List<Book>> listDataChild;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.second_fragment, null);

        if (bkHelper == null) {
            bkHelper = new BookHelper(getActivity());
        } else {
        }

        prepareListData();
        expListView = (ExpandableListView) view.findViewById(R.id.mExpandableListView);
        //lvNews.setAdapter(new MemberAdapter(view.getContext(), newsList));
        listAdapter = new ExpandableListAdapter(view.getContext(),listIv, listDataHeader,listDataChild);

        // 將列表資料加入至展開列表單
        expListView.setAdapter(listAdapter);

        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getContext(),
                        "您的" + listDataHeader.get(groupPosition)+"有↑",
                        Toast.LENGTH_SHORT).show();
            }
        });

        // 點選標題監聽事件
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                Toast.makeText(
                        getContext(),
                                listDataHeader.get(groupPosition)
                                + " :   "
                                + listDataChild.get(
                                listDataHeader.get(groupPosition)).get(
                                childPosition).getName()+"  位於書櫃1-"+listDataChild.get(
                                        listDataHeader.get(groupPosition)).get(
                                        childPosition).getRate(), Toast.LENGTH_SHORT)
                        .show();
                return false;
            }
        });

        // 點選標題 收回 監聽事件
        expListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getContext(),
                        "關閉" + listDataHeader.get(groupPosition),
                        Toast.LENGTH_SHORT).show();

            }
        });

        return view;
    }


    public class ExpandableListAdapter extends BaseExpandableListAdapter {

        private Context context;
        private int[] listIv; // 標題圖示
        private List<String> listDataHeader; // 標題
        private HashMap<String, List<Book>> listDataChild; // 內容

        /**
         * @param listIv
         *            -標題圖示
         * @param listDataHeader
         *            -標題
         * @param listChildData
         *            -內容
         */
        public ExpandableListAdapter(Context context, int[] listIv,
                                     List<String> listDataHeader,
                                     HashMap<String, List<Book>> listChildData) {
            this.context = context;
            this.listIv = listIv;
            this.listDataHeader = listDataHeader;
            this.listDataChild = listChildData;
        }

        /* -------------------- 內容 -------------------- */
        public Object getChild(int groupPosition, int childPosititon) {
            return this.listDataChild.get(this.listDataHeader.get(groupPosition))
                    .get(childPosititon);
        }

        public int getChildrenCount(int groupPosition) {
            return this.listDataChild.get(this.listDataHeader.get(groupPosition))
                    .size();
        }

        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        /* 內容View */
        public View getChildView(int groupPosition, final int childPosition,
                                 boolean isLastChild, View convertView, ViewGroup parent) {

          //  final String childText = (String) getChild(groupPosition, childPosition); // 取得內容

  /* 設置內容layout */
            if (convertView == null) {
                LayoutInflater infalInflater = (LayoutInflater) this.context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.sell_list_item ,null);
            }

  /* 設置內容 */

            final Book member = (Book) getChild(groupPosition, childPosition);


            TextView tvName = (TextView) convertView
                    .findViewById(R.id.tvName);
            tvName.setText(member.getName());

            TextView tvNews = (TextView) convertView
                    .findViewById(R.id.tvAuthor);
            tvNews.setText(member.getAuthor());

            return convertView;
        }

        /* -------------------- 標題 -------------------- */
        public Object getGroup(int groupPosition) {
            return this.listDataHeader.get(groupPosition);
        }

        public int getGroupCount() {
            return this.listDataHeader.size();
        }

        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        /* -------------------- 標題圖示 -------------------- */
        public Object getGroupView(int groupPosition) {
            return Integer.toString(this.listIv[groupPosition]);
        }

        public int getGroupViewCount() {
            return this.listIv.length;
        }

        public long getGroupViewId(int groupPosition) {
            return groupPosition;
        }

        /* 標題View */
        public View getGroupView(int groupPosition, boolean isExpanded,
                                 View convertView, ViewGroup parent) {
            String headerIv = (String) getGroupView(groupPosition); // 取得圖示
            String headerTitle = (String) getGroup(groupPosition); // 取得標題
  /* 設置標題layout */
            if (convertView == null) {
                LayoutInflater infalInflater = (LayoutInflater) this.context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.group, null);
            }

  /* 設置圖示 */
            ImageView iv = (ImageView) convertView.findViewById(R.id.titleIcon);
            iv.setImageResource(Integer.parseInt(headerIv));
  /* 設置標題 */
            TextView lblListHeader = (TextView) convertView
                    .findViewById(R.id.ListTitle);
            lblListHeader.setTypeface(null, Typeface.BOLD);
            lblListHeader.setText(headerTitle);

            return convertView;
        }

        /* ------------------------------------------------ */
        public boolean hasStableIds() {
            return false;
        }

        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }

    private void prepareListData() {
        listIv = new int[]{R.mipmap.like,R.mipmap.f2_on}; //圖示
        listDataHeader = new ArrayList<String>(); // 標題
        listDataChild = new HashMap<String, List<Book>>(); // 內容

        listDataHeader.add("收藏書籍");
        listDataHeader.add("推薦一書");

        // Adding child data
        List<Book> first = new ArrayList<Book>();
        first= bkHelper.getLikeBooks();


        List<Book> second = new ArrayList<Book>();
        second= bkHelper.getSellBooks();


        listDataChild.put(listDataHeader.get(0), first); // 標題, 內容
        listDataChild.put(listDataHeader.get(1), second);

    }

}