package com.cherishTang.laishou.api;

import com.cherishTang.laishou.util.apiUtil.DateUtil;

/**
 * Created by 方舟 on 2018/1/10.
 * 静态变量类
 */

public class ConstantsHelper {
    public static int indexShowPages = 15;//一页总条数

    public static final int LOGIN_SUCCESS= 1;//登录回调
    public static final int REGISTER_SUCCESS= 2;//注册回调
    public static final int MODIFY_PASSWORD_SUCCESS= 5;//修改密码回调
    public static final int LOGIN_FAILURE= 0;//登录回调
    public static final int PERFECT_MESSAGE_SUCCESS = 6;//编辑用户信息
    public static final int REQUEST_ERROR_LOGIN = 3;//过期，或登录异常等登录请求吗
    public static final int LOGIN_EXIT = 8;//退出登录
    public static final int COUNSELOR_REQUEST_CODE = 9;//我是营养顾问
    public static final int REQUEST_BACK = 4;//点击返回按钮
    public static final int QR_CODE_REQUEST = 7;//二维码
    public static final int COLLECT = 10;//收藏

    public static final int SIGN_IN_REQUEST_CODE = 201;//签到

    public static final int FIND_LOCATION_REQUEST_CODE = 301;//gps位置


    public static final int BASEFRAGMENT_PERMISSION_REQUEST_CODE = 3001; // baseFragment请求码
    public static final int BASEACTIVITY_PERMISSION_REQUEST_CODE = 3002; // baseactivity请求码

    public static final int CAMERA_REQUEST_CODE = 1001; // 拍照权限请求码
    public static final int PHOTO_REQUEST_CAREMA = 1002;// 拍照
    public static final int CROP_PHOTO = 1003;//裁剪图片
    public static final int CHOOSE_PICTURE = 1004;//相册选择图片
    public static final int LOCKVIEW_ISOPEN = 1003;//手势密码是否开启
    public static final String ROOTFILEPATH = "laishou";//项目文件夹名称
    public static final String TAG = "LaiShouLog";
    public static final int REQUEST_CODE = 2000; // 权限请求码


    public static final String INTENT_PATH = "path";
    public static final String BASE_PATH = "/com/cherishTang/laishou/path/";

    public static final String Login = BASE_PATH + "LoginActivity";
    public static final String MAIN = BASE_PATH + "MainActivity";


    public static boolean isDownLoadApk = false;//apk是否正在下载
    public static boolean isSuccessRequestUpdate = false;//更新请求是否成功了
    public static final String apkName = "LaiShow.apk";
    public static boolean hasNewAppVersion = false;//是否有新版本

    public static final String stepChannelId = MyApplication.getInstance().getPackageName()+".step";
    public static final String stepChannelName = "计步器";
    public static final String stepAlarmChannelId = MyApplication.getInstance().getPackageName()+".stepAlarm";
    public static final String stepAlarmChannelName = "锻炼提醒";

    public static final String updateVerisonChannelId = MyApplication.getInstance().getPackageName()+"updateVersionApp";
    public static final String updateVerisonChannelName = "版本更新";
    public static final String MESSAGE_RECEIVED_ACTION = MyApplication.getInstance().getPackageName()+".MESSAGE_RECEIVED_ACTION";
    public static final String MESSAGE_INFO = MyApplication.getInstance().getPackageName()+".MESSAGE_INFO";

    public static final String KEY_TITLE = "title";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";
}
