package com.misy.photopicker.listener;

import java.util.Map;

/**
 * Created by Administrator on 2020/1/9/009.
 */

public interface OnItemCheckChangeListener{
    void onItemCheckChanged(Map<Integer, Boolean> checkStatusMap, int checkNum);
}
