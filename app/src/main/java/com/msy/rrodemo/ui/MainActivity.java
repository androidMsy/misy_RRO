package com.msy.rrodemo.ui;

import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.RadioButton;

import com.msy.rrodemo.basic.BaseActivity;
import com.msy.rrodemo.R;
import com.msy.rrodemo.ui.home.HomeFragment;
import com.msy.rrodemo.ui.mine.MineFragment;
import com.msy.rrodemo.ui.contacts.ContactsFragment;
import com.msy.rrodemo.ui.product.ProductFragment;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by Administrator on 2019/9/26/026.
 */

public class MainActivity extends BaseActivity implements View.OnClickListener{

    @BindView(R.id.home_btn)
    RadioButton homeBtn;

    private HomeFragment homeFragment;
    private ProductFragment productFragment;
    private MineFragment mineFragment;
    private ContactsFragment contactsFragment;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initWidget(){
        homeBtn.setChecked(true);
        homeFragment = new HomeFragment();
        productFragment = new ProductFragment();
        contactsFragment = new ContactsFragment();
        mineFragment = new MineFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.container, homeFragment);
        ft.commit();
    }

    @Override
    public void initToolbar(){
    }

    @Override
    public void initListner(){

    }

    @Override
    protected void loadData() {

    }

    @OnClick({R.id.home_btn,R.id.product_btn,R.id.contacts_btn,R.id.mine_btn})
    @Override
    public void onClick(View v) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        switch (v.getId()){
            case R.id.home_btn:
                switchFragment(0, ft);
                break;
            case R.id.product_btn:
                switchFragment(1, ft);
                break;
            case R.id.contacts_btn:
                switchFragment(2, ft);
                break;
            case R.id.mine_btn:
                switchFragment(3, ft);
                break;
        }
        ft.commit();
    }

    private void switchFragment(int index, FragmentTransaction ft){
        switch (index){
            case 0:
                ft.show(homeFragment);
                if (productFragment.isAdded())ft.hide(productFragment);
                if (contactsFragment.isAdded())ft.hide(contactsFragment);
                if (mineFragment.isAdded())ft.hide(mineFragment);
                break;
            case 1:
                ft.hide(homeFragment);
                if (productFragment.isAdded()){
                    ft.show(productFragment);
                }else {
                    ft.add(R.id.container, productFragment);
                }
                if (contactsFragment.isAdded())ft.hide(contactsFragment);
                if (mineFragment.isAdded())ft.hide(mineFragment);
                break;
            case 2:
                ft.hide(homeFragment);
                if (productFragment.isAdded())ft.hide(productFragment);
                if (contactsFragment.isAdded()){
                    ft.show(contactsFragment);
                }else {
                    ft.add(R.id.container, contactsFragment);
                }
                if (mineFragment.isAdded())ft.hide(mineFragment);
                break;
            case 3:
                ft.hide(homeFragment);
                if (productFragment.isAdded())ft.hide(productFragment);
                if (contactsFragment.isAdded())ft.hide(contactsFragment);
                if (mineFragment.isAdded()){
                    ft.show(mineFragment);
                }else {
                    ft.add(R.id.container, mineFragment);
                }
                break;
        }
    }
}
