package com.demon.base.mvvm

import android.content.Context
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.demon.base.R
import com.demon.base.utils.ext.inflateViewBinding

/**
 * @author DeMonnnnnn
 * date 2022/9/23
 * email liu_demon@qq.com
 * desc ViewBinding Activity基类
 */
abstract class BaseVBActivity<VB : ViewBinding> : AppCompatActivity() {

    protected val TAG = this.javaClass.simpleName

    protected lateinit var mContext: Context

    protected lateinit var binding: VB


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this
        binding = inflateViewBinding(layoutInflater)
        setContentView(binding.root)
        initFun()
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

    protected open fun initFun(){
        setupData()
    }

    protected abstract fun setupData()

    protected fun bindingRun(block: VB.() -> Unit) {
        binding.run(block)
    }

}