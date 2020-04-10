package com.demon.easyjetpack.dagger

import androidx.lifecycle.ViewModel
import com.demon.basemvvm.dagger.ActivityScoped
import com.demon.basemvvm.dagger.ViewModelKey
import com.demon.easyjetpack.module.dagger.DaggerTestActivity
import com.demon.easyjetpack.module.fragment.FragmentActivity
import com.demon.easyjetpack.module.fragment.FragmentViewModel
import com.demon.easyjetpack.module.main.MainActivity
import com.demon.easyjetpack.module.main.MainViewModel
import com.demon.easyjetpack.module.room.RoomActivity
import com.demon.easyjetpack.module.room.RoomViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

/**
 * @author DeMon
 * Created on 2020/1/13.
 * E-mail 757454343@qq.com
 * Desc: Activity,ViewModel注入
 */
@Module
abstract class ActivityBindModule {
    @ActivityScoped
    @ContributesAndroidInjector
    abstract fun mainActivity(): MainActivity

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(viewModel: MainViewModel): ViewModel


    @ActivityScoped
    @ContributesAndroidInjector
    abstract fun daggerTestActivity(): DaggerTestActivity


    @ActivityScoped
    @ContributesAndroidInjector
    abstract fun roomActivity(): RoomActivity

    @Binds
    @IntoMap
    @ViewModelKey(RoomViewModel::class)
    abstract fun bindRoomViewModel(viewModel: RoomViewModel): ViewModel

    @ActivityScoped
    @ContributesAndroidInjector(modules = [FragmentActivityBindModule::class])
    abstract fun fragmentActivity(): FragmentActivity

    @Binds
    @IntoMap
    @ViewModelKey(FragmentViewModel::class)
    abstract fun bindFragmentViewModel(viewModel: FragmentViewModel): ViewModel

}