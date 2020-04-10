package com.demon.basemvvm.helper

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import com.google.gson.Gson
import javax.inject.Inject


/**
 * @author DeMon
 * Created on 2020/4/10.
 * E-mail 757454343@qq.com
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
 * @date 2015-9-17
 **/
class BroadcastHelper {
    @Inject
    lateinit var mContext: Context

    private val receiverMap: MutableMap<String, BroadcastReceiver>
    private val RESULT = "Result"

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
    fun addAction(action: String, receiver: BroadcastReceiver) {
        try {
            val filter = IntentFilter()
            filter.addAction(action)
            mContext.registerReceiver(receiver, filter)
            receiverMap[action] = receiver
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
    @JvmOverloads
    fun sendBroadcast(action: String, s: String = "") {
        val intent = Intent()
        intent.action = action
        intent.putExtra(RESULT, s)
        mContext.sendBroadcast(intent)
    }

    /**
     * 销毁广播
     *
     * @param action
     */
    fun destroy(action: String) {
        val receiver = receiverMap.remove(action)
        if (receiver != null) {
            mContext.unregisterReceiver(receiver)
        }
    }

    companion object {
        /**
         * [获取BroadcastHelper实例，单例模式实现]
         *
         * @param context
         * @return
         */
        fun getInstance() = Helper.instance
    }

    private object Helper {
        val instance = BroadcastHelper()
    }

}