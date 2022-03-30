package com.demon.demonjetpack.module.room

import com.alibaba.android.arouter.facade.annotation.Route
import com.demon.base.mvvm.MvvmActivity
import com.demon.base.utils.ext.toastDigital
import com.demon.base.utils.ext.toastEmpty
import com.demon.demonjetpack.base.data.RouterConst
import com.demon.demonjetpack.databinding.ActivityRoomBinding
import dagger.hilt.android.AndroidEntryPoint

@Route(path = RouterConst.ACT_ROOM)
@AndroidEntryPoint
class RoomActivity : MvvmActivity<ActivityRoomBinding, RoomViewModel>() {


    override fun initData() {
        setToolbar("Room")
        binding.btnInsert.setOnClickListener {
            val str = binding.etInsert.text.toString().trim()
            if (!str.toastEmpty())
                mViewModel.insertUser(str)
        }

        binding.btnDel.setOnClickListener {
            val str = binding.etDel.text.toString().trim()
            if (!str.toastDigital())
                mViewModel.deleteUser(str.toInt())
        }


        binding.btnUpdate.setOnClickListener {
            val strId = binding.etUpdateId.text.toString().trim()
            val str = binding.etUpdate.text.toString().trim()
            if (!strId.toastDigital() && !str.toastEmpty())
                mViewModel.updateUser(strId.toInt(), str)
        }

        vmRun {
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
