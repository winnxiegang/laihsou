package com.cherishTang.laishou.laishou.user.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cherishTang.laishou.R;
import com.cherishTang.laishou.api.ApiHttpClient;
import com.cherishTang.laishou.api.ConstantsHelper;
import com.cherishTang.laishou.base.BaseRecyclerViewAdapter;
import com.cherishTang.laishou.base.BaseRecyclerViewFragment;
import com.cherishTang.laishou.bean.base.PageBean;
import com.cherishTang.laishou.bean.base.ResultBean;
import com.cherishTang.laishou.custom.dialog.CustomProgressDialog;
import com.cherishTang.laishou.custom.swiperefreshlayout.wight.SwipeRecyclerView;
import com.cherishTang.laishou.laishou.user.activity.MyGradeActivity;
import com.cherishTang.laishou.laishou.user.adapter.MyGradeListAdapter;
import com.cherishTang.laishou.laishou.user.bean.MyWeightRecordBean;
import com.cherishTang.laishou.laishou.user.bean.PageRequestBean;
import com.cherishTang.laishou.util.apiUtil.DateUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by CherishTang on 2019/3/9.
 * 我的成绩
 */

public class MyGradeListFragment extends BaseRecyclerViewFragment<MyWeightRecordBean> {
    public static String ARG_PAGE = "ARG_PAGE";

    private int mPage = 0;
    private CustomProgressDialog customProgressDialog;

    @Override
    protected Type getType() {
        return new TypeToken<ResultBean<PageBean<MyWeightRecordBean>>>() {
        }.getType();
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        super.initView(view, savedInstanceState);
    }

    @Override
    protected void initData(Bundle bundle) {
        super.initData(bundle);
        requestData();
    }

//    @Override
//    protected void setListData(ResultBean<PageBean<MyWeightRecordBean>> resultBean) {
//        super.setListData(resultBean);
//        try {
//            if (resultBean == null || resultBean.getData() == null || resultBean.getData().getList() == null)
//                return;
//            List<MyWeightRecordBean> lists = resultBean.getData().getList();
//            double today = 0;
//            long todayTime = 0;
//            double yestoday = 0;
//            long yestodayTime = 0;
//
//            for (MyWeightRecordBean contents : lists) {
//                if (contents.getStrCreateTime() != null && contents.getStrCreateTime().startsWith(DateUtil.getToday()) &&
//                        (todayTime < DateUtil.stringToLong(contents.getStrCreateTime(), "yyyy-MM-dd HH:mm:ss"))) {
//                    today = contents.getWeight();
//                    todayTime = DateUtil.stringToLong(contents.getStrCreateTime(), "yyyy-MM-dd HH:mm:ss");
//                } else if (contents.getStrCreateTime() != null && contents.getStrCreateTime().startsWith(DateUtil.getYesterday()) &&
//                        (yestodayTime < DateUtil.stringToLong(contents.getStrCreateTime(), "yyyy-MM-dd HH:mm:ss"))) {
//                    yestoday = contents.getWeight();
//                    yestodayTime = DateUtil.stringToLong(contents.getStrCreateTime(), "yyyy-MM-dd HH:mm:ss");
//                }
//            }
//            MyGradeActivity myGradeActivity = (MyGradeActivity) getActivity();
//            if (today > yestoday) {
//                myGradeActivity.setResultWeight("比昨天胖\n" + (Math.abs(today - yestoday)) + "公斤");
//            } else {
//                myGradeActivity.setResultWeight("比昨天瘦\n" + (Math.abs(today - yestoday)) + "公斤");
//            }
//
//            myGradeActivity.updateChart(resultBean.getData().getList());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//
//    }

    @Override
    protected void requestData() {
        super.requestData();
        ApiHttpClient.getMyGradeList(new Gson().toJson(new PageRequestBean(mCurrentPage, ConstantsHelper.indexShowPages)), this, stringCallback);
    }

    @Override
    protected BaseRecyclerViewAdapter<MyWeightRecordBean> getRecyclerAdapter() {
        return new MyGradeListAdapter(getActivity());
    }

    public static Fragment instantiate(Bundle bundle) {
        Fragment fragment = new MyGradeListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

}
