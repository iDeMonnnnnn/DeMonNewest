package com.demon.base.mvvm

import android.content.Context
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.demon.base.R
import com.demon.base.utils.LoadingUtils
import com.demon.base.utils.getTClass
import com.demon.base.utils.inflateViewBinding

abstract class MvvmActivity<VB : ViewBinding, VM : BaseViewModel> : AppCompatActivity() {
    protected val TAG = this.javaClass.simpleName
    protected lateinit var mContext: Context
    protected lateinit var binding: VB
    protected val mViewModel by lazy { ViewModelProvider(this).get(getTClass<VM>(1)) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        runCatching {
            binding = inflateViewBinding(layoutInflater)
            setContentView(binding.root)
            mContext = this
            mViewModel.run {
                lifecycle.addObserver(this)
                errLiveData.observe(this@MvvmActivity) {
                    doOnError(it)
                }
                loadingData.observe(this@MvvmActivity) {
                    LoadingUtils.show(mContext, it)
                }
            }
            initData()
        }.onFailure {
            it.printStackTrace()
        }
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

    protected fun vmRun(block: VM.() -> Unit) {
        mViewModel.run(block)
    }

    protected fun bindingRun(block: VB.() -> Unit) {
        binding.run(block)
    }


    protected abstract fun initData()


    open fun doOnError(msg: String) {}


    override fun onDestroy() {
        super.onDestroy()
        lifecycle.removeObserver(mViewModel)
    }
}
