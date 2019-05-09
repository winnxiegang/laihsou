package com.cherishTang.laishou.adapter.popupwindow;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cherishTang.laishou.R;
import com.cherishTang.laishou.bean.PopPupWindowSelectedBean;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 项目名称：PopupWindowAdapter
 * 创建人：方舟
 * 创建时间：2017/8/10 11:00
 * 修改备注：
 */
public class PopupWindowAdapter extends RecyclerView.Adapter<PopupWindowAdapter.ViewHolder> implements View.OnClickListener {
    private List<PopPupWindowSelectedBean> conditionsList = new CopyOnWriteArrayList<>();

    private Context context;
    public String[] parent;
    public String[][] child;
    private OnItemClickListener mOnItemClickListener = null;

    public PopupWindowAdapter(String[] parent, String[][] child, Context context) {
        this.context = context;
        this.child = child;
        this.parent = parent;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.popupwindow_recyclerview_item, null);
        return new ViewHolder(view);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.textTitle.setText(parent[position]);
        int count = 0;
        int heightCount = 0;
        if ((child[position].length % 3) == 0) {
            heightCount = (int) (child[position].length / 3);
        } else {
            heightCount = (int) (child[position].length / 3) + 1;
        }
        for (int i = 0; i < heightCount; i++) {
            LinearLayout linearLayout = (LinearLayout) addLinearLayout(holder.parentLayout, heightCount);
            for (int k = 0; k < 3; k++) {
                if (count < child[position].length) {
                    TextView btn = (TextView) addButton(holder.parentLayout, linearLayout, child[position][count], parent[position]);
                    linearLayout.addView(btn);
                    count++;
                }
            }
            holder.parentLayout.addView(linearLayout);
        }
    }

    private View addButton(LinearLayout totalLinearLayout, LinearLayout linearLayout, String str, String parentString) {
        View contentView = LayoutInflater.from(context).inflate(R.layout.button_custom, linearLayout, false);
        TextView button = (TextView) contentView.findViewById(R.id.customBtn);
        button.setText(str);
        totalLinearLayout.setTag(parentString);
        button.setTag(totalLinearLayout);
        button.setOnClickListener(this);
        return contentView;
    }

    private View addLinearLayout(LinearLayout layout, int position) {
        View contentView = LayoutInflater.from(context).inflate(R.layout.layout_custom, layout, false);
        LinearLayout linearLayout = (LinearLayout) contentView.findViewById(R.id.layoutCustom);
        linearLayout.setTag(position);
        return contentView;
    }

    @Override
    public int getItemCount() {
        return parent.length;
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            TextView textView = (TextView) v;
            String childString = textView.getText().toString();
            LinearLayout parentLinear = (LinearLayout) textView.getTag();
            String parentString = parentLinear.getTag().toString();
            showView(parentLinear, textView);
            add(parentString, childString);
            //注意这里使用getTag方法获取position
            mOnItemClickListener.onItemClick(v, conditionsList);
        }
    }

    private void showView(LinearLayout parent, TextView textView) {
        for (int i = 0; i < parent.getChildCount(); i++) {
            LinearLayout childLinear = (LinearLayout) parent.getChildAt(i);
            for (int k = 0; k < childLinear.getChildCount(); k++) {
                TextView text = (TextView) childLinear.getChildAt(k);
                if(text.isSelected()){
                    remove(parent.getTag().toString(),text.getText().toString());
                    text.setTextColor(context.getResources().getColor(R.color.btn_textcolor_unselected));
                    text.setSelected(false);
                }
            }
        }
        textView.setTextColor(context.getResources().getColor(R.color.white));
        textView.setSelected(true);
    }

    private void remove(String parent, String child) {
        for (PopPupWindowSelectedBean contents : conditionsList) {
            if (contents.getParentTitle().equals(parent) && contents.getTitle().equals(child)) {
                conditionsList.remove(contents);
            }
        }
    }

    private void add(String parent, String child) {
        PopPupWindowSelectedBean bean = new PopPupWindowSelectedBean();
        bean.setParentTitle(parent);
        bean.setTitle(child);
        int count = 0;
        for (PopPupWindowSelectedBean contents : conditionsList) {
            if (contents.getParentTitle().equals(parent)) {
                count++;
                conditionsList.set(conditionsList.indexOf(contents), bean);
            }
        }
        if (count == 0) {
            conditionsList.add(bean);
        }
    }

    //define interface
    public static interface OnItemClickListener {
        void onItemClick(View view, List<PopPupWindowSelectedBean> list);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textTitle;
        public LinearLayout parentLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            textTitle = (TextView) itemView.findViewById(R.id.textTitle);
            parentLayout = (LinearLayout) itemView.findViewById(R.id.parentLayout);

        }
    }
}

