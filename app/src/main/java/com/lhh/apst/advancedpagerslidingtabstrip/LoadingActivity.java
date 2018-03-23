package com.lhh.apst.advancedpagerslidingtabstrip;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TableRow;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class LoadingActivity extends AppCompatActivity {
    private BookHelper bkHelper;
    List<String> paths = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading);

        Intent intent =this.getIntent();
        paths=intent.getStringArrayListExtra("list");

        if (bkHelper==null) {
            bkHelper = new BookHelper(this);
        } else {}


        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()
                .penaltyLog()
                .build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects()
                .penaltyLog()
                .penaltyDeath()
                .build());

        new Thread() {
            public void run() {
                try {
                    String result = DBConnector.executeQuery("SELECT * FROM books");//要撈的資料表
                    System.out.println("1");

                   // JSONArray jsonArray = new JSONArray(result);
                        JSONArray jsonArray = new JSONArray(result);

                    System.out.println("2");
                    for(int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonData = jsonArray.getJSONObject(i);
                        System.out.println("3");

                        String author=jsonData.getString("author");
                        String title=jsonData.getString("title");
                        String cover=jsonData.getString("coverlink");
                        String publisher=jsonData.getString("publisher");
                        String isbn=jsonData.getString("isbn");

                        bkHelper.insert(new Book(title, cover, author,publisher,isbn, paths.get(0), 0, 1, 1));
                    }
                } catch(Exception e) {
                    System.out.println("4");
                    Log.e("log_tag", e.toString());
                }

            }
        }.start();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                /*
                bkHelper.insert(new Book("本日公休", "http://cft.findbook.tw/image/book/9789575605025/large","吳明益","九歌出版社有限公司","9575605020", Environment.getExternalStorageDirectory().toString()
                        + "/BookCam/2.jpg",1, 1, 1));
                bkHelper.insert(new Book("九十年小說選", "http://cft.findbook.tw/image/book/9789575608927/large","李昂/主編","九歌","9575608925", Environment.getExternalStorageDirectory().toString()
                        + "/BookCam/2.jpg",1, 1, 1));
                bkHelper.insert(new Book("虎爺", "http://cft.findbook.tw/image/book/9789574440115/large","吳明益","九歌出版社有限公司","9574440117", Environment.getExternalStorageDirectory().toString()
                        + "/BookCam/2.jpg",1, 1, 1));
                bkHelper.insert(new Book("迷蝶誌", "http://im2.book.com.tw/image/getImage?i=http://www.books.com.tw/img/001/012/71/0010127137.jpg&v=3a67cd2c&w=120&h=120","吳明益","麥田出版社","9574691012", Environment.getExternalStorageDirectory().toString()
                        + "/BookCam/2.jpg",1, 1, 1));
                bkHelper.insert(new Book("台北伊甸園：士林官邸歷史生態", "http://im1.book.com.tw/image/getImage?i=http://www.books.com.tw/img/001/020/56/0010205633.jpg&v=3db5b578&w=348&h=348","七星生態基金會/企劃製作","前衛","9575605020", Environment.getExternalStorageDirectory().toString()
                        + "/BookCam/2.jpg",1, 1, 1));
                bkHelper.insert(new Book("蝶道", "http://cft.findbook.tw/image/book/9789867642134/large","吳明益","二魚文化","9867642139", Environment.getExternalStorageDirectory().toString()
                        + "/BookCam/2.jpg",1, 1, 1));
                bkHelper.insert(new Book("家離水邊那麼近", "http://cft.findbook.tw/image/book/9789867237705/large","吳明益","二魚文化","9867237706", Environment.getExternalStorageDirectory().toString()
                        + "/BookCam/2.jpg",1, 1, 1));
                bkHelper.insert(new Book("睡眠的航線", "http://cft.findbook.tw/image/book/9789867237699/large","吳明益","二魚文化","9867237692", Environment.getExternalStorageDirectory().toString()
                        + "/BookCam/2.jpg",1, 1, 1));
                bkHelper.insert(new Book("迷蝶誌", "http://cft.findbook.tw/image/book/9789868557079/large","吳明益","夏日出版社","9575605020", Environment.getExternalStorageDirectory().toString()
                        + "/BookCam/2.jpg",1, 1, 1));
                bkHelper.insert(new Book("複眼人", "http://cft.findbook.tw/image/book/9789868689510/large","吳明益","夏日出版","9868689511", Environment.getExternalStorageDirectory().toString()
                        + "/BookCam/2.jpg",1, 1, 1));
*/
                bkHelper.insert(new Book("天橋上的魔術師", "http://cft.findbook.tw/image/book/9789868781900/large","吳明益","夏日出版社","9868781906", Environment.getExternalStorageDirectory().toString()
                        + "/BookCam/3.jpg",2, 1, 1));
                bkHelper.insert(new Book("浮光", "http://cft.findbook.tw/image/book/9789865824150/large","吳明益","新經典文化","9865824159", Environment.getExternalStorageDirectory().toString()
                        + "/BookCam/3.jpg",2, 1, 1));
                bkHelper.insert(new Book("浮光", "http://cft.findbook.tw/image/book/9789865824150/large","吳明益","新經典文化","9865824159", Environment.getExternalStorageDirectory().toString()
                        + "/BookCam/3.jpg",2, 1, 1));
                bkHelper.insert(new Book("天橋上的魔術師", "http://cft.findbook.tw/image/book/9789868781900/large","吳明益","夏日出版社","9868781906", Environment.getExternalStorageDirectory().toString()
                        + "/BookCam/3.jpg",2, 1, 1));
                bkHelper.insert(new Book("單車失竊記", "http://cft.findbook.tw/image/book/9789863442448/large","吳明益","麥田","9863442445", Environment.getExternalStorageDirectory().toString()
                        + "/BookCam/3.jpg",2, 1, 1));
                bkHelper.insert(new Book("單車失竊記", "http://cft.findbook.tw/image/book/9789863442448/large","吳明益","麥田","9863442445", Environment.getExternalStorageDirectory().toString()
                        + "/BookCam/3.jpg",2, 1, 1));

                bkHelper.insert(new Book("福爾摩斯", "http://cft.findbook.tw/image/book/9789867939166/large","張鼎譯","博元","??????????", Environment.getExternalStorageDirectory().toString()
                        + "/BookCam/4.jpg",3, 1, 1));
                bkHelper.insert(new Book("三國演義", "http://cft.findbook.tw/image/book/9789867939166/large","（明）羅貫中 ","智楊","??????????", Environment.getExternalStorageDirectory().toString()
                        + "/BookCam/4.jpg",3, 1, 1));
                bkHelper.insert(new Book("西遊記", "http://cft.findbook.tw/image/book/9789867939166/large","吳承恩 ","智楊","??????????", Environment.getExternalStorageDirectory().toString()
                        + "/BookCam/4.jpg",3, 1, 1));
                bkHelper.insert(new Book("濟公傳", "http://cft.findbook.tw/image/book/9789867939166/large","- ","智楊","??????????", Environment.getExternalStorageDirectory().toString()
                        + "/BookCam/4.jpg",3, 1, 1));
                bkHelper.insert(new Book("封神榜", "http://cft.findbook.tw/image/book/9789867939166/large","許仲琳/原著","智楊","??????????", Environment.getExternalStorageDirectory().toString()
                        + "/BookCam/4.jpg",3, 1, 1));

                bkHelper.insert(new Book("Longman Dictionary of American English", "http://cft.findbook.tw/image/book/9780801335211/large","Longman"," ","0801335213", Environment.getExternalStorageDirectory().toString()
                        + "/BookCam/5.jpg",3, 1, 1));
                bkHelper.insert(new Book("新編中國辭典25K", "http://cft.findbook.tw/image/book/9789867939166/large","薛頌留主編","大中國","9575210026", Environment.getExternalStorageDirectory().toString()
                        + "/BookCam/5.jpg",3, 1, 1));
                bkHelper.insert(new Book("成語典", "http://cft.findbook.tw/image/book/9789571442716/large","三民編輯部 ","三民書局股份有限公司","9571442712", Environment.getExternalStorageDirectory().toString()
                        + "/BookCam/5.jpg",3, 1, 1));
                bkHelper.insert(new Book("國語活用辭典(聖)", "http://cft.findbook.tw/image/book/9789571102283/large","周何總主編","五南","9571102288", Environment.getExternalStorageDirectory().toString()
                        + "/BookCam/5.jpg",3, 1, 1));
                bkHelper.insert(new Book("牛津高階英漢雙解詞典", "http://cft.findbook.tw/image/book/9787100062534/large","霍恩比","商務印書館","7100062535", Environment.getExternalStorageDirectory().toString()
                        + "/BookCam/5.jpg",3, 1, 1));

                bkHelper.insert(new Book("射鵰英雄傳(1)", "http://cft.findbook.tw/image/book/9789573229131/large","金庸","遠流","9573229137", Environment.getExternalStorageDirectory().toString()
                        + "/BookCam/6.jpg",4, 1, 1));
                bkHelper.insert(new Book("射鵰英雄傳(2)", "http://cft.findbook.tw/image/book/9789573229148/large","金庸","遠流","9573229145", Environment.getExternalStorageDirectory().toString()
                        + "/BookCam/6.jpg",4, 1, 1));
                bkHelper.insert(new Book("射鵰英雄傳(3)", "http://cft.findbook.tw/image/book/9789573229728/large","金庸","遠流","9573229153", Environment.getExternalStorageDirectory().toString()
                        + "/BookCam/6.jpg",4, 1, 1));
                bkHelper.insert(new Book("射鵰英雄傳(4)", "http://cft.findbook.tw/image/book/9789573229162/large","金庸","遠流","9573229161", Environment.getExternalStorageDirectory().toString()
                        + "/BookCam/6.jpg",4, 1, 1));
                bkHelper.insert(new Book("射鵰英雄傳(5)", "http://cft.findbook.tw/image/book/9789573229742/large","金庸","遠流","9573229749", Environment.getExternalStorageDirectory().toString()
                        + "/BookCam/6.jpg",4, 1, 1));
                bkHelper.insert(new Book("射鵰英雄傳(6)", "http://cft.findbook.tw/image/book/9789573229759/large","金庸","遠流","9573229757", Environment.getExternalStorageDirectory().toString()
                        + "/BookCam/6.jpg",4, 1, 1));
                bkHelper.insert(new Book("射鵰英雄傳(7)", "http://cft.findbook.tw/image/book/9789573229766/large","金庸","遠流","9573229765", Environment.getExternalStorageDirectory().toString()
                        + "/BookCam/6.jpg",4, 1, 1));
                bkHelper.insert(new Book("射鵰英雄傳(8)", "http://cft.findbook.tw/image/book/9789573229773/large","金庸","遠流","9573229773", Environment.getExternalStorageDirectory().toString()
                        + "/BookCam/6.jpg",4, 1, 1));


                startActivity(new Intent().setClass(LoadingActivity.this, TabActivity.class));
                finish();
            }
        }, 3000);

    }





    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
