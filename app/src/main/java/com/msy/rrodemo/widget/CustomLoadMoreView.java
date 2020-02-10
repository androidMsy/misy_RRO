package com.msy.rrodemo.widget;

import com.chad.library.adapter.base.loadmore.LoadMoreView;
import com.msy.rrodemo.R;

public class CustomLoadMoreView extends LoadMoreView {
    @Override
    public int getLayoutId() {
        return R.layout.layout_load_more;
    }

    @Override
    protected int getLoadingViewId() {
        return R.id.ll_loading;
    }

    @Override
    protected int getLoadFailViewId() {
        return R.id.ll_load_fail;
    }

    @Override
    protected int getLoadEndViewId() {
        return R.id.ll_load_end;
    }
}
