package com.alpha.jxust.activity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alpha.jxust.R;
import com.alpha.jxust.base.BaseActivity;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;

/**
 * @author SongJian
 * @created 2016/2/10.
 * @e-mail 1129574214@qq.com
 */
public class LoginActivity extends BaseActivity {
    private static String TAG = "LoginActivity";
    private TextView textView;
    private EditText username, password;
    private Button login;
    private String user, psd;

    @Override
    protected void initDatas() {

    }

    @Override
    protected void initViews() {
        setContentView(R.layout.activity_login);
    }

    @Override
    protected void findViews() {
        textView = (TextView) findViewById(R.id.tv_register);
        username = (EditText) findViewById(R.id.et_username_login);
        password = (EditText) findViewById(R.id.et_password_login);

        login = (Button) findViewById(R.id.btn_login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user = username.getText().toString().trim();
                psd = password.getText().toString();
                checkUserInfo();
                if (checkUserInfo()){
                    AVUser.logInInBackground(user, psd, new LogInCallback<AVUser>() {
                        @Override
                        public void done(AVUser avUser, AVException e) {
                            if (e == null) {
                                Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                                startActivity(MainActivity.class, null, true);
                            } else {
                                Toast.makeText(LoginActivity.this, "登录失败！请重试", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(RegisterActivity.class, null, false);
            }
        });
    }

    private boolean checkUserInfo() {
        if (user.equals("")){
            Toast.makeText(LoginActivity.this,"请输入用户名",Toast.LENGTH_SHORT).show();
            return false;
        }else if (psd.equals("")){
            Toast.makeText(LoginActivity.this,"请输入密码",Toast.LENGTH_SHORT).show();
            return false;
        }
            return true;
    }



}
