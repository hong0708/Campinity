package com.ssafy.campinity.data.remote.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.ContentValues
import android.content.Context
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.ssafy.campinity.R
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class FirebaseService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d(ContentValues.TAG, "Refreshed token: $token")
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        if (remoteMessage.data.isNotEmpty()) {
            val body = remoteMessage.data["body"]
            sendNotification(body)
        } else if (remoteMessage.notification != null) {
            val body = remoteMessage.notification!!.body
            sendNotification(body)
        }
    }

    private fun sendNotification(body: String?) {
        //val intent = Intent(this, JoinActivity::class.java)
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
       // val pendingIntent = PendingIntent.getActivity(this, 0, PendingIntent.FLAG_ONE_SHOT)

        val CHANNEL_ID = getString(R.string.notification_channel_id)
        val CHANNEL_NAME = getString(R.string.notification_channel_name)
        val notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentText(body)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val channel = NotificationChannel(
            CHANNEL_ID,
            CHANNEL_NAME,
            NotificationManager.IMPORTANCE_HIGH
        )
        notificationManager.createNotificationChannel(channel)

        notificationManager.notify(0, notificationBuilder.build())
    }

    suspend fun getCurrentToken() = suspendCoroutine<String> { continuation ->
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (task.isSuccessful) {
                val token = task.result
                Log.d(ContentValues.TAG, token)
                continuation.resume(token)
            } else {
                Log.w(ContentValues.TAG, "Fetching FCM registration token failed", task.exception)
                continuation.resume("")
                return@OnCompleteListener
            }
        })
    }
}