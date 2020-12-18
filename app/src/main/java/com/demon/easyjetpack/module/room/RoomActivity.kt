package com.demon.easyjetpack.module.room

import androidx.lifecycle.observe
import com.alibaba.android.arouter.facade.annotation.Route
import com.demon.basemvvm.mvvm.MvvmVBActivity
import com.demon.easyjetpack.R
import com.demon.easyjetpack.base.data.RouterConst
import com.demon.easyjetpack.base.ext.toastDigital
import com.demon.easyjetpack.base.ext.toastEmpty
import com.demon.easyjetpack.databinding.ActivityRoomBinding
import dagger.hilt.android.AndroidEntryPoint

@Route(path = RouterConst.ACT_ROOM)
@AndroidEntryPoint
class RoomActivity : MvvmVBActivity<ActivityRoomBinding, RoomViewModel>() {

    override fun setupLayoutId(): Int = R.layout.activity_room

    override fun init() {
        binding.btnInsert.setOnClickListener {
            val str = binding.etInsert.text.toString().trim()
            if (!str.toastEmpty(this))
                mViewModel.insertUser(str)
        }


        binding.btnDel.setOnClickListener {
            val str = binding.etDel.text.toString().trim()
            if (!str.toastDigital(this))
                mViewModel.deleteUser(str.toInt())
        }


        binding.btnUpdate.setOnClickListener {
            val strId = binding.etUpdateId.text.toString().trim()
            val str = binding.etUpdate.text.toString().trim()
            if (!strId.toastDigital(this) && !str.toastEmpty(this))
                mViewModel.updateUser(strId.toInt(), str)
        }
    }

    override fun initViewModel() {
        mViewModel.run {
            getUsers()
            useData.observe(this@RoomActivity) {
                binding.tvQuery.text = ""
                it.forEach { user ->
                    binding.tvQuery.append(user.toString() + "\n")
                }
            }

        }
    }
}
