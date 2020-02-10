package com.msy.rrodemo.di.component;

import android.app.Activity;

import com.msy.rrodemo.di.module.FragmentModule;
import com.msy.rrodemo.di.scope.FragmentScope;
import com.msy.rrodemo.ui.contacts.ContactsFragment;
import com.msy.rrodemo.ui.home.HomeFragment;
import com.msy.rrodemo.ui.mine.MineFragment;

import dagger.Component;

/**
 * Created by Administrator on 2019/12/19/019.
 */

@FragmentScope
@Component(dependencies = AppComponent.class, modules = FragmentModule.class)
public interface FragmentComponent {
    Activity getActivity();

    void inject(HomeFragment homeFragment);

    void inject(ContactsFragment contactsFragment);

    void inject(MineFragment mineFragment);
}
