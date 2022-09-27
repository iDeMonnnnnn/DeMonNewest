package com.demon.base.mvvm

import android.util.Log
import androidx.annotation.StringRes
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.demon.base.R
import com.demon.base.utils.ext.getTClassIndex
import com.demon.base.utils.ext.inflateViewBinding

abstract class MvvmActivity<VB : ViewBinding, VM : BaseViewModel> : BaseVMActivity<VM>() {

    protected lateinit var binding: VB

    override fun providerVM(): VM = ViewModelProvider(this)[getTClassIndex(1)]

    override fun initContentView() {
        binding = inflateViewBinding(layoutInflater, 0)
        setContentView(binding.root)
    }

    fun setToolbar(@StringRes id: Int) {
        setToolbar(getString(id))
    }

    open fun setToolbar(title: String) {
        findViewById<Toolbar>(R.id.toolbar)?.run {
            setTitle(title)
            setNavigationOnClickListener {
                finish()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        Log.i(TAG, "onResume: ")
    }

    override fun onPause() {
        super.onPause()
        Log.i(TAG, "onPause: ")
    }

    override fun onStop() {
        super.onStop()
        Log.i(TAG, "onStop: ")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "onDestroy: ")
        lifecycle.removeObserver(mViewModel)
    }

    protected fun bindingRun(block: VB.() -> Unit) {
        binding.run(block)
    }


}
