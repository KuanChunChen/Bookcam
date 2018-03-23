package com.lhh.apst.advancedpagerslidingtabstrip;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by user on 2016/07/26.
 */
public class CreateActivity extends Activity {

    private TextView text;
    private EditText nick,user,password;
    private Button sign_in_register;
    private Button log_out;
    private RequestQueue requestQueue;
    private static final String URL = "http://140.115.80.225:80/bookcam/user_control.php";
    private StringRequest request;
    private LoginHelper helper;
    private SharedPreferences settings;
    private static final String data = "DATA";
    private static final String nameField = "NAME";
    private File file;
    private static final int REQUEST_TAKE_PICTURE = 0;
    private static final int REQUEST_PICK_IMAGE = 1;
    private String valuestring = null;
    public SharedPreferences setting;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create);

        nick = (EditText) findViewById(R.id.etNick);
        user = (EditText) findViewById(R.id.etName);
        password = (EditText) findViewById(R.id.etPassword);
        sign_in_register = (Button) findViewById(R.id.btFinishInsert);
        text= (TextView) findViewById(R.id.textView);

        if (helper == null) {
            helper = new LoginHelper(this);
        }

        file = new File("/data/data/com.example.user.AndoirdImagePicker/shared_prefs","LoginInfo.xml");
        if(file.exists()) {
            ReadValue();
            if (!valuestring.equals("")) {
                SendIntent();
            }
        }

        TextView button=(TextView)findViewById(R.id.backLogin);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CreateActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        });

        requestQueue = Volley.newRequestQueue(this);

        sign_in_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //text.setText(response);
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.names().get(0).equals("success")) {
                                Toast.makeText(getApplicationContext(), "Sucess： " + jsonObject.getString("success"), Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), TabActivity.class));
                                CreateActivity.this.finish();
                            } else {
                                Toast.makeText(getApplicationContext(), "Error：" + jsonObject.getString("error"), Toast.LENGTH_SHORT).show();
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
                        hashMap.put("nick", nick.getText().toString());
                        hashMap.put("user", user.getText().toString());
                        hashMap.put("password", password.getText().toString());

                        return hashMap;
                    }
                };

                String name = user.getText().toString().trim();
                Logins login = new Logins(name);
                long rowId = helper.insert(login);
                if (rowId != -1) {
                   /* Toast.makeText(LoginActivity.this, R.string.msg_InsertSuccess,
                            Toast.LENGTH_SHORT).show();*/
                } else {
                    /*Toast.makeText(LoginActivity.this, R.string.msg_InsertFail,
                            Toast.LENGTH_SHORT).show();*/
                }

                requestQueue.add(request);
                saveData();
                //CreateActivity.this.finish();
            }
        });
    }

    public void ReadValue(){
        setting = getSharedPreferences("LoginInfo", 0);
        valuestring = setting.getString("VALUESTRING","");
    }
    public void SendIntent(){
        Intent it = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString("VALUE", valuestring);
        it.putExtras(bundle);
        startActivity(it);
        CreateActivity.this.finish();
    }

    public void readData(){
        settings = getSharedPreferences(data, 0);
        user.setText(settings.getString(nameField, ""));

    }
    public void saveData() {
        settings = getSharedPreferences(data,0);
        settings.edit()
                .putString(nameField, user.getText().toString())
                .commit();
    }
}


