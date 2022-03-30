package com.demon.demonnewest.module.views

import android.graphics.BitmapFactory
import com.demon.base.mvvm.BaseViewModel
import com.demon.base.mvvm.MvvmActivity
import com.demon.base.utils.ext.saveBitmapImg
import com.demon.base.utils.ext.toast
import com.demon.demonnewest.R
import com.demon.demonnewest.databinding.ActivityDoodleBinding
import com.demon.demonnewest.module.views.widget.imaging.IMGMode
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