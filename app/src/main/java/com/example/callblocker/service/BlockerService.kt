package com.example.callblocker.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.telecom.Call
import android.telecom.CallScreeningService
import androidx.core.app.NotificationCompat
import com.example.callblocker.models.PrefixStore
import kotlin.jvm.java

class BlockerService : CallScreeningService() {
    override fun onScreenCall(callDetails: Call.Details) {
        val response = CallResponse.Builder()

        if (callDetails.callDirection == Call.Details.DIRECTION_INCOMING) {
            val store = PrefixStore(this)
            val number = callDetails.handle?.schemeSpecificPart // null for hidden numbers

            if (store.enabled && number != null && store.matches(number)) {
                response
                    .setDisallowCall(true)      // no ring
                    .setRejectCall(true)        // caller gets rejected
                    .setSkipNotification(true)  // we show our own
                notifyBlocked(number)
            }
        }
        respondToCall(callDetails, response.build())
    }

    private fun notifyBlocked(number: String) {
        val nm = getSystemService(NotificationManager::class.java)
        nm.createNotificationChannel(
            NotificationChannel("blocked", "Blocked calls", NotificationManager.IMPORTANCE_LOW)
        )
        val n = NotificationCompat.Builder(this, "blocked")
            .setSmallIcon(android.R.drawable.ic_menu_call)
            .setContentTitle("Call blocked")
            .setContentText(number)
            .setAutoCancel(true)
            .build()
        nm.notify(System.currentTimeMillis().toInt(), n)
    }
}