package com.cherishTang.laishou.custom.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.cherishTang.laishou.R;
import com.cherishTang.laishou.adapter.SingleRecyclerViewAdapter;
import com.cherishTang.laishou.bean.PopupWindowBean;
import com.cherishTang.laishou.custom.recyclerview.MyLayoutManager;

import java.util.List;

/**
 * Created by 方舟 on 2017/10/18.
 * 单选弹框
 */

public class SingleSelectedDialog implements SingleRecyclerViewAdapter.OnItemClickListener {

    public Context context;
    private List<PopupWindowBean> list;
    private SingleRecyclerViewAdapter adapter;
    private RecyclerView recyclerView;
    private Dialog customDialog;
    private TextView singleTextView;
    private CallBack callBack;

    public SingleSelectedDialog(Context context, List<PopupWindowBean> list, TextView singleTextView) {
        this.context = context;
        this.singleTextView = singleTextView;
        this.list = list;
    }

    public Dialog create() {
        customDialog = new Dialog(context, R.style.ActionSheetDialogStyle);
        recyclerView = new RecyclerView(context);
        MyLayoutManager myLayoutManager = new MyLayoutManager(context);
        recyclerView.setLayoutManager(myLayoutManager);
        adapter = new SingleRecyclerViewAdapter(context, list);
        adapter.setOnItemClickListener(this);
        recyclerView.setAdapter(adapter);

        customDialog.setContentView(recyclerView);
        Window dialogWindow = customDialog.getWindow();
        dialogWindow.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);// 设置dialog宽度
        dialogWindow.setGravity(Gravity.BOTTOM);
        customDialog.setCanceledOnTouchOutside(true);
        return customDialog;
    }

    @Override
    public void onItemClick(View view, int position) {
        if (customDialog.isShowing()) customDialog.dismiss();
        TextView textView = (TextView) view.findViewById(R.id.textView);
        singleTextView.setText(textView.getText().toString());
        if (textView.getTag() == null)
            singleTextView.setTag(null);
        else
            singleTextView.setTag(textView.getTag().toString());
        if (callBack != null) {
            if (textView.getTag() == null)
                callBack.callback(textView.getText().toString(), null);
            else
                callBack.callback(textView.getText().toString(), textView.getTag().toString());
        }
    }

    public void setCallBackListener(CallBack callBack) {
        this.callBack = callBack;
    }

    public interface CallBack {
        void callback(String callBackString, String tag);
    }

}
