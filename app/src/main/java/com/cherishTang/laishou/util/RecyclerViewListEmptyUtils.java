package com.cherishTang.laishou.util;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cherishTang.laishou.R;


public class RecyclerViewListEmptyUtils {

    public static View notDataView(Context context, RecyclerView recyclerView, int iv, String msg) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_empty_view, (ViewGroup) recyclerView.getParent(), false);
        ImageView imageView = view.findViewById(R.id.activity_empty_iv);
        TextView textView = view.findViewById(R.id.activity_empty_tv);
        imageView.setImageResource(iv);
        textView.setText(msg);
        return view;
    }

}
