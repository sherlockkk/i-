package com.alpha.jxust.fragment;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.alpha.jxust.R;
import com.alpha.jxust.base.BaseFragment;
import com.alpha.jxust.tools.ToolLog;
import com.alpha.jxust.utils.CacheUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author SongJian
 * @created 2016/1/18.
 * @e-mail 1129574214@qq.com
 */
public class MineFragment extends BaseFragment implements View.OnClickListener {
    private final String TAG = "MineFragment";

    private ListView listView;
    private ImageView imageView_userIcon;
    private TextView textView_nickName;
    private TextView textView_loc;
    private TextView textView_sex;
    private TextView textView_signature;

    private SimpleAdapter mineListAdapter;
    private String[] options;
    private int[] ids;

    private final int GET_AVATA_FROM_ALBUM = 1;
    private final int GET_AVATA_FROM_CAMERA = 2;
    private final int PHOTO_ZOOM = 3;


    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        listView = (ListView) view.findViewById(R.id.lv_mine_options);
        imageView_userIcon = (ImageView) view.findViewById(R.id.iv_mine_user_icon);
        textView_nickName = (TextView) view.findViewById(R.id.tv_mine_nickname);
        textView_loc = (TextView) view.findViewById(R.id.tv_mine_loc);
        textView_sex = (TextView) view.findViewById(R.id.tv_mine_sex);
        textView_signature = (TextView) view.findViewById(R.id.tv_mine_signature);

        setListener();

        init();
        mineListAdapter = new SimpleAdapter(mActivity, getData(), R.layout.listview_mine, options, ids);

        listView.setAdapter(mineListAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Snackbar.make(view, position + "", Snackbar.LENGTH_SHORT).show();
            }
        });


        return view;
    }

    private void setListener() {
        imageView_userIcon.setOnClickListener(this);
        textView_nickName.setOnClickListener(this);
        textView_loc.setOnClickListener(this);
        textView_sex.setOnClickListener(this);
        textView_signature.setOnClickListener(this);
    }

    private void init() {

        options = new String[]{"img_left", "options_list", "img_right"};
        ids = new int[]{R.id.iv_lv_mine_l, R.id.tv_lv_mine, R.id.iv_lv_mine_r};
    }

    private List<Map<String, Object>> getData() {
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("img_left", R.mipmap.ic_launcher);
        map.put("options_list", "我的资料");
        map.put("img_right", R.mipmap.ic_launcher);
        list.add(map);

        map = new HashMap<>();
        map.put("img_left", R.mipmap.ic_launcher);
        map.put("options_list", "我的收藏");
        map.put("img_right", R.mipmap.ic_launcher);
        list.add(map);

        map = new HashMap<>();
        map.put("img_left", R.mipmap.ic_launcher);
        map.put("options_list", "理工认证");
        map.put("img_right", R.mipmap.ic_launcher);
        list.add(map);

        map = new HashMap<>();
        map.put("img_left", R.mipmap.ic_launcher);
        map.put("options_list", "分享软件");
        map.put("img_right", R.mipmap.ic_launcher);
        list.add(map);

        map = new HashMap<>();
        map.put("img_left", R.mipmap.ic_launcher);
        map.put("options_list", "通用设置");
        map.put("img_right", R.mipmap.ic_launcher);
        list.add(map);

        map = new HashMap<>();
        map.put("img_left", R.mipmap.ic_launcher);
        map.put("options_list", "关于我们");
        map.put("img_right", R.mipmap.ic_launcher);
        list.add(map);

        return list;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_mine_user_icon:
                showSelectDialog();
                break;
            case R.id.tv_mine_nickname:

                break;
            case R.id.tv_mine_loc:

                break;
            case R.id.tv_mine_sex:

                break;
            case R.id.tv_mine_signature:

                break;
            default:
                break;
        }
    }

    AlertDialog selectDialog;

    private void showSelectDialog() {
        selectDialog = new AlertDialog.Builder(mActivity).create();
        selectDialog.setCanceledOnTouchOutside(true);
        View view = LayoutInflater.from(mActivity).inflate(R.layout.dialog_usericon, null);
        selectDialog.show();
        selectDialog.setContentView(view);
        selectDialog.getWindow().setGravity(Gravity.CENTER);

        TextView albumPic = (TextView) view.findViewById(R.id.tv_album_pic);
        TextView cameraPic = (TextView) view.findViewById(R.id.tv_camera_pic);

        albumPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDialog.dismiss();
                getAvataFromAlbum();
            }
        });
        cameraPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDialog.dismiss();
                getAvataFromCamera();
            }
        });

    }


    String dateTime;

    private void getAvataFromAlbum() {
//        Intent intent1 = new Intent(Intent.ACTION_GET_CONTENT);
        Intent intent1 = new Intent(Intent.ACTION_PICK);
        intent1.setType("image/*");
        startActivityForResult(intent1, GET_AVATA_FROM_ALBUM);
    }

    private void getAvataFromCamera() {
        dateTime = getDateTime();
        File f = new File(CacheUtils.getCacheDirectory(mActivity, true, "icon")
                + dateTime);
        if (f.exists()) {
            f.delete();
        }
        try {
            f.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Uri uri = Uri.fromFile(f);
        Log.e("uri", uri + "");

        Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        camera.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(camera, GET_AVATA_FROM_CAMERA);

    }

    public void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");

        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);

        intent.putExtra("outputX", 120);
        intent.putExtra("outputY", 120);
        intent.putExtra("crop", "true");
        intent.putExtra("scale", true);
        intent.putExtra("scaleUpIfNeeded", true);
        // intent.putExtra("noFaceDetection", true);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, PHOTO_ZOOM);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case GET_AVATA_FROM_ALBUM:
                    if (data != null) {
                        Cursor cursor = mActivity.getContentResolver().query(
                                data.getData(), null, null, null, null);
                        cursor.moveToFirst();
                        int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                        String fileSrc = cursor.getString(index);

                        ToolLog.i(TAG, ">>>>>>>>>>>>>>>>>>>>>>>" + fileSrc);

                        File file = new File(fileSrc);
                        if (file.exists() && file.length() > 0) {
                            Uri uri = Uri.fromFile(file);
                            startPhotoZoom(uri);
                        }

                    }
                    break;
                case GET_AVATA_FROM_CAMERA:
                    String files = CacheUtils.getCacheDirectory(mActivity, true, "icon") + dateTime;
                    File file = new File(files);
                    if (file.exists() && file.length() > 0) {
                        Uri uri = Uri.fromFile(file);
                        ToolLog.i(TAG, ">>>>>>>>>uri>>>>>>>>>>>>" + uri.toString());
                        startPhotoZoom(uri);
                    } else {

                    }
                    break;
                case PHOTO_ZOOM:
                    if (data != null) {
                        Bundle extras = data.getExtras();
                        if (extras != null) {
                            Bitmap bitmap = extras.getParcelable("data");

                            String iconUrl = saveToSdCard(bitmap);
                            ToolLog.i(TAG, ">>>>>>iconUrl>>>>>>>>>>>>" + iconUrl);
                            imageView_userIcon.setImageBitmap(bitmap);
                            //updateIcon(iconUrl);
                        }
                    }
                    break;
                default:
                    break;
            }
        }
    }

    public String saveToSdCard(Bitmap bitmap) {
        String files = CacheUtils.getCacheDirectory(mActivity, true, "icon")
                + getDateTime() + "_12.jpg";
        File file = new File(files);
        try {
            FileOutputStream out = new FileOutputStream(file);
            if (bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)) {
                out.flush();
                out.close();
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        ToolLog.i(TAG, file.getAbsolutePath());
        return file.getAbsolutePath();
    }

    private String getDateTime() {
        Date date = new Date(System.currentTimeMillis());
        String dateTime = date.getTime() + "";
        return dateTime;
    }
}
