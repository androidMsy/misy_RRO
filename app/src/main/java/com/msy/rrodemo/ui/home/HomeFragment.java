package com.msy.rrodemo.ui.home;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.msy.rrodemo.R;
import com.msy.rrodemo.basic.BaseFragment;
import com.msy.rrodemo.basic.RxPresenter;
import com.msy.rrodemo.entity.MainBean;
import com.msy.rrodemo.entity.UserBean;
import com.msy.rrodemo.presenter.HomePresenter;
import com.msy.rrodemo.ui.ChatActivity;
import com.msy.rrodemo.view.HomeView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2019/12/19/019.
 */

public class HomeFragment extends BaseFragment<HomePresenter> implements HomeView.View,View.OnClickListener {

    @BindView(R.id.tv_content)
    TextView contentTv;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }


    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void loadData() {
        mPresenter.getData();
    }

    @Override
    public void setData(MainBean bean) {
        String str = "";
        for (UserBean user : bean.getList()){
            str += user.getUserId() + "----" + user.getUsername() + "----" + user.getRealname() + "---"
                    + user.getHeaderUrl() + "----" + user.getIntro() + "\n";
        }
        contentTv.setText(str);
    }

    @OnClick({R.id.menu_img})
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.menu_img:
                startActivity(new Intent(getActivity(), ChatActivity.class));
                break;
            default:

                break;
        }
    }
}
