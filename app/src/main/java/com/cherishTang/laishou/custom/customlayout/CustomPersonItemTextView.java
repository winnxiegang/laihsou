package com.cherishTang.laishou.custom.customlayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.RequiresApi;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cherishTang.laishou.R;

/**
 * Created by 方舟 on 2019/3/7.
 * 个人中心列表item
 */
public class CustomPersonItemTextView extends FrameLayout {
    private Context mContext;
    private int iconRes;
    private ImageView mIconView;
    private TextView mTitleView;
    private String message;

    public CustomPersonItemTextView(Context context) {
        super(context);
    }

    public CustomPersonItemTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.person_item);
        iconRes = a.getResourceId(R.styleable.person_item_icon_image, 0);
        message = a.getString(R.styleable.person_item_item_message);
        a.recycle();
        mContext = context;
        init();
    }

    public CustomPersonItemTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        inflater.inflate(R.layout.personal_item, this, true);

        mIconView = findViewById(R.id.icon_image);
        mTitleView = findViewById(R.id.tv_message);
        try {
            if (iconRes != 0)
                mIconView.setImageResource(iconRes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mTitleView.setText(message);
    }

}
