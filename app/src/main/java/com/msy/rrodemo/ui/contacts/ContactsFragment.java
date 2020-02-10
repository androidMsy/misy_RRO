package com.msy.rrodemo.ui.contacts;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.msy.rrodemo.R;
import com.msy.rrodemo.adapter.ContactsAdapter;

import com.msy.rrodemo.basic.BaseRefreshAndMoreFragment;
import com.msy.rrodemo.entity.BaseCollection;
import com.msy.rrodemo.entity.UserBean;
import com.msy.rrodemo.presenter.ContactsPresenter;
import com.msy.rrodemo.ui.ChatActivity;
import com.msy.rrodemo.utils.Toast;
import com.msy.rrodemo.view.ContactsView;


import butterknife.BindView;

/**
 * Created by Administrator on 2019/12/19/019.
 */

public class ContactsFragment extends BaseRefreshAndMoreFragment<ContactsPresenter, UserBean, BaseQuickAdapter> implements ContactsView.View {

    @BindView(R.id.toolbar)
    Toolbar toolbar;


    @Override
    public int getLayoutId() {
        return R.layout.fragment_contacts;
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void initToolbar() {
        toolbar.setTitle("会话");
    }

    @Override
    protected void initRecyclerView() {
        mAdapter = new ContactsAdapter(mList);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        recycler.setLayoutManager(manager);
        recycler.setAdapter(mAdapter);
        setAdapter(mAdapter);
        super.initRecyclerView();
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            judgeLoginStatus();
            if (getUserId().equals(mList.get(position).getUserId())){
                Toast.show(getActivity(), "不能与自己聊天");
                return;
            }
            Intent intent = new Intent(getActivity(), ChatActivity.class);
            intent.putExtra("targetUserId", mList.get(position).getUserId());
            startActivity(intent);
        });
    }

    @Override
    protected void loadData() {
        if (!isRefresh && !isLoadMore)statusView.showLoading();
        mPresenter.getAllContacts(mPage);
    }

    @Override
    public void setData(BaseCollection<UserBean> list) {
        if (!isLoadMore){
            mList = list.getList();
            mAdapter.setNewData(list.getList());
        }else {
            mList.addAll(list.getList());
            if (!list.isHasNextPage()){
                mAdapter.addData(list.getList());
                mAdapter.loadMoreEnd();
            }else {
                mAdapter.addData(list.getList());
                mAdapter.loadMoreComplete();
            }
        }

    }
}
