package com.cherishTang.laishou.laishou.user.activity;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cherishTang.laishou.R;
import com.cherishTang.laishou.activity.PermissionsActivity;
import com.cherishTang.laishou.api.ApiHttpClient;
import com.cherishTang.laishou.api.ConstantsHelper;
import com.cherishTang.laishou.api.UserAccountHelper;
import com.cherishTang.laishou.base.BaseActivity;
import com.cherishTang.laishou.bean.base.ResultBean;
import com.cherishTang.laishou.custom.customlayout.RoundImageView;
import com.cherishTang.laishou.custom.customlayout.StarBar;
import com.cherishTang.laishou.custom.dialog.CustomProgressDialog;
import com.cherishTang.laishou.custom.dialog.InputDialog;
import com.cherishTang.laishou.custom.dialog.OpenImageDialog;
import com.cherishTang.laishou.custom.dialog.QrCodeDialog;
import com.cherishTang.laishou.custom.multi_image_selector.MultiImageSelector;
import com.cherishTang.laishou.custom.picDialog.PicShowDialog;
import com.cherishTang.laishou.custom.picDialog.bean.ImageInfo;
import com.cherishTang.laishou.custom.recyclerview.FullyGridLayoutManager;
import com.cherishTang.laishou.custom.recyclerview.FullyLinearLayoutManager;
import com.cherishTang.laishou.custom.recyclerview.RecycleViewDivider;
import com.cherishTang.laishou.laishou.consultant.bean.ConsultantBean;
import com.cherishTang.laishou.laishou.main.activity.LaiShowWebViewActivity;
import com.cherishTang.laishou.laishou.main.activity.VideoViewActivity;
import com.cherishTang.laishou.laishou.main.adapter.LaiShowAdapter;
import com.cherishTang.laishou.laishou.user.adapter.ConsultantImageAddAdapter;
import com.cherishTang.laishou.laishou.user.adapter.NutritionConsultantMemberListAdapter;
import com.cherishTang.laishou.laishou.user.bean.BindingBean;
import com.cherishTang.laishou.laishou.user.bean.ElegantUploadImageBean;
import com.cherishTang.laishou.laishou.user.bean.IdBean;
import com.cherishTang.laishou.laishou.user.bean.UpdateIntroduceBean;
import com.cherishTang.laishou.laishou.user.bean.UploadHeadImagBean;
import com.cherishTang.laishou.laishou.user.bean.UserInfo;
import com.cherishTang.laishou.okhttp.callback.StringCallback;
import com.cherishTang.laishou.util.apiUtil.FileUtils;
import com.cherishTang.laishou.util.apiUtil.NumberUtils;
import com.cherishTang.laishou.util.apiUtil.StringUtil;
import com.cherishTang.laishou.util.imgCompressor.ImgCompressor;
import com.cherishTang.laishou.util.log.LogUtil;
import com.cherishTang.laishou.util.log.ToastUtils;
import com.cherishTang.laishou.util.params.DataHolder;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Request;

/**
 * Created by CherishTang on 2019/3/7.
 * describe
 */
public class NutritionConsultantActivity extends BaseActivity implements ConsultantImageAddAdapter.ImageViewAddListener,
        ConsultantImageAddAdapter.ImageViewClearListener, ImgCompressor.CompressListener, OpenImageDialog.OnOpenImageClickListener {
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.starBar)
    StarBar starBar;
    @BindView(R.id.ll_star)
    LinearLayout llStar;
    @BindView(R.id.ll_mark)
    LinearLayout llMark;
    @BindView(R.id.tv_tel)
    TextView tvTel;
    @BindView(R.id.tv_club_name)
    TextView tvClubName;
    @BindView(R.id.tv_describe)
    TextView tvDescribe;
    @BindView(R.id.tv_score)
    TextView tvScore;
    @BindView(R.id.tv_user_number)
    TextView tvUserNumber;
    @BindView(R.id.tv_label)
    TextView tvLabel;
    @BindView(R.id.mRecyclerView_show)
    RecyclerView mRecyclerViewShow;
    @BindView(R.id.mRecyclerView_article)
    RecyclerView mRecyclerViewArticle;
    @BindView(R.id.mRecyclerView_member)
    RecyclerView mRecyclerViewMember;
    @BindView(R.id.rl_member)
    LinearLayout rlMember;
    private LaiShowAdapter laiShowAdapter;
    private ConsultantImageAddAdapter imageShowAdapter;
    private NutritionConsultantMemberListAdapter nutritionConsultantMemberListAdapter;
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
    public static final int CAMERA_REQUEST_CODE_HEAD = 2001; // 拍照权限请求码
    public static final int PHOTO_REQUEST_CAREMA_HEAD = 2002;// 拍照
    public static final int CROP_PHOTO_HEAD = 2003;//裁剪图片
    public static final int CHOOSE_PICTURE_HEAD = 2004;// 相册

    private Uri imageUri;

    public static File tempFile;

    @Override
    public String setTitleBar() {
        return null;
    }

    @Override
    public int initLayout() {
        return R.layout.nutrition_consultant_activity;
    }

    @Override
    public void initView() {

        mSelectPath = new ArrayList<>();
        listImg = new ArrayList<>();

        imageShowAdapter = new ConsultantImageAddAdapter(this);
        imageShowAdapter.setImageViewAddListener(this);
        imageShowAdapter.setImageViewClearListener(this);
        mRecyclerViewShow.setAdapter(imageShowAdapter);
        mRecyclerViewShow.setLayoutManager(new FullyGridLayoutManager(this, 4) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });

        rlMember.setVisibility(View.VISIBLE);
        nutritionConsultantMemberListAdapter = new NutritionConsultantMemberListAdapter(this);
        nutritionConsultantMemberListAdapter.setHeaderView(R.layout.consultant_member_list_item_title);
        mRecyclerViewMember.setAdapter(nutritionConsultantMemberListAdapter);
        mRecyclerViewMember.setLayoutManager(new FullyLinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        mRecyclerViewMember.addItemDecoration(new RecycleViewDivider(this,
                LinearLayoutManager.HORIZONTAL, 1,
                ContextCompat.getColor(this, R.color.default_background)));

        laiShowAdapter = new LaiShowAdapter(this);
        mRecyclerViewArticle.setAdapter(laiShowAdapter);
        mRecyclerViewArticle.setLayoutManager(new FullyLinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        laiShowAdapter.setOnItemClickListener((view, position) -> {
            try {
                LogUtil.show("地址：" + laiShowAdapter.getList().get(position).getArticleContent());
                if (laiShowAdapter.getList().get(position).getIsVideo() != 1) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("contentId", LaiShowWebViewActivity.content_id);
                    bundle.putString("id", laiShowAdapter.getList().get(position).getId());
                    bundle.putString("title", laiShowAdapter.getList().get(position).getArticleTitle());
                    bundle.putString("image", laiShowAdapter.getList().get(position).getImg());
                    DataHolder.getInstance().save(LaiShowWebViewActivity.content_id + "", laiShowAdapter.getList().get(position).getArticleContent());
                    LaiShowWebViewActivity.show(NutritionConsultantActivity.this, bundle);
                    return;
                }
                if (StringUtil.isEmpty(laiShowAdapter.getList().get(position).getArticleContent())) {
                    ToastUtils.showShort(NutritionConsultantActivity.this, "视频地址为空");
                    return;
                }
                Intent intent = new Intent(NutritionConsultantActivity.this, VideoViewActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("videoCoverImage",laiShowAdapter.getList().get(position).getImg());
                bundle.putString("title",laiShowAdapter.getList().get(position).getArticleTitle());
                bundle.putString("path", laiShowAdapter.getList().get(position).getArticleContent());
                intent.putExtras(bundle);
                startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
                LogUtil.show("e:" + e);
                ToastUtils.showShort(NutritionConsultantActivity.this, "播放失败");
            }
//            Bundle bundle = new Bundle();
//            bundle.putInt("contentId", LaiShowWebViewActivity.content_id);
//            DataHolder.getInstance().save(LaiShowWebViewActivity.content_id + "", laiShowAdapter.getList().get(position).getArticleContent());
//            LaiShowWebViewActivity.show(NutritionConsultantActivity.this, bundle);
        });

        mToolbar.inflateMenu(R.menu.toolbar_menu_qr_code);
        mToolbar.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.toolbar_qr_code:
                    BindingBean bindingBean = new BindingBean();
                    bindingBean.setCounselorId(id);
                    new QrCodeDialog(this)
                            .setMessage(new Gson().toJson(bindingBean))
                            .builder()
                            .show();
                    break;
            }
            return false;
        });

    }

    @Override
    public void initData() {
        bundle = getIntent().getExtras() == null ? new Bundle() : getIntent().getExtras();
        id = bundle.getString("id");
        if (!StringUtil.isEmpty(id))
            consultantDetail(new Gson().toJson(new IdBean(id)));
    }

    @OnClick({R.id.tv_modify_introduction, R.id.imageView})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageView:
                OpenImageDialog.getInstance().create(this).show();
                OpenImageDialog.getInstance().setOnOpenImageClickListener(this);
                break;
            case R.id.tv_modify_introduction:
                new InputDialog(this)
                        .setMaxWords(100)
                        .setTipsStr("请输入个人简介")
                        .setDefaultStr(tvDescribe.getText().toString())
                        .setOnSureClickListener((dialog, inputString) -> {
                            if (StringUtil.isEmpty(inputString)) {
                                ToastUtils.showShort(NutritionConsultantActivity.this, "请填写个人简介");
                                return;
                            }
                            dialog.dismiss();
                            UpdateIntroduceBean updateIntroduceBean = new UpdateIntroduceBean();
                            updateIntroduceBean.setIntroduce(inputString);
                            updateIntroduce(new Gson().toJson(updateIntroduceBean));
                        })
                        .builder()
                        .show();
                break;
        }
    }

    /**
     * 加载顾问详情
     *
     * @param postJson
     */
    private void consultantDetail(String postJson) {
        ApiHttpClient.getConsultantDetail(postJson, this, new StringCallback() {
            @Override
            public void onBefore(Request request, int id) {
                super.onBefore(request, id);
                if (customProgressDialog == null || !customProgressDialog.isShowing())
                    customProgressDialog = new CustomProgressDialog(NutritionConsultantActivity.this, R.style.loading_dialog);
                customProgressDialog.setMessage("正在加载，请稍后...");
                customProgressDialog.show();
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtils.showShort(NutritionConsultantActivity.this, R.string.requestError);
            }

            @Override
            public void onAfter(int id) {
                super.onAfter(id);
                if (customProgressDialog != null && customProgressDialog.isShowing())
                    customProgressDialog.dismiss();
            }

            @Override
            public void onResponse(String response, int id) {
                ResultBean<ConsultantBean> resultBean = new GsonBuilder().disableHtmlEscaping().create().
                        fromJson(response, new TypeToken<ResultBean<ConsultantBean>>() {
                        }.getType());
                if (resultBean.isSuccess()) {
                    ToastUtils.showShort(NutritionConsultantActivity.this, StringUtil.isEmpty(resultBean.getMessage()) ? "操作成功" : resultBean.getMessage());
                    updateView(resultBean.getData());
                } else {
                    ToastUtils.showShort(NutritionConsultantActivity.this, StringUtil.isEmpty(resultBean.getMessage()) ? "操作失败" : resultBean.getMessage());
                }
            }
        });

    }

    /**
     * 编辑顾问个人简介
     *
     * @param postJson
     */
    private void updateIntroduce(String postJson) {
        ApiHttpClient.updateIntroduce(postJson, this, new StringCallback() {
            @Override
            public void onBefore(Request request, int id) {
                super.onBefore(request, id);
                if (customProgressDialog == null || !customProgressDialog.isShowing())
                    customProgressDialog = new CustomProgressDialog(NutritionConsultantActivity.this, R.style.loading_dialog);
                customProgressDialog.setMessage("正在加载，请稍后...");
                customProgressDialog.show();
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtils.showShort(NutritionConsultantActivity.this, R.string.requestError);
            }

            @Override
            public void onAfter(int id) {
                super.onAfter(id);
                if (customProgressDialog != null && customProgressDialog.isShowing())
                    customProgressDialog.dismiss();
            }

            @Override
            public void onResponse(String response, int code) {
                ResultBean<ConsultantBean> resultBean = new GsonBuilder().disableHtmlEscaping().create().
                        fromJson(response, new TypeToken<ResultBean<ConsultantBean>>() {
                        }.getType());
                if (resultBean.isSuccess()) {
                    ToastUtils.showShort(NutritionConsultantActivity.this, StringUtil.isEmpty(resultBean.getMessage()) ? "操作成功" : resultBean.getMessage());
                    consultantDetail(new Gson().toJson(new IdBean(id)));
                } else {
                    ToastUtils.showShort(NutritionConsultantActivity.this, StringUtil.isEmpty(resultBean.getMessage()) ? "操作失败" : resultBean.getMessage());
                }
            }
        });

    }

    private void updateView(ConsultantBean consultantBean) {
        if (consultantBean == null) return;
        tvTel.setText(consultantBean.getMobile());
        tvClubName.setText(consultantBean.getClubName());
        tvName.setText(consultantBean.getName());
        starBar.setIntegerMark(true);
        starBar.setStarMark(consultantBean.getScore());
        starBar.setStarClickable(false);
        tvLabel.setText(consultantBean.getLabel());
        tvDescribe.setText(StringUtil.isEmpty(consultantBean.getIntroduce()) ? "未填写" : consultantBean.getIntroduce());
        tvUserNumber.setText("会员：" + NumberUtils.formatInteger(consultantBean.getUserNumber()) + "人");
        tvScore.setText(NumberUtils.decimalFormatInteger(consultantBean.getScore() + "") + "分");
        Glide.with(this)
                .load(consultantBean.getHeadImg())
                .asBitmap()
                .placeholder(R.mipmap.icon_zwf_default)
                .error(R.mipmap.icon_zwf_default)
                .dontAnimate()
                .into(imageView);
        laiShowAdapter.setList(consultantBean.getArticleList());
        laiShowAdapter.notifyDataSetChanged();

        imageShowAdapter.setList(consultantBean.getMienList());
        imageShowAdapter.notifyDataSetChanged();

        nutritionConsultantMemberListAdapter.setList(consultantBean.getUserList());
        nutritionConsultantMemberListAdapter.notifyDataSetChanged();
    }

    @Override
    public void imgClear(View view, int position, String imgUrl) {
        deleteElement(new Gson().toJson(new IdBean(imageShowAdapter.getList().get(position).getId())));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PHOTO_REQUEST_CAREMA_HEAD:
                if (resultCode == RESULT_OK) {
                    Intent intent = new Intent("com.android.camera.action.CROP");
                    intent.setDataAndType(imageUri, "image/*");
                    intent.putExtra("scale", true);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
                    startActivityForResult(intent, ConstantsHelper.CROP_PHOTO); // 启动裁剪程序
                }
                break;
            case CROP_PHOTO_HEAD:
                if (resultCode == RESULT_OK) {
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver()
                                .openInputStream(imageUri));
                        updateHeadImage(FileUtils.getPath(this, imageUri));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case CHOOSE_PICTURE_HEAD:
                if (data == null) return;
                Uri selectedImage = data.getData();
                String[] filePathColumns = {MediaStore.Images.Media.DATA};
                Cursor c = getContentResolver().query(selectedImage, filePathColumns, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePathColumns[0]);
                String imagePath = c.getString(columnIndex);
                updateHeadImage(imagePath);
//                Glide.with(NutritionConsultantActivity.this).load(imagePath).error(R.mipmap.default_head_img).into(imgHeadPortrait);
                c.close();
                break;
            case ConstantsHelper.CHOOSE_PICTURE:
                if (data == null) return;
//                for (String p : mSelectPath) {
//                    if (!data.getStringArrayListExtra(MultiImageSelector.EXTRA_RESULT).contains(p)) {
//                        for (int i = 0; i < uriList.size(); i++) {
//                            if (uriList.get(i) == null || uriList.get(i).getImageUrl().equals(p))
//                                uriList.remove(i);
//                        }
//                    }
//                }
                StringBuilder sb = new StringBuilder();
                for (String p : data.getStringArrayListExtra(MultiImageSelector.EXTRA_RESULT)) {
                    ImgCompressor.getInstance(this).withListener(this).
                            starCompress(p, 600, 800, 300);
                }
                break;
            case 0:
                if (resultCode == PermissionsActivity.PERMISSIONS_GRANTED)
                    openImg();
                else
                    ToastUtils.showShort(this, "权限被拒绝，相册打开失败");
                break;
        }
    }


    /**
     * 上传头像
     *
     * @param path 图片路径
     */
    private void updateHeadImage(String path) {
        File file = new File(path);

        if (!file.exists()) {
            ToastUtils.showShort(this, "文件不存在,请重新选择！");
            return;
        }
        //限定文件大小
        if (FileUtils.getFileSize(path) > (3 * 1024 * 1024)) {
            ToastUtils.showShort(this, "仅限上传3MB以内的文件");
            return;
        }
        uploadHeadImage(file);
    }

    /**
     * 上传图片
     *
     * @param file
     */
    private void uploadHeadImage(File file) {
        ApiHttpClient.uploadImage(file, FileUtils.getFileName(file.getAbsolutePath()), this, new StringCallback() {
            @Override
            public void onBefore(Request request, int id) {
                super.onBefore(request, id);
                if (customProgressDialog == null || !customProgressDialog.isShowing())
                    customProgressDialog = new CustomProgressDialog(NutritionConsultantActivity.this, R.style.loading_dialog);
                customProgressDialog.setMessage("开始上传，请稍后......");
                customProgressDialog.show();
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                if (customProgressDialog != null && customProgressDialog.isShowing())
                    customProgressDialog.dismiss();
                ToastUtils.showShort(NutritionConsultantActivity.this, R.string.requestError);
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
                    postHeadImg(new Gson().toJson(new UploadHeadImagBean(resultBean.getData().getFileName())));
                } else {
                    if (customProgressDialog != null && customProgressDialog.isShowing())
                        customProgressDialog.dismiss();
                    ToastUtils.showShort(NutritionConsultantActivity.this, StringUtil.isEmpty(resultBean.getMessage()) ? "上传失败" : resultBean.getMessage());
                }
            }
        });
    }

    /**
     * 上传头像
     *
     * @param postJson
     */
    private void postHeadImg(String postJson) {
        ApiHttpClient.postCounselorHeadImage(postJson, this, new StringCallback() {
            @Override
            public void onBefore(Request request, int id) {
                super.onBefore(request, id);
                if (customProgressDialog != null && customProgressDialog.isShowing())
                    customProgressDialog.setMessage("正在上传头像，请稍后...");
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtils.showShort(NutritionConsultantActivity.this, R.string.requestError);
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
                    ToastUtils.showShort(NutritionConsultantActivity.this, StringUtil.isEmpty(resultBean.getMessage()) ? "上传成功" : resultBean.getMessage());
                    consultantDetail(new Gson().toJson(new IdBean(id)));//更新用户信息
                } else {
                    ToastUtils.showShort(NutritionConsultantActivity.this, StringUtil.isEmpty(resultBean.getMessage()) ? "上传失败" : resultBean.getMessage());
                }
            }
        });
    }

    @Override
    public void imgAdd(View view) {
        // 缺少权限时, 进入权限配置页面
        if (checkPermission(PERMISSIONS)) {
            requestPermission(NutritionConsultantActivity.this, PERMISSIONS);
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
        selector.start(NutritionConsultantActivity.this, ConstantsHelper.CHOOSE_PICTURE);
    }

    @Override
    public void onCompressStart() {
        if (customProgressDialog == null || !customProgressDialog.isShowing())
            customProgressDialog = new CustomProgressDialog(NutritionConsultantActivity.this, R.style.loading_dialog);
        customProgressDialog.setMessage("正在处理图片，请稍后...");
        customProgressDialog.show();
    }

    @Override
    public void onCompressEnd(ImgCompressor.CompressResult compressResult) {
        if (compressResult.getStatus() == ImgCompressor.CompressResult.RESULT_ERROR)//压缩失败
        {
            errorCount++;
            ToastUtils.showShort(NutritionConsultantActivity.this, "图片处理失败");
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
                ToastUtils.showShort(NutritionConsultantActivity.this, R.string.requestError);
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
                    addElement(new Gson().toJson(new ElegantUploadImageBean(resultBean.getData().getFileName())));
                } else {
                    if (customProgressDialog != null && customProgressDialog.isShowing())
                        customProgressDialog.dismiss();
                    ToastUtils.showShort(NutritionConsultantActivity.this, StringUtil.isEmpty(resultBean.getMessage()) ? "上传失败" : resultBean.getMessage());
                }
            }
        });
    }

    /**
     * 添加风采
     *
     * @param postJson
     */
    private void addElement(String postJson) {
        ApiHttpClient.postElement(postJson, this, new StringCallback() {
            @Override
            public void onBefore(Request request, int id) {
                super.onBefore(request, id);
                if (customProgressDialog != null && customProgressDialog.isShowing())
                    customProgressDialog.setMessage("正在添加风采，请稍后...");
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtils.showShort(NutritionConsultantActivity.this, R.string.requestError);
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
                    ToastUtils.showShort(NutritionConsultantActivity.this, StringUtil.isEmpty(resultBean.getMessage()) ? "上传成功" : resultBean.getMessage());
                    consultantDetail(new Gson().toJson(new IdBean(id)));
                } else {
                    ToastUtils.showShort(NutritionConsultantActivity.this, StringUtil.isEmpty(resultBean.getMessage()) ? "上传失败" : resultBean.getMessage());
                }
            }
        });
    }

    /**
     * 删除风采
     *
     * @param postJson
     */
    private void deleteElement(String postJson) {
        ApiHttpClient.deleteElement(postJson, this, new StringCallback() {
            @Override
            public void onBefore(Request request, int id) {
                super.onBefore(request, id);
                if (customProgressDialog == null || !customProgressDialog.isShowing())
                    customProgressDialog = new CustomProgressDialog(NutritionConsultantActivity.this, R.style.loading_dialog);
                customProgressDialog.setMessage("正在删除，请稍后...");
                customProgressDialog.show();
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtils.showShort(NutritionConsultantActivity.this, R.string.requestError);
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
                    ToastUtils.showShort(NutritionConsultantActivity.this, StringUtil.isEmpty(resultBean.getMessage()) ? "删除成功" : resultBean.getMessage());
                    consultantDetail(new Gson().toJson(new IdBean(id)));
                    checkUserInfo();
                } else {
                    ToastUtils.showShort(NutritionConsultantActivity.this, StringUtil.isEmpty(resultBean.getMessage()) ? "删除失败" : resultBean.getMessage());
                }
            }
        });
    }

    /**
     * 检查登录状态
     */
    private void checkUserInfo() {
        ApiHttpClient.getUserInfo(null, this, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {
                ToastUtils.showShort(NutritionConsultantActivity.this, "网络状态不佳，请稍后再试！");
            }

            @Override
            public void onResponse(String s, int i) {
                ResultBean<UserInfo> resultBean = new GsonBuilder().disableHtmlEscaping().create().
                        fromJson(s, new TypeToken<ResultBean<UserInfo>>() {
                        }.getType());
                if (resultBean.isSuccess()) {
                    UserAccountHelper.saveLoginState(resultBean.getData(), UserAccountHelper.isLogin());
                } else {
                    ToastUtils.showShort(NutritionConsultantActivity.this, StringUtil.isEmpty(resultBean.getMessage()) ? "操作失败" : resultBean.getMessage());
                }
            }
        });
    }

    public static void show(Context context, Bundle bundle) {
        Intent intent = new Intent(context, NutritionConsultantActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    public void openCamera() {
        // 缺少权限时, 进入权限配置页面
        if (checkPermission(PERMISSIONS)) {
            PermissionsActivity.startActivityForResult(NutritionConsultantActivity.this, ConstantsHelper.REQUEST_CODE, PERMISSIONS);
            return;
        }
        camera();
    }

    //打开摄像头并读写文件
    public void camera() {
        //获取系統版本
        int currentapiVersion = Build.VERSION.SDK_INT;
        // 激活相机
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 判断存储卡是否可以用，可用进行存储
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            String filename
                    = Calendar.getInstance().getTimeInMillis() + "";
            File file = new File(Environment.getExternalStorageDirectory(),
                    "/" + ConstantsHelper.ROOTFILEPATH + "/image/");
            if (!file.exists())
                file.mkdirs();

            tempFile = new File(Environment.getExternalStorageDirectory(),
                    "/" + ConstantsHelper.ROOTFILEPATH + "/image/" + filename + ".jpg");
            if (currentapiVersion < 24) {
                // 从文件中创建uri
                imageUri = Uri.fromFile(tempFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            } else {
                //兼容android7.0 使用共享文件的形式
                ContentValues contentValues = new ContentValues(1);
                contentValues.put(MediaStore.Images.Media.DATA, tempFile.getAbsolutePath());
                imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            }
        }
        startActivityForResult(intent, PHOTO_REQUEST_CAREMA_HEAD);
    }

    @Override
    public void openAlbum() {
        //调用相册
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, CHOOSE_PICTURE_HEAD);
    }

}
