package com.demon.easyjetpack.module.room

import androidx.lifecycle.observe
import com.alibaba.android.arouter.facade.annotation.Route
import com.demon.basemvvm.mvvm.MvvmActivity
import com.demon.easyjetpack.R
import com.demon.easyjetpack.base.data.RouterConst
import com.demon.easyjetpack.base.ext.toastDigital
import com.demon.easyjetpack.base.ext.toastEmpty
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_room.*

@Route(path = RouterConst.ACT_ROOM)
@AndroidEntryPoint
class RoomActivity : MvvmActivity<RoomViewModel>() {

    override fun setupLayoutId(): Int = R.layout.activity_room

    override fun init() {

        btnInsert.setOnClickListener {
            val str = etInsert.text.toString().trim()
            if (!str.toastEmpty(this))
                mViewModel.insertUser(str)
        }


        btnDel.setOnClickListener {
            val str = etDel.text.toString().trim()
            if (!str.toastDigital(this))
                mViewModel.deleteUser(str.toInt())
        }


        btnUpdate.setOnClickListener {
            val strId = etUpdateId.text.toString().trim()
            val str = etUpdate.text.toString().trim()
            if (!strId.toastDigital(this) && !str.toastEmpty(this))
                mViewModel.updateUser(strId.toInt(), str)
        }
    }

    override fun initViewModel() {
        mViewModel.run {
            getUsers()
            useData.observe(this@RoomActivity) {
                tvQuery.text = ""
                it.forEach { user ->
                    tvQuery.append(user.toString() + "\n")
                }
            }

        }
    }
}
