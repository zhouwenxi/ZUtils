package com.qishui.commontoolslibrary.core;

import android.os.Handler;
import android.os.Looper;

/**
 * Created by zhou on 2018/12/22.
 * 计时器功能
 */

public class TimerUtils {

    //单位
    public static final int UNIT = 1000;
    //结束时间
    private int endTime;
    //计时器累计时间
    private int tempTime;
    //计时间隔
    private int interval;
    private int type1 = 1;
    private int type2 = 2;
    private int type = type1;

    private TimerUtils() {
    }

    /**
     * 开启一个倒计时
     *
     * @return
     */
    public static TimerUtils with() {
        return new TimerUtils();
    }

    private Handler handler = new Handler(Looper.getMainLooper());
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            handler.postDelayed(this, interval);
            if (callBack == null) {
                return;
            }

            //倒计时
            if (type == type1) {
                endTime = endTime - interval;
                if (endTime >= 0) {
                    callBack.updateUI(String.valueOf(endTime / UNIT));
                } else {
                    cancel();
                    callBack.endUI();
                }
            }

            //计时器
            if (type == type2) {
                tempTime = tempTime + interval;
                if (endTime == 0) {
                    callBack.updateUI(getTime(tempTime / UNIT));
                } else {
                    if (tempTime <= endTime) {
                        callBack.updateUI(getTime(tempTime / UNIT));
                    } else {
                        cancel();
                        callBack.endUI();
                    }
                }
            }

        }
    };

    /**
     * 开始倒计时计时
     */
    public void countdownStart(int endTime, int interval) {
        type = type1;
        this.endTime = (endTime + interval) * UNIT;
        this.interval = interval * UNIT;
        handler.removeCallbacks(runnable);
        handler.postDelayed(runnable, this.interval);
    }


    /**
     * 开始倒计时计时
     */
    public void strat(int interval) {
        type = type2;
        this.tempTime = 0;
        this.interval = interval * UNIT;
        handler.removeCallbacks(runnable);
        handler.postDelayed(runnable, this.interval);
    }

    /**
     * 设置结束条件
     *
     * @param endTime
     */
    public TimerUtils setEndTime(int endTime) {
        this.endTime = endTime * UNIT;
        return this;
    }

    /**
     * 停止计时
     */
    public void cancel() {
        handler.removeCallbacks(runnable);
    }


    /**
     * 获取格式化时间
     *
     * @param time
     * @return
     */
    public String getTime(int time) {

        String value;

        if (time < 60) {
            if (time < 10) {
                value = "00:00:0" + time;
            } else {
                value = "00:00:" + time;
            }

        } else if (time < 3600) {

            int min = time / 60;
            int sec = time % 60;

            if (min < 10) {
                if (sec < 10) {
                    value = "00:0" + min + ":0" + sec;

                } else {
                    value = "00:0" + min + ":" + sec;

                }
            } else {
                if (sec < 10) {
                    value = "00:" + min + ":0" + sec;

                } else {
                    value = "00:" + min + ":" + sec;
                }
            }
        } else {

            int hour = time / 3600;
            int min = time % 3600 / 60;
            int sec = time % 60;

            if (hour < 10) {
                if (min < 10) {
                    if (sec < 10) {
                        value = "0" + hour + ":0" + min + ":0" + sec;

                    } else {
                        value = "0" + hour + ":0" + min + ":" + sec;

                    }
                } else {
                    if (sec < 10) {
                        value = "0" + hour + ":" + min + ":0" + sec;

                    } else {
                        value = "0" + hour + ":" + min + ":" + sec;

                    }
                }
            } else {
                if (min < 10) {
                    if (sec < 10) {
                        value = hour + ":0" + min + ":0" + sec;

                    } else {
                        value = hour + ":0" + min + ":" + sec;

                    }
                } else {
                    if (sec < 10) {
                        value = hour + ":" + min + ":0" + sec;

                    } else {
                        value = hour + ":" + min + ":" + sec;

                    }
                }
            }
        }

        return value;
    }

    /**
     * 计时器更新界面
     */

    private CallBack callBack;

    public TimerUtils setCallBack(CallBack callBack) {
        this.callBack = callBack;
        return this;
    }

    public interface CallBack {

        void updateUI(String time);

        void endUI();
    }
}
