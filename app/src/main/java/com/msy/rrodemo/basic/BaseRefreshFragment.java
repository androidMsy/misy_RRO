package com.msy.rrodemo.basic;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.msy.rrodemo.R;
import com.msy.rrodemo.widget.MultipleStatusView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2019/12/20/020.
 */

public abstract class BaseRefreshFragment<T extends RxPresenter, K> extends BaseFragment<T> implements SwipeRefreshLayout.OnRefreshListener{

    @BindView(R.id.refresh)
    protected SwipeRefreshLayout mRefreshLayout;
    @BindView(R.id.recycler)
    protected RecyclerView recycler;
    @BindView(R.id.multipleStatusView)
    protected MultipleStatusView statusView;

    protected boolean isRefresh = false;
    protected List<K> mList = new ArrayList<>();

    @Override
    protected void initWidget() {
        initRefreshListner();
    }

    @Override
    public void onRefresh() {
        clearData();
        loadData();
    }
    private void initRefreshListner(){
        mRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        mRefreshLayout.setOnRefreshListener(this);
    }

    protected void clearData(){
        isRefresh = true;
    }

    @Override
    public void complete() {
        super.complete();
        statusView.showContent();
        mRefreshLayout.postDelayed(()-> {
            if (null != mRefreshLayout){
                mRefreshLayout.setRefreshing(false);
            }
        },500);
        if (isRefresh){
            if (null != mList)mList.clear();
            clear();
        }
        isRefresh = false;

    }
    protected void clear(){

    }

    @Override
    public void showError(String s) {
        super.showError(s);
        if (s.equals("网络连接异常")){
            statusView.showNoNetwork();
        }else {
            statusView.showError();
        }
    }
}
