package com.msy.rrodemo.di.scope;

import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by Administrator on 2019/9/27/027.
 */

@Scope
@Retention(RUNTIME)
public @interface FragmentScope {
}

