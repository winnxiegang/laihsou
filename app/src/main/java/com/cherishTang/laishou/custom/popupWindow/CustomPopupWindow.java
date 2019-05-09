package com.cherishTang.laishou.custom.popupWindow;

import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;

import com.cherishTang.laishou.R;
import com.cherishTang.laishou.adapter.popupwindow.PopupWindowAdapter;
import com.cherishTang.laishou.bean.PopPupWindowSelectedBean;
import com.cherishTang.laishou.custom.recyclerview.MyLayoutManager;
import com.cherishTang.laishou.util.log.ToastUtils;

import java.util.List;

/**
 * PopupWindow 下拉框
 *
 * @author 方舟
 */
public class CustomPopupWindow extends PopupWindow implements View.OnClickListener {
    private SelectCategory selectCategory;
    private Context activity;
    private RecyclerView recyclerView = null;
    private PopupWindowAdapter popupWindowAdapter = null;
    private List<PopPupWindowSelectedBean> listData;

    public CustomPopupWindow(String[] parentStrings, String[][] childrenStrings, int count, Context activity,
                             SelectCategory selectCategory) {
        this.selectCategory = selectCategory;
        this.activity = activity;
        View contentView = LayoutInflater.from(activity).inflate(R.layout.popupwindow_recyclerview, null);
        Button submit = (Button) contentView.findViewById(R.id.submit);
        submit.setOnClickListener(this);
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wm = (WindowManager) activity
                .getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(dm);

        this.setContentView(contentView);
        this.setWidth(dm.widthPixels);
//        this.setHeight(dm.heightPixels * 8 / 10);
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);

		/* 设置触摸外面时消失 */
        setOutsideTouchable(true);
        setTouchable(true);
        setFocusable(true); /*设置点击menu以外其他地方以及返回键退出 */
        /*
         * 1.解决再次点击MENU键无反应问题
         */
        contentView.setFocusableInTouchMode(true);
                /* 设置背景显示 */
        setBackgroundDrawable(ContextCompat.getDrawable(activity,R.drawable.pop_bg));

        recyclerView = (RecyclerView) contentView.findViewById(R.id.recyclerView);
        MyLayoutManager myLayoutManager = new MyLayoutManager(activity);
        popupWindowAdapter = new PopupWindowAdapter(parentStrings, childrenStrings, activity);
        popupWindowAdapter.setOnItemClickListener(new PopupWindowAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, List<PopPupWindowSelectedBean> list) {
                listData = list;
            }
        });
        recyclerView.setLayoutManager(myLayoutManager);
        recyclerView.setAdapter(popupWindowAdapter);

    }
    @Override
    public void showAsDropDown(View anchor) {
        if(Build.VERSION.SDK_INT == 24) {
            Rect rect = new Rect();
            anchor.getGlobalVisibleRect(rect);
            int h = anchor.getResources().getDisplayMetrics().heightPixels - rect.bottom;
            setHeight(h);
        }
        super.showAsDropDown(anchor);
    }
    @Override
    public void showAsDropDown(View anchor, int xoff, int yoff) {
        if(Build.VERSION.SDK_INT == 24) {
            Rect rect = new Rect();
            anchor.getGlobalVisibleRect(rect);
            int h = anchor.getResources().getDisplayMetrics().heightPixels - rect.bottom;
            setHeight(h);
        }
        super.showAsDropDown(anchor, xoff, yoff);
    }
    @Override
    public void onClick(View v) {
        if (listData == null || listData.size() == 0 || listData.isEmpty()) {
            ToastUtils.showShort(activity, "请至少选择一条数据");
            return;
        }
        selectCategory.submit(this, listData);
        dismiss();
    }

    /**
     * 选择成功回调
     *
     * @author apple
     */
    public interface SelectCategory {
        /**
         * 把选中的下标通过方法回调回来
         *
         * @param list
         */
        void submit(CustomPopupWindow customPopupWindow, List<PopPupWindowSelectedBean> list);
    }

}
