package com.demon.basemvvm.intent

import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment

/**
 * @author DeMon
 * Created on 2020/7/22.
 * E-mail 757454343@qq.com
 * Desc:
 */
class GhostFragment : Fragment() {

    private var requestCode = -1
    private var intent: Intent? = null
    private var callback: ((result: Intent?) -> Unit)? = null

    fun init(requestCode: Int, intent: Intent, callback: ((result: Intent?) -> Unit)) {
        this.requestCode = requestCode
        this.intent = intent
        this.callback = callback
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        intent?.let {
            forActivityResult(it){
                callback?.let { it1 -> it1(it) }
            }.launch()
        }
    }

    override fun onDetach() {
        super.onDetach()
        intent = null
        callback = null
    }
}