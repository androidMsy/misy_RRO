package com.msy.rrodemo.net.scheduler;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2019/9/29/029.
 */

public class IoMainScheduler<T> extends BaseScheduler<T> {
    public IoMainScheduler() {
        super(Schedulers.io(), AndroidSchedulers.mainThread());
    }
}
