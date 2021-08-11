package com.demon.demonjetpack.module.views

import android.graphics.BitmapFactory
import com.demon.basemvvm.mvvm.BaseViewModel
import com.demon.basemvvm.mvvm.MvvmFragment
import com.demon.demonjetpack.base.ext.toast
import com.demon.demonjetpack.base.util.getExternalOrFilesDirPath
import com.demon.demonjetpack.base.util.saveBitmapImg
import com.demon.demonjetpack.databinding.FragmentDoodleBinding
import com.demon.demonjetpack.module.views.widget.img.IMGMode

/**
 * @author DeMon
 * Created on 2021/8/4.
 * E-mail 757454343@qq.com
 * Desc:
 */
class DoodleFragment : MvvmFragment<FragmentDoodleBinding, BaseViewModel>() {

    override fun init() {
        binding.run {
            imgView.setImageBitmap(BitmapFactory.decodeFile(requireContext().getExternalOrFilesDirPath(null) + "/abc.jpg"))
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
                val saveFile = imgView.saveBitmap().saveBitmapImg(requireContext())
                if (saveFile?.exists() == true) {
                    "保存成功!".toast()
                } else {
                    "保存失败!".toast()
                }
            }
        }
    }
}