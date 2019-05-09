package com.cherishTang.laishou.api;

import android.app.Application;

import com.cherishTang.laishou.enumbean.LoginTypeEnum;
import com.cherishTang.laishou.okhttp.OkHttpUtils;
import com.cherishTang.laishou.okhttp.callback.StringCallback;
import com.cherishTang.laishou.util.log.LogUtil;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 方舟 on 2017/5/22.
 * 网络请求等，token的保存获取和清空
 */

public class ApiHttpClient {
    private static String BASE_URL = "http://47.99.100.88:81/api/%s";//正式环境
//    private static String BASE_URL = "http://47.94.172.208:20192/api/%s";//测试环境

    private static ApiHttpClient apiHttpClient;
    public static Application application;

    public ApiHttpClient(Application application) {
        ApiHttpClient.application = application;
    }

    public static void init(Application application) {
        apiHttpClient = new ApiHttpClient(application);
    }

    public static String getAbsoluteBaseUrl(String partUrl) {
        LogUtil.show("请求地址为:" + String.format(BASE_URL, partUrl));
        return String.format(BASE_URL, partUrl);
    }

    /**
     * 本地保存token值
     *
     * @param token token值
     */
    public static void setToken(String token) {
        SharedPreferencesHelper sharedPreferencesHelper = new SharedPreferencesHelper(application, ApiAccountHelper.TOKEN_DATA);
        sharedPreferencesHelper.put(ApiAccountHelper.TOKEN_DATA_STRING, token);
    }

    /**
     * 清理token值
     */
    static void clearToken() {
        SharedPreferencesHelper sharedPreferencesHelper = new SharedPreferencesHelper(application, ApiAccountHelper.TOKEN_DATA);
        sharedPreferencesHelper.clear();
    }

    /**
     * 获取token值
     */
    public static String getToken() {
        SharedPreferencesHelper sharedPreferencesHelper = new SharedPreferencesHelper(application, ApiAccountHelper.TOKEN_DATA);
        return sharedPreferencesHelper.getSharedPreference(ApiAccountHelper.TOKEN_DATA_STRING, null) == null ?
                null : sharedPreferencesHelper.getSharedPreference(ApiAccountHelper.TOKEN_DATA_STRING, null).toString();
    }

    public static void checkUpdateVersion(String postJson, Object tag, StringCallback stringCallback) {
        OkhttpsHelper.post("Version/CheckVersion", postJson, tag, stringCallback);
    }

    /**
     * 获取验证码
     *
     * @param postJson       请求参数
     * @param tag            请求标记
     * @param stringCallback 回调
     */
    public static void getPhoneCode(String postJson, Object tag, StringCallback stringCallback) {
        OkhttpsHelper.post("Register/SendAuthCode", postJson, tag, stringCallback);
    }

    public static void registerUser(String postJson, Object tag, StringCallback stringCallback) {
        OkhttpsHelper.post("Register/UserRegister", postJson, tag, stringCallback);
    }

    public static void login(String postJson, Object tag, StringCallback stringCallback) {
        OkhttpsHelper.post("Login/UserLogin", postJson, tag, stringCallback);
    }

    public static void forgetPassword(String postJson, Object tag, StringCallback stringCallback) {
        OkhttpsHelper.post("Register/ForgetPwd", postJson, tag, stringCallback);
    }

    /**
     * 会员登录信息
     *
     * @param postJson
     * @param tag
     * @param stringCallback
     */
    public static void getMemberUserInfo(String postJson, Object tag, StringCallback stringCallback) {
        OkhttpsHelper.post("User/MyLoginInfo", postJson, tag, stringCallback);
    }

    /**
     * 根据判断是访问哪个个人信息
     *
     * @param postJson
     * @param tag
     * @param stringCallback
     */
    public static void getUserInfo(String postJson, Object tag, StringCallback stringCallback) {
        if (UserAccountHelper.getLoginType() == LoginTypeEnum.consultant.getIndex()) {
            OkhttpsHelper.post("User/CounselorInfo", postJson, tag, stringCallback);

        } else {
            OkhttpsHelper.post("User/MyLoginInfo", postJson, tag, stringCallback);
        }
    }

    /**
     * 顾问个人信息
     *
     * @param postJson
     * @param tag
     * @param stringCallback
     */
    public static void getConsultantUserInfo(String postJson, Object tag, StringCallback stringCallback) {
        OkhttpsHelper.post("User/CounselorInfo", postJson, tag, stringCallback);
    }

    public static void updateUserInfo(String postJson, Object tag, StringCallback stringCallback) {
        OkhttpsHelper.post("User/UpdateUser", postJson, tag, stringCallback);
    }

    public static void getClubList(String postJson, Object tag, StringCallback stringCallback) {
        OkhttpsHelper.post("Home/ClubPage", postJson, tag, stringCallback);
    }

    public static void getConsultantList(String postJson, Object tag, StringCallback stringCallback) {
        OkhttpsHelper.post("Home/CounselorPage", postJson, tag, stringCallback);
    }

    public static void getSubstationList(String postJson, Object tag, StringCallback stringCallback) {
        OkhttpsHelper.post("Home/CounselorPage", postJson, tag, stringCallback);
    }

    public static void getLocalSubstation(String postJson, Object tag, StringCallback stringCallback) {
        OkhttpsHelper.post("Home/Location", postJson, tag, stringCallback);
    }

    public static void getAdvertisingList(String postJson, Object tag, StringCallback stringCallback) {
        OkhttpsHelper.post("Home/AdvList", postJson, tag, stringCallback);
    }

    public static void getRecommendList(String postJson, Object tag, StringCallback stringCallback) {
        OkhttpsHelper.post("Home/Recommend", postJson, tag, stringCallback);
    }

    /**
     * 积分订单
     *
     * @param postJson
     * @param tag
     * @param stringCallback
     */
    public static void getOrderIntegralList(String postJson, Object tag, StringCallback stringCallback) {
        OkhttpsHelper.post("User/IndentPage", postJson, tag, stringCallback);
    }

    /**
     * 活动列表
     *
     * @param postJson
     * @param tag
     * @param stringCallback
     */
    public static void getActivityList(String postJson, Object tag, StringCallback stringCallback) {
        OkhttpsHelper.post("User/MyActivityPage", postJson, tag, stringCallback);
    }

    /**
     * 我的收藏
     *
     * @param postJson
     * @param tag
     * @param stringCallback
     */
    public static void getMyCollectionList(String postJson, Object tag, StringCallback stringCallback) {
        OkhttpsHelper.post("User/MyArticlePage", postJson, tag, stringCallback);
    }

    /**
     * 收藏文章
     *
     * @param postJson
     * @param tag
     * @param stringCallback
     */
    public static void collectArticle(String postJson, Object tag, StringCallback stringCallback) {
        OkhttpsHelper.post("User/Collect", postJson, tag, stringCallback);
    }

    /**
     * 我的拼团
     *
     * @param postJson
     * @param tag
     * @param stringCallback
     */
    public static void getMySpellList(String postJson, Object tag, StringCallback stringCallback) {
        OkhttpsHelper.post("User/MySpellPage", postJson, tag, stringCallback);
    }

    /**
     * 签到
     *
     * @param tag
     * @param stringCallback
     */
    public static void postSignIn(Object tag, StringCallback stringCallback) {
        OkhttpsHelper.post("User/DaySign", null, tag, stringCallback);
    }

    public static void getTodayIsSignIn(Object tag, StringCallback stringCallback) {
        OkhttpsHelper.post("User/DayIsSign", null, tag, stringCallback);
    }

    /**
     * 绑定俱乐部
     *
     * @param tag
     * @param stringCallback
     */
    public static void bindClub(String postJson, Object tag, StringCallback stringCallback) {
        OkhttpsHelper.post("User/BindingClub", postJson, tag, stringCallback);
    }

    /**
     * 绑定顾问
     *
     * @param tag
     * @param stringCallback
     */
    public static void bindCounselor(String postJson, Object tag, StringCallback stringCallback) {
        OkhttpsHelper.post("User/BindingCounselor", postJson, tag, stringCallback);
    }

    /**
     * 积分商品列表
     *
     * @param postJson
     * @param tag
     * @param stringCallback
     */
    public static void getGoodsPage(String postJson, Object tag, StringCallback stringCallback) {
        OkhttpsHelper.post("Goods/GoodsPage", postJson, tag, stringCallback);
    }

    /**
     * 积分兑换
     *
     * @param postJson
     * @param tag
     * @param stringCallback
     */
    public static void getGoodsExchange(String postJson, Object tag, StringCallback stringCallback) {
        OkhttpsHelper.post("User/Exchange", postJson, tag, stringCallback);
    }

    /**
     * 活动分页
     *
     * @param postJson
     * @param tag
     * @param stringCallback
     */
    public static void getActivityPage(String postJson, Object tag, StringCallback stringCallback) {
        OkhttpsHelper.post("Activity/ActivityPage", postJson, tag, stringCallback);
    }

    /**
     * 文章分页
     *
     * @param postJson
     * @param tag
     * @param stringCallback
     */
    public static void getArticlePage(String postJson, Object tag, StringCallback stringCallback) {
        OkhttpsHelper.post("Article/ArticlePage", postJson, tag, stringCallback);
    }

    /**
     * 四种活动统一请求
     *
     * @param postJson
     * @param tag
     * @param stringCallback
     */
    public static void getHomeRecommendList(String postJson, Object tag, StringCallback stringCallback) {
        OkhttpsHelper.post("Home/RecommendList", postJson, tag, stringCallback);
    }

    /**
     * 达人
     *
     * @param postJson
     * @param tag
     * @param stringCallback
     */
    public static void getSevereUserPage(String postJson, Object tag, StringCallback stringCallback) {
        OkhttpsHelper.post("Article/SevereUserPage", postJson, tag, stringCallback);
    }

    /**
     * 获取pk数据
     *
     * @param tag
     * @param stringCallback
     */
    public static void getMyPK(Object tag, StringCallback stringCallback) {
        OkhttpsHelper.post("User/MyPK", null, tag, stringCallback);
    }

    /**
     * 参与pk
     *
     * @param postJson
     * @param tag
     * @param stringCallback
     */
    public static void startPK(String postJson, Object tag, StringCallback stringCallback) {
        OkhttpsHelper.post("User/PK", postJson, tag, stringCallback);
    }

    public static void deleteIndentOrderList(String postJson, Object tag, StringCallback stringCallback) {
        OkhttpsHelper.post("User/DeleteIndent", postJson, tag, stringCallback);
    }

    public static void deleteActitivyOrderList(String postJson, Object tag, StringCallback stringCallback) {
        OkhttpsHelper.post("User/DeleteActivity", postJson, tag, stringCallback);
    }

    public static void deleteSpellOrderList(String postJson, Object tag, StringCallback stringCallback) {
        OkhttpsHelper.post("User/DeleteSpellUser", postJson, tag, stringCallback);
    }

    public static void deleteCollectOrderList(String postJson, Object tag, StringCallback stringCallback) {
        OkhttpsHelper.post("User/DeleteMyCollect", postJson, tag, stringCallback);
    }


    /**
     * 拼团商品分页
     *
     * @param postJson
     * @param tag
     * @param stringCallback
     */
    public static void getSpellGoods(String postJson, Object tag, StringCallback stringCallback) {
        OkhttpsHelper.post("SpellGoods/SpellPage", postJson, tag, stringCallback);
    }

    /**
     * 发起拼单
     *
     * @param postJson
     * @param tag
     * @param stringCallback
     */
    public static void postSpellGoods(String postJson, Object tag, StringCallback stringCallback) {
        OkhttpsHelper.post("User/Spell", postJson, tag, stringCallback);
    }

    /**
     * 顾问详情
     *
     * @param postJson
     * @param tag
     * @param stringCallback
     */
    public static void getConsultantDetail(String postJson, Object tag, StringCallback stringCallback) {
        OkhttpsHelper.post("Counselor/CounselorDetails", postJson, tag, stringCallback);
    }

    /**
     * 俱乐部详情
     *
     * @param postJson
     * @param tag
     * @param stringCallback
     */
    public static void getClubDetail(String postJson, Object tag, StringCallback stringCallback) {
        OkhttpsHelper.post("Club/ClubDetails", postJson, tag, stringCallback);
    }

    /**
     * 我的成绩
     *
     * @param postJson
     * @param tag
     * @param stringCallback
     */
    public static void getMyGradeList(String postJson, Object tag, StringCallback stringCallback) {
        OkhttpsHelper.post("User/LoseWeightPage", postJson, tag, stringCallback);
    }

    /**
     * 记录减肥
     *
     * @param postJson
     * @param tag
     * @param stringCallback
     */
    public static void recordMyGradeList(String postJson, Object tag, StringCallback stringCallback) {
        OkhttpsHelper.post("User/LoseWeight", postJson, tag, stringCallback);
    }

    /**
     * 最近体重记录
     *
     * @param tag
     * @param stringCallback
     */
    public static void weightRecently(Object tag, StringCallback stringCallback) {
        OkhttpsHelper.post("User/Recently", null, tag, stringCallback);
    }

    /**
     * 拼团商品详情
     *
     * @param postJson
     * @param tag
     * @param stringCallback
     */
    public static void getSpellGoodDetail(String postJson, Object tag, StringCallback stringCallback) {
        OkhttpsHelper.post("SpellGoods/SpellDetails", postJson, tag, stringCallback);
    }

    /**
     * 获取分享链接
     *
     * @param postJson
     * @param tag
     * @param stringCallback
     */
    public static void getSpellShareLink(String postJson, Object tag, StringCallback stringCallback) {
        OkhttpsHelper.post("User/SpellUrl", postJson, tag, stringCallback);
    }

    /**
     * 获取活动分享接口
     *
     * @param postJson
     * @param tag
     * @param stringCallback
     */
    public static void getActivityShareLink(String postJson, Object tag, StringCallback stringCallback) {
        OkhttpsHelper.post("Activity/ActivityUrl", postJson, tag, stringCallback);
    }

    /**
     * 获取我的推广
     *
     * @param tag
     * @param stringCallback
     */
    public static void getMyPromoteShareLink(Object tag, StringCallback stringCallback) {
        OkhttpsHelper.post("User/SharePromote", null, tag, stringCallback);
    }

    /**
     * 获取我的推广----下载地址
     *
     * @param tag
     * @param stringCallback
     */
    public static void getQrCode(Object tag, StringCallback stringCallback) {
        OkhttpsHelper.post("User/QrCode", null, tag, stringCallback);
    }

    /**
     * 获取文章分享链接
     *
     * @param postJson
     * @param tag
     * @param stringCallback
     */
    public static void getArticleShareLink(String postJson, Object tag, StringCallback stringCallback) {
        OkhttpsHelper.post("User/ShareArticle", postJson, tag, stringCallback);
    }

    /**
     * 获取文章pk链接
     *
     * @param postJson
     * @param tag
     * @param stringCallback
     */
    public static void getPKShareLink(String postJson, Object tag, StringCallback stringCallback) {
        OkhttpsHelper.post("User/SharePK", postJson, tag, stringCallback);
    }

    /**
     * 热门活动详情
     *
     * @param postJson
     * @param tag
     * @param stringCallback
     */
    public static void getHotActivityDetail(String postJson, Object tag, StringCallback stringCallback) {
        OkhttpsHelper.post("Activity/ActivityDetails", postJson, tag, stringCallback);
    }

    /**
     * 报名人数分页
     *
     * @param postJson
     * @param tag
     * @param stringCallback
     */
    public static void getHotActivityApplyPeopleDetail(String postJson, Object tag, StringCallback stringCallback) {
        OkhttpsHelper.post("Activity/SignPage", postJson, tag, stringCallback);
    }

    /**
     * 修改密码
     *
     * @param postJson
     * @param tag
     * @param stringCallback
     */
    public static void modifyPassword(String postJson, Object tag, StringCallback stringCallback) {
        OkhttpsHelper.post("User/UpdatePwd", postJson, tag, stringCallback);
    }

    /**
     * 修改顾问详情
     *
     * @param postJson
     * @param tag
     * @param stringCallback
     */
    public static void updateIntroduce(String postJson, Object tag, StringCallback stringCallback) {
        OkhttpsHelper.post("User/UpdateIntroduce", postJson, tag, stringCallback);
    }

    /**
     * 上传风采图片
     *
     * @param file
     * @param fileName
     * @param tag
     * @param stringCallback
     */
    public static void uploadImage(File file, String fileName, Object tag, StringCallback stringCallback) {
        OkhttpsHelper.postFileWebForm("Upload/UploadImg", fileName, file, tag, stringCallback);
    }

    /**
     * 添加风采
     *
     * @param postJson
     * @param tag
     * @param stringCallback
     */
    public static void postElement(String postJson, Object tag, StringCallback stringCallback) {
        OkhttpsHelper.post("User/AddMien", postJson, tag, stringCallback);
    }

    /**
     * 删除风采
     *
     * @param postJson
     * @param tag
     * @param stringCallback
     */
    public static void deleteElement(String postJson, Object tag, StringCallback stringCallback) {
        OkhttpsHelper.post("User/DeleteMien", postJson, tag, stringCallback);
    }

    /**
     * 添加相册
     *
     * @param postJson
     * @param tag
     * @param stringCallback
     */
    public static void postPhoto(String postJson, Object tag, StringCallback stringCallback) {
        OkhttpsHelper.post("User/AddPhoto", postJson, tag, stringCallback);
    }

    /**
     * 删除相册
     *
     * @param postJson
     * @param tag
     * @param stringCallback
     */
    public static void deletePhoto(String postJson, Object tag, StringCallback stringCallback) {
        OkhttpsHelper.post("User/DeletePhoto", postJson, tag, stringCallback);
    }

    /**
     * 相册分页
     *
     * @param postJson
     * @param tag
     * @param stringCallback
     */
    public static void getPhoto(String postJson, Object tag, StringCallback stringCallback) {
        OkhttpsHelper.post("User/PhotoPage", postJson, tag, stringCallback);
    }

    /**
     * 阿里支付tn号
     *
     * @param postJson
     * @param tag
     * @param stringCallback
     */
    public static void postAlipayOrder(String postJson, Object tag, StringCallback stringCallback) {
        OkhttpsHelper.post("User/PaySign", postJson, tag, stringCallback);
    }

    /**
     * 上传头像
     *
     * @param postJson
     * @param tag
     * @param stringCallback
     */
    public static void postHeadImage(String postJson, Object tag, StringCallback stringCallback) {
        OkhttpsHelper.post("User/UploadHead", postJson, tag, stringCallback);
    }

    /**
     * 上传顾问头像
     *
     * @param postJson
     * @param tag
     * @param stringCallback
     */
    public static void postCounselorHeadImage(String postJson, Object tag, StringCallback stringCallback) {
        OkhttpsHelper.post("User/CounselorHead", postJson, tag, stringCallback);
    }

    /**
     * 积分参加活动
     *
     * @param postJson
     * @param tag
     * @param stringCallback
     */
    public static void postIntegralSign(String postJson, Object tag, StringCallback stringCallback) {
        OkhttpsHelper.post("User/IntegralSign", postJson, tag, stringCallback);
    }

    /**
     * 我的活动支付
     *
     * @param postJson
     * @param tag
     * @param stringCallback
     */
    public static void postActivityOrderPay(String postJson, Object tag, StringCallback stringCallback) {
        OkhttpsHelper.post("User/IndentPay", postJson, tag, stringCallback);
    }

    /**
     * 会员我的推广
     *
     * @param postJson
     * @param tag
     * @param stringCallback
     */
    public static void postUserPromote(String postJson, Object tag, StringCallback stringCallback) {
        OkhttpsHelper.post("User/UserPromote", postJson, tag, stringCallback);
    }

    /**
     * 顾问我的推广
     *
     * @param postJson
     * @param tag
     * @param stringCallback
     */
    public static void postCounselorPromote(String postJson, Object tag, StringCallback stringCallback) {
        OkhttpsHelper.post("User/CounselorPromote", postJson, tag, stringCallback);
    }

/**
 * http://47.94.172.208:20192/api/Article/ArticleDetails
 */
    /**
     * 首页轮播图文章详情
     *
     * @param postJson
     * @param tag
     * @param stringCallback
     */
    public static void postMainArticleDetails(String postJson, Object tag, StringCallback stringCallback) {
        OkhttpsHelper.post("Article/ArticleDetails", postJson, tag, stringCallback);
    }


    /**
     * 达人详情
     *
     * @param postJson
     * @param tag
     * @param stringCallback
     */
    public static void postSevereUserDetails(String postJson, Object tag, StringCallback stringCallback) {
        OkhttpsHelper.post("Article/SevereUserDetails", postJson, tag, stringCallback);
    }

    /**
     * 攻略", 莱课堂  莱视界
     *
     * @param postJson
     * @param tag
     * @param stringCallback
     */
    public static void getCirlceThreeLei(String postJson, Object tag, StringCallback stringCallback) {
        OkhttpsHelper.post("Article/ArticlePage", postJson, tag, stringCallback);
    }

    /**
     * 朋友圈
     *
     * @param postJson
     * @param tag
     * @param stringCallback
     */
    public static void getCircleHomePage(String postJson, Object tag, StringCallback stringCallback) {
        OkhttpsHelper.post("Home/CirclePage", postJson, tag, stringCallback);
    }

    /**
     * 图片分享
     *
     * @param tag
     * @param stringCallback
     */
    public static void shareCircelImage(Object tag, StringCallback stringCallback) {
        OkhttpsHelper.post("User/SharePhoto", null, tag, stringCallback);
    }

    /**
     * 上传体重记录图片
     *
     * @param file
     * @param fileName
     * @param tag
     * @param stringCallback
     */
    public static void uploadWeightecordImage(File file, String fileName, Object tag, StringCallback stringCallback) {
        OkhttpsHelper.postFileWebForm("Upload/ShareWeight", fileName, file, tag, stringCallback);
    }  /**
     * /user/DeletePhoto
     *
     * @param tag
     * @param stringCallback
     */
    public static void deletePhoto(Object tag, StringCallback stringCallback) {
        OkhttpsHelper.post("User/DeletePhoto", null, tag, stringCallback);
    }
}