package com.demon.demonnewest.module.compose

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.demon.base.mvvm.BaseVMActivity
import com.demon.base.mvvm.BaseViewModel
import com.demon.demonnewest.module.compose.ui.theme.DeMonNewestTheme

/**
 * @author DeMonnnnnn
 * date 2022/9/23
 * email liu_demon@qq.com
 * desc
 */
abstract class BaseComposeActivity<VM : BaseViewModel> : BaseVMActivity<VM>() {

    /**
     * 标题栏标题，是否为null决定是否显示TopAppBar
     */
    protected open fun appBarText(): String? = null

    /**
     * Compose通用主题，Surface部分
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DeMonNewestTheme {
                Surface {
                    if (!appBarText().toBoolean()) {
                        initViewWithAppBar()
                    } else {
                        setupView()
                    }
                }
            }
        }
    }

    /**
     * Compose通用标题栏部分
     */
    @Composable
    fun initViewWithAppBar() {
        Column {
            TopAppBar(navigationIcon = {
                Image(painter = painterResource(android.R.drawable.btn_plus), contentDescription = "back", modifier = Modifier
                    .clickable {
                        finish()
                    }
                    .padding(16.dp))
            }, title = {
                Text(text = appBarText() ?: "")
            })
            setupView()
        }
    }

    /**
     * Compose自定义View部分
     */
    @Composable
    abstract fun setupView()
}