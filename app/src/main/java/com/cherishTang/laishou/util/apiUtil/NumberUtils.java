package com.cherishTang.laishou.util.apiUtil;

import android.content.Context;

import com.cherishTang.laishou.api.RegexUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by 方舟 on 2018/3/16.
 * 有关数字工具类
 */

public class NumberUtils {

    private static final String UNIT = "万千佰拾亿千佰拾万千佰拾元角分";
    private static final String DIGIT = "零壹贰叁肆伍陆柒捌玖";
    private static final double MAX_VALUE = 9999999999999.99D;

    //转为大写金额转为大写
    public static String change(double v) {
        if (v < 0 || v > MAX_VALUE) {
            return "参数非法!";
        }
        long l = Math.round(v * 100);
        if (l == 0) {
            return "零元整";
        }
        String strValue = l + "";
        // i用来控制数
        int i = 0;
        // j用来控制单位
        int j = UNIT.length() - strValue.length();
        String rs = "";
        boolean isZero = false;
        for (; i < strValue.length(); i++, j++) {
            char ch = strValue.charAt(i);
            if (ch == '0') {
                isZero = true;
                if (UNIT.charAt(j) == '亿' || UNIT.charAt(j) == '万'
                        || UNIT.charAt(j) == '元') {
                    rs = rs + UNIT.charAt(j);
                    isZero = false;
                }
            } else {
                if (isZero) {
                    rs = rs + "零";
                    isZero = false;
                }
                rs = rs + DIGIT.charAt(ch - '0') + UNIT.charAt(j);
            }
        }
//      if (!rs.endsWith("分")) {
//          rs = rs + "整";
//      }
        rs = rs.replaceAll("亿万", "亿");
        return rs;
    }

    /**
     * 定义数组存放数字对应的大写
     */
    private final static String[] STR_NUMBER = {"零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖"};

    /**
     * 定义数组存放位数的大写
     */
    private final static String[] STR_MODIFY = {"", "拾", "佰", "仟", "万", "拾", "佰", "仟", "亿", "拾", "佰", "仟"};

    /**
     * 转化整数部分
     *
     * @param tempString
     * @return 返回整数部分
     */
    private static String getInteger(String tempString) {
        /** 用来保存整数部分数字串 */
        String strInteger = null;//
        /** 记录"."所在位置 */
        int intDotPos = tempString.indexOf(".");
        int intSignPos = tempString.indexOf("-");
        if (intDotPos == -1)
            intDotPos = tempString.length();
        /** 取出整数部分 */
        strInteger = tempString.substring(intSignPos + 1, intDotPos);
        strInteger = new StringBuffer(strInteger).reverse().toString();
        StringBuffer sbResult = new StringBuffer();
        for (int i = 0; i < strInteger.length(); i++) {
            sbResult.append(STR_MODIFY[i]);
            sbResult.append(STR_NUMBER[strInteger.charAt(i) - 48]);
        }

        sbResult = sbResult.reverse();
        replace(sbResult, "零拾", "零");
        replace(sbResult, "零佰", "零");
        replace(sbResult, "零仟", "零");
        replace(sbResult, "零万", "万");
        replace(sbResult, "零亿", "亿");
        replace(sbResult, "零零", "零");
        replace(sbResult, "零零零", "零");
        /** 这两句不能颠倒顺序 */
        replace(sbResult, "零零零零万", "");
        replace(sbResult, "零零零零", "");
        /** 这样读起来更习惯. */
        replace(sbResult, "壹拾亿", "拾亿");
        replace(sbResult, "壹拾万", "拾万");
        /** 删除个位上的零 */
        if (sbResult.charAt(sbResult.length() - 1) == '零' && sbResult.length() != 1)
            sbResult.deleteCharAt(sbResult.length() - 1);
        if (strInteger.length() == 2) {
            replace(sbResult, "壹拾", "拾");
        }
        /** 将结果反转回来. */
        return sbResult.toString();
    }

    /**
     * 转化小数部分 例：输入22.34返回叁肆
     *
     * @param tempString
     * @return
     */
    private static String getFraction(String tempString) {
        String strFraction = null;
        int intDotPos = tempString.indexOf(".");
        /** 没有点说明没有小数，直接返回 */
        if (intDotPos == -1)
            return "";
        strFraction = tempString.substring(intDotPos + 1);
        StringBuffer sbResult = new StringBuffer(strFraction.length());
        for (int i = 0; i < strFraction.length(); i++) {
            sbResult.append(STR_NUMBER[strFraction.charAt(i) - 48]);
        }
        return sbResult.toString();
    }

    /**
     * 判断传入的字符串中是否有.如果有则返回点
     *
     * @param tempString
     * @return
     */
    private static String getDot(String tempString) {
        return tempString.indexOf(".") != -1 ? "点" : "";
    }

    /**
     * 判断传入的字符串中是否有-如果有则返回负
     *
     * @param tempString
     * @return
     */
    private static String getSign(String tempString) {
        return tempString.indexOf("-") != -1 ? "负" : "";
    }

    /**
     * 将一个数字转化为金额
     *
     * @param tempNumber 传入一个double的变量
     * @return 返一个转换好的字符串
     */
    public static String numberToChinese(double tempNumber) {
        java.text.DecimalFormat df = new java.text.DecimalFormat("#.#########");
        String pTemp = String.valueOf(df.format(tempNumber));
        StringBuffer sbResult = new StringBuffer(getSign(pTemp) + getInteger(pTemp) + getDot(pTemp) + getFraction(pTemp));
        return sbResult.toString();
    }

    public static String numberToChinese(BigDecimal tempNumber) {
        return numberToChinese(tempNumber.doubleValue());
    }

    /**
     * 替代字符
     *
     * @param pValue
     * @param pSource
     * @param pDest
     */
    private static void replace(StringBuffer pValue, String pSource, String pDest) {
        if (pValue == null || pSource == null || pDest == null)
            return;
        /** 记录pSource在pValue中的位置 */
        int intPos = 0;
        do {
            intPos = pValue.toString().indexOf(pSource);
            /** 没有找到pSource */
            if (intPos == -1)
                break;
            pValue.delete(intPos, intPos + pSource.length());
            pValue.insert(intPos, pDest);
        } while (true);
    }

    /**
     * 小数格式化
     *
     * @param data 数字
     * @return 精确到小数点后两位
     */
    public static String decimalFormat(String data) {
        try {
            Pattern pattern = Pattern.compile("-?[0-9]+.?[0-9]+");
            Matcher isNum = pattern.matcher(data);
            if (!isNum.matches()) {
                return data;
            }
            DecimalFormat df = new DecimalFormat("#0.00");
            return df.format(Double.parseDouble(data));
        } catch (Exception e) {
            e.printStackTrace();
            return data;
        }
    }


    /**
     * 小数转整数
     *
     * @param numString
     * @return
     */
    public static String formatInteger(String numString) {
        try {
            Pattern pattern = Pattern.compile("-?[0-9]+.?[0-9]+");
            Matcher isNum = pattern.matcher(numString);
            if (!isNum.matches()) {
                return numString;
            }
            DecimalFormat df = new DecimalFormat("#0");
            return df.format(Double.parseDouble(numString));
        } catch (Exception e) {
            e.printStackTrace();
            return numString;
        }
    }

    public static String decimalFormatInteger(String data) {
        if (data == null) return null;
        try {
            if (RegexUtils.isInteger(data) || data.endsWith(".00") || data.endsWith(".0")) {
                data = formatInteger(data);
            } else {
                data = decimalFormat(data);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return data;
        }
        return data;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2Px(Context mContext, float dpValue) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

}
