package com.demon.demonjetpack.module.viewbinding

import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.demon.basemvvm.mvvm.BaseViewModel
import com.demon.basemvvm.mvvm.MvvmActivity
import com.demon.demonjetpack.base.data.RouterConst
import com.demon.demonjetpack.databinding.ActivityViewBindingBinding
import com.demon.demonjetpack.databinding.MergeLayoutBinding
import com.demon.demonjetpack.databinding.ViewstubLayoutBinding

/**
 * @author DeMon
 * Created on 2020/1/14.
 * E-mail 757454343@qq.com
 * Desc: https://www.jianshu.com/p/651dbe37d805
 */
@Route(path = RouterConst.ACT_VIEWBINDING)
class ViewBindingActivity : MvvmActivity<ActivityViewBindingBinding, BaseViewModel>() {

    private var inflateView: View? = null

    override fun init() {
        binding.btnDialog.setOnClickListener {

        }

        binding.includeLayout.tvInclude.text = "Include"

        val mergeBinding = MergeLayoutBinding.bind(binding.root)
        mergeBinding.tvMerge.text = "Merge"

        if (inflateView == null) {
            inflateView = binding.viewStub.inflate()
        }

        val stubBinding = ViewstubLayoutBinding.bind(binding.root)
        stubBinding.tvStub.text = "ViewStub"


    }

}