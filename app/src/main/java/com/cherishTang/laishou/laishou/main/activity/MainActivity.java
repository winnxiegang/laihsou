package com.cherishTang.laishou.laishou.main.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationManagerCompat;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.cherishTang.laishou.R;
import com.cherishTang.laishou.activity.PermissionsActivity;
import com.cherishTang.laishou.api.ConstantsHelper;
import com.cherishTang.laishou.api.UserAccountHelper;
import com.cherishTang.laishou.base.BaseActivity;
import com.cherishTang.laishou.broadCastReceiver.LocalBroadcastManager;
import com.cherishTang.laishou.custom.customlayout.NavigationButton;
import com.cherishTang.laishou.custom.dialog.DialogHelper;
import com.cherishTang.laishou.laishou.club.fragment.LaiShouCircleFragment;
import com.cherishTang.laishou.laishou.main.fragment.IndexFragment;
import com.cherishTang.laishou.laishou.main.fragment.LaiShowSimpleFragment;
import com.cherishTang.laishou.laishou.main.fragment.MeFragment;
import com.cherishTang.laishou.laishou.main.fragment.SpellGoodsListFragment;
import com.cherishTang.laishou.service.UpdateService;
import com.cherishTang.laishou.util.JPushDataUitl;
import com.cherishTang.laishou.util.log.ToastUtils;
import com.cherishTang.laishou.util.permission.PermissionUtils;
import com.cherishTang.laishou.util.step.service.StepService;
import com.cherishTang.laishou.util.step.utils.SharedPreferencesUtils;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.OnClick;

@Route(path = ConstantsHelper.MAIN)
public class MainActivity extends BaseActivity {
    @BindView(R.id.nv_index)
    NavigationButton nvIndex;
    @BindView(R.id.nv_me)
    NavigationButton nvMe;
    @BindView(R.id.nv_layout)
    LinearLayout nvLayout;
    @BindView(R.id.activity_main)
    RelativeLayout activityMain;
    @BindView(R.id.nv_unit)
    NavigationButton nvUnit;
    @BindView(R.id.nv_discount)
    NavigationButton nvDiscount;
    private OnNavigationReselectListener mOnNavigationReselectListener;
    private Context mContext;
    private int mContainerId;
    private FragmentManager mFragmentManager;
    public NavigationButton mCurrentNavButton;

    public static boolean isForeground = false;

    // 定义一个变量，来标识是否退出
    private static boolean isExit = false;
    private static final int REQUEST_CODE = 0; // 请求码

    // 所需的全部权限
    static String[] PERMISSIONS = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION
    };
    private SharedPreferencesUtils sp;

    @Override
    protected boolean hasToolBar() {
        return false;
    }

    @Override
    public String setTitleBar() {
        return null;
    }

    @Override
    public int initLayout() {
        return R.layout.activity_main;
    }

    //权限检测
    private void checkPermission() {
        if (!NotificationManagerCompat.from(this).areNotificationsEnabled() && !UserAccountHelper.getNotifications()) {
            requestPermission();
        }
        if (checkPermission(PERMISSIONS)) {
            requestPermission(this, PERMISSIONS);
        }
    }

    private boolean isBind = false;

    /**
     * 初始化服务
     */
    private void initReceiver() {
        registerMessageReceiver();
        Intent intent = new Intent(this, StepService.class);
//        isBind = bindService(intent, conn, Context.BIND_AUTO_CREATE);
        startService(intent);
        //启动亮屏service检测服务
//        Intent intentFloat = new Intent(MainActivity.this, FloatService.class);
//        startService(intentFloat);
        Intent intentUpdate = new Intent(MainActivity.this, UpdateService.class);
        startService(intentUpdate);
    }

    /**
     * 用于查询应用服务（application Service）的状态的一种interface，
     * 更详细的信息可以参考Service 和 context.bindService()中的描述，
     * 和许多来自系统的回调方式一样，ServiceConnection的方法都是进程的主线程中调用的。
     */
    ServiceConnection conn = new ServiceConnection() {
        /**
         * 在建立起于Service的连接时会调用该方法，目前Android是通过IBind机制实现与服务的连接。
         * @param name 实际所连接到的Service组件名称
         * @param service 服务的通信信道的IBind，可以通过Service访问对应服务
         */
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            StepService stepService = ((StepService.StepBinder) service).getService();
            //设置初始化数据
            String planWalk_QTY = (String) sp.getParam("planWalk_QTY", "7000");

            //设置步数监听回调
            stepService.registerCallback(stepCount -> {
                String planWalk_QTY1 = (String) sp.getParam("planWalk_QTY", "7000");
            });
        }

        /**
         * 当与Service之间的连接丢失的时候会调用该方法，
         * 这种情况经常发生在Service所在的进程崩溃或者被Kill的时候调用，
         * 此方法不会移除与Service的连接，当服务重新启动的时候仍然会调用 onServiceConnected()。
         * @param name 丢失连接的组件名称
         */
        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    //通知栏推送消息获取权限
    protected void requestPermission() {
        DialogHelper.getConfirmDialog(this,
                "通知权限已关闭，是否前往设置中心开启此功能?",
                (dialog, which) -> {
                    // 6.0以上系统才可以判断权限
                    // 进入设置系统应用权限界面
                    Intent intent = new Intent();
                    intent.setAction("android.intent.action.MAIN");
                    intent.setClassName("com.android.settings", "com.android.settings.ManageApplications");
                    startActivity(intent);
                }, (dialog, which) -> UserAccountHelper.setNotifications(true)).show();
    }

    @Override
    protected void onResume() {
        //收起键盘
        try {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(),
                        0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        isForeground = true;
        super.onResume();
    }

    @Override
    protected void onPause() {
        //收起键盘
        try {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(),
                        0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        isForeground = false;
        super.onPause();
    }

    /**
     * show the MainActivity
     *
     * @param context context
     */
    public static void show(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    //初始化布局
    public void initView() {
        nvIndex.init(R.drawable.main_index_selector, R.string.nv_index, IndexFragment.class);
        nvMe.init(R.drawable.main_me_selector, R.string.nv_me, MeFragment.class);
        nvUnit.init(R.drawable.main_setting_selector, R.string.nv_unit, LaiShouCircleFragment.class);
        nvDiscount.init(R.drawable.main_discount_selector, R.string.nv_discount, SpellGoodsListFragment.class);

        setTransparentStatusBar(R.color.themeColor);
        mCurrentNavButton = nvIndex;
        nvIndex.setSelected(true);
        doTabChanged(null, nvIndex);
        initReceiver();
        //消息通知权限，如果没有开启的话，跳转到系统设置的应用管理页面,为了适配部分手机无法自动获取
        checkPermission();
        getTipsMakeInvoiceDialog();
    }

    @Override
    public void initData() {
        sp = new SharedPreferencesUtils(this);

    }

    private long lastClickTime = 0;

    @OnClick({R.id.nv_index, R.id.nv_unit, R.id.nv_discount, R.id.nv_me})
    public void onClick(View v) {
        long currentTime = Calendar.getInstance().getTimeInMillis();
        if (currentTime - lastClickTime < 300) {
            lastClickTime = currentTime;
            return;
        }
        lastClickTime = currentTime;
        if (v instanceof NavigationButton) {
            NavigationButton nav = (NavigationButton) v;
            doSelect(nav);
        }
    }

    private void doSelect(NavigationButton newNavButton) {
        NavigationButton oldNavButton = null;
        if (mCurrentNavButton != null) {
            oldNavButton = mCurrentNavButton;
            if (oldNavButton == newNavButton) {
                onReselect(oldNavButton);
                return;
            }
            oldNavButton.setSelected(false);
        }
        newNavButton.setSelected(true);
        doTabChanged(oldNavButton, newNavButton);
        mCurrentNavButton = newNavButton;
    }

    private void doTabChanged(NavigationButton oldNavButton, NavigationButton newNavButton) {
        mFragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = mFragmentManager.beginTransaction();
        if (oldNavButton != null) {
            if (oldNavButton.getFragment() != null) {
                ft.hide(oldNavButton.getFragment());
            }
        }
        if (newNavButton != null) {
            if (newNavButton.getFragment() == null) {
                Fragment fragment = Fragment.instantiate(MainActivity.this,
                        newNavButton.getClx().getName(), null);
                ft.add(R.id.frameLayout, fragment, newNavButton.getTag());
                newNavButton.setFragment(fragment);
            } else {
                ft.show(newNavButton.getFragment());
            }
        }
        ft.commit();
    }

    private void onReselect(NavigationButton navigationButton) {
        OnNavigationReselectListener listener = mOnNavigationReselectListener;
        if (listener != null) {
            listener.onReselect(navigationButton);
        }
    }

    interface OnNavigationReselectListener {
        void onReselect(NavigationButton navigationButton);
    }

    @Override
    protected void onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
//        if (isBind) {
//            this.unbindService(conn);
//        }
        super.onDestroy();
    }

    /**
     * 主线程处理视图，isExit默认为false，就是点击第一次时，弹出"再按一次退出程序"
     * 点击第二次时关闭应用
     */
    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 点击两次退出程序
     */
    private void exit() {
        if (!isExit) {
            isExit = true;
            Toast.makeText(getApplicationContext(), "再按一次退出程序",
                    Toast.LENGTH_SHORT).show();
            // 利用handler延迟发送更改状态信息
            mHandler.sendEmptyMessageDelayed(0, 2000);
        } else {
            finish();
            //参数用作状态码；根据惯例，非 0 的状态码表示异常终止。
            System.exit(0);
        }
    }

    //for receive customer msg from jpush server
    private MessageReceiver mMessageReceiver;


    public void registerMessageReceiver() {
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(ConstantsHelper.MESSAGE_RECEIVED_ACTION);
        filter.addAction(ConstantsHelper.MESSAGE_INFO);
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, filter);
    }

    public class MessageReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (ConstantsHelper.MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
                String message = intent.getStringExtra(ConstantsHelper.KEY_MESSAGE);
                String extras = intent.getStringExtra(ConstantsHelper.KEY_EXTRAS);
                StringBuilder showMsg = new StringBuilder();
                showMsg.append(ConstantsHelper.KEY_MESSAGE + " : " + message + "\n");
                if (!JPushDataUitl.isEmpty(extras)) {
                    showMsg.append(ConstantsHelper.KEY_EXTRAS + " : " + extras + "\n");
                }
                setCustomMsg(showMsg.toString());
            } else if (ConstantsHelper.MESSAGE_INFO.equals(intent.getAction())) {
//                Bundle bundle = intent.getExtras();
//                if (bundle != null && bundle.size() != 0) {
//                    CustomNotificationBean notificationBean = new CustomNotificationBean(bundle.getString("title"),
//                            bundle.getString("message"));
//                    customNotificationBeanList.add(notificationBean);
//                }
            }
        }
    }

    private void setCustomMsg(String msg) {
//        if (null != msgText) {
//            msgText.setText(msg);
//            msgText.setVisibility(View.VISIBLE);
//        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 拒绝时, 关闭页面, 缺少主要权限, 无法运行
        if (requestCode == REQUEST_CODE && resultCode == PermissionsActivity.PERMISSIONS_DENIED) {
            ToastUtils.showShort(this, "权限拒绝可能会导致部分功能无法正常使用");
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (null != this.getCurrentFocus()) {
            /*
             * 点击空白位置 隐藏软键盘
             */
            InputMethodManager mInputMethodManager = (InputMethodManager) this.getSystemService(INPUT_METHOD_SERVICE);
            return mInputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionUtils.requestPermissionsResult(this, requestCode, permissions, grantResults, mPermissionGrant);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    PermissionUtils.PermissionGrant mPermissionGrant = requestCode -> {
        switch (requestCode) {
            case PermissionUtils.CODE_RECORD_AUDIO:
                break;
            case PermissionUtils.CODE_GET_ACCOUNTS:
                break;
            case PermissionUtils.CODE_READ_PHONE_STATE:
                break;
            case PermissionUtils.CODE_CALL_PHONE:
                break;
            case PermissionUtils.CODE_CAMERA:
                break;
            case PermissionUtils.CODE_ACCESS_FINE_LOCATION:
                break;
            case PermissionUtils.CODE_ACCESS_COARSE_LOCATION:
                break;
            case PermissionUtils.CODE_READ_EXTERNAL_STORAGE:
                break;
            case PermissionUtils.CODE_WRITE_EXTERNAL_STORAGE:
                break;
            default:
                break;
        }
    };

    public void getTipsMakeInvoiceDialog() {
        Dialog builder;
        LayoutInflater factory = LayoutInflater.from(this);
        View textEntryView = factory.inflate(R.layout.make_invoice_dialog,//dialog的view
                null);
        builder = new Dialog(this,
                R.style.AlarmDialog);
        builder.show();//这些设置必须放在 dialog.show();后面才能有效果
        Window window = builder.getWindow();
        window.setContentView(textEntryView);
        Display display = this.getWindowManager().getDefaultDisplay();
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = (int) (display.getWidth() * 0.7);
        params.height = (int) (display.getHeight() * 0.8);    //使用这种方式更改了dialog的框宽
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
        ImageView balance_close = (ImageView) textEntryView.findViewById(R.id.balance_close);
        balance_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.dismiss();
            }
        });
    }
}
