package com.lhh.apst.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.lhh.apst.advancedpagerslidingtabstrip.Book;
import com.lhh.apst.advancedpagerslidingtabstrip.BookHelper;
import com.lhh.apst.advancedpagerslidingtabstrip.Load;
import com.lhh.apst.advancedpagerslidingtabstrip.LoginActivity;
import com.lhh.apst.advancedpagerslidingtabstrip.LoginHelper;
import com.lhh.apst.advancedpagerslidingtabstrip.NoScrollListView;
import com.lhh.apst.advancedpagerslidingtabstrip.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Selling extends Fragment {
    private TextView text;
    public TextView name,author;
    public EditText price;
    public ListView listview4;
    private BookHelper bkHelper;
    private List<Book> memberList = new ArrayList<>();
    public RequestQueue requestQueue;
    private static final String URL = "http://140.115.80.225:80/sell/sell.php";
    public StringRequest request;
    private LoginHelper helper;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.selling, container, false);
        if (bkHelper == null) {
            bkHelper = new BookHelper(getActivity());
        } else {
        }


        requestQueue= Volley.newRequestQueue(getActivity());
        listview4 = (ListView) v.findViewById(R.id.listView4);
        listview4.setAdapter(new MemberAdapter(v.getContext(), memberList));

        listview4.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                final View item = LayoutInflater.from(getActivity()).inflate(R.layout.sell_fragment, null);

                final TextView name = (TextView) item
                        .findViewById(R.id.sellName);
                name.setText(memberList.get(position).getName());


                final TextView author = (TextView) item
                        .findViewById(R.id.sellAuthor);
                author.setText(memberList.get(position).getAuthor());


                final TextView tvInformation = (TextView) item
                        .findViewById(R.id.sellPublisher);
                tvInformation.setText(memberList.get(position).getPublisher());


                final TextView isbn = (TextView) item
                        .findViewById(R.id.sellIsbn);
                isbn.setText(memberList.get(position).getLocation());


                final ImageView ivImage = (ImageView) item
                        .findViewById(R.id.sellImage);

                new AsyncTask<String, Void, Bitmap>() {
                    protected Bitmap doInBackground(String... params) {
                        String url = memberList.get(position).getCover();
                        return getBitmapFromURL(url);
                    }

                    protected void onPostExecute(Bitmap result) {
                        ivImage.setImageBitmap(result);
                        super.onPostExecute(result);
                    }
                }.execute(memberList.get(position).getCover());

                final EditText tvPrice = (EditText) item.findViewById(R.id.tvPrice);

                //helper = new LoginHelper(getActivity());
                //helper.newMember();
                //get the new value of the number from the DB
               // Cursor c = helper.newMember();
                //final String str = c.getString(c.getColumnIndex("name"));
                helper = new LoginHelper(getActivity());
                int r=(helper.getAllLogins().size())-1;
                final String st = String.valueOf(helper.getAllLogins().get(r).getName());

                Button sell = (Button) item.findViewById(R.id.btConfirm);
                sell.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        final AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                        dialog.setTitle("賣出書本");
                        dialog.setMessage("確定將此書本賣出？");
                        dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                // TODO Auto-generated method stub

                            }

                        });

                        dialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {

                                request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        try {
                                            //text.setText(response);
                                            JSONObject jsonObject = new JSONObject(response);
                                            if(jsonObject.names().get(0).equals("success")){
                                                Toast.makeText(getActivity(),"Sucess： "+jsonObject.getString("success"),Toast.LENGTH_SHORT).show();
                                            }else {
                                                Toast.makeText(getActivity(), "Error：" +jsonObject.getString("error"), Toast.LENGTH_SHORT).show();
                                            }

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }


                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {

                                    }
                                }) {
                                    @Override
                                    protected Map<String, String> getParams() throws AuthFailureError {
                                        HashMap<String, String> hashMap = new HashMap<String, String>();
                                        hashMap.put("name", name.getText().toString());
                                        hashMap.put("author", author.getText().toString());
                                        hashMap.put("isbn", isbn.getText().toString());
                                        hashMap.put("price", tvPrice.getText().toString());
                                        hashMap.put("user", st);


                                        return hashMap;
                                    }
                                };
                                requestQueue.add(request);
                                dialog.setTitle("已將欲賣書本送至書城");
                                memberList.get(position).setSell(2);
                                Book book=new Book( memberList.get(position).getId(), memberList.get(position).getName(), memberList.get(position).getCover(), memberList.get(position).getAuthor(), memberList.get(position).getPublisher(), memberList.get(position).getLocation(), memberList.get(position).getPicture(), memberList.get(position).getRate() ,memberList.get(position).getLike(),memberList.get(position).getSell());
                                bkHelper.sell(book);
                                startActivity(new Intent(getActivity(), Load.class));
                            }
                        });
                        dialog.show();
                    }

                });
                final Dialog dialog = new AlertDialog.Builder(view.getContext())
                        .setView(item)
                        .create();
                dialog.show();

                ImageButton btClose = (ImageButton) item.findViewById(R.id.btClose);
                btClose.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        dialog.dismiss();

                    }
                });

            }
        });
        return v;
    }


    private class MemberAdapter extends BaseAdapter {

        private LayoutInflater layoutInflater;

        //Book(id,name, cover, author,publisher,location,picture,rate,like,sell)
        public MemberAdapter(Context context, List<Book> memberList) {
            layoutInflater = LayoutInflater.from(context);
            Selling.this.memberList = bkHelper.getSellBooks();
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
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = layoutInflater.inflate(R.layout.sell_list_item, parent, false);
            }
            final Book member = memberList.get(position);

            final ImageButton btSell;
            btSell = (ImageButton)convertView.findViewById(R.id.btSelling);
            if(member.getSell() == 0)btSell.setImageResource(R.mipmap.sell);
            else btSell.setImageResource(R.mipmap.unsell);
            btSell.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (member.getSell() == 0) {  //喜歡就是true
                        member.setSell(1);//將喜歡改為不喜歡
                        btSell.setImageResource(R.mipmap.unsell);
                    } else if (member.getSell() == 1) {
                        member.setSell(0);
                        btSell.setImageResource(R.mipmap.sell);
                       }
                }
            });
            TextView tvName = (TextView) convertView
                    .findViewById(R.id.tvName);
            tvName.setText(member.getName());

            TextView tvAuthor = (TextView) convertView
                    .findViewById(R.id.tvAuthor);
            tvAuthor.setText(member.getAuthor());

            return convertView;
        }
    }

    private static Bitmap getBitmapFromURL(String imageUrl) {
        try {
            java.net.URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            //connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(input);
            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}



