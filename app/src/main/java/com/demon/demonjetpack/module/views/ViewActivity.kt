package com.demon.demonjetpack.module.views

import android.net.Uri
import com.alibaba.android.arouter.launcher.ARouter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.demon.basemvvm.intent.toActivity
import com.demon.basemvvm.mvvm.BaseViewModel
import com.demon.basemvvm.mvvm.MvvmActivity
import com.demon.basemvvm.utils.dpToPx
import com.demon.basemvvm.utils.launchIO
import com.demon.basemvvm.utils.launchUI
import com.demon.demonjetpack.base.data.RouterConst
import com.demon.demonjetpack.databinding.ActivityViewBinding
import com.demon.demonjetpack.module.views.lighter.LighterActivity
import com.demon.demonjetpack.module.views.motion.MotionActivity
import jp.wasabeef.glide.transformations.RoundedCornersTransformation


class ViewActivity : MvvmActivity<ActivityViewBinding, BaseViewModel>() {

    override fun init() {

        binding.btnViewBinding.setOnClickListener {
            ARouter.getInstance().build(RouterConst.ACT_VIEWBINDING).navigation()
        }

        binding.btnMotion.setOnClickListener {
            toActivity(MotionActivity::class.java)
        }

        binding.btnLighter.setOnClickListener {
            toActivity(LighterActivity::class.java)
        }


        val options = RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .dontAnimate()
            .transform(RoundedCornersTransformation(dpToPx(6), 0, RoundedCornersTransformation.CornerType.ALL))

        launchIO {
            val imgFile = Glide.with(this)
                .asFile()
                .load("http://qqf9nw01d.hn-bkt.clouddn.com/image1813650318662041701.jpg")
                .submit().get()

            launchUI {
                val uri: Uri = Uri.fromFile(imgFile)
                binding.ivImage.setImageURI(uri)
            }
        }
        /*.thumbnail(0.5f)
        .apply(options)
        .into(binding.ivImage)*/
    }

}