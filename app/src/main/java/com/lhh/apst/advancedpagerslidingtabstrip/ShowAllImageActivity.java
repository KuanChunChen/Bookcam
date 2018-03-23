package com.lhh.apst.advancedpagerslidingtabstrip;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lhh.apst.fragments.FirstFragment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


//選手機內相片作頭貼
public class ShowAllImageActivity extends Activity {
    public static final String CAMERA_IMAGE_BUCKET_NAME =
            Environment.getExternalStorageDirectory().toString()
                    + "/BookCam";
    public static final String CAMERA_IMAGE_BUCKET_ID =
            getBucketId(CAMERA_IMAGE_BUCKET_NAME);

    public static String getBucketId(String path) {
        return String.valueOf(path.toLowerCase().hashCode());
    }
    /**
     * 存储手机中所有图片的list集合
     */
    List<String> paths = new ArrayList<String>();

    //用来显示手机中所有图片的GridView
    private GridView mGridView;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showallimage);
        context=this;
        mGridView=(GridView) findViewById(R.id.gridview);

        //获得手机中所有图片的路径
        getAllImagePath();

        adapter=new MyGridViewAdapter();
        mGridView.setAdapter(adapter);

        //设置GridView的条目点击事件
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                String path=paths.get(arg2);
                //当我点击某个图片的时候代表要给圆形ImageView设置头像，所以跳转到MainActivity
                Intent intent=new Intent(context,TabActivity.class);
                //仅仅跳转过去不行，必须将当前点击图片的路径带过去
                intent.putExtra("YA", path);
                setResult(1,intent);

                finish();
            }
        });
    }

    private MyGridViewAdapter adapter;

    class MyGridViewAdapter extends BaseAdapter{

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
                convertView=View.inflate(context, R.layout.photo_layout, null);
                vh=new ViewHolder();
                vh.imageView=(ImageView) convertView.findViewById(R.id.photo);
                convertView.setTag(vh);
            }else{
                vh=(ViewHolder) convertView.getTag();
            }
            //当前item要加载的图片路径
            String path=paths.get(position);
            //使用谷歌官方提供的Glide加载图片
            Glide.with(context).load(new File(path)).diskCacheStrategy(DiskCacheStrategy.ALL).centerCrop().into(vh.imageView);

            return convertView;
        }

    }
    class ViewHolder{
        ImageView imageView;
    }

    /**
     * 获得所有图片的路径
     */
    private void getAllImagePath() {
        //selection: 指定查询条件
//设定查询目录

        final String[] columns = {MediaStore.Images.Media.DATA,MediaStore.Images.Media._ID};
        final String orderBy = MediaStore.Images.Media.DATE_ADDED;

        final String selection = MediaStore.Images.Media.BUCKET_ID + " = ?";
        final String[] selectionArgs = {CAMERA_IMAGE_BUCKET_ID};

        Cursor cursor = getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns,null ,null, orderBy);
        //遍历相册
        while (cursor.moveToNext()) {
            String path = cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA));
            //将图片路径添加到集合
            paths.add(path);
        }
        cursor.close();
    }
}
