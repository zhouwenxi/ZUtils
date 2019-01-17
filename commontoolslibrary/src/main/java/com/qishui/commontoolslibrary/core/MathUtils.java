package com.qishui.commontoolslibrary.core;

/**
 * Created by zhou on 2018/12/22.
 */

public class MathUtils {

    /**
     *
     * @param value
     * @param min
     * @param max
     * @return
     */
    public int getMin_Max_Vaule(int value,int min,int max){

        if(value<=min){
            return min;
        }
        if(value>=max){
            return max;
        }
        return value;
    }

}
