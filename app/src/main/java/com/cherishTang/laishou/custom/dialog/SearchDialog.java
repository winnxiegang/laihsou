package com.cherishTang.laishou.custom.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import com.cherishTang.laishou.R;
import com.cherishTang.laishou.adapter.SearchRecyclerViewAdapter;
import com.cherishTang.laishou.bean.rent.RentParamsBean;
import com.cherishTang.laishou.custom.recyclerview.MyLayoutManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 方舟 on 2017/10/18.
 * 搜索列表
 */

public class SearchDialog implements TextWatcher, SearchRecyclerViewAdapter
        .OnItemClickListener {

    public Context context;
    private List<RentParamsBean.Detail> list;
    private SearchRecyclerViewAdapter adapter;
    RecyclerView recyclerView;
    private Dialog customDialog;
    private TextView community;

    public SearchDialog(Context context, List<RentParamsBean.Detail> list, TextView community) {
        this.context = context;
        this.community = community;
        this.list = list;
    }

    public Dialog create() {
        customDialog = new Dialog(context, R.style.ActionSheetDialogStyle);
        View view = LayoutInflater.from(context).inflate(
                R.layout.search_dialog, null);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        TextView cancelText = (TextView) view.findViewById(R.id.cancelText);
        EditText searchView = (EditText) view.findViewById(R.id.view_searcher);
        searchView.addTextChangedListener(this);
        cancelText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (customDialog.isShowing()) customDialog.dismiss();
            }
        });
        MyLayoutManager myLayoutManager = new MyLayoutManager(context);
        recyclerView.setLayoutManager(myLayoutManager);
        adapter = new SearchRecyclerViewAdapter(context, list);
        adapter.setOnItemClickListener(this);
        recyclerView.setAdapter(adapter);

        customDialog.setContentView(view);
        Window dialogWindow = customDialog.getWindow();
        dialogWindow.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);// 设置dialog宽度
        dialogWindow.setGravity(Gravity.TOP);
        return customDialog;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (adapter.getHeaderView() != null) {
            TextView textView = (TextView) adapter.getHeaderView().findViewById(R.id.textView);
            textView.setText(s.toString() + "（没有找到，点击选择这个小区）");
            textView.setTag(null);
        } else {
            View view = LayoutInflater.from(context).inflate(
                    R.layout.textview, null);
            TextView textView = (TextView) view.findViewById(R.id.textView);
            textView.setText(s.toString() + "（没有找到，点击选择这个小区）");
            textView.setTextColor(context.getResources().getColor(R.color.themeColor));
            textView.setTag(null);
            adapter.setHeaderView(view);
        }

        List<RentParamsBean.Detail> userEditList = new ArrayList<>();
        for (RentParamsBean.Detail contents : list) {
            if (contents.getName().contains(s.toString())) {
                userEditList.add(contents);
            }
        }
        adapter.setDefaultString(userEditList);
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onItemClick(View view, int position) {
        if (customDialog.isShowing()) customDialog.dismiss();
        TextView textView = (TextView) view.findViewById(R.id.textView);
        community.setText(textView.getText().toString().split("（")[0]);
        if (textView.getTag() == null)
            community.setTag(null);
        else
            community.setTag(textView.getTag().toString());
    }

    @Override
    public void onItemLongClick(View view, int position) {

    }
}
