package com.demon.demonjetpack.module.views

import android.view.View
import com.demon.basemvvm.mvvm.BaseViewModel
import com.demon.basemvvm.mvvm.MvvmActivity
import com.demon.demonjetpack.databinding.ActivityVbBinding
import com.demon.demonjetpack.databinding.MergeLayoutBinding
import com.demon.demonjetpack.databinding.ViewstubLayoutBinding

/**
 * @author DeMon
 * Created on 2021/6/29.
 * E-mail 757454343@qq.com
 * Desc:
 */
class VbActivity : MvvmActivity<ActivityVbBinding, BaseViewModel>() {
    private var inflateView: View? = null

    override fun initData() {
        setToolbar("ViewBinding")
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