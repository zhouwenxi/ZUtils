package com.qishui.commontoolslibrary.core;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * 作者: create by qishui
 * 日期：2019/3/22  11:01
 * 邮箱: qishuichixi@163.com
 * 描述：大数字,精确位数
 */

public class BigDecimalUtils {


    /**
     * 添加人: add by qishui
     * 添加时间: 2019/3/22  11:02
     * 添加注释: 比较大小  value1 小于 value2 -1  等于0  大于1
     */
    public static int compare(double value1, double value2) {
        return compare(String.valueOf(value1), String.valueOf(value2));
    }

    public static int compare(String value1, String value2) {
        BigDecimal bigDecimal = new BigDecimal(value1);
        BigDecimal bigDecimal1 = new BigDecimal(value2);
        return bigDecimal.compareTo(bigDecimal1);
    }

    /**
     * 添加人: add by qishui
     * 添加时间: 2019/3/22  11:04
     * 添加注释: 数字相加
     */
    public static double add(String... values) {
        BigDecimal total = BigDecimal.ZERO;
        for (String value : values) {
            total = total.add(new BigDecimal(value));
        }
        return total.doubleValue();
    }

    public static double add(double... values) {
        String[] strValues = new String[values.length];
        for (int i = 0; i < values.length; i++) {
            strValues[i] = String.valueOf(values[i]);
        }
        return add(strValues);
    }

    /**
     * 添加人: add by qishui
     * 添加时间: 2019/3/22  11:07
     * 添加注释: 减法
     */
    public static double subtract(String... values) {
        if (values.length > 0) {
            BigDecimal total = new BigDecimal(values[0]);
            for (int i = 1; i < values.length; i++) {
                total = total.subtract(new BigDecimal(values[i]));
            }
            return total.doubleValue();
        }
        return 0;
    }

    public static double subtract(double... values) {
        String[] strValues = new String[values.length];
        for (int i = 0; i < values.length; i++) {
            strValues[i] = String.valueOf(values[i]);
        }
        return subtract(strValues);
    }

    public static double multiply(String... values) {
        if (values.length > 0) {
            BigDecimal total = new BigDecimal(values[0]);
            for (int i = 1; i < values.length; i++) {
                total = total.multiply(new BigDecimal(values[i]));
            }
            return total.doubleValue();
        }
        return 0;
    }

    /**
     * 添加人: add by qishui
     * 添加时间: 2019/3/22  11:08
     * 添加注释: 乘法
     */
    public static double multiply(double... values) {
        String[] strValues = new String[values.length];
        for (int i = 0; i < values.length; i++) {
            strValues[i] = String.valueOf(values[i]);
        }
        return multiply(strValues);
    }

    /**
     * 添加人: add by qishui
     * 添加时间: 2019/3/22  11:08
     * 添加注释: 除法
     */
    public static double divide(String value1, String value2, int scale, int model) {
        return new BigDecimal(value1).divide(new BigDecimal(value2), scale, model).doubleValue();
    }

    public static double divide(double value1, double value2, int scale, int model) {
        return divide(String.valueOf(value1), String.valueOf(value2), scale, model);
    }

    public static double divide(String value1, String value2) {
        return new BigDecimal(value1).divide(new BigDecimal(value2), 3, RoundingMode.HALF_UP).doubleValue();
    }

    public static double divide(double value1, double value2) {
        return divide(String.valueOf(value1), String.valueOf(value2));
    }

    /**
     * 添加人: add by qishui
     * 添加时间: 2019/3/22  11:09
     * 添加注释: 绝对值
     */
    public static double abs(double value) {
        return new BigDecimal(String.valueOf(value)).abs().doubleValue();
    }

    /**
     * 添加人: add by qishui
     * 添加时间: 2019/3/22  11:09
     * 添加注释: 最大值
     */
    public static double max(String value1, String value2) {
        return new BigDecimal(value1).max(new BigDecimal(value2)).doubleValue();
    }

    public static double max(double value1, double value2) {
        return max(String.valueOf(value1), String.valueOf(value2));
    }

    /**
     * 添加人: add by qishui
     * 添加时间: 2019/3/22  11:10
     * 添加注释: 最小值
     */
    public static double min(String value1, String value2) {
        return new BigDecimal(value1).min(new BigDecimal(value2)).doubleValue();
    }

    public static double min(double value1, double value2) {
        return min(String.valueOf(value1), String.valueOf(value2));
    }


    /**
     * double pi = 3.1415;
     * //3
     * System.out.println(new DecimalFormat("0").format(pi));
     * //3.14
     * System.out.println(new DecimalFormat("0.00").format(pi));
     * //3.14150
     * System.out.println(new DecimalFormat("0.00000").format(pi));
     * //03.142
     * System.out.println(new DecimalFormat("00.000").format(pi));
     * //3
     * System.out.println(new DecimalFormat("#").format(pi));
     * //3
     * System.out.println(new DecimalFormat("##").format(pi));
     * //3.1
     * System.out.println(new DecimalFormat("##.#").format(pi));
     * //314.16%
     * System.out.println(new DecimalFormat("#.##%").format(pi));
     * //圆周率为 3.14 米
     * System.out.println(new DecimalFormat("圆周率为 ###.00 米").format(pi));
     * //113.1
     * System.out.println(new DecimalFormat("##.#").format(113.1415));
     * //113.2
     * System.out.println(new DecimalFormat("00.#").format(113.1615));
     * //11211.210012
     * System.out.println(new BigDecimal("0011211.210012000").stripTrailingZeros().toPlainString());
     */
    public static String getFormatString(Number value, String format) {
        return new DecimalFormat(format).format(value);
    }

    public static Double getFormatDouble(Number value, String format) {
        return add(new DecimalFormat(format).format(value));
    }

//    public static void main(String args[]) {
//        System.out.println(getFormatString(11.22233, "0.##")+10);
//        System.out.println(getFormatDouble(11.22233, "0.##")+10);
//    }


}
