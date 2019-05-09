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
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cherishTang.laishou.R;
import com.cherishTang.laishou.activity.PermissionsActivity;
import com.cherishTang.laishou.api.ApiHttpClient;
import com.cherishTang.laishou.api.ConstantsHelper;
import com.cherishTang.laishou.api.OkhttpsHelper;
import com.cherishTang.laishou.api.RegexUtils;
import com.cherishTang.laishou.api.UserAccountHelper;
import com.cherishTang.laishou.base.BaseActivity;
import com.cherishTang.laishou.bean.PopupWindowBean;
import com.cherishTang.laishou.bean.base.ResultBean;
import com.cherishTang.laishou.custom.customlayout.AutoNextLineLinearlayout;
import com.cherishTang.laishou.custom.customlayout.RoundImageView;
import com.cherishTang.laishou.custom.dialog.CustomProgressDialog;
import com.cherishTang.laishou.custom.dialog.OpenImageDialog;
import com.cherishTang.laishou.custom.dialog.PickDateDialog;
import com.cherishTang.laishou.custom.dialog.SingleSelectedDialog;
import com.cherishTang.laishou.custom.pickertime.PickTimeView;
import com.cherishTang.laishou.enumbean.SexEnum;
import com.cherishTang.laishou.laishou.user.bean.ElegantUploadImageBean;
import com.cherishTang.laishou.laishou.user.bean.UploadHeadImagBean;
import com.cherishTang.laishou.laishou.user.bean.UserInfo;
import com.cherishTang.laishou.okhttp.callback.StringCallback;
import com.cherishTang.laishou.util.apiUtil.DateUtil;
import com.cherishTang.laishou.util.apiUtil.FileUtils;
import com.cherishTang.laishou.util.apiUtil.StringUtil;
import com.cherishTang.laishou.util.log.LogUtil;
import com.cherishTang.laishou.util.log.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Request;

/**
 * Created by CherishTang on 2019/2/28.
 * describe:完善信息
 */
public class PerfectMessageActivity extends BaseActivity implements OpenImageDialog.OnOpenImageClickListener {

    @BindView(R.id.head_image)
    RoundImageView headImage;
    @BindView(R.id.edit_name)
    EditText editName;
    @BindView(R.id.tv_sex)
    TextView tvSex;
    @BindView(R.id.tv_birthday)
    TextView tvBirthday;
    @BindView(R.id.tv_height)
    EditText tvHeight;
    @BindView(R.id.edit_tel)
    EditText editTel;
    @BindView(R.id.tv_weight)
    EditText tvWeight;
    @BindView(R.id.tv_target_weight)
    EditText tvTargetWeight;
    @BindView(R.id.tv_business)
    TextView tvBusiness;
    @BindView(R.id.tv_professional)
    EditText tvProfessional;
    @BindView(R.id.auto_layout)
    AutoNextLineLinearlayout autoLayout;
    List<PopupWindowBean> listSex = null;
    List<String> listHabits = null;
    @BindView(R.id.tv_habit_football)
    TextView tvHabitFootball;
    @BindView(R.id.tv_habit_basketball)
    TextView tvHabitBasketball;
    @BindView(R.id.tv_habit_fitness)
    TextView tvHabitFitness;
    @BindView(R.id.tv_habit_music)
    TextView tvHabitMusic;
    @BindView(R.id.tv_habit_read)
    TextView tvHabitRead;
    @BindView(R.id.tv_habit_game)
    TextView tvHabitGame;
    @BindView(R.id.edit_yw)
    EditText editYw;
    @BindView(R.id.edit_xw)
    EditText editXw;
    @BindView(R.id.edit_tw)
    EditText editTw;
    private CustomProgressDialog customProgressDialog;
    // 所需的全部权限
    static final String[] PERMISSIONS = new String[]{
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
    };

    private boolean isFreshingUserInfo = false;
    private Uri imageUri;

    public static File tempFile;

    @Override
    public String setTitleBar() {
        return "完善信息";
    }

    @Override
    public int initLayout() {
        return R.layout.perfect_message_activity;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        listHabits = new ArrayList<>();
        listSex = new ArrayList<>();
        for (SexEnum sex : SexEnum.values()) {
            listSex.add(new PopupWindowBean(sex.getIndex(), sex.getName()));
        }
        updateView();
    }

    private void updateView() {
        try {
            if (UserAccountHelper.getUser() != null) {
                Glide.with(this)
                        .load(UserAccountHelper.getUser().getHeadImg())
                        .asBitmap()
                        .placeholder(R.mipmap.icon_head_default)
                        .error(R.mipmap.icon_head_default)
                        .dontAnimate()
                        .into(headImage);
                editName.setText(UserAccountHelper.getUser().getName());
                tvWeight.setText(UserAccountHelper.getUser().getWeight());
                tvTargetWeight.setText(UserAccountHelper.getUser().getTargetWeight());
                tvHeight.setText(UserAccountHelper.getUser().getHeight());
                tvBirthday.setText(UserAccountHelper.getUser().getBirthday());
                editYw.setText(UserAccountHelper.getUser().getWaistWai());
                editXw.setText(UserAccountHelper.getUser().getChestWai());
                editTw.setText(UserAccountHelper.getUser().getHipWai());
                editTel.setText(UserAccountHelper.getUser().getMobile());
                setHabitsChecked(UserAccountHelper.getUser().getBefond());
                tvBusiness.setText(UserAccountHelper.getUser().getJob());
                tvProfessional.setText(UserAccountHelper.getUser().getIndustry());
                if (UserAccountHelper.getUser().getSex() != null && UserAccountHelper.getUser().getSex() == SexEnum.female.getIndex()) {
                    tvSex.setText(SexEnum.female.getName());
                    tvSex.setTag(SexEnum.female.getIndex());
                } else if (UserAccountHelper.getUser().getSex() != null && UserAccountHelper.getUser().getSex() == SexEnum.male.getIndex()) {
                    tvSex.setText(SexEnum.male.getName());
                    tvSex.setTag(SexEnum.male.getIndex());

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            ToastUtils.showShort(this, R.string.error);
        }
    }

    private void setHabitsChecked(String habits) {
        if (habits == null)
            return;
        listHabits.clear();
        listHabits.addAll(Arrays.asList(habits.replace("，", ",").split(",")));
        for (int i = 0; i < autoLayout.getChildCount(); i++) {
            TextView childText = (TextView) autoLayout.getChildAt(i);

            if (listHabits.contains(childText.getText().toString()))
                childText.setSelected(true);
        }
    }

    @OnClick({R.id.tv_habit_football, R.id.tv_habit_basketball, R.id.tv_habit_fitness, R.id.tv_habit_music,
            R.id.tv_habit_game, R.id.tv_habit_read})
    public void onHabitClick(View v) {
        if (!fastClick(500)) return;
        TextView textView = (TextView) v;
        String tvStr = textView.getText().toString();
        if (textView.isSelected()) {
            if (listHabits.contains(tvStr))
                listHabits.remove(tvStr);
        } else {
            if (!listHabits.contains(tvStr))
                listHabits.add(tvStr);
        }
        textView.setSelected(!textView.isSelected());
    }

    @OnClick({R.id.tv_sex, R.id.tv_birthday, R.id.button_submit, R.id.head_image})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.head_image:
                if (UserAccountHelper.isLogin()) {
                    OpenImageDialog.getInstance().create(this).show();
                    OpenImageDialog.getInstance().setOnOpenImageClickListener(this);
                } else {
                    showLoginDialog(this, this, "您还未登录，请先登陆", ConstantsHelper.REQUEST_ERROR_LOGIN);
                }
                break;
            case R.id.button_submit:
                if (!fastClick(1000)) return;
//                if (StringUtil.isEmpty(editName.getText().toString())) {
//                    ToastUtils.showShort(this, "请输入姓名");
//                    return;
//                }
//                if (StringUtil.isEmpty(tvSex.getText().toString())) {
//                    ToastUtils.showShort(this, "请选择性别");
//                    return;
//                }
//                if (StringUtil.isEmpty(tvBirthday.getText().toString())) {
//                    ToastUtils.showShort(this, "请选择生日");
//                    return;
//                }
                if (StringUtil.isEmpty(editTel.getText().toString())) {
                    ToastUtils.showShort(this, "请输入手机号码");
                    return;
                }
                if (!RegexUtils.isMobile(editTel.getText().toString())) {
                    ToastUtils.showShort(this, "您输入的手机号码不正确");
                    return;
                }
                if (StringUtil.isEmpty(tvHeight.getText().toString())) {
                    ToastUtils.showShort(this, "请输入身高");
                    return;
                }
                if (StringUtil.isEmpty(tvHeight.getText().toString())) {
                    ToastUtils.showShort(this, "请输入身高");
                    return;
                }
                if (StringUtil.isEmpty(tvWeight.getText().toString())) {
                    ToastUtils.showShort(this, "请输入体重");
                    return;
                }
//                if (StringUtil.isEmpty(tvTargetWeight.getText().toString())) {
//                    ToastUtils.showShort(this, "请输入目标体重");
//                    return;
//                }
//                if (listHabits == null || listHabits.isEmpty()) {
//                    ToastUtils.showShort(this, "请至少选择一个喜好");
//                    return;
//                }
//                if (StringUtil.isEmpty(tvBusiness.getText().toString())) {
//                    ToastUtils.showShort(this, "请输入行业");
//                    return;
//                }
//                if (StringUtil.isEmpty(tvProfessional.getText().toString())) {
//                    ToastUtils.showShort(this, "请输入职业");
//                    return;
//                }
//
//                if (StringUtil.isEmpty(editYw.getText().toString())) {
//                    ToastUtils.showShort(this, "请输入腰围");
//                    return;
//                }
//                if (StringUtil.isEmpty(editXw.getText().toString())) {
//                    ToastUtils.showShort(this, "请输入胸围");
//                    return;
//                }
//                if (StringUtil.isEmpty(editTw.getText().toString())) {
//                    ToastUtils.showShort(this, "请输入臀围");
//                    return;
//                }
                UserInfo userInfo = new UserInfo();
                userInfo.setMobile(editTel.getText().toString());
                userInfo.setName(editName.getText().toString());
                if(!StringUtil.isEmpty(tvSex.getText().toString())){
                    userInfo.setSex(tvSex.getTag() == null ? null : Integer.parseInt(tvSex.getTag().toString()));
                }
                userInfo.setBirthday(tvBirthday.getText().toString());
                userInfo.setHeight(tvHeight.getText().toString());
                userInfo.setWeight(tvWeight.getText().toString());
                userInfo.setTargetWeight(tvTargetWeight.getText().toString());
                if (listHabits != null && !listHabits.isEmpty()) {
                    userInfo.setBefond(StringUtil.listToString(listHabits));
                }
                userInfo.setJob(tvBusiness.getText().toString());
                userInfo.setIndustry(tvProfessional.getText().toString());
                userInfo.setWaistWai(editYw.getText().toString());
                userInfo.setChestWai(editXw.getText().toString());
                userInfo.setHipWai(editTw.getText().toString());
                updateMessage(new Gson().toJson(userInfo));
                break;
            case R.id.tv_sex:
                new SingleSelectedDialog(this, listSex, tvSex).create().show();
                break;
            case R.id.tv_birthday:
                new PickDateDialog(this)
                        .setDateType(PickTimeView.TYPE_PICK_DATE)
                        .setTipMessage("请选择生日")
                        .setDefaultData(tvBirthday.getText().toString())
                        .setOnSureClickListener((dialog, dateLong) -> {
                            dialog.dismiss();
                            tvBirthday.setText(DateUtil.getDateFromMillisNew(dateLong));
                        })
                        .setSimpleDateFormat("yyyy.MM.dd")
                        .builder()
                        .show();
                break;

        }
    }

    /**
     * 更新用户信息
     *
     * @param postJson
     */
    private void updateMessage(String postJson) {
        ApiHttpClient.updateUserInfo(postJson, this, new StringCallback() {
            @Override
            public void onBefore(Request request, int id) {
                super.onBefore(request, id);
                if (customProgressDialog == null || !customProgressDialog.isShowing())
                    customProgressDialog = new CustomProgressDialog(PerfectMessageActivity.this, R.style.loading_dialog);
                customProgressDialog.setMessage("正在加载，请稍后...");
                customProgressDialog.show();
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                if (customProgressDialog != null && customProgressDialog.isShowing())
                    customProgressDialog.dismiss();
                ToastUtils.showShort(PerfectMessageActivity.this, R.string.requestError);
            }

            @Override
            public void onResponse(String response, int id) {
                ResultBean<Object> resultBean = new GsonBuilder().disableHtmlEscaping().create().
                        fromJson(response, new TypeToken<ResultBean<Object>>() {
                        }.getType());
                if (resultBean.isSuccess()) {
                    ToastUtils.showShort(PerfectMessageActivity.this, StringUtil.isEmpty(resultBean.getMessage()) ? "登录成功" : resultBean.getMessage());
                    getUserInfo();
                } else {
                    if (customProgressDialog != null && customProgressDialog.isShowing())
                        customProgressDialog.dismiss();
                    ToastUtils.showShort(PerfectMessageActivity.this, StringUtil.isEmpty(resultBean.getMessage()) ? "登录失败" : resultBean.getMessage());
                }
            }
        });

    }

    /**
     * 获取用户信息
     */
    private void getUserInfo() {

        ApiHttpClient.getUserInfo(null, this, new StringCallback() {
            @Override
            public void onBefore(Request request, int id) {
                super.onBefore(request, id);
                if (customProgressDialog != null || customProgressDialog.isShowing())
                    customProgressDialog.setMessage("正在更新用户信息，请稍后...");
            }

            @Override
            public void onAfter(int id) {
                super.onAfter(id);
                if (customProgressDialog != null && customProgressDialog.isShowing())
                    customProgressDialog.dismiss();
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtils.showShort(PerfectMessageActivity.this, R.string.requestError);
            }

            @Override
            public void onResponse(String response, int id) {
                ResultBean<UserInfo> resultBean = new GsonBuilder().disableHtmlEscaping().create().
                        fromJson(response, new TypeToken<ResultBean<UserInfo>>() {
                        }.getType());
                if (resultBean.isSuccess()) {
                    ToastUtils.showShort(PerfectMessageActivity.this, StringUtil.isEmpty(resultBean.getMessage()) ? "登录成功" : resultBean.getMessage());
                    UserAccountHelper.saveLoginState(resultBean.getData(), true);
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("result", true);
                    setResult(ConstantsHelper.PERFECT_MESSAGE_SUCCESS, getIntent().putExtras(bundle));
                    finish();
                } else {
                    ToastUtils.showShort(PerfectMessageActivity.this, StringUtil.isEmpty(resultBean.getMessage()) ? "登录失败" : resultBean.getMessage());
                }
            }
        });

    }

    public static void show(Context context, Bundle bundle) {
        Intent intent = new Intent(context, PerfectMessageActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    public static void show(Fragment context, Bundle bundle) {
        Intent intent = new Intent(context.getActivity(), PerfectMessageActivity.class);
        intent.putExtras(bundle);
        context.startActivityForResult(intent, ConstantsHelper.PERFECT_MESSAGE_SUCCESS);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case ConstantsHelper.REQUEST_ERROR_LOGIN:
                if (resultCode == ConstantsHelper.LOGIN_SUCCESS)
                    updateView();
                break;
            case ConstantsHelper.PHOTO_REQUEST_CAREMA:
                if (resultCode == RESULT_OK) {
                    Intent intent = new Intent("com.android.camera.action.CROP");
                    intent.setDataAndType(imageUri, "image/*");
                    intent.putExtra("scale", true);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
                    startActivityForResult(intent, ConstantsHelper.CROP_PHOTO); // 启动裁剪程序
                }
                break;
            case ConstantsHelper.CROP_PHOTO:
                if (resultCode == RESULT_OK) {
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(PerfectMessageActivity.this.getContentResolver()
                                .openInputStream(imageUri));
                        updateHeadImage(FileUtils.getPath(PerfectMessageActivity.this, imageUri));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case ConstantsHelper.CHOOSE_PICTURE:
                if (data == null) return;
                Uri selectedImage = data.getData();
                String[] filePathColumns = {MediaStore.Images.Media.DATA};
                Cursor c = PerfectMessageActivity.this.getContentResolver().query(selectedImage, filePathColumns, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePathColumns[0]);
                String imagePath = c.getString(columnIndex);
                updateHeadImage(imagePath);
//                Glide.with(PerfectMessageActivity.this).load(imagePath).error(R.mipmap.default_head_img).into(imgHeadPortrait);
                c.close();
                break;
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
                if (customProgressDialog == null || !customProgressDialog.isShowing())
                    customProgressDialog = new CustomProgressDialog(PerfectMessageActivity.this, R.style.loading_dialog);
                customProgressDialog.setMessage("开始上传，请稍后......");
                customProgressDialog.show();
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                if (customProgressDialog != null && customProgressDialog.isShowing())
                    customProgressDialog.dismiss();
                ToastUtils.showShort(PerfectMessageActivity.this, R.string.requestError);
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
                    ToastUtils.showShort(PerfectMessageActivity.this, StringUtil.isEmpty(resultBean.getMessage()) ? "上传失败" : resultBean.getMessage());
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
        ApiHttpClient.postHeadImage(postJson, this, new StringCallback() {
            @Override
            public void onBefore(Request request, int id) {
                super.onBefore(request, id);
                if (customProgressDialog != null && customProgressDialog.isShowing())
                    customProgressDialog.setMessage("正在上传头像，请稍后...");
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtils.showShort(PerfectMessageActivity.this, R.string.requestError);
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
                    ToastUtils.showShort(PerfectMessageActivity.this, StringUtil.isEmpty(resultBean.getMessage()) ? "上传成功" : resultBean.getMessage());
                    getUserInfo();//更新用户信息
                } else {
                    ToastUtils.showShort(PerfectMessageActivity.this, StringUtil.isEmpty(resultBean.getMessage()) ? "上传失败" : resultBean.getMessage());
                }
            }
        });
    }

    /**
     * 上传头像
     *
     * @param path 图片路径
     */
    private void updateHeadImage(String path) {
        File file = new File(path);

        if (!file.exists()) {
            ToastUtils.showShort(PerfectMessageActivity.this, "文件不存在,请重新选择！");
            return;
        }
        //限定文件大小
        if (FileUtils.getFileSize(path) > (3 * 1024 * 1024)) {
            ToastUtils.showShort(PerfectMessageActivity.this, "仅限上传3MB以内的文件");
            return;
        }
        uploadImage(file);
    }

    @Override
    public void openCamera() {
        // 缺少权限时, 进入权限配置页面
        if (checkPermission(PERMISSIONS)) {
            PermissionsActivity.startActivityForResult(PerfectMessageActivity.this, ConstantsHelper.CAMERA_REQUEST_CODE, PERMISSIONS);
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
        startActivityForResult(intent, ConstantsHelper.PHOTO_REQUEST_CAREMA);
    }

    @Override
    public void openAlbum() {
        //调用相册
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, ConstantsHelper.CHOOSE_PICTURE);
    }
}
