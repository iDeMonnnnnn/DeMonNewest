package com.demon.easyjetpack.module.dagger

import com.alibaba.android.arouter.facade.annotation.Route
import com.demon.basemvvm.mvvm.BaseViewModel
import com.demon.basemvvm.mvvm.MvvmActivity
import com.demon.easyjetpack.R
import com.demon.easyjetpack.data.Constants
import com.demon.easyjetpack.data.RouterConst
import com.jeremyliao.liveeventbus.LiveEventBus
import kotlinx.android.synthetic.main.activity_dagger_test.*
import javax.inject.Inject

@Route(path = RouterConst.ACT_DAGGER)
class DaggerTestActivity : MvvmActivity<BaseViewModel>() {


    @Inject
    protected lateinit var helper: DoHelper

    override fun setupLayoutId(): Int = R.layout.activity_dagger_test

    override fun init() {

        name.text = helper.name

        btn.setOnClickListener {
            time.text = "${helper.getNowTime()}"
        }

        btn1.setOnClickListener {
            ha.text = helper.getHa()
        }

        btn2.setOnClickListener {
            provides.text = helper.getGson()
        }

        btn3.setOnClickListener {
            LiveEventBus.get(Constants.EVENT_BUS).post("Hello LiveEventBus!")
        }

    }

    override fun initViewModel() {

    }
}
