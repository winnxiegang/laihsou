package com.cherishTang.laishou.custom.customlayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.RequiresApi;
import android.support.annotation.StringRes;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cherishTang.laishou.R;

/**
 * Created by 方舟 on 2017/4/24.
 * 底部按钮textView文本
 */
public class CustomTextView extends FrameLayout {
    private Context mContext;
    private float mHeight;
    private ImageView mIconView;
    private TextView mTitleView;
    private TextView mDot;
    private LinearLayout linearLayout;

    public CustomTextView(Context context) {
        super(context);
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.auto3d);
        mHeight = a.getDimension(R.styleable.auto3d_textSize, 12);
        a.recycle();
        mContext = context;
        init();
    }

    public CustomTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CustomTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        inflater.inflate(R.layout.custom_textview, this, true);

        linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
        mIconView = (ImageView) findViewById(R.id.nav_iv_icon);
        mTitleView = (TextView) findViewById(R.id.nav_tv_title);
        mDot = (TextView) findViewById(R.id.nav_tv_dot);
    }

    public void changeBackground(boolean selected) {
//        if(selected){
//            linearLayout.setBackgroundResource(R.color.custom_textview_color);
//        }else{
//            linearLayout.setBackgroundResource(R.color.login_icon_backgound);
//        }
    }

    public void showRedDot(int count) {
        mDot.setVisibility(count > 0 ? VISIBLE : GONE);
        mDot.setText(String.valueOf(count));
    }

    public void init(@DrawableRes int resId, @StringRes int strId, String tag) {
        mIconView.setImageResource(resId);
        mTitleView.setText(strId);
        mDot.setTag(getResources().getString(strId));
        mTitleView.setTag(tag);
    }

    public String getText() {
        return mDot.getTag().toString();
    }

    public String getTextString() {
        return mTitleView.getTag().toString();
    }

    public int getTagString() {
        if (mTitleView == null || mTitleView.getTag() == null) return 0;
        return Integer.parseInt(mTitleView.getTag().toString());
    }
}
