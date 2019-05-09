package com.cherishTang.laishou.laishou.main.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;

import com.cherishTang.laishou.R;
import com.cherishTang.laishou.base.BaseFragment;

import butterknife.BindView;

/**
 * Created by CherishTang on 2018/5/30.
 * 测试fragment
 */

public class TestFragment extends BaseFragment {
    @BindView(R.id.textView)
    TextView textView;

    @Override
    protected int getLayoutId() {
        return R.layout.test_fragment;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {

    }

    @Override
    protected void initData(Bundle bundle) {
        int page = bundle.getInt("page", 0);
        textView.setText("这是第"+page+"个fragment");
    }

    public static Fragment instantiate(Bundle bundle) {
        Fragment fragment = new TestFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

}
