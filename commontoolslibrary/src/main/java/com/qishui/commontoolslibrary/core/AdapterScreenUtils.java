package com.qishui.commontoolslibrary.core;

import android.content.ComponentCallbacks;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;

import java.lang.reflect.Field;

/**
 * 屏幕适配
 * dip：      Density independent pixels ，设备无关像素。
 * dp：       就是dip
 * px：       像素
 * dpi：      dots per inch ， 直接来说就是一英寸多少个像素点。常见取值 120，160，240。我一般称作像素密度，简称密度
 * density ：        直接翻译的话貌似叫 密度。常见取值 1.5 ， 1.0 。和标准dpi的比例（160px/inc）
 * 分辨率   ：        横纵2个方向的像素点的数量，常见取值 480X800 ，320X480
 * 屏幕尺寸：          屏幕对角线的长度。电脑电视同理。
 * densityDpi ：     就是我们常说的dpi。
 * density      ：   其实是 DPI / (160像素/英寸) 后得到的值。
 * <p>
 * 1. 计算dpi　　　比如一个机器，屏幕4寸，分辨率480X800，他的dpi能算么。
 * 因为不知道边长，肯定不能分开算，4是对角线长度，那直接用勾股定理算对角线像素，除以4，算出来大概是 dpi = 233 像素/英寸。
 * 那么density就是 （233 px/inch）/（160 px/inch）=1.46 左右
 * 顺带说下，android默认的只有3个dpi，low、medium和high，对应 120、160、240，如果没有特别设置，
 * 所有的dpi都会被算成这3个，具体可以参考下这个帖子
 * http://android.tgbus.com/Android/tutorial/201103/347176.shtml
 * 其中的default就是160。
 * <p>
 * 2. 计算 dp 与 px
 * 我们写布局的时候，肯定还是要知道1个dp到底有多少px的。
 * 换算公式如下： dp = （DPI/（160像素/英寸））* px = density px
 * 注意，这里都是带单位的。px是单位，dp是单位，density没单位。
 * 为了方便，假设dpi是240 像素/英寸 ， 那么density就是1.5
 * 那么就是 1dp=1.5px ，注意这是带了单位的，也就是 设备无关像素 = density 像素
 * 那么转换为数值计算的话，应该是下面这个式子
 * PX = density * DP
 * 也就是 　　像素值 = density * 设备无关像素值
 */
public class AdapterScreenUtils {

    //适配基准 宽度、高度
    public static final int MATCH_BASE_WIDTH = 0;
    public static final int MATCH_BASE_HEIGHT = 1;
    // 适配单位 dp  pt
    public static final int MATCH_UNIT_DP = 0;
    public static final int MATCH_UNIT_PT = 1;
    //屏幕信息
    private static MatchInfo matchInfo;

    //注册初始化信息
    public static void setup(@NonNull Context context) {

        final DisplayMetrics displayMetrics = getMetrics(context.getResources());
        if (displayMetrics == null) {
            return;
        }
        if (matchInfo == null) {
            matchInfo = new MatchInfo();
            matchInfo.screenWidth = displayMetrics.widthPixels;
            matchInfo.screenHeight = displayMetrics.heightPixels;
            matchInfo.appDensity = displayMetrics.density;
            matchInfo.appDensityDpi = displayMetrics.densityDpi;
            matchInfo.appScaledDensity = displayMetrics.scaledDensity;
            matchInfo.appXdpi = displayMetrics.xdpi;
        }

        // 字体改变
        context.registerComponentCallbacks(new ComponentCallbacks() {
            @Override
            public void onConfigurationChanged(Configuration newConfig) {
                // After the font changes, reassign the appscaleddensity
                if (newConfig != null && newConfig.fontScale > 0) {
                    matchInfo.appScaledDensity = displayMetrics.scaledDensity;
                }
            }

            @Override
            public void onLowMemory() {
            }
        });
    }

    /*
     *适配
     */
    public static void match(@NonNull Context context, float designSize, int matchBase, int matchUnit) {
        if (designSize == 0) {
            return;
        }
        if (matchUnit == MATCH_UNIT_PT) {
            matchbyPt(context, designSize, matchBase);
        } else {
            matchByDP(context, designSize, matchBase);
        }
    }

    /**
     * px = density * dp
     * density = dpi / 160
     * px = dp * (dpi / 160)
     */
    private static void matchByDP(@NonNull Context context, float designSize, int base) {
        matchByDP(matchInfo, getMetrics(context.getResources()), designSize, base);
    }

    private static void matchByDP(MatchInfo matchInfo, DisplayMetrics displayMetrics, float designSize, int base) {
        if (AdapterScreenUtils.matchInfo == null) {
            return;
        }
        final float targetDensity;
        if (base == MATCH_BASE_HEIGHT) {
            targetDensity = matchInfo.screenHeight * 1f / designSize;
        } else {
            targetDensity = matchInfo.screenWidth * 1f / designSize;
        }
        final int targetDensityDpi = (int) (targetDensity * 160);
        final float targetScaledDensity = targetDensity * (matchInfo.appScaledDensity / matchInfo.appDensity);
        displayMetrics.density = targetDensity;
        displayMetrics.densityDpi = targetDensityDpi;
        displayMetrics.scaledDensity = targetScaledDensity;
    }

    /**
     * px=pt * metrics.xdpi * (1.0f/72)
     */
    private static void matchbyPt(@NonNull final Context context, final float designSize, int base) {
        matchByPt(matchInfo, getMetrics(context.getResources()), designSize, base);
    }

    private static void matchByPt(final MatchInfo matchInfo, final DisplayMetrics displayMetrics, final float designSize, final int base) {

        if (matchInfo == null) {
            return;
        }
        if (base == MATCH_BASE_HEIGHT) {
            displayMetrics.xdpi = matchInfo.screenHeight * 72f / designSize;
        } else {
            displayMetrics.xdpi = matchInfo.screenWidth * 72f / designSize;
        }
    }

    public static void cancelMatch(@NonNull Context context, int matchUnit) {
        cancelMatch(matchUnit, getMetrics(context.getResources()), matchInfo);
    }

    private static void cancelMatch(final int matchUnit, final DisplayMetrics displayMetrics, final MatchInfo matchInfo) {
        if (matchInfo == null) {
            return;
        }
        if (matchUnit == MATCH_UNIT_DP) {
            if (matchInfo.appDensity != 0 && displayMetrics.density != matchInfo.appDensity) {
                displayMetrics.density = matchInfo.appDensity;
            }
            if (matchInfo.appDensityDpi != 0 && displayMetrics.densityDpi != matchInfo.appDensityDpi) {
                displayMetrics.densityDpi = (int) matchInfo.appDensityDpi;
            }
            if (matchInfo.appScaledDensity != 0 && displayMetrics.scaledDensity != matchInfo.appScaledDensity) {
                displayMetrics.scaledDensity = matchInfo.appScaledDensity;
            }
        } else if (matchUnit == MATCH_UNIT_PT) {
            if (matchInfo.appXdpi != 0 && displayMetrics.xdpi != matchInfo.appXdpi) {
                displayMetrics.xdpi = matchInfo.appXdpi;
            }
        }
    }

    /**
     * 获取屏幕对象
     *
     * @param resources
     * @return
     */
    private static DisplayMetrics getMetrics(Resources resources) {
        DisplayMetrics metricsOnMiui = null;
        if ("MiuiResources".equals(resources.getClass().getSimpleName()) || "XResources".equals(resources.getClass().getSimpleName())) {
            try {
                Field field = Resources.class.getDeclaredField("mTmpMetrics");
                field.setAccessible(true);
                metricsOnMiui = (DisplayMetrics) field.get(resources);
            } catch (Exception e) {
                return null;
            }
        }
        if (metricsOnMiui == null) {
            return resources.getDisplayMetrics();
        }
        return metricsOnMiui;
    }

    /**
     * 屏幕信息
     */
    public static class MatchInfo {
        //宽度
        public int screenWidth;
        //高度
        public int screenHeight;
        //密度 基准160  1 1.5 2  1dp=1px  1dp=1.5px
        public float appDensity;
        //指每英寸中的像素数 120 160 240 320 480   4：3：2：1.5：1
        public float appDensityDpi;
        //字体缩放比例
        public float appScaledDensity;
        //水平方向上1inch实际上容纳的点的数量
        public float appXdpi;
    }
}
