package com.demon.demonnewest.module.firebase

import android.content.Intent
import android.net.Uri
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.tencent.mars.xlog.Log

/**
 * @author DeMon
 * Created on 2023/12/20.
 * E-mail demonl@binarywalk.com
 * Desc:
 */
class FCMService : FirebaseMessagingService() {
    private val TAG = "FCMService"
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.i(TAG, "onNewToken: $token")

    }



    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        Log.i(TAG, "onMessageReceived: $message")
        Log.i(TAG, "From: ${message.data}")

        val url = message.data["url"]
        Log.i(TAG, "onMessageReceived: $url")
        url?.run {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(this)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
    }

}