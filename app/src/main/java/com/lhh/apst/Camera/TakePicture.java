package com.lhh.apst.Camera;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lhh.apst.advancedpagerslidingtabstrip.LoadingActivity;
import com.lhh.apst.advancedpagerslidingtabstrip.R;
import com.lhh.apst.advancedpagerslidingtabstrip.TabActivity;
import com.lidong.photopicker.ImageCaptureManager;
import com.lidong.photopicker.PhotoPickerActivity;
import com.lidong.photopicker.PhotoPreviewActivity;
import com.lidong.photopicker.SelectModel;
import com.lidong.photopicker.intent.PhotoPickerIntent;
import com.lidong.photopicker.intent.PhotoPreviewIntent;

import org.json.JSONArray;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;


public class TakePicture extends AppCompatActivity {

    private static final int REQUEST_CAMERA_CODE = 10;
    private static final int REQUEST_PREVIEW_CODE = 20;
    public static ArrayList<String> imagePaths = new ArrayList<>();
    //arraylist 是java中的一個類別  上面將arraylist指定為string型態
    private ImageCaptureManager captureManager; // 相机拍照处理类
    public  static String Test1;
    private GridView gridView;
    private GridAdapter gridAdapter;
    private Button mButton ,tButton;
    private TextView text,text2,text3;
    private String depp;
    private EditText textView;
    private String TAG =TakePicture.class.getSimpleName();
    private String path1;
    public int size=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take);
        //連接主actvity
        //啟動gridView Button textView

        gridView = (GridView) findViewById(R.id.gridView);
        mButton = (Button) findViewById(R.id.button1);
        tButton = (Button) findViewById(R.id.button);

        int cols = getResources().getDisplayMetrics().widthPixels / getResources().getDisplayMetrics().densityDpi;
        cols = cols < 3 ? 3 : cols;
        gridView.setNumColumns(cols);

        // preview
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String imgs = (String) parent.getItemAtPosition(position); //取得你點的那張圖
                //parent.getItemAtPosition(position);  是在講獲取position位置上的内容然後傳給 String imgs
                //.equals() 方法是判斷實體的內容相不相等
                if ("000000".equals(imgs)) {
                    PhotoPickerIntent intent = new PhotoPickerIntent(TakePicture.this);
                    intent.setSelectModel(SelectModel.MULTI);
                    intent.setShowCarema(true); //允許拍照與否
                    intent.setMaxTotal(6); // 多選照片數量
                    intent.setSelectedPaths(imagePaths); //選中之照片位置
                    size += 1;
                    startActivityForResult(intent, REQUEST_CAMERA_CODE);
                } else {
                    PhotoPreviewIntent intent = new PhotoPreviewIntent(TakePicture.this);
                    intent.setCurrentItem(position);
                    intent.setPhotoPaths(imagePaths);
                    startActivityForResult(intent, REQUEST_PREVIEW_CODE);
                }
            }
        });

        imagePaths.add("000000");
        gridAdapter = new GridAdapter(imagePaths);
        gridView.setAdapter(gridAdapter);

        final String BookCam1 = Environment.getExternalStorageDirectory()+"/BookCam/1.JPG";
        final String BookCam2 = Environment.getExternalStorageDirectory()+"/BookCam/2.JPG";
        final String BookCam3 = Environment.getExternalStorageDirectory()+"/BookCam/3.JPG";
        final String BookCam4 = Environment.getExternalStorageDirectory()+"/BookCam/4.JPG";
        final String BookCam5 = Environment.getExternalStorageDirectory()+"/BookCam/5.JPG";
        final String BookCam6 = Environment.getExternalStorageDirectory()+"/BookCam/6.JPG";

        // 按下建立按鈕 會執行的事件
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncTaskForPostFile PostFile = new AsyncTaskForPostFile(TakePicture.this);
                if (imagePaths.size() == 1) {
                    Toast.makeText(TakePicture.this, "尚未選擇圖片", Toast.LENGTH_SHORT).show();
                } else if (imagePaths.size() == 2) {
                    String FilePath = imagePaths.get(0);
                    PostFile.execute(FilePath);

                } else if (imagePaths.size() == 3) {
                    String FilePath = imagePaths.get(0);
                    String FilePath2 = imagePaths.get(1);
                    PostFile.execute(FilePath, FilePath2);

                } else if (imagePaths.size() == 4) {
                    String FilePath = imagePaths.get(0);
                    String FilePath2 = imagePaths.get(1);
                    String FilePath3 = imagePaths.get(2);
                    PostFile.execute(FilePath, FilePath2, FilePath3);

                } else if (imagePaths.size() == 5) {
                    String FilePath = imagePaths.get(0);
                    String FilePath2 = imagePaths.get(1);
                    String FilePath3 = imagePaths.get(2);
                    String FilePath4 = imagePaths.get(3);
                    PostFile.execute(FilePath, FilePath2, FilePath3, FilePath4);

                } else if (imagePaths.size() == 6) {
                    String FilePath6 = imagePaths.get(5);
                    switch (FilePath6) {

                        case "000000":
                            String FilePath = imagePaths.get(0);
                            String FilePath2 = imagePaths.get(1);
                            String FilePath3 = imagePaths.get(2);
                            String FilePath4 = imagePaths.get(3);
                            String FilePath5 = imagePaths.get(4);
                            PostFile.execute(FilePath, FilePath2, FilePath3, FilePath4, FilePath5);

                            break;


                        default:

                            String FilePatha = imagePaths.get(0);
                            String FilePathb = imagePaths.get(1);
                            String FilePathc = imagePaths.get(2);
                            String FilePathd = imagePaths.get(3);
                            String FilePathe = imagePaths.get(4);

                            PostFile.execute(FilePatha, FilePathb, FilePathc, FilePathd, FilePathe, FilePath6);

                            break;
                    }


                }


                //PostFile.execute(FilePath, FilePath2, FilePath3, FilePath4, FilePath5, FilePath6);
                //UploadFilesss.UploadFiles(FilePath2);
                // text.setText(T);
/*for(int i=1 ;i<3;i++) {
    String FilePath= imagePaths.get(i) ;
    Log.d(TAG, "FilePath : " + FilePath);
    //System.out.print("FilePath:"+FilePath);

    try {
        Thread.sleep(2000);
    } catch (InterruptedException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();}

    PostFile.execute(FilePath, null, null);
    PostFile.cancel(true);
    Log.d(TAG, "0000000 : " + i);
    System.out.print("0000000"+ i);
    //PostFile.execute(FilePath2, null, null);
    //}}

}*/

                Log.d(TAG, "imagePaths: " + "imagePaths = [" + imagePaths.size());
                Log.d(TAG, "imagePaths: " + "imagePaths = [" + imagePaths.get(1));
                //   text.setText(imagePaths.size());
                //text2.setText(imagePaths.get(1));
                // text3.setText(imagePaths.get(2));
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        //UploadFilesss.UploadFiles(imagePaths);
                        // FileUploadManager.uploadMany(imagePaths, depp);
//                        FileUploadManager.upload(imagePaths,depp);
                        //  UploadFilesss.UploadFiles(T);
                    }
                }.start();

                Intent  intent=new Intent(TakePicture.this, LoadingActivity.class);
                intent.putExtra("list", imagePaths);
                startActivity(intent);
                finish();
            }
        });

        // 按下下一層按鈕 會執行的事件
   /*     tButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent().setClass(TakePicture.this, LoadingActivity.class));
                finish();
            }
        });
*/
    }



    protected void onStart(){super.onStart();}

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {
            switch (requestCode) {
                // 選照片
                case REQUEST_CAMERA_CODE:
                    ArrayList<String> list = data.getStringArrayListExtra(PhotoPickerActivity.EXTRA_RESULT);
                    Log.d(TAG, "list: " + "list = [" + list.size());
                    loadAdpater(list);

                    break;
                // 預覽
                case REQUEST_PREVIEW_CODE:
                    ArrayList<String> ListExtra = data.getStringArrayListExtra(PhotoPreviewActivity.EXTRA_RESULT);
                    Log.d(TAG, "ListExtra: " + "ListExtra = [" + ListExtra.size());
                    loadAdpater(ListExtra);
                    break;
            }
        }
    }

   /* public boolean moveFile(String srcFileName, String destDirName) {

        File srcFile = new File(srcFileName);
        if(!srcFile.exists() || !srcFile.isFile())
            return false;

        File destDir = new File(destDirName);
        if (!destDir.exists())
            destDir.mkdirs();


        return srcFile.renameTo(new File(destDirName + File.separator + srcFile.getName()));
    }



        public void copyfile(String srFile, String dtFile){
            try{
                File f1 = new File(srFile);
                File f2 = new File(dtFile);
                f2.createNewFile();
                InputStream in = new FileInputStream(f1);
                OutputStream out = new FileOutputStream(f2);

                byte[] buf = new byte[1024*10];
                int len=0;
                while ((len = in.read(buf)) !=-1){
                    out.write(buf, 0, len);
                }
                if(in!=null) {in.close();}
                if(out!=null) {out.close();}
                System.out.println("File copied.");

            }
            catch(FileNotFoundException ex){
                System.out.println(ex.getMessage() + " in the specified directory.");
                System.exit(0);
            }
            catch(IOException e){
                System.out.println(e.getMessage());
            }
        }

*/

    private void loadAdpater(ArrayList<String> paths){
        if (imagePaths!=null&& imagePaths.size()>0){
            imagePaths.clear();
        }
        if (paths.contains("000000")){
            paths.remove("000000");
        }
        paths.add("000000");
        imagePaths.addAll(paths);
        gridAdapter  = new GridAdapter(imagePaths);
        gridView.setAdapter(gridAdapter);
        try{
            JSONArray obj = new JSONArray(imagePaths);
            Log.e("--", obj.toString());
        }catch (Exception e){
            e.printStackTrace();
        }
    }



    private class GridAdapter extends BaseAdapter {
        private ArrayList<String> listUrls;
        private LayoutInflater inflater;
        public GridAdapter(ArrayList<String> listUrls) {
            this.listUrls = listUrls;
            if(listUrls.size() == 7){
                listUrls.remove(listUrls.size()-1);
            }

            inflater = LayoutInflater.from(TakePicture.this);
        }

        public int getCount(){
            return  listUrls.size();
        }
        @Override
        public String getItem(int position) {
            return listUrls.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
                if (convertView == null) {
                holder = new ViewHolder();
                convertView = inflater.inflate(R.layout.item_image, parent,false);
                holder.image = (ImageView) convertView.findViewById(R.id.imageView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder)convertView.getTag();
            }

            final String path=listUrls.get(position);
            if (path.equals("000000")){
                holder.image.setImageResource(R.mipmap.qqq);
            }else {
                Glide.with(TakePicture.this)
                        .load(path)
                        .placeholder(R.mipmap.default_error)
                        .error(R.mipmap.default_error)
                        .centerCrop()
                        .crossFade()
                        .into(holder.image);
            }
            return convertView;
        }
        class ViewHolder {
            ImageView image;
        }
    }
}
