package com.alpha.jxust.activity;

import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alpha.jxust.R;
import com.alpha.jxust.base.BaseActivity;
import com.alpha.jxust.tools.ToolLog;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SignUpCallback;

/**
 * @author SongJian
 * @created 2016/2/10.
 * @e-mail 1129574214@qq.com
 */
public class RegisterActivity extends BaseActivity {
    private static String TAG = "RegisterActivity";
    private EditText et_username, et_password, et_password_again;
    private Button register;
    private String username, password;


    @Override
    protected void initDatas() {

    }

    @Override
    protected void initViews() {
        setContentView(R.layout.activity_register);
    }

    @Override
    protected void findViews() {
        et_username = (EditText) findViewById(R.id.et_username);
        et_password = (EditText) findViewById(R.id.et_password);
        et_password_again = (EditText) findViewById(R.id.et_password_again);
        register = (Button) findViewById(R.id.btn_register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = et_username.getText().toString().trim();
                if (!username.equals("") && checkPassword()) {
                    //执行登录
                    AVUser user = new AVUser();
                    user.setUsername(username);
                    user.setPassword(password);
                    user.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(AVException e) {
                            if (e==null){
                                ToolLog.i(TAG,"---->>:register successfully!");
                                startActivity(LoginActivity.class,null,true);
                            }else {
                                ToolLog.i(TAG,"---->>:register failed!--"+e.toString());
                            }
                        }
                    });
                } else {
                    Toast.makeText(RegisterActivity.this, "用户名不能为空！", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * 检查密码输入规范
     *
     * @return
     */
    private boolean checkPassword() {
        password = et_password.getText().toString();
        String password2 = et_password_again.getText().toString();
        if (password.equals("")) {
            Toast.makeText(RegisterActivity.this, "请输入密码！", Toast.LENGTH_SHORT).show();
            return false;
        } else if (password2.equals("")) {
            Toast.makeText(RegisterActivity.this, "请再次输入密码！", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!password.equals(password2)) {
            Toast.makeText(RegisterActivity.this, "两次输入密码不一致，请重新输入！", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }


}
