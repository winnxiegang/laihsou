package com.cherishTang.laishou.laishou.main.fragment;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cherishTang.laishou.R;
import com.cherishTang.laishou.activity.FeedBackActivity;
import com.cherishTang.laishou.activity.PermissionsActivity;
import com.cherishTang.laishou.api.ApiHttpClient;
import com.cherishTang.laishou.api.ConstantsHelper;
import com.cherishTang.laishou.api.UserAccountHelper;
import com.cherishTang.laishou.base.BaseFragment;
import com.cherishTang.laishou.bean.base.ResultBean;
import com.cherishTang.laishou.custom.customlayout.CustomPersonItemTextView;
import com.cherishTang.laishou.custom.customlayout.CustomTextView;
import com.cherishTang.laishou.custom.customlayout.RoundImageView;
import com.cherishTang.laishou.custom.dialog.CallDialog;
import com.cherishTang.laishou.custom.dialog.CustomProgressDialog;
import com.cherishTang.laishou.custom.dialog.OpenImageDialog;
import com.cherishTang.laishou.enumbean.LoginTypeEnum;
import com.cherishTang.laishou.laishou.club.activity.LaiShowClubDetailActivity;
import com.cherishTang.laishou.laishou.consultant.activity.ConsultantListActivity;
import com.cherishTang.laishou.laishou.main.activity.CouponsListActivity;
import com.cherishTang.laishou.laishou.main.activity.HistoryActivity;
import com.cherishTang.laishou.laishou.main.activity.SignInActivity;
import com.cherishTang.laishou.laishou.setting.activity.SettingActivity;
import com.cherishTang.laishou.laishou.user.activity.DataRecordActivity;
import com.cherishTang.laishou.laishou.user.activity.JoinInActivity;
import com.cherishTang.laishou.laishou.user.activity.LoginActivity;
import com.cherishTang.laishou.laishou.user.activity.MyCircleActivity;
import com.cherishTang.laishou.laishou.user.activity.MyCollectActivity;
import com.cherishTang.laishou.laishou.user.activity.MyGradeActivity;
import com.cherishTang.laishou.laishou.user.activity.MyOrderActivity;
import com.cherishTang.laishou.laishou.user.activity.MyPromoteActivity;
import com.cherishTang.laishou.laishou.user.activity.MyPromoteListActivity;
import com.cherishTang.laishou.laishou.user.activity.NutritionConsultantActivity;
import com.cherishTang.laishou.laishou.user.activity.PerfectMessageActivity;
import com.cherishTang.laishou.laishou.user.bean.UserInfo;
import com.cherishTang.laishou.okhttp.callback.StringCallback;
import com.cherishTang.laishou.util.QRCodeUtil;
import com.cherishTang.laishou.util.apiUtil.NumberUtils;
import com.cherishTang.laishou.util.apiUtil.StringUtil;
import com.cherishTang.laishou.util.log.LogUtil;
import com.cherishTang.laishou.util.log.ToastUtils;
import com.cherishTang.laishou.util.permission.PermissionUtils;
import com.cherishTang.laishou.util.permission.PermissionsChecker;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.sxu.shadowdrawable.ShadowDrawable;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.Call;

import static android.app.Activity.RESULT_OK;

/**
 * Created by 方舟 on 2017/5/24.
 * 我的页面
 */

public class MeFragment extends BaseFragment implements OpenImageDialog.OnOpenImageClickListener {
    @BindView(R.id.headImg)
    RoundImageView headImg;
    @BindView(R.id.counselorHeadImg)
    RoundImageView counselorHeadImg;
    @BindView(R.id.ll_register_day)
    LinearLayout llRegisterDay;
    @BindView(R.id.custom_grade)
    CustomTextView customGrade;
    @BindView(R.id.custom_circle)
    CustomTextView customCircle;
    @BindView(R.id.custom_club)
    CustomTextView customClub;
    @BindView(R.id.custom_housekeeper)
    CustomTextView customHousekeeper;
    @BindView(R.id.ll_user_message)
    LinearLayout llUserMessage;
    @BindView(R.id.tv_user_no)
    TextView tvUserNo;
    @BindView(R.id.tv_day)
    TextView tvDay;
    @BindView(R.id.tv_order_num)
    TextView tvOrderNum;
    @BindView(R.id.tv_publish_num)
    TextView tvPublishNum;
    @BindView(R.id.tv_collection_num)
    TextView tvCollectionNum;
    @BindView(R.id.ll_current_weight)
    LinearLayout llCurrentWeight;
    @BindView(R.id.club)
    TextView club;
    @BindView(R.id.tv_current_weight)
    TextView tvCurrentWeight;
    @BindView(R.id.tv_target_weight)
    TextView tvTargetWeight;
    @BindView(R.id.tv_lose_weight)
    TextView tvLoseWeight;
    @BindView(R.id.tv_club_name)
    TextView tvClubName;
    @BindView(R.id.tv_consultant)
    TextView tvConsultant;
    @BindView(R.id.tv_reservation)
    TextView tvReservation;
    @BindView(R.id.tv_binding_consultant)
    TextView tvBindingConsultant;
    @BindView(R.id.tv_my_plan)
    TextView tvMyPlan;
    @BindView(R.id.tv_club_number)
    TextView tvClubNumber;
    @BindView(R.id.ll_plan)
    LinearLayout llPlan;
    @BindView(R.id.ll_order)
    LinearLayout llOrder;
    @BindView(R.id.ll_collect)
    LinearLayout llCollect;
    @BindView(R.id.image_sign_in)
    ImageView imageSignIn;
    @BindView(R.id.v_nutrition_consultant)
    View vNutritionConsultant;
    @BindView(R.id.tv_nutrition_consultant)
    CustomPersonItemTextView tvNutritionConsultant;
    @BindView(R.id.ll_activity)
    LinearLayout llActivity;
    @BindView(R.id.tv_data_sync)
    CustomPersonItemTextView tvDataSync;
    private Uri imageUri;

    public static File tempFile;
    public static final int PHOTO_REQUEST_CAREMA = 1;// 拍照
    public static final int CROP_PHOTO = 2;
    private static final int REQUEST_CODE = 4; // 权限请求码
    private static final int TAKEPHOTO = 0;//相册

    private static final int LOGINFLAG_IMG = 202;
    private static final int LOGINFLAG = 201;

    // 所需的全部权限
    static final String[] PERMISSIONS = new String[]{
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
    };
    private PermissionsChecker mPermissionsChecker; // 权限检测器

    private CustomProgressDialog customProgressDialog = null;
    static String[] PERMISSIONS_CAMERA = new String[]{
            Manifest.permission.CAMERA,
    };

    @Override
    protected int getLayoutId() {
        return R.layout.me_fragment;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        customGrade.init(R.mipmap.me_menu_cj, R.string.me_menu_cj, "1");
        customCircle.init(R.mipmap.me_menu_qz, R.string.me_menu_qz, "2");
        customClub.init(R.mipmap.me_menu_club, R.string.me_menu_club, "3");
        customHousekeeper.init(R.mipmap.me_menu_gj, R.string.me_menu_gj, "4");
        ShadowDrawable.setShadowDrawable(llUserMessage,
                Color.parseColor("#ffffff"),
                NumberUtils.dip2Px(getActivity(), 5),
                Color.parseColor("#1A000000"),
                NumberUtils.dip2Px(getActivity(), 5),
                2, 2);

    }

    @Override
    protected void initData(Bundle bundle) {
        checkUserInfo();
    }

    @Override
    public void onResume() {
        super.onResume();
        updateView();
    }

    @OnClick({R.id.headImg, R.id.tv_history, R.id.tv_user_no, R.id.tv_coupons, R.id.tv_data_sync, R.id.image_sign_in,
            R.id.tv_promote, R.id.ll_collect, R.id.ll_order, R.id.tv_binding_consultant, R.id.tv_reservation,
            R.id.tv_nutrition_consultant, R.id.tv_join_in, R.id.custom_grade, R.id.custom_circle, R.id.feed_back,
            R.id.setting, R.id.custom_club, R.id.tv_lose_weight})
    public void onClick(View view) {
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            case R.id.custom_club:
                if (!UserAccountHelper.isLogin() || UserAccountHelper.getUser() == null) {
                    showLoginDialog(getActivity(), this, "你还没有登录或登录已过期，请先登陆", ConstantsHelper.REQUEST_ERROR_LOGIN);
                    return;
                }
                if(StringUtil.isEmpty(UserAccountHelper.getUser().getClubId())){
                    ToastUtils.showShort(getActivity(),"请先绑定俱乐部");
                    return;
                }
                bundle.putString("id", UserAccountHelper.getUser().getClubId());
                LaiShowClubDetailActivity.show(getActivity(), bundle);
                break;
            case R.id.setting:
                SettingActivity.show(this);
                break;
            case R.id.feed_back:
                FeedBackActivity.show(getActivity());
                break;
            case R.id.custom_circle:
                if (!UserAccountHelper.isLogin() || UserAccountHelper.getUser() == null) {
                    showLoginDialog(getActivity(), this, "你还没有登录或登录已过期，请先登陆", ConstantsHelper.REQUEST_ERROR_LOGIN);
                    return;
                }
                MyCircleActivity.show(getActivity(), new Bundle());
                break;
            case R.id.custom_grade:
            case R.id.tv_lose_weight:
                if (!UserAccountHelper.isLogin() || UserAccountHelper.getUser() == null) {
                    showLoginDialog(getActivity(), this, "你还没有登录或登录已过期，请先登陆", ConstantsHelper.REQUEST_ERROR_LOGIN);
                    return;
                }
                MyGradeActivity.show(getActivity(), new Bundle());
                break;
            case R.id.tv_nutrition_consultant:
                if (!UserAccountHelper.isLogin() || UserAccountHelper.getUser() == null) {
                    showLoginDialog(getActivity(), this, "你还没有登录或登录已过期，请先登陆", ConstantsHelper.REQUEST_ERROR_LOGIN);
                    return;
                }
                if (UserAccountHelper.getLoginType() != LoginTypeEnum.consultant.getIndex()) {
                    showLoginDialog(getActivity(), this, "您不是顾问，没有此项操作权限", ConstantsHelper.REQUEST_ERROR_LOGIN);
                    return;
                }
                bundle.putString("id", UserAccountHelper.getUser().getId());
                NutritionConsultantActivity.show(getActivity(), bundle);
                break;
            case R.id.image_sign_in:
                SignInActivity.show(getActivity(), new Bundle());
                break;
            case R.id.tv_reservation:
                if (!UserAccountHelper.isLogin() || UserAccountHelper.getUser() == null) {
                    showLoginDialog(getActivity(), this, "你还没有登录或登录已过期，请先登陆", ConstantsHelper.REQUEST_ERROR_LOGIN);
                    return;
                }
                if (StringUtil.isEmpty(UserAccountHelper.getUser().getCounselorMobile())) {
                    ConsultantListActivity.show(getActivity(), new Bundle());
                } else {
                    new CallDialog(getActivity(), UserAccountHelper.getUser().getCounselorMobile()).call();
                }
                break;
            case R.id.tv_binding_consultant:
                if (!UserAccountHelper.isLogin() || UserAccountHelper.getUser() == null) {
                    showLoginDialog(getActivity(), this, "你还没有登录或登录已过期，请先登陆", ConstantsHelper.REQUEST_ERROR_LOGIN);
                    return;
                }
                if (checkPermission(PERMISSIONS_CAMERA)) {
                    requestPermission(ConstantsHelper.CAMERA_REQUEST_CODE, getActivity(), PERMISSIONS_CAMERA);
                    return;
                }
                QRCodeUtil.getInstance().decode(this);
                break;
            case R.id.ll_collect:
                if (!UserAccountHelper.isLogin() || UserAccountHelper.getUser() == null) {
                    showLoginDialog(getActivity(), this, "你还没有登录或登录已过期，请先登陆", ConstantsHelper.REQUEST_ERROR_LOGIN);
                    return;
                }
                MyCollectActivity.show(this, new Bundle());
                break;
            case R.id.ll_order:
                if (!UserAccountHelper.isLogin() || UserAccountHelper.getUser() == null) {
                    showLoginDialog(getActivity(), this, "你还没有登录或登录已过期，请先登陆", ConstantsHelper.REQUEST_ERROR_LOGIN);
                    return;
                }
                MyOrderActivity.show(getActivity(), new Bundle());
                break;
            case R.id.tv_join_in:
                JoinInActivity.show(getActivity(), new Bundle());
                break;
            case R.id.tv_promote:
                if (!UserAccountHelper.isLogin() || UserAccountHelper.getUser() == null) {
                    showLoginDialog(getActivity(), this, "你还没有登录或登录已过期，请先登陆", ConstantsHelper.REQUEST_ERROR_LOGIN);
                    return;
                }
                MyPromoteListActivity.show(getActivity(), new Bundle());
                break;
            case R.id.tv_coupons:
                if (!UserAccountHelper.isLogin() || UserAccountHelper.getUser() == null) {
                    showLoginDialog(getActivity(), this, "你还没有登录或登录已过期，请先登陆", ConstantsHelper.REQUEST_ERROR_LOGIN);
                    return;
                }
                CouponsListActivity.show(getActivity(), new Bundle());
                break;
            case R.id.tv_data_sync:
                if (!UserAccountHelper.isLogin() || UserAccountHelper.getUser() == null) {
                    showLoginDialog(getActivity(), this, "你还没有登录或登录已过期，请先登陆", ConstantsHelper.REQUEST_ERROR_LOGIN);
                    return;
                }
                DataRecordActivity.show(getActivity(), new Bundle());
                break;
            case R.id.tv_user_no:
                if (!UserAccountHelper.isLogin()) {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivityForResult(intent, ConstantsHelper.REQUEST_ERROR_LOGIN);
                }
                break;
            case R.id.headImg:
                if (!UserAccountHelper.isLogin()) {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivityForResult(intent, LOGINFLAG_IMG);
                } else {
                    if (UserAccountHelper.getLoginType() == LoginTypeEnum.consultant.getIndex())
                        return;
                    PerfectMessageActivity.show(this, new Bundle());
                }
                break;
            case R.id.tv_history:
                HistoryActivity.show(getActivity(), new Bundle());
                break;
        }
    }


    /*
     * 判断sdcard是否被挂载
     */
    public static boolean hasSdcard() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }

    public String getRealFilePath(final Uri uri) {
        if (null == uri) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = getActivity().getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

    QRCodeUtil.OnDecodeListener onDecodeListener = isComplete -> {
        if (isComplete) {
            ToastUtils.showShort(getActivity(), "绑定成功");
            checkUserInfo();
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == ConstantsHelper.LOGIN_SUCCESS && data != null &&
                data.getBooleanExtra("result", false)) {
            updateView();
            return;
        }
        if (requestCode == ConstantsHelper.LOGIN_EXIT && resultCode == RESULT_OK) {
            updateView();
            return;
        }
        if (requestCode == ConstantsHelper.COLLECT && resultCode == RESULT_OK) {
            updateView();
            return;
        }
        if (resultCode == ConstantsHelper.PERFECT_MESSAGE_SUCCESS && data != null &&
                data.getBooleanExtra("result", false)) {
            updateView();
            return;
        }
        switch (requestCode) {
            case ConstantsHelper.QR_CODE_REQUEST:
                if (resultCode == RESULT_OK && data != null && data.getExtras() != null) {
                    try {
                        Bundle bundle = data.getExtras();
                        String scanResult = bundle.getString("result", null);
                        QRCodeUtil.decodeResult(getActivity(), scanResult, onDecodeListener);
                    } catch (Exception e) {
                        e.printStackTrace();
                        LogUtil.show("e:" + e);
                    }
                }
                break;
            case PHOTO_REQUEST_CAREMA:
                if (resultCode == RESULT_OK) {
                    Intent intent = new Intent("com.android.camera.action.CROP");
                    intent.setDataAndType(imageUri, "image/*");
                    intent.putExtra("scale", true);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
                    startActivityForResult(intent, CROP_PHOTO); // 启动裁剪程序
                }
                break;
            case CROP_PHOTO:
                if (resultCode == RESULT_OK) {
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(getActivity().getContentResolver()
                                .openInputStream(imageUri));
                        Glide.with(getActivity()).load(imageUri).error(R.mipmap.icon_head_default).into(headImg);
//                        headImg.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case TAKEPHOTO:
                if (data == null) return;
                Uri selectedImage = data.getData();
                String[] filePathColumns = {MediaStore.Images.Media.DATA};
                Cursor c = getActivity().getContentResolver().query(selectedImage, filePathColumns, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePathColumns[0]);
                String imagePath = c.getString(columnIndex);
                Glide.with(getActivity()).load(imagePath).error(R.mipmap.icon_head_default).into(headImg);

                c.close();
                break;
            case REQUEST_CODE:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 打开摄像机
     */
    public void camera() {
        //获取系統版本
        int currentapiVersion = Build.VERSION.SDK_INT;
        // 激活相机
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 判断存储卡是否可以用，可用进行存储
        if (hasSdcard()) {
            String filename = Calendar.getInstance().getTimeInMillis() + "";
            File file = new File(Environment.getExternalStorageDirectory(),
                    "/" + ConstantsHelper.ROOTFILEPATH + "/headImages/");
            if (!file.exists()) {
                file.mkdirs();
            }
            tempFile = new File(Environment.getExternalStorageDirectory(),
                    "/" + ConstantsHelper.ROOTFILEPATH + "/headImages/" + filename + ".jpg");
            if (currentapiVersion < 24) {
                // 从文件中创建uri
                imageUri = Uri.fromFile(tempFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            } else {
                //兼容android7.0 使用共享文件的形式
                ContentValues contentValues = new ContentValues(1);
                contentValues.put(MediaStore.Images.Media.DATA, tempFile.getAbsolutePath());
                imageUri = getActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            }
        }
        startActivityForResult(intent, PHOTO_REQUEST_CAREMA);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionUtils.requestPermissionsResult(getActivity(), requestCode, permissions, grantResults, mPermissionGrant);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    /**
     * 检查登录状态
     */
    private void checkUserInfo() {
        ApiHttpClient.getUserInfo(null, this, new StringCallback() {
//            @Override
//            public void onBefore(Request request, int id) {
//                super.onBefore(request, id);
//                if (customProgressDialog == null || !customProgressDialog.isShowing()) {
//                    customProgressDialog = new CustomProgressDialog(getActivity(), R.style.loading_dialog);
//                    customProgressDialog.setMessage("正在加载，请稍后...");
//                    customProgressDialog.show();
//                }
//            }
//
//            @Override
//            public void onAfter(int id) {
//                super.onAfter(id);
//                if (customProgressDialog != null && customProgressDialog.isShowing())
//                    customProgressDialog.dismiss();
//            }

            @Override
            public void onError(Call call, Exception e, int i) {
                ToastUtils.showShort(getActivity(), "网络状态不佳，请稍后再试！");
            }

            @Override
            public void onResponse(String s, int i) {
                ResultBean<UserInfo> resultBean = new GsonBuilder().disableHtmlEscaping().create().
                        fromJson(s, new TypeToken<ResultBean<UserInfo>>() {
                        }.getType());
                if (resultBean.isSuccess()) {
                    UserAccountHelper.saveLoginState(resultBean.getData(), UserAccountHelper.isLogin());
                    updateView();
                } else {
                    ToastUtils.showShort(getActivity(), StringUtil.isEmpty(resultBean.getMessage()) ? "操作失败" : resultBean.getMessage());
                }
            }
        });
    }

    /**
     * 刷新布局内容
     */
    private void updateView() {
        try {
            if (UserAccountHelper.isLogin() && UserAccountHelper.getUser() != null) {
                char[] bys = UserAccountHelper.getUser().getMobile().toCharArray();
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < bys.length; i++) {
                    if (i >= 3 && i < 9) sb.append("*");
                    else sb.append(bys[i]);
                }

                Glide.with(getActivity())
                        .load(UserAccountHelper.getUser().getHeadImg())
                        .asBitmap()
                        .placeholder(R.mipmap.icon_head_default)
                        .error(R.mipmap.icon_head_default)
                        .dontAnimate()
                        .into(headImg);

                Glide.with(getActivity())
                        .load(UserAccountHelper.getUser().getCounselorHeadImg())
                        .asBitmap()
                        .placeholder(R.mipmap.icon_head_default)
                        .error(R.mipmap.icon_head_default)
                        .dontAnimate()
                        .into(counselorHeadImg);
                tvDay.setText(NumberUtils.formatInteger(UserAccountHelper.getUser().getRegisterDay()));
                tvUserNo.setText(sb.toString());
                tvConsultant.setText(StringUtil.isEmpty(UserAccountHelper.getUser().getCounselorName()) ? "" : UserAccountHelper.getUser().getCounselorName());
                tvCurrentWeight.setText(StringUtil.isEmpty(UserAccountHelper.getUser().getWeight()) ? "未知" : (NumberUtils.decimalFormatInteger(UserAccountHelper.getUser().getWeight()) + "kg"));
                tvClubName.setText(StringUtil.isEmpty(UserAccountHelper.getUser().getClubName()) ? "" : UserAccountHelper.getUser().getClubName());
                tvOrderNum.setText(UserAccountHelper.getUser().getIndentNumber() + "");
                tvCollectionNum.setText(UserAccountHelper.getUser().getCollectNumber() + "");
                tvTargetWeight.setText(StringUtil.isEmpty(UserAccountHelper.getUser().getTargetWeight()) ? "未知" : (NumberUtils.decimalFormatInteger(UserAccountHelper.getUser().getTargetWeight()) + "kg"));
                if (UserAccountHelper.getUser().getClubNumber() != 0)
                    tvClubNumber.setText("第 " + UserAccountHelper.getUser().getClubNumber() + "名");
                else
                    tvClubNumber.setText("暂无排名");

                if (UserAccountHelper.getLoginType() == LoginTypeEnum.consultant.getIndex()) {
                    vNutritionConsultant.setVisibility(View.VISIBLE);
                    tvNutritionConsultant.setVisibility(View.VISIBLE);

                    tvMyPlan.setVisibility(View.GONE);
                    llPlan.setVisibility(View.GONE);
                    imageSignIn.setVisibility(View.GONE);
                    llOrder.setVisibility(View.GONE);
//                    llCollect.setVisibility(View.GONE);
                    llActivity.setVisibility(View.GONE);
                    tvDataSync.setVisibility(View.GONE);
                } else {
                    tvMyPlan.setVisibility(View.VISIBLE);
                    llPlan.setVisibility(View.VISIBLE);
//                    llCollect.setVisibility(View.VISIBLE);
                    imageSignIn.setVisibility(View.VISIBLE);
                    llOrder.setVisibility(View.VISIBLE);
                    llActivity.setVisibility(View.VISIBLE);
                    tvDataSync.setVisibility(View.VISIBLE);

                    vNutritionConsultant.setVisibility(View.GONE);
                    tvNutritionConsultant.setVisibility(View.GONE);
                    if (StringUtil.isEmpty(UserAccountHelper.getUser().getCounselorName()) || StringUtil.isEmpty(UserAccountHelper.getUser().getCounselorId())) {
                        tvReservation.setVisibility(View.GONE);
                        tvBindingConsultant.setVisibility(View.VISIBLE);
                    } else {
                        tvReservation.setVisibility(View.VISIBLE);
                        tvBindingConsultant.setVisibility(View.GONE);
                    }
                }

            } else {
                tvClubNumber.setText("暂无排名");
                tvUserNo.setText("点击登录");
                tvConsultant.setText("未绑定顾问");
                tvCurrentWeight.setText("未知");
                tvTargetWeight.setText("未知");
                tvClubName.setText("");
                tvOrderNum.setText("0");
                tvCollectionNum.setText("0");
                tvPublishNum.setText("0");
                tvDay.setText("0");
                vNutritionConsultant.setVisibility(View.GONE);
                tvNutritionConsultant.setVisibility(View.GONE);
                tvMyPlan.setVisibility(View.VISIBLE);
                llPlan.setVisibility(View.VISIBLE);
                imageSignIn.setVisibility(View.VISIBLE);
                llOrder.setVisibility(View.VISIBLE);
                llActivity.setVisibility(View.VISIBLE);
                tvDataSync.setVisibility(View.VISIBLE);
                Glide.with(getActivity()).load(R.mipmap.icon_head_default).into(counselorHeadImg);
                Glide.with(getActivity()).load(R.mipmap.icon_head_default).into(headImg);
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.show("e:" + e);
        }

    }

    PermissionUtils.PermissionGrant mPermissionGrant = new PermissionUtils.PermissionGrant() {
        @Override
        public void onPermissionGranted(int requestCode) {
        }
    };

    @Override
    public void openCamera() {
        // 缺少权限时, 进入权限配置页面
        if (checkPermission(PERMISSIONS)) {
            PermissionsActivity.startActivityForResult(getActivity(), ConstantsHelper.CAMERA_REQUEST_CODE, PERMISSIONS);
            return;
        }
        camera();
    }

    @Override
    public void openAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, TAKEPHOTO);
    }

}
