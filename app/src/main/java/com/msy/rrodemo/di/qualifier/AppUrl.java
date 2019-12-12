package com.msy.rrodemo.di.qualifier;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;

import javax.inject.Qualifier;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by Administrator on 2019/9/27/027.
 */

@Qualifier
@Documented
@Retention(RUNTIME)
public @interface AppUrl {
}
