package com.demon.basemvvm.intent

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

/**
 * @author DeMon
 * Created on 2021/10/20.
 * E-mail 757454343@qq.com
 * Desc: Activity Results API
 */
class PairActivityResultContract constructor(private val cls: Class<*>) : ActivityResultContract<Array<Pair<String, Any?>>, Intent>() {

    override fun createIntent(context: Context, input: Array<Pair<String, Any?>>): Intent {
        val intent = Intent(context, cls)
        intent.putExtras(*input)
        return intent
    }

    override fun parseResult(resultCode: Int, data: Intent?): Intent? {
        return if (resultCode == Activity.RESULT_OK && data != null) {
            data
        } else {
            null
        }
    }

}

fun ActivityResultLauncher<Array<Pair<String, Any?>>>.launch(vararg extras: Pair<String, Any?>) {
    val array: Array<Pair<String, Any?>> = arrayOf(*extras)
    this.launch(array)
}

class IntentActivityResultContract constructor(private val intent: Intent) : ActivityResultContract<String, Intent>() {
    override fun createIntent(context: Context, input: String?): Intent = intent

    override fun parseResult(resultCode: Int, data: Intent?): Intent? {
        return if (resultCode == Activity.RESULT_OK && data != null) {
            data
        } else {
            null
        }
    }
}

fun ActivityResultLauncher<String>.launch() {
    this.launch("")
}

fun AppCompatActivity.forActivityResult(cls: Class<*>, callback: ((result: Intent?) -> Unit)) =
    registerForActivityResult(PairActivityResultContract(cls), callback)


fun Fragment.forActivityResult(cls: Class<*>, callback: ((result: Intent?) -> Unit)) =
    registerForActivityResult(PairActivityResultContract(cls), callback)

fun AppCompatActivity.forActivityResult(intent: Intent, callback: ((result: Intent?) -> Unit)) =
    registerForActivityResult(IntentActivityResultContract(intent), callback)


fun Fragment.forActivityResult(intent: Intent, callback: ((result: Intent?) -> Unit)) =
    registerForActivityResult(IntentActivityResultContract(intent), callback)
