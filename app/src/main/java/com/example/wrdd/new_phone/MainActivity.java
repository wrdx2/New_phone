package com.example.wrdd.new_phone;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.ip.ip;

public class MainActivity extends AppCompatActivity {

    private EditText entryzh;
    private EditText entrymm;
    private Button ok;
    private String phone,pass;
    public static String leixing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        entryzh=(EditText)findViewById(R.id.editText2);
        entrymm=(EditText)findViewById(R.id.editText1);
        ok=(Button)findViewById(R.id.login);

    }

    public void login_onClick(View arg0) {
        phone=entryzh.getText().toString().trim();
        pass=entrymm.getText().toString().trim();
        //账号密码为空
        if(phone.equals("")|pass.equals("")){
            Toast.makeText(MainActivity.this, "请输入账号密码", Toast.LENGTH_SHORT).show();
        }
        else{
            try {
                phone= URLEncoder.encode(phone, "UTF-8");
                pass=URLEncoder.encode(pass, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            RequestQueue mQueue = Volley.newRequestQueue(MainActivity.this);
            StringRequest stringRequest = new StringRequest(Method.GET,
                    ip.url+"login?phone="+phone+"&pass="+pass+"&jibie=student",
                    new Response.Listener<String>(){

                        @Override
                        public void onResponse(String arg0) {
                            if("0".equals(arg0)){
                                Toast.makeText(MainActivity.this, "用户名不存在，请注册用户", Toast.LENGTH_SHORT).show();
                            }
                            else if("1".equals(arg0)){
                                Toast.makeText(MainActivity.this, "密码不正确，请输入正确密码", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Intent intent=new Intent();
                                intent.putExtra("no", phone);
                                System.out.println(arg0);
                                intent.setClass(MainActivity.this,TabMenu.class);
                                MainActivity.this.startActivity(intent);
                            }
                        }}, new Response.ErrorListener() {

                public void onErrorResponse(VolleyError error) {
                    System.out.println("error");
                }
            });

            mQueue.add(stringRequest);
        }

    }
}
