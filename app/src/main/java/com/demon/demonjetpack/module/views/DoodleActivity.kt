package com.demon.demonjetpack.module.views

import android.graphics.BitmapFactory
import com.demon.basemvvm.mvvm.BaseViewModel
import com.demon.basemvvm.mvvm.MvvmActivity
import com.demon.demonjetpack.R
import com.demon.demonjetpack.base.ext.toast
import com.demon.demonjetpack.base.util.saveBitmapImg
import com.demon.demonjetpack.databinding.ActivityDoodleBinding
import com.demon.demonjetpack.module.views.widget.imaging.IMGMode
import com.gyf.immersionbar.ktx.immersionBar

/**
 * @author DeMon
 * Created on 2021/8/4.
 * E-mail 757454343@qq.com
 * Desc:
 */
class DoodleActivity : MvvmActivity<ActivityDoodleBinding, BaseViewModel>() {

    override fun initData() {
        immersionBar {

        }
        binding.run {
            imgView.setImageBitmap(BitmapFactory.decodeResource(resources, R.drawable.img1))
            btnMosaic.setOnClickListener {
                imgView.mode = IMGMode.MOSAIC
            }
            btnDoodle.setOnClickListener {
                imgView.mode = IMGMode.DOODLE
            }
            btnUndo.setOnClickListener {
                imgView.undo()
            }
            btnCancel.setOnClickListener {
                imgView.reset()
                imgView.mode = IMGMode.NONE
            }
            btnSave.setOnClickListener {
                val saveFile = imgView.saveBitmap().saveBitmapImg(this@DoodleActivity)
                if (saveFile?.exists() == true) {
                    "保存成功!".toast()
                } else {
                    "保存失败!".toast()
                }
            }
        }
    }
}