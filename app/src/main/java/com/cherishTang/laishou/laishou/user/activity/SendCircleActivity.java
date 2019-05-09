package com.cherishTang.laishou.laishou.user.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import com.cherishTang.laishou.R;
import com.cherishTang.laishou.activity.FeedBackActivity;
import com.cherishTang.laishou.activity.PermissionsActivity;
import com.cherishTang.laishou.adapter.ImageAddAdapter;
import com.cherishTang.laishou.api.ApiHttpClient;
import com.cherishTang.laishou.api.ConstantsHelper;
import com.cherishTang.laishou.api.OkhttpsHelper;
import com.cherishTang.laishou.base.BaseActivity;
import com.cherishTang.laishou.bean.ImagePathBean;
import com.cherishTang.laishou.bean.base.ResultBean;
import com.cherishTang.laishou.custom.dialog.CustomProgressDialog;
import com.cherishTang.laishou.custom.multi_image_selector.MultiImageSelector;
import com.cherishTang.laishou.custom.recyclerview.FullyGridLayoutManager;
import com.cherishTang.laishou.laishou.user.bean.AddPhotoBean;
import com.cherishTang.laishou.laishou.user.bean.ElegantUploadImageBean;
import com.cherishTang.laishou.okhttp.callback.StringCallback;
import com.cherishTang.laishou.util.apiUtil.FileUtils;
import com.cherishTang.laishou.util.apiUtil.StringUtil;
import com.cherishTang.laishou.util.imgCompressor.ImgCompressor;
import com.cherishTang.laishou.util.jiami.Base64Util;
import com.cherishTang.laishou.util.log.LogUtil;
import com.cherishTang.laishou.util.log.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Request;

/**
 * Created by CherishTang on 2019/3/10.
 * describe
 */
public class SendCircleActivity extends BaseActivity implements ImageAddAdapter.ImageViewAddListener,
        ImageAddAdapter.ImageViewClearListener, ImgCompressor.CompressListener {

    @BindView(R.id.edit_circle_content)
    EditText editCircleContent;
    @BindView(R.id.mRecyclerView_Circle)
    RecyclerView mRecyclerViewCircle;
    private ImageAddAdapter adapter;
    private ArrayList<String> mSelectPath;
    // 所需的全部权限
    static final String[] PERMISSIONS = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
    };
    private int successCount = 0, errorCount = 0;
    private List<File> fileList = new ArrayList<>();
    private StringBuffer sb = new StringBuffer();
    private List<String> listImg = null;
    private CustomProgressDialog customProgressDialog;
    public static final int SEND_CIRCLE_REQUEST_CODE = 202;//发送相册

    @Override
    public String setTitleBar() {
        return null;
    }

    @Override
    public int initLayout() {
        return R.layout.send_circle_activity;
    }

    @Override
    public void initView() {
        mSelectPath = new ArrayList<>();
        listImg = new ArrayList<>();
        adapter = new ImageAddAdapter(this);
        adapter.setImageViewAddListener(this);
        adapter.setImageViewClearListener(this);
        mRecyclerViewCircle.setLayoutManager(new FullyGridLayoutManager(this, 3) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        mRecyclerViewCircle.setAdapter(adapter);

        mToolbar.inflateMenu(R.menu.toolbar_menu_send_submit);
        mToolbar.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.toolbar_send:
                    if ((adapter.getList() == null || adapter.getList().size() == 0) && StringUtil.isEmpty(editCircleContent.getText().toString())) {
                        ToastUtils.showShort(SendCircleActivity.this, "请至少选择一张图片或者填写简介内容");
                    } else {
                        customProgressDialog = new CustomProgressDialog(SendCircleActivity.this, R.style.loading_dialog);
                        customProgressDialog.setMessage("正在加载，请稍后...");
                        customProgressDialog.show();
                        if (adapter.getList() == null || adapter.getList().isEmpty()) {
                            addPhoto(new Gson().toJson(new AddPhotoBean(null, editCircleContent.getText().toString())));
                        } else {
                            fileList.clear();
                            sb = new StringBuffer();
                            for (ImagePathBean contents : adapter.getList()) {
                                ImgCompressor.getInstance(SendCircleActivity.this)
                                        .withListener(SendCircleActivity.this).
                                        starCompress(contents.getImageUrl(), 600, 800, 300);
                            }
                        }
                    }


                    break;
            }
            return false;
        });

    }

    public static void show(Activity context) {
        Intent intent = new Intent(context, SendCircleActivity.class);
        context.startActivityForResult(intent,SEND_CIRCLE_REQUEST_CODE);
    }
    @Override
    public void initData() {

    }

    @Override
    public void imgClear(View view, String imgUrl) {
        if (adapter.getList() == null) return;
        for (ImagePathBean contents : adapter.getList()) {
            if (contents != null && imgUrl != null && contents.getImageUrl().equals(imgUrl)) {
                adapter.getList().remove(contents);
                mSelectPath.remove(imgUrl);
                adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case ConstantsHelper.CHOOSE_PICTURE:
                if (data == null) return;
                for (String p : mSelectPath) {
                    if (!data.getStringArrayListExtra(MultiImageSelector.EXTRA_RESULT).contains(p)) {
                        for (int i = 0; i < adapter.getList().size(); i++) {
                            if (adapter.getList().get(i) == null || adapter.getList().get(i).getImageUrl().equals(p))
                                adapter.getList().remove(i);
                        }
                    }
                }
                StringBuilder sb = new StringBuilder();
                for (String p : data.getStringArrayListExtra(MultiImageSelector.EXTRA_RESULT)) {
                    if (!mSelectPath.contains(p))
                        adapter.getList().add(new ImagePathBean(null, p));
                }
                mSelectPath = data.getStringArrayListExtra(MultiImageSelector.EXTRA_RESULT);
                adapter.notifyDataSetChanged();
                break;
            case 0:
                if (resultCode == PermissionsActivity.PERMISSIONS_GRANTED)
                    openImg();
                else
                    ToastUtils.showShort(this, "权限被拒绝，相册打开失败");
                break;
        }
    }

    @Override
    public void imgAdd(View view) {
        // 缺少权限时, 进入权限配置页面
        if (checkPermission(PERMISSIONS)) {
            requestPermission(SendCircleActivity.this, PERMISSIONS);
            return;
        }
        if (adapter != null && adapter.getList().size() >= 9) {
            ToastUtils.showShort(this, "最多只能上传9张图片");
            return;
        }
        openImg();
    }

    private void openImg() {
        MultiImageSelector selector = MultiImageSelector.create(SendCircleActivity.this);
        selector.showCamera(false);
        selector.count(9);
        selector.multi();
        selector.origin(mSelectPath);
        selector.start(SendCircleActivity.this, ConstantsHelper.CHOOSE_PICTURE);
    }

    @Override
    public void onCompressStart() {

    }

    @Override
    public void onCompressEnd(ImgCompressor.CompressResult compressResult) {
        if (compressResult.getStatus() == ImgCompressor.CompressResult.RESULT_ERROR)//压缩失败
        {
            errorCount++;
            return;
        }
        successCount++;
        fileList.add(new File(compressResult.getOutPath()));
        try {
            if (errorCount + successCount == adapter.getList().size()) {
                Message message = new Message();
                message.what = 0;
                handler.sendMessage(message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            try {
                int count = msg.what;
                LogUtil.show("count:" + count);
                if (fileList != null && count < fileList.size()) {
                    uploadImage(fileList.get(count), count);
                } else {
                    addPhoto(new Gson().toJson(new AddPhotoBean(sb.toString(), editCircleContent.getText().toString())));
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (customProgressDialog != null && customProgressDialog.isShowing())
                    customProgressDialog.dismiss();
            }
        }
    };


    /**
     * 上传图片
     *
     * @param file
     */
    private void uploadImage(File file, int filePosition) {
        ApiHttpClient.uploadImage(file, FileUtils.getFileName(file.getAbsolutePath()), this, new StringCallback() {
            @Override
            public void onBefore(Request request, int id) {
                super.onBefore(request, id);
                if (customProgressDialog != null && customProgressDialog.isShowing())
                    customProgressDialog.setMessage("正在上传，请稍后...");
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                if (customProgressDialog != null && customProgressDialog.isShowing())
                    customProgressDialog.dismiss();
                ToastUtils.showShort(SendCircleActivity.this, R.string.requestError);
            }


            @Override
            public void inProgress(float progress, long total, int id) {
                super.inProgress(progress, total, id);
//                if (customProgressDialog != null && customProgressDialog.isShowing()) {
//                    customProgressDialog.setMessage("正在上传第" + (filePosition + 1) + "张图：" + ((int) (progress * 100)) + "%");
//                }
            }

            @Override
            public void onResponse(String response, int id) {
                ResultBean<ElegantUploadImageBean> resultBean = new GsonBuilder().disableHtmlEscaping().create().
                        fromJson(response, new TypeToken<ResultBean<ElegantUploadImageBean>>() {
                        }.getType());
                if (resultBean.isSuccess() && resultBean.getData() != null) {
                    sb.append(resultBean.getData().getFileName());
                    sb.append(";");
                    Message message = new Message();
                    message.what = filePosition + 1;
                    handler.sendMessage(message);
                } else {
                    if (customProgressDialog != null && customProgressDialog.isShowing())
                        customProgressDialog.dismiss();
                    ToastUtils.showShort(SendCircleActivity.this, StringUtil.isEmpty(resultBean.getMessage()) ? "上传失败" : resultBean.getMessage());
                }
            }
        });
    }

    /**
     * 添加图片
     *
     * @param postJson
     */
    private void addPhoto(String postJson) {
        ApiHttpClient.postPhoto(postJson, this, new StringCallback() {
            @Override
            public void onBefore(Request request, int id) {
                super.onBefore(request, id);
                if (customProgressDialog != null && customProgressDialog.isShowing())
                    customProgressDialog.setMessage("正在添加相册，请稍后...");
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtils.showShort(SendCircleActivity.this, R.string.requestError);
            }

            @Override
            public void onAfter(int id) {
                super.onAfter(id);
                if (customProgressDialog != null && customProgressDialog.isShowing())
                    customProgressDialog.dismiss();
            }

            @Override
            public void onResponse(String response, int code) {
                ResultBean<Object> resultBean = new GsonBuilder().disableHtmlEscaping().create().
                        fromJson(response, new TypeToken<ResultBean<Object>>() {
                        }.getType());
                if (resultBean.isSuccess()) {
                    ToastUtils.showShort(SendCircleActivity.this, StringUtil.isEmpty(resultBean.getMessage()) ? "上传成功" : resultBean.getMessage());
                    setResult(RESULT_OK);
                    finish();
                } else {
                    ToastUtils.showShort(SendCircleActivity.this, StringUtil.isEmpty(resultBean.getMessage()) ? "上传失败" : resultBean.getMessage());
                }
            }
        });
    }


}
