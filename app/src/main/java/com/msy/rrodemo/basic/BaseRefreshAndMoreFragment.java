package com.msy.rrodemo.basic;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.msy.rrodemo.widget.CustomLoadMoreView;

/**
 * Created by Administrator on 2019/12/24/024.
 */

public abstract class BaseRefreshAndMoreFragment<T extends RxPresenter, K, H extends BaseQuickAdapter> extends BaseRefreshFragment<T, K>
        implements BaseQuickAdapter.RequestLoadMoreListener {

    protected H mAdapter;
    protected int mPage = 1;
    protected boolean isLoadMore = false;

    protected void setAdapter(H adapter){
        this.mAdapter = adapter;
    }

    @Override
    protected void initRecyclerView() {
        super.initRecyclerView();
        mAdapter.setLoadMoreView(new CustomLoadMoreView());
        mAdapter.setOnLoadMoreListener(this, recycler);
    }

    @Override
    public void onLoadMoreRequested() {
        isLoadMore = true;
        recycler.postDelayed(()->{
            mPage++;
            loadData();
        }, 650);
    }

    @Override
    protected void clearData() {
        super.clearData();
        mPage = 1;
        isLoadMore = false;
        mAdapter.setEnableLoadMore(false);
    }

    @Override
    public void complete() {
        super.complete();
        mAdapter.setEnableLoadMore(true);
    }
}
