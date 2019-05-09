package com.cherishTang.laishou.api;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.telephony.TelephonyManager;

import com.cherishTang.laishou.enumbean.LoginTypeEnum;
import com.cherishTang.laishou.laishou.main.bean.SubstationBean;
import com.cherishTang.laishou.laishou.user.bean.UserInfo;
import com.cherishTang.laishou.custom.swiperefreshlayout.bean.RefreshDateBean;
import com.cherishTang.laishou.util.apiUtil.DateUtil;
import com.cherishTang.laishou.util.jiami.Base64;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.Reader;
import java.net.NetworkInterface;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import static android.content.Context.TELEPHONY_SERVICE;

/**
 * Created by 方舟 on 2017/5/23.
 * 用户登录等相关信息
 */

public class UserAccountHelper {
    public static UserAccountHelper userAccountHelper;
    public static Application application;
    private static SharedPreferences sp;

    public static String APPNAME = MyApplication.getInstance().getPackageName();

    private static final String USER_STATE = APPNAME + "_USER_STATE";//用户登录是否成功
    private static final String USER_INFO = APPNAME + "_USER_INFO";//用户信息
    private static final String APP_IS_FIRST_RUN = APPNAME + "_APP_IS_FIRST_RUN";//app是否为第一次运行
    private static final String SUBSTATION = APPNAME + "_SUBSTATION";//分站

    private static final String NOTIFICATIONS_ISOPEN = APPNAME + "_NOTIFICATIONS_ISOPEN";//通知栏是否开启
    private static final String NOTIFICATIONS_DATE = APPNAME + "_NOTIFICATIONS_DATE";//通知栏更改设置时间

    private static final String USER_ACCOUNT = APPNAME + "USER_ACCOUNT";//登录账号
    private static final String USER_PASSWORD = APPNAME + "USER_PASSWORD";//登录密码
    private static final String REFRESH_MESSAGE = APPNAME + "refresh_message";//信息
    private static final String USER_LOGIN_TYPE = APPNAME + "user_login_type";//身份信息信息

    static SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss a");

    public UserAccountHelper(Application application) {
        UserAccountHelper.application = application;
    }

    public static void init(Application application) {
        userAccountHelper = new UserAccountHelper(application);
        sp = application.getSharedPreferences(APPNAME, Activity.MODE_PRIVATE);
    }

    /**
     * 获取刷新时间
     *
     * @return
     */
    public static String getDate(String tag) {
        try {
            SharedPreferencesHelper sharedPreferencesHelper = new SharedPreferencesHelper(application, REFRESH_MESSAGE);
            List<RefreshDateBean> list = sharedPreferencesHelper.getObject(application, new TypeToken<List<RefreshDateBean>>() {
            }.getType());
            String lastDate = null;
            int index = getRefreshDateBean(list, tag);
            if (index != -1 && index != -2) {
                lastDate = list.get(index).getTime();
            }
            format.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
            String nowDate = DateUtil.getDateTimeFormat(new Date(Calendar.getInstance().getTimeInMillis()));
            Date date_start = null;
            Date date_end = null;
            if (lastDate == null) {
                return "最近更新：刚刚";
            } else {
                date_start = DateUtil.getDateByFormat(lastDate, DateUtil.DEFAULT_DATE_TIME_FORMAT);
                date_end = DateUtil.getDateByFormat(nowDate, DateUtil.DEFAULT_DATE_TIME_FORMAT);
                int day = DateUtil.getGapCount(date_start, date_end);
                String resultDate = null;
                switch (day) {
                    case 0:
                        resultDate = "最近更新：" + DateUtil.getTimeHourFormat(date_start);
                        break;
                    case 1:
                        resultDate = "最近更新：昨天" + DateUtil.getTimeHourFormat(date_start);
                        break;
                    case 2:
                        resultDate = "最近更新：前天" + DateUtil.getTimeHourFormat(date_start);
                        break;
                    default:
                        resultDate = "最近更新：" + day + "天前";
                        break;
                }
                return resultDate;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "最近更新：刚刚";
    }

    /**
     * 设置刷新时间
     * new Date(Calendar.getInstance().getTimeInMillis())
     */
    public static void setDate(RefreshDateBean refreshDateBean) {
        SharedPreferencesHelper sharedPreferencesHelper = new SharedPreferencesHelper(application, REFRESH_MESSAGE);
        List<RefreshDateBean> list = sharedPreferencesHelper.getObject(application, new TypeToken<List<RefreshDateBean>>() {
        }.getType());
        int index = getRefreshDateBean(list, refreshDateBean.getTag());
        if (index == -1) {
            list = new ArrayList<>();
            list.add(refreshDateBean);
        } else if (index == -2) {
            list.add(refreshDateBean);
        } else {
            list.set(index, refreshDateBean);
        }
        sharedPreferencesHelper.putObject(application, list, new TypeToken<List<RefreshDateBean>>() {
        }.getType());
    }

    /**
     * 根据传入的refreshDateBean值判断是否已经存储过这个页面的刷新时间信息
     *
     * @param list 读取已储存的细腻
     * @param tag  此次传入的数据刷新控件标记
     * @return -1==》不存在存储信息，2==》有存储过的信息，但是没有此页面的信息
     * 其他值代表list中的index索引
     */
    private static int getRefreshDateBean(List<RefreshDateBean> list, String tag) {
        if (list == null || list.size() == 0 || tag == null) return -1;
        int index = -2;
        for (RefreshDateBean contents : list) {
            if (contents.getTag().equals(tag))
                index = list.indexOf(contents);
        }
        return index;
    }

    /**
     * 判断是否登录
     *
     * @return 登录状态
     */
    public static boolean isLogin() {
        return ApiHttpClient.getToken() != null && getUser() != null
                && ((boolean) new SharedPreferencesHelper(application, USER_INFO).getSharedPreference(USER_STATE, false));
    }

    /**
     * 保存分站信息
     */
    public static void saveSubstation(List<SubstationBean> substationBeanList) {
        SharedPreferencesHelper sharedPreferencesHelper = new SharedPreferencesHelper(application, SUBSTATION);
        sharedPreferencesHelper.putObject(application, substationBeanList, new TypeToken<List<SubstationBean>>() {
        }.getType());
    }

    /**
     * 保存分站信息
     */
    public static void saveLocalSubstation(SubstationBean substationBeanList) {
        SharedPreferencesHelper sharedPreferencesHelper = new SharedPreferencesHelper(application, SUBSTATION);
        sharedPreferencesHelper.putObject(application, substationBeanList);
    }

    /**
     * 获取分站信息
     *
     * @return 分站信息，不为null
     */
    public static SubstationBean getLocalSubstation() {
        SharedPreferencesHelper sharedPreferencesHelper = new SharedPreferencesHelper(application, SUBSTATION);
        SubstationBean substationBean = sharedPreferencesHelper.getObject(application, SubstationBean.class);
        return substationBean == null ? new SubstationBean() : substationBean;
    }

    /**
     * 登录保存用户信息
     *
     * @param object    用户相关信息
     * @param isSuccess 是否登录成功
     */
    public static void saveLoginState(Object object, boolean isSuccess) {
        SharedPreferencesHelper sharedPreferencesHelper = new SharedPreferencesHelper(application, USER_INFO);
        sharedPreferencesHelper.putObject(application, object);
        sharedPreferencesHelper.put(USER_STATE, isSuccess);
    }

    /**
     * 获取用户信息
     *
     * @return
     */
    public static UserInfo getUser() {
        return new SharedPreferencesHelper(application, USER_INFO).getObject(application, UserInfo.class);
    }

    /**
     * 退出登录
     */
    public static void exit() {
        SharedPreferencesHelper sharedPreferencesHelper = new SharedPreferencesHelper(application, USER_INFO);
        sharedPreferencesHelper.removeObject(application, UserInfo.class);
        sharedPreferencesHelper.put(USER_STATE, false);
        ApiHttpClient.clearToken();
    }

    /**
     * 保存用户账号和密码
     *
     * @param account  账号
     * @param password 密码
     */
    public static void saveUserMessage(String account, String password) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(USER_ACCOUNT, account);
        editor.putString(USER_PASSWORD, password);
        editor.apply();
    }

    public static void saveLoginType(int loginType) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(USER_LOGIN_TYPE, loginType);
        editor.apply();
    }

    public static int getLoginType() {
        return sp.getInt(USER_LOGIN_TYPE, LoginTypeEnum.members.getIndex());
    }

    public static String getAccount() {
        return sp.getString(USER_ACCOUNT, null);
    }

    public static String getPassword(String account, String password) {
        return sp.getString(USER_PASSWORD, null);
    }

    /**
     * 判断是否为第一次运行app
     *
     * @return
     */
    public static boolean isFirstRun() {
        return sp == null || sp.getBoolean(APP_IS_FIRST_RUN, true);
    }

    public static void setNotFirstRun() {
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(APP_IS_FIRST_RUN, false);
        editor.apply();
    }

    /**
     * IMEI号，IESI号，手机型号：
     */
    public static String getPhoneInfo() {
        try {
            TelephonyManager mTm = (TelephonyManager) application.getSystemService(TELEPHONY_SERVICE);
            @SuppressLint("HardwareIds") String imei = mTm.getDeviceId();
            @SuppressLint("HardwareIds") String imsi = mTm.getSubscriberId();
            String mtype = Build.MODEL; // 手机型号
            @SuppressLint("HardwareIds") String numer = mTm.getLine1Number(); // 手机号码，有的可得，有的不可得
            String str = "imei:" + imei + ",imsi:" + imsi + ",phoneType:" + mtype + ",phoneNumber:" + numer + ",mac:" + getMacAddress();
            return Base64.encode(str.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 获取手机MAC地址：
     *
     * @return 手机MAC地址
     */
    public static String getMacAddress() {
        String result = "";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) { // 如果当前设备系统大于等于6.0 使用下面的方法
            result = getMac();
        } else {
            WifiManager wifiManager = (WifiManager) application.getSystemService(Context.WIFI_SERVICE);
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            result = wifiInfo.getMacAddress();
        }
        return result;
    }

    /**
     * 获取手机的MAC地址
     *
     * @return
     */
    public static String getMac() {
        String str = "";
        String macSerial = "";
        try {
            Process pp = Runtime.getRuntime().exec(
                    "cat /sys/class/net/wlan0/address ");
            InputStreamReader ir = new InputStreamReader(pp.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);
            for (; null != str; ) {
                str = input.readLine();
                if (str != null) {
                    macSerial = str.trim();// 去空格
                    break;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (macSerial == null || "".equals(macSerial)) {
            try {
                return loadFileAsString("/sys/class/net/eth0/address")
                        .toUpperCase().substring(0, 17);
            } catch (Exception e) {
                e.printStackTrace();
                macSerial = getAndroid7MAC();
            }
        }
        return macSerial;
    }

    public static String loadFileAsString(String fileName) throws Exception {
        FileReader reader = new FileReader(fileName);
        String text = loadReaderAsString(reader);
        reader.close();
        return text;
    }

    public static String loadReaderAsString(Reader reader) throws Exception {
        StringBuilder builder = new StringBuilder();
        char[] buffer = new char[4096];
        int readLength = reader.read(buffer);
        while (readLength >= 0) {
            builder.append(buffer, 0, readLength);
            readLength = reader.read(buffer);
        }
        return builder.toString();
    }

    /**
     * 手机CPU信息
     *
     * @return cpu信息
     */
    public static String[] getCpuInfo() {
        String str1 = "/proc/cpuinfo";
        String str2 = "";
        String[] cpuInfo = {"", ""};  //1-cpu型号  //2-cpu频率
        String[] arrayOfString;
        try {
            FileReader fr = new FileReader(str1);
            BufferedReader localBufferedReader = new BufferedReader(fr, 8192);
            str2 = localBufferedReader.readLine();
            arrayOfString = str2.split("\\s+");
            for (int i = 2; i < arrayOfString.length; i++) {
                cpuInfo[0] = cpuInfo[0] + arrayOfString[i] + " ";
            }
            str2 = localBufferedReader.readLine();
            arrayOfString = str2.split("\\s+");
            cpuInfo[1] += arrayOfString[2];
            localBufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cpuInfo;
    }

    /**
     * 兼容7.0获取不到的问题
     *
     * @return
     */
    public static String getAndroid7MAC() {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;
                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return "";
                }
                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    res1.append(String.format("%02X:", b));
                }
                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                return res1.toString();
            }
        } catch (Exception ex) {
        }
        return "02:00:00:00:00:00";
    }

    public static void setNotifications(boolean isOpen) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(NOTIFICATIONS_ISOPEN, isOpen);
        editor.putString(NOTIFICATIONS_DATE, DateUtil.getToday());
        editor.apply();
    }

    public static boolean getNotifications() {
        return sp.getBoolean(NOTIFICATIONS_ISOPEN, false);
    }

    public static String getNotificationsDate() {
        return sp.getString(NOTIFICATIONS_DATE, DateUtil.getToday());
    }

    public static void clearNotifications() {
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(NOTIFICATIONS_ISOPEN, false);
        editor.putString(NOTIFICATIONS_DATE, DateUtil.getToday());
        editor.apply();

    }
}
