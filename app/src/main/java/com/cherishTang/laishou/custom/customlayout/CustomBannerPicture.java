package com.cherishTang.laishou.custom.customlayout;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.cherishTang.laishou.R;
import com.cherishTang.laishou.activity.WebViewActivity;
import com.cherishTang.laishou.adapter.PictureAdapter;
import com.cherishTang.laishou.api.ConstantsHelper;
import com.cherishTang.laishou.custom.picDialog.PicShowDialog;
import com.cherishTang.laishou.custom.picDialog.bean.ImageInfo;
import com.cherishTang.laishou.laishou.activity.activity.HotDetailActivity;
import com.cherishTang.laishou.laishou.main.activity.AdvertisingWebViewActivity;
import com.cherishTang.laishou.laishou.main.activity.DiscountDetailActivity;
import com.cherishTang.laishou.laishou.main.activity.LaiShowWebViewActivity;
import com.cherishTang.laishou.laishou.main.activity.LaiShowWebViewForMainActivity;
import com.cherishTang.laishou.laishou.main.activity.VideoViewActivity;
import com.cherishTang.laishou.sqlite.BannerBean;
import com.cherishTang.laishou.util.apiUtil.OtherUtil;
import com.cherishTang.laishou.util.apiUtil.StringUtil;
import com.cherishTang.laishou.util.glideImageToLocal.DownLoadImageService;
import com.cherishTang.laishou.util.glideImageToLocal.ImageDownLoadCallBack;
import com.cherishTang.laishou.util.log.LogUtil;
import com.cherishTang.laishou.util.log.ToastUtils;
import com.cherishTang.laishou.util.params.DataHolder;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 方舟 on 2018/3/15.
 * 自定义banner轮播图
 */

public class CustomBannerPicture extends RelativeLayout implements View.OnClickListener, MyViewPager.OnImageItemClickListener {
    private Context mContext;
    private ViewGroup.LayoutParams matchParams = new
            ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    private List<ImageView> images = new ArrayList<>();
    private MyViewPager viewPager;
    private LinearLayout dots_layout;
    private boolean isBrowse = false, isAutoBanner = true, isDownLoadLoacl = false;
    private List<ImageInfo> imageInfosList = new ArrayList<>();
    private int mCurrentPosition = 0;
    private List<BannerBean> imgPic;

    public CustomBannerPicture(Context context) {
        super(context);
    }

    public CustomBannerPicture(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public void init(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.Topbar);
        isBrowse = ta.getBoolean(R.styleable.Topbar_isBrowse, false);
        isAutoBanner = ta.getBoolean(R.styleable.Topbar_isAutoBanner, false);
        isDownLoadLoacl = ta.getBoolean(R.styleable.Topbar_isDownLoadLocal, false);
        this.mContext = context;
        setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
    }

    public void initView(List<BannerBean> imgPic) {
        this.imgPic = imgPic;
        if (!imgPic.isEmpty() && imgPic.size() != 0) {
            if (imgPic.size() == 1) {
                ImageView imageView = new ImageView(mContext);
                imageView.setLayoutParams(matchParams);
                Object imgPath = imgPic.get(0).getPath() == null ? imgPic.get(0).getLocalPath() : imgPic.get(0).getPath();
                Glide.with(mContext)
                        .load(imgPath)
                        .placeholder(R.mipmap.icon_banner_default)
                        .error(R.mipmap.icon_banner_default)
                        .into(imageView);
                imageInfosList.add(new ImageInfo(imgPath, 200, 200));

                if (isDownLoadLoacl)
                    downloadPic(imgPic.get(0).getPath() == null ? imgPic.get(0).getLocalPath() : imgPic.get(0).getPath());
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                addView(imageView);
                imageView.setTag(R.id.imageUrl, imgPic.get(0).getPath());
                imageView.setTag(R.id.AddUrl, imgPic.get(0).getLinkPath());
                imageView.setTag(R.id.imageType, imgPic.get(0).getUrlType());
                imageView.setTag(R.id.TypeId, imgPic.get(0).getUrlType());
                imageView.setOnClickListener(this);
            } else {
                images.clear();
                for (int k = 0; k < imgPic.size(); k++) {
                    Object imgPath = imgPic.get(k).getPath() == null ? imgPic.get(k).getLocalPath() : imgPic.get(k).getPath();
                    imageInfosList.add(new ImageInfo(imgPath, 200, 200));
                    if (isDownLoadLoacl) downloadPic(imgPath);
                    getImages(imgPath, imgPic.get(k).getLinkPath(), imgPic.get(k).getUrlType(), imgPic.get(k).getTypeid());
                }
                addHeader();
            }
        }
    }

    /**
     * 创建ViewPager的子item项
     */
    private void getImages(Object imgPath, String linkPath, String urlType, String TypeId) {
        ImageView img1 = new ImageView(mContext);
        img1.setLayoutParams(matchParams);
        img1.setTag(R.id.imageUrl, imgPath);
        img1.setTag(R.id.AddUrl, linkPath);
        img1.setTag(R.id.imageType, urlType);
        img1.setTag(R.id.TypeId, TypeId);
        img1.setOnClickListener(this);
        Glide.with(mContext).load(imgPath).error(R.mipmap.icon_banner_default).into(img1);
        img1.setScaleType(ImageView.ScaleType.FIT_XY);
        images.add(img1);
    }

    public ViewPager getViewPager() {
        return viewPager;
    }

    public int getCurrentPosition() {
        return mCurrentPosition;
    }

    /**
     * 填充布局
     */
    private void addHeader() {
        View headerView = LayoutInflater.from(mContext).inflate(R.layout.viewpager_round_layout_index, null);
        LinearLayout images_layout = (LinearLayout) headerView.findViewById(R.id.carousel_image_layout);
        dots_layout = (LinearLayout) headerView.findViewById(R.id.image_round_layout);
        viewPager = new MyViewPager(mContext, isAutoBanner);
        initImageRounds();
        viewPager.setImages(images);
        viewPager.setOnImageItemClickListener(this);
        viewPager.setAdapter(new PictureAdapter(images));
        viewPager.setCurrentItem(Integer.MAX_VALUE / 2);
        //一个childView只能被赋给一个parent。因此在添加前需要移除，再添加
        images_layout.removeAllViews();
        removeAllViews();

        images_layout.addView(viewPager);
        addView(headerView);
    }

    /**
     * 计算viewPager小底部小圆点的大小
     */
    private void initImageRounds() {
        List<ImageView> dots = new ArrayList<>();
        dots_layout.removeAllViews();
        /*
         *当轮播图大于1张时小圆点显示
         */
        if (images.size() > 1) {
            dots_layout.setVisibility(View.VISIBLE);
        } else {
            dots_layout.setVisibility(View.INVISIBLE);
        }
        for (int i = 0; i < images.size(); i++) {
            ImageView round = new ImageView(mContext);
            /*
             * 默认让第一张图片显示深颜色的圆点
             */
            if (i == 0) {
                round.setImageResource(R.mipmap.icon_point2);
            } else {
                round.setImageResource(R.mipmap.icon_point1);
            }
            dots.add(round);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, -2);
            params.leftMargin = 20;
            dots_layout.addView(round, params);
        }
        viewPager.setDots(dots);
    }


    //缓存图片至本地
    public void downloadPic(Object pic) {
        DownLoadImageService service = new DownLoadImageService(mContext, pic, ConstantsHelper.ROOTFILEPATH + "/bannerPic/", new ImageDownLoadCallBack() {
            @Override
            public void onDownLoadSuccess(File file) {
//                BannerBean bannerBean = new BannerBean();
//                bannerBean.setModifyTime(System.currentTimeMillis());
//                bannerBean.setPath(file.getPath());
//                dataBaseOperate.insertToBanner(bannerBean);
            }

            @Override
            public void onDownLoadSuccess(Bitmap bitmap) {
            }

            @Override
            public void onDownLoadFailed() {
                LogUtil.show("本地图片缓存失败");
            }
        });
        //千万别忘了启动线程服务
        new Thread(service).start();
    }

    @Override
    public void onClick(View v) {
/**
 * 行风:
 * @Android谢 @二老表 banner中的urlType需要重新定义，0:无;1:文章;2:活动;3:视频;4:产品
 * 二老表:
 * 你先看下你的文章详情怎么显示，是请求了一个详情接口还是直接从列表中将content带进去显示的
 *`
 * 二老表:
 * 0的话写的是无，我就给他跳webview，把加载url内容；后面的1、2、4的话url里面是ID，
 * 即文章、活动、产品的ID，将ID传到对应的详情页就行了，3 视频 url里面放的是视频播放地址，直接跳到视频播放页面就行了
 */
        try {
            /**
             * 2019-04-20 17:03:25.948 25119-25119/com.cherishTang.laishou D/LaiShouLog: xgimageUrlhttp://47.99.100.88:81/file/showimg?filename=20190417102418.jpg
             * 2019-04-20 17:03:25.948 25119-25119/com.cherishTang.laishou D/LaiShouLog: xgAddUrl18350fd8-35be-423a-b54f-72b5e86a64bf
             * 2019-04-20 17:03:25.948 25119-25119/com.cherishTang.laishou D/LaiShouLog: xgimageType2
             */
            ImageView imageView = (ImageView) v;
            LogUtil.show("xgimageUrl" + imageView.getTag(R.id.imageUrl).toString());
            LogUtil.show("xgAddUrl" + imageView.getTag(R.id.AddUrl).toString()); //id
            LogUtil.show("xgimageType" + imageView.getTag(R.id.imageType).toString());
            if (imageView.getTag(R.id.imageType) != null) {
                if ("0".equals(imageView.getTag(R.id.imageType).toString()) && !StringUtil.isEmpty(imageView.getTag(R.id.imageUrl).toString())) {
//                    Intent intent = new Intent(mContext, WebViewActivity.class);
//                    intent.putExtra("loadUrl", imageView.getTag(R.id.AddUrl).toString());
//                    mContext.startActivity(intent);

                } else if ("1".equals(imageView.getTag(R.id.imageType).toString())) {//1:文章
                    Bundle bundle = new Bundle();
                    bundle.putString("id", imageView.getTag(R.id.AddUrl).toString());
                    bundle.putInt("contentId", LaiShowWebViewActivity.content_id);
                    bundle.putString("title", "");
                    bundle.putString("image", imageView.getTag(R.id.imageUrl).toString());
                    //DataHolder.getInstance().save(LaiShowWebViewActivity.content_id + "", laiShowAdapter.getList().get(position).getArticleContent());
                    LaiShowWebViewForMainActivity.show(mContext, bundle);

                } else if ("2".equals(imageView.getTag(R.id.imageType).toString())) {//2:活动
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("isActivityOrder", false);
                    bundle.putString("id", imageView.getTag(R.id.AddUrl).toString());
                    HotDetailActivity.show(mContext, bundle);
                } else if ("3".equals(imageView.getTag(R.id.AddUrl).toString())) {//3:视频
                    Bundle bundle = new Bundle();
                    bundle.putString("path", imageView.getTag(R.id.imageUrl).toString());
                    VideoViewActivity.show(mContext, bundle);

                } else if ("4".equals(imageView.getTag(R.id.imageType).toString())) {//4:产品
                    Bundle bundle = new Bundle();
                    bundle.putString("id", imageView.getTag(R.id.AddUrl).toString());
                    DiscountDetailActivity.show(mContext, bundle);

                }
            }
        } catch (Exception e) {
            LogUtil.show("e:" + e);
            e.printStackTrace();
        }
//        if (imageInfosList == null || imageInfosList.isEmpty()) return;
//        PicShowDialog dialog = new PicShowDialog(mContext, imageInfosList, mCurrentPosition);
//        dialog.show();
    }

    @Override
    public void onItemClick(int itemPosition) {

    }

    @Override
    public void getPosition(int itemPosition) {
        mCurrentPosition = itemPosition;//把viewPager当前的position传递出去，viewPager.getCurrentItem()获取的位置是不准确的
//        try{
//            if(!isBrowse&& StringUtil.isEmpty(imgPic.get(mCurrentPosition).getLinkPath())) {
//                Uri uri = Uri.parse(imgPic.get(mCurrentPosition).getLinkPath());
//                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//                mContext.startActivity(intent);
//            }
//        }catch (Exception e){
//            LogUtil.show("e:"+e);
//            e.printStackTrace();
//        }
    }
}
