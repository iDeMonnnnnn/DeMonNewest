package com.demon.demonnewest.module.fragment

import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.demon.base.mvvm.BaseVBFragment
import com.demon.base.mvvm.BaseViewModel
import com.demon.base.mvvm.MvvmFragment
import com.demon.base.utils.ext.showFragment
import com.demon.demonnewest.R
import com.demon.demonnewest.databinding.FragmentTranslationBinding
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import reactivecircus.flowbinding.material.TabLayoutSelectionEvent
import reactivecircus.flowbinding.material.tabSelectionEvents

/**
 * @author DeMonnnnnn
 * date 2022/8/5
 * email liu_demon@qq.com
 * desc
 */
class ShowFragment : BaseVBFragment<FragmentTranslationBinding>() {

    private val oneFragment by lazy {
        GoFragment("ShowFragment-One")
    }

    private val twoFragment by lazy {
        GoFragment("ShowFragment-Two")
    }

    override fun initLazyData() {

        bindingRun {
            tabLayout.tabSelectionEvents().onEach {
                if (it is TabLayoutSelectionEvent.TabSelected) {
                    showFragment(
                        R.id.frameLayout, if (it.tab.position == 0) {
                            oneFragment
                        } else {
                            twoFragment
                        }
                    )
                }

            }.launchIn(lifecycleScope)
        }
    }
}