package com.cherishTang.laishou.custom.swiperefreshlayout.wight;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cherishTang.laishou.R;
import com.cherishTang.laishou.api.UserAccountHelper;
import com.cherishTang.laishou.custom.empty.EmptyLayout;
import com.cherishTang.laishou.custom.swiperefreshlayout.bean.RefreshDateBean;
import com.cherishTang.laishou.custom.swiperefreshlayout.customswipe.OnPullListener;
import com.cherishTang.laishou.custom.swiperefreshlayout.customswipe.SwipeRefreshLayout;
import com.cherishTang.laishou.util.apiUtil.DateUtil;

import java.util.Calendar;
import java.util.Date;


/**
 * Created by 方舟 on 2016/8/23.
 *
 */
public class SwipeRecyclerView extends FrameLayout implements OnPullListener ,EmptyLayout.OnEmptyLayoutClickListener {

    private final float OFFSET_RATIO = 0.3f;

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private TextView tvLoadingText;
    private boolean isCanLoadMore = true;//是否可以加载更多
    private boolean isCanRefresh = true;//是否可以刷新
    private EmptyLayout emptyLayout;
    //x上次保存的
    private int mLastMotionX;
    //y上次保存的
    private int mLastMotionY;
    //滑动状态
    private int mPullState;
    //上滑
    private int PULL_UP_STATE = 2;
    private int PULL_FINISH_STATE = 0;
    //当前滑动的距离
    private int curTransY;
    //尾部的高度
    private int footerHeight;
    //内容布局
    private View contentView;
    //尾部局
    private View footerView;
    private LinearLayout linearView;
    //是否上拉加载更多
    private boolean isLoadNext = false;
    //是否在加载中
    private boolean isLoading = false;
    private Integer emptyType;
    private boolean mIsRefreshing = false;//是否能够滑动
    private String refreshDateTag;//用于标记刷新控件
    private RotateAnimation mRotateAnimation;
    private RotateAnimation mRotateAnimationDown;
    private OnSwipeRecyclerViewListener onSwipeRecyclerViewListener;

    private boolean isCancelLoadNext = false;
    private View headView;

    public SwipeRecyclerView(Context context) {
        this(context, null);
    }

    public SwipeRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwipeRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    public void setRefreshDateTag(String refreshDateTag){
        this.refreshDateTag = refreshDateTag;
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return swipeRefreshLayout;
    }

    private void initView(Context context) {
        linearView = new LinearLayout(context);
        linearView.setOrientation(LinearLayout.VERTICAL);
        final LinearLayout.LayoutParams linearParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        addView(linearView, linearParams);

        contentView = LayoutInflater.from(context).inflate(R.layout.swiperecyclerview, null);
        swipeRefreshLayout = (SwipeRefreshLayout) contentView.findViewById(R.id.swiperefreshlayout);
        emptyLayout = (EmptyLayout) contentView.findViewById(R.id.emptyLayout);
        recyclerView = (RecyclerView) contentView.findViewById(R.id.recyclerview);
        footerView = LayoutInflater.from(context).inflate(R.layout.swiperecyclerview_footerview, null);
        tvLoadingText = (TextView) footerView.findViewById(R.id.loading_text);
        emptyLayout.setOnEmptyLayoutClickListener(this);
        swipeRefreshLayout.isShowColorProgressBar(false);
        //解决swipelayout与Recyclerview的冲突
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int topRowVerticalPosition =
                        (recyclerView == null || recyclerView.getChildCount() == 0) ? 0 : recyclerView.getChildAt(0).getTop();
                swipeRefreshLayout.setEnabled(topRowVerticalPosition >= 0);
//                swipeRefreshLayout.setEnabled(false);
            }
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
        recyclerView.setOnTouchListener(
                new OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        return mIsRefreshing;
                    }
                }
        );
        headView = contentView.findViewById(R.id.headview);
        initHeadView(headView);
        swipeRefreshLayout.setOnPullListener(this);

        swipeRefreshLayout.setColorSchemeResources(
                R.color.themeColor, R.color.themeColor,
                R.color.themeColor, R.color.themeColor);

        linearView.addView(contentView);
        linearView.addView(footerView);

        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onGlobalLayout() {
                int height = getHeight();
                if (height != 0) {
                    getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    ViewGroup.LayoutParams recycleParams = contentView.getLayoutParams();
                    recycleParams.height = height;
                    contentView.setLayoutParams(recycleParams);

                    ViewGroup.LayoutParams footerParams = tvLoadingText.getLayoutParams();
                    footerHeight = footerParams.height;

                    ViewGroup.LayoutParams contentParams = linearView.getLayoutParams();
                    contentParams.height = height + footerHeight;
                    linearView.setLayoutParams(contentParams);
                    curTransY = 0;
                }
            }
        });
    }
    public void setCanRefresh(boolean isCanRefresh){
        swipeRefreshLayout.setCanRefresh(isCanRefresh);
    }

    public void setCanLoadMore(boolean isCanLoadMore){
        this.isCanLoadMore = isCanLoadMore;
    }

    public void setSwipeRefreshColor(int color) {
    }

    public void isCancelLoadNext(Boolean isCancelLoadNext) {
        this.isCancelLoadNext = isCancelLoadNext;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (!isCanLoadMore) return false; //返回false则拦截手势事件，不会传递到TouchEvent
        if (isLoading) return true;
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastMotionX = x;
                mLastMotionY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaX = x - mLastMotionX;
                int deltaY = y - mLastMotionY;
                if (Math.abs(deltaX) < Math.abs(deltaY) && Math.abs(deltaY) > 10) {
                    if (isRefreshViewScroll(deltaY)) {
                        return true;
                    }
                }
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    private boolean isRefreshViewScroll(int deltaY) {
        if (deltaY < 0 && !recyclerView.canScrollVertically(1) && curTransY <= footerHeight && !isLoading && !isCancelLoadNext) {
            mPullState = PULL_UP_STATE;
            isLoading = true;
            return true;
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                float deltaY = y - mLastMotionY;
                if (mPullState == PULL_UP_STATE) {
                    curTransY += deltaY;
                    if (Math.abs(curTransY) > Math.abs(footerHeight)) {
                        curTransY = -footerHeight;
                    }
                    linearView.setTranslationY(curTransY);
                    if (Math.abs(curTransY) == Math.abs(footerHeight)) {
                        isLoadNext = true;
                    } else {
                        isLoadNext = false;
                    }
                }
                mLastMotionY = y;
                return true;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (isLoadNext) {
                    changeFooterState(true);
                    mPullState = PULL_FINISH_STATE;
                    setRecyclerViewVisibility(EmptyLayout.NETWORK_LOADING_LOADMORE);
                    if (onSwipeRecyclerViewListener != null) {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                onSwipeRecyclerViewListener.onLoadNext();
                            }
                        }, 500);
                    } else {
                        hideTranslationY();
                        isLoading = false;
                        isLoadNext = false;
                    }
                } else {
                    hideTranslationY();
                    isLoading = false;
                }
                return true;
        }
        return super.onTouchEvent(event);
    }

    public int getEmptyType() {
        return this.emptyType == null ? EmptyLayout.NODATA : this.emptyType;
    }

    public void setRecyclerViewVisibility(int emptyType) {
        if (emptyLayout == null || recyclerView == null) return;
        emptyLayout.setErrorType(emptyType);
        switch (emptyType) {
            case EmptyLayout.LOADING_ERROR:
                recyclerView.setVisibility(View.GONE);
                onRefreshFinish();
                onLoadFinish();
                break;
            case EmptyLayout.NETWORK_LOADING:
                emptyLayout.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
                break;
            case EmptyLayout.NETWORK_LOADING_RERESH://刷新
                emptyLayout.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                break;
            case EmptyLayout.NETWORK_LOADING_LOADMORE://加载
                emptyLayout.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                break;
            case EmptyLayout.NODATA:
                onRefreshFinish();
                onLoadFinish();
                recyclerView.setVisibility(View.GONE);
                break;
            case EmptyLayout.HIDE_LAYOUT:
                onRefreshFinish();
                onLoadFinish();
                emptyLayout.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                break;
        }
    }

    public void onRefreshFinish() {
        try {
            if(headView!=null){
                HeadHolder holder = (HeadHolder) headView.getTag();
                holder.mRefreshTitle.setText("刷新完成");
            }
            swipeRefreshLayout.setRefreshing(false);
            isLoading = false;
            isLoadNext = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onLoadFinish() {
        try {
            if (curTransY == 0) {
                return;
            }
            if(tvLoadingText!=null) tvLoadingText.setText("加载完成");
            isLoading = false;
            isLoadNext = false;
            hideTranslationY();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void hideTranslationY() {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(linearView, "translationY", curTransY, 0).setDuration(1000);
        objectAnimator.setInterpolator(new DecelerateInterpolator());
        objectAnimator.start();

        objectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                curTransY = 0;
                changeFooterState(false);
            }
        });
    }

    private void changeFooterState(boolean loading) {
        if (loading) {
            tvLoadingText.setText("正在努力的加载中...");
        } else {
            tvLoadingText.setText("加载更多");
        }
    }

    public void setOnSwipeRecyclerViewListener(OnSwipeRecyclerViewListener onSwipeRecyclerViewListener) {
        this.onSwipeRecyclerViewListener = onSwipeRecyclerViewListener;
    }

    private void initHeadView(View headview) {
        mRotateAnimation = new RotateAnimation(0, -180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        mRotateAnimation.setDuration(150);
        mRotateAnimation.setFillAfter(true);

        mRotateAnimationDown = new RotateAnimation(-180, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        mRotateAnimationDown.setDuration(150);
        mRotateAnimationDown.setFillAfter(true);
        HeadHolder holder = new HeadHolder();
        holder.mRefreshImageview = (ImageView) headview.findViewById(R.id.pull_to_refresh_image);
        holder.mRefreshProgress = (ProgressBar) headview.findViewById(R.id.pull_to_refresh_progress);
        holder.mRefreshTitle = (TextView) headview.findViewById(R.id.pull_to_refresh_text);
        holder.mRefreshDate = (TextView) headview.findViewById(R.id.pull_to_refresh_sub_text);
        holder.mRefreshDate.setText(UserAccountHelper.getDate(refreshDateTag));
        headview.setTag(holder);
    }

    @Override
    public void onPulling(View headview) {
        HeadHolder holder = (HeadHolder) headview.getTag();
        holder.mRefreshImageview.clearAnimation();
        holder.mRefreshImageview.setVisibility(View.VISIBLE);
        holder.mRefreshProgress.setVisibility(View.GONE);
        holder.mRefreshTitle.setText(getContext().getString(R.string.pullToRefresh) + "...");
        holder.mRefreshDate.setText(UserAccountHelper.getDate(refreshDateTag));
        holder.mRefreshImageview.startAnimation(mRotateAnimationDown);
    }

    @Override
    public void onCanRefreshing(View headview) {
        HeadHolder holder = (HeadHolder) headview.getTag();
        holder.mRefreshImageview.startAnimation(mRotateAnimation);
        holder.mRefreshTitle.setText(getContext().getString(R.string.loosenToRefresh) + "...");
        holder.mRefreshDate.setText(UserAccountHelper.getDate(refreshDateTag));
    }

    @Override
    public void onRefreshing(View headview) {
        HeadHolder holder = (HeadHolder) headview.getTag();
        holder.mRefreshImageview.clearAnimation();
        holder.mRefreshImageview.setVisibility(View.GONE);
        holder.mRefreshProgress.setVisibility(View.VISIBLE);
        holder.mRefreshTitle.setText(getContext().getString(R.string.refreshing) + "...");
        holder.mRefreshDate.setText(UserAccountHelper.getDate(refreshDateTag));
        setRecyclerViewVisibility(EmptyLayout.NETWORK_LOADING_RERESH);
        if (!isLoading) {
            isLoading = true;
            if (onSwipeRecyclerViewListener != null) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        UserAccountHelper.setDate(new RefreshDateBean(refreshDateTag, DateUtil.getDateTimeFormat(new Date(Calendar.getInstance().getTimeInMillis()))));//保存刷新时间
                        onSwipeRecyclerViewListener.onRefresh();
                    }
                }, 500);
            }
        }
    }

    class HeadHolder {
        ImageView mRefreshImageview;
        TextView mRefreshTitle;
        ProgressBar mRefreshProgress;
        TextView mRefreshDate;
    }

    @Override
    public void onEmptyLayoutClick(View v) {
        setRecyclerViewVisibility(EmptyLayout.NETWORK_LOADING_RERESH);
        if (!isLoading) {
            isLoading = true;
            if (onSwipeRecyclerViewListener != null) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        UserAccountHelper.setDate(new RefreshDateBean(refreshDateTag, DateUtil.getDateTimeFormat(new Date(Calendar.getInstance().getTimeInMillis()))));//保存刷新时间
                        onSwipeRecyclerViewListener.onRefresh();
                    }
                }, 500);
            }
        }
    }
    public interface OnSwipeRecyclerViewListener {
        public void onRefresh();
        public void onLoadNext();
    }

}
