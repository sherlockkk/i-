package com.alpha.jxust.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.alpha.jxust.controller.StatusController;
import com.alpha.jxust.R;
import com.alpha.jxust.base.BaseActivity;
import com.alpha.jxust.tools.ToolDialog;
import com.alpha.jxust.tools.ToolLog;
import com.alpha.jxust.utils.StatusUtil;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.SaveCallback;

/**
 * @author SongJian
 * @created 2016/3/3.
 * @e-mail 1129574214@qq.com
 */
public class StatusPublishActivity extends BaseActivity {
    public static String TAG = "StatusPublishActivity";
    private EditText editText;
    private ImageView statusPic;
    private Button add_pic, publish;
    Bitmap bitmap;
    private Context context;

    @Override
    protected void initDatas() {

    }

    @Override
    protected void initViews() {
        setContentView(R.layout.status_publish_layout);
        context = this;
    }

    @Override
    protected void findViews() {
        editText = (EditText) findViewById(R.id.et_status_publish);
        statusPic = (ImageView) findViewById(R.id.iv_status_pic);
        add_pic = (Button) findViewById(R.id.btn_add_status_pic);
        add_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePic();
            }
        });
        publish = (Button) findViewById(R.id.btn_publish);
        publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                publishStatus();
            }
        });
    }


    private void choosePic() {
        ToolLog.i(TAG, "选择图片");
        Toast.makeText(context, "选择图片", Toast.LENGTH_SHORT).show();
    }

    private void publishStatus() {
        ToolLog.i(TAG, "选择图片");
        String text = editText.getText().toString().trim();
        if (TextUtils.isEmpty(text) == false || bitmap != null) {
            final ProgressDialog dialog = ToolDialog.showSpinnerDialog(this);
            StatusController.sendStatus(text, bitmap, new SaveCallback() {
                @Override
                public void done(AVException e) {
                    dialog.dismiss();
                    if (StatusUtil.filterException(context, e)) {
                        setResult(RESULT_OK);
                        finish();
                    }
                }
            });
        } else {
            Toast.makeText(context, "请输入内容", Toast.LENGTH_SHORT).show();
        }
    }
}
