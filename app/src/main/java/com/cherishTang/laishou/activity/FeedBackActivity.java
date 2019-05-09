package com.cherishTang.laishou.activity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.cherishTang.laishou.R;
import com.cherishTang.laishou.adapter.ImageAddAdapter;
import com.cherishTang.laishou.api.ApiAccountHelper;
import com.cherishTang.laishou.api.ConstantsHelper;
import com.cherishTang.laishou.api.OkhttpsHelper;
import com.cherishTang.laishou.base.BaseActivity;
import com.cherishTang.laishou.bean.FeedBackBean;
import com.cherishTang.laishou.bean.ImagePathBean;
import com.cherishTang.laishou.bean.base.ResultBean;
import com.cherishTang.laishou.custom.dialog.CustomProgressDialog;
import com.cherishTang.laishou.custom.dialog.DialogHelper;
import com.cherishTang.laishou.custom.multi_image_selector.MultiImageSelector;
import com.cherishTang.laishou.custom.recyclerview.FullyGridLayoutManager;
import com.cherishTang.laishou.custom.recyclerview.MyGridLayoutManager;
import com.cherishTang.laishou.okhttp.callback.StringCallback;
import com.cherishTang.laishou.util.apiUtil.FileUtils;
import com.cherishTang.laishou.util.apiUtil.StringUtil;
import com.cherishTang.laishou.util.imgCompressor.ImgCompressor;
import com.cherishTang.laishou.util.jiami.Base64Util;
import com.cherishTang.laishou.util.log.ToastUtils;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by 方舟 on 2018/1/22.
 * 问题反馈
 */

public class FeedBackActivity extends BaseActivity implements View.OnClickListener, ImageAddAdapter.ImageViewAddListener,
        ImageAddAdapter.ImageViewClearListener, ImgCompressor.CompressListener {
    @BindView(R.id.proDescribe)
    EditText proDescribe;
    @BindView(R.id.feedBackBtn)
    Button feedBackBtn;
    @BindView(R.id.imgRecyclerView)
    RecyclerView imgRecyclerView;
    @BindView(R.id.edit_type)
    EditText editType;
    @BindView(R.id.edit_qq)
    EditText editQq;
    @BindView(R.id.edit_tel)
    EditText editTel;

    private ImageAddAdapter adapter;
    private List<ImagePathBean> uriList = new CopyOnWriteArrayList<>();
    private ArrayList<String> mSelectPath;
    // 所需的全部权限
    static final String[] PERMISSIONS = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
    };
    private int successCount = 0, errorCount = 0;
    private List<String> listImg = null;
    private CustomProgressDialog customProgressDialog;


    @Override
    public String setTitleBar() {
        return "";
    }

    @Override
    public int initLayout() {
        return R.layout.feedback;
    }

    @Override
    public void initData() {
        mSelectPath = new ArrayList<>();
        listImg = new ArrayList<>();

    }

    @Override
    public void initView() {
        adapter = new ImageAddAdapter(this, uriList);
        adapter.setImageViewAddListener(this);
        adapter.setImageViewClearListener(this);
        imgRecyclerView.setLayoutManager(new FullyGridLayoutManager(this,3){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        imgRecyclerView.setAdapter(adapter);
    }


    public static void show(Context context) {
        Intent intent = new Intent(context, FeedBackActivity.class);
        context.startActivity(intent);
    }

    @OnClick({R.id.feedBackBtn})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.feedBackBtn:
                if (StringUtil.isEmpty(editType.getText().toString())) {
                    ToastUtils.showShort(this, "请填写问题类型");
                    return;
                }
                if (StringUtil.isEmpty(proDescribe.getText().toString())) {
                    ToastUtils.showShort(this, "提交问题内容不能为空");
                    return;
                }
                if (StringUtil.isEmpty(editQq.getText().toString())) {
                    ToastUtils.showShort(this, "请填写QQ号");
                    return;
                }
                if (StringUtil.isEmpty(editTel.getText().toString())) {
                    ToastUtils.showShort(this, "请填写手机号码");
                    return;
                }
                showSureDialog("确定提交反馈内容吗？");
                break;
        }
    }

    private void showSureDialog(String message) {
        DialogHelper.getConfirmDialog(this, message,
                (dialogInterface, i) -> {
                    listImg.clear();
                    customProgressDialog = new CustomProgressDialog(FeedBackActivity.this, R.style.loading_dialog);
                    customProgressDialog.setMessage("正在提交反馈内容，请稍后...");
                    customProgressDialog.show();
                    if (uriList == null || uriList.isEmpty()) {
//                        FeedBackBean feedBackBean = new FeedBackBean(rb.getText().toString(), proDescribe.getText().toString(),
//                                name.getText().toString(), linkWay.getText().toString(), listImg, ApiAccountHelper.getIp(FeedBackActivity.this));
//                        feedBackPost(new GsonBuilder().disableHtmlEscaping().create().toJson(feedBackBean));
                        return;
                    }
                    for (ImagePathBean contents : uriList) {
                        ImgCompressor.getInstance(FeedBackActivity.this).withListener(FeedBackActivity.this).
                                starCompress(contents.getImageUrl(), 600, 800, 300);
                    }
                }).show();
    }

    @Override
    public void imgClear(View view, String imgUrl) {
        if (uriList == null) return;
        for (ImagePathBean contents : uriList) {
            if (contents != null && imgUrl != null && contents.getImageUrl().equals(imgUrl)) {
                uriList.remove(contents);
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
                        for (int i = 0; i < uriList.size(); i++) {
                            if (uriList.get(i) == null || uriList.get(i).getImageUrl().equals(p))
                                uriList.remove(i);
                        }
                    }
                }
                StringBuilder sb = new StringBuilder();
                for (String p : data.getStringArrayListExtra(MultiImageSelector.EXTRA_RESULT)) {
                    if (!mSelectPath.contains(p))
                        uriList.add(new ImagePathBean(null, p));
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
            requestPermission(FeedBackActivity.this, PERMISSIONS);
            return;
        }
        openImg();
    }

    private void openImg() {
        MultiImageSelector selector = MultiImageSelector.create(FeedBackActivity.this);
        selector.showCamera(false);
        selector.count(10);
        selector.multi();
        selector.origin(mSelectPath);
        selector.start(FeedBackActivity.this, ConstantsHelper.CHOOSE_PICTURE);
    }

    @Override
    public void onCompressStart() {
    }

    public void feedBackPost(String postJson) {
        OkhttpsHelper.post("SysGlobal/SaveProblemFedBack", postJson, this, new StringCallback() {
            @Override
            public void onAfter(int id) {
                super.onAfter(id);
                if (customProgressDialog != null && customProgressDialog.isShowing())
                    customProgressDialog.dismiss();
            }

            @Override
            public void onError(Call call, Exception e, int i) {
                ToastUtils.showShort(FeedBackActivity.this, "网络状态不佳，请稍后再试！");
            }

            @Override
            public void onResponse(String s, int i) {
                ResultBean<Object> resultBean = new GsonBuilder().disableHtmlEscaping().create().
                        fromJson(s, new TypeToken<ResultBean<Object>>() {
                        }.getType());
                if (resultBean.isSuccess()) {
                    finish();
                    ToastUtils.showShort(FeedBackActivity.this, "提交成功");
                } else {
                    ToastUtils.showShort(FeedBackActivity.this, resultBean.getMessage());
                }
            }
        });
    }

    @Override
    public void onCompressEnd(ImgCompressor.CompressResult compressResult) {
        if (compressResult.getStatus() == ImgCompressor.CompressResult.RESULT_ERROR)//压缩失败
        {
            errorCount++;
            return;
        }
        successCount++;
        File file = new File(compressResult.getOutPath());
//        Bitmap bitmap = null;
        try {
//            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), Uri.fromFile(file));
//            float imageFileSize = file.length() / 1024f;
            byte[] bys = FileUtils.getFileToByte(new File(file.getAbsolutePath()));

            listImg.add(Base64Util.encode(bys));
            if (errorCount + successCount == uriList.size()) {
//                RadioButton rb = (RadioButton) findViewById(radioGroup.getCheckedRadioButtonId());
//                FeedBackBean feedBackBean = new FeedBackBean(rb.getText().toString(), proDescribe.getText().toString(),
//                        name.getText().toString(), linkWay.getText().toString(), listImg, ApiAccountHelper.getIp(this));
//                feedBackPost(new GsonBuilder().disableHtmlEscaping().create().toJson(feedBackBean));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
