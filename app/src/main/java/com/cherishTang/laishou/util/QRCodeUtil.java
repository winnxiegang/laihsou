package com.cherishTang.laishou.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.support.v4.app.Fragment;
import android.view.Display;
import android.view.WindowManager;
import android.widget.Toast;

import com.cherishTang.laishou.R;
import com.cherishTang.laishou.api.ApiHttpClient;
import com.cherishTang.laishou.api.AppManager;
import com.cherishTang.laishou.api.ConstantsHelper;
import com.cherishTang.laishou.bean.base.ResultBean;
import com.cherishTang.laishou.custom.dialog.CustomProgressDialog;
import com.cherishTang.laishou.okhttp.callback.StringCallback;
import com.cherishTang.laishou.util.log.ToastUtils;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.cherishTang.laishou.zxing.DecodeActivity;

import org.json.JSONObject;

import java.util.EnumMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Request;

/**
 * 扫描条形码、二维码，将字符串编码成二维码图片的工具类
 * Created by Zero on 2016/2/25.
 */
public class QRCodeUtil {
    private static QRCodeUtil zxingUtil;

    private QRCodeUtil() {
    }

    public static QRCodeUtil getInstance() {
        if (zxingUtil == null) {
            zxingUtil = new QRCodeUtil();
        }
        return zxingUtil;
    }

    private static OnDecodeListener onDecodeListener;
    private static CustomProgressDialog customProgressDialog;

    public interface OnDecodeListener {
        void onDecodeComplete(boolean isComplete);
    }

    public static void setOnDecodeListener(OnDecodeListener onDecodeListener) {
        QRCodeUtil.onDecodeListener = onDecodeListener;
    }

    /**
     * 解析二维码数据
     *
     * @param context
     * @param result
     * @throws Exception
     */
    public static void decodeResult(Context context, String result,OnDecodeListener onDecodeListener) throws Exception {
        setOnDecodeListener(onDecodeListener);
        JSONObject jsonObject = new JSONObject(result);
        if (jsonObject.has("counselorId")) {//绑定顾问
            ApiHttpClient.bindCounselor(result,context, stringCallback);
        } else if (jsonObject.has("clubId")) {//绑定俱乐部
            ApiHttpClient.bindClub(result,context, stringCallback);
        } else {
            Toast.makeText(context,
                    "解析失败", Toast.LENGTH_SHORT).show();
        }
    }

    static StringCallback stringCallback = new StringCallback() {
        @Override
        public void onBefore(Request request, int id) {
            super.onBefore(request, id);
            if (customProgressDialog == null || !customProgressDialog.isShowing())
                customProgressDialog = new CustomProgressDialog(AppManager.getAppManager().currentActivity(), R.style.loading_dialog);
            customProgressDialog.setMessage("正在加载，请稍后...");
            customProgressDialog.show();
        }

        @Override
        public void onAfter(int id) {
            super.onAfter(id);
            if (customProgressDialog != null && customProgressDialog.isShowing())
                customProgressDialog.dismiss();
        }

        @Override
        public void onError(Call call, Exception e, int id) {
            ToastUtils.showShort(AppManager.getAppManager().currentActivity(), R.string.requestError);
            if (onDecodeListener!=null)
                onDecodeListener.onDecodeComplete(false);
        }

        @Override
        public void onResponse(String response, int id) {
            ResultBean<Object> resultBean = new GsonBuilder().disableHtmlEscaping().create().
                    fromJson(response, new TypeToken<ResultBean<Object>>() {
                    }.getType());
            if (onDecodeListener!=null)
                onDecodeListener.onDecodeComplete(resultBean.isSuccess());
        }
    };

    /**
     * 将文字编码成二维码图片
     */
    public Bitmap encodeAsBitmap(Context context, String contents) throws WriterException {
        if (contents == null) {
            return null;
        }
        Map<EncodeHintType, Object> hints = null;
        String encoding = null;
        for (int i = 0; i < contents.length(); i++) {
            if (contents.charAt(i) > 0xFF) {
                encoding = "UTF-8";
                break;
            }
        }
        if (encoding != null) {
            hints = new EnumMap<>(EncodeHintType.class);
            hints.put(EncodeHintType.CHARACTER_SET, encoding);
        }

        WindowManager manager = (WindowManager) context.getSystemService(context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        Point displaySize = new Point();
        display.getSize(displaySize);
        int displayWidth = displaySize.x;
        int displayHeight = displaySize.y;
        int smallerDimension = displayWidth < displayHeight ? displayWidth : displayHeight;
        smallerDimension = smallerDimension * 7 / 8;

        BitMatrix result;
        try {
            result = new MultiFormatWriter().encode(contents, BarcodeFormat.QR_CODE, smallerDimension, smallerDimension, hints);
        } catch (IllegalArgumentException iae) {
            // Unsupported format
            return null;
        }
        int width = result.getWidth();
        int height = result.getHeight();
        int[] pixels = new int[width * height];
        for (int y = 0; y < height; y++) {
            int offset = y * width;
            for (int x = 0; x < width; x++) {
                pixels[offset + x] = result.get(x, y) ? 0xFF000000 : 0xFFFFFFFF;
            }
        }

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }

    /**
     * 扫描条形码和二维码
     */
    public void decode(Context context) {
        context.startActivity(new Intent(context, DecodeActivity.class));
    }

    /**
     * 扫描条形码和二维码
     */
    public void decode(Fragment context) {
        context.startActivityForResult(new Intent(context.getActivity(), DecodeActivity.class), ConstantsHelper.QR_CODE_REQUEST);
    }

    /**
     * 扫描条形码和二维码
     */
    public void decode(Activity context) {
        context.startActivityForResult(new Intent(context, DecodeActivity.class), ConstantsHelper.QR_CODE_REQUEST);
    }
}
