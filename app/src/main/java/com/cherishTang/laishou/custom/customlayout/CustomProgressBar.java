package com.cherishTang.laishou.custom.customlayout;

import android.content.Context;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.RequiresApi;
import android.support.annotation.StringRes;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cherishTang.laishou.R;

import butterknife.BindView;

/**
 * Created by 方舟 on 2019/3/6.
 * progressbar
 */
public class CustomProgressBar extends FrameLayout {
    TextView tvTitle;
    TextView tvLeft;
    ProgressBar progressBarLeft;
    ProgressBar progressBarRight;
    TextView tvRight;
    private Context mContext;

    public CustomProgressBar(Context context) {
        super(context);
    }

    public CustomProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    public CustomProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CustomProgressBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        inflater.inflate(R.layout.custom_progress_bar, this, true);
        progressBarRight =  findViewById(R.id.progressBarRight);
        progressBarLeft =  findViewById(R.id.progressBarLeft);
        tvRight =  findViewById(R.id.tv_right);
        tvLeft =  findViewById(R.id.tv_left);
        tvTitle =  findViewById(R.id.tv_title);
    }

    public void init(int leftProgress, int rightProgress,String title,String leftStr,String rightStr) {
        tvRight.setText(rightStr);
        tvLeft.setText(leftStr);
        tvTitle.setText(title);
        progressBarLeft.setProgress(leftProgress);
        progressBarRight.setProgress(rightProgress);
    }

}
