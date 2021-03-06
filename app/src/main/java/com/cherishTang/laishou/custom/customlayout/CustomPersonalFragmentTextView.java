package com.cherishTang.laishou.custom.customlayout;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cherishTang.laishou.R;
import com.cherishTang.laishou.util.apiUtil.DensityUtil;


/**
 * Created by 方舟 on 2017/4/24.
 * 个人中心
 */
public class CustomPersonalFragmentTextView extends FrameLayout {
    private Context mContext;
    private float mHeight;
    private ImageView mIconView;
    private TextView mTitleView;
    private LinearLayout linearLayout;
    private Class<?> mClx;
    private String mTag;
    private Activity mActivity;
    private int requestCode;
    private int flag;

    public CustomPersonalFragmentTextView(Context context) {
        super(context);
    }

    public CustomPersonalFragmentTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.auto3d);
        mHeight = a.getDimension(R.styleable.auto3d_textSize, DensityUtil.px2sp(context, 24));
        a.recycle();
        mContext = context;
        init();
    }

    public CustomPersonalFragmentTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        inflater.inflate(R.layout.custom_personal_fragment_textview, this, true);

        linearLayout = findViewById(R.id.linearLayout);
        mIconView = findViewById(R.id.nav_iv_icon);
        mTitleView = findViewById(R.id.nav_tv_title);
    }

    public void init(@DrawableRes int resId, @StringRes int strId, String tag) {
        mIconView.setImageResource(resId);
        mTitleView.setText(strId);
        mTitleView.setTag(tag);
    }

    public void init(@DrawableRes int resId, @StringRes int strId, String tag,
                     Class<?> clx, int requestCode, int flag) {
        mIconView.setImageResource(resId);
        mTitleView.setText(strId);
        mClx = clx;
        if (clx != null)
            mTag = mClx.getName();
        else
            mTag = tag;
        this.requestCode = requestCode;
        this.flag = flag;
    }

    public void init(@DrawableRes int resId, @StringRes int strId, String tag, @ColorRes int colorId) {
        mIconView.setImageResource(resId);
        mTitleView.setText(strId);
        mTitleView.setTextColor(ContextCompat.getColor(mContext, colorId));
        mTitleView.setTag(tag);
    }

    public Class<?> getClx() {
        return mClx;
    }

    public int getRequestCode() {
        return requestCode;
    }

    public Activity getActivity() {
        return mActivity;
    }

    public void setActivity(Activity mActivity) {
        this.mActivity = mActivity;
    }

    public String getTag() {
        return mTag;
    }

    public String getTagString() {
        return mTitleView.getTag().toString();
    }

    public String getTextString() {
        return mTitleView.getText().toString();
    }

    public boolean isNeedLogin() {
        return flag == 1;
    }
}
