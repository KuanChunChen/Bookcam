package com.lhh.apst.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lhh.apst.advancedpagerslidingtabstrip.Book;
import com.lhh.apst.advancedpagerslidingtabstrip.BookHelper;
import com.lhh.apst.advancedpagerslidingtabstrip.Load;
import com.lhh.apst.advancedpagerslidingtabstrip.NoScrollGridView;
import com.lhh.apst.advancedpagerslidingtabstrip.NoScrollListView;
import com.lhh.apst.advancedpagerslidingtabstrip.R;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class tab03 extends Fragment {
    private List<Book> memberList = new ArrayList<>();
    public static ArrayList<String> image = new ArrayList<>();
    private MemberAdapter adapter;
    public static boolean isGridView = false;
    public NoScrollGridView gridview3;
    public NoScrollListView listview3;
    private BookHelper bkHelper;
    List<String> paths = new ArrayList<String>();

    //用来显示手机中所有图片的GridView

    private MyGridViewAdapter Gadapter;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tab03, container, false);

        bkHelper = new BookHelper(getActivity());

        getAllImagePath();

        listview3 = (NoScrollListView) v.findViewById(R.id.listView3);
        gridview3 = (NoScrollGridView) v.findViewById(R.id.gridView3);
        gridview3.setVisibility(View.VISIBLE);

        Gadapter=new MyGridViewAdapter();
        gridview3.setAdapter(Gadapter);


        listview3.setVisibility(View.GONE);
        listview3.setAdapter(new MemberAdapter(v.getContext(), memberList));

        gridview3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, final View view,
                                    final int position, long id) {
                ImageView imageView = new ImageView(view.getContext());
                String imgs = paths.get(position);
                System.out.println(imgs);
                Bitmap bmp = BitmapFactory.decodeFile(imgs);
                //長按顯示大圖
                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                imageView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                imageView.setImageBitmap(bmp);
                Dialog dialog = new AlertDialog.Builder(view.getContext())
                        .setView(imageView)
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        }).create();
                dialog.show();
            }
        });



        FloatingActionButton button = (FloatingActionButton) v.findViewById(R.id.buttonChange);
        button.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                isGridView = !isGridView;
                if (isGridView) {
                    gridview3.setVisibility(View.VISIBLE);
                    listview3.setVisibility(View.GONE);

                } else {
                    gridview3.setVisibility(View.GONE);
                    listview3.setVisibility(View.VISIBLE);
                    listview3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        public void onItemClick(AdapterView<?> parent, final View view,
                                                final int position, long id) {

                            final View item = LayoutInflater.from(getActivity()).inflate(R.layout.member_fragment, null);

                            final TextView tvName = (TextView) item
                                    .findViewById(R.id.tvName);
                            tvName.setText(memberList.get(position).getName());


                            final TextView tvAuthor = (TextView) item
                                    .findViewById(R.id.tvAuthor);
                            tvAuthor.setText(memberList.get(position).getAuthor());


                            final TextView tvInformation = (TextView) item
                                    .findViewById(R.id.tvPublisher);
                            tvInformation.setText(memberList.get(position).getPublisher());


                            final TextView tvISBN = (TextView) item
                                    .findViewById(R.id.tvISBN);
                            tvISBN.setText(memberList.get(position).getLocation());



                            final ImageView ivImage = (ImageView) item
                                    .findViewById(R.id.ivImage);

                            new AsyncTask<String, Void,Bitmap>(){
                                protected Bitmap doInBackground(String... params){
                                    String url =  memberList.get(position).getCover();
                                    return getBitmapFromURL(url);
                                }

                                protected void onPostExecute(Bitmap result){
                                    ivImage.setImageBitmap(result);
                                    super.onPostExecute(result);
                                }
                            }.execute(memberList.get(position).getCover());

                            final Button btLocation=(Button)item.findViewById(R.id.btLocation);
                            int photo = memberList.get(position).getRate();
                            btLocation.setText("書櫃1-"+photo);
                            Drawable drawable=getResources().getDrawable(R.mipmap.locat);
                            drawable.setBounds(0,0,50,50);
                            btLocation.setCompoundDrawables(drawable,null,null,null);
                            btLocation.setOnClickListener(new Button.OnClickListener() {
                                public void onClick(View v) {
                                    ImageView imageView = new ImageView(view.getContext());
                                    String imgs = memberList.get(position).getPicture();
                                    System.out.println(imgs);
                                    Bitmap bmp = BitmapFactory.decodeFile(imgs);
                                    //長按顯示大圖
                                    imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                                    imageView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                    imageView.setImageBitmap(bmp);
                                    Dialog dialog = new AlertDialog.Builder(v.getContext())
                                            .setView(imageView)
                                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                }
                                            }).create();
                                    dialog.show();
                                }
                            });

                            final ImageButton btlike;
                            btlike = (ImageButton)item.findViewById(R.id.btLike);

                            if(memberList.get(position).getLike()==0)btlike.setImageResource(R.mipmap.like);
                            else btlike.setImageResource(R.mipmap.dislike);
                            btlike.setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    if (memberList.get(position).getLike()==0) {  //喜歡就是true
                                        memberList.get(position).setLike(1);   //將喜歡改為不喜歡
                                        btlike.setImageResource(R.mipmap.dislike);
                                        Toast.makeText(getActivity(), "不設成喜歡",
                                                Toast.LENGTH_SHORT).show();

                                    } else if (memberList.get(position).getLike()==1) {
                                        memberList.get(position).setLike(0);
                                        btlike.setImageResource(R.mipmap.like);
                                        Toast.makeText(getActivity(), "改為喜歡",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                    Book book=new Book( memberList.get(position).getId(), memberList.get(position).getName(), memberList.get(position).getCover(), memberList.get(position).getAuthor(), memberList.get(position).getPublisher(), memberList.get(position).getLocation(), memberList.get(position).getPicture(), memberList.get(position).getRate() ,memberList.get(position).getLike(),memberList.get(position).getSell());
                                    bkHelper.update(book);
                                    startActivity(new Intent().setClass(getActivity(), Load.class));
                                    getActivity().finish();
                                }
                            });

                            final ImageButton btSell;
                            btSell = (ImageButton)item.findViewById(R.id.btSell);
                            if(memberList.get(position).getSell()==1)btSell.setImageResource(R.mipmap.unsell);
                            else btSell.setImageResource(R.mipmap.sell);
                            btSell.setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    if (memberList.get(position).getSell()==0) {  //喜歡就是true
                                        memberList.get(position).setSell(1);//將喜歡改為不喜歡
                                        btSell.setImageResource(R.mipmap.unsell);
                                        Toast.makeText(getActivity(), "不預賣此書",
                                                Toast.LENGTH_SHORT).show();
                                        Book book=new Book( memberList.get(position).getId(), memberList.get(position).getName(), memberList.get(position).getCover(), memberList.get(position).getAuthor(), memberList.get(position).getPublisher(), memberList.get(position).getLocation(), memberList.get(position).getPicture(), memberList.get(position).getRate() ,memberList.get(position).getLike(),memberList.get(position).getSell());
                                        bkHelper.sell(book);
                                        startActivity(new Intent().setClass(getActivity(), Load.class));
                                        getActivity().finish();

                                    } else if (memberList.get(position).getSell()==1) {
                                        memberList.get(position).setSell(0);
                                        btSell.setImageResource(R.mipmap.sell);
                                        Toast.makeText(getActivity(), "預賣此書",
                                                Toast.LENGTH_SHORT).show();
                                        Book book=new Book( memberList.get(position).getId(), memberList.get(position).getName(), memberList.get(position).getCover(), memberList.get(position).getAuthor(), memberList.get(position).getPublisher(), memberList.get(position).getLocation(), memberList.get(position).getPicture(), memberList.get(position).getRate() ,memberList.get(position).getLike(),memberList.get(position).getSell());
                                        bkHelper.sell(book);
                                        startActivity(new Intent().setClass(getActivity(), Load.class));
                                        getActivity().finish();
                                    }
                                    else if (memberList.get(position).getSell()==2) {
                                        Toast.makeText(getActivity(), "此書已上架書城",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });


                            final Dialog dialog = new AlertDialog.Builder(view.getContext())
                                    .setView(item)
                                    .create();
                            dialog.show();

                          ImageButton btClose=(ImageButton)item.findViewById(R.id.btClose);
                            btClose.setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    dialog.dismiss();

                                }
                            });
                        }
                    });
                }
            }
        });
        return v;
    }

    private class MyGridViewAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return paths.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder vh;
            if(convertView==null){
                //这里面的item是一个自定义的View继承线性布局，继承什么布局不重要，
                // 重要的是将item的宽高设置成一样；感觉这个效果项目中很多地方都能用到
                convertView=View.inflate(getContext(), R.layout.photo_layout, null);
                vh=new ViewHolder();
                vh.imageView=(ImageView) convertView.findViewById(R.id.photo);
                convertView.setTag(vh);
            }else{
                vh=(ViewHolder) convertView.getTag();
            }
            //当前item要加载的图片路径
            String path=paths.get(position);
            //使用谷歌官方提供的Glide加载图片
            Glide.with(getContext()).load(new File(path)).diskCacheStrategy(DiskCacheStrategy.ALL).centerCrop().into(vh.imageView);

            return convertView;
        }

    }
    class ViewHolder{
        ImageView imageView;
    }

    public static final String CAMERA_IMAGE_BUCKET_NAME = Environment.getExternalStorageDirectory() + File.separator +"BookCam";
    public static final String CAMERA_IMAGE_BUCKET_ID = getBucketId(CAMERA_IMAGE_BUCKET_NAME);

    public static String getBucketId(String path) {
        return String.valueOf(path.toLowerCase().hashCode());
    }
    private void getAllImagePath() {
        final String[] columns = {MediaStore.Images.Media.DATA,MediaStore.Images.Media._ID};
        final String orderBy = MediaStore.Images.Media.DATE_ADDED;

        final String selection = MediaStore.Images.Media.BUCKET_ID + " = ?";
        final String[] selectionArgs = {CAMERA_IMAGE_BUCKET_ID};

        Cursor cursor = getContext().getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns,selection ,selectionArgs, orderBy);
        //遍历相册
        while (cursor.moveToNext()) {
            String path = cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA));
            //将图片路径添加到集合
            paths.add(path);
        }
        cursor.close();
    }


    private class MemberAdapter extends BaseAdapter {

        private LayoutInflater layoutInflater;

        public MemberAdapter(Context context, List<Book> memberList) {
            layoutInflater = LayoutInflater.from(context);
            tab03.this.memberList = bkHelper.getAllBooks();
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
                convertView = layoutInflater.inflate(R.layout.each_app_list_layout, parent, false);
            }

            final Book member = memberList.get(position);

            TextView tvId = (TextView) convertView
                    .findViewById(R.id.tvId);
            tvId.setText(String.valueOf(member.getId()));


            TextView tvName = (TextView) convertView
                    .findViewById(R.id.tvName);
            tvName.setText(member.getName());

            TextView tvLocat = (TextView) convertView
                    .findViewById(R.id.tvLocat);
            tvLocat.setText("書櫃1-"+member.getRate());

            TextView tvAuthor = (TextView) convertView
                    .findViewById(R.id.tvAuthor);
            tvAuthor.setText(member.getAuthor());

            return convertView;
        }
    }


/*
    protected void removeListItem(final View rowView, final int position) {

        final Animation animation = (Animation) AnimationUtils.loadAnimation(rowView.getContext(), R.anim.item_anim);
        animation.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationStart(Animation animation) {
            }

            public void onAnimationRepeat(Animation animation) {
            }

            public void onAnimationEnd(Animation animation) {
                final Book member = memberList.get(position);
                if (member.getLike()) {
                    Toast.makeText(getActivity(), "喜歡的東西你確定要刪掉?",
                            Toast.LENGTH_SHORT).show();
                } else {

                    memberList.remove(adapter.getItem(position));
                    adapter.notifyDataSetChanged();
                    animation.cancel();
                }
            }
        });
        rowView.startAnimation(animation);
    }*/

    //讀取網路圖片，型態為Bitmap
    private static Bitmap getBitmapFromURL(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
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



