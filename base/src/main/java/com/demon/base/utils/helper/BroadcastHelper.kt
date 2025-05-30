package com.demon.base.utils.helper

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import com.demon.base.BaseApp
import com.demon.base.utils.ext.Tag
import com.google.gson.Gson
import com.tencent.mars.xlog.Log


/**
 * @author DeMon
 * Created on 2020/4/10.
 * E-mail idemon_liu@qq.com
 * Desc:
 * [A brief description]
 * <p/>
 * //在任何地方发送广播
 * BroadcastHelper.getInstance().sendBroadcast(FindOrderActivity.ACTION_RECEIVE_MESSAGE);
 * <p/>
 * //页面在oncreate中初始化广播
 * BroadcastHelper.getInstance().addAction(ACTION_RECEIVE_MESSAGE, new BroadcastReceiver(){
 * @Override public void onReceive(Context arg0, Intent intent) {
 * String command = intent.getAction();
 * if(!TextUtils.isEmpty(command)){
 * if((ACTION_RECEIVE_MESSAGE).equals(command)){
 * //获取json结果
 * String json = intent.getStringExtra("result");
 * //做你该做的事情
 * }
 * }
 * }
 * });
 * <p/>
 * //页面在ondestory销毁广播
 * BroadcastHelper.getInstance().destroy(ACTION_RECEIVE_MESSAGE);
 **/

class BroadcastHelper constructor() {


    private val receiverMap: MutableMap<String, BroadcastReceiver>

    private val mContext by lazy {
        BaseApp.appContext
    }

    /**
     * 构造方法
     *
     * @param context
     */
    init {
        receiverMap = HashMap()
    }

    /**
     * 添加
     *
     * @param
     */
    fun addAction(receiver: BroadcastReceiver, vararg actions: String) {
        var key = ""
        actions.forEach {
            key += it
        }
        Log.i(Tag, "addAction: $key")
        try {
            val filter = IntentFilter()
            actions.forEach {
                filter.addAction(it)
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                mContext.registerReceiver(receiver, filter, Context.RECEIVER_NOT_EXPORTED)
            } else {
                mContext.registerReceiver(receiver, filter)
            }
            receiverMap[key] = receiver
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 发送广播
     *
     * @param action 唯一码
     * @param obj    参数
     */
    fun sendBroadcast(action: String, obj: Any?) {
        try {
            val intent = Intent()
            intent.action = action
            obj?.run {
                intent.putExtra(RESULT, Gson().toJson(this))
            }
            mContext.sendBroadcast(intent)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 发送参数为 String 的数据广播
     * @param action 唯一码
     */
    fun sendBroadcast(action: String, s: String = "") {
        try {
            val intent = Intent()
            intent.action = action
            intent.putExtra(RESULT, s)
            mContext.sendBroadcast(intent)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 销毁广播
     *
     * @param actions
     */
    fun destroy(vararg actions: String) {
        var key = ""
        actions.forEach {
            key += it
        }
        Log.i(Tag, "destroy: $key")
        try {
            val receiver = receiverMap.remove(key)
            if (receiver != null) {
                mContext.unregisterReceiver(receiver)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    companion object {
        val RESULT = "Result"

        val instance: BroadcastHelper by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            BroadcastHelper()
        }
    }

}