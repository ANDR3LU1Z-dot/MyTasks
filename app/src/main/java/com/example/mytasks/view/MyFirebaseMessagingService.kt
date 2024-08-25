package com.example.mytasks.view

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


class MyFirebaseMessagingService(): FirebaseMessagingService()   {

    override fun onNewToken(token: String) {
        Log.d("FCM", "Token de dispositivo: $token")
        super.onNewToken(token)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        Log.d("FCM", "Mensagem recebida: ${message.data}")
        super.onMessageReceived(message)
    }

}