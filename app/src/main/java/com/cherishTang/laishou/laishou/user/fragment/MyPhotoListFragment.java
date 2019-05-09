package com.cherishTang.laishou.laishou.user.fragment;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.cherishTang.laishou.R;
import com.cherishTang.laishou.activity.PermissionsActivity;
import com.cherishTang.laishou.api.ApiHttpClient;
import com.cherishTang.laishou.api.ConstantsHelper;
import com.cherishTang.laishou.base.BaseRecyclerViewAdapter;
import com.cherishTang.laishou.base.BaseRecyclerViewFragment;
import com.cherishTang.laishou.bean.base.PageBean;
import com.cherishTang.laishou.bean.base.ResultBean;
import com.cherishTang.laishou.custom.dialog.CustomProgressDialog;
import com.cherishTang.laishou.custom.empty.EmptyLayout;
import com.cherishTang.laishou.custom.multi_image_selector.MultiImageSelector;
import com.cherishTang.laishou.custom.recyclerview.FullyGridLayoutManager;
import com.cherishTang.laishou.custom.recyclerview.FullyLinearLayoutManager;
import com.cherishTang.laishou.laishou.user.activity.NutritionConsultantActivity;
import com.cherishTang.laishou.laishou.user.adapter.MyCircleListAdapter;
import com.cherishTang.laishou.laishou.user.adapter.PhotoImageAddAdapter;
import com.cherishTang.laishou.laishou.user.bean.ElegantUploadImageBean;
import com.cherishTang.laishou.laishou.user.bean.IdBean;
import com.cherishTang.laishou.laishou.user.bean.MessageEvent;
import com.cherishTang.laishou.laishou.user.bean.PageRequestBean;
import com.cherishTang.laishou.okhttp.callback.StringCallback;
import com.cherishTang.laishou.util.apiUtil.FileUtils;
import com.cherishTang.laishou.util.apiUtil.StringUtil;
import com.cherishTang.laishou.util.imgCompressor.ImgCompressor;
import com.cherishTang.laishou.util.log.LogUtil;
import com.cherishTang.laishou.util.log.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Request;

/**
 * Created by CherishTang on 2019/3/10.
 * 我的相册
 */

public class MyPhotoListFragment extends BaseRecyclerViewFragment<Object> implements PhotoImageAddAdapter.ImageViewAddListener,
        PhotoImageAddAdapter.ImageViewClearListener, ImgCompressor.CompressListener {
    public static String ARG_PAGE = "ARG_PAGE";
    private int mPage = 0;
    private CustomProgressDialog customProgressDialog;
    private Bundle bundle;
    private String id;
    // 所需的全部权限
    static final String[] PERMISSIONS = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
    };
    private ArrayList<String> mSelectPath;
    private int successCount = 0, errorCount = 0;
    private List<String> listImg = null;

    @Override
    protected Type getType() {
        return new TypeToken<ResultBean<PageBean<Object>>>() {
        }.getType();
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        super.initView(view, savedInstanceState);
        mSelectPath = new ArrayList<>();
        listImg = new ArrayList<>();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void initData(Bundle bundle) {
        super.initData(bundle);
        requestData();
    }

    @Override
    protected void requestData() {
        super.requestData();
        ApiHttpClient.getPhoto(new Gson().toJson(new PageRequestBean(mCurrentPage, ConstantsHelper.indexShowPages)), this, stringCallback);
    }

    @Override
    protected BaseRecyclerViewAdapter<Object> getRecyclerAdapter() {
        PhotoImageAddAdapter photoImageAddAdapter = new PhotoImageAddAdapter(getActivity());
        photoImageAddAdapter.setImageViewAddListener(this);
        photoImageAddAdapter.setImageViewClearListener(this);
        return photoImageAddAdapter;
    }

    @Override
    public RecyclerView.LayoutManager initLayoutManager() {
        return new FullyGridLayoutManager(getActivity(), 4) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
    }

    public static Fragment instantiate(Bundle bundle) {
        Fragment fragment = new MyPhotoListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case ConstantsHelper.CHOOSE_PICTURE:
                if (data == null) return;
                StringBuilder sb = new StringBuilder();
                for (String p : data.getStringArrayListExtra(MultiImageSelector.EXTRA_RESULT)) {
                    ImgCompressor.getInstance(getActivity()).withListener(this).
                            starCompress(p, 600, 800, 300);
                }
                break;
            case 0:
                if (resultCode == PermissionsActivity.PERMISSIONS_GRANTED)
                    openImg();
                else
                    ToastUtils.showShort(getActivity(), "权限被拒绝，相册打开失败");
                break;
        }
    }


    @Override
    public void imgClear(View view, int pos, String imgUrl) {
//        deletePhoto(new Gson().toJson(new IdBean(adapter.getList().get(pos).getId())));

    }


    @Override
    public void imgAdd(View view) {
        // 缺少权限时, 进入权限配置页面
        if (checkPermission(PERMISSIONS)) {
            requestPermission(ConstantsHelper.REQUEST_CODE, getActivity(), PERMISSIONS);
            return;
        }
        openImg();
    }

    private void openImg() {
        MultiImageSelector selector = MultiImageSelector.create();
        selector.showCamera(false);
        selector.count(10);
        selector.multi();
        selector.single();
        selector.origin(mSelectPath);
        selector.start(getActivity(), ConstantsHelper.CHOOSE_PICTURE);
    }

    @Override
    public void onCompressStart() {
        if (customProgressDialog == null || !customProgressDialog.isShowing())
            customProgressDialog = new CustomProgressDialog(getActivity(), R.style.loading_dialog);
        customProgressDialog.setMessage("正在处理图片，请稍后...");
        customProgressDialog.show();
    }

    @Override
    public void onCompressEnd(ImgCompressor.CompressResult compressResult) {
        if (compressResult.getStatus() == ImgCompressor.CompressResult.RESULT_ERROR)//压缩失败
        {
            errorCount++;
            ToastUtils.showShort(getActivity(), "图片处理失败");
            if (customProgressDialog != null && customProgressDialog.isShowing())
                customProgressDialog.dismiss();
            return;
        }
        successCount++;
        File file = new File(compressResult.getOutPath());
        try {
            uploadImage(file);
        } catch (Exception e) {
            e.printStackTrace();
            if (customProgressDialog != null && customProgressDialog.isShowing())
                customProgressDialog.dismiss();
        }
    }

    /**
     * 上传图片
     *
     * @param file
     */
    private void uploadImage(File file) {
        ApiHttpClient.uploadImage(file, FileUtils.getFileName(file.getAbsolutePath()), this, new StringCallback() {
            @Override
            public void onBefore(Request request, int id) {
                super.onBefore(request, id);
                if (customProgressDialog != null && customProgressDialog.isShowing())
                    customProgressDialog.setMessage("开始上传，请稍后...");
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                if (customProgressDialog != null && customProgressDialog.isShowing())
                    customProgressDialog.dismiss();
                ToastUtils.showShort(getActivity(), R.string.requestError);
            }


            @Override
            public void inProgress(float progress, long total, int id) {
                super.inProgress(progress, total, id);
                if (customProgressDialog != null && customProgressDialog.isShowing()) {
                    customProgressDialog.setMessage("已上传：" + ((int) (progress * 100)) + "%");
                }
                LogUtil.show("progress:" + progress);
            }

            @Override
            public void onResponse(String response, int id) {
                ResultBean<ElegantUploadImageBean> resultBean = new GsonBuilder().disableHtmlEscaping().create().
                        fromJson(response, new TypeToken<ResultBean<ElegantUploadImageBean>>() {
                        }.getType());
                if (resultBean.isSuccess() && resultBean.getData() != null) {
                    addPhoto(new Gson().toJson(new ElegantUploadImageBean(resultBean.getData().getFileName())));
                } else {
                    if (customProgressDialog != null && customProgressDialog.isShowing())
                        customProgressDialog.dismiss();
                    ToastUtils.showShort(getActivity(), StringUtil.isEmpty(resultBean.getMessage()) ? "上传失败" : resultBean.getMessage());
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
                ToastUtils.showShort(getActivity(), R.string.requestError);
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
                    ToastUtils.showShort(getActivity(), StringUtil.isEmpty(resultBean.getMessage()) ? "上传成功" : resultBean.getMessage());
                    onRefresh();
                } else {
                    ToastUtils.showShort(getActivity(), StringUtil.isEmpty(resultBean.getMessage()) ? "上传失败" : resultBean.getMessage());
                }
            }
        });
    }

    /**
     * 删除相册
     *
     * @param postJson
     */
    private void deletePhoto(String postJson) {
        ApiHttpClient.deletePhoto(postJson, this, new StringCallback() {
            @Override
            public void onBefore(Request request, int id) {
                super.onBefore(request, id);
                if (customProgressDialog == null || !customProgressDialog.isShowing())
                    customProgressDialog = new CustomProgressDialog(getActivity(), R.style.loading_dialog);
                customProgressDialog.setMessage("正在删除，请稍后...");
                customProgressDialog.show();
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtils.showShort(getActivity(), R.string.requestError);
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
                    ToastUtils.showShort(getActivity(), StringUtil.isEmpty(resultBean.getMessage()) ? "删除成功" : resultBean.getMessage());
                    onRefresh();
                } else {
                    ToastUtils.showShort(getActivity(), StringUtil.isEmpty(resultBean.getMessage()) ? "删除失败" : resultBean.getMessage());
                }
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MessageEvent messageEvent) {
        if ("1".equals(messageEvent.getType())) {
            onRefresh();
            ToastUtils.showShort(getActivity(),"111");

        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
}
