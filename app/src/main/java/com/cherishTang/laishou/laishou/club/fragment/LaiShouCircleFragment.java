package com.cherishTang.laishou.laishou.club.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.cherishTang.laishou.R;
import com.cherishTang.laishou.base.BaseFragment;
import com.cherishTang.laishou.laishou.club.adapter.ListDropDownFiveAdapter;
import com.cherishTang.laishou.laishou.club.adapter.ListDropDownFourAdapter;
import com.cherishTang.laishou.laishou.club.adapter.ListDropDownOneAdapter;
import com.cherishTang.laishou.laishou.club.adapter.ListDropDownThreeAdapter;
import com.cherishTang.laishou.laishou.club.adapter.ListDropDownTwoAdapter;
import com.cherishTang.laishou.util.droppopmenu.DropDownMenu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class LaiShouCircleFragment extends BaseFragment {


    @BindView(R.id.dropDownMenu)
    DropDownMenu dropDownMenu;
    Unbinder unbinder;
    @BindView(R.id.fragment_container)
    FrameLayout fragmentContainer;
    private String headers[] = {"达人", "莱攻略", "莱课堂", "莱视界", "附近"};
    private List<View> popupViews = new ArrayList<>();

    private String headerOne[] = {"达人"};
    private String headerTwo[] = {"专家莱说", "减脂食堂", "运动干货"};
    private String headerThree[] = {"明星产品", "体重管理", "营养基础", "莱瘦创业"};
    private String headerFour[] = {"奶昔食谱", "运动健康", "活动风采", "创业故事"};
    private String headerFive[] = {"附近"};
    private ListDropDownOneAdapter headerOneAdapter;
    private ListDropDownTwoAdapter headerTwoAdapter;
    private ListDropDownThreeAdapter headerThreeAdapter;
    private ListDropDownFourAdapter headerFourAdapter;
    private ListDropDownFiveAdapter headerFiveAdapter;
    // 先创建 对应的Fragment的实例 (全局)
    private Fragment currentFragment = new Fragment();
    private ListView headerOneList;
    private ListView headerTwoList;
    private ListView headerThreeList;
    private ListView headerFourList;
    private ListView headerFiveList;
    View laiShouCircle;
    private int headerTwoType = 1;
    private int headerThreeType = 4;
    private int headerFourType = 8;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_layout_laishou_circle2;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        laiShouCircle = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_layout_laishou_circle2, null);
        //init age menu
        headerOneList = new ListView(getActivity());
        headerOneList.setDividerHeight(0);
        headerOneAdapter = new ListDropDownOneAdapter(getActivity(), Arrays.asList(headerOne));
        headerOneList.setAdapter(headerOneAdapter);
        //init age menu
        headerTwoList = new ListView(getActivity());
        headerTwoList.setDividerHeight(0);
        headerTwoAdapter = new ListDropDownTwoAdapter(getActivity(), Arrays.asList(headerTwo));
        headerTwoList.setAdapter(headerTwoAdapter);
        //init age menu
        headerThreeList = new ListView(getActivity());
        headerThreeList.setDividerHeight(0);
        headerThreeAdapter = new ListDropDownThreeAdapter(getActivity(), Arrays.asList(headerThree));
        headerThreeList.setAdapter(headerThreeAdapter);
        //init age menu
        headerFourList = new ListView(getActivity());
        headerFourList.setDividerHeight(0);
        headerFourAdapter = new ListDropDownFourAdapter(getActivity(), Arrays.asList(headerFour));
        headerFourList.setAdapter(headerFourAdapter);
        //init age menu
        headerFiveList = new ListView(getActivity());
        headerFiveList.setDividerHeight(0);
        headerFiveAdapter = new ListDropDownFiveAdapter(getActivity(), Arrays.asList(headerFive));
        headerFiveList.setAdapter(headerFiveAdapter);
        popupViews.add(headerOneList);
        popupViews.add(headerTwoList);
        popupViews.add(headerThreeList);
        popupViews.add(headerFourList);
        popupViews.add(headerFiveList);

        TextView contentView = new TextView(getActivity());
        contentView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        contentView.setGravity(Gravity.CENTER);
        contentView.setText("内容预约");
        contentView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        dropDownMenu.setDropDownMenu(Arrays.asList(headers), popupViews, laiShouCircle);
    }

    @Override
    protected void initData(Bundle bundle) {
        typeChouseFragment(1);
        //add item click event
        headerOneList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                headerOneAdapter.setCheckItem(position);
                typeChouseFragment(1);
                dropDownMenu.closeMenu();
            }
        });
        headerTwoList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                headerTwoAdapter.setCheckItem(position);
                typeransform(headerTwo[position]);
                typeChouseFragment(2);
                dropDownMenu.closeMenu();
            }
        });
        headerThreeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                headerThreeAdapter.setCheckItem(position);
                typeransform(headerThree[position]);
                typeChouseFragment(3);
                dropDownMenu.closeMenu();
            }
        });
        headerFourList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                headerFourAdapter.setCheckItem(position);
                typeransform(headerFour[position]);
                typeChouseFragment(4);
                dropDownMenu.closeMenu();
            }
        });
        headerFiveList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                headerFiveAdapter.setCheckItem(position);
                typeChouseFragment(5);
                dropDownMenu.closeMenu();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    /**
     * Fragment的切换 只隐藏
     */
    private FragmentTransaction switchFragment(Fragment targetFragment) {
        // 获取事物的实例
        FragmentTransaction transaction = getChildFragmentManager()
                .beginTransaction();
        // 判断此fragment是否添加
        if (!targetFragment.isAdded()) {// 没有添加
            //注意 :  第一次使用switchFragment()时currentFragment为null，所以要判断一下
            if (currentFragment != null) {
                transaction.hide(currentFragment);
            }
            // 添加对应的fragment
            transaction.add(R.id.fragment_container, targetFragment, targetFragment.getClass().getName());
        } else {
            transaction
                    .hide(currentFragment)
                    .show(targetFragment);
        }
        currentFragment = targetFragment;
        return transaction;
    }

    //    private HeadOneFragment headOneFragment = new HeadOneFragment();
//    private HeadTwoFragment headTwoFragment = new HeadTwoFragment();
//    private HeadFiveFragment headThreeFragment = new HeadFiveFragment();
//    private HeadFourFragment headFourFragment = new HeadFourFragment();
//    private HeadFiveFragment headFiveFragment = new HeadFiveFragment();
    private void typeChouseFragment(int type) {
        switch (type) {
            case 1:
                switchFragment(new HeadOneFragment()).commit();// 记得要commit
                break;
            case 2:
                switchFragment(new HeadTwoFragment().newInstance(headerTwoType)).commit();// 记得要commit
                break;
            case 3:
                switchFragment(new HeadThreeFragment().newInstance(headerThreeType)).commit();// 记得要commit
                break;
            case 4:
                switchFragment(new HeadFourFragment().newInstance(headerFourType)).commit();// 记得要commit
                // Log.d("postJson", headerFourType + "newInstance");
                break;
            case 5:
                switchFragment(new HeadFiveFragment()).commit();// 记得要commit
                break;
        }
    }


    private void typeransform(String title) {
        //Log.d("postJson", title + "title");
        switch (title) {
            case "专家莱说":
                headerTwoType = 1;
                break;
            case "减脂食堂":
                headerTwoType = 2;
                break;
            case "运动干货":
                headerTwoType = 3;
                break;
            case "明星产品":
                headerThreeType = 4;
                break;
            case "体重管理":
                headerThreeType = 5;
                break;
            case "营养基础":
                headerThreeType = 6;
                break;
            case "莱瘦创业":
                headerThreeType = 7;
                break;
            case "奶昔食谱":
                headerFourType = 8;
                break;
            case "运动健康":
                headerFourType = 9;
                break;
            case "活动风采":
                headerFourType = 10;
                break;
            case "创业故事":
                headerFourType = 11;
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
