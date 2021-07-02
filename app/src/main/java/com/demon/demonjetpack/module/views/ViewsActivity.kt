package com.demon.demonjetpack.module.views

import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.demon.basemvvm.mvvm.BaseViewModel
import com.demon.basemvvm.mvvm.MvvmActivity
import com.demon.demonjetpack.R
import com.demon.demonjetpack.databinding.AcvtivtyViewsBinding

class ViewsActivity : MvvmActivity<AcvtivtyViewsBinding, BaseViewModel>() {


    override fun init() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        binding.includeToolbar.toolbar.setupWithNavController(navController)
    }
}