package com.demon.easyjetpack.dagger

import com.demon.basemvvm.dagger.FragmentScoped
import com.demon.easyjetpack.module.fragment.TabFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * @author DeMon
 * Created on 2020/3/16.
 * E-mail 757454343@qq.com
 * Desc:
 */
@Module
abstract class FragmentActivityBindModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract fun tabFragment(): TabFragment
}