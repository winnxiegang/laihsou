package com.cherishTang.laishou.custom.popupWindow;

import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.cherishTang.laishou.R;
import com.cherishTang.laishou.adapter.popupwindow.ChildrenCategoryAdapter;
import com.cherishTang.laishou.adapter.popupwindow.ParentCategoryAdapter;
import com.cherishTang.laishou.adapter.popupwindow.PopupWindowAdapter;

import java.util.List;

/**
 * PopupWindow 下拉框
 *
 * @author 方舟
 */
public class SelectPopupWindow extends PopupWindow {
    private SelectCategory selectCategory;

    private String[] parentStrings;
    private String[][] childrenStrings;

    private ListView lvParentCategory = null;
    private ListView lvChildrenCategory = null;
    private ParentCategoryAdapter parentCategoryAdapter = null;
    private ChildrenCategoryAdapter childrenCategoryAdapter = null;

    private RecyclerView recyclerView = null;
    private PopupWindowAdapter popupWindowAdapter = null;
    //回调数据
    private String string;
    private Context activity;

    /**
     * @param parentStrings  字类别数据
     * @param activity
     * @param selectCategory 回调接口注入
     */
    public SelectPopupWindow(String[] parentStrings, String[][] childrenStrings, Context activity,
                             SelectCategory selectCategory) {
        this.selectCategory = selectCategory;
        this.parentStrings = parentStrings;
        this.childrenStrings = childrenStrings;
        this.activity = activity;
        View contentView = LayoutInflater.from(activity).inflate(R.layout.layout_quyu_choose_view, null);
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wm = (WindowManager) activity
                .getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(dm);

        this.setContentView(contentView);
        this.setWidth(dm.widthPixels);
        this.setHeight(WindowManager.LayoutParams.MATCH_PARENT);

        /* 设置触摸外面时消失 */
        setOutsideTouchable(true);
        setTouchable(true);
        setFocusable(true); /*设置点击menu以外其他地方以及返回键退出 */

        /*
         * 1.解决再次点击MENU键无反应问题
         */
        contentView.setFocusableInTouchMode(true);
        /* 设置背景显示 */
        setBackgroundDrawable(ContextCompat.getDrawable(activity, R.drawable.pop_bg));
        //父类别适配器
        lvParentCategory = (ListView) contentView.findViewById(R.id.lv_parent_category);
        parentCategoryAdapter = new ParentCategoryAdapter(activity, parentStrings);
        lvParentCategory.setAdapter(parentCategoryAdapter);

        //子类别适配器
        lvChildrenCategory = (ListView) contentView.findViewById(R.id.lv_children_category);
        childrenCategoryAdapter = new ChildrenCategoryAdapter(activity);
        lvChildrenCategory.setAdapter(childrenCategoryAdapter);

        lvParentCategory.setOnItemClickListener(parentItemClickListener);
        lvChildrenCategory.setOnItemClickListener(childrenItemClickListener);
        if (childrenStrings == null) {
            lvChildrenCategory.setVisibility(View.GONE);
        }
    }

    @Override
    public void showAsDropDown(View anchor) {
        if (Build.VERSION.SDK_INT == 24) {
            Rect rect = new Rect();
            anchor.getGlobalVisibleRect(rect);
            int h = anchor.getResources().getDisplayMetrics().heightPixels - rect.bottom;
            setHeight(h);
        }
        super.showAsDropDown(anchor);
    }

    @Override
    public void showAsDropDown(View anchor, int xoff, int yoff) {
        if (Build.VERSION.SDK_INT == 24) {
            Rect rect = new Rect();
            anchor.getGlobalVisibleRect(rect);
            int h = anchor.getResources().getDisplayMetrics().heightPixels - rect.bottom;
            setHeight(h);
        }
        super.showAsDropDown(anchor, xoff, yoff);
    }

    /**
     * 子类别点击事件
     */
    private OnItemClickListener childrenItemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (selectCategory != null && parentCategoryAdapter != null) {
                selectCategory.selectCategory(parentCategoryAdapter.getPos(), position);
                childrenCategoryAdapter.setSelectedPosition(position);
            }
            dismiss();
        }
    };
    /**
     * 父类别点击事件
     */
    private OnItemClickListener parentItemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (childrenCategoryAdapter==null||parentCategoryAdapter==null) {
                dismiss();
                return;
            }
            if (childrenStrings != null) {
                childrenCategoryAdapter.setDatas(childrenStrings[position]);
                childrenCategoryAdapter.notifyDataSetChanged();
                parentCategoryAdapter.setSelectedPosition(position);
                parentCategoryAdapter.notifyDataSetChanged();
            } else {
                if (selectCategory != null) {
                    selectCategory.selectCategory(position, null);
                }
                parentCategoryAdapter.setSelectedPosition(position);
                parentCategoryAdapter.notifyDataSetChanged();
                dismiss();
            }


        }
    };

    /**
     * 选择成功回调
     *
     * @author apple
     */
    public interface SelectCategory {
        /**
         * 把选中的下标通过方法回调回来
         *
         * @param parentSelectposition 父类别选中下标
         */
        public void selectCategory(Integer parentSelectposition, Integer childrenSelectposition);
    }

    /**
     * 选择成功回调
     *
     * @author apple
     */
    public interface SelectCategoryView {
        /**
         * 把选中的下标通过方法回调回来
         *
         * @param list
         */
        public void selectCategoryView(List list);
    }

}
